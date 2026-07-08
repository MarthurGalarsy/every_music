package com.everymusic.app.service

import com.everymusic.app.mapper.MemberMapper
import com.everymusic.app.mapper.SongMapper
import com.everymusic.app.mapper.SongPlayMapper
import com.everymusic.app.model.Member
import com.everymusic.app.model.MypageDashboard
import com.everymusic.app.model.MypageForm
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberMapper: MemberMapper,
    private val songMapper: SongMapper,
    private val songPlayMapper: SongPlayMapper,
    private val followService: FollowService
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

    fun findById(id: Long): Member? {
        return memberMapper.findById(id)
    }

    fun loadMypage(memberId: Long): MypageDashboard? {
        val member = memberMapper.findById(memberId) ?: return null
        return MypageDashboard(
            profile = MypageForm(member.mailAddress, member.memberName),
            createdSongs = songMapper.findSongsByCreaterId(memberId),
            playedSongs = songPlayMapper.findPlaysByPlayerId(memberId),
            followingCount = followService.countFollowing(memberId),
            followerCount = followService.countFollowers(memberId),
            followingMembers = followService.findFollowingMembers(memberId),
            followerMembers = followService.findFollowerMembers(memberId)
        )
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
