package com.everymusic.app.model

enum class NotificationType(val value: String) {
    SONG_PLAY_POSTED("song_play_posted"),
    SONG_LIKED("song_liked"),
    SONG_COMMENTED("song_commented"),
    SONG_PLAY_LIKED("song_play_liked"),
    SONG_PLAY_COMMENTED("song_play_commented"),
    MEMBER_FOLLOWED("member_followed")
}

enum class RankingType(val value: String) {
    FEATURED("featured"),
    LIKES("likes"),
    COMMENTS("comments"),
    PLAYS("plays");

    companion object {
        fun fromValue(value: String?): RankingType {
            return entries.firstOrNull { it.value == value } ?: FEATURED
        }
    }
}

object ValidationLimits {
    const val MAX_TAG_COUNT = 5
    const val MAX_TAG_LENGTH = 20
    const val MAX_COMMENT_LENGTH = 1000
    const val MAX_RECRUITMENT_NOTE_LENGTH = 255
}

object QueryLimits {
    const val DEFAULT_ACTIVITY_LIMIT = 50
    const val MAX_ACTIVITY_LIMIT = 100
    const val DEFAULT_RANKING_LIMIT = 20
    const val MAX_RANKING_LIMIT = 50
}
