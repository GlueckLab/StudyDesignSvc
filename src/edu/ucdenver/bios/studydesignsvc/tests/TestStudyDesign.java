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
import org.restlet.resource.ClientResource;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.studydesignsvc.resource.StudyDesignResource;
import edu.ucdenver.bios.studydesignsvc.resource.StudyDesignServerResource;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.enums.SolutionTypeEnum;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;

/**
 * Test basic create, read, update, delete for TableStudyDesign
 * @author Sarah Kreidler
 *
 */
public class TestStudyDesign extends TestCase
{	
	
	private static UUID STUDY_UUID = UUID.fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");
	private static String STUDY_NAME = "Junit Test Study Design";
	private static String PARTICIPANT_LABEL = "subjects";
	private static int SAMPLE_SIZE = 100;
	byte[] uuid = null;

	StudyDesignServerResource resource = new StudyDesignServerResource();
	ClientResource clientResource = null; 
	StudyDesignResource studyDesignResource = null;
	/**
     * Connect to the server
     */
    public void setUp()
    {
    	uuid = UUIDUtils.asByteArray(STUDY_UUID);	
        try
        {
        	clientResource = new ClientResource("http://localhost:8080/study/study"); 
            studyDesignResource = clientResource.wrap(StudyDesignResource.class);
        }
        catch (Exception e)
        {
            System.err.println("Failed to connect to server: " + e.getMessage());
            fail();
        }
    }
    
    /**
     * Call the calculatePower function
     */
    private void testFunction()
    {    	
        // calculate power
        try
        {            
            StudyDesign studyDesign = studyDesignResource.create();
            System.err.println("Got object: " + studyDesign.toString());
            assertTrue(true);
        }
        catch (Exception e)
        {
            System.err.println("Failed to retrieve: " + e.getMessage());
            fail();
        }


    }

	
	/**
	 * Test retrieving a UUID from the database
	 * Note, this test must run after testCreate of a 
	 * not found will be thrown
	 */
	@Test
	public void testCreate()
	{	
		StudyDesign studyDesign = new StudyDesign();
		//studyDesign.setStudyUUID(STUDY_UUID);
		studyDesign.setParticipantLabel(PARTICIPANT_LABEL);
		studyDesign.setUuid(UUIDUtils.asByteArray(STUDY_UUID));
		studyDesign.setName(STUDY_NAME);
		studyDesign.setGaussianCovariate(true);				
		studyDesign.setSolutionTypeEnum(SolutionTypeEnum.POWER);				
		
        try
		{
        	studyDesign=resource.create(studyDesign);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			studyDesign=null;
			fail();
		}
		if(studyDesign==null)
		{
			fail();
		}
		else
		{
			System.out.println("ID: "+studyDesign.getUuid()+" SampleSize: "+studyDesign.getName());
		}
	}
	
	
	/**
	 * Test retrieving a UUID from the database
	 * Note, this test must run after testCreate of a 
	 * not found will be thrown
	 */
	@Test
	public void testRetrieve()
	{
		StudyDesign studyDesign = null;
		try
		{
			studyDesign = resource.retrieve(uuid);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			studyDesign=null;
			fail();
		}
		if (studyDesign == null)
        {
        	System.err.println("No matching confidence interval found");
        	fail();
        }
        else
        {     
        	System.out.println("testRetrieve() : ");        	        	
        	//System.out.println("Name: "+studyDesign.getName()+"\nConf Int: "+studyDesign.getConfidenceIntervalDescriptions().getId());
        	System.out.println(studyDesign);
            assertTrue(studyDesign!=null);
        }

	}

	@Test
	private void testRetrieveList()
	{
		StudyDesignManager manager = null;
		List<StudyDesign> studyDesignList = null;
        try
        {
            manager = new StudyDesignManager();
            manager.beginTransaction();
            studyDesignList = manager.getStudyUUIDs();
            manager.commit();
        }
        catch (BaseManagerException bme)
        {
        	System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error("Failed to load Study Design information: " + bme.getMessage());
            if (manager != null) try { manager.rollback(); } catch (BaseManagerException e) {}
            studyDesignList = null;
            fail();
        }
        catch (StudyDesignException sde)
        {
        	System.out.println(sde.getMessage());
            StudyDesignLogger.getInstance().error("Failed to load Study Design information: " + sde.getMessage());
            if (manager != null) try { manager.rollback(); } catch (BaseManagerException e) {}
            studyDesignList = null;
            fail();
        }
        
        
        if (studyDesignList == null)
        {
        	System.err.println("No matching studydesign found");
        	fail();
        }
        else
        {
            String name = studyDesignList.get(0).getName();
            assertTrue(STUDY_NAME.equals(name));
        }

	}

	
	/**
	 * Test deletion of record from the table
	 */
	@Test
	private void testDelete()
	{
		StudyDesignManager manager = null;
		StudyDesign studyDesign = null;
        try
        {
            manager = new StudyDesignManager();
            manager.beginTransaction();
            studyDesign = manager.delete(UUIDUtils.asByteArray(STUDY_UUID));
            manager.commit();
        }
        catch (BaseManagerException bme)
        {
        	System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error("Failed to delete Study Design information: " + bme.getMessage());
            if (manager != null) try { manager.rollback(); } catch (BaseManagerException e) {}
            studyDesign = null;
            fail();
        }
        catch (StudyDesignException sde)
        {
        	System.out.println(sde.getMessage());
            StudyDesignLogger.getInstance().error("Failed to delete Study Design information: " + sde.getMessage());
            if (manager != null) try { manager.rollback(); } catch (BaseManagerException e) {}
            studyDesign = null;
            fail();
        }
        
        
        if (studyDesign == null)
        {
        	System.err.println("No matching studydesign found");
        	fail();
        }
        else
        {
            String name = studyDesign.getName();
            assertTrue(STUDY_NAME.equals(name));
        }
	}
}
