package me.takehara.gateway

import com.google.api.services.youtube.model.PlaylistItem
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import me.takehara.domain.*
import me.takehara.driver.YoutubeApi

class SusuruTvVideoFindGatewayTest : FreeSpec({
    "Driverから取得した情報をSusuruTvVideosに変換する" {
        mockkStatic(SusuruTvVideoFindGateway::class.qualifiedName + "Kt")
        val channelId = ChannelId.SusuruTv
        val playlistId = PlaylistId.MainichiRamenSeikatsu
        val youtubeApi = mockk<YoutubeApi>()
        val target = SusuruTvVideoFindGateway(youtubeApi)

        val playlistItemList = mockk<List<PlaylistItem>>()
        val expected = mockk<SusuruTvVideos>()
        every { youtubeApi.findPlaylistItems(channelId.value, playlistId.value) } returns playlistItemList
        every { playlistItemList.toSusuruTvVideos() } returns expected

        val actual = target.findBy(channelId, playlistId)

        actual shouldBe expected
    }
})
