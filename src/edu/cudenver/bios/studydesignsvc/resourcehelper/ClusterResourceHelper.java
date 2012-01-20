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
package edu.cudenver.bios.studydesignsvc.resourcehelper;

import org.apache.log4j.Logger;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.cudenver.bios.studydesignsvc.application.StudyDesignConstants;
import edu.cudenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.cudenver.bios.studydesignsvc.domain.ClusterNode;
import edu.cudenver.bios.studydesignsvc.domain.GenericTreeNode;
/**
 * 
 * This is a utility class to parse an XML DOM Node containing the request XML
 * or "Entity Body" in RESTful terminology.  It provides all parsing necessary
 * for Study Design Services.
 * 
 * @author Uttara Sakhadeo
 */
public class ClusterResourceHelper 
{
	private static Logger logger = StudyDesignLogger.getInstance();		
	/**
	 * This is a convenience method to throw a ResourceException if the
	 * request XML's root node is incorrect.
	 * @param nodeName is the name that was found in the XML for the root node.
	 * @param expectedNodeName is the expected root node name for this service.
	 */
	private static void notifyClientBadRequest(String nodeName, String expectedNodeName)
	throws ResourceException
	{
		String msg = "Invalid node '" + nodeName + 
		"' when parsing parameter object.  ";
		
		logger.info(msg);
		throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, msg);
	}		
	public static GenericTreeNode<ClusterNode> parseCluster(Node node)
	throws ResourceException
    {		
		GenericTreeNode<ClusterNode> rootNode = new GenericTreeNode<ClusterNode>(new ClusterNode(StudyDesignConstants.TAG_CLUSTERING));								    	    		
		parseClusterNode(node,rootNode);    	
		return rootNode;		    
    }
	
	//public static GenericTreeNode<ClusterNode> parseClusterNode(Node node,GenericTreeNode<ClusterNode> rootNode)
	private static void parseClusterNode(Node node,GenericTreeNode<ClusterNode> rootNode)
	throws ResourceException
    {		
		//GenericTreeNode<ClusterNode> treeNode = null;
		ClusterNode clusterNode = null;
		
		NodeList children = node.getChildNodes();
		
		if (children != null && children.getLength()!=0)
    	{  			
			for(int i = 0; i < children.getLength(); i++)
			{			
				Node child = children.item(i);
				
				if(children.item(i).getAttributes().getNamedItem(StudyDesignConstants.ATTR_SIZE)!=null)
				{
					clusterNode = new ClusterNode(children.item(i).getAttributes().getNamedItem(StudyDesignConstants.ATTR_NAME).getNodeValue()
							,Integer.parseInt(children.item(i).getAttributes().getNamedItem(StudyDesignConstants.ATTR_SIZE).getNodeValue()));			
				}
				else
				{
					clusterNode = new ClusterNode(children.item(i).getAttributes().getNamedItem(StudyDesignConstants.ATTR_NAME).getNodeValue());
				}
				/*treeNode = new GenericTreeNode<ClusterNode>(clusterNode);
				rootNode.addChild(treeNode);*/				
				GenericTreeNode<ClusterNode> newRoot = new GenericTreeNode<ClusterNode>(clusterNode);
				rootNode.addChild(newRoot);				
				if(clusterNode.getSampleSize()==null)
					parseClusterNode(child,newRoot);
			}
//			for(int i = 0; i < children.getLength(); i++)
//			{
//				parseClusterNode(children.item(i),rootNode.getChildAt(i));
//			}
    	}	
		else
		{
			notifyClientBadRequest(node.getNodeName().trim(),StudyDesignConstants.TAG_CLUSTERING);
		}
		//return treeNode;
    }
		
}
