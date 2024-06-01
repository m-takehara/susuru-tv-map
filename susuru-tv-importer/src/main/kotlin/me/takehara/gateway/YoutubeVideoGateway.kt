package me.takehara.gateway

import com.google.api.services.youtube.model.PlaylistItem
import me.takehara.domain.youtube.ChannelId
import me.takehara.domain.youtube.PlaylistId
import me.takehara.domain.youtube.UnvalidatedYoutubeVideo
import me.takehara.domain.youtube.UnvalidatedYoutubeVideos
import me.takehara.domain.youtube.valueobject.*
import me.takehara.driver.YoutubeApi
import me.takehara.port.YoutubeVideoPort
import java.net.URL
import java.time.ZonedDateTime

class YoutubeVideoGateway(private val youtubeApi: YoutubeApi) : YoutubeVideoPort {
    override suspend fun findBy(channelId: ChannelId, playlistId: PlaylistId): UnvalidatedYoutubeVideos {
        return youtubeApi.findPlaylistItems(channelId.value, playlistId.value).let(::toUnvalidatedYoutubeVideos)
    }

    internal fun toUnvalidatedYoutubeVideos(items: List<PlaylistItem>) =
        UnvalidatedYoutubeVideos(items.map(::toUnvalidatedYoutubeVideo))

    internal fun toUnvalidatedYoutubeVideo(item: PlaylistItem) = UnvalidatedYoutubeVideo(
        item.id?.let(::Id),
        item.snippet?.resourceId?.videoId?.let(::VideoId),
        item.snippet?.title?.let(::Title),
        item.snippet?.description?.let(::Description),
        item.snippet?.publishedAt?.let { PublishedDate(ZonedDateTime.parse(it.toStringRfc3339())) },
        item.snippet?.thumbnails?.let { t ->
            UnvalidatedThumbnails(
                t.default?.let { Thumbnail(URL(it.url), it.height.toInt(), it.width.toInt()) },
                t.high?.let { Thumbnail(URL(it.url), it.height.toInt(), it.width.toInt()) },
                t.maxres?.let { Thumbnail(URL(it.url), it.height.toInt(), it.width.toInt()) },
                t.medium?.let { Thumbnail(URL(it.url), it.height.toInt(), it.width.toInt()) },
                t.standard?.let { Thumbnail(URL(it.url), it.height.toInt(), it.width.toInt()) },
            )
        }
    )
}
