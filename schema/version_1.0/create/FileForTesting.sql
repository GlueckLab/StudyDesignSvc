-- -----------------------------------------------------
-- Table `mydb`.`TableStudyDesign`
-- -----------------------------------------------------
drop table if exists TableStudyDesign;
CREATE  TABLE IF NOT EXISTS `mydb`.`TableStudyDesign` (
  `idTableStudyDesign` INT NOT NULL AUTO_INCREMENT,
  `StudyUUID` VARCHAR(128) NOT NULL UNIQUE ,    
  `Name` VARCHAR(256) ,
  PRIMARY KEY (`idTableStudyDesign`) )
ENGINE = InnoDB;

insert into tablestudydesign values(1,"00000000-0000-002a-0000-00000000002a","test1");


insert into tableconfidenceinterval values(1,true,true,0.5,0.3,500,2,"00000000-0000-002a-0000-00000000002a");


-- -----------------------------------------------------
-- Table `mydb`.`TableStudyDesign`
-- -----------------------------------------------------
drop table if exists TableStudyDesign;
CREATE  TABLE IF NOT EXISTS `mydb`.`TableStudyDesign` (
  `idTableStudyDesign` INT NOT NULL AUTO_INCREMENT,
  `StudyUUID` VARCHAR(128) NOT NULL UNIQUE ,  
  `flagSolveFor` ENUM( 'power' , 'samplesize' ) ,
  `isGuassianSelection` BOOLEAN ,
  `Name` VARCHAR(256) ,
  PRIMARY KEY (`idTableStudyDesign`) )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`TableConfidenceInterval`
-- -----------------------------------------------------
drop table if exists TableConfidenceInterval;
CREATE  TABLE IF NOT EXISTS `mydb`.`TableConfidenceInterval` (
  `idTableConfidenceInterval` INT NOT NULL AUTO_INCREMENT ,
  `isBetaFixed` BOOLEAN ,
  `isSigmaFixed` BOOLEAN ,
  `lowerTrailProbability` FLOAT ,
  `upperTrailProbability` FLOAT ,
  `sampleSize` INT ,
  `rankOfDesignMatrix` INT ,
  `StudyUUID` VARCHAR(128) NOT NULL UNIQUE ,
  PRIMARY KEY (`idTableConfidenceInterval`) ,
  INDEX `fk_TableConfidenceInterval_doctype`(`StudyUUID` ASC) ,
  CONSTRAINT `fk_TableConfidenceInterval`
    FOREIGN KEY (`StudyUUID` )
    REFERENCES `mydb`.`TableStudyDesign` (`StudyUUID` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;
