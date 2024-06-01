package me.takehara.domain.youtube

sealed class PlaylistId(val value: String) {
    data object MainichiRamenSeikatsu : PlaylistId("PLRiGv_zZZiw9dppLgE2QfTcj0uDLYTNEk")
}
