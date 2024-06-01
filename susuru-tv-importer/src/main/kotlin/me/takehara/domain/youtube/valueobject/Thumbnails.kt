package me.takehara.domain.youtube.valueobject

data class Thumbnails(
    val default: Thumbnail,
    val high: Thumbnail,
    val maxres: Thumbnail,
    val medium: Thumbnail,
    val standard: Thumbnail,
)