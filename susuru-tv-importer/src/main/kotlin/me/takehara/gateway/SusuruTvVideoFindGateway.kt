package me.takehara.gateway

import com.google.api.services.youtube.model.PlaylistItem
import io.github.oshai.kotlinlogging.KotlinLogging
import me.takehara.domain.*
import me.takehara.driver.YoutubeApi
import me.takehara.port.SusuruTvVideoFindPort
import java.net.URL
import java.time.ZonedDateTime

class SusuruTvVideoFindGateway(private val youtubeApi: YoutubeApi) : SusuruTvVideoFindPort {
    override fun findBy(channelId: ChannelId, playlistId: PlaylistId): SusuruTvVideos {
        return youtubeApi.findPlaylistItems(channelId.value, playlistId.value).toSusuruTvVideos()
    }
}

private val logger = KotlinLogging.logger {}

internal fun List<PlaylistItem>.toSusuruTvVideos(): SusuruTvVideos {
    return this.mapNotNull {
        try {
            if (it.snippet.title == "Private video") return@mapNotNull null
            it.toSusuruTvVideo()
        } catch (t: Throwable) {
            logger.info { "some error in $it" }
            null
        }
    }.let(::SusuruTvVideos)
}

internal fun PlaylistItem.toSusuruTvVideo() = SusuruTvVideo(
    Id(checkValue({ this.id }, this)),
    VideoId(checkValue({ this.snippet.resourceId.videoId }, this)),
    Title(checkValue({ this.snippet.title }, this)),
    Description(checkValue({ this.snippet.description }, this)),
    PublishedDate(ZonedDateTime.parse(checkValue({ this.snippet.publishedAt.toStringRfc3339() }, this))),
    this.snippet.thumbnails.let { t ->
        Thumbnails(
            checkThumbnail(t.default, this).let { Thumbnail(URL(it.url), it.height.toInt(), it.width.toInt()) },
            checkThumbnail(t.high, this).let { Thumbnail(URL(it.url), it.height.toInt(), it.width.toInt()) },
            checkThumbnail(t.medium, this).let { Thumbnail(URL(it.url), it.height.toInt(), it.width.toInt()) },
            checkThumbnail(t.standard, this).let { Thumbnail(URL(it.url), it.height.toInt(), it.width.toInt()) },
        )
    }
)

private fun <T> checkValue(getter: () -> T, item: PlaylistItem): T {
    val value = getter()
    if (value == null) {
        logger.info { "Invalid PlaylistItem. ${getter}, item: $item" }
    }
    return value
}

private fun checkThumbnail(
    thumbnail: com.google.api.services.youtube.model.Thumbnail?,
    item: PlaylistItem
): com.google.api.services.youtube.model.Thumbnail {
    if (thumbnail == null || thumbnail.url == null || thumbnail.height == null || thumbnail.width == null) {
        logger.info { "Invalid PlaylistItem. Thumbnail: $thumbnail, PlaylistItem: $item" }
    }
    return thumbnail!!
}
