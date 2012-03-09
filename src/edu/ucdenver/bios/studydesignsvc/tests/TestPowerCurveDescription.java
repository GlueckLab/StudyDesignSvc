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

import java.util.UUID;

import junit.framework.TestCase;

import org.junit.Test;

import com.google.gson.Gson;

import edu.ucdenver.bios.studydesignsvc.manager.ConfidenceIntervalManager;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.studydesignsvc.resource.PowerCurveServerResource;
import edu.ucdenver.bios.webservice.common.domain.PowerCurveDescription;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.enums.HorizontalAxisLabelEnum;
import edu.ucdenver.bios.webservice.common.enums.StratificationVariableEnum;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;
/**
 * JUnit test cases for 'PowerCurveDescription' object - CRUD operations.
 * 
 * @author Uttara Sakhadeo
 */
public class TestPowerCurveDescription extends TestCase 
{
	private static UUID STUDY_UUID = UUID.fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");
	byte[] uuid = null;
	//private static int SAMPLE_SIZE = 100;
	StudyDesignManager studyDesignManager = null;
	ConfidenceIntervalManager confidenceIntervalManager = null;
		
	public void setUp()
	{
		uuid = UUIDUtils.asByteArray(STUDY_UUID);
	}
	
	/**
	 * Test to create a PowerCurveDescription
	 */
	@Test
	public void testCreate()
	{	
		
		boolean flag;
		StudyDesign studyDesign = new StudyDesign();
		studyDesign.setUuid(uuid);
		
		PowerCurveDescription powerCurveDescription = new PowerCurveDescription();
		powerCurveDescription.setPowerCurveDescription("test1 description");
		powerCurveDescription.setSampleSize(100);
		powerCurveDescription.setRegressionCoeeficientScaleFactor(0.2f);
		powerCurveDescription.setTypeIError(0.8f);				
		powerCurveDescription.setHorizontalAxisLabelEnum(HorizontalAxisLabelEnum.TOTAL_SAMPLE_SIZE);
		powerCurveDescription.setStratificationVarEnum(StratificationVariableEnum.TYPE_I_ERROR);
		
		PowerCurveServerResource resource = new  PowerCurveServerResource();		
		
		try
		{
			powerCurveDescription=resource.create(uuid,powerCurveDescription);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			powerCurveDescription=null;
			fail();
		}
		if(powerCurveDescription==null)
		{
			fail();
		}
		else
		{
			System.out.println("testCreate() : ");
			System.out.println(powerCurveDescription);
		}	
	}
	
	/**
	 * Test to update a PowerCurveDescription
	 */
	@Test
	private void testUpdate()
	{			
		boolean flag;
		StudyDesign studyDesign = new StudyDesign();
		studyDesign.setUuid(uuid);
		
		PowerCurveDescription powerCurveDescription = new PowerCurveDescription();
		powerCurveDescription.setPowerCurveDescription("changed");
		powerCurveDescription.setSampleSize(100);
		powerCurveDescription.setRegressionCoeeficientScaleFactor(0.2f);
		powerCurveDescription.setTypeIError(0.8f);		
		powerCurveDescription.setStudyDesign(studyDesign);
		
		PowerCurveServerResource resource = new  PowerCurveServerResource();		
		
		try
		{
			powerCurveDescription=resource.update(uuid,powerCurveDescription);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			powerCurveDescription=null;
			fail();
		}
		if(powerCurveDescription==null)
		{
			fail();
		}
		else
		{
			System.out.println("testUpdate() : ");
			System.out.println(powerCurveDescription);
		}	
	}
	
	/**
	 * Test to retrieve a PowerCurveDescription
	 */
	@Test
	public void testRetrieve()
	{		
		PowerCurveDescription powerCurveDescription = null;		
		
		PowerCurveServerResource resource = new  PowerCurveServerResource();		
		
		try
		{
			powerCurveDescription = resource.retrieve(uuid);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			powerCurveDescription=null;
			fail();
		}
		if (powerCurveDescription == null)
        {
        	System.err.println("No matching confidence interval found");
        	fail();
        }
        else
        {              	                    
        	System.out.println("testRetrieve() : ");
        	//System.out.println(powerCurveDescription);
        	 Gson gson = new Gson();
             String json = gson.toJson(powerCurveDescription);  
             System.out.println(json);
            assertTrue(powerCurveDescription!=null);
        }
	}
	
	/**
	 * Test to delete a PowerCurveDescription
	 */
	@Test
	private void testDelete()
	{		
		PowerCurveDescription powerCurveDescription = null;
		PowerCurveServerResource resource = new  PowerCurveServerResource();		
		
		try
		{
			powerCurveDescription = resource.remove(uuid);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			powerCurveDescription=null;
			fail();
		}
		if (powerCurveDescription == null)
        {
        	System.err.println("No matching confidence interval found");
        	fail();
        }
        else
        {              	                    
        	System.out.println("testDelete() : ");
        	System.out.println(powerCurveDescription);
            assertTrue(powerCurveDescription!=null);
        }
	}
}
