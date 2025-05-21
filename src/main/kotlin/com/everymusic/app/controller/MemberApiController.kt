package com.everymusic.app.controller

import com.everymusic.app.model.*
import com.everymusic.app.service.MemberService
import jakarta.servlet.http.HttpSession
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.MethodArgumentNotValidException

@RestController
@RequestMapping("/api/member")
class MemberApiController(
    private val memberService: MemberService
) {

    @PostMapping("/login")
    fun login(
        @RequestParam mailAddress: String,
        @RequestParam password: String,
        session: HttpSession
    ): ResponseEntity<Map<String, Any>> {
        val member = memberService.login(mailAddress, password)
        return if (member != null) {
            session.setAttribute("loginMember", member)
            ResponseEntity.ok(mapOf("message" to "ログイン成功", "redirect" to "/song/list"))
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("message" to "IDかパスワードが間違っています。"))
        }
    }

    @PostMapping("/register")
    fun register(
        @RequestBody @Valid form: RegisterForm,
        session: HttpSession
    ): ResponseEntity<Map<String, Any>> {
        if (form.password != form.passwordConfirm) {
            return ResponseEntity.badRequest().body(mapOf("message" to "パスワードが一致しません"))
        }
        val success = memberService.register(form.name, form.mailAddress, form.password)
        return if (success) {
            val member = memberService.login(form.mailAddress, form.password)
            session.setAttribute("loginMember", member)
            ResponseEntity.ok(mapOf("message" to "新規登録しました。ようこそ！", "redirect" to "/song/list"))
        } else {
            ResponseEntity.status(HttpStatus.CONFLICT).body(mapOf("message" to "既に登録されています"))
        }
    }

    @GetMapping("/mypage")
    fun getMypage(session: HttpSession): ResponseEntity<Any> {
        val loginMember = session.getAttribute("loginMember") as? Member
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("message" to "ログインが必要です"))

        val member = memberService.findById(loginMember.id)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("message" to "メンバー情報が見つかりません"))

        val form = MypageForm(member.mail_address, member.member_name)
        return ResponseEntity.ok(form)
    }

    @PostMapping("/update")
    fun update(
        @RequestBody @Valid form: MypageForm,
        session: HttpSession
    ): ResponseEntity<Map<String, Any>> {
        if (form.password != form.passwordConfirm) {
            return ResponseEntity.badRequest().body(mapOf("message" to "パスワードが一致しません"))
        }
        val member = session.getAttribute("loginMember") as? Member ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("message" to "ログインが必要です"))
        val success = memberService.update(member.id, form.name, form.password)
        return if (success) {
            ResponseEntity.ok(mapOf("message" to "メンバー情報を変更しました。", "redirect" to "/song/list"))
        } else {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mapOf("message" to "更新に失敗しました"))
        }
    }

    @PostMapping("/logout")
    fun logout(session: HttpSession): ResponseEntity<Map<String, String>> {
        session.invalidate()
        return ResponseEntity.ok(mapOf("message" to "ログアウトしました", "redirect" to "/"))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String>> {
        val errorMessage = ex.bindingResult.fieldErrors.joinToString("\n") { "${it.field}: ${it.defaultMessage}" }
        return ResponseEntity.badRequest().body(mapOf("message" to errorMessage))
    }
}
