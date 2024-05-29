package me.takehara.gateway

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import me.takehara.domain.SusuruTvVideos
import me.takehara.port.SusuruTvVideoSavePort
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class SusuruTvVideoSaveGateway : SusuruTvVideoSavePort {
    override fun saveTo(destinationDir: Path, susuruTvVideos: SusuruTvVideos) = runBlocking(Dispatchers.IO) {
        println(susuruTvVideos.list.size)
        TODO()
        if (Files.notExists(destinationDir)) Files.createDirectories(destinationDir)

        val file = Paths.get(destinationDir.toString(), "hoge.csv")

        if (Files.notExists(file)) Files.createFile(file)
        Files.write(file, susuruTvVideos.first().title.value.toByteArray(StandardCharsets.UTF_8))
        return@runBlocking
    }
}
