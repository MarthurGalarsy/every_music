package com.everymusic.app.config

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpSession
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean

@Component
@Order(1)
class AuthFilter : GenericFilterBean() {
    override fun doFilter(
        req: ServletRequest,
        res: ServletResponse,
        chain: FilterChain
    ) {
        val request = req as HttpServletRequest
        val response = res as HttpServletResponse
        val path = request.requestURI

        val isStatic = path.startsWith("/css") || path.startsWith("/js") || path.startsWith("/images")
        val isMemberPage = path.startsWith("/member")
        val isPublicPage = path == "/" || path.startsWith("/login") || path.startsWith("/introduction")
        val isPublicApi = path.startsWith("/api/member/login") || path.startsWith("/api/member/register")

        if (isStatic || isMemberPage || isPublicPage || isPublicApi) {
            chain.doFilter(req, res)
            return
        }

        val session: HttpSession = request.getSession(false) ?: run {
            response.sendRedirect("/")
            return
        }

        if (session.getAttribute("loginMember") == null) {
            response.sendRedirect("/")
            return
        }

        chain.doFilter(req, res)
    }
}