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
package edu.cudenver.bios.studydesignsvc.test.presentation;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import edu.cudenver.bios.studydesignsvc.application.StudyDesignConstants;
import edu.cudenver.bios.studydesignsvc.domain.ClusterNode;
import edu.cudenver.bios.studydesignsvc.domain.GenericTreeNode;
import edu.cudenver.bios.studydesignsvc.representation.ClusterXMLRepresentation;
import edu.cudenver.bios.studydesignsvc.resourcehelper.ClusterResourceHelper;

/**
 * Unit test for parsing of outgoing representation of a study design.
 * 
 * @author Uttara Sakhadeo
 */
public class TestClusterRepresentation extends TestCase 
{			
	 GenericTreeNode<ClusterNode> rootNode = null;
	private Document validDataDoc = null;
	//List<ClusterNode> listClusterNode = null;
	private static final String validData = 
			"<"+StudyDesignConstants.TAG_CLUSTERING+">"+
				"<"+StudyDesignConstants.TAG_CLUSTER+" "+StudyDesignConstants.ATTR_NAME+"=\"Country1\">"+
					"<"+StudyDesignConstants.TAG_CLUSTER+" "+StudyDesignConstants.ATTR_NAME+"=\"State1\">"+
						"<"+StudyDesignConstants.TAG_SAMPLE+" "+StudyDesignConstants.ATTR_NAME+"=\"participants\" "+StudyDesignConstants.ATTR_SIZE+"=\"50\"/>"+				
					"</"+StudyDesignConstants.TAG_CLUSTER+">"+				
					"<"+StudyDesignConstants.TAG_CLUSTER+" "+StudyDesignConstants.ATTR_NAME+"=\"State2\">"+
						"<"+StudyDesignConstants.TAG_SAMPLE+" "+StudyDesignConstants.ATTR_NAME+"=\"participants\" "+StudyDesignConstants.ATTR_SIZE+"=\"150\"/>"+				
					"</"+StudyDesignConstants.TAG_CLUSTER+">"+
				"</"+StudyDesignConstants.TAG_CLUSTER+">"+
				"<"+StudyDesignConstants.TAG_CLUSTER+" "+StudyDesignConstants.ATTR_NAME+"=\"Country2\">"+
					"<"+StudyDesignConstants.TAG_CLUSTER+" "+StudyDesignConstants.ATTR_NAME+"=\"State1\">"+
						"<"+StudyDesignConstants.TAG_SAMPLE+" "+StudyDesignConstants.ATTR_NAME+"=\"participants\" "+StudyDesignConstants.ATTR_SIZE+"=\"100\"/>"+				
					"</"+StudyDesignConstants.TAG_CLUSTER+">"+
					"<"+StudyDesignConstants.TAG_CLUSTER+" "+StudyDesignConstants.ATTR_NAME+"=\"State2\">"+
						"<"+StudyDesignConstants.TAG_SAMPLE+" "+StudyDesignConstants.ATTR_NAME+"=\"participants\" "+StudyDesignConstants.ATTR_SIZE+"=\"250\"/>"+				
					"</"+StudyDesignConstants.TAG_CLUSTER+">"+
					"<"+StudyDesignConstants.TAG_CLUSTER+" "+StudyDesignConstants.ATTR_NAME+"=\"State3\">"+
						"<"+StudyDesignConstants.TAG_SAMPLE+" "+StudyDesignConstants.ATTR_NAME+"=\"participants\" "+StudyDesignConstants.ATTR_SIZE+"=\"1000\"/>"+				
					"</"+StudyDesignConstants.TAG_CLUSTER+">"+
				"</"+StudyDesignConstants.TAG_CLUSTER+">"+
			"</"+StudyDesignConstants.TAG_CLUSTERING+">";
	
	private static final String expectedXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"+validData;
	
	/**
	 * Create some List
	 */
    public void setUp()
    { 
    	
    	//GenericTreeNode<ClusterNode> rootNode = new GenericTreeNode<ClusterNode>(new ClusterNode("Clutering",null));  
    	    	
    	/*listClusterNode = new ArrayList<ClusterNode>();      	
    	listClusterNode.add(new ClusterNode("Country1",null));
    	listClusterNode.add(new ClusterNode("State1",null));
    	listClusterNode.add(new ClusterNode("participants",50));
    	listClusterNode.add(new ClusterNode("State2",null));
    	listClusterNode.add(new ClusterNode("participants",150));    	    	
    	listClusterNode.add(new ClusterNode("Country2",null));
    	listClusterNode.add(new ClusterNode("State1",null));
    	listClusterNode.add(new ClusterNode("participants",100));
    	listClusterNode.add(new ClusterNode("State2",null));
    	listClusterNode.add(new ClusterNode("participants",250));
    	listClusterNode.add(new ClusterNode("State3",null));
    	listClusterNode.add(new ClusterNode("participants",1000));*/
    	    	
    	//List<GenericTreeNode<ClusterNode>> listTree = new ArrayList<GenericTreeNode<ClusterNode>();
    	
    	//rootNode.addChild(new GenericTreeNode<ClusterNode>(new ClusterNode("Country1")));
		
    	 try
         {
             DocumentBuilderFactory factory =  DocumentBuilderFactory.newInstance();
             DocumentBuilder builder = factory.newDocumentBuilder();
             
             validDataDoc = builder.parse(new InputSource(new StringReader(validData.toString())));
            rootNode = ClusterResourceHelper.parseCluster(validDataDoc.getDocumentElement());
         }
         catch (Exception e)
         {
             fail();
         }
    	
    }
    
        

    /**
     * Test that the representation matches the expected XML string above.
     */
    public void testListXMLRepresentation()
    {
        try
        {        	
        	/*ClusterXMLRepresentation rep = new ClusterXMLRepresentation(clusterNode);        	
        	StringWriter sw = new StringWriter();        	
        	rep.write(sw);         	
            assertEquals(sw.toString(), expectedXML);*/
        	
        	//ClusterXMLRepresentation rep = new ClusterXMLRepresentation(listClusterNode);
        	ClusterXMLRepresentation rep = new ClusterXMLRepresentation(rootNode);
        	StringWriter sw = new StringWriter();        	
        	rep.write(sw);         	
            assertEquals(sw.toString(), expectedXML);
        }
        catch(Exception e)
        {
            System.out.println("Exception during representation test: " + e.getMessage());
            e.printStackTrace();
            fail();
        }
    }
}
