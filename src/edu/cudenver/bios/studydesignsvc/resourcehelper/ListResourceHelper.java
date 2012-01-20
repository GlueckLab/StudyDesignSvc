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
import edu.cudenver.bios.studydesignsvc.domain.NamedList;

/**
 * 
 * This is a utility class to parse an XML DOM Node containing the request XML
 * or "Entity Body" in RESTful terminology.  It provides all parsing necessary
 * for Study Design Services.
 * 
 * @author Uttara Sakhadeo
 */
public class ListResourceHelper 
{
	private static Logger logger = StudyDesignLogger.getInstance();
	 /**
     *  Parse the list to get its type.
     *  
     * @param params parameter object being built
     * @param children children of the testList tag
     */   
	/*public static String parseListForListName(Node node)
	{
		
		NodeList children = node.getChildNodes();
		String listName = children.item(0).getNodeName();		
		return listName;
	}*/
		
	/**
     *  Parse the list of List<String> values to be included with this Study Design
     *  
     * @param params parameter object being built
     * @param children children of the testList tag
     */    	
	/*public static List<String> parseList(Node node)
	throws ResourceException
    {		
		ArrayList<String> list = new ArrayList<String>();
		NodeList children = node.getChildNodes();
    	if (children != null)
    	{
    		// Parse all of the values in the list
    		for(int i = 0; i < children.getLength(); i++)
    		{
    			Node textNode = children.item(i).getFirstChild();
    			if (StudyDesignConstants.TAG_LIST_ELEMENT.equals(textNode.getNodeName()))
    			{
					list.add(textNode.getNodeValue().toString());
    			}

    		}
    	}
    	return list;
    }*/
	
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
	
	
	public static NamedList parseList(Node node)
	throws ResourceException
    {		
		NamedList namedList = null;
		
		//make sure we have a list
    	if ( node.getNodeName()==null || ("".equals(node.getNodeName().trim())) )
        {
            notifyClientBadRequest(node.getNodeName().trim(), StudyDesignConstants.TAG_LIST_NAME);
        }    	    
    	    	 
		NodeList children = node.getChildNodes();	
		System.out.println(children.getLength());
		//System.out.println(""+children.getLength());
		
    	if (children != null && children.getLength()!=0)
    	{   
    		namedList = new NamedList();    	
        	namedList.setName(node.getNodeName().toLowerCase().toString());
               	    		
    		for(int i = 0; i < children.getLength(); i++)
    		{
    			Node textNode = children.item(i).getFirstChild();    			
    			if(StudyDesignConstants.TAG_LIST_ELEMENT.toString().equalsIgnoreCase(children.item(0).getNodeName().toString()))
    			{    				
    				namedList.addEntry(textNode.getNodeValue());
    			}    			
    			else
    			{
    				notifyClientBadRequest(node.getNodeName().trim(), StudyDesignConstants.TAG_LIST_NAME);
    			}
    		}    	
    	}
    	else
    	{
    		notifyClientBadRequest(node.getNodeName().trim(), StudyDesignConstants.TAG_LIST_NAME);
    	}
    	return namedList;
    }
}
