package com.everymusic.app.mapper

import com.everymusic.app.model.Section
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface SectionsMapper {
    @Select("SELECT id, name FROM sections ORDER BY display_order")
    fun findAll(): List<Section>
}