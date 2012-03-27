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

import edu.ucdenver.bios.studydesignsvc.resource.TypeIErrorServerResource;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.domain.TypeIError;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;

// TODO: Auto-generated Javadoc
/**
 * JUnit test cases for 'TypeIError' object - CRUD operations.
 * 
 * @author Uttara Sakhadeo
 */
public class TestTypeIError extends TestCase
{
	
	/** The STUD y_ uuid. */
	private static UUID STUDY_UUID = UUID.fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");
	
	/** The resource. */
	TypeIErrorServerResource resource = new TypeIErrorServerResource();
	
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
	 * Test to create a TypeIError List.
	 */
	@Test
	public void testCreate()
	{			
		List<TypeIError> typeIErrorList = new ArrayList<TypeIError>();		
		TypeIError typeIError = new TypeIError();		
			typeIError.setAlphaValue(0.5);	
		typeIErrorList.add(typeIError);	
		typeIError = new TypeIError();		
			typeIError.setAlphaValue(1);			
		typeIErrorList.add(typeIError);		
				
		try
		{
			typeIErrorList = resource.create(uuid,typeIErrorList);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			typeIErrorList=null;
			fail();
		}
		if(typeIErrorList==null)
		{
			fail();
		}
		else
		{
			System.out.println("testCreate() :  ");
			 Gson gson = new Gson();
             String json = gson.toJson(typeIErrorList);  
             System.out.println(json);
            assertTrue(typeIErrorList!=null);
		}
	}	
	
	/**
	 * Test to update a TypeIError List.
	 */
	@Test
	private void testUpdate()
	{
		List<TypeIError> typeIErrorList = new ArrayList<TypeIError>();		
		TypeIError typeIError = new TypeIError();		
			typeIError.setAlphaValue(0.11);	
		typeIErrorList.add(typeIError);	
		typeIError = new TypeIError();		
			typeIError.setAlphaValue(0.22);			
		typeIErrorList.add(typeIError);		
				
		try
		{
			typeIErrorList = resource.update(uuid,typeIErrorList);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			typeIErrorList=null;
			fail();
		}
		if(typeIErrorList==null)
		{
			fail();
		}
		else
		{
			System.out.println("testUpdate() :  ");
			 Gson gson = new Gson();
             String json = gson.toJson(typeIErrorList);  
             System.out.println(json);
            assertTrue(typeIErrorList!=null);
		}
	}
	
	/**
	 * Test to retrieve a TypeIError List.
	 */
	@Test
	public void testRetrieve()
	{
		List<TypeIError> typeIErrorList = null;			
		
		try
		{
			typeIErrorList = resource.retrieve(uuid);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			typeIErrorList=null;
			fail();
		}
		if (typeIErrorList == null)
        {
        	System.err.println("No matching confidence interval found");
        	fail();
        }
        else
        {     
        	System.out.println("testRetrieve() : ");
        	 Gson gson = new Gson();
             String json = gson.toJson(typeIErrorList);  
             System.out.println(json);
            assertTrue(typeIErrorList!=null);
        }
	}
	
	/**
	 * Test to delete a TypeIError List.
	 */
	@Test
	private void testDelete()
	{
		List<TypeIError> typeIErrorList = null;			
		
		try
		{
			typeIErrorList = resource.remove(uuid);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			typeIErrorList=null;
			fail();
		}
		if (typeIErrorList == null)
        {
        	System.err.println("No matching confidence interval found");
        	fail();
        }
        else
        {     
        	System.out.println("testDelete() : ");
        	 Gson gson = new Gson();
             String json = gson.toJson(typeIErrorList);  
             System.out.println(json);
            assertTrue(typeIErrorList!=null);
        }
	}	
}
