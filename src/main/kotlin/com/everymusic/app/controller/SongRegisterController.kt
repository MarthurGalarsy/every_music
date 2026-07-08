package com.everymusic.app.controller

import com.everymusic.app.model.ValidationLimits
import com.everymusic.app.service.SongRegisterService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class SongRegisterController(
    private val songRegisterService: SongRegisterService
) {

    @GetMapping("/song/register")
    fun showRegisterPage(model: Model): String {
        val beats = songRegisterService.getBeatList()
        val sections = songRegisterService.getSectionList()
        model.addAttribute("beats", beats)
        model.addAttribute("sections", sections)
        model.addAttribute("copySong", null)
        addValidationLimits(model)
        return "song/register"
    }

    @GetMapping("/song/register/copy/{songId}")
    fun registerPage(
        @PathVariable(required = true) songId: Long,
        model: Model
    ): String {
        val beats = songRegisterService.getBeatList()
        val sections = songRegisterService.getSectionList()
        model.addAttribute("beats", beats)
        model.addAttribute("sections", sections)
        addValidationLimits(model)

        val copiedData = songRegisterService.getSongCopyData(songId)
        model.addAttribute("copySong", copiedData)

        return "song/register"
    }

    private fun addValidationLimits(model: Model) {
        model.addAttribute("maxTagCount", ValidationLimits.MAX_TAG_COUNT)
        model.addAttribute("maxTagLength", ValidationLimits.MAX_TAG_LENGTH)
    }
}
