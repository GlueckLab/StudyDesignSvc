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
package edu.cudenver.bios.studydesignsvc.representation;

import java.io.IOException;

import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.cudenver.bios.studydesignsvc.application.MatrixConstants;
import edu.cudenver.bios.studydesignsvc.domain.NamedRealMatrix;


public class MatrixXMLRepresentation  extends DomRepresentation
{
	
    /**
     * Create an XML representation of a matrix response
     * entity body.  
     * 
     * @param params is @see NamedRealMatrix, a wrapper for a RealMatrix
     * that allows us to name the matrix element.  
     * @throws IOException
     */
    public MatrixXMLRepresentation(NamedRealMatrix matrix) throws IOException
    {
        super(MediaType.APPLICATION_XML);
        Document doc = getDocument();
        
        Element rootElement = createMatrixNodeFromRealMatrix(doc, matrix);
        doc.appendChild(rootElement);
        doc.normalizeDocument();
    }
    
    public static Element createMatrixNodeFromRealMatrix(Document doc, NamedRealMatrix matrix)
    {
    	Element matrixElement = doc.createElement(MatrixConstants.TAG_MATRIX);
    	
    	// set the 'columns' attribute    	
    	matrixElement.setAttribute(MatrixConstants.ATTR_COLUMNS, Integer.toString( matrix.getColumnDimension() ) );
    	    	
    	//set the 'name' attribute to the correct name...
    	matrixElement.setAttribute(MatrixConstants.ATTR_NAME, matrix.getName() );
    	
    	// set the 'rows' attribute
    	matrixElement.setAttribute(MatrixConstants.ATTR_ROWS, Integer.toString( matrix.getRowDimension() ) );    	    
    	
		// loop through the rows in the matrix
    	for(int rowNum = 0; rowNum < matrix.getRowDimension(); rowNum++){
    		// create row tag
    		Element rowNode = doc.createElement(MatrixConstants.TAG_ROW);
    		matrixElement.appendChild(rowNode);
    		
    		// loop through the columns
    	    for(int colNum = 0; colNum < matrix.getColumnDimension(); colNum++){
    	    	//extract the elements using the row index and column index
    	    	//and put them in column tags
    	    	Element colNode = doc.createElement(MatrixConstants.TAG_COLUMN);
    	    	rowNode.appendChild(colNode);
    	    	colNode.setTextContent(Double.toString( matrix.getEntry(rowNum, colNum)));
    	    	
    	    }
    	}
    	return matrixElement;
    }
}
