package com.everymusic.app.model

data class Tag(
    val id: Long,
    val tagName: String
)

data class SongTagView(
    val id: Long,
    val tagName: String
)

data class SongTagRequest(
    val tags: List<String> = emptyList()
)

data class SongListItem(
    val id: Long,
    val songTitle: String,
    val songNote: String,
    val bpm: Int,
    val createrName: String,
    val beatName: String,
    val likeCount: Int,
    val commentCount: Int,
    val tags: List<SongTagView>
)
