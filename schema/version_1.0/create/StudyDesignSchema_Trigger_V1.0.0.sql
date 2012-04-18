--------------------------------------------------------------------------------
delimiter $$

DROP trigger `studydesigndb`.`delete_Study` $$

CREATE trigger `studydesigndb`.`delete_Study`
	AFTER DELETE ON `studydesigndb`.`STUDY_DESIGN`
	FOR EACH ROW
BEGIN
SET @olduuid = OLD.uuid;
-- LISTS	
	-- 1. BETA_SCALE_LIST
	DELETE FROM `studydesigndb`.`BETA_SCALE_LIST`
	WHERE
		`BETA_SCALE_LIST`.id =
		(
			SELECT `STUDY_BETA_MAP`.id 
			FROM `STUDY_BETA_MAP` INNER JOIN `STUDY_DESIGN`
			ON 	@olduuid = `STUDY_BETA_MAP`.uuid
		);
	-- 2. ALPHA_LIST
	DELETE FROM `studydesigndb`.`ALPHA_LIST`	
	WHERE
		`ALPHA_LIST`.id =
		(
			SELECT `STUDY_ALPHA_MAP`.id 
			FROM `STUDY_ALPHA_MAP` INNER JOIN `STUDY_DESIGN`
			ON 	@olduuid =`STUDY_ALPHA_MAP`.uuid
		);
	-- 3. SIGMA_SCALE_LIST
	DELETE FROM `studydesigndb`.`SIGMA_SCALE_LIST`	
	WHERE
		`SIGMA_SCALE_LIST`.id =
		(
			SELECT `STUDY_SIGMA_SCALE_MAP`.id 
			FROM `STUDY_SIGMA_SCALE_MAP` INNER JOIN `STUDY_DESIGN`
			ON 	@olduuid =`STUDY_SIGMA_SCALE_MAP`.uuid
		);
	-- 4. QUANTILE_LIST
	DELETE FROM `studydesigndb`.`QUANTILE_LIST`	
	WHERE
		`QUANTILE_LIST`.id =
		(
			SELECT `STUDY_QUANTILE_MAP`.id 
			FROM `STUDY_QUANTILE_MAP` INNER JOIN `STUDY_DESIGN`
			ON 	@olduuid =`STUDY_QUANTILE_MAP`.uuid
		);
	-- 5. POWER_METHOD_LIST
	DELETE FROM `studydesigndb`.`POWER_METHOD_LIST`	
	WHERE
		`POWER_METHOD_LIST`.id =
		(
			SELECT `STUDY_POWER_METHOD_MAP`.id 
			FROM `STUDY_POWER_METHOD_MAP` INNER JOIN `STUDY_DESIGN`
			ON 	@olduuid =`STUDY_POWER_METHOD_MAP`.uuid
		);
	-- 6. NOMINAL_POWER_LIST
	DELETE FROM `studydesigndb`.`NOMINAL_POWER_LIST`	
	WHERE
		`NOMINAL_POWER_LIST`.id =
		(
			SELECT `STUDY_NOMINAL_POWER_MAP`.id 
			FROM `STUDY_NOMINAL_POWER_MAP` INNER JOIN `STUDY_DESIGN`
			ON 	@olduuid =`STUDY_NOMINAL_POWER_MAP`.uuid
		);
	-- 7. RELATIVE_GROUP_SIZE_LIST
	DELETE FROM `studydesigndb`.`RELATIVE_GROUP_SIZE_LIST`	
	WHERE
		`RELATIVE_GROUP_SIZE_LIST`.id =
		(
			SELECT `STUDY_RELATIVE_GROUP_SIZE_MAP`.id 
			FROM `STUDY_RELATIVE_GROUP_SIZE_MAP` INNER JOIN `STUDY_DESIGN`
			ON 	@olduuid =`STUDY_RELATIVE_GROUP_SIZE_MAP`.uuid
		);
	-- 8. RESPONSE_LIST
	DELETE FROM `studydesigndb`.`RESPONSE_LIST`	
	WHERE
		`RESPONSE_LIST`.id =
		(
			SELECT `STUDY_RESPONSE_MAP`.id 
			FROM `STUDY_RESPONSE_MAP` INNER JOIN `STUDY_DESIGN`
			ON 	@olduuid =`STUDY_RESPONSE_MAP`.uuid
		);
	-- 9. SAMPLE_SIZE_LIST
	DELETE FROM `studydesigndb`.`SAMPLE_SIZE_LIST`	
	WHERE
		`SAMPLE_SIZE_LIST`.id =
		(
			SELECT `STUDY_SAMPLE_SIZE_MAP`.id 
			FROM `STUDY_SAMPLE_SIZE_MAP` INNER JOIN `STUDY_DESIGN`
			ON 	@olduuid =`STUDY_SAMPLE_SIZE_MAP`.uuid
		);
	-- 10.  STATISTICAL_TEST_LIST
	DELETE FROM `studydesigndb`.`STATISTICAL_TEST_LIST`	
	WHERE
		`STATISTICAL_TEST_LIST`.id =
		(
			SELECT `STUDY_STATISTICAL_TEST_MAP`.id 
			FROM `STUDY_STATISTICAL_TEST_MAP` INNER JOIN `STUDY_DESIGN`
			ON 	@olduuid =`STUDY_STATISTICAL_TEST_MAP`.uuid
		);
-- Clustering
-- Hypothesis
-- Between Participant Factor
-- Repeated Measures
END$$
delimiter ;


-------------------------------------------------------------------------------		
