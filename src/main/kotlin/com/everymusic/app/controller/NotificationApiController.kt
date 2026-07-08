package com.everymusic.app.controller

import com.everymusic.app.model.QueryLimits
import com.everymusic.app.service.NotificationService
import jakarta.servlet.http.HttpSession
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/notifications")
class NotificationApiController(
    private val notificationService: NotificationService
) {

    @GetMapping
    fun list(
        @RequestParam(required = false) limit: Int?,
        session: HttpSession
    ): ResponseEntity<Any> {
        val member = currentMember(session) ?: return unauthorizedResponse()
        return ResponseEntity.ok(notificationService.findByMemberId(member.id, limit ?: QueryLimits.DEFAULT_ACTIVITY_LIMIT))
    }

    @GetMapping("/unread-count")
    fun unreadCount(session: HttpSession): ResponseEntity<Any> {
        val member = currentMember(session) ?: return unauthorizedResponse()
        return ResponseEntity.ok(mapOf("count" to notificationService.countUnread(member.id)))
    }

    @PostMapping("/{id}/read")
    fun markAsRead(
        @PathVariable id: Long,
        session: HttpSession
    ): ResponseEntity<Any> {
        val member = currentMember(session) ?: return unauthorizedResponse()
        return handleBadRequest {
            notificationService.markAsRead(id, member.id)
            mapOf("message" to "既読にしました")
        }
    }

    @PostMapping("/read-all")
    fun markAllAsRead(session: HttpSession): ResponseEntity<Any> {
        val member = currentMember(session) ?: return unauthorizedResponse()
        notificationService.markAllAsRead(member.id)
        return ResponseEntity.ok(mapOf("message" to "すべて既読にしました"))
    }
}
