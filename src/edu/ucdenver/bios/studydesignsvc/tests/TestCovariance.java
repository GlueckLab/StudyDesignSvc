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


import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import junit.framework.TestCase;

import org.junit.Test;
import org.restlet.resource.ClientResource;

import com.google.gson.Gson;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignConstants;
import edu.ucdenver.bios.studydesignsvc.resource.ConfidenceIntervalResource;
import edu.ucdenver.bios.studydesignsvc.resource.CovarianceResource;
import edu.ucdenver.bios.studydesignsvc.resource.CovarianceServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.CovarianceSetResource;
import edu.ucdenver.bios.webservice.common.domain.Covariance;
import edu.ucdenver.bios.webservice.common.domain.CovarianceSet;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;

// TODO: Auto-generated Javadoc
/**
 * JUnit test cases for 'Covariance' object - CRUD operations.
 * 
 * @author Uttara Sakhadeo
 */
public class TestCovariance extends TestCase
{
	
	/** The STUD y_ uuid. */
	private static UUID STUDY_UUID = UUID.fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");
	
	/** The Constant COVARIANCE_NAME_1. */
	private static final String COVARIANCE_NAME_1 = "Covariance 1";
	
	/** The Constant COVARIANCE_NAME_2. */
	private static final String COVARIANCE_NAME_2 = "Covariance 2";
	
	/** The resource. */
	CovarianceResource resource = null;
	
	CovarianceSetResource setResource = null;
	
	/** The uuid. */
	byte[] uuid = null;	
	
	/** The columns. */
	int rows, columns;
	
	/** The client resource. */
	ClientResource clientResource = null; 
		
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	public void setUp() {
        uuid = UUIDUtils.asByteArray(STUDY_UUID);        
    }

	/**
	 * Test to create a Covariance.
	 */
	@Test
	public void testCreate()
	{			
		Set<Covariance> covarianceSet = new HashSet<Covariance>();		
		Covariance covariance = new Covariance();						
			covariance.setName(COVARIANCE_NAME_1);
			rows=2;
			columns=3;
			covariance.setColumns(columns);
			covariance.setRows(rows);
			covariance.setRho(1.2);
			covariance.setDelta(2.5);
			double[] value = new double[1];
            value[0]= 10;
            covariance.setStandardDeviationListFromArray(value);			
				double[][] data = new double[rows][columns];
				for(int i=0; i<columns ; i++)
				{
					for(int j =0 ; j<rows; j++)
					{
						data[j][i]=2.0;
					}
				}
			covariance.setBlobFromArray(data);
		covarianceSet.add(covariance);	
		covariance = new Covariance();					
			covariance.setName(COVARIANCE_NAME_2);
			rows=5;
			columns=1;
			covariance.setColumns(columns);
			covariance.setRows(rows);
			covariance.setRho(1.1);
			covariance.setDelta(0.5);
			value = new double[1];
            value[0]= 0.5;
            covariance.setStandardDeviationListFromArray(value);			
				data = new double[rows][columns];
				for(int i=0; i<columns ; i++)
				{
					for(int j =0 ; j<rows; j++)
					{
						data[j][i]=2.0;
					}
				}
			covariance.setBlobFromArray(data);
		covarianceSet.add(covariance);		
		CovarianceSet set = new CovarianceSet(uuid,covarianceSet);		
		try
		{
		    System.clearProperty("http.proxyHost");
            clientResource = new ClientResource("http://localhost:8080/study/covarianceSet");
            setResource = clientResource.wrap(CovarianceSetResource.class);
            
		    set = setResource.create(set);			
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
	 * Test to retrieve a Covariance.
	 */
	@Test
	public void testRetrieve()
	{
		CovarianceSet covarianceSet = null;			
		
		try
		{
		    System.clearProperty("http.proxyHost");
            clientResource = new ClientResource("http://localhost:8080/study/covarianceSet");
            setResource = clientResource.wrap(CovarianceSetResource.class);
            
			covarianceSet = setResource.retrieve(uuid);			
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
        	System.out.println("testRetrieve() : ");
        	 Gson gson = new Gson();
             String json = gson.toJson(covarianceSet);  
             System.out.println(json);
            assertTrue(covarianceSet!=null);
        }
	}
	
	/**
	 * Test to update a Covariance.
	 */
	@Test
	public void testUpdate()
	{
	    Set<Covariance> covarianceSet = new HashSet<Covariance>();     
        Covariance covariance = new Covariance();						
			covariance.setName(COVARIANCE_NAME_1+" Updated");
			rows=10;
			columns=10;
			covariance.setColumns(columns);
			covariance.setRows(rows);
			covariance.setRho(1.2);
			covariance.setDelta(2.5);
			double[] value = new double[1];
			value[0]= 5;
			covariance.setStandardDeviationListFromArray(value);			
				double[][] data = new double[rows][columns];
				for(int i=0; i<columns ; i++)
				{
					for(int j =0 ; j<rows; j++)
					{
							data[j][i]=4.4;
					}
				}				
			covariance.setBlobFromArray(data);			
		covarianceSet.add(covariance);	
		CovarianceSet set = new CovarianceSet(uuid,covarianceSet);    				
		try
		{
		    System.clearProperty("http.proxyHost");
            clientResource = new ClientResource("http://localhost:8080/study/covarianceSet");
            setResource = clientResource.wrap(CovarianceSetResource.class);
            
			set = setResource.update(set);			
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
	 * Test to delete a Covariance.
	 */
	@Test
	public void testDelete()
	{
		CovarianceSet covarianceSet = null;			
		
		try
		{
		    System.clearProperty("http.proxyHost");
            clientResource = new ClientResource("http://localhost:8080/study/covarianceSet");
            setResource = clientResource.wrap(CovarianceSetResource.class);
            
			covarianceSet = setResource.remove(uuid);			
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
        	System.out.println("testDelete() : ");
        	Gson gson = new Gson();
            String json = gson.toJson(covarianceSet);  
            System.out.println(json);
           assertTrue(covarianceSet!=null);
            assertTrue(covarianceSet!=null);
        }
	}
		
}
