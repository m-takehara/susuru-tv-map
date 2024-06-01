package me.takehara.domain

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import me.takehara.domain.youtube.UnvalidatedYoutubeVideo
import me.takehara.domain.youtube.UnvalidatedYoutubeVideos

class UnvalidatedYoutubeVideosTest : FreeSpec({
    "filterNonNullFields" - {
        "nullを含むUnvalidatedYoutubeVideoを除外できる" {
            val item1 = mockk<UnvalidatedYoutubeVideo>()
            val item2 = mockk<UnvalidatedYoutubeVideo>()
            val item3 = mockk<UnvalidatedYoutubeVideo>()
            val items = UnvalidatedYoutubeVideos(listOf(item1, item2, item3))

            every { item1.hasNull() } returns true
            every { item2.hasNull() } returns false
            every { item3.hasNull() } returns true

            val expected = UnvalidatedYoutubeVideos(listOf(item2))
            items.filterNonNullFields() shouldBe expected
        }
    }

    "filterPublicVideos" - {
        "Privateな動画を除外できる" {
            val item1 = mockk<UnvalidatedYoutubeVideo>()
            val item2 = mockk<UnvalidatedYoutubeVideo>()
            val item3 = mockk<UnvalidatedYoutubeVideo>()
            val items = UnvalidatedYoutubeVideos(listOf(item1, item2, item3))

            every { item1.isPublicVideo() } returns true
            every { item2.isPublicVideo() } returns false
            every { item3.isPublicVideo() } returns true

            val expected = UnvalidatedYoutubeVideos(listOf(item1, item3))
            items.filterPublicVideos() shouldBe expected
        }
    }
})
