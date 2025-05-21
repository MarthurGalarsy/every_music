package com.everymusic.app.mapper

import com.everymusic.app.model.Beat
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select

@Mapper
interface BeatMapper {
    @Select("SELECT id, name FROM beat ORDER BY display_order")
    fun findAll(): List<Beat>

    @Select("SELECT id, name FROM beat WHERE id = #{id}")
    fun findById(@Param("id") id: Int): Beat
}
