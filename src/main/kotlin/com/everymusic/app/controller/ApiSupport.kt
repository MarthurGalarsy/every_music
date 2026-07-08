package com.everymusic.app.controller

import com.everymusic.app.config.LOGIN_MEMBER_SESSION_KEY
import com.everymusic.app.config.LOGIN_REQUIRED_MESSAGE
import com.everymusic.app.model.Member
import jakarta.servlet.http.HttpSession
import org.apache.coyote.BadRequestException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

fun currentMember(session: HttpSession): Member? {
    return session.getAttribute(LOGIN_MEMBER_SESSION_KEY) as? Member
}

fun unauthorizedResponse(): ResponseEntity<Any> {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("message" to LOGIN_REQUIRED_MESSAGE))
}

fun handleBadRequest(action: () -> Any): ResponseEntity<Any> {
    return try {
        ResponseEntity.ok(action())
    } catch (e: BadRequestException) {
        ResponseEntity.badRequest().body(mapOf("message" to (e.message ?: "リクエストが不正です")))
    }
}
