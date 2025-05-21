package com.everymusic.app.controller

import com.everymusic.app.model.Member
import com.everymusic.app.service.SongRegisterService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import jakarta.servlet.http.HttpSession
import org.springframework.web.bind.annotation.PathVariable

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

    @GetMapping("/song/register/copy/{songId}")
    fun registerPage(
        @PathVariable(required = true) songId: Long,
        model: Model, session: HttpSession
    ): String {
        val beats = songRegisterService.getBeatList()
        val sections = songRegisterService.getSectionList()
        model.addAttribute("beats", beats)
        model.addAttribute("sections", sections)

        val copiedData = songRegisterService.getSongCopyData(songId)
        model.addAttribute("copySong", copiedData)

        return "song/register"
    }
}