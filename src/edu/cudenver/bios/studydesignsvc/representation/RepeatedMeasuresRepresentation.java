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
 * Class which converts a RepeatedMesures object to an XML DOM
 * 
 * @author Uttara Sakhadeo
 */
public class RepeatedMeasuresRepresentation extends DomRepresentation
{
	/**
	 * Constructor
	 * @param generic List object
	 * @throws IOException
	 */
    public RepeatedMeasuresRepresentation(GenericTreeNode<RepeatedMeasures> tree) throws IOException 
	{    	
    	super(MediaType.APPLICATION_XML);        
        Document doc = getDocument();        
        Element listRootElement = doc.createElement(StudyDesignConstants.TAG_REPEATEDMEASURES);
        //Element rootElement = createClusteringElement(doc, tree,listRootElement);                
        createRepeatedMeasuresElement(doc, tree, listRootElement);
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
    public static void createRepeatedMeasuresElement(Document doc, GenericTreeNode<RepeatedMeasures> tree, Element listRootElement) 
    {    	    	    	    	
    	List<GenericTreeNode<RepeatedMeasures>> children = tree.getChildren();   	
    	Element dimensionElement = null;
    	//Element parent = null;    	   	     	              
    	
        for(GenericTreeNode<RepeatedMeasures> treeNode : children)
        {    
        	RepeatedMeasures repeatedMeasures = treeNode.getData();
        	
        	dimensionElement = doc.createElement(StudyDesignConstants.TAG_DIMENSION); 
        	listRootElement.appendChild(dimensionElement);
        	dimensionElement.setAttribute(StudyDesignConstants.ATTR_NAME, repeatedMeasures.getName());
        	dimensionElement.setAttribute(StudyDesignConstants.ATTR_TYPE, repeatedMeasures.getType());
        	dimensionElement.setAttribute(StudyDesignConstants.ATTR_COUNT, repeatedMeasures.getCount().toString());
        	List<Integer> spacingList = repeatedMeasures.getSpacingList();
        	if(spacingList!=null)
        	{
        		Element spacingElement = doc.createElement(StudyDesignConstants.TAG_SPACING); 
        		dimensionElement.appendChild(spacingElement);    			
        		for(Integer spacing : spacingList)
        		{
        			Element element = doc.createElement(StudyDesignConstants.TAG_LIST_ELEMENT);
        			dimensionElement.appendChild(element);
        			element.setTextContent(spacing.toString());         			
        		}
        	}                
        	createRepeatedMeasuresElement(doc, treeNode, dimensionElement);
        }        	       	        	      
        //return listRootElement;
    }
}
