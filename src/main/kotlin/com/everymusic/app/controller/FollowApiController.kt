package com.everymusic.app.controller

import com.everymusic.app.service.FollowService
import jakarta.servlet.http.HttpSession
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/members")
class FollowApiController(
    private val followService: FollowService
) {

    @PostMapping("/{memberId}/follow")
    fun toggleFollow(
        @PathVariable memberId: Long,
        session: HttpSession
    ): ResponseEntity<Any> {
        val member = currentMember(session) ?: return unauthorizedResponse()
        return handleBadRequest {
            followService.toggleFollow(member.id, memberId)
        }
    }
}
