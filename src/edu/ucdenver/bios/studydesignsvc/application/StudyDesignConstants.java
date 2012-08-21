/*
 * Study Design Service for the GLIMMPSE Software System.  
 * This service stores study design definitions for users of the GLIMMSE interface.
 * Service contain all information related to a power or sample size calculation.  
 * The Study Design Service simplifies communication between different screens in the user interface.
 * 
 * Copyright (C) 2010 Regents of the University of Colorado.  
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package edu.ucdenver.bios.studydesignsvc.application;

// TODO: Auto-generated Javadoc
/**
 * Convenience class for study design service constants.
 *
 * @author Uttara Sakhadeo
 */
public class StudyDesignConstants {

	// XML tag names
	/** The Constant TAG_ERROR. */
	public static final String TAG_ERROR = "error";
	
	/** The Constant TAG_STUDY_DESIGN. */
	public static final String TAG_STUDY_DESIGN = "study";
	
	/** The Constant TAG_VERBOSE_STUDY_DESIGN. */
	public static final String TAG_VERBOSE_STUDY_DESIGN = "verbose";
	
	/** The Constant TAG_LIST_ELEMENT. */
	public static final String TAG_LIST_ELEMENT = "v";
	
	/** The Constant TAG_LIST_NAME. */
	public static final String TAG_LIST_NAME = "listName";
	
	/** The Constant TAG_MATRIX_NAME. */
	public static final String TAG_MATRIX_NAME = "matrixName";
	
	/** The Constant TAG_STUDY_UUID. */
	public static final String TAG_STUDY_UUID = "studyUUID";

	// Application List Names
	/** The Constant TAG_ALPHA_LIST. */
	public static final String TAG_ALPHA_LIST = "alphaList";
	
	/** The Constant TAG_RELATIVE_GROUP_SIZE_LIST. */
	public static final String TAG_RELATIVE_GROUP_SIZE_LIST = "relativeGroupSizeList";
	
	/** The Constant TAG_SAMPLE_SIZE_LIST. */
	public static final String TAG_SAMPLE_SIZE_LIST = "sampleSizeList";
	
	/** The Constant TAG_RESPONSE_LIST. */
	public static final String TAG_RESPONSE_LIST = "responseList";
	
	/** The Constant TAG_BETA_SCALE_LIST. */
	public static final String TAG_BETA_SCALE_LIST = "betaScaleList";
	
	/** The Constant TAG_SIGMA_SCALE_LIST. */
	public static final String TAG_SIGMA_SCALE_LIST = "sigmaScaleList";
	
	/** The Constant TAG_TEST_LIST. */
	public static final String TAG_TEST_LIST = "testList";
	
	/** The Constant TAG_POWER_METHOD_LIST. */
	public static final String TAG_POWER_METHOD_LIST = "powerMethodList";
	
	/** The Constant TAG_QUANTILE_LIST. */
	public static final String TAG_QUANTILE_LIST = "quantileList";
	
	/** The Constant TAG_NOMINAL_POWER_LIST. */
	public static final String TAG_NOMINAL_POWER_LIST = "nominalPowerList";
	
	public static final String TAG_CONFIDENCE_INTERVAL_DESCRIPTION = "confidenceIntervalDescription";

	/** The Constant TAG_All_LISTS. */
	public static final String TAG_All_LISTS = "allLists";
	
	/** The Constant TAG_All_MATRICES. */
	public static final String TAG_All_MATRICES = "allMatrices";

	// URL mapper tags
	/** The Constant TAG_LIST. */
	public static final String TAG_LIST = "list";
	
	/** The Constant TAG_CONFIDENCE_INTERVAL_DESCRIPTION. *//*
	public static final String TAG_CONFIDENCE_INTERVAL_DESCRIPTION = "confidenceIntervalDescription";
	*/
	/** The Constant TAG_POWER_CURVE_DESCRIPTION. */
	public static final String TAG_POWER_CURVE_DESCRIPTION = "powerCurveDescription";
	
	/** The Constant TAG_MATRIX. */
	public static final String TAG_MATRIX = "matrix";
	
	/** The Constant TAG_BETWEEN_PARTICIPANT_FACTOR. */
	public static final String TAG_BETWEEN_PARTICIPANT_FACTOR = "betweenParticipantFactor";
	
	/** The Constant TAG_PREDICTOR_LIST. */
	public static final String TAG_PREDICTOR_LIST = "predictorList";
	
	/** The Constant TAG_PREDICTOR. */
	public static final String TAG_PREDICTOR = "predictor";
	
	/** The Constant TAG_CATEGORY_LIST. */
	public static final String TAG_CATEGORY_LIST = "categoryList";
	
	/** The Constant TAG_CLUSTERING. */
	public static final String TAG_CLUSTERING = "clustering";
	
	/** The Constant TAG_CLUSTER. */
	public static final String TAG_CLUSTER = "cluster";
	
	/** The Constant TAG_CLUSTER. */
    public static final String TAG_COVARIANCE = "covariance";
	
	/** The Constant TAG_SAMPLE. */
	public static final String TAG_SAMPLE = "sample";
	
	/** The Constant TAG_REPEATEDMEASURES. */
	public static final String TAG_REPEATEDMEASURES = "repeatedMeasures";
	
	/** The Constant TAG_DIMENSION. */
	public static final String TAG_DIMENSION = "dimension";
	
	/** The Constant TAG_SPACING. */
	public static final String TAG_SPACING = "spacing";
	
	/** The Constant TAG_HYPOTHESIS. */
	public static final String TAG_HYPOTHESIS = "hypothesis";

	// XML attribute names
	/** The Constant ATTR_NAME. */
	public static final String ATTR_NAME = "name";
	
	/** The Constant ATTR_SIZE. */
	public static final String ATTR_SIZE = "size";
	
	/** The Constant ATTR_TYPE. */
	public static final String ATTR_TYPE = "type";
	
	/** The Constant ATTR_COUNT. */
	public static final String ATTR_COUNT = "count";
	// public static final String ATTR_TYPE = "type";

	// Database connection constants
	/** The Constant DATABASE. */
	public static final String DATABASE = "mydb";
	/*
	 * public static final String ; public static final String ; public static
	 * final String ;
	 */

	// Java Constants
	/*
	 * public static final String CONST_DOUBLE = "double"; public static final
	 * String CONST_STRING = "string"; public static final String CONST_INTEGER
	 * = "integer";
	 */

	//
}