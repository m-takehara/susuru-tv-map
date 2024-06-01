package me.takehara.domain.youtube

sealed class ChannelId(val value: String) {
    data object SusuruTv : ChannelId("UCXcjvt8cOfwtcqaMeE7-hqA")
}
