package com.everymusic.app.controller

import com.everymusic.app.service.SeoService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SeoController(
    private val seoService: SeoService
) {
    @GetMapping(value = ["/sitemap.xml"], produces = [MediaType.APPLICATION_XML_VALUE])
    fun sitemap(): String {
        return seoService.buildSitemap()
    }
}
