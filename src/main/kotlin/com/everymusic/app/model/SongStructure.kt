package com.everymusic.app.model

data class SongStructureInsert(
    var id: Long = 0,
    val songId: Long,
    val sectionId: Int,
    val sortOrder: Int
)

data class SongStructure(
    val id: Long,
    val sectionName: String
)

data class SongStructureView(
    val sectionName: String,
    val chords: List<ChordView>
)