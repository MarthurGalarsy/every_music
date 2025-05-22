package com.everymusic.app.model

data class SongPlayUploadForm(
    val songId: Long,
    val instrumentId: Int,
    val playTitle: String,
    val playNote: String
)

