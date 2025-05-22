package com.everymusic.app.controller

import com.everymusic.app.model.Member
import com.everymusic.app.model.SongPlayUploadForm
import com.everymusic.app.service.S3UploaderService
import com.everymusic.app.service.SongPlayService
import jakarta.servlet.http.HttpSession
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/song/play")
class SongPlayUploadApiController(
    private val songPlayService: SongPlayService,
    private val s3UploaderService: S3UploaderService
) {

    @PostMapping("/upload")
    fun upload(
        @ModelAttribute form: SongPlayUploadForm,
        @RequestParam("file") file: MultipartFile,
        session: HttpSession
    ): ResponseEntity<Any> {
        val loginMember =
            session.getAttribute("loginMember") as? Member ?: return ResponseEntity.status(401).body("未ログインです")
        val s3Key = s3UploaderService.upload(file)
        songPlayService.register(
            form,
            s3Key,
            loginMember
        )

        return ResponseEntity.ok(mapOf("message" to "アップロード成功", "redirect" to "/song/detail/${form.songId}"))
    }
}