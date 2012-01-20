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
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import edu.cudenver.bios.studydesignsvc.application.StudyDesignConstants;
import edu.cudenver.bios.studydesignsvc.domain.BetweenSubjectEffect;
import edu.cudenver.bios.studydesignsvc.representation.BetweenSubEffectXMLRepresentation;

/**
 * Unit test for parsing of outgoing representation of a study design.
 * 
 * @author Uttara Sakhadeo
 */
public class TestBetweenSubEffectRepresentation extends TestCase 
{
	private List<BetweenSubjectEffect> list= new ArrayList<BetweenSubjectEffect>();
	
	private static final String expectedXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"+
	"<"+StudyDesignConstants.TAG_PREDICTOR_LIST+">"+
		"<"+StudyDesignConstants.TAG_PREDICTOR+" "+StudyDesignConstants.ATTR_NAME+"=\"Gender\">"+
			"<"+StudyDesignConstants.TAG_CATEGORY_LIST+">" +
					"<"+StudyDesignConstants.TAG_LIST_ELEMENT+">"+"M"+"</"+StudyDesignConstants.TAG_LIST_ELEMENT+">"+
					"<"+StudyDesignConstants.TAG_LIST_ELEMENT+">"+"F"+"</"+StudyDesignConstants.TAG_LIST_ELEMENT+">"+
			"</"+StudyDesignConstants.TAG_CATEGORY_LIST+">"+
		"</"+StudyDesignConstants.TAG_PREDICTOR+">"+
		"<"+StudyDesignConstants.TAG_PREDICTOR+" "+StudyDesignConstants.ATTR_NAME+"=\"Group\">"+
		"<"+StudyDesignConstants.TAG_CATEGORY_LIST+">" +
				"<"+StudyDesignConstants.TAG_LIST_ELEMENT+">"+"Treatment"+"</"+StudyDesignConstants.TAG_LIST_ELEMENT+">"+
				"<"+StudyDesignConstants.TAG_LIST_ELEMENT+">"+"Control"+"</"+StudyDesignConstants.TAG_LIST_ELEMENT+">"+
		"</"+StudyDesignConstants.TAG_CATEGORY_LIST+">"+
	"</"+StudyDesignConstants.TAG_PREDICTOR+">"+
	"</"+StudyDesignConstants.TAG_PREDICTOR_LIST+">";
	
	/**
	 * Create some List
	 */
    public void setUp()
    {    	
    	List<String> genderCList = new ArrayList<String>();
    	genderCList.add("M");
    	genderCList.add("F");
    	BetweenSubjectEffect gender = new BetweenSubjectEffect("Gender", genderCList);    	
    	
    	List<String> groupCList = new ArrayList<String>();
    	groupCList.add("Treatment");
    	groupCList.add("Control");
    	BetweenSubjectEffect group = new BetweenSubjectEffect("Group", groupCList);
    	
    	list.add(gender);
    	list.add(group);
    }

    /**
     * Test that the representation matches the expected XML string above.
     */
    public void testListXMLRepresentation()
    {
        try
        {        	
        	BetweenSubEffectXMLRepresentation rep = new BetweenSubEffectXMLRepresentation(list);        	
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
