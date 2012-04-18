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
import edu.ucdenver.bios.studydesignsvc.resource.ResponsesResource;
import edu.ucdenver.bios.webservice.common.domain.ResponseList;
import edu.ucdenver.bios.webservice.common.domain.ResponseNode;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;
// TODO: Auto-generated Javadoc
/**
 * JUnit test cases for 'ResponseNode' object - CRUD operations.
 * 
 * @author Uttara Sakhadeo
 */
public class TestResponseList extends TestCase
{
	
	/** The STUD y_ uuid. */
	private static UUID STUDY_UUID = UUID.fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");
	
	/** The STUD y_ name. */
	private static String STUDY_NAME = "Junit Test Study Design";
	
	/** The resource. */
	ResponsesResource resource = null;
	
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
            ClientResource clientResource = new ClientResource("http://localhost:8080/study/"+StudyDesignConstants.TAG_RESPONSE_LIST);
            resource = clientResource.wrap(ResponsesResource.class);            
        }
        catch (Exception e)
        {
            System.err.println("Failed to connect to server: " + e.getMessage());
            fail();
        }
    }
	
	/**
	 * Test to create a Response List.
	 */
	@Test
	public void testCreate()
	{			
	    List<ResponseNode> list = new ArrayList<ResponseNode>();	
		ResponseNode ResponseNode = new ResponseNode();		
			ResponseNode.setName("node1");	
		list.add(ResponseNode);	
		ResponseNode = new ResponseNode();		
			ResponseNode.setName("node2");			
		list.add(ResponseNode);		
		ResponseList responseList = new ResponseList(uuid,list);   		
		try
		{
			responseList = resource.create(responseList);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			responseList=null;
			fail();
		}
		if(responseList==null)
		{
			fail();
		}
		else
		{
			System.out.println("testCreate() : ");
			System.out.println(responseList);
		}
	}	
	
	/**
	 * Test update.
	 */
	@Test
	private void testUpdate()
	{
	    List<ResponseNode> list = new ArrayList<ResponseNode>();	
		ResponseNode responseNode = new ResponseNode();		
			responseNode.setName("node_11");	
		list.add(responseNode);	
		responseNode = new ResponseNode();		
			responseNode.setName("node_22");			
		list.add(responseNode);		
		ResponseList responseList = new ResponseList(uuid,list);   		
		try
		{
			responseList = resource.update(responseList);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			responseList=null;
			fail();
		}
		if(responseList==null)
		{
			fail();
		}
		else
		{
			System.out.println("testUpdate");
			System.out.println(responseList);
		}
	}
	
	/**
	 * Test to delete a Response List.
	 */
	@Test
	private void testDelete()
	{
	    ResponseList responseList = null;			
		
		try
		{
			responseList = resource.remove(uuid);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			responseList=null;
			fail();
		}
		if (responseList == null)
        {
        	System.err.println("No matching Response Node List found");
        	fail();
        }
        else
        {     
        	System.out.println("testDelete() : ");
        	System.out.println(responseList);
            assertTrue(responseList!=null);
        }
	}
	
	/**
	 * Test to Retrieve a Response List.
	 */
	@Test
	public void testRetrieve()
	{
		ResponseList responseList = null;			
		
		try
		{
			responseList = resource.retrieve(uuid);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			responseList=null;
			fail();
		}
		if (responseList == null)
        {
        	System.err.println("No matching confidence interval found");
        	fail();
        }
        else
        {     
        	System.out.println("testRetrieve() : ");        	
        	 Gson gson = new Gson();
             String json = gson.toJson(responseList);  
             System.out.println(json);
            assertTrue(responseList!=null);
        }
	}
}
