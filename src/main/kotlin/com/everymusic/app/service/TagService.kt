package com.everymusic.app.service

import com.everymusic.app.mapper.TagInsert
import com.everymusic.app.mapper.TagMapper
import com.everymusic.app.mapper.SongMapper
import com.everymusic.app.model.SongTagView
import com.everymusic.app.model.ValidationLimits
import org.apache.coyote.BadRequestException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TagService(
    private val tagMapper: TagMapper,
    private val songMapper: SongMapper
) {

    fun findBySongId(songId: Long): List<SongTagView> {
        return tagMapper.findBySongId(songId)
    }

    @Transactional
    fun saveTagsForNewSong(songId: Long, tagNames: List<String>) {
        replaceSongTags(songId, normalizeTagNames(tagNames))
    }

    @Transactional
    fun saveTagsForCreator(
        songId: Long,
        memberId: Long,
        tagNames: List<String>
    ): List<SongTagView> {
        val song = songMapper.findById(songId) ?: throw BadRequestException("Song not found")
        if (song.createrId != memberId) {
            throw BadRequestException("タグを編集できるのは作曲者だけです")
        }

        replaceSongTags(songId, normalizeTagNames(tagNames))
        return tagMapper.findBySongId(songId)
    }

    private fun replaceSongTags(songId: Long, tagNames: List<String>) {
        tagMapper.deleteBySongId(songId)
        tagNames.forEach { tagName ->
            val tag = tagMapper.findByName(tagName) ?: run {
                val insert = TagInsert(tagName = tagName)
                tagMapper.insert(insert)
                tagMapper.findByName(tagName) ?: throw BadRequestException("Tag creation failed")
            }
            tagMapper.insertSongTag(songId, tag.id)
        }
    }

    private fun normalizeTagNames(tagNames: List<String>): List<String> {
        val normalized = tagNames
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .distinct()

        if (normalized.size > ValidationLimits.MAX_TAG_COUNT) {
            throw BadRequestException("タグは${ValidationLimits.MAX_TAG_COUNT}個以内で入力してください")
        }
        if (normalized.any { it.length > ValidationLimits.MAX_TAG_LENGTH }) {
            throw BadRequestException("タグは${ValidationLimits.MAX_TAG_LENGTH}文字以内で入力してください")
        }

        return normalized
    }
}
