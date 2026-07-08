package com.everymusic.app.controller

import com.everymusic.app.model.QueryLimits
import com.everymusic.app.service.RankingService
import jakarta.servlet.http.HttpSession
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/ranking")
class RankingApiController(
    private val rankingService: RankingService
) {

    @GetMapping("/songs")
    fun songRanking(
        @RequestParam(required = false) type: String?,
        @RequestParam(required = false) limit: Int?,
        session: HttpSession
    ): ResponseEntity<Any> {
        currentMember(session) ?: return unauthorizedResponse()

        return ResponseEntity.ok(rankingService.findSongRanking(type, limit ?: QueryLimits.DEFAULT_RANKING_LIMIT))
    }
}
