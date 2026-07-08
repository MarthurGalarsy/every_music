package com.everymusic.app.controller

import com.everymusic.app.model.CommentRequest
import com.everymusic.app.service.InteractionService
import jakarta.servlet.http.HttpSession
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
        val member = currentMember(session) ?: return unauthorizedResponse()
        return handleBadRequest {
            interactionService.toggleSongLike(songId, member.id)
        }
    }

    @GetMapping("/song/{songId}/comments")
    fun getSongComments(
        @PathVariable songId: Long,
        session: HttpSession
    ): ResponseEntity<Any> {
        val member = currentMember(session) ?: return unauthorizedResponse()
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
        val member = currentMember(session) ?: return unauthorizedResponse()
        return handleBadRequest {
            interactionService.saveSongComment(songId, member.id, request.comment)
        }
    }

    @PostMapping("/song/play/{songPlayId}/like")
    fun toggleSongPlayLike(
        @PathVariable songPlayId: Long,
        session: HttpSession
    ): ResponseEntity<Any> {
        val member = currentMember(session) ?: return unauthorizedResponse()
        return handleBadRequest {
            interactionService.toggleSongPlayLike(songPlayId, member.id)
        }
    }

    @GetMapping("/song/play/{songPlayId}/comments")
    fun getSongPlayComments(
        @PathVariable songPlayId: Long,
        session: HttpSession
    ): ResponseEntity<Any> {
        val member = currentMember(session) ?: return unauthorizedResponse()
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
        val member = currentMember(session) ?: return unauthorizedResponse()
        return handleBadRequest {
            interactionService.saveSongPlayComment(songPlayId, member.id, request.comment)
        }
    }
}
