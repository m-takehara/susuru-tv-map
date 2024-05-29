package me.takehara.usecase

import io.kotest.core.spec.style.FreeSpec
import io.mockk.*
import me.takehara.domain.ChannelId
import me.takehara.domain.OutputDestinationDirectories.OUTPUT_DIR
import me.takehara.domain.PlaylistId
import me.takehara.domain.SusuruTvVideos
import me.takehara.port.SusuruTvVideoFindPort
import me.takehara.port.SusuruTvVideoSavePort

class FindVideosUseCaseTest : FreeSpec() {
    init {
        "SUSURU TVの動画一覧を取得して保存する" {
            val susuruTvVideos = mockk<SusuruTvVideos>()

            val susuruTvVideoFindPort = mockk<SusuruTvVideoFindPort>()
            coEvery { susuruTvVideoFindPort.findBy(ChannelId.SusuruTv, PlaylistId.MainichiRamenSeikatsu) } returns susuruTvVideos
            val susuruTvVideoSavePort = mockk<SusuruTvVideoSavePort>()
            coEvery { susuruTvVideoSavePort.saveTo(any(), any()) } just runs

            val target = FindVideosUseCase(susuruTvVideoFindPort, susuruTvVideoSavePort)
            target.execute()

            coVerify { susuruTvVideoSavePort.saveTo(OUTPUT_DIR.path, susuruTvVideos) }
        }
    }
}
