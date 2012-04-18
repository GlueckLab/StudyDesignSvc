/*
 * Power Service for the GLIMMPSE Software System.  Processes
 * incoming HTTP requests for power, sample size, and detectable
 * difference
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
 * This class holds constants for the Matrix Services.
 * 
 * @author Uttara Sakhadeo
 * 
 */
public class MatrixConstants {
	// TODO: make this an interface?
	// TODO: refactor to combine with PowerConstants. Add to one shared matrix
	// jar?

	// XML tag names
	/** The Constant TAG_ERROR. */
	public static final String TAG_ERROR = "error";
	
	/** The Constant TAG_MATRIX_LIST. */
	public static final String TAG_MATRIX_LIST = "matrixList";
	
	/** The Constant TAG_MATRIX. */
	public static final String TAG_MATRIX = "matrix";
	
	/** The Constant TAG_SCALAR_MULT_PARAMETER_LIST. */
	public static final String TAG_SCALAR_MULT_PARAMETER_LIST = "scalarMultiplicationParams";
	
	/** The Constant TAG_SCALAR_MULTIPLIER. */
	public static final String TAG_SCALAR_MULTIPLIER = "scalarMultiplier";
	
	/** The Constant TAG_RANK. */
	public static final String TAG_RANK = "rank";
	
	/** The Constant TAG_TRACE. */
	public static final String TAG_TRACE = "trace";
	
	/** The Constant TAG_POSITIVE_DEFINITE. */
	public static final String TAG_POSITIVE_DEFINITE = "positiveDefinite";
	
	/** The Constant TAG_ROW. */
	public static final String TAG_ROW = "r";
	
	/** The Constant TAG_COLUMN. */
	public static final String TAG_COLUMN = "c";
	
	/** The Constant TAG_SUM. */
	public static final String TAG_SUM = "sum";
	
	/** The Constant TAG_CHOLESKY_DECOMP. */
	public static final String TAG_CHOLESKY_DECOMP = "choleskyDecomposition";
	
	/** The Constant TAG_FACTOR_LIST. */
	public static final String TAG_FACTOR_LIST = "factorList";
	
	/** The Constant TAG_FACTOR. */
	public static final String TAG_FACTOR = "factor";
	
	/** The Constant TAG_ORTHOG_POLY_CONTRAST_LIST. */
	public static final String TAG_ORTHOG_POLY_CONTRAST_LIST = "orthogonalPolynomialContrastList";
	
	/** The Constant TAG_CONTRAST. */
	public static final String TAG_CONTRAST = "contrast";
	
	/** The Constant TAG_V. */
	public static final String TAG_V = "v";

	// XML attribute names
	/** The Constant ATTR_ROWS. */
	public static final String ATTR_ROWS = "rows";
	
	/** The Constant ATTR_COLUMNS. */
	public static final String ATTR_COLUMNS = "columns";
	
	/** The Constant ATTR_VALUE. */
	public static final String ATTR_VALUE = "value";
	
	/** The Constant ATTR_NAME. */
	public static final String ATTR_NAME = "name";
	
	/** The Constant ATTR_TYPE. */
	public static final String ATTR_TYPE = "type";

	// Values of the attribute "type" for <factorList> elements
	/** The Constant BETWEEN. */
	public static final String BETWEEN = "between";
	
	/** The Constant WITHIN. */
	public static final String WITHIN = "within";

	// Values of the attribute "type" for <contrast> elements
	/** The Constant GRAND_MEAN. */
	public static final String GRAND_MEAN = "grandMean";
	
	/** The Constant MAIN_EFFECT. */
	public static final String MAIN_EFFECT = "mainEffect";
	
	/** The Constant INTERACTION. */
	public static final String INTERACTION = "interaction";

	// Matrix Names for Response objects
	/** The Constant ADDITION_MATRIX_RETURN_NAME. */
	public static final String ADDITION_MATRIX_RETURN_NAME = "sum";
	
	/** The Constant SUBTRACTION_MATRIX_RETURN_NAME. */
	public static final String SUBTRACTION_MATRIX_RETURN_NAME = "difference";
	
	/** The Constant MULTIPLICATION_MATRIX_RETURN_NAME. */
	public static final String MULTIPLICATION_MATRIX_RETURN_NAME = "product";
	
	/** The Constant INVERSION_MATRIX_RETURN_NAME. */
	public static final String INVERSION_MATRIX_RETURN_NAME = "inverse";
	
	/** The Constant SQ_ROOT_MATRIX_RETURN_NAME. */
	public static final String SQ_ROOT_MATRIX_RETURN_NAME = "L";
	
	/** The Constant TRANSPOSE_MATRIX_RETURN_NAME. */
	public static final String TRANSPOSE_MATRIX_RETURN_NAME = "lTranspose";
	
	/** The Constant VEC_MATRIX_RETURN_NAME. */
	public static final String VEC_MATRIX_RETURN_NAME = "vec";
	
	/** The Constant VECH_MATRIX_RETURN_NAME. */
	public static final String VECH_MATRIX_RETURN_NAME = "vech";

	/**
	 * This value is used in the Positive Definite calculation. It could be used
	 * as follows: MatrixUtils.isPositiveDefinite( myMatrix,
	 * MatrixConstants.EIGEN_TOLERANCE )
	 */
	public static Double EIGEN_TOLERANCE_DEFAULT = 1.0E-15;
}
