package com.everymusic.app.mapper

import com.everymusic.app.model.SongTagView
import com.everymusic.app.model.Tag
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Options
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select

@Mapper
interface TagMapper {

    @Select("SELECT id, tag_name AS tagName FROM tags WHERE tag_name = #{tagName}")
    fun findByName(@Param("tagName") tagName: String): Tag?

    @Insert("INSERT INTO tags (tag_name) VALUES (#{tagName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    fun insert(tag: TagInsert): Int

    @Select("""
        SELECT
            t.id,
            t.tag_name AS tagName
        FROM song_tag st
        JOIN tags t
        ON st.tag_id = t.id
        WHERE st.song_id = #{songId}
        ORDER BY t.tag_name
    """)
    fun findBySongId(@Param("songId") songId: Long): List<SongTagView>

    @Delete("DELETE FROM song_tag WHERE song_id = #{songId}")
    fun deleteBySongId(@Param("songId") songId: Long): Int

    @Insert("INSERT IGNORE INTO song_tag (song_id, tag_id) VALUES (#{songId}, #{tagId})")
    fun insertSongTag(
        @Param("songId") songId: Long,
        @Param("tagId") tagId: Long
    ): Int
}

data class TagInsert(
    var id: Long = 0,
    val tagName: String
)
