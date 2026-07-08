package com.everymusic.app.controller

import com.everymusic.app.config.LOGIN_REQUIRED_MESSAGE
import com.everymusic.app.model.SongRegisterRequest
import com.everymusic.app.service.SongRegisterService
import jakarta.servlet.http.HttpSession
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/song")
class SongRegisterApiController(
    private val songRegisterService: SongRegisterService
) {

    @PostMapping("/register")
    fun registerSong(
        @RequestBody @Valid request: SongRegisterRequest,
        session: HttpSession
    ): ResponseEntity<Map<String, String>> {
        val member = currentMember(session)
            ?: return ResponseEntity.status(401).body(mapOf("message" to LOGIN_REQUIRED_MESSAGE))

        songRegisterService.registerSong(request, member)
        return ResponseEntity.ok(mapOf("message" to "投稿完了", "redirect" to "/song/list"))
    }
}
