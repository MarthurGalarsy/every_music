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
