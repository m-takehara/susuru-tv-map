package me.takehara.domain

import java.nio.file.Path
import java.nio.file.Paths

enum class OutputDestinationDirectories(val path: Path) {
    OUTPUT_DIR(Paths.get(Paths.get("").toAbsolutePath().toString(), "output"))
}
