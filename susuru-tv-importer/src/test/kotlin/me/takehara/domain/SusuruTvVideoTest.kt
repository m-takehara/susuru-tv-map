package me.takehara.domain

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import me.takehara.domain.youtube.*
import me.takehara.domain.youtube.valueobject.*
import java.net.URL
import java.time.ZonedDateTime

class SusuruTvVideoTest : FreeSpec({
    "UnvalidatedYoutubeVideoからSusuruTvVideoを生成する" {
        val now = ZonedDateTime.now()
        val unvalidated = UnvalidatedYoutubeVideo(
            Id("id"),
            VideoId("videoId"),
            Title("title"),
            Description("description"),
            PublishedDate(now),
            UnvalidatedThumbnails(
                Thumbnail(URL("http://default"), 101, 102),
                Thumbnail(URL("http://high"), 201, 202),
                Thumbnail(URL("http://maxres"), 301, 302),
                Thumbnail(URL("http://medium"), 401, 402),
                Thumbnail(URL("http://standard"), 501, 502)
            )
        )
        val expected = SusuruTvVideo(
            Id("id"),
            VideoId("videoId"),
            Title("title"),
            Description("description"),
            PublishedDate(now),
            Thumbnails(
                Thumbnail(URL("http://default"), 101, 102),
                Thumbnail(URL("http://high"), 201, 202),
                Thumbnail(URL("http://maxres"), 301, 302),
                Thumbnail(URL("http://medium"), 401, 402),
                Thumbnail(URL("http://standard"), 501, 502)
            )
        )
        SusuruTvVideo.from(unvalidated) shouldBe expected
    }
})
