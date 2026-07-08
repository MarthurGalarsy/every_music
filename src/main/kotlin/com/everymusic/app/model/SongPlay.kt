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
    val playerId: Long,
    val playerName: String,
    val audioUrl: String,
    val liked: Boolean,
    val likeCount: Int,
    val commentCount: Int,
    val followingPlayer: Boolean,
    val canFollowPlayer: Boolean
)

data class SongPlayInsert(
    val id: Long = 0,
    val songId: Long,
    val title: String,
    val note: String,
    val instrumentId: Int,
    val fileId: Long,
    val memberId: Long
)
