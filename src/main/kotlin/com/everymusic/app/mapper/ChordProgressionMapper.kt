package com.everymusic.app.mapper

import com.everymusic.app.model.*
import org.apache.ibatis.annotations.*

@Mapper
interface ChordProgressionMapper {
    @Select("""
        SELECT chord, measure_num
        FROM chord_progression
        WHERE song_structure_id = #{structureId}
        ORDER BY sort_order
    """)
    fun findByStructureId(@Param("structureId") structureId: Long): List<ChordProgression>

    @Insert("""
        INSERT INTO chord_progression (
            song_id,
            song_structure_id,
            sort_order,
            chord,
            measure_num
        )
        VALUES (
            #{songId},
            #{structureId},
            #{sortOrder},
            #{chord},
            #{measureNum}
        )
    """)
    fun insertChordProgression(cp: ChordProgressionInsert): Int
}