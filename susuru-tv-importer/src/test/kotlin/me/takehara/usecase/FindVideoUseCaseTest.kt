package me.takehara.usecase

import io.kotest.core.spec.style.FreeSpec
import io.mockk.*
import me.takehara.domain.SusuruTvVideos
import me.takehara.domain.youtube.ChannelId
import me.takehara.domain.youtube.PlaylistId
import me.takehara.domain.youtube.UnvalidatedYoutubeVideos
import me.takehara.port.SusuruTvVideoPort
import me.takehara.port.YoutubeVideoPort

class FindVideoUseCaseTest : FreeSpec({
    "SUSURU TVの動画一覧を取得して保存する" {
        mockkObject(SusuruTvVideos)
        val findPort = mockk<YoutubeVideoPort>()
        val savePort = mockk<SusuruTvVideoPort>()
        val unvalidated = mockk<UnvalidatedYoutubeVideos>()
        val videos = mockk<SusuruTvVideos>()

        coEvery { findPort.findBy(ChannelId.SusuruTv, PlaylistId.MainichiRamenSeikatsu) } returns unvalidated
        every { SusuruTvVideos.from(unvalidated) } returns videos
        coEvery { savePort.save(videos) } just runs

        val target = FindVideosUseCase(findPort, savePort)
        target.execute()

        coVerify { savePort.save(videos) }
    }
})
