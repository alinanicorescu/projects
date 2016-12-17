CREATE SCHEMA `accountmanager` DEFAULT CHARACTER SET utf8 ;
CREATE TABLE `accountmanager`.`USER_DETAILS` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC));

CREATE TABLE `USER_ACCOUNT` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(45) NOT NULL,
  `balance` decimal(4,0) NOT NULL DEFAULT '0',
  `description` varchar(45) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`),
  KEY `user_account_id_fk` (`user_id`),
  CONSTRAINT `user_account_id_fk` FOREIGN KEY (`user_id`) REFERENCES `USER_DETAILS` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
