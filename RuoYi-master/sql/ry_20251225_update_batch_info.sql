-- 增加批次信息表的审计字段（创建人、更新人）
ALTER TABLE `batch_info`
    ADD COLUMN `create_by` VARCHAR(64) NULL DEFAULT NULL COMMENT '创建人' AFTER `owner_id`;

ALTER TABLE `batch_info`
    ADD COLUMN `update_by` VARCHAR(64) NULL DEFAULT NULL COMMENT '更新人' AFTER `end_time`;

-- 若不存在请确保以下时间字段已创建（通常已存在）
-- ALTER TABLE `batch_info` ADD COLUMN `create_time` DATETIME NULL DEFAULT NULL COMMENT '创建时间';
-- ALTER TABLE `batch_info` ADD COLUMN `update_time` DATETIME NULL DEFAULT NULL COMMENT '更新时间';
