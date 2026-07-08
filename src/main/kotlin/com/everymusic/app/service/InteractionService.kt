package com.everymusic.app.service

import com.everymusic.app.mapper.InteractionMapper
import com.everymusic.app.mapper.MemberMapper
import com.everymusic.app.mapper.SongMapper
import com.everymusic.app.mapper.SongPlayMapper
import com.everymusic.app.model.CommentView
import com.everymusic.app.model.InteractionSummary
import com.everymusic.app.model.NotificationType
import com.everymusic.app.model.ValidationLimits
import org.apache.coyote.BadRequestException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InteractionService(
    private val interactionMapper: InteractionMapper,
    private val songMapper: SongMapper,
    private val songPlayMapper: SongPlayMapper,
    private val memberMapper: MemberMapper,
    private val notificationService: NotificationService
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
        val song = songMapper.findById(songId) ?: throw BadRequestException("Song not found")

        if (interactionMapper.countSongLikeByMember(songId, memberId) > 0) {
            interactionMapper.deleteSongLike(songId, memberId)
        } else {
            interactionMapper.insertSongLike(songId, memberId)
            val actor = memberMapper.findById(memberId)
            notificationService.notifyIfNeeded(
                memberId = song.createrId,
                actorMemberId = memberId,
                notificationType = NotificationType.SONG_LIKED,
                songId = songId,
                songPlayId = null,
                message = "${actor?.memberName ?: "誰か"}さんが「${song.songTitle}」にいいねしました。"
            )
        }

        return songSummary(songId, memberId)
    }

    @Transactional
    fun toggleSongPlayLike(songPlayId: Long, memberId: Long): InteractionSummary {
        val play = songPlayMapper.findById(songPlayId) ?: throw BadRequestException("Song play not found")

        if (interactionMapper.countSongPlayLikeByMember(songPlayId, memberId) > 0) {
            interactionMapper.deleteSongPlayLike(songPlayId, memberId)
        } else {
            interactionMapper.insertSongPlayLike(songPlayId, memberId)
            val actor = memberMapper.findById(memberId)
            val song = songMapper.findById(play.songId)
            notificationService.notifyIfNeeded(
                memberId = play.playerId,
                actorMemberId = memberId,
                notificationType = NotificationType.SONG_PLAY_LIKED,
                songId = play.songId,
                songPlayId = songPlayId,
                message = "${actor?.memberName ?: "誰か"}さんが「${song?.songTitle ?: "曲"}」の演奏「${play.playTitle}」にいいねしました。"
            )
        }

        return songPlaySummary(songPlayId, memberId)
    }

    @Transactional
    fun saveSongComment(songId: Long, memberId: Long, comment: String): InteractionSummary {
        val song = songMapper.findById(songId) ?: throw BadRequestException("Song not found")
        interactionMapper.upsertSongComment(songId, memberId, normalizeComment(comment))
        val actor = memberMapper.findById(memberId)
        notificationService.notifyIfNeeded(
            memberId = song.createrId,
            actorMemberId = memberId,
            notificationType = NotificationType.SONG_COMMENTED,
            songId = songId,
            songPlayId = null,
            message = "${actor?.memberName ?: "誰か"}さんが「${song.songTitle}」にコメントしました。"
        )
        return songSummary(songId, memberId)
    }

    @Transactional
    fun saveSongPlayComment(songPlayId: Long, memberId: Long, comment: String): InteractionSummary {
        val play = songPlayMapper.findById(songPlayId) ?: throw BadRequestException("Song play not found")
        interactionMapper.upsertSongPlayComment(songPlayId, memberId, normalizeComment(comment))
        val actor = memberMapper.findById(memberId)
        val song = songMapper.findById(play.songId)
        notificationService.notifyIfNeeded(
            memberId = play.playerId,
            actorMemberId = memberId,
            notificationType = NotificationType.SONG_PLAY_COMMENTED,
            songId = play.songId,
            songPlayId = songPlayId,
            message = "${actor?.memberName ?: "誰か"}さんが「${song?.songTitle ?: "曲"}」の演奏「${play.playTitle}」にコメントしました。"
        )
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
        if (normalized.length > ValidationLimits.MAX_COMMENT_LENGTH) {
            throw BadRequestException("Comment is too long")
        }
        return normalized
    }
}
