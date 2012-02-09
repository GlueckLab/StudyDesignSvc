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
	public static final String TAG_ERROR = "error";
	public static final String TAG_MATRIX_LIST = "matrixList";
	public static final String TAG_MATRIX = "matrix";
	public static final String TAG_SCALAR_MULT_PARAMETER_LIST = "scalarMultiplicationParams";
	public static final String TAG_SCALAR_MULTIPLIER = "scalarMultiplier";
	public static final String TAG_RANK = "rank";
	public static final String TAG_TRACE = "trace";
	public static final String TAG_POSITIVE_DEFINITE = "positiveDefinite";
	public static final String TAG_ROW = "r";
	public static final String TAG_COLUMN = "c";
	public static final String TAG_SUM = "sum";
	public static final String TAG_CHOLESKY_DECOMP = "choleskyDecomposition";
	public static final String TAG_FACTOR_LIST = "factorList";
	public static final String TAG_FACTOR = "factor";
	public static final String TAG_ORTHOG_POLY_CONTRAST_LIST = "orthogonalPolynomialContrastList";
	public static final String TAG_CONTRAST = "contrast";
	public static final String TAG_V = "v";

	// XML attribute names
	public static final String ATTR_ROWS = "rows";
	public static final String ATTR_COLUMNS = "columns";
	public static final String ATTR_VALUE = "value";
	public static final String ATTR_NAME = "name";
	public static final String ATTR_TYPE = "type";

	// Values of the attribute "type" for <factorList> elements
	public static final String BETWEEN = "between";
	public static final String WITHIN = "within";

	// Values of the attribute "type" for <contrast> elements
	public static final String GRAND_MEAN = "grandMean";
	public static final String MAIN_EFFECT = "mainEffect";
	public static final String INTERACTION = "interaction";

	// Matrix Names for Response objects
	public static final String ADDITION_MATRIX_RETURN_NAME = "sum";
	public static final String SUBTRACTION_MATRIX_RETURN_NAME = "difference";
	public static final String MULTIPLICATION_MATRIX_RETURN_NAME = "product";
	public static final String INVERSION_MATRIX_RETURN_NAME = "inverse";
	public static final String SQ_ROOT_MATRIX_RETURN_NAME = "L";
	public static final String TRANSPOSE_MATRIX_RETURN_NAME = "lTranspose";
	public static final String VEC_MATRIX_RETURN_NAME = "vec";
	public static final String VECH_MATRIX_RETURN_NAME = "vech";

	/**
	 * This value is used in the Positive Definite calculation. It could be used
	 * as follows: MatrixUtils.isPositiveDefinite( myMatrix,
	 * MatrixConstants.EIGEN_TOLERANCE )
	 */
	public static Double EIGEN_TOLERANCE_DEFAULT = 1.0E-15;
}
