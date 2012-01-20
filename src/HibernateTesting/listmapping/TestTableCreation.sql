-- -----------------------------------------------------
-- Table `test`.`grouptable`
-- -----------------------------------------------------
drop table if exists grouptable;
CREATE  TABLE IF NOT EXISTS `test.`grouptable` (
  `id` INT NOT NULL AUTO_INCREMENT,  
  `name` VARCHAR(45) ,
  `stories` VARCHAR(256) ,
  PRIMARY KEY (`TestId`) )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `test`.`grouptable`
-- -----------------------------------------------------
drop table if exists story;
CREATE  TABLE IF NOT EXISTS `test`.`story` (
  `id` INT NOT NULL AUTO_INCREMENT,  
  `info` VARCHAR(256) ,
  PRIMARY KEY (`TestId`) )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `test`.`studenttable`
-- -----------------------------------------------------
drop table if exists studenttable;
CREATE  TABLE IF NOT EXISTS `test`.`studenttable` (
  `id` INT NOT NULL AUTO_INCREMENT, 
  `schoolbench` INT UNIQUE,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `test`.`namelisttable`
-- -----------------------------------------------------
drop table if exists namelisttable;
CREATE  TABLE IF NOT EXISTS `test`.`namelisttable` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `listid` int,
  `name` VARCHAR(45) ,  
  PRIMARY KEY (`id`),
   INDEX `fk_namelisttable_doctype` (`listid` ASC) ,
  CONSTRAINT `fk_namelisttable`
  FOREIGN KEY(`listid`)
  REFERENCES `test`.`studenttable` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE)
ENGINE = InnoDB;
