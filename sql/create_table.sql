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

-- master
CREATE TABLE `beat` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(10) NOT NULL,
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