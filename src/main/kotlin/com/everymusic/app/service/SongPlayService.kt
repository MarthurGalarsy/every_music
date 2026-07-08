package com.everymusic.app.service

import com.everymusic.app.mapper.SongMapper
import com.everymusic.app.mapper.SongPlayFileMapper
import com.everymusic.app.mapper.SongPlayMapper
import com.everymusic.app.model.Member
import com.everymusic.app.model.NotificationType
import com.everymusic.app.model.SongPlayFileInsert
import com.everymusic.app.model.SongPlayInsert
import com.everymusic.app.model.SongPlayUploadForm
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SongPlayService(
    private val songPlayFileMapper: SongPlayFileMapper,
    private val songPlayMapper: SongPlayMapper,
    private val songMapper: SongMapper,
    private val notificationService: NotificationService
) {
    @Transactional
    fun register(
        form: SongPlayUploadForm,
        s3Key: String,
        member: Member
    ) {
        val song = songMapper.findById(form.songId)

        val songPlayFile = SongPlayFileInsert(
            type = "audio",
            s3Key = s3Key
        )
        songPlayFileMapper.insertPlayFile(
            songPlayFile
        )

        val request = SongPlayInsert(
            songId = form.songId,
            title = form.playTitle,
            note = form.playNote,
            instrumentId = form.instrumentId,
            fileId = songPlayFile.id,
            memberId = member.id
        )
        songPlayMapper.insertPlay(request)

        if (song != null) {
            notificationService.notifyIfNeeded(
                memberId = song.createrId,
                actorMemberId = member.id,
                notificationType = NotificationType.SONG_PLAY_POSTED,
                songId = form.songId,
                songPlayId = null,
                message = "${member.memberName}さんが「${song.songTitle}」に演奏「${form.playTitle}」を投稿しました。"
            )
        }
    }
}
