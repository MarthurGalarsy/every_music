package com.everymusic.app.model

data class Member(
    val id: Long,
    val member_name: String,
    val mail_address: String,
    val password: String,
    val status: Byte
)