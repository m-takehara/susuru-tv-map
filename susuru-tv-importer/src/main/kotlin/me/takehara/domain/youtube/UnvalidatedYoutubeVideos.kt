package me.takehara.domain.youtube

import me.takehara.domain.FCC

data class UnvalidatedYoutubeVideos(val list: List<UnvalidatedYoutubeVideo>): FCC<UnvalidatedYoutubeVideo>(list) {
    fun filterNonNullFields(): UnvalidatedYoutubeVideos {
        return UnvalidatedYoutubeVideos(this.list.filterNot { it.hasNull() })
    }

    fun filterPublicVideos(): UnvalidatedYoutubeVideos {
        return UnvalidatedYoutubeVideos(this.list.filter { it.isPublicVideo() })
    }
}