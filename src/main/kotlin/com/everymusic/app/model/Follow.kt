package com.everymusic.app.model

import java.time.LocalDateTime

data class FollowSummary(
    val memberId: Long,
    val following: Boolean,
    val followerCount: Int
)

data class FollowMemberView(
    val memberId: Long,
    val memberName: String
)

data class TimelineItem(
    val itemType: String,
    val songId: Long,
    val songPlayId: Long?,
    val title: String,
    val note: String,
    val memberName: String,
    val createdDate: LocalDateTime
)
