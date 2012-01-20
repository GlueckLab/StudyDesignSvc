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

import edu.cudenver.bios.studydesignsvc.application.StudyDesignConstants;
import edu.cudenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.cudenver.bios.studydesignsvc.resourcehelper.ListResourceHelper;

/**
 * Unit test for parsing of incoming entity body
 * 
 * @author Uttara Sakhadeo
 */
public class TestListParsing extends TestCase 
{
	private static Logger logger = StudyDesignLogger.getInstance();
	
	// Valid XML for an entity body with a List element
	private Document validListDoc = null;    	
	private String validList = "<"+StudyDesignConstants.TAG_ALPHA_LIST+">"+
								    "<"+StudyDesignConstants.TAG_LIST_ELEMENT+">"+"0.5"+
								    "</"+StudyDesignConstants.TAG_LIST_ELEMENT+">"+
								    "<"+StudyDesignConstants.TAG_LIST_ELEMENT+">"+"0.1"+
								    "</"+StudyDesignConstants.TAG_LIST_ELEMENT+">"+	    
							   "</"+StudyDesignConstants.TAG_ALPHA_LIST+">";
	
	// Valid XML for an entity body with a List element
	private Document validListDoc1 = null;	
	private String validList1 =  "<"+StudyDesignConstants.TAG_ALPHA_LIST+">"+
							    	"<"+StudyDesignConstants.TAG_LIST_ELEMENT+">"+"Hello"+
								    "</"+StudyDesignConstants.TAG_LIST_ELEMENT+">"+
								 "</"+StudyDesignConstants.TAG_ALPHA_LIST+">";
	
	
	// Invalid XML for an entity body with a List element
	private Document inValidListDoc = null;    	 
	private String inValidList = "<"+StudyDesignConstants.TAG_ALPHA_LIST+">"+	       
								"</"+StudyDesignConstants.TAG_ALPHA_LIST+">";
				 
	//Invalid XML for an entity body with a List element
	private Document invalidListDoc1 = null;
	private String inValidList1 = "<"+StudyDesignConstants.TAG_ALPHA_LIST+">"+
							    		"<a>"+"0.5"+"</a>"+
								    	"<a>"+"0.1"+"</a>"+	    
								  "</"+StudyDesignConstants.TAG_ALPHA_LIST+">";
    	    
    public TestListParsing(final String name) 
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
            
            validListDoc = builder.parse(new InputSource(new StringReader(validList.toString())));
			validListDoc1 = builder.parse(new InputSource(new StringReader(validList1.toString())));
			inValidListDoc = builder.parse(new InputSource(new StringReader(inValidList.toString())));						
			invalidListDoc1 = builder.parse(new InputSource(new StringReader(inValidList1.toString())));
        }
        catch (Exception e)
        {
            fail();
        }
    }
    
    /**
     * Test parsing of a valid List
     */
    public void testValidList()
    {
        try
        {
        	ListResourceHelper.parseList(validListDoc.getDocumentElement());                      
            assertTrue(true);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Exception during testValidMatrix(): " + e.getMessage());
        }
    }
    
    /**
     * Test parsing of a valid List.
     */
   /* public void testValidListStringElements()
    {
        try
        {
        	ListResourceHelper.parseList(validListDoc1.getDocumentElement()); 
        	assertTrue(true);            
        }
        catch(Exception e)
        {
        	System.out.println("Exception caught as expected in testValidListStringElements()");
        	logger.info("Exception caught as expected in testValidListStringElements(): " +
            		e.getMessage());
        	fail();
        }
    }     */
    
    /**
     * Test parsing of a invalid List. (i.e. should throw an exception) 
     */
   /* public void testInvalidListEmpty()
    {
        try
        {
        	ListResourceHelper.parseList(inValidListDoc.getDocumentElement());                     
            fail("INVALID list input (empty) parsed successfully!  BAD!");
        }
        catch(Exception e)
        {        	
        	System.out.println("Exception caught as expected in testInvalidListEmpty()");
        	logger.info("Exception caught as expected in testInvalidListEmpty(): " +
            		e.getMessage());        	
            assertTrue(true);
        }
    }*/
    
    /**
     * Test parsing of a invalid List. (i.e. should throw an exception)
     */
 /*   public void testInvalidTag()
    {
        try
        {
        	ListResourceHelper.parseList(invalidListDoc1.getDocumentElement());            
            fail();
        }
        catch(Exception e)
        {
        	System.out.println("Exception caught on invalid List: "+e.getMessage());            
            assertTrue(true);            
        }
    }*/
         
}
