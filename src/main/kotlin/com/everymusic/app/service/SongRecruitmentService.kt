package com.everymusic.app.service

import com.everymusic.app.mapper.InstrumentsMapper
import com.everymusic.app.mapper.SongMapper
import com.everymusic.app.mapper.SongRecruitmentMapper
import com.everymusic.app.model.SongRecruitmentEntryRequest
import com.everymusic.app.model.SongRecruitmentView
import com.everymusic.app.model.ValidationLimits
import org.apache.coyote.BadRequestException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SongRecruitmentService(
    private val songMapper: SongMapper,
    private val instrumentsMapper: InstrumentsMapper,
    private val songRecruitmentMapper: SongRecruitmentMapper
) {

    fun findActiveBySongId(songId: Long): List<SongRecruitmentView> {
        songMapper.findById(songId) ?: throw BadRequestException("Song not found")
        return songRecruitmentMapper.findActiveBySongId(songId)
    }

    @Transactional
    fun saveRecruitments(
        songId: Long,
        memberId: Long,
        recruitments: List<SongRecruitmentEntryRequest>
    ): List<SongRecruitmentView> {
        val song = songMapper.findById(songId) ?: throw BadRequestException("Song not found")
        if (song.createrId != memberId) {
            throw BadRequestException("募集内容を編集できるのは作曲者だけです")
        }

        val instrumentIds = instrumentsMapper.findAll().map { it.id }.toSet()
        val normalizedRecruitments = recruitments
            .distinctBy { it.instrumentId }
            .map {
                if (!instrumentIds.contains(it.instrumentId)) {
                    throw BadRequestException("存在しない楽器が選択されています")
                }
                it.instrumentId to normalizeNote(it.note)
            }

        songRecruitmentMapper.deleteBySongId(songId)
        normalizedRecruitments.forEach { (instrumentId, note) ->
            songRecruitmentMapper.insertActive(songId, instrumentId, note)
        }

        return songRecruitmentMapper.findActiveBySongId(songId)
    }

    private fun normalizeNote(note: String): String {
        val normalized = note.trim()
        if (normalized.length > ValidationLimits.MAX_RECRUITMENT_NOTE_LENGTH) {
            throw BadRequestException("募集コメントは${ValidationLimits.MAX_RECRUITMENT_NOTE_LENGTH}文字以内で入力してください")
        }
        return normalized
    }
}
