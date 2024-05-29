package me.takehara.gateway.extension

import com.google.api.services.youtube.model.PlaylistItem
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import me.takehara.domain.SusuruTvVideo
import me.takehara.domain.SusuruTvVideos
import me.takehara.gateway.SusuruTvVideoFindGateway
import me.takehara.gateway.toSusuruTvVideo
import me.takehara.gateway.toSusuruTvVideos

class PlaylistItemListTest : FreeSpec({
    "List<PlaylistItem>をSusuruTvVideosに変換する" {
        mockkStatic(SusuruTvVideoFindGateway::class.qualifiedName + "Kt")
        val item1 = mockk<PlaylistItem>()
        val item2 = mockk<PlaylistItem>()
        val item3 = mockk<PlaylistItem>()
        val target = listOf(item1, item2, item3)

        val video1 = mockk<SusuruTvVideo>()
        val video2 = mockk<SusuruTvVideo>()
        val video3 = mockk<SusuruTvVideo>()
        every { item1.toSusuruTvVideo() } returns video1
        every { item2.toSusuruTvVideo() } returns video2
        every { item3.toSusuruTvVideo() } returns video3
        val expected = SusuruTvVideos(listOf(video1, video2, video3))

        val actual = target.toSusuruTvVideos()
        actual shouldBe expected
    }

    "必要な項目にnullを含むPlaylistItemがある場合はそれを無視する" {
        mockkStatic(SusuruTvVideoFindGateway::class.qualifiedName + "Kt")
        val item1 = mockk<PlaylistItem>()
        val item2 = mockk<PlaylistItem>()
        val item3 = mockk<PlaylistItem>()
        val target = listOf(item1, item2, item3)

        val video1 = mockk<SusuruTvVideo>()
        val video2 = mockk<SusuruTvVideo>()
        every { item1.toSusuruTvVideo() } returns video1
        every { item2.toSusuruTvVideo() } returns video2
        every { item3.toSusuruTvVideo() } throws NullPointerException()
        val expected = SusuruTvVideos(listOf(video1, video2))

        val actual = target.toSusuruTvVideos()
        actual shouldBe expected
    }
})
