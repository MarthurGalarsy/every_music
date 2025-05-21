package com.everymusic.app.service

import com.everymusic.app.mapper.SongMapper
import com.everymusic.app.model.SongResponse
import org.springframework.stereotype.Service

@Service
class SongService(private val songMapper: SongMapper) {
    fun searchSongs(title: String?, creater: String?): List<SongResponse> {
        return songMapper.findSongs(title, creater)
    }
}