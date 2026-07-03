package com.everymusic.app.service

import com.everymusic.app.mapper.InteractionMapper
import com.everymusic.app.mapper.SongMapper
import com.everymusic.app.mapper.SongPlayMapper
import com.everymusic.app.model.CommentView
import com.everymusic.app.model.InteractionSummary
import org.apache.coyote.BadRequestException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InteractionService(
    private val interactionMapper: InteractionMapper,
    private val songMapper: SongMapper,
    private val songPlayMapper: SongPlayMapper
) {

    fun songSummary(songId: Long, memberId: Long): InteractionSummary {
        return InteractionSummary(
            liked = interactionMapper.countSongLikeByMember(songId, memberId) > 0,
            likeCount = interactionMapper.countSongLikes(songId),
            commentCount = interactionMapper.countSongComments(songId)
        )
    }

    fun songPlaySummary(songPlayId: Long, memberId: Long): InteractionSummary {
        return InteractionSummary(
            liked = interactionMapper.countSongPlayLikeByMember(songPlayId, memberId) > 0,
            likeCount = interactionMapper.countSongPlayLikes(songPlayId),
            commentCount = interactionMapper.countSongPlayComments(songPlayId)
        )
    }

    @Transactional
    fun toggleSongLike(songId: Long, memberId: Long): InteractionSummary {
        songMapper.findById(songId) ?: throw BadRequestException("Song not found")

        if (interactionMapper.countSongLikeByMember(songId, memberId) > 0) {
            interactionMapper.deleteSongLike(songId, memberId)
        } else {
            interactionMapper.insertSongLike(songId, memberId)
        }

        return songSummary(songId, memberId)
    }

    @Transactional
    fun toggleSongPlayLike(songPlayId: Long, memberId: Long): InteractionSummary {
        songPlayMapper.findById(songPlayId) ?: throw BadRequestException("Song play not found")

        if (interactionMapper.countSongPlayLikeByMember(songPlayId, memberId) > 0) {
            interactionMapper.deleteSongPlayLike(songPlayId, memberId)
        } else {
            interactionMapper.insertSongPlayLike(songPlayId, memberId)
        }

        return songPlaySummary(songPlayId, memberId)
    }

    @Transactional
    fun saveSongComment(songId: Long, memberId: Long, comment: String): InteractionSummary {
        songMapper.findById(songId) ?: throw BadRequestException("Song not found")
        interactionMapper.upsertSongComment(songId, memberId, normalizeComment(comment))
        return songSummary(songId, memberId)
    }

    @Transactional
    fun saveSongPlayComment(songPlayId: Long, memberId: Long, comment: String): InteractionSummary {
        songPlayMapper.findById(songPlayId) ?: throw BadRequestException("Song play not found")
        interactionMapper.upsertSongPlayComment(songPlayId, memberId, normalizeComment(comment))
        return songPlaySummary(songPlayId, memberId)
    }

    fun findSongComments(songId: Long, memberId: Long): List<CommentView> {
        songMapper.findById(songId) ?: throw BadRequestException("Song not found")
        return interactionMapper.findSongComments(songId, memberId)
    }

    fun findSongPlayComments(songPlayId: Long, memberId: Long): List<CommentView> {
        songPlayMapper.findById(songPlayId) ?: throw BadRequestException("Song play not found")
        return interactionMapper.findSongPlayComments(songPlayId, memberId)
    }

    private fun normalizeComment(comment: String): String {
        val normalized = comment.trim()
        if (normalized.isBlank()) {
            throw BadRequestException("Comment is blank")
        }
        if (normalized.length > 1000) {
            throw BadRequestException("Comment is too long")
        }
        return normalized
    }
}
