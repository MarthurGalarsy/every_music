package com.everymusic.app.controller

import com.everymusic.app.model.KeyRequest
import com.everymusic.app.service.S3UploaderService
import com.everymusic.app.service.SongService
import jakarta.servlet.http.HttpSession
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/song")
class SongApiController(
    private val songService: SongService,
    private val s3UploaderService: S3UploaderService
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

    @PostMapping("/url")
    fun getSignedUrl(
        @RequestBody req: KeyRequest
    ): ResponseEntity<String> {
        val url = s3UploaderService.getPublicUrl(req.key)
        return ResponseEntity.ok(url)
    }
}