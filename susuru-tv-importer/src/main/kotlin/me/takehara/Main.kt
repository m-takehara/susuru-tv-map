package me.takehara

import me.takehara.driver.YoutubeApi
import me.takehara.gateway.SusuruTvVideoFindGateway
import me.takehara.gateway.SusuruTvVideoSaveGateway
import me.takehara.usecase.FindVideosUseCase

fun main() {
    val findPort = SusuruTvVideoFindGateway(YoutubeApi())
    val savePort = SusuruTvVideoSaveGateway()
    val useCase = FindVideosUseCase(findPort, savePort)
    useCase.execute()
}
