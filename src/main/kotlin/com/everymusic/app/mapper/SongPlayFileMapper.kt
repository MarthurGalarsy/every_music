package com.everymusic.app.mapper

import com.everymusic.app.model.SongPlayFile
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select

@Mapper
interface SongPlayFileMapper {
    @Select("SELECT id, s3_key FROM song_play_file WHERE id = #{id}")
    fun findFileById(@Param("id") id: Long): SongPlayFile
}