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
package edu.cudenver.bios.studydesignsvc.test.parsing;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import edu.cudenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.cudenver.bios.studydesignsvc.resourcehelper.MatrixParamParser;

/**
 * Unit test for parsing of incoming entity body
 * 
 * @author Uttara Sakhadeo
 */
public class TestMatrixParsing extends TestCase 
{
	private static Logger logger = StudyDesignLogger.getInstance();
	
	// Valid XML for an entity body with a List element
	private Document validDoc = null;    	
	private String validMatrix = "<matrix name='A' rows='3' columns='3'>"+
								    "<r><c>1</c><c>1</c><c>1</c></r>"+
								    "<r><c>2</c><c>2</c><c>2</c></r>"+
								    "<r><c>3</c><c>3</c><c>3</c></r>"+
							     "</matrix>";
	
	 public TestMatrixParsing(final String name) 
    {
        super(name);
    }
    
    /**
     * Convert the above strings into DOM documents
     */
    public void setUp()
    {
        try
        {
            DocumentBuilderFactory factory =  DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            validDoc = builder.parse(new InputSource(new StringReader(validMatrix.toString())));
			//validListDoc1 = builder.parse(new InputSource(new StringReader(validList1.toString())));
			//inValidListDoc = builder.parse(new InputSource(new StringReader(inValidList.toString())));						
			//invalidListDoc1 = builder.parse(new InputSource(new StringReader(inValidList1.toString())));
        }
        catch (Exception e)
        {
            fail();
        }
    }
    
    /**
     * Test parsing of a valid Matrix
     */
    public void testValidMatrix()
    {
        try
        {
        	MatrixParamParser.getPositiveDefiniteParamsFromDomNode(validDoc.getDocumentElement());                 
            assertTrue(true);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception during testValidMatrix(): " + e.getMessage());
        }
    }
}
