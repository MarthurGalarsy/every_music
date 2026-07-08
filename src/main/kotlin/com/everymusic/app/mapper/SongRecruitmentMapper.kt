package com.everymusic.app.mapper

import com.everymusic.app.model.SongRecruitmentView
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select

@Mapper
interface SongRecruitmentMapper {

    @Select("""
        SELECT
            sr.instrument_id AS instrumentId,
            i.name AS instrumentName,
            sr.recruitment_note AS note
        FROM song_recruitment sr
        JOIN instruments i
        ON sr.instrument_id = i.id
        WHERE sr.song_id = #{songId}
          AND sr.status = 1
        ORDER BY i.display_order
    """)
    fun findActiveBySongId(@Param("songId") songId: Long): List<SongRecruitmentView>

    @Insert("""
        INSERT INTO song_recruitment (
            song_id,
            instrument_id,
            recruitment_note,
            status
        ) VALUES (
            #{songId},
            #{instrumentId},
            #{note},
            1
        )
    """)
    fun insertActive(
        @Param("songId") songId: Long,
        @Param("instrumentId") instrumentId: Int,
        @Param("note") note: String
    ): Int

    @Delete("DELETE FROM song_recruitment WHERE song_id = #{songId}")
    fun deleteBySongId(@Param("songId") songId: Long): Int
}
