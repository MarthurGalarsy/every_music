package com.everymusic.app.mapper

import com.everymusic.app.model.Song
import com.everymusic.app.model.SongInsert
import com.everymusic.app.model.SongRankingRow
import com.everymusic.app.model.SongResponse
import com.everymusic.app.model.MypageSongView
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Options
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select

@Mapper
interface SongMapper {
    @Select("""
        <script>
        SELECT
            s.id,
            s.song_title,
            s.song_note,
            s.bpm,
            m.member_name AS creater_name,
            b.name AS beat_name,
            (SELECT COUNT(*) FROM song_like sl WHERE sl.song_id = s.id) AS like_count,
            (SELECT COUNT(*) FROM song_comment sc WHERE sc.song_id = s.id) AS comment_count
        FROM songs s
        JOIN member m
        ON s.creater_id = m.id
        JOIN beat b
        ON s.beat_id = b.id
        <where>
            <if test="title != null and title != ''">
                AND s.song_title LIKE CONCAT('%', #{title}, '%')
            </if>
            <if test="creater != null and creater != ''">
                AND m.member_name LIKE CONCAT('%', #{creater}, '%')
            </if>
            <if test="tag != null and tag != ''">
                AND EXISTS (
                    SELECT 1
                    FROM song_tag st
                    JOIN tags t ON st.tag_id = t.id
                    WHERE st.song_id = s.id
                      AND t.tag_name LIKE CONCAT('%', #{tag}, '%')
                )
            </if>
        </where>
        ORDER BY s.id DESC
        LIMIT #{limit} OFFSET #{offset}
        </script>
    """)
    fun findSongs(
        @Param("title") title: String?,
        @Param("creater") creater: String?,
        @Param("tag") tag: String?,
        @Param("limit") limit: Int,
        @Param("offset") offset: Int
    ): List<SongResponse>

    @Select("""
        SELECT
            s.id,
            s.song_title AS title,
            s.song_note AS note,
            s.bpm,
            b.name AS beatName,
            (SELECT COUNT(*) FROM song_play sp WHERE sp.song_id = s.id) AS playCount,
            (SELECT COUNT(*) FROM song_like sl WHERE sl.song_id = s.id) AS likeCount,
            (SELECT COUNT(*) FROM song_comment sc WHERE sc.song_id = s.id) AS commentCount
        FROM songs s
        JOIN beat b
        ON s.beat_id = b.id
        WHERE s.creater_id = #{memberId}
        ORDER BY s.id DESC
    """)
    fun findSongsByCreaterId(@Param("memberId") memberId: Long): List<MypageSongView>

    @Select("""
        <script>
        SELECT COUNT(*)
        FROM songs s
        JOIN member m ON s.creater_id = m.id
        JOIN beat b ON s.beat_id = b.id
        <where>
            <if test="title != null and title != ''">
                AND s.song_title LIKE CONCAT('%', #{title}, '%')
            </if>
            <if test="creater != null and creater != ''">
                AND m.member_name LIKE CONCAT('%', #{creater}, '%')
            </if>
            <if test="tag != null and tag != ''">
                AND EXISTS (
                    SELECT 1
                    FROM song_tag st
                    JOIN tags t ON st.tag_id = t.id
                    WHERE st.song_id = s.id
                      AND t.tag_name LIKE CONCAT('%', #{tag}, '%')
                )
            </if>
        </where>
        </script>
    """)
    fun countSongs(
        @Param("title") title: String?,
        @Param("creater") creater: String?,
        @Param("tag") tag: String?
    ): Int

    @Select("""
        <script>
        SELECT
            ranked.id,
            ranked.songTitle,
            ranked.songNote,
            ranked.bpm,
            ranked.createrName,
            ranked.beatName,
            ranked.likeCount,
            ranked.commentCount,
            ranked.playCount,
            (ranked.likeCount * 3 + ranked.commentCount * 2 + ranked.playCount) AS rankingScore
        FROM (
            SELECT
                s.id,
                s.song_title AS songTitle,
                s.song_note AS songNote,
                s.bpm,
                m.member_name AS createrName,
                b.name AS beatName,
                (SELECT COUNT(*) FROM song_like sl WHERE sl.song_id = s.id) AS likeCount,
                (SELECT COUNT(*) FROM song_comment sc WHERE sc.song_id = s.id) AS commentCount,
                (SELECT COUNT(*) FROM song_play sp WHERE sp.song_id = s.id) AS playCount
            FROM songs s
            JOIN member m
            ON s.creater_id = m.id
            JOIN beat b
            ON s.beat_id = b.id
        ) ranked
        ORDER BY
        <choose>
            <when test="rankingType == 'likes'">
                ranked.likeCount DESC,
            </when>
            <when test="rankingType == 'comments'">
                ranked.commentCount DESC,
            </when>
            <when test="rankingType == 'plays'">
                ranked.playCount DESC,
            </when>
            <otherwise>
                rankingScore DESC,
            </otherwise>
        </choose>
            ranked.id DESC
        LIMIT #{limit}
        </script>
    """)
    fun findRankingSongs(
        @Param("rankingType") rankingType: String,
        @Param("limit") limit: Int
    ): List<SongRankingRow>

    @Select("SELECT id, song_title, song_note, bpm, beat_id, creater_id FROM songs WHERE id = #{id}")
    fun findById(@Param("id") id: Long): Song?

    @Insert("""
        INSERT INTO songs (
            song_title,
            song_note,
            bpm,
            beat_id,
            creater_id
        )
        VALUES (
            #{title},
            #{note},
            #{bpm},
            #{beatId},
            #{createrId}
        )
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    fun insertSong(song: SongInsert): Int
}
