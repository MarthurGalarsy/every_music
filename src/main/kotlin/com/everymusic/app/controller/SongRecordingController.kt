package com.everymusic.app.controller

import com.everymusic.app.service.InstrumentsService
import com.everymusic.app.service.SongDetailService
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

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
        model.addAttribute("selectedUrls", session.getAttribute("selectedUrls") ?: emptyList<String>())
        return "song/recording"
    }

    // ✅ 録音画面への選択演奏付き遷移（POST）
    @PostMapping("/song/recording/to")
    fun recordTo(
        @RequestParam("songId") songId: Long,
        @RequestParam("urls", required = false) urls: List<String>?,
        session: HttpSession
    ): String {
        session.setAttribute("selectedUrls", urls ?: emptyList<String>())
        return "redirect:/song/recording/$songId"
    }
}