package com.everymusic.app.model

import java.time.LocalDateTime

data class Notification(
    val id: Long,
    val memberId: Long,
    val actorMemberId: Long,
    val notificationType: String,
    val songId: Long?,
    val songPlayId: Long?,
    val message: String,
    val readStatus: Byte,
    val createdDate: LocalDateTime
)

data class NotificationView(
    val id: Long,
    val notificationType: String,
    val songId: Long?,
    val songPlayId: Long?,
    val message: String,
    val readStatus: Byte,
    val createdDate: LocalDateTime
)

data class NotificationCreate(
    val memberId: Long,
    val actorMemberId: Long,
    val notificationType: String,
    val songId: Long?,
    val songPlayId: Long?,
    val message: String
)
