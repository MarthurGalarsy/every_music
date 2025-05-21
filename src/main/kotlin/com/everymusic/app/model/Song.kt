package com.everymusic.app.model

data class SongResponse(
    val id: Long,
    val songTitle: String,
    val songNote: String,
    val bpm: Int,
    val createrName: String,
    val beatName: String
)

data class SongInsert(
    var id: Long = 0,
    val title: String,
    val note: String,
    val bpm: Int,
    val beatId: Int,
    val createrId: Long
)