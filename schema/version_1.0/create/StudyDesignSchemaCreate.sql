
-- Turn off foreign key checks so we can drop tables as needed.
set foreign_key_checks = 0;

create database if not exists studydesigndb
character set utf8;
    
use studydesigndb;

-- -----------------------------------------------------
-- Table `studydesigndb`.`TableStudyDesign`
-- -----------------------------------------------------
drop table if exists TableStudyDesign;
CREATE  TABLE IF NOT EXISTS `studydesigndb`.`TableStudyDesign` (
  `uuid` BINARY(16) NOT NULL ,  
  `flagSolveFor` ENUM( ' power ' , ' samplesize ' ) ,
  `isGuassianSelection` BOOLEAN ,
  `name` VARCHAR(256) ,
  PRIMARY KEY (`uuid`) )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `studydesigndb`.`TableConfidenceInterval`
-- -----------------------------------------------------
drop table if exists TableConfidenceInterval;
CREATE  TABLE IF NOT EXISTS `studydesigndb`.`TableConfidenceInterval` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `isBetaFixed` BOOLEAN ,
  `isSigmaFixed` BOOLEAN ,
  `lowerTrailProbability` FLOAT ,
  `upperTrailProbability` FLOAT ,
  `sampleSize` INT ,
  `rankOfDesignMatrix` INT ,
  `uuid` BINARY(16) ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_study_confidenceInterval`(`uuid` ASC) ,
  CONSTRAINT `fk_study_confidenceInterval`
    FOREIGN KEY (`uuid` )
    REFERENCES `studydesigndb`.`TableStudyDesign` (`uuid` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `studydesigndb`.`TableResponses`
-- -----------------------------------------------------
drop table if exists TableResponses;
CREATE  TABLE IF NOT EXISTS `studydesigndb`.`TableResponses` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `uuid` BINARY(16) ,
  `ResponseName` VARCHAR(45) ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_study_responses` (`uuid` ASC) ,
  CONSTRAINT `fk_study_responses`
    FOREIGN KEY (`uuid` )
    REFERENCES `studydesigndb`.`TableStudyDesign` (`uuid` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `studydesigndb`.`TableSampleSize`
-- -----------------------------------------------------
drop table if exists TableSampleSize;
CREATE  TABLE IF NOT EXISTS `studydesigndb`.`TableSampleSize` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `uuid` BINARY(16),
  `SampleSize` INT ,
  `Type` ENUM( 'Clustering' , 'PerGroup' ) ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_study_sampleSize` (`uuid` ASC) ,
  CONSTRAINT `fk_study_sampleSize`
    FOREIGN KEY (`uuid` )
    REFERENCES `studydesigndb`.`TableStudyDesign` (`uuid` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `studydesigndb`.`TableClustering`
-- -----------------------------------------------------
drop table if exists TableClustering;
CREATE  TABLE IF NOT EXISTS `studydesigndb`.`TableClustering` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `uuid` BINARY(16),
  `Group` VARCHAR(45) ,
  `Size` INT , 
  `Depth` INT ,
  `Correlation` DOUBLE ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_study_cluster` (`uuid` ASC) ,
  CONSTRAINT `fk_study_cluster`
    FOREIGN KEY (`uuid` )
    REFERENCES `studydesigndb`.`TableStudyDesign` (`uuid` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `studydesigndb`.`TableRepeatedMeasures`
-- -----------------------------------------------------
drop table if exists TableRepeatedMeasures;
CREATE  TABLE IF NOT EXISTS `studydesigndb`.`TableRepeatedMeasures` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `uuid` BINARY(16),
  `name` VARCHAR(45) ,
  `repeatType` ENUM( 'Numerical','Ordinal','Categorical') ,
  `depth` INT ,
  `units` VARCHAR(45) ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_study_repeatedMeasures` (`uuid` ASC) ,
  CONSTRAINT `fk_study_repeatedMeasures`
    FOREIGN KEY (`uuid` )
    REFERENCES `studydesigndb`.`TableStudyDesign` (`uuid` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `studydesigndb`.`TableStudyHypothesis`
-- -----------------------------------------------------
drop table if exists TableStudyHypothesis;
CREATE  TABLE IF NOT EXISTS `studydesigndb`.`TableStudyHypothesis` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `uuid` BINARY(16),
  `Type` ENUM('Main Effects' , 'Interacgtion' , 'Linear Trend' , 'Quad Trend' , 'Cubic Trend') ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_study_hypothesis` (`uuid` ASC) ,
  CONSTRAINT `fk_study_hypothesis`
    FOREIGN KEY (`uuid` )
    REFERENCES `studydesigndb`.`TableStudyDesign` (`uuid`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `studydesigndb`.`TableClusterSize`
-- -----------------------------------------------------
drop table if exists TableClusterSize;
CREATE  TABLE IF NOT EXISTS `studydesigndb`.`TableClusterSize` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `size` INT ,
  `idCluster` INT ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_cluster_clusterSize` (`idCluster` ASC) ,
  CONSTRAINT `fk_cluster_clusterSize`
    FOREIGN KEY (`idCluster` )
    REFERENCES `studydesigndb`.`TableClustering` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- ------------------------------------------------------
-- Table `studydesigndb`.`TableBetweenSubjectsEffects`
-- ------------------------------------------------------
drop table if exists TableRepeatedMeasuresSpacing;
CREATE TABLE IF NOT EXISTS `studydesigndb`.`TableBetweenSubjectsEffects` (
  `id` INT NOT NULL AUTO_INCREMENT , 
  `uuid` BINARY(16),
  `predictorName` VARCHAR(45) ,
  `category` VARCHAR(45),
  PRIMARY KEY(`id`) ,
  INDEX `fk_study_betweenSubjectsEffects` (`uuid` ASC) ,
  CONSTRAINT `fk_study_betweenSubjectsEffects`
  FOREIGN KEY(`uuid`)
  REFERENCES `studydesigndb`.`TableStudyDesign` (`uuid`)
  ON DELETE CASCADE
  ON UPDATE CASCADE)
ENGINE = InnoDB;

-- ------------------------------------------------------
-- Table `studydesigndb`.`TableMatrix`
-- ------------------------------------------------------
drop table if exists TableMatrix;
CREATE TABLE IF NOT EXISTS `studydesigndb`.`TableMatrix` (
  `id` INT NOT NULL AUTO_INCREMENT , 
  `name` VARCHAR(45) ,
  `uuid` BINARY(16),
  PRIMARY KEY(`id`) ,
  INDEX `fk_study_matrix` (`uuid` ASC) ,
  CONSTRAINT `fk_study_matrix`
  FOREIGN KEY(`uuid`)
  REFERENCES `studydesigndb`.`TableStudyDesign` (`uuid`)
  ON DELETE CASCADE
  ON UPDATE CASCADE)
ENGINE = InnoDB;

-- ------------------------------------------------------
-- Table `studydesigndb`.`TableMapMatrixColumnToBetweenSubjectEffect`
-- ------------------------------------------------------
drop table if exists TableMapMatrixColumnToBetweenSubjectEffect;
CREATE TABLE IF NOT EXISTS `studydesigndb`.`TableMapMatrixColumnToBetweenSubjectEffect` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `idBetweenSubjectEffect` INT , 
  `idMatrix` INT ,
  `column` INT ,
  PRIMARY KEY(`id`), 
  INDEX `fk_map_betweenSubjectEffect` (`idBetweenSubjectEffect` ASC) ,
  INDEX `fk_map_matrix` (`idMatrix` ASC) ,
CONSTRAINT `fk_matrix_betweenSubjectEffect`
FOREIGN KEY (`idBetweenSubjectEffect`) 
REFERENCES `studydesigndb`.`TableBetweenSubjectEffects` (`id`) ,
CONSTRAINT `fk_map_matrix`
FOREIGN KEY (`idMatrix`) 
REFERENCES `studydesigndb`.`TableMatrix` (`id`)
ON DELETE CASCADE
ON UPDATE CASCADE)
ENGINE =InnoDB;

-- ------------------------------------------------------
-- Table `studydesigndb`.`TableMatrixCell`
-- ------------------------------------------------------
drop table if exists TableMatrixCell;
CREATE TABLE IF NOT EXISTS `studydesigndb`.`TableMatrixCell` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `idMatrix` INT ,
  `row` INT ,
  `col` INT ,
  `value` DOUBLE , 
  PRIMARY KEY(`id`) ,
  INDEX `fk_matrix_matrixCell` (`idMatrix` ASC) ,
  CONSTRAINT `fk_matrix_matrixCell`
  FOREIGN KEY(`idMatrix`)
  REFERENCES `studydesigndb`.`TableMatrix` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE)
ENGINE = InnoDB;

-- ------------------------------------------------------
-- Table `studydesigndb`.`TableRepeatedMeasuresSpacing`
-- ------------------------------------------------------
drop table if exists TableRepeatedMeasuresSpacing;
CREATE TABLE IF NOT EXISTS `studydesigndb`.`TableRepeatedMeasuresSpacing` (
  `id` INT NOT NULL AUTO_INCREMENT , 
  `idRepeatedMeasures` INT ,
  `value` INT ,
  PRIMARY KEY(`id`) ,
  INDEX `fk_spacing_repeatedMeasures` (`idRepeatedMeasures` ASC) ,
  CONSTRAINT `fk_spacing_repeatedMeasures`
  FOREIGN KEY(`idRepeatedMeasures`)
  REFERENCES `studydesigndb`.`TableRepeatedMeasures` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE)
ENGINE = InnoDB; 

-- ------------------------------------------------------
-- Table `studydesigndb`.`TableBetweenSubjectsEffects`
-- ------------------------------------------------------
drop table if exists TableBetweenSubjectEffects;
CREATE TABLE IF NOT EXISTS `studydesigndb`.`TableBetweenSubjectEffects` (
  `id` INT NOT NULL AUTO_INCREMENT , 
  `uuid` BINARY(16),
  `predictor` VARCHAR(45) ,
  PRIMARY KEY(`id`) ,
  INDEX `fk_study_betweenSubjectEffects` (`uuid` ASC) ,
  CONSTRAINT `fk_study_betweenSubjectEffects`
  FOREIGN KEY(`uuid`)
  REFERENCES `studydesigndb`.`TableStudyDesign` (`uuid`)
  ON DELETE CASCADE
  ON UPDATE CASCADE)
ENGINE = InnoDB;

-- ------------------------------------------------------
-- Table `studydesigndb`.`TableCategoryList`
-- ------------------------------------------------------
drop table if exists TableCategoryList;
CREATE TABLE IF NOT EXISTS `studydesigndb`.`TableCategoryList` (
  `id` INT NOT NULL AUTO_INCREMENT , 
  `idBetweenSubjectEffect` INT,
  `category` VARCHAR(45) ,
  PRIMARY KEY(`id`) ,
  INDEX `fk_predictor_categories` (`idBetweenSubjectEffect` ASC) ,
  CONSTRAINT `fk_predictor_categories`
  FOREIGN KEY(`idBetweenSubjectEffect`)
  REFERENCES `studydesigndb`.`TableBetweenSubjectEffects` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE)
ENGINE = InnoDB;

# Turn foreign key checks back on
set foreign_key_checks = 1;