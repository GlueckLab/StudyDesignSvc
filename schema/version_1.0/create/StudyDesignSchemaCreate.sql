
# Turn off foreign key checks so we can drop tables as needed.
set foreign_key_checks = 0;

create database if not exists mydb
character set utf8;
    
use mydb;

-- -----------------------------------------------------
-- Table `mydb`.`TableStudyDesign`
-- -----------------------------------------------------
drop table if exists TableStudyDesign;
CREATE  TABLE IF NOT EXISTS `mydb`.`TableStudyDesign` (
  `StudyUUID` INTEGER NOT NULL ,  
  `flagSolveFor` ENUM( ' power ' , ' samplesize ' ) ,
  `isGuassianSelection` BOOLEAN ,
  `Name` VARCHAR(256) ,
  PRIMARY KEY (`StudyUUID`) )
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
  `StudyUUID` INTEGER ,
  PRIMARY KEY (`idTableConfidenceInterval`) ,
  INDEX `fk_TableConfidenceInterval_doctype`(`StudyUUID` ASC) ,
  CONSTRAINT `fk_TableConfidenceInterval`
    FOREIGN KEY (`StudyUUID` )
    REFERENCES `mydb`.`TableStudyDesign` (`StudyUUID` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`TableResponses`
-- -----------------------------------------------------
drop table if exists TableResponses;
CREATE  TABLE IF NOT EXISTS `mydb`.`TableResponses` (
  `idTableResponses` INT NOT NULL AUTO_INCREMENT,
  `StudyUUID` INT ,
  `ResponseName` VARCHAR(45) ,
  PRIMARY KEY (`idTableResponses`) ,
  INDEX `fk_TableStudyDesign_doctype` (`StudyUUID` ASC) ,
  CONSTRAINT `fk_TableResponses`
    FOREIGN KEY (`StudyUUID` )
    REFERENCES `mydb`.`TableStudyDesign` (`StudyUUID` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`TableSampleSize`
-- -----------------------------------------------------
drop table if exists TableSampleSize;
CREATE  TABLE IF NOT EXISTS `mydb`.`TableSampleSize` (
  `idTableSampleSize` INT NOT NULL AUTO_INCREMENT ,
  `StudyUUID` INT ,
  `SampleSize` INT ,
  `Type` ENUM( 'Clustering' , 'PerGroup' ) ,
  PRIMARY KEY (`idTableSampleSize`) ,
  INDEX `fk_TableStudyDesign_doctype` (`StudyUUID` ASC) ,
  CONSTRAINT `fk_TableSampleSize`
    FOREIGN KEY (`StudyUUID` )
    REFERENCES `mydb`.`TableStudyDesign` (`StudyUUID` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`TableClustering`
-- -----------------------------------------------------
drop table if exists TableClustering;
CREATE  TABLE IF NOT EXISTS `mydb`.`TableClustering` (
  `idTableClustering` INT NOT NULL AUTO_INCREMENT ,
  `StudyUUID` INT ,
  `Group` VARCHAR(45) ,
  `Size` INT , 
  `Depth` INT ,
  `Correlation` DOUBLE ,
  PRIMARY KEY (`idTableClustering`) ,
  INDEX `fk_TableClustering_doctype` (`StudyUUID` ASC) ,
  CONSTRAINT `fk_TableClustering`
    FOREIGN KEY (`StudyUUID` )
    REFERENCES `mydb`.`TableStudyDesign` (`StudyUUID` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`TableRepeatedMeasures`
-- -----------------------------------------------------
drop table if exists TableRepeatedMeasures;
CREATE  TABLE IF NOT EXISTS `mydb`.`TableRepeatedMeasures` (
  `idTableRepeatedMeasures` INT NOT NULL AUTO_INCREMENT ,
  `StudyUUID` INT ,
  `Name` VARCHAR(45) ,
  `Type` ENUM( 'Numerical','Ordinal','Categorical') ,
  `Depth` INT ,
  `Units` VARCHAR(45) ,
  PRIMARY KEY (`idTableRepeatedMeasures`) ,
  INDEX `fk_TableRepeatedMeasures_doctype` (`StudyUUID` ASC) ,
  CONSTRAINT `fk_TableRepeatedMeasures`
    FOREIGN KEY (`StudyUUID` )
    REFERENCES `mydb`.`TableStudyDesign` (`StudyUUID` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`TableStudyHypothesis`
-- -----------------------------------------------------
drop table if exists TableStudyHypothesis;
CREATE  TABLE IF NOT EXISTS `mydb`.`TableStudyHypothesis` (
  `idTableStudyHypothesis` INT NOT NULL AUTO_INCREMENT ,
  `StudyUUID` INT ,
  `Type` ENUM('Main Effects' , 'Interacgtion' , 'Linear Trend' , 'Quad Trend' , 'Cubic Trend') ,
  PRIMARY KEY (`idTableStudyHypothesis`) ,
  INDEX `fk_TableStudyHypothesis_doctype` (`StudyUUID` ASC) ,
  CONSTRAINT `fk_TableStudyHypothesis`
    FOREIGN KEY (`StudyUUID` )
    REFERENCES `mydb`.`TableStudyDesign` (`StudyUUID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`TableClusterSize`
-- -----------------------------------------------------
drop table if exists TableClusterSize;
CREATE  TABLE IF NOT EXISTS `mydb`.`TableClusterSize` (
  `idTableClusterSize` INT NOT NULL AUTO_INCREMENT ,
  `Size` INT ,
  `idCluster` INT ,
  PRIMARY KEY (`idTableClusterSize`) ,
  INDEX `fk_TableClusterSize_doctype` (`idCluster` ASC) ,
  CONSTRAINT `fk_TableClusterSize`
    FOREIGN KEY (`idCluster` )
    REFERENCES `mydb`.`TableClustering` (`idTableClustering` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- ------------------------------------------------------
-- Table `mydb`.`TableRepeatedMeasures`
-- ------------------------------------------------------
drop table if exists TableRepeatedMeasures;
CREATE TABLE IF NOT EXISTS `mydb`.`TableRepeatedMeasures` (
  `idTableRepeatedMeasures` INT NOT NULL AUTO_INCREMENT ,
  `StudyUUID` INT ,
  `Name` VARCHAR(45) ,
  `Type` ENUM( 'Numerical' , 'Ordinal' , 'Categorical') ,
  `Depth` INT ,
  `Units` VARCHAR(45) ,
  PRIMARY KEY (`idTableRepeatedMeasures`) ,
  INDEX `fk_TableRepeatedMeasures_doctype` (`StudyUUID` ASC) ,
  CONSTRAINT `fk_TableRepeatedMeasures`
    FOREIGN KEY(`StudyUUID`) 
    REFERENCES `mydb`.`TableStudyDesign` (`StudyUUID` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- ------------------------------------------------------
-- Table `mydb`.`TableBetweenSubjectsEffects`
-- ------------------------------------------------------
drop table if exists TableRepeatedMeasuresSpacing;
CREATE TABLE IF NOT EXISTS `mydb`.`TableBetweenSubjectsEffects` (
  `idTableBetweenSubjectsEffects` INT NOT NULL AUTO_INCREMENT , 
  `StudyUUID` INT ,
  `PredictorName` VARCHAR(45) ,
  `Category` VARCHAR(45),
  PRIMARY KEY(`idTableBetweenSubjectsEffects`) ,
  INDEX `fk_TableBetweenSubjectsEffects_doctype` (`StudyUUID` ASC) ,
  CONSTRAINT `fk_TableBetweenSubjectsEffects`
  FOREIGN KEY(`StudyUUID`)
  REFERENCES `mydb`.`TableStudyDesign` (`StudyUUID`)
  ON DELETE CASCADE
  ON UPDATE CASCADE)
ENGINE = InnoDB;

-- ------------------------------------------------------
-- Table `mydb`.`TableMatrix`
-- ------------------------------------------------------
drop table if exists TableMatrix;
CREATE TABLE IF NOT EXISTS `mydb`.`TableMatrix` (
  `idTableMatrix` INT NOT NULL AUTO_INCREMENT , 
  `Name` VARCHAR(45) ,
  `StudyUUID` INT ,
  PRIMARY KEY(`idTableMatrix`) ,
  INDEX `fk_TableMatrix_doctype` (`StudyUUID` ASC) ,
  CONSTRAINT `fk_TableMatrix`
  FOREIGN KEY(`StudyUUID`)
  REFERENCES `mydb`.`TableStudyDesign` (`StudyUUID`)
  ON DELETE CASCADE
  ON UPDATE CASCADE)
ENGINE = InnoDB;

-- ------------------------------------------------------
-- Table `mydb`.`TableMapMatrixColumnToBetweenSubjectEffect`
-- ------------------------------------------------------
drop table if exists TableMapMatrixColumnToBetweenSubjectEffect;
CREATE TABLE IF NOT EXISTS `mydb`.`TableMapMatrixColumnToBetweenSubjectEffect` (
  `idTableMapMatrixColumnToBetweenSubjectEffect` INT NOT NULL AUTO_INCREMENT ,
  `idBetweenSubjectEffect` INT , 
  `idTableMatrix` INT ,
  `column` INT ,
  PRIMARY KEY(`idTableMapMatrixColumnToBetweenSubjectEffect`), 
  INDEX `fk_TableMapMatrixColumnToBetweenSubjectEffect1_doctype` (`idBetweenSubjectEffect` ASC) ,
  INDEX `fk_TableMapMatrixColumnToBetweenSubjectEffect2_doctype` (`idTableMatrix` ASC) ,
CONSTRAINT `fk_TableMapMatrixColumnToBetweenSubjectEffect1`
FOREIGN KEY (`idBetweenSubjectEffect`) 
REFERENCES `mydb`.`TableBetweenSubjectEffects` (`idTableBetweenSubjectEffects`) ,
CONSTRAINT `fk_TableMapMatrixColumnToBetweenSubjectEffect2`
FOREIGN KEY (`idTableMatrix`) 
REFERENCES `mydb`.`TableMatrix` (`idTableMatrix`)
ON DELETE CASCADE
ON UPDATE CASCADE)
ENGINE =InnoDB;

-- ------------------------------------------------------
-- Table `mydb`.`TableMatrixCell`
-- ------------------------------------------------------
drop table if exists TableMatrixCell;
CREATE TABLE IF NOT EXISTS `mydb`.`TableMatrixCell` (
  `idTableMatrixCell` INT NOT NULL AUTO_INCREMENT ,
  `idTableMatrix` INT ,
  `row` INT ,
  `col` INT ,
  `value` DOUBLE , 
  PRIMARY KEY(`idTableMatrixCell`) ,
  INDEX `fk_TableMatrixCell_doctype` (`idTableMatrix` ASC) ,
  CONSTRAINT `fk_TableMatrixCell`
  FOREIGN KEY(`idTableMatrix`)
  REFERENCES `mydb`.`TableMatrix` (`idTableMatrix`)
  ON DELETE CASCADE
  ON UPDATE CASCADE)
ENGINE = InnoDB;

-- ------------------------------------------------------
-- Table `mydb`.`TableRepeatedMeasuresSpacing`
-- ------------------------------------------------------
drop table if exists TableRepeatedMeasuresSpacing;
CREATE TABLE IF NOT EXISTS `mydb`.`TableRepeatedMeasuresSpacing` (
  `idTableRepeatedMeasuresSpacing` INT NOT NULL AUTO_INCREMENT , 
  `idRepeatedMeasures` INT ,
  `value` INT ,
  PRIMARY KEY(`idTableRepeatedMeasuresSpacing`) ,
  INDEX `fk_TableRepeatedMeasuresSpacing_doctype` (`idRepeatedMeasures` ASC) ,
  CONSTRAINT `fk_TableRepeatedMeasuresSpacing`
  FOREIGN KEY(`idRepeatedMeasures`)
  REFERENCES `mydb`.`TableRepeatedMeasures`       (`idTableRepeatedMeasures`)
  ON DELETE CASCADE
  ON UPDATE CASCADE)
ENGINE = InnoDB; 

# Turn foreign key checks back on
set foreign_key_checks = 1;

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