-- -----------------------------------------------------
-- Table `mydb`.`TableTest`
-- -----------------------------------------------------
drop table if exists TableSampleTest;
CREATE  TABLE IF NOT EXISTS `test`.`TableSampleTest` (
  `testId` INT NOT NULL AUTO_INCREMENT,  
  `name` VARCHAR(256) ,
  PRIMARY KEY (`TestId`) )
ENGINE = InnoDB;

-- ------------------------------------------------------
-- Table `mydb`.`TableBetweenSubjectsEffects`
-- ------------------------------------------------------
drop table if exists TableBetweenSubjectEffects;
CREATE TABLE IF NOT EXISTS `mydb`.`TableBetweenSubjectEffects` (
  `idTableBetweenSubjectEffects` INT NOT NULL AUTO_INCREMENT , 
  `StudyUUID` INT ,
  `PredictorName` VARCHAR(45) ,
  PRIMARY KEY(`idTableBetweenSubjectEffects`) ,
  INDEX `fk_TableBetweenSubjectEffects_doctype` (`StudyUUID` ASC) ,
  CONSTRAINT `fk_TableBetweenSubjectEffects`
  FOREIGN KEY(`StudyUUID`)
  REFERENCES `mydb`.`TableStudyDesign` (`StudyUUID`)
  ON DELETE CASCADE
  ON UPDATE CASCADE)
ENGINE = InnoDB;

-- ------------------------------------------------------
-- Table `mydb`.`TableCategoryList`
-- ------------------------------------------------------
drop table if exists TableCategoryList;
CREATE TABLE IF NOT EXISTS `mydb`.`TableCategoryList` (
  `idTableCategoryList` INT,
  `Name` VARCHAR(45) ,
  PRIMARY KEY(`idTableCategoryList`) ,
  INDEX `fk_TableCategoryList_doctype` (`idTableCategoryList` ASC) ,
  CONSTRAINT `fk_TableCategoryList`
  FOREIGN KEY(`idTableCategoryList`)
  REFERENCES `mydb`.`TableBetweenSubjectEffects` (`idTableBetweenSubjectEffects`)
  ON DELETE CASCADE
  ON UPDATE CASCADE)
ENGINE = InnoDB;