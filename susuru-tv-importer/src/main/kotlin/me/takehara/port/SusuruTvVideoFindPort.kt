package me.takehara.port

import me.takehara.domain.ChannelId
import me.takehara.domain.PlaylistId
import me.takehara.domain.SusuruTvVideos

interface SusuruTvVideoFindPort {
    fun findBy(channelId: ChannelId, playlistId: PlaylistId): SusuruTvVideos
}
