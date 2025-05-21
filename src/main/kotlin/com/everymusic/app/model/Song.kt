package com.everymusic.app.model

data class Song(
    val id: Long,
    val songTitle: String,
    val songNote: String,
    val bpm: Int,
    val beatId: Int,
    val createrId: Long
)

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

data class SongDetailView(
    val song: SongMeta,
    val structures: List<SongStructureView>,
    val instrumentMap: Map<Instrument, List<SongPlayView>>
)

data class SongMeta(
    val id: Long,
    val title: String,
    val note: String,
    val bpm: Int,
    val beat: String,
    val creater: String
)