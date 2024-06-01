package me.takehara.domain

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verify
import me.takehara.domain.youtube.UnvalidatedYoutubeVideos

class SusuruTvVideosTest : FreeSpec({
    "データ不備のあるUnvalidatedYoutubeVideoを除外した上でSusuruTvVideosを生成できる" {
        mockkObject(SusuruTvVideo)
        val unvalidated = mockk<UnvalidatedYoutubeVideos>()
        val videos = mockk<List<SusuruTvVideo>>()

        every { unvalidated.filterPublicVideos() } returns unvalidated
        every { unvalidated.filterNonNullFields() } returns unvalidated
        every { unvalidated.map(SusuruTvVideo::from) } returns videos

        val actual = SusuruTvVideos.from(unvalidated)
        actual shouldBe SusuruTvVideos(videos)

        verify { unvalidated.filterPublicVideos() }
        verify { unvalidated.filterNonNullFields() }
    }
})
