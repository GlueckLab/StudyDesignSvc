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

import java.util.List;
import java.util.UUID;

import junit.framework.TestCase;

import org.junit.Test;

import com.google.gson.Gson;

import edu.ucdenver.bios.studydesignsvc.resource.SigmaScaleServerResource;
import edu.ucdenver.bios.webservice.common.domain.SigmaScale;
import edu.ucdenver.bios.webservice.common.domain.SigmaScaleList;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;
// TODO: Auto-generated Javadoc
/**
 * JUnit test cases for 'SigmaScale' object - CRUD operations.
 * 
 * @author Uttara Sakhadeo
 */
public class TestSigmaScaleList extends TestCase
{
	
	/** The STUD y_ uuid. */
	private static UUID STUDY_UUID = UUID.fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");
	
	/** The resource. */
	SigmaScaleServerResource resource = new SigmaScaleServerResource();
	
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
	 * Test to create a SigmaScale List.
	 */
	@Test
	public void testCreate()
	{	
		
		SigmaScaleList sigmaScaleList = new SigmaScaleList();		
		SigmaScale sigmaScale = new SigmaScale();		
			sigmaScale.setValue(0.5);	
		sigmaScaleList.add(sigmaScale);	
		sigmaScale = new SigmaScale();		
			sigmaScale.setValue(1);			
		sigmaScaleList.add(sigmaScale);		
				
		try
		{
			sigmaScaleList = resource.create(uuid,sigmaScaleList);			
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
			System.out.println("testCreate() : ");
			Gson gson = new Gson();
            String json = gson.toJson(sigmaScaleList);  
            System.out.println(json);
           assertTrue(sigmaScaleList!=null);
		}
	}	
	
	/**
	 * Test to update a SigmaScale List.
	 */
	@Test
	private void testUpdate()
	{
	    SigmaScaleList sigmaScaleList = new SigmaScaleList();		
		SigmaScale sigmaScale = new SigmaScale();		
			sigmaScale.setValue(0.11);	
		sigmaScaleList.add(sigmaScale);	
		sigmaScale = new SigmaScale();		
			sigmaScale.setValue(0.22);			
		sigmaScaleList.add(sigmaScale);		
				
		try
		{
			sigmaScaleList = resource.update(uuid,sigmaScaleList);			
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
			System.out.println("testUpdate() : ");
			Gson gson = new Gson();
            String json = gson.toJson(sigmaScaleList);  
            System.out.println(json);
           assertTrue(sigmaScaleList!=null);
		}
	}
	
	/**
	 * Test to retrieve a SigmaScale List.
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
        	Gson gson = new Gson();
            String json = gson.toJson(sigmaScaleList);  
            System.out.println(json);
           assertTrue(sigmaScaleList!=null);
        }
	}
	
	/**
	 * Test to delete a SigmaScale List.
	 */
	@Test
	private void testDelete()
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
        	Gson gson = new Gson();
            String json = gson.toJson(sigmaScaleList);  
            System.out.println(json);
           assertTrue(sigmaScaleList!=null);
        }
	}
		
}
