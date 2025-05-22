package com.everymusic.app.service

import com.everymusic.app.mapper.InstrumentsMapper
import com.everymusic.app.model.Instrument
import org.springframework.stereotype.Service

@Service
class InstrumentsService(
    private val instrumentMapper: InstrumentsMapper
) {
    fun findAll(): List<Instrument> {
        return instrumentMapper.findAll()
    }
}