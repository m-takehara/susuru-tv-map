package me.takehara.gateway

import io.kotest.core.spec.style.FreeSpec
import io.mockk.*
import me.takehara.domain.OutputDestination
import me.takehara.domain.SusuruTvVideo
import me.takehara.domain.SusuruTvVideos
import me.takehara.domain.youtube.valueobject.*
import me.takehara.driver.FileDriver
import java.net.URL
import java.time.ZonedDateTime

class SusuruTvVideoGatewayTest : FreeSpec({
    "SusuruTvVideosを保存する" {
        val fileDriver = mockk<FileDriver>()
        val dest = OutputDestination.OutputDir
        val now = ZonedDateTime.now()
        val videos = SusuruTvVideos(
            listOf(
                SusuruTvVideo(
                    Id("id"),
                    VideoId("videoId"),
                    Title("title"),
                    Description("description"),
                    PublishedDate(now),
                    Thumbnails(
                        Thumbnail(URL("https://default"), 101, 102),
                        Thumbnail(URL("https://high"), 201, 202),
                        Thumbnail(URL("https://maxres"), 301, 302),
                        Thumbnail(URL("https://medium"), 401, 402),
                        Thumbnail(URL("https://standard"), 501, 502),
                    )
                )
            )
        )
        val rows = listOf(
            "id,videoId,title,publishedDate,thumbnail.default,thumbnail.high,thumbnail.maxres,thumbnail.medium,thumbnail.standard",
            "id,videoId,title,$now,https://default,https://high,https://maxres,https://medium,https://standard",
        )

        coEvery { fileDriver.saveAll(any(), any()) } just runs
        val target = SusuruTvVideoGateway(fileDriver, dest)
        target.save(videos)

        coVerify { fileDriver.saveAll(rows, dest.path) }
    }
})
