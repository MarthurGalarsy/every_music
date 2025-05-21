package com.everymusic.app.controller

import com.everymusic.app.model.SongResponse
import com.everymusic.app.service.SongService
import jakarta.servlet.http.HttpSession
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/song")
class SongApiController(private val songService: SongService) {

    @GetMapping("/list")
    fun getSongList(
        @RequestParam(required = false) title: String?,
        @RequestParam(required = false) creater: String?,
        session: HttpSession
    ): ResponseEntity<List<SongResponse>> {
        val result = songService.searchSongs(title, creater)
        return ResponseEntity.ok(result)
    }
}