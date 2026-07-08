package com.everymusic.app.service

import com.everymusic.app.mapper.NotificationMapper
import com.everymusic.app.model.NotificationCreate
import com.everymusic.app.model.NotificationType
import com.everymusic.app.model.NotificationView
import com.everymusic.app.model.QueryLimits
import org.apache.coyote.BadRequestException
import org.springframework.stereotype.Service

@Service
class NotificationService(
    private val notificationMapper: NotificationMapper
) {

    fun findByMemberId(memberId: Long, limit: Int = QueryLimits.DEFAULT_ACTIVITY_LIMIT): List<NotificationView> {
        return notificationMapper.findByMemberId(memberId, limit.coerceIn(1, QueryLimits.MAX_ACTIVITY_LIMIT))
    }

    fun countUnread(memberId: Long): Int {
        return notificationMapper.countUnread(memberId)
    }

    fun markAsRead(id: Long, memberId: Long) {
        val updated = notificationMapper.markAsRead(id, memberId)
        if (updated == 0) {
            throw BadRequestException("Notification not found")
        }
    }

    fun markAllAsRead(memberId: Long) {
        notificationMapper.markAllAsRead(memberId)
    }

    fun notifyIfNeeded(
        memberId: Long,
        actorMemberId: Long,
        notificationType: NotificationType,
        songId: Long?,
        songPlayId: Long?,
        message: String
    ) {
        if (memberId == actorMemberId) return

        notificationMapper.insert(
            NotificationCreate(
                memberId = memberId,
                actorMemberId = actorMemberId,
                notificationType = notificationType.value,
                songId = songId,
                songPlayId = songPlayId,
                message = message
            )
        )
    }
}
