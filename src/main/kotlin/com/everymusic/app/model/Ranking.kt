package com.everymusic.app.model

data class SongRankingRow(
    val id: Long,
    val songTitle: String,
    val songNote: String,
    val bpm: Int,
    val createrName: String,
    val beatName: String,
    val likeCount: Int,
    val commentCount: Int,
    val playCount: Int,
    val rankingScore: Int
)

data class SongRankingItem(
    val id: Long,
    val songTitle: String,
    val songNote: String,
    val bpm: Int,
    val createrName: String,
    val beatName: String,
    val likeCount: Int,
    val commentCount: Int,
    val playCount: Int,
    val rankingScore: Int,
    val tags: List<SongTagView>
)
