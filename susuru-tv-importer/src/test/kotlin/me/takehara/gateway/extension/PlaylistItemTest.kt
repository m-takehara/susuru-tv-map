package me.takehara.gateway.extension

import com.google.api.client.util.DateTime
import com.google.api.services.youtube.model.PlaylistItem
import com.google.api.services.youtube.model.PlaylistItemSnippet
import com.google.api.services.youtube.model.ResourceId
import com.google.api.services.youtube.model.ThumbnailDetails
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockkStatic
import me.takehara.domain.*
import me.takehara.gateway.SusuruTvVideoFindGateway
import me.takehara.gateway.toSusuruTvVideo
import java.net.URL
import java.time.ZonedDateTime

class PlaylistItemTest : FreeSpec({
    "PlaylistItemをSusuruTvVideoに変換する" {
        mockkStatic(SusuruTvVideoFindGateway::class.qualifiedName + "Kt")
        val resourceId = ResourceId()
            .setVideoId("videoId")
        val thumbnails = ThumbnailDetails()
            .setDefault(
                com.google.api.services.youtube.model.Thumbnail().setUrl("http://default").setWidth(100).setHeight(100)
            )
            .setHigh(
                com.google.api.services.youtube.model.Thumbnail().setUrl("http://high").setWidth(200).setHeight(200)
            )
            .setMedium(
                com.google.api.services.youtube.model.Thumbnail().setUrl("http://medium").setWidth(400).setHeight(400)
            )
            .setStandard(
                com.google.api.services.youtube.model.Thumbnail().setUrl("http://standard").setWidth(500).setHeight(500)
            )
        val snippet = PlaylistItemSnippet()
            .setTitle("title")
            .setDescription("description")
            .setPublishedAt(DateTime("2024-05-27T07:11:52.000Z"))
            .setResourceId(resourceId)
            .setThumbnails(thumbnails)
        val target = PlaylistItem()
            .setId("id")
            .setSnippet(snippet)

        val expected = SusuruTvVideo(
            Id("id"),
            VideoId("videoId"),
            Title("title"),
            Description("description"),
            PublishedDate(ZonedDateTime.parse("2024-05-27T07:11:52.000Z")),
            Thumbnails(
                Thumbnail(URL("http://default"), 100, 100),
                Thumbnail(URL("http://high"), 200, 200),
                Thumbnail(URL("http://medium"), 400, 400),
                Thumbnail(URL("http://standard"), 500, 500),
            )
        )
        val actual = target.toSusuruTvVideo()
        actual shouldBe expected
    }
})
