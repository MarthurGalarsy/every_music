package com.everymusic.app.controller

import com.everymusic.app.model.ValidationLimits
import com.everymusic.app.service.SongDetailService
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class SongDetailController(
    private val songDetailService: SongDetailService
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
        return "song/detail"
    }
}
