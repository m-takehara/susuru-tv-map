package me.takehara.domain

import java.nio.file.Path
import java.nio.file.Paths

sealed class DestinationDir(val path: Path) {
    data object Output : DestinationDir((Paths.get(Paths.get("").toAbsolutePath().toString(), "output")))
}

sealed class DestinationFile(val name: String) {
    data object Overview : DestinationFile("overview.csv")
    data object Description : DestinationFile("description.csv")
}
