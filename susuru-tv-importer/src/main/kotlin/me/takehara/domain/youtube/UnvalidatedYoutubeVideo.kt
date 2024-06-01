package me.takehara.domain.youtube

import me.takehara.domain.youtube.valueobject.*

data class UnvalidatedYoutubeVideo(
    val id: Id?,
    val videoId: VideoId?,
    val title: Title?,
    val description: Description?,
    val publishedDate: PublishedDate?,
    val thumbnails: UnvalidatedThumbnails?,
) {
    fun isPublicVideo(): Boolean {
        return this.title?.let { it.value != "Private video" } ?: true
    }

    fun hasNull(): Boolean {
        return this.id == null ||
                this.videoId == null ||
                this.title == null ||
                this.description == null ||
                this.publishedDate == null ||
                this.thumbnails == null ||
                this.thumbnails.default == null ||
                this.thumbnails.high == null ||
                this.thumbnails.maxres == null ||
                this.thumbnails.medium == null ||
                this.thumbnails.standard == null
    }
}

