package me.takehara.gateway

import com.google.api.services.youtube.model.PlaylistItem

class InvalidPlaylistItemException(
    override val message: String,
    val playlistItem: PlaylistItem
): Exception()
