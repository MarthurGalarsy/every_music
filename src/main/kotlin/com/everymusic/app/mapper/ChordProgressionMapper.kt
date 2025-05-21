package com.everymusic.app.mapper

import com.everymusic.app.model.*
import org.apache.ibatis.annotations.*

@Mapper
interface ChordProgressionMapper {

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