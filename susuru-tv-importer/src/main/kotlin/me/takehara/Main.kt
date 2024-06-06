package me.takehara

import kotlinx.coroutines.runBlocking
import me.takehara.domain.OutputDestination
import me.takehara.driver.FileDriver
import me.takehara.driver.YoutubeApi
import me.takehara.gateway.SusuruTvVideoGateway
import me.takehara.gateway.YoutubeVideoGateway
import me.takehara.usecase.ExportSusuruTvVideosUseCase

fun main() = runBlocking {
    val findPort = YoutubeVideoGateway(YoutubeApi())
    val savePort = SusuruTvVideoGateway(FileDriver(), OutputDestination.OutputDir)
    val useCase = ExportSusuruTvVideosUseCase(findPort, savePort)
    useCase.execute()
}
