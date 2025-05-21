package com.everymusic.app.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class SongController {

    @GetMapping("/song/list")
    fun songList(): String {
        return "song/list"
    }
}