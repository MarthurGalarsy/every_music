package com.everymusic.app.service

import com.everymusic.app.mapper.SongPlayFileMapper
import com.everymusic.app.mapper.SongPlayMapper
import com.everymusic.app.model.Member
import com.everymusic.app.model.SongPlayFileInsertRequest
import com.everymusic.app.model.SongPlayInsertRequest
import com.everymusic.app.model.SongPlayUploadForm
import org.springframework.stereotype.Service

@Service
class SongPlayService(
    private val songPlayFileMapper: SongPlayFileMapper,
    private val songPlayMapper: SongPlayMapper
) {
    fun register(
        form: SongPlayUploadForm,
        s3Key: String,
        member: Member
    ) {
        val fileId = songPlayFileMapper.insertPlayFile(
            SongPlayFileInsertRequest(
                "audio",
                s3Key
            )
        )
        val request = SongPlayInsertRequest(
            songId = form.songId,
            title = form.playTitle,
            note = form.playNote,
            instrumentId = form.instrumentId,
            fileId = fileId,
            memberId = member.id
        )
        songPlayMapper.insertPlay(request)
    }
}