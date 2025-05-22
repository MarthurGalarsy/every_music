package com.everymusic.app.controller

import com.everymusic.app.service.InstrumentsService
import com.everymusic.app.service.SongService
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@Controller
class SongPlayUploadController(
    private val songService: SongService,
    private val instrumentService: InstrumentsService
) {
    @GetMapping("/song/play/upload/{songId}")
    fun uploadPage(
        @PathVariable songId: Long,
        model: Model,
        session: HttpSession
    ): String {
        val song = songService.findById(songId) ?: return "redirect:/song/list"
        model.addAttribute("song", song)
        model.addAttribute("instruments", instrumentService.findAll())
        return "song/upload"
    }
}