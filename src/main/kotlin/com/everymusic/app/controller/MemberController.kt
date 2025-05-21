package com.everymusic.app.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MemberController {
    @GetMapping("/")
    fun loginPage(): String {
        return "member/login"
    }

    @GetMapping("/member/register")
    fun registerPage(): String {
        return "member/register"
    }

    @GetMapping("/member/mypage")
    fun mypagePage(): String {
        return "member/mypage"
    }
}