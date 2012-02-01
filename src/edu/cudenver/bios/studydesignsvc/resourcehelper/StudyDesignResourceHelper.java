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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.cudenver.bios.studydesignsvc.application.StudyDesignConstants;
import edu.cudenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.cudenver.bios.studydesignsvc.domain.NamedList;
import edu.cudenver.bios.studydesignsvc.domain.NamedRealMatrix;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;

/**
 * 
 * This is a utility class to parse an XML DOM Node containing the request XML
 * or "Entity Body" in RESTful terminology.  It provides all parsing necessary
 * for Study Design Services.
 *  
 * @author Uttara Sakhadeo
 */
public class StudyDesignResourceHelper 
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
		"' when parsing parameter object.  " +
		"It must be '" + expectedNodeName + "' for this service.";
		
		logger.info(msg);
		throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, msg);
	}
	
	/*public static StudyDesign parseStudyDesign(Node node)
			throws ResourceException
    {		
		//List<String> list = new ArrayList<String>();
		UUID uuid = null;
		NamedList namedList = null;
		List<NamedList> listOfNamedLists = null;
		NamedRealMatrix namedMatrix = null;
		List<NamedRealMatrix> listOfNamedRealMatrix = null;
		//make sure we have a list
    	if ( node.getNodeName()==null || ("".equals(node.getNodeName().trim())) )
        {
            notifyClientBadRequest(node.getNodeName().trim(), StudyDesignConstants.TAG_LIST_NAME);
        }
		
    	NamedNodeMap attrs = node.getAttributes();
    	
    	try
    	{
    		Node uuidStr = attrs.getNamedItem(StudyDesignConstants.TAG_STUDY_UUID);
        	if( uuidStr != null && !uuidStr.equals("")){
        		uuid = UUID.fromString(uuidStr.getNodeValue().toString());
        	}
        	else{
        		String msg = "Cannot find attribute '" +
        				StudyDesignConstants.TAG_STUDY_DESIGN+"' in this study design.";
        		logger.info(msg);
        		throw new IllegalArgumentException(msg);
        	}
    	}
    	catch(Exception exc)
    	{
    		exc.printStackTrace();
			String msg = "Study Service :String -> byte[] conversion exception in Resource class";
			logger.info(msg);
			throw new IllegalArgumentException(msg);
    	}
    	
    	StudyDesign studyDesign = new StudyDesign();
    	
    	studyDesign.setStudyUUID(uuid);
		NodeList children = node.getChildNodes();
		studyDesign.setName(children.item(0).getNodeName().toString());
		listOfNamedLists = new ArrayList<NamedList>();
		listOfNamedRealMatrix = new ArrayList<NamedRealMatrix>();
		
    	if (children != null)
    	{   
    		for(int i = 0; i < children.getLength(); i++)
    		{
    			Node textNode = children.item(i).getFirstChild();
    			// list
    			if (textNode.getNodeName().toLowerCase().endsWith(StudyDesignConstants.TAG_LIST.toLowerCase()))
    			{    				
    				namedList = ListResourceHelper.parseList(textNode);     				
    				listOfNamedLists.add(namedList);
    			}	    		    	
	    		// Matrix
    			else if (textNode.getNodeName().toLowerCase().endsWith(StudyDesignConstants.TAG_MATRIX.toLowerCase()))
    			{    				
    				namedMatrix = MatrixParamParser.getPositiveDefiniteParamsFromDomNode(textNode);     				
    				listOfNamedRealMatrix.add(namedMatrix);
    			}	    		
	    		// set other objects
    			//else if()
    			{
    				
    			}
    		}
    		
    		studyDesign.setListOfNamedLists(listOfNamedLists);
    		studyDesign.setListOfNamedRealMatrices(listOfNamedRealMatrix);
    	}
    	return studyDesign;
    }*/
}
