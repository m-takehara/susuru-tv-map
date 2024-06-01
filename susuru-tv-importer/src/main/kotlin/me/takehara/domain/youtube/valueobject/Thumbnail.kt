package me.takehara.domain.youtube.valueobject

import java.net.URL

data class Thumbnail(
    val url: URL,
    val height: Int,
    val width: Int,
)