package com.everymusic.app.model

data class ChordProgression(
    val chord: String,
    val measureNum: Int
)

data class ChordProgressionInsert(
    val songId: Long,
    val structureId: Long,
    val sortOrder: Int,
    val chord: String,
    val measureNum: Int
)

data class ChordView(
    val chord: String,
    val measureNum: Int
)
