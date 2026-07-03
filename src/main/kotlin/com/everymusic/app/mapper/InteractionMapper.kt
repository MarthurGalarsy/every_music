package com.everymusic.app.mapper

import com.everymusic.app.model.CommentView
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select

@Mapper
interface InteractionMapper {

    @Select("SELECT COUNT(*) FROM song_like WHERE song_id = #{songId}")
    fun countSongLikes(@Param("songId") songId: Long): Int

    @Select("SELECT COUNT(*) FROM song_like WHERE song_id = #{songId} AND member_id = #{memberId}")
    fun countSongLikeByMember(
        @Param("songId") songId: Long,
        @Param("memberId") memberId: Long
    ): Int

    @Insert("INSERT INTO song_like (song_id, member_id) VALUES (#{songId}, #{memberId})")
    fun insertSongLike(
        @Param("songId") songId: Long,
        @Param("memberId") memberId: Long
    ): Int

    @Delete("DELETE FROM song_like WHERE song_id = #{songId} AND member_id = #{memberId}")
    fun deleteSongLike(
        @Param("songId") songId: Long,
        @Param("memberId") memberId: Long
    ): Int

    @Select("SELECT COUNT(*) FROM song_comment WHERE song_id = #{songId}")
    fun countSongComments(@Param("songId") songId: Long): Int

    @Insert("""
        INSERT INTO song_comment (song_id, member_id, comment_text)
        VALUES (#{songId}, #{memberId}, #{comment})
        ON DUPLICATE KEY UPDATE
            comment_text = #{comment},
            updated_date = CURRENT_TIMESTAMP(3)
    """)
    fun upsertSongComment(
        @Param("songId") songId: Long,
        @Param("memberId") memberId: Long,
        @Param("comment") comment: String
    ): Int

    @Select("""
        SELECT
            m.member_name AS memberName,
            c.comment_text AS comment,
            c.created_date AS createdDate,
            CASE WHEN c.member_id = #{memberId} THEN TRUE ELSE FALSE END AS isMine
        FROM song_comment c
        JOIN member m ON c.member_id = m.id
        WHERE c.song_id = #{songId}
        ORDER BY c.updated_date DESC, c.id DESC
    """)
    fun findSongComments(
        @Param("songId") songId: Long,
        @Param("memberId") memberId: Long
    ): List<CommentView>

    @Select("SELECT COUNT(*) FROM song_play_like WHERE song_play_id = #{songPlayId}")
    fun countSongPlayLikes(@Param("songPlayId") songPlayId: Long): Int

    @Select("SELECT COUNT(*) FROM song_play_like WHERE song_play_id = #{songPlayId} AND member_id = #{memberId}")
    fun countSongPlayLikeByMember(
        @Param("songPlayId") songPlayId: Long,
        @Param("memberId") memberId: Long
    ): Int

    @Insert("INSERT INTO song_play_like (song_play_id, member_id) VALUES (#{songPlayId}, #{memberId})")
    fun insertSongPlayLike(
        @Param("songPlayId") songPlayId: Long,
        @Param("memberId") memberId: Long
    ): Int

    @Delete("DELETE FROM song_play_like WHERE song_play_id = #{songPlayId} AND member_id = #{memberId}")
    fun deleteSongPlayLike(
        @Param("songPlayId") songPlayId: Long,
        @Param("memberId") memberId: Long
    ): Int

    @Select("SELECT COUNT(*) FROM song_play_comment WHERE song_play_id = #{songPlayId}")
    fun countSongPlayComments(@Param("songPlayId") songPlayId: Long): Int

    @Insert("""
        INSERT INTO song_play_comment (song_play_id, member_id, comment_text)
        VALUES (#{songPlayId}, #{memberId}, #{comment})
        ON DUPLICATE KEY UPDATE
            comment_text = #{comment},
            updated_date = CURRENT_TIMESTAMP(3)
    """)
    fun upsertSongPlayComment(
        @Param("songPlayId") songPlayId: Long,
        @Param("memberId") memberId: Long,
        @Param("comment") comment: String
    ): Int

    @Select("""
        SELECT
            m.member_name AS memberName,
            c.comment_text AS comment,
            c.created_date AS createdDate,
            CASE WHEN c.member_id = #{memberId} THEN TRUE ELSE FALSE END AS isMine
        FROM song_play_comment c
        JOIN member m ON c.member_id = m.id
        WHERE c.song_play_id = #{songPlayId}
        ORDER BY c.updated_date DESC, c.id DESC
    """)
    fun findSongPlayComments(
        @Param("songPlayId") songPlayId: Long,
        @Param("memberId") memberId: Long
    ): List<CommentView>
}
