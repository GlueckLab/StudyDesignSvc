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

/**
 * Convenience class for study design service constants
 * 
 * @author Uttara Sakhadeo
 */
public class StudyDesignConstants {
	// current version
	public static final String VERSION = "1.0.0";

	// XML tag names
	public static final String TAG_ERROR = "error";
	public static final String TAG_STUDY_DESIGN = "study";
	public static final String TAG_VERBOSE_STUDY_DESIGN = "verbose";
	public static final String TAG_LIST_ELEMENT = "v";
	public static final String TAG_LIST_NAME = "listName";
	public static final String TAG_MATRIX_NAME = "matrixName";
	public static final String TAG_STUDY_UUID = "studyUUID";

	// Application List Names
	public static final String TAG_ALPHA_LIST = "alphaList";
	public static final String TAG_RELATIVE_GROUP_SIZE_LIST = "relativeGroupSizeList";
	public static final String TAG_SAMPLE_SIZE_LIST = "sampleSizeList";
	public static final String TAG_RESPONSE_LIST = "responseList";
	public static final String TAG_BETA_SCALE_LIST = "betaScaleList";
	public static final String TAG_SIGMA_SCALE_LIST = "sigmaScaleList";
	public static final String TAG_TEST_LIST = "testList";
	public static final String TAG_POWER_METHOD_LIST = "powerMethodList";
	public static final String TAG_QUANTILE_LIST = "quantileList";

	public static final String TAG_All_LISTS = "allLists";
	public static final String TAG_All_MATRICES = "allMatrices";

	// URL mapper tags
	public static final String TAG_LIST = "list";
	public static final String TAG_CONFIDENCE_INTERVAL = "confidenceInterval";
	public static final String TAG_MATRIX = "matrix";
	public static final String TAG_PREDICTOR_LIST = "predictorList";
	public static final String TAG_PREDICTOR = "predictor";
	public static final String TAG_CATEGORY_LIST = "categoryList";
	public static final String TAG_CLUSTERING = "clustering";
	public static final String TAG_CLUSTER = "cluster";
	public static final String TAG_SAMPLE = "sample";
	public static final String TAG_REPEATEDMEASURES = "repeatedMeasures";
	public static final String TAG_DIMENSION = "dimension";
	public static final String TAG_SPACING = "spacing";

	// XML attribute names
	public static final String ATTR_NAME = "name";
	public static final String ATTR_SIZE = "size";
	public static final String ATTR_TYPE = "type";
	public static final String ATTR_COUNT = "count";
	// public static final String ATTR_TYPE = "type";

	// Database connection constants
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