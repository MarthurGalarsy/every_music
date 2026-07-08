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

data class MypageDashboard(
    val profile: MypageForm,
    val createdSongs: List<MypageSongView>,
    val playedSongs: List<MypagePlayView>,
    val followingCount: Int,
    val followerCount: Int,
    val followingMembers: List<FollowMemberView>,
    val followerMembers: List<FollowMemberView>
)

data class MypageSongView(
    val id: Long,
    val title: String,
    val note: String,
    val bpm: Int,
    val beatName: String,
    val playCount: Int,
    val likeCount: Int,
    val commentCount: Int
)

data class MypagePlayView(
    val id: Long,
    val songId: Long,
    val songTitle: String,
    val playTitle: String,
    val playNote: String,
    val instrumentName: String,
    val likeCount: Int,
    val commentCount: Int
)
