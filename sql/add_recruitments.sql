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
