package me.takehara.driver

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.PlaylistItem
import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.InputStreamReader

class YoutubeApi {
    private val logger = KotlinLogging.logger {}
    private val youtubeService: YouTube = getService()

    fun findPlaylistItems(channelId: String, playlistId: String): List<PlaylistItem> {
        logger.info { "Fetching playlist items from YouTube API" }
        return fetchPlaylistItemsRecursively(channelId, playlistId, null)
    }

    private fun fetchPlaylistItemsRecursively(
        channelId: String,
        playlistId: String,
        pageToken: String?
    ): List<PlaylistItem> {
        val request = youtubeService.playlistItems()
            .list(channelId)
            .setPlaylistId(playlistId)
            .setPart("snippet")
            .setMaxResults(200)

        if (pageToken != null) {
            request.setPageToken(pageToken)
        }

        val response = request.execute()
        val currentItems = response.items ?: listOf()

        return if (response.nextPageToken.isNullOrEmpty()) {
            logger.info { "Fetched ${currentItems.size} items" }
            currentItems
        } else {
            currentItems + fetchPlaylistItemsRecursively(channelId, playlistId, response.nextPageToken)
        }
    }
}

private const val CLIENT_SECRETS = "client_secret.json"
private val SCOPES: Collection<String> = mutableListOf("https://www.googleapis.com/auth/youtube.readonly")

private const val APPLICATION_NAME = "SUSURU TV Importer"
private val JSON_FACTORY: JsonFactory = GsonFactory.getDefaultInstance()

fun getAuthorize(httpTransport: HttpTransport): Credential {
    val `in` = Thread.currentThread().contextClassLoader.getResourceAsStream(CLIENT_SECRETS)
    val clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, InputStreamReader(`in`!!))
    val flow = GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets, SCOPES).build()
    return AuthorizationCodeInstalledApp(flow, LocalServerReceiver()).authorize("user")
}

fun getService(): YouTube {
    val httpTransport = GoogleNetHttpTransport.newTrustedTransport()
    val credential = getAuthorize(httpTransport)
    return YouTube.Builder(httpTransport, JSON_FACTORY, credential)
        .setApplicationName(APPLICATION_NAME)
        .build()
}
