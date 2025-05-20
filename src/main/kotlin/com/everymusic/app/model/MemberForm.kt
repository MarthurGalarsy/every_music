package com.everymusic.app.model

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegisterForm(
    @field:Email(message = "メールアドレスの形式で入力してください")
    @field:NotBlank(message = "メールアドレスは必須です")
    val mailAddress: String = "",

    @field:NotBlank(message = "名前は必須です")
    val name: String = "",

    @field:Size(min = 6, message = "パスワードは6文字以上で入力してください")
    val password: String = "",

    val passwordConfirm: String = ""
)

data class MypageForm(
    val mailAddress: String = "",

    @field:NotBlank(message = "名前は必須です")
    val name: String = "",

    @field:Size(min = 6, message = "パスワードは6文字以上で入力してください")
    val password: String = "",

    val passwordConfirm: String = ""
)