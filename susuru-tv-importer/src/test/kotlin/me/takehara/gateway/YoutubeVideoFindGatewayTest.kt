package me.takehara.gateway

import com.google.api.client.util.DateTime
import com.google.api.services.youtube.model.PlaylistItem
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import me.takehara.domain.youtube.*
import me.takehara.domain.youtube.valueobject.*
import me.takehara.driver.YoutubeApi
import java.net.URL
import java.time.ZonedDateTime

class YoutubeVideoFindGatewayTest : FreeSpec({
    "findBy" - {
        "Driverから取得した情報をUnvalidatedYoutubeVideoに変換する" {
            val youtubeApi = mockk<YoutubeApi>()
            val target = YoutubeVideoGateway(youtubeApi).let(::spyk)

            val playlistItemList = mockk<List<PlaylistItem>>()
            val expected = mockk<UnvalidatedYoutubeVideos>()
            every { youtubeApi.findPlaylistItems("channelId", "playlistId") } returns playlistItemList
            every { target.toUnvalidatedYoutubeVideos(playlistItemList) } returns expected

            val channelId = mockk<ChannelId> { every { value } returns "channelId" }
            val playlistId = mockk<PlaylistId> { every { value } returns "playlistId" }
            val actual = target.findBy(channelId, playlistId)

            actual shouldBe expected
        }
    }

    "toUnvalidatedYoutubeVideos" - {
        "List<PlaylistItem>をtoUnvalidatedYoutubeVideosに変換する" {
            val item1 = mockk<PlaylistItem>()
            val item2 = mockk<PlaylistItem>()
            val item3 = mockk<PlaylistItem>()
            val video1 = mockk<UnvalidatedYoutubeVideo>()
            val video2 = mockk<UnvalidatedYoutubeVideo>()
            val video3 = mockk<UnvalidatedYoutubeVideo>()
            val gateway = YoutubeVideoGateway(mockk()).let(::spyk)

            every { gateway.toUnvalidatedYoutubeVideo(item1) } returns video1
            every { gateway.toUnvalidatedYoutubeVideo(item2) } returns video2
            every { gateway.toUnvalidatedYoutubeVideo(item3) } returns video3

            val actual = gateway.toUnvalidatedYoutubeVideos(listOf(item1, item2, item3))
            val expected = UnvalidatedYoutubeVideos(listOf(video1, video2, video3))
            actual shouldBe expected
        }
    }

    "toUnvalidatedYoutubeVideo" - {
        "PlaylistItemをUnvalidatedYoutubeVideoに変換する" {
            val gateway = YoutubeVideoGateway(mockk())
            val item = createPlaylistItem()
            val expected = createExpected()
            val actual = gateway.toUnvalidatedYoutubeVideo(item)
            actual shouldBe expected
        }
        "PlaylistItemがnullを持つ場合 " - {
            "PlaylistItem.id" {
                val gateway = YoutubeVideoGateway(mockk())
                val item = createPlaylistItem()
                every { item.id } returns null
                val expected = createExpected().copy(id = null)
                val actual = gateway.toUnvalidatedYoutubeVideo(item)
                actual shouldBe expected
            }
            "PlaylistItem.snippet" {
                val gateway = YoutubeVideoGateway(mockk())
                val item = createPlaylistItem()
                every { item.snippet } returns null
                val expected = UnvalidatedYoutubeVideo(Id("id"), null, null, null, null, null)
                val actual = gateway.toUnvalidatedYoutubeVideo(item)
                actual shouldBe expected
            }
            "PlaylistItem.snippet.resourceId" {
                val gateway = YoutubeVideoGateway(mockk())
                val item = createPlaylistItem()
                every { item.snippet.resourceId } returns null
                val expected = createExpected().copy(videoId = null)
                val actual = gateway.toUnvalidatedYoutubeVideo(item)
                actual shouldBe expected
            }
            "PlaylistItem.snippet.title" {
                val gateway = YoutubeVideoGateway(mockk())
                val item = createPlaylistItem()
                every { item.snippet.title } returns null
                val expected = createExpected().copy(title = null)
                val actual = gateway.toUnvalidatedYoutubeVideo(item)
                actual shouldBe expected
            }
            "PlaylistItem.snippet.description" {
                val gateway = YoutubeVideoGateway(mockk())
                val item = createPlaylistItem()
                every { item.snippet.description } returns null
                val expected = createExpected().copy(description = null)
                val actual = gateway.toUnvalidatedYoutubeVideo(item)
                actual shouldBe expected
            }
            "PlaylistItem.snippet.publishedAt" {
                val gateway = YoutubeVideoGateway(mockk())
                val item = createPlaylistItem()
                every { item.snippet.publishedAt } returns null
                val expected = createExpected().copy(publishedDate = null)
                val actual = gateway.toUnvalidatedYoutubeVideo(item)
                actual shouldBe expected
            }
            "PlaylistItem.snippet.thumbnails" {
                val gateway = YoutubeVideoGateway(mockk())
                val item = createPlaylistItem()
                every { item.snippet.thumbnails } returns null
                val expected = createExpected().copy(thumbnails = null)
                val actual = gateway.toUnvalidatedYoutubeVideo(item)
                actual shouldBe expected
            }
            "PlaylistItem.snippet.thumbnails.maxres" {
                val gateway = YoutubeVideoGateway(mockk())
                val item = createPlaylistItem()
                every { item.snippet.thumbnails.maxres } returns null
                val thumbnails = createExpected().thumbnails!!.copy(maxres = null)
                val expected = createExpected().copy(thumbnails = thumbnails)
                val actual = gateway.toUnvalidatedYoutubeVideo(item)
                actual shouldBe expected
            }
        }
    }
})

private fun createPlaylistItem(): PlaylistItem {
    val item = mockk<PlaylistItem>()
    every { item.id } returns "id"
    every { item.snippet.resourceId.videoId } returns "videoId"
    every { item.snippet.title } returns "title"
    every { item.snippet.description } returns "description"
    every { item.snippet.publishedAt } returns DateTime.parseRfc3339("2024-05-31T01:30:55.020Z")
    every { item.snippet.thumbnails.default } returns createThumbnailMock("http://default", 101, 102)
    every { item.snippet.thumbnails.high } returns createThumbnailMock("http://high", 201, 202)
    every { item.snippet.thumbnails.maxres } returns createThumbnailMock("http://maxres", 301, 302)
    every { item.snippet.thumbnails.medium } returns createThumbnailMock("http://medium", 401, 402)
    every { item.snippet.thumbnails.standard } returns createThumbnailMock("http://standard", 501, 502)
    return item
}

private fun createThumbnailMock(
    url: String,
    height: Long,
    width: Long
): com.google.api.services.youtube.model.Thumbnail {
    return com.google.api.services.youtube.model.Thumbnail()
        .setUrl(url)
        .setHeight(height)
        .setWidth(width)
}

private fun createExpected() = UnvalidatedYoutubeVideo(
    Id("id"),
    VideoId("videoId"),
    Title("title"),
    Description("description"),
    PublishedDate(ZonedDateTime.parse("2024-05-31T01:30:55.020Z")),
    UnvalidatedThumbnails(
        Thumbnail(URL("http://default"), 101, 102),
        Thumbnail(URL("http://high"), 201, 202),
        Thumbnail(URL("http://maxres"), 301, 302),
        Thumbnail(URL("http://medium"), 401, 402),
        Thumbnail(URL("http://standard"), 501, 502),
    )
)
