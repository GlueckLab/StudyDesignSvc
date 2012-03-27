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
import org.restlet.resource.ClientResource;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignConstants;
import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.ucdenver.bios.studydesignsvc.manager.ConfidenceIntervalManager;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.studydesignsvc.resource.BetaScaleResource;
import edu.ucdenver.bios.studydesignsvc.resource.ConfidenceIntervalServerResource;
import edu.ucdenver.bios.webservice.common.domain.BetaScale;
import edu.ucdenver.bios.webservice.common.domain.ConfidenceIntervalDescription;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;
// TODO: Auto-generated Javadoc
/**
 * JUnit test cases for 'ConfidenceIntervalDescription' - CRUD operations.
 * 
 * @author Uttara Sakhadeo
 */
public class TestConfidenceInterval extends TestCase 
{	
	
	/** The STUD y_ uuid. */
	private static UUID STUDY_UUID = UUID.fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");
	
	/** The SAMPL e_ size. */
	private static int SAMPLE_SIZE = 100;
	
	/** The uuid. */
	byte[] uuid = null;
	
	/** The resource. */
	ConfidenceIntervalServerResource resource = new ConfidenceIntervalServerResource();
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	public void setUp()
	{
		uuid = UUIDUtils.asByteArray(STUDY_UUID);		
	}
		
	/**
	 * Test to create a ConfidenceIntervalDescription.
	 */
	@Test
	public void testCreate()
	{	
		ConfidenceIntervalDescription confidenceInterval = new ConfidenceIntervalDescription();
		confidenceInterval.setSigmaFixed(true);		
		confidenceInterval.setSampleSize(SAMPLE_SIZE);
		confidenceInterval.setRankOfDesignMatrix(2);
		confidenceInterval.setBetaFixed(true);
		confidenceInterval.setSigmaFixed(true);
		confidenceInterval.setLowerTrailProbability(0.5f);
		confidenceInterval.setUpperTrailProbability(0.5f);
				
		try
		{
			confidenceInterval=resource.create(uuid,confidenceInterval);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			confidenceInterval=null;
			fail();
		}
		if(confidenceInterval==null)
		{
			fail();
		}
		else
		{
			System.out.println("testCreate() : ");
			System.out.println(confidenceInterval);
		}		
	}
	
	/**
	 * Test to update a ConfidenceIntervalDescription.
	 */
	@Test
	private void testUpdate()
	{	
		ConfidenceIntervalDescription confidenceInterval = new ConfidenceIntervalDescription();
		confidenceInterval.setSigmaFixed(true);		
		confidenceInterval.setSampleSize(SAMPLE_SIZE*2);
		confidenceInterval.setRankOfDesignMatrix(2);
		confidenceInterval.setBetaFixed(true);
		confidenceInterval.setSigmaFixed(true);
		confidenceInterval.setLowerTrailProbability(0.5f);
		confidenceInterval.setUpperTrailProbability(0.5f);
				
		try
		{
			confidenceInterval=resource.update(uuid,confidenceInterval);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			confidenceInterval=null;
			fail();
		}
		if(confidenceInterval==null)
		{
			fail();
		}
		else
		{
			System.out.println("ID: "+confidenceInterval.getId()+" SampleSize: "+confidenceInterval.getSampleSize());
		}
	}
	
	/**
	 * Test to retrieve a ConfidenceIntervalDescription.
	 */
	@Test
	public void testRetrieve()
	{
		ConfidenceIntervalDescription confidenceInterval = null;
		try
		{
			confidenceInterval = resource.retrieve(uuid);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			confidenceInterval=null;
			fail();
		}
		if (confidenceInterval == null)
        {
        	System.err.println("No matching confidence interval found");
        	fail();
        }
        else
        {     
        	System.out.println("testRetrieve() : ");
        	System.out.println(confidenceInterval);        	
            assertTrue(confidenceInterval!=null);
        }
	}
	
	/**
	 * Test to delete a ConfidenceIntervalDescription.
	 */
	@Test
	private void testDelete()
	{
		ConfidenceIntervalDescription confidenceInterval = null;
		try
		{
			confidenceInterval = resource.remove(uuid);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			confidenceInterval=null;
			fail();
		}
		if (confidenceInterval == null)
        {
        	System.err.println("No matching confidence interval found");
        	fail();
        }
        else
        {     
        	System.out.println("testDelete() : ");
        	System.out.println(confidenceInterval.getId()+" "+confidenceInterval.getSampleSize());        	
            assertTrue(confidenceInterval!=null);
        }
	}
		
}
