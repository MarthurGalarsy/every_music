package com.everymusic.app.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class SeoProperties(
    @Value("\${app.public-base-url:https://every-music.com}")
    publicBaseUrl: String,
    @Value("\${app.og-image-path:/images/ogp-default.svg}")
    val ogImagePath: String
) {
    val publicBaseUrl: String = publicBaseUrl.trimEnd('/')

    fun absoluteUrl(path: String): String {
        val normalizedPath = if (path.startsWith("/")) path else "/$path"
        return "$publicBaseUrl$normalizedPath"
    }
}
