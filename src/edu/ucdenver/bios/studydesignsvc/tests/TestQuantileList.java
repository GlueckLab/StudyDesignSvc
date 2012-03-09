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

import edu.ucdenver.bios.studydesignsvc.resource.QuantileServerResource;
import edu.ucdenver.bios.webservice.common.domain.Quantile;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;
/**
 * JUnit test cases for 'Quantile' object - CRUD operations.
 * 
 * @author Uttara Sakhadeo
 */
public class TestQuantileList extends TestCase
{
	private static UUID STUDY_UUID = UUID.fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");
	private static String STUDY_NAME = "Junit Test Study Design";
	QuantileServerResource resource = new QuantileServerResource();
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
				
		List<Quantile> quantileList = new ArrayList<Quantile>();		
		Quantile quantile = new Quantile();		
			quantile.setStudyDesign(studyDesign);			
			quantile.setValue(0.5);	
		quantileList.add(quantile);	
		quantile = new Quantile();		
			quantile.setStudyDesign(studyDesign);			
			quantile.setValue(1);			
		quantileList.add(quantile);		
				
		try
		{
			quantileList = resource.create(quantileList);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			quantileList=null;
			fail();
		}
		if(quantileList==null)
		{
			fail();
		}
		else
		{
			System.out.println("testCreate() : Quantile list size after persistance: "+quantileList.size());
		}
	}	
	
	@Test
	private void testUpdate()
	{
		StudyDesign studyDesign = new StudyDesign();		
		studyDesign.setUuid(uuid);				
				
		List<Quantile> quantileList = new ArrayList<Quantile>();		
		Quantile quantile = new Quantile();		
			quantile.setStudyDesign(studyDesign);			
			quantile.setValue(0.11);	
		quantileList.add(quantile);	
		quantile = new Quantile();		
			quantile.setStudyDesign(studyDesign);			
			quantile.setValue(0.22);			
		quantileList.add(quantile);		
				
		try
		{
			quantileList = resource.update(quantileList);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			quantileList=null;
			fail();
		}
		if(quantileList==null)
		{
			fail();
		}
		else
		{
			System.out.println("testCreate() : Quantile list size after persistance: "+quantileList.size());
		}
	}
	
	@Test
	private void testDelete()
	{
		List<Quantile> quantileList = null;			
		
		try
		{
			quantileList = resource.remove(uuid);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			quantileList=null;
			fail();
		}
		if (quantileList == null)
        {
        	System.err.println("No matching confidence interval found");
        	fail();
        }
        else
        {     
        	System.out.println("testDelete() : ");
        	for(Quantile quantile: quantileList)
        		System.out.println(quantile.getValue());
            assertTrue(quantileList!=null);
        }
	}
	
	/**
	 * Test retrieving a UUID from the database
	 * Note, this test must run after testCreate of a 
	 * not found will be thrown
	 */
	@Test
	private void testRetrieve()
	{
		List<Quantile> quantileList = null;			
		
		try
		{
			quantileList = resource.retrieve(uuid);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			quantileList=null;
			fail();
		}
		if (quantileList == null)
        {
        	System.err.println("No matching confidence interval found");
        	fail();
        }
        else
        {     
        	System.out.println("testRetrieve() : ");
        	for(Quantile quantile: quantileList)
        		System.out.println(quantile.getValue());
            assertTrue(quantileList!=null);
        }
	}
}
