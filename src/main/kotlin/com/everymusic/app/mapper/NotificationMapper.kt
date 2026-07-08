package com.everymusic.app.mapper

import com.everymusic.app.model.NotificationCreate
import com.everymusic.app.model.NotificationView
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update

@Mapper
interface NotificationMapper {

    @Insert("""
        INSERT INTO notifications (
            member_id,
            actor_member_id,
            notification_type,
            song_id,
            song_play_id,
            message,
            read_status
        ) VALUES (
            #{memberId},
            #{actorMemberId},
            #{notificationType},
            #{songId},
            #{songPlayId},
            #{message},
            0
        )
    """)
    fun insert(notification: NotificationCreate): Int

    @Select("""
        SELECT
            id,
            notification_type AS notificationType,
            song_id AS songId,
            song_play_id AS songPlayId,
            message,
            read_status AS readStatus,
            created_date AS createdDate
        FROM notifications
        WHERE member_id = #{memberId}
        ORDER BY created_date DESC, id DESC
        LIMIT #{limit}
    """)
    fun findByMemberId(
        @Param("memberId") memberId: Long,
        @Param("limit") limit: Int
    ): List<NotificationView>

    @Select("SELECT COUNT(*) FROM notifications WHERE member_id = #{memberId} AND read_status = 0")
    fun countUnread(@Param("memberId") memberId: Long): Int

    @Update("""
        UPDATE notifications
        SET read_status = 1
        WHERE id = #{id}
          AND member_id = #{memberId}
    """)
    fun markAsRead(
        @Param("id") id: Long,
        @Param("memberId") memberId: Long
    ): Int

    @Update("""
        UPDATE notifications
        SET read_status = 1
        WHERE member_id = #{memberId}
          AND read_status = 0
    """)
    fun markAllAsRead(@Param("memberId") memberId: Long): Int
}
