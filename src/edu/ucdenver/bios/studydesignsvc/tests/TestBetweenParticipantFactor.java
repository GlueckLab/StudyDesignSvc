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

import edu.ucdenver.bios.studydesignsvc.resource.BetweenParticipantServerResource;
import edu.ucdenver.bios.webservice.common.domain.BetweenParticipantFactor;
import edu.ucdenver.bios.webservice.common.domain.Category;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;

/**
 * JUnit test cases for 'BetweenParticipantFactor' object - CRUD operations.
 * 
 * @author Uttara Sakhadeo
 */
public class TestBetweenParticipantFactor extends TestCase 
{
	private static UUID STUDY_UUID = UUID.fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");
	private static String STUDY_NAME = "Junit Test Study Design";
	BetweenParticipantServerResource resource = new BetweenParticipantServerResource();
	byte[] uuid = null;		
		
	public void setUp()
	{uuid = UUIDUtils.asByteArray(STUDY_UUID);}
	
	/**
	 * Test to create a BetweenParticipantFactor List
	 */
	@Test
	private void testCreate()
	{	
		List<BetweenParticipantFactor> betweenParticipantFactorList = new ArrayList<BetweenParticipantFactor>();		
			BetweenParticipantFactor betweenParticipantFactor = new BetweenParticipantFactor();		
				betweenParticipantFactor.setPredictorName("Medicine");
					List<Category> categoryList = new ArrayList<Category>();
					categoryList.add(new Category("A"));
					categoryList.add(new Category("B"));
				betweenParticipantFactor.setCategoryList(categoryList);
		betweenParticipantFactorList.add(betweenParticipantFactor);	
			betweenParticipantFactor = new BetweenParticipantFactor();							
				betweenParticipantFactor.setPredictorName("Natural Food");
					categoryList = new ArrayList<Category>();
					categoryList.add(new Category("Fruits"));
					categoryList.add(new Category("Grains"));
				betweenParticipantFactor.setCategoryList(categoryList);	
		betweenParticipantFactorList.add(betweenParticipantFactor);	
		try
		{
			betweenParticipantFactorList = resource.create(uuid,betweenParticipantFactorList);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			betweenParticipantFactorList=null;
			fail();
		}
		if(betweenParticipantFactorList==null)
		{
			fail();
		}
		else
		{
			System.out.println("testCreate() : ");
			for(BetweenParticipantFactor betParticipantFactor: betweenParticipantFactorList)
				System.out.println(betParticipantFactor);
		}
	}
	
	/**
	 * Test to update a BetweenParticipantFactor List
	 */
	@Test
	public void testUpdate()
	{					
		List<BetweenParticipantFactor> betweenParticipantFactorList = new ArrayList<BetweenParticipantFactor>();		
		BetweenParticipantFactor betweenParticipantFactor = new BetweenParticipantFactor();		
			betweenParticipantFactor.setPredictorName("Medicine");
				List<Category> categoryList = new ArrayList<Category>();
				categoryList.add(new Category("Alpha"));
				categoryList.add(new Category("Beta"));
			betweenParticipantFactor.setCategoryList(categoryList);
		betweenParticipantFactorList.add(betweenParticipantFactor);	
			betweenParticipantFactor = new BetweenParticipantFactor();							
				betweenParticipantFactor.setPredictorName("Natural Food");
					categoryList = new ArrayList<Category>();
					categoryList.add(new Category("Fruits"));
					categoryList.add(new Category("Grains"));
				betweenParticipantFactor.setCategoryList(categoryList);	
		betweenParticipantFactorList.add(betweenParticipantFactor);	
				
		try
		{
			betweenParticipantFactorList = resource.update(uuid,betweenParticipantFactorList);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			betweenParticipantFactorList=null;
			fail();
		}
		if(betweenParticipantFactorList==null)
		{
			fail();
		}
		else
		{
			System.out.println("testUpdate() : ");
			for(BetweenParticipantFactor betParticipantFactor: betweenParticipantFactorList)
				System.out.println(betParticipantFactor);
		}
	}
	
	/**
	 * Test to delete a BetweenParticipantFactor List
	 */
	@Test
	private void testDelete()
	{
		List<BetweenParticipantFactor> betweenParticipantFactorList = null;			
		
		try
		{
			betweenParticipantFactorList = resource.remove(uuid);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			betweenParticipantFactorList=null;
			fail();
		}
		if (betweenParticipantFactorList == null)
        {
        	System.err.println("No matching BetweenParticipantFactor found");
        	fail();
        }
        else
        {     
        	System.out.println("testDelete() : ");
        	for(BetweenParticipantFactor BetweenParticipantFactor: betweenParticipantFactorList)
        		System.out.println(BetweenParticipantFactor);
            assertTrue(betweenParticipantFactorList!=null);
        }
	}
	
	/**
	 * Test to retrieve a BetweenParticipantFactor List
	 */
	@Test
	public void testRetrieve()
	{
		List<BetweenParticipantFactor> betweenParticipantFactorList = null;			
		
		try
		{
			betweenParticipantFactorList = resource.retrieve(uuid);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			betweenParticipantFactorList=null;
			fail();
		}
		if (betweenParticipantFactorList == null)
        {
        	System.err.println("No matching BetweenParticipantFactor found");
        	fail();
        }
        else
        {     
        	System.out.println("testRetrieve() : ");
        	/*for(BetweenParticipantFactor BetweenParticipantFactor: betweenParticipantFactorList)
        		System.out.println(BetweenParticipantFactor);*/
        	Gson gson = new Gson();
            String json = gson.toJson(betweenParticipantFactorList);  
            System.out.println(json);
            assertTrue(betweenParticipantFactorList!=null);
        }
	}
}
