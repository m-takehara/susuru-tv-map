package me.takehara.domain

import me.takehara.domain.youtube.UnvalidatedYoutubeVideos

data class SusuruTvVideos(val list: List<SusuruTvVideo>) : FCC<SusuruTvVideo>(list) {
    companion object {
        fun from(unvalidated: UnvalidatedYoutubeVideos) = unvalidated
            .filterNonNullFields()
            .filterPublicVideos()
            .map(SusuruTvVideo.Companion::from)
            .let(::SusuruTvVideos)
    }
}
