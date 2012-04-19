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
import org.restlet.resource.ClientResource;

import com.google.gson.Gson;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignConstants;
import edu.ucdenver.bios.studydesignsvc.resource.RepeatedMeasuresResource;
import edu.ucdenver.bios.webservice.common.domain.RepeatedMeasuresNode;
import edu.ucdenver.bios.webservice.common.domain.RepeatedMeasuresNodeList;
import edu.ucdenver.bios.webservice.common.domain.Spacing;
import edu.ucdenver.bios.webservice.common.enums.RepeatedMeasuresDimensionType;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;

// TODO: Auto-generated Javadoc
/**
 * JUnit test cases for 'RepeatedMeasuresNode' object - CRUD operations.
 * 
 * @author Uttara Sakhadeo
 */
public class TestRepeatedMeasures extends TestCase
{
	
	/** The STUD y_ uuid. */
	private static UUID STUDY_UUID = UUID.fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");
	
	/** The STUD y_ name. */
	private static String STUDY_NAME = "Junit Test Study Design";
	
	/** The resource. */
	RepeatedMeasuresResource resource = null;
	
	/** The uuid. */
	byte[] uuid = null;		
		
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	public void setUp() {
        uuid = UUIDUtils.asByteArray(STUDY_UUID);
        try
        {
            System.clearProperty("http.proxyHost");
            ClientResource clientResource = new ClientResource("http://localhost:8080/study/"+StudyDesignConstants.TAG_REPEATEDMEASURES);
            resource = clientResource.wrap(RepeatedMeasuresResource.class);            
        }
        catch (Exception e)
        {
            System.err.println("Failed to connect to server: " + e.getMessage());
            fail();
        }
    }
	
	/**
	 * Test to create a RepeatedMeasuresNode List.
	 */
	@Test
	public void testCreate()
	{			
	    List<RepeatedMeasuresNode> list = new ArrayList<RepeatedMeasuresNode>();		
		RepeatedMeasuresNode repeatedMeasuresNode = new RepeatedMeasuresNode();		
			repeatedMeasuresNode.setDimension("Week");
			repeatedMeasuresNode.setNode(0);
			repeatedMeasuresNode.setParent(null);	
			repeatedMeasuresNode.setNumberOfMeasurements(2);
			repeatedMeasuresNode.setRepeatedMeasuresDimensionType(RepeatedMeasuresDimensionType.NUMERICAL);
				ArrayList<Spacing> spacingList = new ArrayList<Spacing>();
					Spacing spacing = new Spacing();
					//spacing.setRepeatedMeasuresNode(repeatedMeasuresNode);
					spacing.setValue(10);
					spacingList.add(spacing);
					
					spacing = new Spacing();
					//spacing.setRepeatedMeasuresNode(repeatedMeasuresNode);
					spacing.setValue(20);
					spacingList.add(spacing);
					repeatedMeasuresNode.setSpacingList(spacingList);
					list.add(repeatedMeasuresNode);	
			
			repeatedMeasuresNode = new RepeatedMeasuresNode();	
			repeatedMeasuresNode.setNode(1);
			repeatedMeasuresNode.setParent(0);	
			repeatedMeasuresNode.setNumberOfMeasurements(2);
			repeatedMeasuresNode.setRepeatedMeasuresDimensionType(RepeatedMeasuresDimensionType.ORDINAL);
				spacingList = new ArrayList<Spacing>();
				spacing = new Spacing();
				//spacing.setRepeatedMeasuresNode(repeatedMeasuresNode);
				spacing.setValue(10);
				spacingList.add(spacing);
				
				spacing = new Spacing();
				//spacing.setRepeatedMeasuresNode(repeatedMeasuresNode);
				spacing.setValue(20);
				spacingList.add(spacing);
				repeatedMeasuresNode.setSpacingList(spacingList);
				list.add(repeatedMeasuresNode);			
		RepeatedMeasuresNodeList repeatedMeasuresTree = new RepeatedMeasuresNodeList(uuid,list);	
		try
		{
		    repeatedMeasuresTree = resource.create(repeatedMeasuresTree);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			repeatedMeasuresTree=null;
			fail();
		}
		if(repeatedMeasuresTree==null)
		{
			fail();
		}
		else
		{
			System.out.println("testCreate() : ");
			Gson gson = new Gson();
            String json = gson.toJson(repeatedMeasuresTree);  
            System.out.println(json);
            assertTrue(repeatedMeasuresTree!=null);
		}
	}		
	
	/**
	 * Test to update a RepeatedMeasuresNode List.
	 */
	@Test
	private void testUpdate()
	{
	    List<RepeatedMeasuresNode> list = new ArrayList<RepeatedMeasuresNode>();               
	    RepeatedMeasuresNode repeatedMeasuresNode = new RepeatedMeasuresNode();		
			repeatedMeasuresNode.setDimension("Week");
			repeatedMeasuresNode.setNode(0);
			repeatedMeasuresNode.setParent(null);	
			repeatedMeasuresNode.setNumberOfMeasurements(2);
			repeatedMeasuresNode.setRepeatedMeasuresDimensionType(RepeatedMeasuresDimensionType.NUMERICAL);
			ArrayList<Spacing> spacingList = new ArrayList<Spacing>();
					Spacing spacing = new Spacing();
					//spacing.setRepeatedMeasuresNode(repeatedMeasuresNode);
					spacing.setValue(10);
					spacingList.add(spacing);
					
					spacing = new Spacing();
					//spacing.setRepeatedMeasuresNode(repeatedMeasuresNode);
					spacing.setValue(20);
					spacingList.add(spacing);
					repeatedMeasuresNode.setSpacingList(spacingList);
			list.add(repeatedMeasuresNode);	
			
			repeatedMeasuresNode = new RepeatedMeasuresNode();	
			repeatedMeasuresNode.setDimension("Day");
			repeatedMeasuresNode.setNode(1);
			repeatedMeasuresNode.setParent(0);	
			repeatedMeasuresNode.setNumberOfMeasurements(2);
			repeatedMeasuresNode.setRepeatedMeasuresDimensionType(RepeatedMeasuresDimensionType.ORDINAL);
				spacingList = new ArrayList<Spacing>();
				spacing = new Spacing();
				//spacing.setRepeatedMeasuresNode(repeatedMeasuresNode);
				spacing.setValue(10);
				spacingList.add(spacing);
				
				spacing = new Spacing();
				//spacing.setRepeatedMeasuresNode(repeatedMeasuresNode);
				spacing.setValue(20);
				spacingList.add(spacing);
				repeatedMeasuresNode.setSpacingList(spacingList);
			list.add(repeatedMeasuresNode);	
			
			repeatedMeasuresNode = new RepeatedMeasuresNode();	
			repeatedMeasuresNode.setDimension("Time");
			repeatedMeasuresNode.setNode(2);
			repeatedMeasuresNode.setParent(1);	
			repeatedMeasuresNode.setNumberOfMeasurements(1);
			repeatedMeasuresNode.setRepeatedMeasuresDimensionType(RepeatedMeasuresDimensionType.ORDINAL);
				spacingList = new ArrayList<Spacing>();
				spacing = new Spacing();
				//spacing.setRepeatedMeasuresNode(repeatedMeasuresNode);
				spacing.setValue(10);
				spacingList.add(spacing);
				repeatedMeasuresNode.setSpacingList(spacingList);
			list.add(repeatedMeasuresNode);	
		RepeatedMeasuresNodeList repeatedMeasuresTree = new RepeatedMeasuresNodeList(uuid,list);	
		try
		{
			repeatedMeasuresTree = resource.update(repeatedMeasuresTree);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			repeatedMeasuresTree=null;
			fail();
		}
		if(repeatedMeasuresTree==null)
		{
			fail();
		}
		else
		{
			System.out.println("testUpdate() : ");
			Gson gson = new Gson();
            String json = gson.toJson(repeatedMeasuresTree);  
            System.out.println(json);
            assertTrue(repeatedMeasuresTree!=null);
		}
	}
	
	/**
	 * Test to retrieve a RepeatedMeasuresNode List.
	 */
	@Test
	private void testRetrieve()
	{
		RepeatedMeasuresNodeList repeatedMeasuresTree = null;			
		
		try
		{
			repeatedMeasuresTree = resource.retrieve(uuid);			
		}		
		catch(Exception e)
		{
			System.out.println("Exception : "+e.getMessage());
			repeatedMeasuresTree=null;
			fail();
		}
		if (repeatedMeasuresTree == null)
        {
        	System.err.println("No matching confidence interval found");
        	fail();
        }
        else
        {     
        	System.out.println("testRetrieve() : ");
			/*for(RepeatedMeasuresNode rmNode: repeatedMeasuresTree)
				System.out.println(rmNode); */ 
        	Gson gson = new Gson();
            String json = gson.toJson(repeatedMeasuresTree);  
            System.out.println(json);
            assertTrue(repeatedMeasuresTree!=null);
        }
	}
	
	/**
	 * Test to delete a RepeatedMeasuresNode List.
	 */
	@Test
	private void testDelete()
	{
	    RepeatedMeasuresNodeList repeatedMeasuresTree = null;			
		
		try
		{
			repeatedMeasuresTree = resource.remove(uuid);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			repeatedMeasuresTree=null;
			fail();
		}
		if (repeatedMeasuresTree == null)
        {
        	System.err.println("No matching confidence interval found");
        	fail();
        }
        else
        {     
        	System.out.println("testDelete() : ");
        	Gson gson = new Gson();
            String json = gson.toJson(repeatedMeasuresTree);  
            System.out.println(json);
            assertTrue(repeatedMeasuresTree!=null);
        }
	}
		
}
