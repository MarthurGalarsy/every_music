package com.everymusic.app.model

data class SongPlayFile(
    val id: Long,
    val s3Key: String
)

data class SongPlayFileInsertRequest(
    val type: String,
    val s3Key: String
)