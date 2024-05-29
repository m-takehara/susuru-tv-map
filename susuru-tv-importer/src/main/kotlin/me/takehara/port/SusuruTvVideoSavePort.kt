package me.takehara.port

import me.takehara.domain.SusuruTvVideos
import java.nio.file.Path

interface SusuruTvVideoSavePort {
    fun saveTo(destinationDir: Path, susuruTvVideos: SusuruTvVideos)
}
