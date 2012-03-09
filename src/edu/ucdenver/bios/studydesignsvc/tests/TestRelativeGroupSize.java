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

import org.junit.Test;

import edu.ucdenver.bios.studydesignsvc.resource.RelativeGroupSizeServerResource;
import edu.ucdenver.bios.webservice.common.domain.RelativeGroupSize;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;
import junit.framework.TestCase;
/**
 * JUnit test cases for 'RelativeGroupSize' object - CRUD operations.
 * 
 * @author Uttara Sakhadeo
 */
public class TestRelativeGroupSize extends TestCase
{
	private static UUID STUDY_UUID = UUID.fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");
	private static String STUDY_NAME = "Junit Test Study Design";
	RelativeGroupSizeServerResource resource = new RelativeGroupSizeServerResource();
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
	private void testCreate()
	{	
		
		StudyDesign studyDesign = new StudyDesign();		
		studyDesign.setUuid(uuid);				
				
		List<RelativeGroupSize> relativeGroupSizeList = new ArrayList<RelativeGroupSize>();		
		RelativeGroupSize relativeGroupSize = new RelativeGroupSize();		
			relativeGroupSize.setStudyDesign(studyDesign);			
			relativeGroupSize.setValue(5);	
		relativeGroupSizeList.add(relativeGroupSize);	
		relativeGroupSize = new RelativeGroupSize();		
			relativeGroupSize.setStudyDesign(studyDesign);			
			relativeGroupSize.setValue(1);			
		relativeGroupSizeList.add(relativeGroupSize);		
				
		try
		{
			relativeGroupSizeList = resource.create(relativeGroupSizeList);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			relativeGroupSizeList=null;
			fail();
		}
		if(relativeGroupSizeList==null)
		{
			fail();
		}
		else
		{
			System.out.println("testCreate() : RelativeGroupSize list size after persistance: "+relativeGroupSizeList.size());
		}
	}	
	
	@Test
	public void testUpdate()
	{
		StudyDesign studyDesign = new StudyDesign();		
		studyDesign.setUuid(uuid);				
				
		List<RelativeGroupSize> relativeGroupSizeList = new ArrayList<RelativeGroupSize>();		
		RelativeGroupSize relativeGroupSize = new RelativeGroupSize();		
			relativeGroupSize.setStudyDesign(studyDesign);			
			relativeGroupSize.setValue(11);	
		relativeGroupSizeList.add(relativeGroupSize);	
		relativeGroupSize = new RelativeGroupSize();		
			relativeGroupSize.setStudyDesign(studyDesign);			
			relativeGroupSize.setValue(22);			
		relativeGroupSizeList.add(relativeGroupSize);		
				
		try
		{
			relativeGroupSizeList = resource.update(relativeGroupSizeList);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			relativeGroupSizeList=null;
			fail();
		}
		if(relativeGroupSizeList==null)
		{
			fail();
		}
		else
		{
			System.out.println("testCreate() : RelativeGroupSize list size after persistance: "+relativeGroupSizeList.size());
		}
	}
	
	@Test
	public void testDelete()
	{
		List<RelativeGroupSize> relativeGroupSizeList = null;			
		
		try
		{
			relativeGroupSizeList = resource.remove(uuid);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			relativeGroupSizeList=null;
			fail();
		}
		if (relativeGroupSizeList == null)
        {
        	System.err.println("No matching confidence interval found");
        	fail();
        }
        else
        {     
        	System.out.println("testDelete() : ");
        	for(RelativeGroupSize relativeGroupSize: relativeGroupSizeList)
        		System.out.println(relativeGroupSize.getValue());
            assertTrue(relativeGroupSizeList!=null);
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
		List<RelativeGroupSize> relativeGroupSizeList = null;			
		
		try
		{
			relativeGroupSizeList = resource.retrieve(uuid);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			relativeGroupSizeList=null;
			fail();
		}
		if (relativeGroupSizeList == null)
        {
        	System.err.println("No matching confidence interval found");
        	fail();
        }
        else
        {     
        	System.out.println("testRetrieve() : ");
        	for(RelativeGroupSize relativeGroupSize: relativeGroupSizeList)
        		System.out.println(relativeGroupSize.getValue());
            assertTrue(relativeGroupSizeList!=null);
        }
	}
}
