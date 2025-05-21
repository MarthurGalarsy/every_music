package com.everymusic.app.model

data class ChordProgressionInsert(
    val songId: Long,
    val structureId: Long,
    val sortOrder: Int,
    val chord: String,
    val measureNum: Int
)
