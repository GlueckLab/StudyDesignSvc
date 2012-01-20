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

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import edu.cudenver.bios.studydesignsvc.application.StudyDesignConstants;
import edu.cudenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.cudenver.bios.studydesignsvc.resourcehelper.ListResourceHelper;
import edu.cudenver.bios.studydesignsvc.resourcehelper.RepeatedMeasuresResourceHelper;
import junit.framework.TestCase;

/**
 * Unit test for parsing of incoming entity body
 * 
 * @author Uttara Sakhadeo
 */
public class TestRepeatedMeasuresParsing extends TestCase  
{
	private static Logger logger = StudyDesignLogger.getInstance();
	
	// Valid XML for an entity body with a RepeatedMeasures element
	private Document validDataDoc = null;    	
	private String validData = "<"+StudyDesignConstants.TAG_REPEATEDMEASURES+">"+
									"<"+StudyDesignConstants.TAG_DIMENSION+" "+StudyDesignConstants.ATTR_NAME+"=\"time\" "+StudyDesignConstants.ATTR_TYPE+" =\"numeric\" "+
									StudyDesignConstants.ATTR_COUNT+" =\"3\">"+
										"<"+StudyDesignConstants.TAG_SPACING+">"+
										   "<"+StudyDesignConstants.TAG_LIST_ELEMENT+">"+"\"5\""+"</"+StudyDesignConstants.TAG_LIST_ELEMENT+">"+
										   "<"+StudyDesignConstants.TAG_LIST_ELEMENT+">"+"\"10\""+"</"+StudyDesignConstants.TAG_LIST_ELEMENT+">"+	
										"</"+StudyDesignConstants.TAG_SPACING+">"+
										"<"+StudyDesignConstants.TAG_DIMENSION+" "+StudyDesignConstants.ATTR_NAME+"=\"timeOfDay\" "+StudyDesignConstants.ATTR_TYPE+" =\"numeric\" "+
											StudyDesignConstants.ATTR_COUNT+" =\"2\">"+
											"<"+StudyDesignConstants.TAG_DIMENSION+" "+StudyDesignConstants.ATTR_NAME+"=\"bodyPosition\" "+StudyDesignConstants.ATTR_TYPE+" =\"categorical\" "+
											StudyDesignConstants.ATTR_COUNT+" =\"3\"/>"+
										"</"+StudyDesignConstants.TAG_DIMENSION+">"+
									"</"+StudyDesignConstants.TAG_DIMENSION+">"+
							   "</"+StudyDesignConstants.TAG_REPEATEDMEASURES+">";
	
	public TestRepeatedMeasuresParsing(final String name) 
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
            
            validDataDoc = builder.parse(new InputSource(new StringReader(validData.toString())));			
        }
        catch (Exception e)
        {
            fail();
        }
    }
    
    /**
     * Test parsing of a valid RepeatedMeasures element
     */
    public void testValidData()
    {
        try
        {
        	RepeatedMeasuresResourceHelper.parseRepeatedMeasures(validDataDoc.getDocumentElement());                      
            assertTrue(true);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception during testValidMatrix(): " + e.getMessage());
        }
    }
}
