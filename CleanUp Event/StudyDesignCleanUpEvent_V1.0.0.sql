-- deleting records FROM each table iff month < current_month -1
DROP event if EXISTS myevent;
delimiter |
CREATE EVENT myevent
    ON SCHEDULE EVERY 5 SECOND
    DO
    BEGIN
	    START TRANSACTION;
	-- (1b) Power Curve Description
		DELETE FROM sample_studydesigndb.power_curve_description WHERE id IN 
		(SELECT power_curve_map.id FROM study_power_curve_map AS power_curve_map WHERE power_curve_map.uuid IN
			(SELECT study.uuid FROM study_design AS study WHERE study.uuid = power_curve_map.uuid AND
			study.creation_date < DATE_SUB(CURDATE(), INTERVAL 1 MONTH)
			)
		);
	-- (1c) Confidence Interval Description
		DELETE FROM sample_studydesigndb.confidence_interval WHERE id IN 
		(SELECT confidence_map.id FROM study_confidence_map AS confidence_map WHERE confidence_map.uuid IN
			(SELECT study.uuid FROM study_design AS study WHERE study.uuid = confidence_map.uuid AND
			study.creation_date < DATE_SUB(CURDATE(), INTERVAL 1 MONTH)
			)
		);
	-- (1a) Lists
		-- 1. Alpha List
		DELETE FROM sample_studydesigndb.alpha_list WHERE id IN 
		(SELECT alpha_map.id FROM study_alpha_map AS alpha_map WHERE alpha_map.uuid IN
			(SELECT study.uuid FROM study_design AS study WHERE study.uuid = alpha_map.uuid AND
			study.creation_date < DATE_SUB(CURDATE(), INTERVAL 1 MONTH)
			)
		);			
	    -- 2. Beta Scale List
	    DELETE FROM sample_studydesigndb.beta_scale_list WHERE id IN 
		(SELECT beta_map.id FROM study_beta_map AS beta_map WHERE beta_map.uuid IN
			(SELECT study.uuid FROM study_design AS study WHERE study.uuid = beta_map.uuid AND
			study.creation_date < DATE_SUB(CURDATE(), INTERVAL 1 MONTH)
			)
		);	  		
		-- 3. Nominal Power List
		DELETE FROM sample_studydesigndb.nominal_power_list WHERE id IN 
		(SELECT nominal_map.id FROM study_nominal_power_map AS nominal_map WHERE nominal_map.uuid IN
			(SELECT study.uuid FROM study_design AS study WHERE study.uuid = nominal_map.uuid AND
			study.creation_date < DATE_SUB(CURDATE(), INTERVAL 1 MONTH)
			)
		);
		-- 4. Power Method List
		DELETE FROM sample_studydesigndb.power_method_list  WHERE id IN 
		(SELECT power_method_map.id FROM study_power_method_map AS power_method_map WHERE power_method_map.uuid IN
			(SELECT study.uuid FROM study_design AS study WHERE study.uuid = power_method_map.uuid AND
			study.creation_date < DATE_SUB(CURDATE(), INTERVAL 1 MONTH)
			)
		);
		-- 5. Quantile List
		DELETE FROM sample_studydesigndb.quantile_list WHERE id IN 
		(SELECT quantile_map.id FROM study_quantile_map AS quantile_map WHERE quantile_map.uuid IN
			(SELECT study.uuid FROM study_design AS study WHERE study.uuid = quantile_map.uuid AND
			study.creation_date < DATE_SUB(CURDATE(), INTERVAL 1 MONTH)
			)
		);
		-- 6. Relative Group Size List
		DELETE FROM sample_studydesigndb.relative_group_size_list WHERE id IN 
		(SELECT relative_map.id FROM study_relative_group_size_map AS relative_map WHERE relative_map.uuid IN
			(SELECT study.uuid FROM study_design AS study WHERE study.uuid = relative_map.uuid AND
			study.creation_date < DATE_SUB(CURDATE(), INTERVAL 1 MONTH)
			)
		);
		-- 7. Sample Size List
		DELETE FROM sample_studydesigndb.sample_size_list WHERE id IN 
		(SELECT sample_map.id FROM study_sample_size_map AS SAMPLE_map WHERE sample_map.uuid IN
			(SELECT study.uuid FROM study_design AS study WHERE study.uuid = sample_map.uuid AND
			study.creation_date < DATE_SUB(CURDATE(), INTERVAL 1 MONTH)
			)
		);
		-- 8. Response List
		DELETE FROM sample_studydesigndb.response_list WHERE id IN 
		(SELECT resp_map.id FROM study_response_map AS resp_map WHERE resp_map.uuid IN
			(SELECT study.uuid FROM study_design AS study WHERE study.uuid = resp_map.uuid AND
			study.creation_date < DATE_SUB(CURDATE(), INTERVAL 1 MONTH)
			)
		);
		-- 9. Sigma Scale List
		DELETE FROM sample_studydesigndb.sigma_scale_list WHERE id IN 
		(SELECT sigma_map.id FROM study_sigma_scale_map AS sigma_map WHERE sigma_map.uuid IN
			(SELECT study.uuid FROM study_design AS study WHERE study.uuid = sigma_map.uuid AND
			study.creation_date < DATE_SUB(CURDATE(), INTERVAL 1 MONTH)
			)
		);
		-- 10. Statistical Test List
		DELETE FROM sample_studydesigndb.statistical_test_list WHERE id IN 
		(SELECT test_map.id FROM study_statistical_test_map AS test_map WHERE test_map.uuid IN
			(SELECT study.uuid FROM study_design AS study WHERE study.uuid = test_map.uuid AND
			study.creation_date < DATE_SUB(CURDATE(), INTERVAL 1 MONTH)
			)
		);
	-- (2) Clustering
		DELETE FROM sample_studydesigndb.cluster_node WHERE id IN 
		(SELECT cluster_map.id FROM study_clustering_map AS cluster_map WHERE cluster_map.uuid IN
			(SELECT study.uuid FROM study_design AS study WHERE study.uuid = cluster_map.uuid AND
			study.creation_date < DATE_SUB(CURDATE(), INTERVAL 1 MONTH)
			)
		);
	-- (3) Matrix
		DELETE FROM sample_studydesigndb.matrix WHERE id IN 
		(SELECT matrix_map.id FROM study_matrix_map AS matrix_map WHERE matrix_map.uuid IN
			(SELECT study.uuid FROM study_design AS study WHERE study.uuid = matrix_map.uuid AND
			study.creation_date < DATE_SUB(CURDATE(), INTERVAL 1 MONTH)
			)
		);
	-- (4) Covariance
		-- Standard Deviation
			DELETE FROM sample_studydesigndb.standard_deviation WHERE id IN 
			(SELECT sd_map.sid FROM covariance_sd_map AS sd_map WHERE sd_map.cid IN
				(SELECT cv.id FROM covariance AS cv WHERE cv.id IN 
					(SELECT covariance_map.id FROM study_covariance_map AS covariance_map WHERE covariance_map.uuid IN
						(SELECT study.uuid FROM study_design AS study WHERE study.uuid = covariance_map.uuid AND
						study.creation_date < DATE_SUB(CURDATE(), INTERVAL 1 MONTH)
						)
					)
				)
			);
		DELETE FROM sample_studydesigndb.covariance WHERE id IN 
		(SELECT covariance_mp.id FROM study_covariance_map AS covariance_mp WHERE covariance_mp.uuid IN
			(SELECT study.uuid FROM study_design AS study WHERE study.uuid = covariance_mp.uuid AND
			study.creation_date < DATE_SUB(CURDATE(), INTERVAL 1 MONTH)
			)
		);
		
	-- (5) Between Participant Factors
		-- Category Node
			DELETE FROM sample_studydesigndb.category_node WHERE id IN 
			(SELECT category_map.categoryId FROM between_participant_effects_category_map AS category_map WHERE category_map.id IN
				(SELECT bpe.id FROM between_participant_effects AS bpe WHERE bpe.id IN 
					(SELECT bpe_map.id FROM study_between_participant_effects_map AS bpe_map WHERE bpe_map.uuid IN
						(SELECT study.uuid FROM study_design AS study WHERE study.uuid = bpe_map.uuid AND
						study.creation_date < DATE_SUB(CURDATE(), INTERVAL 1 MONTH)
						)
					)
				)
			);
		DELETE FROM sample_studydesigndb.between_participant_effects WHERE id IN 
		(SELECT effects_map.id FROM study_between_participant_effects_map AS effects_map WHERE effects_map.uuid IN
			(SELECT study.uuid FROM study_design AS study WHERE study.uuid = effects_map.uuid AND
			study.creation_date < DATE_SUB(CURDATE(), INTERVAL 1 MONTH)
			)
		);
	-- (6) Repeated Measures
		-- Spacing List
			DELETE FROM sample_studydesigndb.spacing_list WHERE id IN 
			(SELECT spacing_map.spacingId FROM repeated_measures_spacing_map AS spacing_map WHERE spacing_map.id IN
				(SELECT rm.id FROM repeated_measures AS rm WHERE rm.id IN 
					(SELECT rm_map.id FROM study_repeated_measures_map AS rm_map WHERE rm_map.uuid IN
						(SELECT study.uuid FROM study_design AS study WHERE study.uuid = rm_map.uuid AND
						study.creation_date < DATE_SUB(CURDATE(), INTERVAL 1 MONTH)
						)
					)
				)
			);
		DELETE FROM sample_studydesigndb.repeated_measures WHERE id IN 
		(SELECT measures_map.id FROM study_repeated_measures_map AS measures_map WHERE measures_map.uuid IN
			(SELECT study.uuid FROM study_design AS study WHERE study.uuid = measures_map.uuid AND
			study.creation_date < DATE_SUB(CURDATE(), INTERVAL 1 MONTH)
			)
		);
	-- (7) Hypothesis
		DELETE FROM sample_studydesigndb.hypothesis WHERE id IN 
		(SELECT hypothesis_map.id FROM study_hypothesis_map AS hypothesis_map WHERE hypothesis_map.uuid IN
			(SELECT study.uuid FROM study_design AS study WHERE study.uuid = hypothesis_map.uuid AND
			study.creation_date < DATE_SUB(CURDATE(), INTERVAL 1 MONTH)
			)
		);
	-- (8) Study Design
		DELETE FROM sample_studydesigndb.study_design WHERE creation_date < DATE_SUB(CURDATE(), INTERVAL 1 MONTH);
COMMIT;
END |

delimiter ;
