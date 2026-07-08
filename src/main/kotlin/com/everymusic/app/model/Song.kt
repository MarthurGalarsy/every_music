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
    val beatName: String,
    val likeCount: Int,
    val commentCount: Int
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
    val instrumentMap: Map<Instrument, List<SongPlayView>>,
    val allInstruments: List<Instrument>,
    val recruitments: List<SongRecruitmentView>,
    val canEditRecruitments: Boolean,
    val tags: List<SongTagView>,
    val canEditTags: Boolean
)

data class SongMeta(
    val id: Long,
    val title: String,
    val note: String,
    val bpm: Int,
    val beat: String,
    val createrId: Long,
    val creater: String,
    val liked: Boolean,
    val likeCount: Int,
    val commentCount: Int,
    val followingCreator: Boolean,
    val canFollowCreator: Boolean
)
