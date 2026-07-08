package com.everymusic.app.config

import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute

@ControllerAdvice
class SeoModelAdvice(
    private val seoProperties: SeoProperties
) {
    @ModelAttribute
    fun addSeoAttributes(model: Model) {
        model.addAttribute("siteName", SITE_NAME)
        model.addAttribute("publicBaseUrl", seoProperties.publicBaseUrl)
        model.addAttribute("defaultOgImageUrl", seoProperties.absoluteUrl(seoProperties.ogImagePath))
    }

    companion object {
        const val SITE_NAME = "Every Music"
    }
}
