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
import edu.ucdenver.bios.studydesignsvc.resource.SampleSizeResource;
import edu.ucdenver.bios.webservice.common.domain.SampleSize;
import edu.ucdenver.bios.webservice.common.domain.SampleSizeList;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;

// TODO: Auto-generated Javadoc
/**
 * JUnit test cases for 'Sample Size' object - CRUD operations.
 * 
 * @author Uttara Sakhadeo
 */
public class TestSampleSizeList extends TestCase
{
    
    /** The STUD y_ uuid. */
    private static UUID STUDY_UUID = UUID.fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");
    
    /** The resource. */
    SampleSizeResource resource = null;
    
    /** The uuid. */
    byte[] uuid = null;     
        
   
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    public void setUp() {
        uuid = UUIDUtils.asByteArray(STUDY_UUID);
        try
        {
            System.clearProperty("http.proxyHost");
            ClientResource clientResource = new ClientResource("http://localhost:8080/study/"+StudyDesignConstants.TAG_SAMPLE_SIZE_LIST);
            resource = clientResource.wrap(SampleSizeResource.class);            
        }
        catch (Exception e)
        {
            System.err.println("Failed to connect to server: " + e.getMessage());
            fail();
        }
    }
    
    /**
     * Test to create a SampleSize List.
     */
    @Test
    public void testCreate()
    {   
        
        List<SampleSize> list = new ArrayList<SampleSize>();     
        SampleSize sampleSize = new SampleSize();     
            sampleSize.setValue(5); 
        list.add(sampleSize); 
        sampleSize = new SampleSize();      
            sampleSize.setValue(1);           
        list.add(sampleSize);     
        SampleSizeList sampleSizeList = new SampleSizeList(uuid,list);          
        try
        {
            sampleSizeList = resource.create(sampleSizeList);          
        }       
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            sampleSizeList=null;
            fail();
        }
        if(sampleSizeList==null)
        {
            fail();
        }
        else
        {
            System.out.println("testCreate() : ");
            Gson gson = new Gson();
            String json = gson.toJson(sampleSizeList);  
            System.out.println(json);
           assertTrue(sampleSizeList!=null);
        }
    }   
    
    /**
     * Test to update a SampleSize List.
     */
    @Test
    private void testUpdate()
    {
        StudyDesign studyDesign = new StudyDesign();        
        studyDesign.setUuid(uuid);              
                
        List<SampleSize> list = new ArrayList<SampleSize>();          
        SampleSize sampleSize = new SampleSize();     
            sampleSize.setValue(11);    
        list.add(sampleSize); 
        sampleSize = new SampleSize();      
            sampleSize.setValue(22);            
        list.add(sampleSize);     
        SampleSizeList sampleSizeList = new SampleSizeList(uuid,list);        
        try
        {
            sampleSizeList = resource.update(sampleSizeList);          
        }       
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            sampleSizeList=null;
            fail();
        }
        if(sampleSizeList==null)
        {
            fail();
        }
        else
        {
            System.out.println("testUpdate() : ");
            Gson gson = new Gson();
            String json = gson.toJson(sampleSizeList);  
            System.out.println(json);
           assertTrue(sampleSizeList!=null);
        }
    }
    
    /**
     * Test to retrieve a SampleSize List.
     */
    @Test
    public void testRetrieve()
    {
        SampleSizeList sampleSizeList = null;         
        
        try
        {
            sampleSizeList = resource.retrieve(uuid);         
        }       
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            sampleSizeList=null;
            fail();
        }
        if (sampleSizeList == null)
        {
            System.err.println("No matching SampleSize found");
            fail();
        }
        else
        {     
            System.out.println("testRetrieve() : ");
            Gson gson = new Gson();
            String json = gson.toJson(sampleSizeList);  
            System.out.println(json);
           assertTrue(sampleSizeList!=null);
        }
    }
    
    /**
     * Test to delete a SampleSize List.
     */
    @Test
    private void testDelete()
    {
        SampleSizeList sampleSizeList = null;         
        
        try
        {
            sampleSizeList = resource.remove(uuid);           
        }       
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            sampleSizeList=null;
            fail();
        }
        if (sampleSizeList == null)
        {
            System.err.println("No matching SampleSize found");
            fail();
        }
        else
        {     
            System.out.println("testDelete() : ");
            Gson gson = new Gson();
            String json = gson.toJson(sampleSizeList);  
            System.out.println(json);
           assertTrue(sampleSizeList!=null);
        }
    }

}
