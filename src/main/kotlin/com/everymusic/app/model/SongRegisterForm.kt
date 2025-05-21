package com.everymusic.app.model

import jakarta.validation.constraints.*

data class SongRegisterRequest(
    @field:NotBlank val title: String,
    @field:NotBlank val note: String,
    @field:Min(1) val bpm: Int,
    @field:NotNull val beatId: Int,
    @field:Size(min = 1) val structures: List<SongStructureRequest>
)

data class SongStructureRequest(
    @field:NotNull val sectionId: Int,
    @field:Size(min = 1) val chords: List<ChordRequest>
)

data class ChordRequest(
    @field:NotBlank
    @field:Pattern(regexp = "^[A-G].*", message = "コードはA〜Gで始めてください")
    val chord: String,
    @field:Min(1)
    val measureNum: Int
)