package com.everymusic.app.service

import com.everymusic.app.mapper.MemberMapper
import com.everymusic.app.model.Member
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberMapper: MemberMapper
) {

    fun login(
        mailAddress: String,
        password: String
    ): Member? {
        val member = memberMapper.findByMail(mailAddress) ?: return null
        return if (member.status == 1.toByte()
            && BCrypt.checkpw(password, member.password))
            member
        else null
    }

    fun register(name: String, mailAddress: String, password: String): Boolean {
        if (memberMapper.findByMail(mailAddress) != null) return false
        val hashed = BCrypt.hashpw(password, BCrypt.gensalt())
        return memberMapper.insert(
            name,
            mailAddress,
            hashed,
            true,
            ) > 0
    }

    fun update(id: Long, name: String, password: String): Boolean {
        val hashed = BCrypt.hashpw(password, BCrypt.gensalt())
        return memberMapper.update(
            id,
            name,
            hashed
        ) > 0
    }
}