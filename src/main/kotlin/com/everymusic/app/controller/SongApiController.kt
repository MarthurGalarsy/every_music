package com.everymusic.app.controller

import com.everymusic.app.service.SongService
import jakarta.servlet.http.HttpSession
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/song")
class SongApiController(
    private val songService: SongService
) {

    @GetMapping("/list")
    fun getSongList(
        @RequestParam(required = false) title: String?,
        @RequestParam(required = false) creater: String?,
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        session: HttpSession
    ): ResponseEntity<Map<String, Any>> {
        val (songs, totalCount) = songService.searchSongs(title, creater, page, size)
        return ResponseEntity.ok(mapOf(
            "songs" to songs,
            "totalCount" to totalCount
        ))
    }
}