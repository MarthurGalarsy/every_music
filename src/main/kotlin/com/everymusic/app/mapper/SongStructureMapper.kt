package com.everymusic.app.mapper

import com.everymusic.app.model.*
import org.apache.ibatis.annotations.*

@Mapper
interface SongStructureMapper {
    @Select("""
        SELECT
            ss.id,
            s.name AS section_name
        FROM song_structure ss
        JOIN sections s
        ON ss.section_id = s.id
        WHERE ss.song_id = #{songId}
        ORDER BY ss.sort_order
    """)
    fun findBySongId(@Param("songId") songId: Long): List<SongStructure>

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