package com.everymusic.app.model

data class SongResponse(
    val id: Long,
    val songTitle: String,
    val songNote: String,
    val bpm: Int,
    val createrName: String,
    val beatName: String
)