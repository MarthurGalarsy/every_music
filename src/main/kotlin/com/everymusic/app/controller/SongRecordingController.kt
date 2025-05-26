package com.everymusic.app.controller

import com.everymusic.app.service.InstrumentsService
import com.everymusic.app.service.SongDetailService
import com.everymusic.app.service.SongService
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class SongRecordingController(
    private val songDetailService: SongDetailService,
    private val instrumentService: InstrumentsService
) {
    @GetMapping("/song/recording/{songId}")
    fun uploadPage(
        @PathVariable songId: Long,
        model: Model,
        session: HttpSession
    ): String {
        val detail = songDetailService.loadSongDetail(songId)
        model.addAttribute("song", detail.song)
        model.addAttribute("structures", detail.structures)
        model.addAttribute("instruments", instrumentService.findAll())
        return "song/recording"
    }
}