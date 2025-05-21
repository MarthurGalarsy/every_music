package com.everymusic.app.service

import com.everymusic.app.mapper.*
import com.everymusic.app.model.*
import org.springframework.stereotype.Service

@Service
class SongDetailService(
    private val songMapper: SongMapper,
    private val memberMapper: MemberMapper,
    private val beatMapper: BeatMapper,
    private val structureMapper: SongStructureMapper,
    private val chordMapper: ChordProgressionMapper,
    private val instrumentMapper: InstrumentsMapper,
    private val songPlayMapper: SongPlayMapper,
    private val songPlayFileMapper: SongPlayFileMapper
) {

    fun loadSongDetail(songId: Long): SongDetailView {
        val song = songMapper.findById(songId)
        val creater = memberMapper.findById(song.createrId)
        val beat = beatMapper.findById(song.beatId)

        val structures = structureMapper.findBySongId(songId).map { structure ->
            val chords = chordMapper.findByStructureId(structure.id)
            SongStructureView(
                sectionName = structure.sectionName,
                chords = chords.map { ChordView(it.chord, it.measureNum) }
            )
        }

        val instruments = instrumentMapper.findAll()
        val plays = songPlayMapper.findBySongId(songId)
        val instrumentMap = instruments.associateWith { inst ->
            plays.filter { it.instrumentId == inst.id }.map { play ->
                val player = memberMapper.findById(play.playerId)
                val file = songPlayFileMapper.findFileById(play.songPlayFileId)
                SongPlayView(
                    id = play.id,
                    title = play.playTitle,
                    note = play.playNote,
                    // 必ずいる
                    playerName = player!!.memberName,
                    audioUrl = "/files/${file.s3Key}"
                )
            }
        }

        return SongDetailView(
            song = SongMeta(
                id = song.id,
                title = song.songTitle,
                note = song.songNote,
                bpm = song.bpm,
                beat = beat.name,
                // 必ずいる
                creater = creater!!.memberName
            ),
            structures = structures,
            instrumentMap = instrumentMap
        )
    }
}