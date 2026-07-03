package com.everymusic.app.model

import java.time.LocalDateTime

data class InteractionSummary(
    val liked: Boolean,
    val likeCount: Int,
    val commentCount: Int
)

data class CommentRequest(
    val comment: String
)

data class CommentView(
    val memberName: String,
    val comment: String,
    val createdDate: LocalDateTime,
    val isMine: Boolean
)
