package me.takehara.port

import me.takehara.domain.youtube.ChannelId
import me.takehara.domain.youtube.UnvalidatedYoutubeVideos
import me.takehara.domain.youtube.PlaylistId

interface YoutubeVideoPort {
    suspend fun findBy(channelId: ChannelId, playlistId: PlaylistId): UnvalidatedYoutubeVideos
}
