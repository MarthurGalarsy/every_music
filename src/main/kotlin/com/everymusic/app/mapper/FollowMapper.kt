package com.everymusic.app.mapper

import com.everymusic.app.model.FollowMemberView
import com.everymusic.app.model.TimelineItem
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select

@Mapper
interface FollowMapper {

    @Select("""
        SELECT COUNT(*)
        FROM member_follow
        WHERE follower_member_id = #{followerMemberId}
          AND followed_member_id = #{followedMemberId}
    """)
    fun countFollow(
        @Param("followerMemberId") followerMemberId: Long,
        @Param("followedMemberId") followedMemberId: Long
    ): Int

    @Insert("""
        INSERT INTO member_follow (
            follower_member_id,
            followed_member_id
        ) VALUES (
            #{followerMemberId},
            #{followedMemberId}
        )
    """)
    fun insertFollow(
        @Param("followerMemberId") followerMemberId: Long,
        @Param("followedMemberId") followedMemberId: Long
    ): Int

    @Delete("""
        DELETE FROM member_follow
        WHERE follower_member_id = #{followerMemberId}
          AND followed_member_id = #{followedMemberId}
    """)
    fun deleteFollow(
        @Param("followerMemberId") followerMemberId: Long,
        @Param("followedMemberId") followedMemberId: Long
    ): Int

    @Select("SELECT COUNT(*) FROM member_follow WHERE follower_member_id = #{memberId}")
    fun countFollowing(@Param("memberId") memberId: Long): Int

    @Select("SELECT COUNT(*) FROM member_follow WHERE followed_member_id = #{memberId}")
    fun countFollowers(@Param("memberId") memberId: Long): Int

    @Select("""
        SELECT
            m.id AS memberId,
            m.member_name AS memberName
        FROM member_follow mf
        JOIN member m
        ON mf.followed_member_id = m.id
        WHERE mf.follower_member_id = #{memberId}
        ORDER BY mf.created_date DESC
    """)
    fun findFollowingMembers(@Param("memberId") memberId: Long): List<FollowMemberView>

    @Select("""
        SELECT
            m.id AS memberId,
            m.member_name AS memberName
        FROM member_follow mf
        JOIN member m
        ON mf.follower_member_id = m.id
        WHERE mf.followed_member_id = #{memberId}
        ORDER BY mf.created_date DESC
    """)
    fun findFollowerMembers(@Param("memberId") memberId: Long): List<FollowMemberView>

    @Select("""
        SELECT *
        FROM (
            SELECT
                'song' AS itemType,
                s.id AS songId,
                NULL AS songPlayId,
                s.song_title AS title,
                s.song_note AS note,
                m.member_name AS memberName,
                s.created_date AS createdDate
            FROM songs s
            JOIN member_follow mf
            ON s.creater_id = mf.followed_member_id
            JOIN member m
            ON s.creater_id = m.id
            WHERE mf.follower_member_id = #{memberId}

            UNION ALL

            SELECT
                'play' AS itemType,
                sp.song_id AS songId,
                sp.id AS songPlayId,
                sp.play_title AS title,
                sp.play_note AS note,
                m.member_name AS memberName,
                sp.created_date AS createdDate
            FROM song_play sp
            JOIN member_follow mf
            ON sp.player_id = mf.followed_member_id
            JOIN member m
            ON sp.player_id = m.id
            WHERE mf.follower_member_id = #{memberId}
        ) timeline
        ORDER BY createdDate DESC
        LIMIT #{limit}
    """)
    fun findTimeline(
        @Param("memberId") memberId: Long,
        @Param("limit") limit: Int
    ): List<TimelineItem>
}
