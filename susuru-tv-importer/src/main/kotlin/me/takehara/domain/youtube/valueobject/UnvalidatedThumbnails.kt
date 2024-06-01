package me.takehara.domain.youtube.valueobject

data class UnvalidatedThumbnails(
    val default: Thumbnail?,
    val high: Thumbnail?,
    val maxres: Thumbnail?,
    val medium: Thumbnail?,
    val standard: Thumbnail?,
)