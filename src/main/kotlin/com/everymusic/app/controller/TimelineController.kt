package com.everymusic.app.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class TimelineController {

    @GetMapping("/timeline")
    fun timelinePage(): String {
        return "timeline/list"
    }
}
