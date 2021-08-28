drop schema hms;

CREATE SCHEMA IF NOT EXISTS `hms`;

USE `hms` ;

CREATE TABLE IF NOT EXISTS `hms`.`guests` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(40) DEFAULT NULL,
    `surname` VARCHAR(40) DEFAULT NULL,
    PRIMARY KEY (`id`)
);
