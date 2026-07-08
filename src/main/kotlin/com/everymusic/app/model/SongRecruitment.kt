package com.everymusic.app.model

data class SongRecruitment(
    val id: Long,
    val songId: Long,
    val instrumentId: Int,
    val recruitmentNote: String,
    val status: Byte
)

data class SongRecruitmentView(
    val instrumentId: Int,
    val instrumentName: String,
    val note: String
)

data class SongRecruitmentRequest(
    val recruitments: List<SongRecruitmentEntryRequest> = emptyList()
)

data class SongRecruitmentEntryRequest(
    val instrumentId: Int,
    val note: String = ""
)
