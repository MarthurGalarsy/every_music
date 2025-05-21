package com.everymusic.app.model

data class SongPlay(
    val id: Long,
    val songId: Long,
    val playTitle: String,
    val playNote: String,
    val instrumentId: Int,
    val songPlayFileId: Long,
    val playerId: Long
)

data class SongPlayView(
    val id: Long,
    val title: String,
    val note: String,
    val playerName: String,
    val audioUrl: String
)