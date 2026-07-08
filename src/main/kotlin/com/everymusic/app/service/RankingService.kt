package com.everymusic.app.service

import com.everymusic.app.mapper.SongMapper
import com.everymusic.app.model.QueryLimits
import com.everymusic.app.model.RankingType
import com.everymusic.app.model.SongRankingItem
import org.springframework.stereotype.Service

@Service
class RankingService(
    private val songMapper: SongMapper,
    private val tagService: TagService
) {

    fun findSongRanking(rankingType: String?, limit: Int = QueryLimits.DEFAULT_RANKING_LIMIT): List<SongRankingItem> {
        val normalizedType = RankingType.fromValue(rankingType)

        return songMapper.findRankingSongs(normalizedType.value, limit.coerceIn(1, QueryLimits.MAX_RANKING_LIMIT))
            .map { row ->
                SongRankingItem(
                    id = row.id,
                    songTitle = row.songTitle,
                    songNote = row.songNote,
                    bpm = row.bpm,
                    createrName = row.createrName,
                    beatName = row.beatName,
                    likeCount = row.likeCount,
                    commentCount = row.commentCount,
                    playCount = row.playCount,
                    rankingScore = row.rankingScore,
                    tags = tagService.findBySongId(row.id)
                )
            }
    }
}
