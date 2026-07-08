-- every_music production DB update for social, recruitment, tag, notification, and follow features.
-- Safe to run on an existing database: all table additions use CREATE TABLE IF NOT EXISTS.

CREATE TABLE IF NOT EXISTS `song_like`
(
    `song_id` BIGINT UNSIGNED NOT NULL,
    `member_id` BIGINT UNSIGNED NOT NULL,
    `created_date` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    PRIMARY KEY (`song_id`, `member_id`),
    INDEX `idx_member_id` (`member_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT '曲へのいいね'
;

CREATE TABLE IF NOT EXISTS `song_comment`
(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `song_id` BIGINT UNSIGNED NOT NULL,
    `member_id` BIGINT UNSIGNED NOT NULL,
    `comment_text` VARCHAR(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'コメント',
    `created_date` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `updated_date` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    PRIMARY KEY (`id`),
    UNIQUE (`song_id`, `member_id`),
    INDEX `idx_song_id` (`song_id`),
    INDEX `idx_member_id` (`member_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT '曲へのコメント'
;

CREATE TABLE IF NOT EXISTS `song_play_like`
(
    `song_play_id` BIGINT UNSIGNED NOT NULL,
    `member_id` BIGINT UNSIGNED NOT NULL,
    `created_date` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    PRIMARY KEY (`song_play_id`, `member_id`),
    INDEX `idx_member_id` (`member_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT '演奏へのいいね'
;

CREATE TABLE IF NOT EXISTS `song_play_comment`
(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `song_play_id` BIGINT UNSIGNED NOT NULL,
    `member_id` BIGINT UNSIGNED NOT NULL,
    `comment_text` VARCHAR(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'コメント',
    `created_date` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `updated_date` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    PRIMARY KEY (`id`),
    UNIQUE (`song_play_id`, `member_id`),
    INDEX `idx_song_play_id` (`song_play_id`),
    INDEX `idx_member_id` (`member_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT '演奏へのコメント'
;

CREATE TABLE IF NOT EXISTS `song_recruitment`
(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `song_id` BIGINT UNSIGNED NOT NULL,
    `instrument_id` INT UNSIGNED NOT NULL,
    `recruitment_note` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '募集コメント',
    `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '0: 停止中, 1: 募集中',
    `created_date` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `updated_date` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    PRIMARY KEY (`id`),
    UNIQUE (`song_id`, `instrument_id`),
    INDEX `idx_song_id` (`song_id`),
    INDEX `idx_instrument_id` (`instrument_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT '演奏募集'
;

CREATE TABLE IF NOT EXISTS `notifications`
(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `member_id` BIGINT UNSIGNED NOT NULL COMMENT '通知を受け取るメンバーID',
    `actor_member_id` BIGINT UNSIGNED NOT NULL COMMENT '通知のきっかけになったメンバーID',
    `notification_type` VARCHAR(50) NOT NULL COMMENT '通知種別',
    `song_id` BIGINT UNSIGNED NULL,
    `song_play_id` BIGINT UNSIGNED NULL,
    `message` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '通知メッセージ',
    `read_status` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '0: 未読, 1: 既読',
    `created_date` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `updated_date` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    PRIMARY KEY (`id`),
    INDEX `idx_member_id_read_status` (`member_id`, `read_status`),
    INDEX `idx_member_id_created_date` (`member_id`, `created_date`),
    INDEX `idx_song_id` (`song_id`),
    INDEX `idx_song_play_id` (`song_play_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT 'アプリ内通知'
;

CREATE TABLE IF NOT EXISTS `tags`
(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `tag_name` VARCHAR(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'タグ名',
    `created_date` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `updated_date` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    PRIMARY KEY (`id`),
    UNIQUE (`tag_name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT 'タグ'
;

CREATE TABLE IF NOT EXISTS `song_tag`
(
    `song_id` BIGINT UNSIGNED NOT NULL,
    `tag_id` BIGINT UNSIGNED NOT NULL,
    `created_date` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    PRIMARY KEY (`song_id`, `tag_id`),
    INDEX `idx_tag_id` (`tag_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT '曲タグ'
;

CREATE TABLE IF NOT EXISTS `member_follow`
(
    `follower_member_id` BIGINT UNSIGNED NOT NULL COMMENT 'フォローするメンバーID',
    `followed_member_id` BIGINT UNSIGNED NOT NULL COMMENT 'フォローされるメンバーID',
    `created_date` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    PRIMARY KEY (`follower_member_id`, `followed_member_id`),
    INDEX `idx_followed_member_id` (`followed_member_id`),
    INDEX `idx_follower_created_date` (`follower_member_id`, `created_date`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT 'メンバーフォロー'
;
