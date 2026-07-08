package com.everymusic.app.controller

import com.everymusic.app.model.SongTagRequest
import com.everymusic.app.service.TagService
import jakarta.servlet.http.HttpSession
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/song")
class TagApiController(
    private val tagService: TagService
) {

    @PostMapping("/{songId}/tags")
    fun saveTags(
        @PathVariable songId: Long,
        @RequestBody request: SongTagRequest,
        session: HttpSession
    ): ResponseEntity<Any> {
        val member = currentMember(session) ?: return unauthorizedResponse()
        return handleBadRequest {
            tagService.saveTagsForCreator(songId, member.id, request.tags)
        }
    }
}
