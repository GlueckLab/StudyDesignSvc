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
package edu.cudenver.bios.studydesignsvc.test.presentation;

import java.io.StringWriter;

import junit.framework.TestCase;
import edu.cudenver.bios.studydesignsvc.application.MatrixConstants;
import edu.cudenver.bios.studydesignsvc.domain.NamedRealMatrix;
import edu.cudenver.bios.studydesignsvc.representation.MatrixXMLRepresentation;

/**
 * Unit test for parsing of outgoing representation of a study design.
 * 
 * @author Uttara Sakhadeo
 */
public class TestMatrixRepresentation extends TestCase 
{
	private NamedRealMatrix namedRealMatrix;
	
	private static final String expectedXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"+
		"<"+MatrixConstants.TAG_MATRIX+" "+MatrixConstants.ATTR_COLUMNS+"=\"3\" "+MatrixConstants.ATTR_NAME+"=\"A\" "+MatrixConstants.ATTR_ROWS+"=\"3\">"+
				"<"+MatrixConstants.TAG_ROW+"><"+
					MatrixConstants.TAG_COLUMN+">"+1.0+"</"+MatrixConstants.TAG_COLUMN+"><" +
					MatrixConstants.TAG_COLUMN+">"+2.0+"</"+MatrixConstants.TAG_COLUMN+"><" +
					MatrixConstants.TAG_COLUMN+">"+3.0+"</"+MatrixConstants.TAG_COLUMN+">" +
				"</"+MatrixConstants.TAG_ROW+">"+
				"<"+MatrixConstants.TAG_ROW+"><"+
					MatrixConstants.TAG_COLUMN+">"+1.0+"</"+MatrixConstants.TAG_COLUMN+"><" +
					MatrixConstants.TAG_COLUMN+">"+2.0+"</"+MatrixConstants.TAG_COLUMN+"><" +
					MatrixConstants.TAG_COLUMN+">"+3.0+"</"+MatrixConstants.TAG_COLUMN+">" +
				"</"+MatrixConstants.TAG_ROW+">"+
				"<"+MatrixConstants.TAG_ROW+"><"+
					MatrixConstants.TAG_COLUMN+">"+1.0+"</"+MatrixConstants.TAG_COLUMN+"><" +
					MatrixConstants.TAG_COLUMN+">"+2.0+"</"+MatrixConstants.TAG_COLUMN+"><" +
					MatrixConstants.TAG_COLUMN+">"+3.0+"</"+MatrixConstants.TAG_COLUMN+">" +
				"</"+MatrixConstants.TAG_ROW+">"+
		"</"+MatrixConstants.TAG_MATRIX+">";
	
	/**
	 * Create some Matrix
	 */
    public void setUp()
    {    	
    	namedRealMatrix = new NamedRealMatrix(3,3,"A");    	    	
    	namedRealMatrix.addToEntry(0, 0, Integer.parseInt(""+1));   	
    	namedRealMatrix.addToEntry(0, 1, Integer.parseInt(""+2));
    	namedRealMatrix.addToEntry(0, 2, Integer.parseInt(""+3));
    	namedRealMatrix.addToEntry(1, 0, Integer.parseInt(""+1));
    	namedRealMatrix.addToEntry(1, 1, Integer.parseInt(""+2));
    	namedRealMatrix.addToEntry(1, 2, Integer.parseInt(""+3));
    	namedRealMatrix.addToEntry(2, 0, Integer.parseInt(""+1));
    	namedRealMatrix.addToEntry(2, 1, Integer.parseInt(""+2));
    	namedRealMatrix.addToEntry(2, 2, Integer.parseInt(""+3));
    	//(arg0, arg1, arg2);
    }
    
    /**
     * Test that the representation matches the expected XML string above.
     */
    public void testMatrixXMLRepresentation()
    {
        try
        {        	
        	MatrixXMLRepresentation rep = new MatrixXMLRepresentation(namedRealMatrix);        	
        	StringWriter sw = new StringWriter();        	
        	rep.write(sw);         	
            assertEquals(sw.toString(), expectedXML);
        }
        catch(Exception e)
        {
            System.out.println("Exception during representation test: " + e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

}
