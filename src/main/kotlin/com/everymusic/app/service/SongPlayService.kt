package com.everymusic.app.service

import com.everymusic.app.mapper.SongPlayFileMapper
import com.everymusic.app.mapper.SongPlayMapper
import com.everymusic.app.model.Member
import com.everymusic.app.model.SongPlayFileInsert
import com.everymusic.app.model.SongPlayInsert
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
    }
}