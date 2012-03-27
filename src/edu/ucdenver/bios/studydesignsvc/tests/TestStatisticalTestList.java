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

import com.google.gson.Gson;

import edu.ucdenver.bios.studydesignsvc.resource.StatisticalTestServerResource;
import edu.ucdenver.bios.webservice.common.domain.StatisticalTest;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.enums.StatisticalTestTypeEnum;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;

// TODO: Auto-generated Javadoc
/**
 * JUnit test cases for 'StatisticalTest' object - CRUD operations.
 * 
 * @author Uttara Sakhadeo
 */
public class TestStatisticalTestList extends TestCase
{
	
	/** The STUD y_ uuid. */
	private static UUID STUDY_UUID = UUID.fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");
	
	/** The STUD y_ name. */
	private static String STUDY_NAME = "Junit StatisticalTest Study Design";
	
	/** The resource. */
	StatisticalTestServerResource resource = new StatisticalTestServerResource();
	
	/** The uuid. */
	byte[] uuid = null;		
		
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	public void setUp()
	{
		uuid = UUIDUtils.asByteArray(STUDY_UUID);
	}
	
	/**
	 * Test to create a BetaScale List.
	 */
	@Test
	public void testCreate()
	{	
		
		List<StatisticalTest> testList = new ArrayList<StatisticalTest>();		
		StatisticalTest test = new StatisticalTest();		
			test.setType(StatisticalTestTypeEnum.UNIREP);	
		testList.add(test);	
		test = new StatisticalTest();		
			test.setType(StatisticalTestTypeEnum.UNIREPBOX);		
		testList.add(test);		
				
		try
		{
			testList = resource.create(uuid,testList);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			testList=null;
			fail();
		}
		if(testList==null)
		{
			fail();
		}
		else
		{
			System.out.println("testCreate() ");
			System.out.println(testList);
			 assertTrue(testList!=null);
		}
	}	
	
	/**
	 * Test to update a BetaScale List.
	 */
	@Test
	private void testUpdate()
	{
		List<StatisticalTest> testList = new ArrayList<StatisticalTest>();		
		StatisticalTest test = new StatisticalTest();		
			test.setType(StatisticalTestTypeEnum.UNIREPGG);
		testList.add(test);	
		test = new StatisticalTest();		
			test.setType(StatisticalTestTypeEnum.UNIREP);			
		testList.add(test);		
				
		try
		{
			testList = resource.update(uuid,testList);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			testList=null;
			fail();
		}
		if(testList==null)
		{
			fail();
		}
		else
		{
			System.out.println("testUpdate() : ");
			System.out.println(testList);
			 assertTrue(testList!=null);
		}
	}
	
	/**
	 * Test to delete a BetaScale List.
	 */
	@Test
	private void testDelete()
	{
		List<StatisticalTest> testList = null;			
		
		try
		{
			testList = resource.remove(uuid);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			testList=null;
			fail();
		}
		if (testList == null)
        {
        	System.err.println("No matching confidence interval found");
        	fail();
        }
        else
        {     
        	System.out.println("testDelete() : ");        	
        	System.out.println(testList);
            assertTrue(testList!=null);
        }
	}
	
	/**
	 * Test to retrieve a BetaScale List.
	 */
	@Test
	public void testRetrieve()
	{
		List<StatisticalTest> testList = null;			
		
		try
		{
			testList = resource.retrieve(uuid);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			testList=null;
			fail();
		}
		if (testList == null)
        {
        	System.err.println("No matching StatisticalTest found");
        	fail();
        }
        else
        {     
        	System.out.println("testRetrieve() : ");
        	 Gson gson = new Gson();
             String json = gson.toJson(testList);  
             System.out.println(json);
            assertTrue(testList!=null);
        }
	}
}
