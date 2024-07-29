package me.takehara.gateway

import me.takehara.domain.DestinationDir
import me.takehara.domain.DestinationFile
import me.takehara.domain.SusuruTvVideos
import me.takehara.driver.FileDriver
import me.takehara.port.SusuruTvVideoPort

class SusuruTvVideoGateway(
    private val fileDriver: FileDriver,
    private val dest: DestinationDir,
    private val overviewFileName: DestinationFile,
    private val descriptionFileName: DestinationFile,
) : SusuruTvVideoPort {
    private val titleRow = "videoId,title,publishedDate,thumbnail.default,thumbnail.high,thumbnail.maxres,thumbnail.medium,thumbnail.standard"

    override suspend fun save(susuruTvVideos: SusuruTvVideos) {
        TODO()
    }

    internal suspend fun saveOverviews(videos: SusuruTvVideos) {
        val rows = videos.map {
            it.videoId.value + "," +
                    it.title.value + "," +
                    it.publishedDate.date.toString() + "," +
                    it.thumbnail.default.url.toString() + "," +
                    it.thumbnail.high.url.toString() + "," +
                    it.thumbnail.maxres.url.toString() + "," +
                    it.thumbnail.medium.url.toString() + "," +
                    it.thumbnail.standard.url.toString()
        }.let { listOf(titleRow) + it }
        fileDriver.saveAll(rows, dest.path, overviewFileName.name)
    }

    internal suspend fun saveDescriptions(videos: SusuruTvVideos) {
        val rows = videos.map {
            it.videoId.value + "," + it.description.value
        }.let { listOf("videoId,description") + it }
        fileDriver.saveAll(rows, dest.path, descriptionFileName.name)
    }
}
