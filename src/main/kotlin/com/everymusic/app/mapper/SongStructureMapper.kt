package com.everymusic.app.mapper

import com.everymusic.app.model.*
import org.apache.ibatis.annotations.*

@Mapper
interface SongStructureMapper {

    @Insert(
        """
        INSERT INTO song_structure (
            song_id,
            section_id,
            sort_order
        )
        VALUES (
            #{songId},
            #{sectionId},
            #{sortOrder}
        )
    """
    )
    @Options(useGeneratedKeys = true, keyProperty = "id")
    fun insertSongStructure(structure: SongStructureInsert): Int
}