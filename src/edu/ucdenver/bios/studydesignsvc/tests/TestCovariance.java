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
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import junit.framework.TestCase;

import org.junit.Test;
import org.restlet.resource.ClientResource;

import com.google.gson.Gson;

import edu.ucdenver.bios.studydesignsvc.resource.BetaScaleResource;
import edu.ucdenver.bios.studydesignsvc.resource.CovarianceServerResource;
import edu.ucdenver.bios.webservice.common.domain.Blob2DArray;
import edu.ucdenver.bios.webservice.common.domain.Covariance;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;


/**
 * JUnit test cases for 'Covariance' object - CRUD operations.
 * 
 * @author Uttara Sakhadeo
 */
public class TestCovariance extends TestCase
{
	private static UUID STUDY_UUID = UUID.fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");
	private static final String COVARIANCE_NAME_1 = "Covariance 1";
	private static final String COVARIANCE_NAME_2 = "Covariance 2";
	
	CovarianceServerResource resource = new CovarianceServerResource();
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
	 * Test to create a Covariance
	 */
	@Test
	public void testCreate()
	{			
		Set<Covariance> covarianceSet = new HashSet<Covariance>();		
		Covariance covariance = new Covariance();						
			covariance.setName(COVARIANCE_NAME_1);
			covariance.setColumns(3);
			covariance.setRows(2);
			covariance.setRoh(1.2);
			covariance.setDelta(2.5);
			covariance.setSd(5);			
			Blob2DArray blob = new Blob2DArray();
			double[][] data = new double[2][2];
				for(int i=0; i<2 ; i++)
				{
					for(int j =0 ; j<2; j++)
					{
						data[j][i]=2.0;
					}
				}
			blob.setData(data);
			covariance.setBlob(blob);
		covarianceSet.add(covariance);	
		covariance = new Covariance();					
			covariance.setName(COVARIANCE_NAME_2);
			covariance.setColumns(1);
			covariance.setRows(5);
			covariance.setRoh(1.1);
			covariance.setDelta(0.5);
			covariance.setSd(0.1);			
			blob = new Blob2DArray();
			data = new double[3][2];
				for(int i=0; i<2 ; i++)
				{
					for(int j =0 ; j<3; j++)
					{
						data[j][i]=2.0;
					}
				}
			blob.setData(data);
			covariance.setBlob(blob);
		covarianceSet.add(covariance);		
				
		try
		{
			covarianceSet = resource.create(uuid,covarianceSet);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			covarianceSet=null;
			fail();
		}
		if(covarianceSet==null)
		{
			fail();
		}
		else
		{
			System.out.println("testCreate() :  "+covarianceSet.size());
			Gson gson = new Gson();
            String json = gson.toJson(covarianceSet);  
            System.out.println(json);
           assertTrue(covarianceSet!=null);
		}		
	}	
	
	/**
	 * Test to update a Covariance
	 */
	@Test
	public void testUpdate()
	{
		Set<Covariance> covarianceSet = new HashSet<Covariance>();		
		Covariance covariance = new Covariance();						
			covariance.setName(COVARIANCE_NAME_1+" Updated");
			covariance.setColumns(3);
			covariance.setRows(2);
			covariance.setRoh(1.2);
			covariance.setDelta(2.5);
			covariance.setSd(5);			
			Blob2DArray blob = new Blob2DArray();
			double[][] data = new double[10][10];
				for(int i=0; i<3 ; i++)
				{
					for(int j =0 ; j<3; j++)
					{
						data[j][i]=4.4;
					}
				}
			blob.setData(data);
			covariance.setBlob(blob);
		covarianceSet.add(covariance);	
						
		try
		{
			covarianceSet = resource.update(uuid,covarianceSet);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			covarianceSet=null;
			fail();
		}
		if(covarianceSet==null)
		{
			fail();
		}
		else
		{
			System.out.println("testUpdate() : "+covarianceSet.size());
			Gson gson = new Gson();
            String json = gson.toJson(covarianceSet);  
            System.out.println(json);
           assertTrue(covarianceSet!=null);
		}
	}
	
	/**
	 * Test to delete a Covariance
	 */
	@Test
	private void testDelete()
	{
		Set<Covariance> covarianceSet = null;			
		
		try
		{
			covarianceSet = resource.remove(uuid);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			covarianceSet=null;
			fail();
		}
		if (covarianceSet == null)
        {
        	System.err.println("No matching Covariance found");
        	fail();
        }
        else
        {     
        	System.out.println("testDelete() : "+covarianceSet.size());
        	Gson gson = new Gson();
            String json = gson.toJson(covarianceSet);  
            System.out.println(json);
           assertTrue(covarianceSet!=null);
            assertTrue(covarianceSet!=null);
        }
	}
	
	/**
	 * Test to retrieve a Covariance
	 */
	@Test
	public void testRetrieve()
	{
		Set<Covariance> covarianceSet = null;			
		
		try
		{
			covarianceSet = resource.retrieve(uuid);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			covarianceSet=null;
			fail();
		}
		if (covarianceSet == null)
        {
        	System.err.println("No matching Covariance found");
        	fail();
        }
        else
        {     
        	System.out.println("testRetrieve() : "+covarianceSet.size());
        	 Gson gson = new Gson();
             String json = gson.toJson(covarianceSet);  
             System.out.println(json);
            assertTrue(covarianceSet!=null);
        }
	}
}