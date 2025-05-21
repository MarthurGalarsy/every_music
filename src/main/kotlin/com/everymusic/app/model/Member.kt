package com.everymusic.app.model

data class Member(
    val id: Long,
    val memberName: String,
    val mailAddress: String,
    val password: String,
    val status: Byte
)