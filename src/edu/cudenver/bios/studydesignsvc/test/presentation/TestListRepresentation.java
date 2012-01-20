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
import edu.cudenver.bios.studydesignsvc.application.StudyDesignConstants;
import edu.cudenver.bios.studydesignsvc.domain.NamedList;
import edu.cudenver.bios.studydesignsvc.representation.ListXMLRepresentation;

/**
 * Unit test for parsing of outgoing representation of a study design.
 * 
 * @author Uttara Sakhadeo
 */
public class TestListRepresentation extends TestCase 
{
	private NamedList namedList = new NamedList();
	
	private static final String expectedXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"+
	"<"+StudyDesignConstants.TAG_ALPHA_LIST+">"+
		"<"+StudyDesignConstants.TAG_LIST_ELEMENT+">0.5"+
		"</"+StudyDesignConstants.TAG_LIST_ELEMENT+">"+
	"</"+StudyDesignConstants.TAG_ALPHA_LIST+">";
	
	/**
	 * Create a List
	 */
    public void setUp()
    {    	
		namedList.setName(StudyDesignConstants.TAG_ALPHA_LIST);
		namedList.addEntry(""+0.5);    	
    }

    /**
     * Test that the representation matches the expected XML string above.
     */
    public void testListXMLRepresentation()
    {
        try
        {        	
        	ListXMLRepresentation rep = new ListXMLRepresentation(namedList);        	
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
