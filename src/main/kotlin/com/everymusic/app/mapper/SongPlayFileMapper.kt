package com.everymusic.app.mapper

import com.everymusic.app.model.SongPlayFile
import com.everymusic.app.model.SongPlayFileInsert
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Options
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select

@Mapper
interface SongPlayFileMapper {
    @Select("SELECT id, s3_key FROM song_play_file WHERE id = #{id}")
    fun findFileById(@Param("id") id: Long): SongPlayFile

    @Insert("""
        INSERT INTO song_play_file (type, s3_key)
        VALUES ('audio', #{s3Key})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    fun insertPlayFile(req: SongPlayFileInsert): Int

}