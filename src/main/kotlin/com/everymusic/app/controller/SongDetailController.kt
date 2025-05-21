package com.everymusic.app.controller

import com.everymusic.app.service.SongDetailService
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@Controller
class SongDetailController(
    private val songDetailService: SongDetailService
) {

    @GetMapping("/song/detail/{id}")
    fun songDetail(
        @PathVariable id: Long,
        model: Model,
        session: HttpSession,
    ): String {
        val detail = songDetailService.loadSongDetail(id)
        model.addAttribute("song", detail.song)
        model.addAttribute("structures", detail.structures)
        model.addAttribute("instruments", detail.instrumentMap)
        return "song/detail"
    }
}