package com.everymusic.app.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class NotificationController {

    @GetMapping("/notifications")
    fun notificationPage(): String {
        return "notification/list"
    }
}
