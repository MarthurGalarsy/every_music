package com.everymusic.app.controller

import com.everymusic.app.model.QueryLimits
import com.everymusic.app.service.FollowService
import jakarta.servlet.http.HttpSession
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/timeline")
class TimelineApiController(
    private val followService: FollowService
) {

    @GetMapping
    fun timeline(
        @RequestParam(required = false) limit: Int?,
        session: HttpSession
    ): ResponseEntity<Any> {
        val member = currentMember(session) ?: return unauthorizedResponse()

        return ResponseEntity.ok(followService.findTimeline(member.id, limit ?: QueryLimits.DEFAULT_ACTIVITY_LIMIT))
    }
}
