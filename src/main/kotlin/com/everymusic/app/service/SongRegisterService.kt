package com.everymusic.app.service

import com.everymusic.app.mapper.BeatMapper
import com.everymusic.app.mapper.ChordProgressionMapper
import com.everymusic.app.mapper.SectionsMapper
import com.everymusic.app.mapper.SongMapper
import com.everymusic.app.mapper.SongStructureMapper
import com.everymusic.app.model.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SongRegisterService(
    private val beatMapper: BeatMapper,
    private val sectionsMapper: SectionsMapper,
    private val songMapper: SongMapper,
    private val songStructureMapper: SongStructureMapper,
    private val chordProgressionMapper: ChordProgressionMapper,
) {
    fun getBeatList(): List<Beat> = beatMapper.findAll()
    fun getSectionList(): List<Section> = sectionsMapper.findAll()

    @Transactional
    fun registerSong(
        request: SongRegisterRequest,
        member: Member,
    ) {
        val song = SongInsert(
            title = request.title,
            note = request.note,
            bpm = request.bpm,
            beatId = request.beatId,
            createrId = (member as Member).id
        )
        songMapper.insertSong(song)

        request.structures.forEachIndexed { sIndex, s ->
            val structure = SongStructureInsert(
                songId = song.id,
                sectionId = s.sectionId,
                sortOrder = sIndex + 1
            )
            songStructureMapper.insertSongStructure(structure)

            s.chords.forEachIndexed { cIndex, chord ->
                val cp = ChordProgressionInsert(
                    songId = song.id,
                    structureId = structure.id,
                    sortOrder = cIndex + 1,
                    chord = chord.chord,
                    measureNum = chord.measureNum
                )
                chordProgressionMapper.insertChordProgression(cp)
            }
        }
    }

    fun getSongCopyData(songId: Long): SongCopyForm {
        val song = songMapper.findById(songId)
        val structures = songStructureMapper.findBySongId(songId)
        val structureForms = structures.map { structure ->
            val chords = chordProgressionMapper.findByStructureId(structure.id)
            SongStructureForm(
                sectionId = structure.sectionName.toIntOrNull() ?: 0, // 必要に応じてID取得へ
                chords = chords.map { ChordRequest(it.chord, it.measureNum) }
            )
        }
        return SongCopyForm(
            title = song.songTitle,
            note = song.songNote,
            bpm = song.bpm,
            beatId = song.beatId,
            structures = structureForms
        )
    }
}