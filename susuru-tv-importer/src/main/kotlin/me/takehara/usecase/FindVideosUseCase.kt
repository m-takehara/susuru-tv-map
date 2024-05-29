package me.takehara.usecase

import me.takehara.domain.ChannelId
import me.takehara.domain.OutputDestinationDirectories
import me.takehara.domain.PlaylistId
import me.takehara.port.SusuruTvVideoFindPort
import me.takehara.port.SusuruTvVideoSavePort

class FindVideosUseCase(
    private val susuruTvVideoFindPort: SusuruTvVideoFindPort,
    private val susuruTvVideoSavePort: SusuruTvVideoSavePort,
) {
    fun execute() {
        val videos = susuruTvVideoFindPort.findBy(ChannelId.SusuruTv, PlaylistId.MainichiRamenSeikatsu)
        susuruTvVideoSavePort.saveTo(OutputDestinationDirectories.OUTPUT_DIR.path, videos)
    }
}
