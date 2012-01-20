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
package edu.cudenver.bios.studydesignsvc.representation;

import java.io.IOException;
import java.util.List;

import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.cudenver.bios.studydesignsvc.application.StudyDesignConstants;
import edu.cudenver.bios.studydesignsvc.domain.ClusterNode;
import edu.cudenver.bios.studydesignsvc.domain.GenericTreeNode;
import edu.cudenver.bios.studydesignsvc.domain.RepeatedMeasures;
/**
 * Class which converts a Cluster object to an XML DOM
 * 
 * @author Uttara Sakhadeo
 */
public class ClusterXMLRepresentation extends DomRepresentation
{
	/**
	 * Constructor
	 * @param generic List object
	 * @throws IOException
	 */
    /*public ClusterXMLRepresentation(List<ClusterNode> list) throws IOException 
	{    	
        super(MediaType.APPLICATION_XML);        
        Document doc = getDocument();        
        Element rootElement = createClusteringElement(doc, list);                
       	doc.appendChild(rootElement);
       	//System.out.println(rootElement.toString());
        doc.normalizeDocument();        
	}*/
	public ClusterXMLRepresentation(GenericTreeNode<ClusterNode> tree) throws IOException 
	{    	
        super(MediaType.APPLICATION_XML);        
        Document doc = getDocument();        
        Element listRootElement = doc.createElement(StudyDesignConstants.TAG_CLUSTERING);
        //Element rootElement = createClusteringElement(doc, tree,listRootElement);                
        createClusteringElement(doc, tree,listRootElement);
       	doc.appendChild(listRootElement);
       	//System.out.println(rootElement.toString());
        doc.normalizeDocument();        
	}
    
    /**
     * Create a DOM element from a a generic List object.  
     * 
     * @param doc the DOM document
     * @param List object
     * @return DOM node for a single power result
     */
    /*public static Element createClusteringElement(Document doc, List<ClusterNode> list) 
    {    	    	
    	//System.out.println(list.getName()+":"+list.getDataList());
    	Element listRootElement = doc.createElement(StudyDesignConstants.TAG_CLUSTERING);
    	Element listElement = null;
    	Element parent = null;
               	               
        for(ClusterNode cluster : list)
        {        	        	
        	if(cluster.getSampleSize()==null)
        	{
        		listElement = doc.createElement(StudyDesignConstants.TAG_SAMPLE);        		   
        		listElement.setAttribute(StudyDesignConstants.ATTR_NAME, cluster.getClusterName());
        		listElement.setAttribute(StudyDesignConstants.ATTR_NAME, ""+cluster.getSampleSize());    
        		if(list.size()==1)
            		listRootElement.appendChild(listElement);
            	else
            		parent.appendChild(listElement);
        		parent = listRootElement;
        	}
        	else
        	{	
        		Element element = doc.createElement(StudyDesignConstants.TAG_CLUSTER);
        		element.setAttribute(StudyDesignConstants.ATTR_NAME, cluster.getClusterName());
        		if(list.indexOf(cluster)==0)
        			listRootElement.appendChild(element);
        		else
        			parent.appendChild(element);        		
        		parent = element;
        	}        	
        }        	       	        	      
        return listRootElement;
    }*/
	public static void createClusteringElement(Document doc, GenericTreeNode<ClusterNode> tree, Element listRootElement) 
    {    	    	
    	//System.out.println(list.getName()+":"+list.getDataList());
    	
    	Element clusterElement = null;    	
    	
    	List<GenericTreeNode<ClusterNode>> children = tree.getChildren();    	    	
    	for(GenericTreeNode<ClusterNode> treeNode : children)
    	{
    		ClusterNode clusterNode = treeNode.getData();
    		
    		if(clusterNode.getSampleSize()==null)
    		{
    			clusterElement = doc.createElement(StudyDesignConstants.TAG_CLUSTER);
    		}
    		else
    		{
    			clusterElement = doc.createElement(StudyDesignConstants.TAG_SAMPLE);
    			clusterElement.setAttribute(StudyDesignConstants.ATTR_SIZE, ""+clusterNode.getSampleSize());      
    		}        	        	
        	listRootElement.appendChild(clusterElement);
        	clusterElement.setAttribute(StudyDesignConstants.ATTR_NAME, clusterNode.getClusterName());
        	 
        	createClusteringElement(doc,treeNode,clusterElement);
    	}            	        	     
        //return listRootElement;
    }
}
