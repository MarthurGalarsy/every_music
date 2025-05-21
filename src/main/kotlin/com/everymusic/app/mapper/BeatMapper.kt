package com.everymusic.app.mapper

import com.everymusic.app.model.Beat
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface BeatMapper {
    @Select("SELECT id, name FROM beat ORDER BY display_order")
    fun findAll(): List<Beat>
}
