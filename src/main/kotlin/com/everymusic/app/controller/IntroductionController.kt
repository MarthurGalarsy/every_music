package com.everymusic.app.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class IntroductionController {
    @GetMapping("/introduction")
    fun introductionPage(): String {
        return "introduction/explanation"
    }
}