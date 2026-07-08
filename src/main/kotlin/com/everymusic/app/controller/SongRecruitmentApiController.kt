package com.everymusic.app.controller

import com.everymusic.app.model.SongRecruitmentRequest
import com.everymusic.app.service.SongRecruitmentService
import jakarta.servlet.http.HttpSession
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/song")
class SongRecruitmentApiController(
    private val songRecruitmentService: SongRecruitmentService
) {

    @GetMapping("/{songId}/recruitments")
    fun getRecruitments(
        @PathVariable songId: Long,
        session: HttpSession
    ): ResponseEntity<Any> {
        currentMember(session) ?: return unauthorizedResponse()
        return handleBadRequest {
            songRecruitmentService.findActiveBySongId(songId)
        }
    }

    @PostMapping("/{songId}/recruitments")
    fun saveRecruitments(
        @PathVariable songId: Long,
        @RequestBody request: SongRecruitmentRequest,
        session: HttpSession
    ): ResponseEntity<Any> {
        val member = currentMember(session) ?: return unauthorizedResponse()
        return handleBadRequest {
            songRecruitmentService.saveRecruitments(songId, member.id, request.recruitments)
        }
    }
}
