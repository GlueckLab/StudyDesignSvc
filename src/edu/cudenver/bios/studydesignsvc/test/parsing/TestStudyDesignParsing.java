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

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import edu.cudenver.bios.studydesignsvc.application.StudyDesignConstants;
import edu.cudenver.bios.studydesignsvc.application.StudyDesignLogger;
import junit.framework.TestCase;

/**
 * Unit test for parsing of incoming entity body
 * 
 * @author Uttara Sakhadeo
 */
public class TestStudyDesignParsing extends TestCase  
{
	// what should be the display sequence of ? lists ... matrices ....
	
	private static Logger logger = StudyDesignLogger.getInstance();
	
	// Valid XML for an entity body with a List element
	private Document validDoc = null;    	
	private String validList = "<"+StudyDesignConstants.TAG_ALPHA_LIST+">"+
								    "<"+StudyDesignConstants.TAG_LIST_ELEMENT+">"+"0.5"+
								    "</"+StudyDesignConstants.TAG_LIST_ELEMENT+">"+
								    "<"+StudyDesignConstants.TAG_LIST_ELEMENT+">"+"0.1"+
								    "</"+StudyDesignConstants.TAG_LIST_ELEMENT+">"+	    
							   "</"+StudyDesignConstants.TAG_ALPHA_LIST+">";
	
	
}
