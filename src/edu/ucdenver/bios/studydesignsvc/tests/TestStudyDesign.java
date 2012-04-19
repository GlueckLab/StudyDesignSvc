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
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import junit.framework.TestCase;

import org.codehaus.jackson.map.JsonSerializer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.ClientResource;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import edu.ucdenver.bios.studydesignsvc.resource.StudyDesignResource;
import edu.ucdenver.bios.studydesignsvc.resource.StudyDesignRetrieveResource;
import edu.ucdenver.bios.studydesignsvc.resource.StudyDesignRetrieveServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.StudyDesignServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.StudyDesignUploadRetrieveResource;
import edu.ucdenver.bios.studydesignsvc.resource.StudyDesignUploadRetrieveServerResource;
import edu.ucdenver.bios.webservice.common.domain.SampleSize;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.domain.StudyDesignList;
import edu.ucdenver.bios.webservice.common.enums.SolutionTypeEnum;
import edu.ucdenver.bios.webservice.common.enums.StudyDesignViewTypeEnum;
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
	
	/** The retrieve resource. */
    StudyDesignRetrieveServerResource resourceRetrieve = new StudyDesignRetrieveServerResource();
	
	/** The resource. */
    StudyDesignUploadRetrieveServerResource resourceUpload = new StudyDesignUploadRetrieveServerResource();
	
	/** The client resource. */
	ClientResource clientStudyResource = null; 
	
	/** The client resource. */
    /*ClientResource clientStudyUploadResource = null;*/
	
	/** The study design resource. */
	StudyDesignResource studyDesignResource = null;
		
	/** The study design upload retrieve resource. */
    StudyDesignUploadRetrieveResource studyDesignUploadResource = null;
	
	/**
	 * Connect to the server.
	 */
    public void setUp()
    {       
    	uuid = UUIDUtils.asByteArray(STUDY_UUID);	
    	uuid_one = UUIDUtils.asByteArray(STUDY_UUID_ONE);
        try
        {
        	/*clientStudyResource = new ClientResource("http://localhost:8080/study/study"); 
            studyDesignResource = clientStudyResource.wrap(StudyDesignResource.class);
            
            clientStudyResource = new ClientResource("http://localhost:8080/study/studyUploadRetrieve"); 
            studyDesignUploadResource = clientStudyResource.wrap(StudyDesignUploadRetrieveResource.class);
            
            */
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
		studyDesign.setUuid(uuid);
		studyDesign.setParticipantLabel(PARTICIPANT_LABEL);
		studyDesign.setName(STUDY_NAME);
		studyDesign.setViewTypeEnum(StudyDesignViewTypeEnum.GUIDED_MODE);
		studyDesign.setGaussianCovariate(true);				
		studyDesign.setSolutionTypeEnum(SolutionTypeEnum.POWER);
		
		/*StudyDesign studyDesign1 = new StudyDesign();
        studyDesign1.setUuid(uuid_one);
        studyDesign1.setParticipantLabel(PARTICIPANT_LABEL);
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
            System.clearProperty("http.proxyHost");
            clientStudyResource = new ClientResource("http://localhost:8080/study/studyUploadRetrieve"); 
            studyDesignUploadResource = clientStudyResource.wrap(StudyDesignUploadRetrieveResource.class);
        	/*studyDesign=resource.create(studyDesign);*/
            studyDesign = studyDesignUploadResource.upload(studyDesign);
            
            /*studyDesign1 = resourceUpload.upload(studyDesign1);*/
        	
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
	
	@Test
    private void testUpload()   
    {   
        StudyDesign studyDesign = new StudyDesign();
        studyDesign.setUuid(uuid);
        studyDesign.setParticipantLabel(PARTICIPANT_LABEL);
        studyDesign.setName(STUDY_NAME);
        studyDesign.setViewTypeEnum(StudyDesignViewTypeEnum.GUIDED_MODE);
        studyDesign.setGaussianCovariate(true);             
        studyDesign.setSolutionTypeEnum(SolutionTypeEnum.POWER);
        
        /*StudyDesign studyDesign1 = new StudyDesign();
        studyDesign1.setUuid(uuid_one);
        studyDesign1.setParticipantLabel(PARTICIPANT_LABEL);
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
            System.clearProperty("http.proxyHost");
            clientStudyResource = new ClientResource("http://localhost:8080/study/studyUploadRetrieve"); 
            studyDesignUploadResource = clientStudyResource.wrap(StudyDesignUploadRetrieveResource.class);
            /*studyDesign=resource.create(studyDesign);*/
            studyDesign = studyDesignUploadResource.upload(studyDesign);
            
            /*studyDesign1 = resourceUpload.upload(studyDesign1);*/
            
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
	 * Test retrieve list.
	 */
	@Test
	private void testRetrieveList()
	{
	    StudyDesignList studyDesignList = null;
        try
        {
            System.clearProperty("http.proxyHost");
            clientStudyResource = new ClientResource("http://localhost:8080/study/studyUploadRetrieve"); 
            studyDesignUploadResource = clientStudyResource.wrap(StudyDesignUploadRetrieveResource.class);
            studyDesignList = studyDesignUploadResource.retrieve();          
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
            System.out.println("testRetrieveList():");            
           for(StudyDesign studyDesign : studyDesignList.getStudyDesignList())
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
            System.clearProperty("http.proxyHost");
            clientStudyResource = new ClientResource("http://localhost:8080/study/study"); 
            studyDesignResource = clientStudyResource.wrap(StudyDesignResource.class);
            studyDesign=studyDesignResource.remove(uuid);
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
	
	@Test
    private void testUpdate()   
    {   
        StudyDesign studyDesign = new StudyDesign();
        studyDesign.setUuid(uuid);
        studyDesign.setParticipantLabel(PARTICIPANT_LABEL);
        studyDesign.setName(STUDY_NAME);
        studyDesign.setViewTypeEnum(StudyDesignViewTypeEnum.GUIDED_MODE);
        studyDesign.setGaussianCovariate(true);             
        studyDesign.setSolutionTypeEnum(SolutionTypeEnum.POWER);
        
        /*StudyDesign studyDesign1 = new StudyDesign();
        studyDesign1.setUuid(uuid_one);
        studyDesign1.setParticipantLabel(PARTICIPANT_LABEL);
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
            System.clearProperty("http.proxyHost");
            clientStudyResource = new ClientResource("http://localhost:8080/study/study"); 
            studyDesignResource = clientStudyResource.wrap(StudyDesignResource.class);
            /*studyDesign=resource.create(studyDesign);*/
            studyDesign = studyDesignResource.update(studyDesign);
            
            /*studyDesign1 = resourceUpload.upload(studyDesign1);*/
            
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
        JsonRepresentation representation = null;
        try
        {
            /*Gson gson = new Gson();
            String json = gson.toJson(uuid_one);  
               System.out.println(json);*/
            /*JSONArray array = new JSONArray(uuid_one);
            representation = new JsonRepresentation(array);*/
            //System.out.println(representation);
                        
            /*representation = resourceRetrieve.retrieve(representation);
            Gson gson = new Gson();
            String json = gson.toJson(representation.getJsonObject());
            studyDesign = gson.fromJson(json, StudyDesign.class);*/
            
            //studyDesign = resourceRetrieve.retrieve(representation);          
            //JSONObject object = resourceRetrieve.retrieve(representation);
            System.clearProperty("http.proxyHost");
            clientStudyResource = new ClientResource("http://localhost:8080/study/study"); 
            studyDesignResource = clientStudyResource.wrap(StudyDesignResource.class);
            studyDesign = studyDesignResource.retrieve(uuid);
            
            /*Gson gson = new Gson();           
            String json = object.toString();
            System.out.println(json);
            studyDesign = gson.fromJson(json, StudyDesign.class);*/
            //studyDesign = new Gson().fromJson(representation.getJsonObject().getJSONArray(""+StudyDesign.class), StudyDesign.class);
            
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
}
