package com.everymusic.app.model

data class SongStructureInsert(
    var id: Long = 0,
    val songId: Long,
    val sectionId: Int,
    val sortOrder: Int
)