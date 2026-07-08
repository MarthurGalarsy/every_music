package com.everymusic.app.controller

import com.everymusic.app.config.SeoProperties
import com.everymusic.app.model.SongMeta
import com.everymusic.app.model.SongTagView
import com.everymusic.app.model.ValidationLimits
import com.everymusic.app.service.SongDetailService
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class SongDetailController(
    private val songDetailService: SongDetailService,
    private val seoProperties: SeoProperties
) {

    @GetMapping("/song/detail/{id}")
    fun songDetail(
        @PathVariable id: Long,
        model: Model,
        session: HttpSession,
    ): String {
        val member = currentMember(session) ?: return "redirect:/"
        val detail = songDetailService.loadSongDetail(id, member.id)
        model.addAttribute("song", detail.song)
        model.addAttribute("structures", detail.structures)
        model.addAttribute("instruments", detail.instrumentMap)
        model.addAttribute("allInstruments", detail.allInstruments)
        model.addAttribute("recruitments", detail.recruitments)
        model.addAttribute("canEditRecruitments", detail.canEditRecruitments)
        model.addAttribute("tags", detail.tags)
        model.addAttribute("canEditTags", detail.canEditTags)
        model.addAttribute("maxTagCount", ValidationLimits.MAX_TAG_COUNT)
        model.addAttribute("maxTagLength", ValidationLimits.MAX_TAG_LENGTH)
        model.addAttribute("maxCommentLength", ValidationLimits.MAX_COMMENT_LENGTH)
        model.addAttribute("maxRecruitmentNoteLength", ValidationLimits.MAX_RECRUITMENT_NOTE_LENGTH)
        model.addAttribute("seoTitle", "${detail.song.title} - ${detail.song.creater} | Every Music")
        model.addAttribute("seoDescription", buildSeoDescription(detail.song, detail.tags))
        model.addAttribute("seoUrl", seoProperties.absoluteUrl("/song/detail/${detail.song.id}"))
        return "song/detail"
    }

    private fun buildSeoDescription(song: SongMeta, tags: List<SongTagView>): String {
        val note = song.note.ifBlank { "${song.creater}さんがEvery Musicに投稿した曲です。" }
        val tagText = tags.joinToString(" ") { "#${it.tagName}" }
        val description = listOf(
            note,
            "BPM ${song.bpm}",
            song.beat,
            tagText
        ).filter { it.isNotBlank() }.joinToString(" ")
        return description.take(155)
    }
}
