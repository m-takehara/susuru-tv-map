package me.takehara.gateway

import me.takehara.domain.OutputDestination
import me.takehara.domain.SusuruTvVideos
import me.takehara.driver.FileDriver
import me.takehara.port.SusuruTvVideoPort

class SusuruTvVideoGateway(
    private val fileDriver: FileDriver,
    private val dest: OutputDestination,
) : SusuruTvVideoPort {
    private val titleRow = "videoId,title,publishedDate,thumbnail.default,thumbnail.high,thumbnail.maxres,thumbnail.medium,thumbnail.standard"

    override suspend fun save(susuruTvVideos: SusuruTvVideos) {
        val rows = susuruTvVideos.map {
                    it.videoId.value + "," +
                    it.title.value + "," +
                    it.publishedDate.date.toString() + "," +
                    it.thumbnail.default.url.toString() + "," +
                    it.thumbnail.high.url.toString() + "," +
                    it.thumbnail.maxres.url.toString() + "," +
                    it.thumbnail.medium.url.toString() + "," +
                    it.thumbnail.standard.url.toString()
        }.let { listOf(titleRow) + it }
        fileDriver.saveAll(rows, dest.path)
    }
}
