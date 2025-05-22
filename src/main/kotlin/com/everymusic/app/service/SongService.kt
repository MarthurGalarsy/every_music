package com.everymusic.app.service

import com.everymusic.app.mapper.SongMapper
import com.everymusic.app.model.Song
import com.everymusic.app.model.SongResponse
import org.springframework.stereotype.Service

@Service
class SongService(private val songMapper: SongMapper) {

    fun findById(id: Long): Song? {
        return songMapper.findById(id)
    }

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