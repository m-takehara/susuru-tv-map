package me.takehara.driver

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

class FileDriver {
    suspend fun saveAll(rows: List<String>, destination: Path) {
        withContext(Dispatchers.IO) {
            if (Files.notExists(destination)) Files.createDirectories(destination)
            val file = Paths.get(destination.toString(), "output.csv")
            if (Files.notExists(file)) Files.createFile(file)
            Files.write(
                file,
                rows.joinToString("\n").toByteArray(StandardCharsets.UTF_8),
                StandardOpenOption.TRUNCATE_EXISTING
            )
        }
    }
}
