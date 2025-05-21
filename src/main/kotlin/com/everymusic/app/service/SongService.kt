package com.everymusic.app.service

import com.everymusic.app.mapper.SongMapper
import com.everymusic.app.model.SongResponse
import org.springframework.stereotype.Service

@Service
class SongService(private val songMapper: SongMapper) {
    fun searchSongs(
        title: String?,
        creater: String?,
        page: Int,
        size: Int
    ): Pair<List<SongResponse>, Int> {
        val offset = (page - 1) * size
        val songs = songMapper.findSongs(title, creater, size, offset)
        val total = songMapper.countSongs(title, creater)
        return songs to total
    }
}