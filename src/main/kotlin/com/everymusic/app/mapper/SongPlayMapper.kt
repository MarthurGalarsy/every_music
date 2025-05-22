package com.everymusic.app.mapper

import com.everymusic.app.model.SongPlay
import com.everymusic.app.model.SongPlayInsert
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
