package com.everymusic.app.service

import com.everymusic.app.mapper.SongMapper
import com.everymusic.app.model.Song
import com.everymusic.app.model.SongListItem
import org.springframework.stereotype.Service

@Service
class SongService(
    private val songMapper: SongMapper,
    private val tagService: TagService
) {

    fun findById(id: Long): Song? {
        return songMapper.findById(id)
    }

    fun searchSongs(
        title: String?,
        creater: String?,
        tag: String?,
        page: Int,
        size: Int
    ): Pair<List<SongListItem>, Int> {
        val offset = (page - 1) * size
        val songs = songMapper.findSongs(title, creater, tag, size, offset)
            .map { song ->
                SongListItem(
                    id = song.id,
                    songTitle = song.songTitle,
                    songNote = song.songNote,
                    bpm = song.bpm,
                    createrName = song.createrName,
                    beatName = song.beatName,
                    likeCount = song.likeCount,
                    commentCount = song.commentCount,
                    tags = tagService.findBySongId(song.id)
                )
            }
        val total = songMapper.countSongs(title, creater, tag)
        return songs to total
    }
}
