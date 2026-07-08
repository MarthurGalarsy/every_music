package com.everymusic.app.service

import com.everymusic.app.mapper.FollowMapper
import com.everymusic.app.mapper.MemberMapper
import com.everymusic.app.model.FollowMemberView
import com.everymusic.app.model.FollowSummary
import com.everymusic.app.model.NotificationType
import com.everymusic.app.model.QueryLimits
import com.everymusic.app.model.TimelineItem
import org.apache.coyote.BadRequestException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FollowService(
    private val followMapper: FollowMapper,
    private val memberMapper: MemberMapper,
    private val notificationService: NotificationService
) {

    fun isFollowing(followerMemberId: Long, followedMemberId: Long): Boolean {
        return followMapper.countFollow(followerMemberId, followedMemberId) > 0
    }

    fun summary(followerMemberId: Long, followedMemberId: Long): FollowSummary {
        return FollowSummary(
            memberId = followedMemberId,
            following = isFollowing(followerMemberId, followedMemberId),
            followerCount = followMapper.countFollowers(followedMemberId)
        )
    }

    @Transactional
    fun toggleFollow(followerMemberId: Long, followedMemberId: Long): FollowSummary {
        if (followerMemberId == followedMemberId) {
            throw BadRequestException("自分自身はフォローできません")
        }

        val follower = memberMapper.findById(followerMemberId) ?: throw BadRequestException("Member not found")
        memberMapper.findById(followedMemberId) ?: throw BadRequestException("Member not found")

        if (isFollowing(followerMemberId, followedMemberId)) {
            followMapper.deleteFollow(followerMemberId, followedMemberId)
        } else {
            followMapper.insertFollow(followerMemberId, followedMemberId)
            notificationService.notifyIfNeeded(
                memberId = followedMemberId,
                actorMemberId = followerMemberId,
                notificationType = NotificationType.MEMBER_FOLLOWED,
                songId = null,
                songPlayId = null,
                message = "${follower.memberName}さんがあなたをフォローしました。"
            )
        }

        return summary(followerMemberId, followedMemberId)
    }

    fun countFollowing(memberId: Long): Int {
        return followMapper.countFollowing(memberId)
    }

    fun countFollowers(memberId: Long): Int {
        return followMapper.countFollowers(memberId)
    }

    fun findFollowingMembers(memberId: Long): List<FollowMemberView> {
        return followMapper.findFollowingMembers(memberId)
    }

    fun findFollowerMembers(memberId: Long): List<FollowMemberView> {
        return followMapper.findFollowerMembers(memberId)
    }

    fun findTimeline(memberId: Long, limit: Int = QueryLimits.DEFAULT_ACTIVITY_LIMIT): List<TimelineItem> {
        return followMapper.findTimeline(memberId, limit.coerceIn(1, QueryLimits.MAX_ACTIVITY_LIMIT))
    }
}
