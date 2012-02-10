
-- Turn off foreign key checks so we can drop tables as needed.
set foreign_key_checks = 0;

create database if not exists studydesigndb
character set utf8;
    
use studydesigndb;

-- -----------------------------------------------------
-- Table `studydesigndb`.`STUDY_DESIGN`
-- -----------------------------------------------------
drop table if exists STUDY_DESIGN;
CREATE  TABLE IF NOT EXISTS `studydesigndb`.`STUDY_DESIGN` (
  `uuid` BINARY(16) NOT NULL ,  
  `flagSolveFor` ENUM( 'power' , 'samplesize' ) ,
  `hasGaussianCovariate` BOOLEAN ,
  `name` VARCHAR(256) ,
  PRIMARY KEY (`uuid`) )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `studydesigndb`.`CONFIDENCE_INTERVAL`
-- -----------------------------------------------------
drop table if exists CONFIDENCE_INTERVAL;
CREATE  TABLE IF NOT EXISTS `studydesigndb`.`CONFIDENCE_INTERVAL` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `isBetaFixed` BOOLEAN ,
  `isSigmaFixed` BOOLEAN ,
  `lowerTrailProbability` FLOAT ,
  `upperTrailProbability` FLOAT ,
  `sampleSize` INT ,
  `rankOfDesignMatrix` INT ,
  `uuid` BINARY(16) UNIQUE,
  PRIMARY KEY (`id`) ,
  INDEX `fk_study_confidenceInterval`(`uuid` ASC) ,
  CONSTRAINT `fk_study_confidenceInterval`
    FOREIGN KEY (`uuid` )
    REFERENCES `studydesigndb`.`STUDY_DESIGN` (`uuid` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `studydesigndb`.`RESPONSES_LIST`
-- -----------------------------------------------------
drop table if exists RESPONSES_LIST;
CREATE  TABLE IF NOT EXISTS `studydesigndb`.`RESPONSES_LIST` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `uuid` BINARY(16) ,
  `name` VARCHAR(45) ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_study_responses` (`uuid` ASC) ,
  CONSTRAINT `fk_study_responses`
    FOREIGN KEY (`uuid` )
    REFERENCES `studydesigndb`.`STUDY_DESIGN` (`uuid` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `studydesigndb`.`PER_GROUP_SAMPLE_SIZE`
-- -----------------------------------------------------
drop table if exists PER_GROUP_SAMPLE_SIZE;
CREATE  TABLE IF NOT EXISTS `studydesigndb`.`PER_GROUP_SAMPLE_SIZE` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `uuid` BINARY(16),
  `SampleSize` INT ,
  `Type` ENUM( 'Clustering' , 'PerGroup' ) ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_study_sampleSize` (`uuid` ASC) ,
  CONSTRAINT `fk_study_sampleSize`
    FOREIGN KEY (`uuid` )
    REFERENCES `studydesigndb`.`STUDY_DESIGN` (`uuid` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `studydesigndb`.`CLUSTERING`
-- -----------------------------------------------------
drop table if exists CLUSTERING;
CREATE  TABLE IF NOT EXISTS `studydesigndb`.`CLUSTERING` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `uuid` BINARY(16),
  `Group` VARCHAR(45) ,
  `Size` INT , 
  `node` INT ,
  `parent` INT,
  `Correlation` DOUBLE ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_study_cluster` (`uuid` ASC) ,
  CONSTRAINT `fk_study_cluster`
    FOREIGN KEY (`uuid` )
    REFERENCES `studydesigndb`.`STUDY_DESIGN` (`uuid` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `studydesigndb`.`REPEATED_MEASURES`
-- -----------------------------------------------------
drop table if exists REPEATED_MEASURES;
CREATE  TABLE IF NOT EXISTS `studydesigndb`.`REPEATED_MEASURES` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `uuid` BINARY(16),
  `name` VARCHAR(45) ,
  `repeatType` ENUM( 'Numerical','Ordinal','Categorical') ,
  `node` INT ,
  `parent` INT,
  `units` VARCHAR(45) ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_study_repeatedMeasures` (`uuid` ASC) ,
  CONSTRAINT `fk_study_repeatedMeasures`
    FOREIGN KEY (`uuid` )
    REFERENCES `studydesigndb`.`STUDY_DESIGN` (`uuid` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `studydesigndb`.`STUDY_HYPOTHESIS`
-- -----------------------------------------------------
drop table if exists STUDY_HYPOTHESIS;
CREATE  TABLE IF NOT EXISTS `studydesigndb`.`STUDY_HYPOTHESIS` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `uuid` BINARY(16),
  `Type` ENUM('Main Effects' , 'Interacgtion' , 'Linear Trend' , 'Quad Trend' , 'Cubic Trend') ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_study_hypothesis` (`uuid` ASC) ,
  CONSTRAINT `fk_study_hypothesis`
    FOREIGN KEY (`uuid` )
    REFERENCES `studydesigndb`.`STUDY_DESIGN` (`uuid`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `studydesigndb`.`CLUSTER_SIZE`
-- -----------------------------------------------------
drop table if exists CLUSTER_SIZE;
CREATE  TABLE IF NOT EXISTS `studydesigndb`.`CLUSTER_SIZE` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `size` INT ,
  `idCluster` INT ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_cluster_clusterSize` (`idCluster` ASC) ,
  CONSTRAINT `fk_cluster_clusterSize`
    FOREIGN KEY (`idCluster` )
    REFERENCES `studydesigndb`.`CLUSTERING` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- ------------------------------------------------------
-- Table `studydesigndb`.`BETWEEN_SUBJECT_EFFECTS`
-- ------------------------------------------------------
drop table if exists BETWEEN_SUBJECT_EFFECTS;
CREATE TABLE IF NOT EXISTS `studydesigndb`.`BETWEEN_SUBJECT_EFFECTS` (
  `id` INT NOT NULL AUTO_INCREMENT , 
  `uuid` BINARY(16),
  `predictorName` VARCHAR(45) ,
  `category` VARCHAR(45),
  PRIMARY KEY(`id`) ,
  INDEX `fk_study_betweenSubjectsEffects` (`uuid` ASC) ,
  CONSTRAINT `fk_study_betweenSubjectsEffects`
  FOREIGN KEY(`uuid`)
  REFERENCES `studydesigndb`.`STUDY_DESIGN` (`uuid`)
  ON DELETE CASCADE
  ON UPDATE CASCADE)
ENGINE = InnoDB;

-- ------------------------------------------------------
-- Table `studydesigndb`.`MATRIX`
-- ------------------------------------------------------
drop table if exists MATRIX;
CREATE TABLE IF NOT EXISTS `studydesigndb`.`MATRIX` (
  `id` INT NOT NULL AUTO_INCREMENT , 
  `name` VARCHAR(45) ,
  `uuid` BINARY(16),
  PRIMARY KEY(`id`) ,
  INDEX `fk_study_matrix` (`uuid` ASC) ,
  CONSTRAINT `fk_study_matrix`
  FOREIGN KEY(`uuid`)
  REFERENCES `studydesigndb`.`STUDY_DESIGN` (`uuid`)
  ON DELETE CASCADE
  ON UPDATE CASCADE)
ENGINE = InnoDB;

-- ------------------------------------------------------
-- Table `studydesigndb`.`MAP_MATRIX_COLUMN_TO_BETWEEN_SUBJECT_EFFECTS`
-- ------------------------------------------------------
drop table if exists MAP_MATRIX_COLUMN_TO_BETWEEN_SUBJECT_EFFECTS;
CREATE TABLE IF NOT EXISTS `studydesigndb`.`MAP_MATRIX_COLUMN_TO_BETWEEN_SUBJECT_EFFECTS` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `idBetweenSubjectEffect` INT , 
  `idMatrix` INT ,
  `column` INT ,
  PRIMARY KEY(`id`), 
  INDEX `fk_map_betweenSubjectEffect` (`idBetweenSubjectEffect` ASC) ,
  INDEX `fk_map_matrix` (`idMatrix` ASC) ,
CONSTRAINT `fk_matrix_betweenSubjectEffect`
FOREIGN KEY (`idBetweenSubjectEffect`) 
REFERENCES `studydesigndb`.`BETWEEN_SUBJECT_EFFECTS` (`id`) ,
CONSTRAINT `fk_map_matrix`
FOREIGN KEY (`idMatrix`) 
REFERENCES `studydesigndb`.`MATRIX` (`id`)
ON DELETE CASCADE
ON UPDATE CASCADE)
ENGINE =InnoDB;

-- ------------------------------------------------------
-- Table `studydesigndb`.`MATRIX_CELL`
-- ------------------------------------------------------
drop table if exists MATRIX_CELL;
CREATE TABLE IF NOT EXISTS `studydesigndb`.`MATRIX_CELL` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `idMatrix` INT ,
  `row` INT ,
  `col` INT ,
  `value` DOUBLE , 
  PRIMARY KEY(`id`) ,
  INDEX `fk_matrix_matrixCell` (`idMatrix` ASC) ,
  CONSTRAINT `fk_matrix_matrixCell`
  FOREIGN KEY(`idMatrix`)
  REFERENCES `studydesigndb`.`MATRIX` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE)
ENGINE = InnoDB;

-- ------------------------------------------------------
-- Table `studydesigndb`.`REPEATED_MEASURES_SPACING`
-- ------------------------------------------------------
drop table if exists REPEATED_MEASURES_SPACING;
CREATE TABLE IF NOT EXISTS `studydesigndb`.`REPEATED_MEASURES_SPACING` (
  `id` INT NOT NULL AUTO_INCREMENT , 
  `idRepeatedMeasures` INT ,
  `value` INT ,
  PRIMARY KEY(`id`) ,
  INDEX `fk_spacing_repeatedMeasures` (`idRepeatedMeasures` ASC) ,
  CONSTRAINT `fk_spacing_repeatedMeasures`
  FOREIGN KEY(`idRepeatedMeasures`)
  REFERENCES `studydesigndb`.`REPEATED_MEASURES` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE)
ENGINE = InnoDB; 

-- ------------------------------------------------------
-- Table `studydesigndb`.`BETWEEN_SUBJECT_EFFECTS`
-- ------------------------------------------------------
drop table if exists BETWEEN_SUBJECT_EFFECTS;
CREATE TABLE IF NOT EXISTS `studydesigndb`.`BETWEEN_SUBJECT_EFFECTS` (
  `id` INT NOT NULL AUTO_INCREMENT , 
  `uuid` BINARY(16),
  `predictor` VARCHAR(45) ,
  PRIMARY KEY(`id`) ,
  INDEX `fk_study_betweenSubjectEffects` (`uuid` ASC) ,
  CONSTRAINT `fk_study_betweenSubjectEffects`
  FOREIGN KEY(`uuid`)
  REFERENCES `studydesigndb`.`STUDY_DESIGN` (`uuid`)
  ON DELETE CASCADE
  ON UPDATE CASCADE)
ENGINE = InnoDB;

-- ------------------------------------------------------
-- Table `studydesigndb`.`CATEGORY_LIST`
-- ------------------------------------------------------
drop table if exists CATEGORY_LIST;
CREATE TABLE IF NOT EXISTS `studydesigndb`.`CATEGORY_LIST` (
  `id` INT NOT NULL AUTO_INCREMENT , 
  `idBetweenSubjectEffect` INT,
  `category` VARCHAR(45) ,
  PRIMARY KEY(`id`) ,
  INDEX `fk_predictor_categories` (`idBetweenSubjectEffect` ASC) ,
  CONSTRAINT `fk_predictor_categories`
  FOREIGN KEY(`idBetweenSubjectEffect`)
  REFERENCES `studydesigndb`.`BETWEEN_SUBJECT_EFFECTS` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE)
ENGINE = InnoDB;


-- ------------------------------------------------------
-- Table `studydesigndb`.`POWER_CURVE_DESCRIPTION`
-- ------------------------------------------------------
drop table if exists POWER_CURVE_DESCRIPTION;
CREATE  TABLE IF NOT EXISTS `studydesigndb`.`POWER_CURVE_DESCRIPTION` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `sampleSize` INT ,
  `horizontalAxisLabel` ENUM('Variability Scale Factor','Total Sample Size','Regression Coeeficient Scale Factor'),
  `stratificationVariable` ENUM('Type I error','Statistical Test'),
  `tablePowerCurveDescription` VARCHAR(45),
  `regressionCoeeficientScaleFactor` FLOAT,
  `variabilityScaleFactor` FLOAT,
  `statisticalTest` VARCHAR(45),
  `typeIError` FLOAT,
  `uuid` BINARY(16) UNIQUE,
  PRIMARY KEY (`id`) ,
  INDEX `fk_study_powerCurveDescription`(`uuid` ASC) ,
  CONSTRAINT `fk_study_powerCurveDescription`
    FOREIGN KEY (`uuid` )
    REFERENCES `studydesigndb`.`STUDY_DESIGN` (`uuid` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- --------------------------------------------------------
-- Table `studydesigndb`.`ALPHA_LIST`
-- --------------------------------------------------------
drop table if exists ALPHA_LIST;
CREATE  TABLE IF NOT EXISTS `studydesigndb`.`ALPHA_LIST` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `uuid` BINARY(16),
  `alpha_value` DOUBLE ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_study_ALPHA_LIST` (`uuid` ASC) ,
  CONSTRAINT `fk_study_ALPHA_LIST`
    FOREIGN KEY (`uuid` )
    REFERENCES `studydesigndb`.`STUDY_DESIGN` (`uuid` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- --------------------------------------------------------
-- Table `studydesigndb`.`BETA_SCALE_LIST`
-- --------------------------------------------------------
drop table if exists BETA_SCALE_LIST;
CREATE  TABLE IF NOT EXISTS `studydesigndb`.`BETA_SCALE_LIST` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `uuid` BINARY(16),
  `beta_value` DOUBLE ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_study_BETA_SCALE_LIST` (`uuid` ASC) ,
  CONSTRAINT `fk_study_BETA_SCALE_LIST`
    FOREIGN KEY (`uuid` )
    REFERENCES `studydesigndb`.`STUDY_DESIGN` (`uuid` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- --------------------------------------------------------
-- Table `studydesigndb`.`SIGMA_SCALE_LIST`
-- --------------------------------------------------------
drop table if exists SIGMA_SCALE_LIST;
CREATE  TABLE IF NOT EXISTS `studydesigndb`.`SIGMA_SCALE_LIST` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `uuid` BINARY(16),
  `sigma_scale_value` DOUBLE ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_study_SIGMA_SCALE_LIST` (`uuid` ASC) ,
  CONSTRAINT `fk_study_SIGMA_SCALE_LIST`
    FOREIGN KEY (`uuid` )
    REFERENCES `studydesigndb`.`STUDY_DESIGN` (`uuid` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- --------------------------------------------------------
-- Table `studydesigndb`.`QUANTILE_LIST`
-- --------------------------------------------------------
drop table if exists QUANTILE_LIST;
CREATE  TABLE IF NOT EXISTS `studydesigndb`.`QUANTILE_LIST` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `uuid` BINARY(16),
  `quantile_value` DOUBLE ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_study_QUANTILE_LIST` (`uuid` ASC) ,
  CONSTRAINT `fk_study_QUANTILE_LIST`
    FOREIGN KEY (`uuid` )
    REFERENCES `studydesigndb`.`STUDY_DESIGN` (`uuid` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- --------------------------------------------------------
-- Table `studydesigndb`.`RELATIVE_GROUP_SIZE_LIST`
-- --------------------------------------------------------
drop table if exists RELATIVE_GROUP_SIZE_LIST;
CREATE  TABLE IF NOT EXISTS `studydesigndb`.`RELATIVE_GROUP_SIZE_LIST` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `uuid` BINARY(16),
  `relative_group_size_value` INT ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_study_RELATIVE_GROUP_SIZE_LIST` (`uuid` ASC) ,
  CONSTRAINT `fk_study_RELATIVE_GROUP_SIZE_LIST`
    FOREIGN KEY (`uuid` )
    REFERENCES `studydesigndb`.`STUDY_DESIGN` (`uuid` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- --------------------------------------------------------
-- Table `studydesigndb`.`NOMINAL_POWER_LIST`
-- --------------------------------------------------------
drop table if exists NOMINAL_POWER_LIST;
CREATE  TABLE IF NOT EXISTS `studydesigndb`.`NOMINAL_POWER_LIST` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `uuid` BINARY(16),
  `nominal_power_value` DOUBLE ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_study_NOMINAL_POWER_LIST` (`uuid` ASC) ,
  CONSTRAINT `fk_study_NOMINAL_POWER_LIST`
    FOREIGN KEY (`uuid` )
    REFERENCES `studydesigndb`.`STUDY_DESIGN` (`uuid` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- --------------------------------------------------------
-- Table `studydesigndb`.`TEST_LIST`
-- --------------------------------------------------------
drop table if exists TEST_LIST;
CREATE  TABLE IF NOT EXISTS `studydesigndb`.`TEST_LIST` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `uuid` BINARY(16),
  `name` VARCHAR(45) ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_study_TEST_LIST` (`uuid` ASC) ,
  CONSTRAINT `fk_study_TEST_LIST`
    FOREIGN KEY (`uuid` )
    REFERENCES `studydesigndb`.`STUDY_DESIGN` (`uuid` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- --------------------------------------------------------
-- Table `studydesigndb`.`POWER_METHOD_LIST`
-- --------------------------------------------------------
drop table if exists POWER_METHOD_LIST;
CREATE  TABLE IF NOT EXISTS `studydesigndb`.`POWER_METHOD_LIST` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `uuid` BINARY(16),
  `method` ENUM('Conditional' , 'Un Conditional' , 'Quantile'),
  PRIMARY KEY (`id`) ,
  INDEX `fk_study_POWER_METHOD_LIST` (`uuid` ASC) ,
  CONSTRAINT `fk_study_POWER_METHOD_LIST`
    FOREIGN KEY (`uuid` )
    REFERENCES `studydesigndb`.`STUDY_DESIGN` (`uuid` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


# Turn foreign key checks back on
set foreign_key_checks = 1;