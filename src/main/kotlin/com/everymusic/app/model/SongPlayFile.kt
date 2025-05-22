package com.everymusic.app.model

data class SongPlayFile(
    val id: Long,
    val s3Key: String
)

data class SongPlayFileInsertInsert(
    val id: Long = 0,
    val type: String,
    val s3Key: String
)