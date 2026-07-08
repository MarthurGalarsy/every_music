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
