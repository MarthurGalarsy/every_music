package com.everymusic.app.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class SongController {
    @GetMapping("/song/list")
    fun songListPage(
        @RequestParam(required = false) message: String?,
        model: Model
    ): String {
        model.addAttribute("message", message)
        return "song/list"
    }
}