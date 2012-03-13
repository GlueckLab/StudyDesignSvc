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
import edu.ucdenver.bios.studydesignsvc.resource.BetaScaleResource;
import edu.ucdenver.bios.studydesignsvc.resource.BetaScaleServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.StudyDesignResource;
import edu.ucdenver.bios.webservice.common.domain.BetaScale;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;
/**
 * JUnit test cases for 'BetaScale' object - CRUD operations.
 * 
 * @author Uttara Sakhadeo
 */
public class TestBetaScaleList extends TestCase
{
	private static UUID STUDY_UUID = UUID.fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");
	private static String STUDY_NAME = "Junit Test Study Design";
	BetaScaleServerResource resource = new BetaScaleServerResource();
	byte[] uuid = null;		
	
	ClientResource clientResource = null; 
	BetaScaleResource betaScaleResource = null;
		
	public void setUp()
	{
		uuid = UUIDUtils.asByteArray(STUDY_UUID);
		/*try
        {
            clientResource = new ClientResource("http://localhost:8080/study/"+StudyDesignConstants.TAG_BETA_SCALE_LIST); 
            //betaScaleResource = clientResource.wrap(BetaScaleResource.class);            
            betaScaleResource = clientResource.wrap(BetaScaleResource.class);
        }
        catch (Exception e)
        {
            System.err.println("Failed to connect to server: " + e.getMessage());
            fail();
        }*/
	}
	
	/**
     * Call the calculatePower function
     */
    private void testFunction()
    {    	
        // calculate power
        try
        {                    	
            ArrayList<BetaScale> betaScaleList = (ArrayList<BetaScale>)betaScaleResource.retrieve(uuid);  
            //System.err.println("Got object: " + betaScaleList.get(0).getClass());
            for(BetaScale betaScale : betaScaleList)
            	System.err.println(betaScale.getValue());            
            assertTrue(true);
        }
        catch (Exception e)
        {
            System.err.println("Failed to retrieve: " + e.getMessage());
            fail();
        }


    }
	
	/**
	 * Test to create a BetaScale List
	 */
	@Test
	public void testCreate()
	{			
		List<BetaScale> betaScaleList = new ArrayList<BetaScale>();		
		BetaScale betaScale = new BetaScale();						
			betaScale.setValue(0.5);	
		betaScaleList.add(betaScale);	
		betaScale = new BetaScale();					
			betaScale.setValue(1);			
		betaScaleList.add(betaScale);		
				
		try
		{
			betaScaleList = resource.create(uuid,betaScaleList);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			betaScaleList=null;
			fail();
		}
		if(betaScaleList==null)
		{
			fail();
		}
		else
		{
			System.out.println("testCreate() :  "+betaScaleList);
		}		
	}	
	
	/**
	 * Test to update a BetaScale List
	 */
	@Test
	private void testUpdate()
	{
		List<BetaScale> betaScaleList = new ArrayList<BetaScale>();		
		BetaScale betaScale = new BetaScale();					
			betaScale.setValue(0.11);	
		betaScaleList.add(betaScale);	
		betaScale = new BetaScale();					
			betaScale.setValue(0.22);			
		betaScaleList.add(betaScale);	
		betaScale = new BetaScale();				
			betaScale.setValue(0.33);			
		betaScaleList.add(betaScale);	
				
		try
		{
			betaScaleList = resource.update(uuid,betaScaleList);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			betaScaleList=null;
			fail();
		}
		if(betaScaleList==null)
		{
			fail();
		}
		else
		{
			System.out.println("testUpdate() : ");
			System.out.println(betaScale);
		}
	}
	
	/**
	 * Test to delete a BetaScale List
	 */
	@Test
	private void testDelete()
	{
		List<BetaScale> betaScaleList = null;			
		
		try
		{
			betaScaleList = resource.remove(uuid);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			betaScaleList=null;
			fail();
		}
		if (betaScaleList == null)
        {
        	System.err.println("No matching Beta Scale found");
        	fail();
        }
        else
        {     
        	System.out.println("testDelete() : ");
        	for(BetaScale betaScale: betaScaleList)
        		System.out.println(betaScale);
            assertTrue(betaScaleList!=null);
        }
	}
	
	/**
	 * Test to retrieve a BetaScale List
	 */
	@Test
	public void testRetrieve()
	{
		List<BetaScale> betaScaleList = null;			
		
		try
		{
			betaScaleList = resource.retrieve(uuid);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			betaScaleList=null;
			fail();
		}
		if (betaScaleList == null)
        {
        	System.err.println("No matching Beta Scale found");
        	fail();
        }
        else
        {     
        	System.out.println("testRetrieve() : "+betaScaleList.size());
        	 Gson gson = new Gson();
             String json = gson.toJson(betaScaleList);  
             System.out.println(json);
            assertTrue(betaScaleList!=null);
        }
	}
}
