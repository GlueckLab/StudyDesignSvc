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
package edu.ucdenver.bios.studydesignsvc.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import junit.framework.TestCase;

import org.junit.Test;

import edu.ucdenver.bios.studydesignsvc.resource.SigmaScaleServerResource;
import edu.ucdenver.bios.webservice.common.domain.SigmaScale;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;
/**
 * JUnit test cases for 'SigmaScale' object - CRUD operations.
 * 
 * @author Uttara Sakhadeo
 */
public class TestSigmaScaleList extends TestCase
{
	private static UUID STUDY_UUID = UUID.fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");
	private static String STUDY_NAME = "Junit Test Study Design";
	SigmaScaleServerResource resource = new SigmaScaleServerResource();
	byte[] uuid = null;		
		
	public void setUp()
	{
		uuid = UUIDUtils.asByteArray(STUDY_UUID);
	}
	
	/**
	 * Test to create a UUID from the database
	 * Note, this test must run after testCreate of a 
	 * not found will be thrown
	 */
	@Test
	public void testCreate()
	{	
		
		StudyDesign studyDesign = new StudyDesign();		
		studyDesign.setUuid(uuid);				
				
		List<SigmaScale> sigmaScaleList = new ArrayList<SigmaScale>();		
		SigmaScale sigmaScale = new SigmaScale();		
			sigmaScale.setStudyDesign(studyDesign);			
			sigmaScale.setValue(0.5);	
		sigmaScaleList.add(sigmaScale);	
		sigmaScale = new SigmaScale();		
			sigmaScale.setStudyDesign(studyDesign);			
			sigmaScale.setValue(1);			
		sigmaScaleList.add(sigmaScale);		
				
		try
		{
			sigmaScaleList = resource.create(sigmaScaleList);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			sigmaScaleList=null;
			fail();
		}
		if(sigmaScaleList==null)
		{
			fail();
		}
		else
		{
			System.out.println("testCreate() : Beta Scale list size after persistance: "+sigmaScaleList.size());
		}
	}	
	
	@Test
	private void testUpdate()
	{
		StudyDesign studyDesign = new StudyDesign();		
		studyDesign.setUuid(uuid);				
				
		List<SigmaScale> sigmaScaleList = new ArrayList<SigmaScale>();		
		SigmaScale sigmaScale = new SigmaScale();		
			sigmaScale.setStudyDesign(studyDesign);			
			sigmaScale.setValue(0.11);	
		sigmaScaleList.add(sigmaScale);	
		sigmaScale = new SigmaScale();		
			sigmaScale.setStudyDesign(studyDesign);			
			sigmaScale.setValue(0.22);			
		sigmaScaleList.add(sigmaScale);		
				
		try
		{
			sigmaScaleList = resource.update(sigmaScaleList);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			sigmaScaleList=null;
			fail();
		}
		if(sigmaScaleList==null)
		{
			fail();
		}
		else
		{
			System.out.println("testCreate() : Beta Scale list size after persistance: "+sigmaScaleList.size());
		}
	}
	
	@Test
	public void testDelete()
	{
		List<SigmaScale> sigmaScaleList = null;			
		
		try
		{
			sigmaScaleList = resource.remove(uuid);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			sigmaScaleList=null;
			fail();
		}
		if (sigmaScaleList == null)
        {
        	System.err.println("No matching confidence interval found");
        	fail();
        }
        else
        {     
        	System.out.println("testDelete() : ");
        	for(SigmaScale sigmaScale: sigmaScaleList)
        		System.out.println(sigmaScale.getValue());
            assertTrue(sigmaScaleList!=null);
        }
	}
	
	/**
	 * Test retrieving a UUID from the database
	 * Note, this test must run after testCreate of a 
	 * not found will be thrown
	 */
	@Test
	public void testRetrieve()
	{
		List<SigmaScale> sigmaScaleList = null;			
		
		try
		{
			sigmaScaleList = resource.retrieve(uuid);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			sigmaScaleList=null;
			fail();
		}
		if (sigmaScaleList == null)
        {
        	System.err.println("No matching confidence interval found");
        	fail();
        }
        else
        {     
        	System.out.println("testRetrieve() : ");
        	for(SigmaScale sigmaScale: sigmaScaleList)
        		System.out.println(sigmaScale.getValue());
            assertTrue(sigmaScaleList!=null);
        }
	}
}
