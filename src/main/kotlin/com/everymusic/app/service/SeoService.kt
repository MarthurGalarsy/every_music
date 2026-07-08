package com.everymusic.app.service

import com.everymusic.app.config.SeoProperties
import org.springframework.stereotype.Service

@Service
class SeoService(
    private val seoProperties: SeoProperties
) {
    fun buildSitemap(): String {
        val urls = listOf(
            SitemapUrl(path = "/", changefreq = "weekly", priority = "1.0"),
            SitemapUrl(path = "/member/register", changefreq = "monthly", priority = "0.5"),
            SitemapUrl(path = "/introduction", changefreq = "monthly", priority = "0.6")
        )

        return buildString {
            appendLine("""<?xml version="1.0" encoding="UTF-8"?>""")
            appendLine("""<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">""")
            urls.forEach { url ->
                appendLine("  <url>")
                appendLine("    <loc>${xmlEscape(seoProperties.absoluteUrl(url.path))}</loc>")
                appendLine("    <changefreq>${url.changefreq}</changefreq>")
                appendLine("    <priority>${url.priority}</priority>")
                appendLine("  </url>")
            }
            appendLine("</urlset>")
        }
    }

    private fun xmlEscape(value: String): String {
        return value
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&apos;")
    }
}

private data class SitemapUrl(
    val path: String,
    val changefreq: String,
    val priority: String
)
