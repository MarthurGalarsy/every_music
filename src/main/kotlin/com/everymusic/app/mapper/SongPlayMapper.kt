package com.everymusic.app.mapper

import com.everymusic.app.model.SongPlay
import com.everymusic.app.model.SongPlayInsert
import com.everymusic.app.model.MypagePlayView
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Options
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select

@Mapper
interface SongPlayMapper {
    @Select("""
        SELECT
            id,
            song_id,
            play_title,
            play_note,
            instrument_id,
            song_play_file_id,
            player_id
        FROM song_play
        WHERE song_id = #{songId}
    """)
    fun findBySongId(@Param("songId") songId: Long): List<SongPlay>

    @Select("""
        SELECT
            id,
            song_id,
            play_title,
            play_note,
            instrument_id,
            song_play_file_id,
            player_id
        FROM song_play
        WHERE id = #{id}
    """)
    fun findById(@Param("id") id: Long): SongPlay?

    @Select("""
        SELECT
            sp.id,
            sp.song_id AS songId,
            s.song_title AS songTitle,
            sp.play_title AS playTitle,
            sp.play_note AS playNote,
            i.name AS instrumentName,
            (SELECT COUNT(*) FROM song_play_like spl WHERE spl.song_play_id = sp.id) AS likeCount,
            (SELECT COUNT(*) FROM song_play_comment spc WHERE spc.song_play_id = sp.id) AS commentCount
        FROM song_play sp
        JOIN songs s
        ON sp.song_id = s.id
        JOIN instruments i
        ON sp.instrument_id = i.id
        WHERE sp.player_id = #{memberId}
        ORDER BY sp.id DESC
    """)
    fun findPlaysByPlayerId(@Param("memberId") memberId: Long): List<MypagePlayView>

    @Insert("""
        INSERT INTO song_play (
            song_id,
            play_title,
            play_note,
            instrument_id,
            song_play_file_id,
            player_id
        ) VALUES (
            #{songId},
            #{title},
            #{note},
            #{instrumentId},
            #{fileId},
            #{memberId}
        )
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    fun insertPlay(req: SongPlayInsert): Int
}
