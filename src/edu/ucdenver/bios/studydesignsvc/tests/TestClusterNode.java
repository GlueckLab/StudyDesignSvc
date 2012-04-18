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
import edu.ucdenver.bios.studydesignsvc.manager.ClusterNodeManager;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.studydesignsvc.resource.BetweenParticipantResource;
import edu.ucdenver.bios.studydesignsvc.resource.ClusterNodeResource;
import edu.ucdenver.bios.studydesignsvc.resource.ClusterNodeServerResource;
import edu.ucdenver.bios.webservice.common.domain.ClusterNode;
import edu.ucdenver.bios.webservice.common.domain.ClusterNodeList;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;
/**
 * JUnit test cases for 'ClusterNode' object - CRUD operations.
 * 
 * @author Uttara Sakhadeo
 */
public class TestClusterNode extends TestCase
{
	
	/** The STUDY_UUID. */
	private static UUID STUDY_UUID = UUID.fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");	
	byte[] uuid = null;	
	StudyDesignManager studyDesignManager = null;
	ClusterNodeManager clusterNodeManager = null;
	ClusterNodeResource resource = null;
		
	public void setUp() {
        uuid = UUIDUtils.asByteArray(STUDY_UUID);
        try
        {
            System.clearProperty("http.proxyHost");
            ClientResource clientResource = new ClientResource("http://localhost:8080/study/"+StudyDesignConstants.TAG_CLUSTERING);
            resource = clientResource.wrap(ClusterNodeResource.class);            
        }
        catch (Exception e)
        {
            System.err.println("Failed to connect to server: " + e.getMessage());
            fail();
        }
    }
	
	/**
	 * Test to create a ClusterNode List
	 */
	@Test
	public void testCreate()
	{	
		
	    List<ClusterNode> list = new ArrayList<ClusterNode>();		
			ClusterNode clusterNode = new ClusterNode();		
			clusterNode.setGroupName("group1");
			clusterNode.setGroupSize(new Integer(10));			
			clusterNode.setNode(0);
			clusterNode.setParent(null);
		list.add(clusterNode);		
			clusterNode = new ClusterNode();		
			clusterNode.setGroupName("group2");	
			clusterNode.setGroupSize(new Integer(20));
			clusterNode.setNode(1);
			clusterNode.setParent(0);			
		list.add(clusterNode);		
		ClusterNodeList clusterNodeList = new ClusterNodeList(uuid,list);
		
		
		try
		{
			clusterNodeList = resource.create(clusterNodeList);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			clusterNodeList=null;
			fail();
		}
		if(clusterNodeList==null)
		{
			fail();
		}
		else
		{
			System.out.println("testCreate() : ");
			Gson gson = new Gson();
            String json = gson.toJson(clusterNodeList);  
            System.out.println(json);
            assertTrue(clusterNodeList!=null);
		}
	}
	
	/**
	 * Test to retrieve a ClusterNode List
	 */
	@Test
	public void testRetrieve()
	{		
		ClusterNodeList clusterNodeList = null;		
		
		try
		{
			clusterNodeList = resource.retrieve(uuid);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			clusterNodeList=null;
			fail();
		}
		if (clusterNodeList == null)
        {
        	System.err.println("No matching confidence interval found");
        	fail();
        }
        else
        {              	                    
        	System.out.println("testRead() : ");
			Gson gson = new Gson();
            String json = gson.toJson(clusterNodeList);  
            System.out.println(json);
            assertTrue(clusterNodeList!=null);
        }
	}
	
	/**
	 * Test to delete a ClusterNode List
	 */
	@Test
	public void testDelete()
	{		
	    ClusterNodeList clusterNodeList = null;		
		try
		{
			clusterNodeList = resource.remove(uuid);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			clusterNodeList=null;
			fail();
		}
		if (clusterNodeList == null)
        {
        	System.err.println("No matching confidence interval found");
        	fail();
        }
        else
        {         
        	System.out.println("testDelete() : ");
        	System.out.println(clusterNodeList);
            assertTrue(clusterNodeList!=null);
        }
	}
	
	/**
	 * Test to update a ClusterNode List
	 */
	@Test
	public void testUpdate()
	{			
		boolean flag;
		List<ClusterNode> list = new ArrayList<ClusterNode>();        		
			ClusterNode clusterNode = new ClusterNode();		
			clusterNode.setGroupName("group1");
			clusterNode.setGroupSize(new Integer(100));			
			clusterNode.setNode(0);
			clusterNode.setParent(null);
		list.add(clusterNode);		
			clusterNode = new ClusterNode();		
			clusterNode.setGroupName("group2");	
			clusterNode.setGroupSize(new Integer(200));
			clusterNode.setNode(1);
			clusterNode.setParent(0);			
		list.add(clusterNode);		
		ClusterNodeList clusterNodeList = new ClusterNodeList(uuid,list);	
		
		try
		{
			clusterNodeList = resource.update(clusterNodeList);			
		}		
		catch(Exception e)
		{
			System.out.println("Error : "+e.getMessage()+"\n"+e.getStackTrace());
			clusterNodeList=null;
			fail();
		}
		if (clusterNodeList == null)
        {
        	System.err.println("No matching confidence interval found");
        	fail();
        }
        else
        {         
        	System.out.println("testUpdate() : ");
        	System.out.println(clusterNodeList);
            assertTrue(clusterNodeList!=null);
        }
	}
}
