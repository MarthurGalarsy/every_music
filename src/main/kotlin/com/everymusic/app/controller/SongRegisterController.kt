package com.everymusic.app.controller

import com.everymusic.app.service.SongRegisterService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import jakarta.servlet.http.HttpSession

@Controller
class SongRegisterController(
    private val songRegisterService: SongRegisterService
) {

    @GetMapping("/song/register")
    fun showRegisterPage(model: Model, session: HttpSession): String {
        val beats = songRegisterService.getBeatList()
        val sections = songRegisterService.getSectionList()
        model.addAttribute("beats", beats)
        model.addAttribute("sections", sections)
        return "song/register"
    }
}