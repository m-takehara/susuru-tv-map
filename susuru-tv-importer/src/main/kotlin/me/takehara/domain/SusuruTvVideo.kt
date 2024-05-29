package me.takehara.domain

import java.net.URL
import java.time.ZonedDateTime

data class SusuruTvVideos(val list: List<SusuruTvVideo>): List<SusuruTvVideo> by list
data class SusuruTvVideo(
    val id: Id,
    val videoId: VideoId,
    val title: Title,
    val description: Description,
    val publishedDate: PublishedDate,
    val thumbnail: Thumbnails,
)

data class Id(val value: String)
data class VideoId(val value: String)
data class Title(val value: String)
data class Description(val value: String)
data class PublishedDate(val date: ZonedDateTime)
data class Thumbnails(
    val default: Thumbnail,
    val high: Thumbnail,
    val medium: Thumbnail,
    val standard: Thumbnail,
)
data class Thumbnail(
    val url: URL,
    val height: Int,
    val width: Int,
)
