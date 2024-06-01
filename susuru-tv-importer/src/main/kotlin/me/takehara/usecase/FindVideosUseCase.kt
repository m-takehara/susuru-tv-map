package me.takehara.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.takehara.domain.SusuruTvVideos
import me.takehara.domain.youtube.ChannelId
import me.takehara.domain.youtube.PlaylistId
import me.takehara.port.SusuruTvVideoPort
import me.takehara.port.YoutubeVideoPort

class FindVideosUseCase(
    private val youtubeVideoPort: YoutubeVideoPort,
    private val susuruTvVideoPort: SusuruTvVideoPort,
) {
    suspend fun execute() = withContext(Dispatchers.IO) {
        val unvalidated = youtubeVideoPort.findBy(ChannelId.SusuruTv, PlaylistId.MainichiRamenSeikatsu)
        val videos = SusuruTvVideos.from(unvalidated)
        susuruTvVideoPort.save(videos)
    }
}
