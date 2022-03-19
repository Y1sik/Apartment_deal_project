CREATE TABLE `user` (
  `USER_SEQ` INT NOT NULL AUTO_INCREMENT,
  `USER_NAME` VARCHAR(100) NOT NULL,
  `USER_NICKNAME` VARCHAR(100) NOT NULL,
  `USER_PASSWORD` VARCHAR(50) NOT NULL,
  `USER_EMAIL` VARCHAR(100) NOT NULL,
  `USER_PROFILE_IMAGE_URL` VARCHAR(500) NULL DEFAULT NULL,
  `USER_REGISTER_DATE` VARCHAR(45) NULL DEFAULT NULL,
  `USER_ADMIN` TINYINT NOT NULL DEFAULT 0,
  `USER_POINT` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`USER_SEQ`),
  UNIQUE INDEX `USER_EMAIL_UNIQUE` (`USER_EMAIL` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `happyhouse_rf`.`free` (
  `FREE_ID` INT NOT NULL AUTO_INCREMENT,
  `USER_SEQ` INT NOT NULL,
  `TITLE` VARCHAR(500) NULL DEFAULT NULL,
  `CONTENT` TEXT NULL DEFAULT NULL,
  `REG_DT` DATETIME NULL DEFAULT NULL,
  `READ_COUNT` INT NULL DEFAULT '0',
  PRIMARY KEY (`FREE_ID`),
  INDEX `FK_USER_idx` (`USER_SEQ` ASC) VISIBLE,
  CONSTRAINT `FK_USER`
    FOREIGN KEY (`USER_SEQ`)
    REFERENCES `happyhouse_rf`.`user` (`USER_SEQ`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `happyhouse_rf`.`free_file` (
  `FILE_ID` INT NOT NULL AUTO_INCREMENT,
  `FREE_ID` INT NOT NULL,
  `FILE_NAME` VARCHAR(500) NOT NULL,
  `FILE_SIZE` INT NOT NULL,
  `FILE_CONTENT_TYPE` VARCHAR(500) NOT NULL,
  `FILE_URL` VARCHAR(500) NOT NULL,
  `REG_DT` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`FILE_ID`),
  INDEX `FREE_FILE_FK_idx` (`FREE_ID` ASC) VISIBLE,
  CONSTRAINT `FREE_FILE_FK`
    FOREIGN KEY (`FREE_ID`)
    REFERENCES `happyhouse_rf`.`free` (`FREE_ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `happyhouse_rf`.`free_user_read` (
  `FREE_ID` INT NOT NULL,
  `USER_SEQ` INT NOT NULL,
  PRIMARY KEY (`FREE_ID`, `USER_SEQ`),
  INDEX `FREE_FK_idx` (`USER_SEQ` ASC) VISIBLE,
  CONSTRAINT `FREE_USER_FK`
    FOREIGN KEY (`USER_SEQ`)
    REFERENCES `happyhouse_rf`.`user` (`USER_SEQ`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FREE_USER_FK2`
    FOREIGN KEY (`FREE_ID`)
    REFERENCES `happyhouse_rf`.`free` (`FREE_ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `happyhouse_rf`.`notice` (
  `NOTICE_ID` INT NOT NULL AUTO_INCREMENT,
  `USER_SEQ` INT NOT NULL,
  `TITLE` VARCHAR(500) NULL DEFAULT NULL,
  `CONTENT` TEXT NULL DEFAULT NULL,
  `REG_DT` DATETIME NULL DEFAULT NULL,
  `READ_COUNT` INT NULL DEFAULT '0',
  PRIMARY KEY (`NOTICE_ID`),
  INDEX `USER_FK_idx` (`USER_SEQ` ASC) VISIBLE,
  CONSTRAINT `USER_FK`
    FOREIGN KEY (`USER_SEQ`)
    REFERENCES `happyhouse_rf`.`user` (`USER_SEQ`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `happyhouse_rf`.`notice_file` (
  `FILE_ID` INT NOT NULL AUTO_INCREMENT,
  `NOTICE_ID` INT NOT NULL,
  `FILE_NAME` VARCHAR(500) NOT NULL,
  `FILE_SIZE` INT NOT NULL,
  `FILE_CONTENT_TYPE` VARCHAR(500) NOT NULL,
  `FILE_URL` VARCHAR(500) NOT NULL,
  `REG_DT` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`FILE_ID`),
  INDEX `NOTICE_FILE_FK_idx` (`NOTICE_ID` ASC) VISIBLE,
  CONSTRAINT `NOTICE_FILE_FK`
    FOREIGN KEY (`NOTICE_ID`)
    REFERENCES `happyhouse_rf`.`notice` (`NOTICE_ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `happyhouse_rf`.`notice_user_read` (
  `NOTICE_ID` INT NOT NULL,
  `USER_SEQ` INT NOT NULL,
  PRIMARY KEY (`NOTICE_ID`, `USER_SEQ`),
  INDEX `NOTICE_USER_FK_idx` (`USER_SEQ` ASC) VISIBLE,
  CONSTRAINT `NOTICE_USER_FK`
    FOREIGN KEY (`USER_SEQ`)
    REFERENCES `happyhouse_rf`.`user` (`USER_SEQ`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `NOTICE_USER_FK2`
    FOREIGN KEY (`NOTICE_ID`)
    REFERENCES `happyhouse_rf`.`notice` (`NOTICE_ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `happyhouse_rf`.`review` (
  `REVIEW_ID` INT NOT NULL AUTO_INCREMENT,
  `USER_SEQ` INT NOT NULL,
  `TITLE` VARCHAR(500) NULL DEFAULT NULL,
  `CONTENT` TEXT NULL DEFAULT NULL,
  `REG_DT` DATETIME NULL DEFAULT NULL,
  `READ_COUNT` INT NULL DEFAULT '0',
  PRIMARY KEY (`REVIEW_ID`),
  INDEX `USER_RE_FK_idx` (`USER_SEQ` ASC) VISIBLE,
  CONSTRAINT `USER_RE_FK`
    FOREIGN KEY (`USER_SEQ`)
    REFERENCES `happyhouse_rf`.`user` (`USER_SEQ`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `happyhouse_rf`.`review_file` (
  `FILE_ID` INT NOT NULL AUTO_INCREMENT,
  `REVIEW_ID` INT NOT NULL,
  `FILE_NAME` VARCHAR(500) NOT NULL,
  `FILE_SIZE` INT NOT NULL,
  `FILE_CONTENT_TYPE` VARCHAR(500) NOT NULL,
  `FILE_URL` VARCHAR(500) NOT NULL,
  `REG_DT` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`FILE_ID`),
  INDEX `REVIEW_FILE_FK_idx` (`REVIEW_ID` ASC) VISIBLE,
  CONSTRAINT `REVIEW_FILE_FK`
    FOREIGN KEY (`REVIEW_ID`)
    REFERENCES `happyhouse_rf`.`review` (`REVIEW_ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `happyhouse_rf`.`review_user_read` (
  `REVIEW_ID` INT NOT NULL,
  `USER_SEQ` INT NOT NULL,
  PRIMARY KEY (`REVIEW_ID`, `USER_SEQ`),
  INDEX `REVIEW_USER_FK_idx` (`USER_SEQ` ASC) VISIBLE,
  CONSTRAINT `REVIEW_USER_FK`
    FOREIGN KEY (`USER_SEQ`)
    REFERENCES `happyhouse_rf`.`user` (`USER_SEQ`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `REVIEW_USER_FK2`
    FOREIGN KEY (`REVIEW_ID`)
    REFERENCES `happyhouse_rf`.`review` (`REVIEW_ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `happyhouse_rf`.`user_interest` (
  `USER_SEQ` INT NOT NULL,
  `house_no` INT NOT NULL,
  PRIMARY KEY (`USER_SEQ`, `house_no`),
  INDEX `USER_INTERST_FK_idx` (`house_no` ASC) VISIBLE,
  CONSTRAINT `USER_INTEREST_FK`
    FOREIGN KEY (`house_no`)
    REFERENCES `happyhouse_rf`.`houseinfo` (`no`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `USER_INTEREST_FK2`
    FOREIGN KEY (`USER_SEQ`)
    REFERENCES `happyhouse_rf`.`user` (`USER_SEQ`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `happyhouse_rf`.`surrounding_commercial_area` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `dongcode` CHAR(10) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`no`),
  INDEX `COM_DONG_FK_idx` (`dongcode` ASC) VISIBLE,
  CONSTRAINT `COM_DONG_FK`
    FOREIGN KEY (`dongcode`)
    REFERENCES `happyhouse_rf`.`dong_code` (`code`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

ALTER TABLE `happyhouse_rf`.`free_user_read` 
ADD COLUMN `GOOD` TINYINT NOT NULL DEFAULT 0 AFTER `USER_SEQ`;

ALTER TABLE `happyhouse_rf`.`user_interest` 
ADD COLUMN `AptName` VARCHAR(50) NOT NULL AFTER `house_no`,
ADD COLUMN `dong` VARCHAR(30) NOT NULL AFTER `AptName`,
ADD COLUMN `floor` VARCHAR(30) NOT NULL AFTER `dong`,
ADD COLUMN `area` VARCHAR(30) NOT NULL AFTER `floor`,
ADD COLUMN `dealAmount` VARCHAR(50) NOT NULL AFTER `area`;
