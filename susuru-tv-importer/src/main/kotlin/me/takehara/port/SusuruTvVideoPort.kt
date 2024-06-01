package me.takehara.port

import me.takehara.domain.SusuruTvVideos

interface SusuruTvVideoPort {
    suspend fun save(susuruTvVideos: SusuruTvVideos)
}
