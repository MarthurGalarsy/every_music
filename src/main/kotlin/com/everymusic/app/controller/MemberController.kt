package com.everymusic.app.controller

import com.everymusic.app.model.*
import com.everymusic.app.service.MemberService
import jakarta.servlet.http.HttpSession
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@Controller
class MemberController(
    private val memberService: MemberService
) {

    @GetMapping("/member/login")
    fun showLogin(): String = "member/login"

    @PostMapping("/member/login")
    fun login(
        @RequestParam mailAddress: String,
        @RequestParam password: String,
        model: Model,
        session: HttpSession,
    ): String {
        val member = memberService.login(mailAddress, password)
        return if (member != null) {
            session.setAttribute("loginMember", member)
            model.addAttribute("message", "ようこそ！")
            "redirect:/song/list"
        } else {
            model.addAttribute("error", "IDかパスワードが間違っています。")
            "member/login"
        }
    }

    @GetMapping("/member/register")
    fun showRegister(model: Model): String {
        model.addAttribute("registerForm", RegisterForm())
        return "member/register"
    }

    @PostMapping("/member/register")
    fun register(
        @ModelAttribute("registerForm") @Valid form: RegisterForm,
        result: BindingResult,
        model: Model,
        session: HttpSession
    ): String {
        if (result.hasErrors()) return "member/register"

        if (form.password != form.passwordConfirm) {
            model.addAttribute("error", "パスワードが一致しません")
            return "member/register"
        }

        val success = memberService.register(
            form.name,
            form.mailAddress,
            form.password
        )

        return if (success) {
            val member = memberService.login(
                form.mailAddress,
                form.password
            )
            session.setAttribute("loginMember", member)
            model.addAttribute("message", "新規登録しました。ようこそ！")
            "redirect:/song/list"
        } else {
            model.addAttribute("error", "既に登録されています")
            "member/register"
        }
    }

    @GetMapping("/member/mypage")
    fun showMypage(
        session: HttpSession,
        model: Model
    ): String {
        val member = session.getAttribute("loginMember") as? Member ?: return "redirect:/member/login"
        model.addAttribute(
            "mypageForm",
            MypageForm(
                member.mail_address,
                member.member_name
            )
        )
        return "member/mypage"
    }

    @PostMapping("/member/update")
    fun update(
        @ModelAttribute("mypageForm") @Valid form: MypageForm,
        result: BindingResult,
        session: HttpSession,
        model: Model
    ): String {
        if (result.hasErrors()) return "member/mypage"
        if (form.password != form.passwordConfirm) {
            model.addAttribute("error", "パスワードが一致しません")
            return "member/mypage"
        }
        val member = session.getAttribute("loginMember") as? Member ?: return "redirect:/member/login"
        val success = memberService.update(
            member.id,
            form.name,
            form.password
        )
        return if (success) {
            model.addAttribute("message", "メンバー情報を変更しました。")
            "redirect:/song/list"
        } else {
            model.addAttribute("error", "更新に失敗しました")
            "member/mypage"
        }
    }

    @GetMapping("/member/logout")
    fun logout(session: HttpSession): String {
        session.invalidate()
        return "redirect:/member/login"
    }
}