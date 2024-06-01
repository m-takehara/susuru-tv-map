package me.takehara.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.takehara.domain.youtube.ChannelId
import me.takehara.domain.OutputDestination
import me.takehara.domain.youtube.PlaylistId
import me.takehara.domain.SusuruTvVideos
import me.takehara.port.YoutubeVideoPort
import me.takehara.port.SusuruTvVideoPort

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
