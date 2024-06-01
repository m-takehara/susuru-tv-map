package me.takehara.domain

import me.takehara.domain.youtube.*
import me.takehara.domain.youtube.valueobject.*

data class SusuruTvVideo(
    val id: Id,
    val videoId: VideoId,
    val title: Title,
    val description: Description,
    val publishedDate: PublishedDate,
    val thumbnail: Thumbnails,
) {
    companion object {
        fun from(unvalidated: UnvalidatedYoutubeVideo) = SusuruTvVideo(
            unvalidated.id!!,
            unvalidated.videoId!!,
            unvalidated.title!!,
            unvalidated.description!!,
            unvalidated.publishedDate!!,
            unvalidated.thumbnails!!.let {
                Thumbnails(
                    it.default!!,
                    it.high!!,
                    it.maxres!!,
                    it.medium!!,
                    it.standard!!
                )
            }
        )
    }
}
