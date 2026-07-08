package com.everymusic.app.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class RankingController {

    @GetMapping("/ranking")
    fun rankingPage(): String {
        return "ranking/list"
    }
}
