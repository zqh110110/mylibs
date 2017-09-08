USE `test`
;

SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `test`.`user_info` CASCADE
;

DROP TABLE IF EXISTS `test`.`analyst_buildinfo` CASCADE
;

DROP TABLE IF EXISTS `test`.`analyst` CASCADE
;

CREATE TABLE `test`.`user_info`
(
	`uuid` VARCHAR(50) NOT NULL,
	`name` VARCHAR(50),
	`account` VARCHAR(50),
	`password` VARCHAR(200),
	`phone` VARCHAR(50),
	`number` VARCHAR(50),
	`update_time` VARCHAR(50),
	CONSTRAINT `PK_user_info` PRIMARY KEY (`uuid`)
) ENGINE=InnoDB
;

CREATE TABLE `test`.`analyst_buildinfo`
(
	`id` BIGINT NOT NULL AUTO_INCREMENT ,
	`app_name` VARCHAR(50),
	`pic_path` VARCHAR(100),
	`version_name` VARCHAR(50),
	`version_code` VARCHAR(50),
	`analyst_id` BIGINT,
	`build_name` VARCHAR(50),
	`buildpath` VARCHAR(50),
	CONSTRAINT `PK_analyst_buildinfo` PRIMARY KEY (`id`)
) ENGINE=InnoDB
;

CREATE TABLE `test`.`analyst`
(
	`id` BIGINT NOT NULL AUTO_INCREMENT ,
	`analyst_name` VARCHAR(50),
	`password` VARCHAR(50),
	`appkey` VARCHAR(50),
	`buildinfo_id` BIGINT,
	`user_id` VARCHAR(50),
	`temp` VARCHAR(50),
	CONSTRAINT `PK_analyst` PRIMARY KEY (`id`)
) ENGINE=InnoDB
;

ALTER TABLE `test`.`analyst_buildinfo` 
 ADD INDEX `IXFK_analyst_buildinfo_analyst` (`analyst_id` ASC)
;

ALTER TABLE `test`.`analyst_buildinfo` 
 ADD CONSTRAINT `FK_analyst_buildinfo_analyst`
	FOREIGN KEY (`analyst_id`) REFERENCES `test`.`analyst` (`id`) ON DELETE Restrict ON UPDATE Restrict
;

SET FOREIGN_KEY_CHECKS=1
