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

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.studydesignsvc.resource.StudyDesignResource;
import edu.ucdenver.bios.studydesignsvc.resource.StudyDesignServerResource;
import edu.ucdenver.bios.webservice.common.domain.SampleSize;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.enums.SolutionTypeEnum;
import edu.ucdenver.bios.webservice.common.enums.StudyDesignViewTypeEnum;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;

// TODO: Auto-generated Javadoc
/**
 * Test basic create, read, update, delete for TableStudyDesign.
 *
 * @author Sarah Kreidler
 */
public class TestStudyDesign extends TestCase
{	
	
	/** The STUDY_UUID. */
	private static UUID STUDY_UUID = UUID.fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");
	
	/** The STUDY_UUID_ONE. */
    private static UUID STUDY_UUID_ONE = UUID.fromString("66ccfd20-4478-11e1-9641-0002a5d5c51b");
	
	/** The STUD y_ name. */
	private static String STUDY_NAME = "Junit Test Study Design";
	
	/** The PARTICIPAN t_ label. */
	private static String PARTICIPANT_LABEL = "subjects";
	
	/** The SAMPL e_ size. */
	private static int SAMPLE_SIZE = 100;
	
	/** The uuid. */
	byte[] uuid = null;
	
	/** The uuid_one. */
    byte[] uuid_one = null;

	/** The resource. */
	StudyDesignServerResource resource = new StudyDesignServerResource();
	
	/** The client resource. */
	ClientResource clientResource = null; 
	
	/** The study design resource. */
	StudyDesignResource studyDesignResource = null;
	
	/**
	 * Connect to the server.
	 */
    public void setUp()
    {
    	uuid = UUIDUtils.asByteArray(STUDY_UUID);	
    	uuid_one = UUIDUtils.asByteArray(STUDY_UUID_ONE);
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
     * Call the calculatePower function.
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
	 * not found will be thrown.
	 */
	@Test
	public void testCreate()	
	{	
		StudyDesign studyDesign = new StudyDesign();
		//studyDesign.setStudyUUID(STUDY_UUID);
		studyDesign.setParticipantLabel(PARTICIPANT_LABEL);
		studyDesign.setUuid(UUIDUtils.asByteArray(STUDY_UUID));
		studyDesign.setName(STUDY_NAME);
		studyDesign.setViewTypeEnum(StudyDesignViewTypeEnum.GUIDED_MODE);
		studyDesign.setGaussianCovariate(true);				
		studyDesign.setSolutionTypeEnum(SolutionTypeEnum.POWER);
		
		/*StudyDesign studyDesign1 = new StudyDesign();
        //studyDesign.setStudyUUID(STUDY_UUID);
        studyDesign1.setParticipantLabel(PARTICIPANT_LABEL);
        studyDesign1.setUuid(UUIDUtils.asByteArray(STUDY_UUID_ONE));
        studyDesign1.setName(STUDY_NAME+" 1");
        studyDesign1.setViewTypeEnum(StudyDesignViewTypeEnum.GUIDED_MODE);
        studyDesign1.setGaussianCovariate(true);             
        studyDesign1.setSolutionTypeEnum(SolutionTypeEnum.POWER);
            List<SampleSize> sampleSizeList = new ArrayList<SampleSize>();        
            SampleSize sampleSize = new SampleSize();     
                sampleSize.setValue(5); 
            sampleSizeList.add(sampleSize); 
            sampleSize = new SampleSize();      
                sampleSize.setValue(1);           
            sampleSizeList.add(sampleSize);     
		studyDesign1.setSampleSizeList(sampleSizeList);*/
		
        try
		{
        	studyDesign=resource.create(studyDesign);
        	
        	/*studyDesign1=resource.create(studyDesign1);*/
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
			System.out.println("testCreate():");
			 Gson gson = new Gson();
	            String json = gson.toJson(studyDesign);  
	            System.out.println(json);
	           assertTrue(studyDesign!=null);
		}
	}
	
	
	/**
	 * Test retrieving a UUID from the database
	 * Note, this test must run after testCreate of a
	 * not found will be thrown.
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
            System.out.println("testRetrieve():");
            Gson gson = new Gson();
               String json = gson.toJson(studyDesign);  
               System.out.println(json);
              assertTrue(studyDesign!=null);
        }

	}

	/**
	 * Test retrieve list.
	 */
	@Test
	public void testRetrieveList()
	{
	    List<StudyDesign> studyDesignList = null;
        try
        {
            studyDesignList = resource.retrieve();          
        }       
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            studyDesignList=null;
            fail();
        }
        if (studyDesignList == null)
        {
            System.err.println("No matching confidence interval found");
            fail();
        }
        else
        {     
            System.out.println("testRetrieveList():"+studyDesignList.size());            
           for(StudyDesign studyDesign : studyDesignList)
           {
               System.out.println(studyDesign);
           }
        }

	}

	
	/**
	 * Test deletion of record from the table.
	 */
	@Test
	private void testDelete()
	{
	    StudyDesign studyDesign = null;                
        
        try
        {
            /*studyDesign=resource.remove(uuid_one);*/
            
            studyDesign=resource.remove(uuid);
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
            System.out.println("testDelete():");
             Gson gson = new Gson();
                String json = gson.toJson(studyDesign);  
                System.out.println(json);
               assertTrue(studyDesign!=null);
        }
	}
}
