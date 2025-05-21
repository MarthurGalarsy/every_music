package com.everymusic.app.mapper

import com.everymusic.app.model.SongPlay
import org.apache.ibatis.annotations.Mapper
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
}
