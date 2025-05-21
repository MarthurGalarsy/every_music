package com.everymusic.app.mapper

import com.everymusic.app.model.SongResponse
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select

@Mapper
interface SongMapper {
    @Select("""
    <script>
        SELECT
            s.id,
            s.song_title,
            s.song_note,
            s.bpm,
            m.member_name AS creater_name,
            b.name AS beat_name
        FROM songs s
        JOIN member m
        ON s.creater_id = m.id
        JOIN beat b
        ON s.beat_id = b.id
        <where>
            <if test="title != null and title != ''">
                AND s.song_title LIKE CONCAT('%', #{title}, '%')
            </if>
            <if test="creater != null and creater != ''">
                AND m.member_name LIKE CONCAT('%', #{creater}, '%')
            </if>
        </where>
        ORDER BY s.id DESC
    </script>
""")
    fun findSongs(
        @Param("title") title: String?,
        @Param("creater") creater: String?
    ): List<SongResponse>
}