CREATE TABLE `songs`
(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `song_title` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '曲名',
    `song_note` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '説明',
    `bpm` INT UNSIGNED NOT NULL COMMENT 'BPM',
    `beat_id` INT UNSIGNED NOT NULL COMMENT '拍子',
    `creater_id` BIGINT UNSIGNED NOT NULL COMMENT '作曲者ID',
    `created_date` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `updated_date` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    PRIMARY KEY(`id`),
    INDEX `idx_song_title` (`song_title`),
    INDEX `idx_creater_id` (`creater_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT '曲'
;

CREATE TABLE `song_structure`
(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `song_id` BIGINT UNSIGNED NOT NULL,
    `section_id` INT UNSIGNED NOT NULL COMMENT '1: イントロ, 11: Aメロ, 12: Bメロ, 13: Cメロ, 21: 間奏, 31: サビ, 41: アウトロ',
    `sort_order` INT UNSIGNED NOT NULL COMMENT '並び順',
    `created_date` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `updated_date` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    PRIMARY KEY(`id`),
    UNIQUE (`song_id`, `section_id`, `sort_order`),
    INDEX `idx_song_id` (`song_id`),
    INDEX `idx_song_id_section_id_order` (`song_id`, `section_id`, `sort_order`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT '曲の構成'
;

CREATE TABLE `chord_progression`
(
    `song_id` BIGINT UNSIGNED NOT NULL,
    `song_structure_id` BIGINT UNSIGNED NOT NULL,
    `sort_order` INT UNSIGNED NOT NULL COMMENT '並び順',
    `chord` VARCHAR(10) NOT NULL COMMENT 'コード名',
    `measure_num` INT UNSIGNED NOT NULL COMMENT '小節数',
    `created_date` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `updated_date` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    PRIMARY KEY(`song_id`, `song_structure_id`, `sort_order`),
    INDEX `idx_song_id` (`song_id`),
    INDEX `idx_song_structure_id` (`song_structure_id`),
    INDEX `idx_song_id_song_structure_id` (`song_id`, `song_structure_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT 'コード進行'
;

CREATE TABLE `song_play`
(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `song_id` BIGINT UNSIGNED NOT NULL,
    `play_title` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '曲名',
    `play_note` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '説明',
    `instrument_id` INT UNSIGNED NOT NULL,
    `song_play_file_id` BIGINT NOT NULL COMMENT '演奏ファイルID',
    `player_id` BIGINT UNSIGNED NOT NULL COMMENT '演奏者ID',
    `created_date` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `updated_date` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    PRIMARY KEY (`id`),
    INDEX `idx_song_id` (`song_id`),
    INDEX `idx_song_id_instrument_id` (`song_id`, `instrument_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT 'コード進行'
;

CREATE TABLE song_play_file (
    `id` BIGINT UNSIGNED AUTO_INCREMENT,
    `type` VARCHAR(20) NOT NULL COMMENT '音声, midi, プレビュー など',
    `s3_key` VARCHAR(255) NOT NULL,
    `created_date` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `updated_date` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT '演奏データ'
;

CREATE TABLE `song_like`
(
    `song_id` BIGINT UNSIGNED NOT NULL,
    `member_id` BIGINT UNSIGNED NOT NULL,
    `created_date` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    PRIMARY KEY (`song_id`, `member_id`),
    INDEX `idx_member_id` (`member_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT '曲へのいいね'
;

CREATE TABLE `song_comment`
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

CREATE TABLE `song_play_like`
(
    `song_play_id` BIGINT UNSIGNED NOT NULL,
    `member_id` BIGINT UNSIGNED NOT NULL,
    `created_date` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    PRIMARY KEY (`song_play_id`, `member_id`),
    INDEX `idx_member_id` (`member_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT '演奏へのいいね'
;

CREATE TABLE `song_play_comment`
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

CREATE TABLE `song_recruitment`
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

CREATE TABLE `notifications`
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

CREATE TABLE `tags`
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

CREATE TABLE `song_tag`
(
    `song_id` BIGINT UNSIGNED NOT NULL,
    `tag_id` BIGINT UNSIGNED NOT NULL,
    `created_date` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    PRIMARY KEY (`song_id`, `tag_id`),
    INDEX `idx_tag_id` (`tag_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT '曲タグ'
;

CREATE TABLE `member_follow`
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

-- master
CREATE TABLE `beat` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(20) NOT NULL,
    `display_order` INT UNSIGNED NOT NULL,
    PRIMARY KEY(`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT '拍子'
;

CREATE TABLE `instruments` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `display_order` INT UNSIGNED NOT NULL,
    PRIMARY KEY(`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT '楽器'
;

CREATE TABLE `sections` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `display_order` INT UNSIGNED NOT NULL,
    PRIMARY KEY (`id`)
) COMMENT '曲の構成パート（例: Aメロ, サビ, 間奏 など）'
;

CREATE TABLE `member` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `member_name` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名前',
    `mail_address` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `status` TINYINT(1) NOT NULL COMMENT '0: 無効, 1: 有効',
    `created_date` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `updated_date` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    PRIMARY KEY(`id`),
    UNIQUE (`mail_address`),
    INDEX `idx_member_name` (`member_name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT 'メンバー'
;
