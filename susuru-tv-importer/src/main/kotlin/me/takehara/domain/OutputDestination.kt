package me.takehara.domain

import java.nio.file.Path
import java.nio.file.Paths

sealed class OutputDestination(val path: Path) {
    data object OutputDir : OutputDestination((Paths.get(Paths.get("").toAbsolutePath().toString(), "output")))
}
