package com.everymusic.app.mapper

import com.everymusic.app.model.Instrument
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface InstrumentsMapper {
    @Select("SELECT id, name FROM instruments ORDER BY display_order")
    fun findAll(): List<Instrument>
}