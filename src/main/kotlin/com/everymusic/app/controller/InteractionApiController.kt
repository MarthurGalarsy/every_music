package com.everymusic.app.controller

import com.everymusic.app.model.CommentRequest
import com.everymusic.app.model.Member
import com.everymusic.app.service.InteractionService
import jakarta.servlet.http.HttpSession
import org.apache.coyote.BadRequestException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class InteractionApiController(
    private val interactionService: InteractionService
) {

    @PostMapping("/song/{songId}/like")
    fun toggleSongLike(
        @PathVariable songId: Long,
        session: HttpSession
    ): ResponseEntity<Any> {
        val member = currentMember(session) ?: return unauthorized()
        return handleBadRequest {
            interactionService.toggleSongLike(songId, member.id)
        }
    }

    @GetMapping("/song/{songId}/comments")
    fun getSongComments(
        @PathVariable songId: Long,
        session: HttpSession
    ): ResponseEntity<Any> {
        val member = currentMember(session) ?: return unauthorized()
        return handleBadRequest {
            interactionService.findSongComments(songId, member.id)
        }
    }

    @PostMapping("/song/{songId}/comments")
    fun saveSongComment(
        @PathVariable songId: Long,
        @RequestBody request: CommentRequest,
        session: HttpSession
    ): ResponseEntity<Any> {
        val member = currentMember(session) ?: return unauthorized()
        return handleBadRequest {
            interactionService.saveSongComment(songId, member.id, request.comment)
        }
    }

    @PostMapping("/song/play/{songPlayId}/like")
    fun toggleSongPlayLike(
        @PathVariable songPlayId: Long,
        session: HttpSession
    ): ResponseEntity<Any> {
        val member = currentMember(session) ?: return unauthorized()
        return handleBadRequest {
            interactionService.toggleSongPlayLike(songPlayId, member.id)
        }
    }

    @GetMapping("/song/play/{songPlayId}/comments")
    fun getSongPlayComments(
        @PathVariable songPlayId: Long,
        session: HttpSession
    ): ResponseEntity<Any> {
        val member = currentMember(session) ?: return unauthorized()
        return handleBadRequest {
            interactionService.findSongPlayComments(songPlayId, member.id)
        }
    }

    @PostMapping("/song/play/{songPlayId}/comments")
    fun saveSongPlayComment(
        @PathVariable songPlayId: Long,
        @RequestBody request: CommentRequest,
        session: HttpSession
    ): ResponseEntity<Any> {
        val member = currentMember(session) ?: return unauthorized()
        return handleBadRequest {
            interactionService.saveSongPlayComment(songPlayId, member.id, request.comment)
        }
    }

    private fun currentMember(session: HttpSession): Member? {
        return session.getAttribute("loginMember") as? Member
    }

    private fun unauthorized(): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("message" to "ログインが必要です"))
    }

    private fun handleBadRequest(action: () -> Any): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(action())
        } catch (e: BadRequestException) {
            ResponseEntity.badRequest().body(mapOf("message" to (e.message ?: "リクエストが不正です")))
        }
    }
}
