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
package edu.cudenver.bios.studydesignsvc.resource;

import java.io.IOException;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.Resource;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;


import edu.cudenver.bios.studydesignsvc.application.StudyDesignConstants;
import edu.cudenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.cudenver.bios.studydesignsvc.domain.ResponseList;
import edu.cudenver.bios.studydesignsvc.representation.ErrorXMLRepresentation;
import edu.cudenver.bios.studydesignsvc.representation.ListXMLRepresentation;

/**
 * Resource for handling requests for Response List.
 * See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class ResponseListResource extends ServerResource 
{
	private static Logger logger = StudyDesignLogger.getInstance();	
	private UUID StudyUUID = null;
	
	/**
	 * Create a new resource to handle list requests.  Data
	 * is returned as XML.
	 * 
	 * @param context restlet context
	 * @param request http request object
	 * @param response http response object
	 */
    /*public ResponseListResource(Context context, Request request, Response response) 
    {
       // super(context, request, response);
    	super();
        getVariants().add(new Variant(MediaType.APPLICATION_XML));
        // Determine UUID from URL
        this.StudyUUID = UUID.fromString(request.getAttributes().get(StudyDesignConstants.TAG_STUDY_UUID).toString());         
    }*/
    
       
   /**
     * Process a GET request to Return a full representation of a given variant.
     * Please see REST API documentation for details on the entity 
     * body format.
     * 
     * @param entity variant
     */ 
    @Get
    public Representation represent(Variant variant) throws ResourceException
    {
    	DomRepresentation domRep = null;    	    	       	
    	ResponseList responseList = null;
    	ListXMLRepresentation responseForSingleListRead = null;    	
    	try
        {	    	    
    		//list = ListResourceHelper.parseList(domRep.getDocument().getDocumentElement());
    		responseList = new ResponseList();
    		responseList.setName(StudyDesignConstants.TAG_RESPONSE_LIST);
    		responseList.setStudyUUID(this.StudyUUID);
    		
    		// Fetch list fom db -> Java
	    	/*
	    	 * Hibernate query (READ)
	    	 */
    		
    		// Java -> XML : Create our response XML
    		responseForSingleListRead = new ListXMLRepresentation(responseList);
    		getResponse().setEntity(responseForSingleListRead); 
	        getResponse().setStatus(Status.SUCCESS_CREATED);	        		    		    	   		    		    		    	          	    		        
        }
    	catch (IOException ioe)
        {
            StudyDesignLogger.getInstance().error(ioe.getMessage());
            try { getResponse().setEntity(new ErrorXMLRepresentation(ioe.getMessage())); }
            catch (IOException e) {}
            getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
        catch (IllegalArgumentException iae)
        {
        	StudyDesignLogger.getInstance().error(iae.getMessage());
            try { getResponse().setEntity(new ErrorXMLRepresentation(iae.getMessage())); }
            catch (IOException e) {}
            getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
        /*catch (ResourceException re)
        {
        	StudyDesignLogger.getInstance().error(re.getMessage());
            try { getResponse().setEntity(new ErrorXMLRepresentation(re.getMessage())); }
            catch (IOException e) {}
            getResponse().setStatus(re.getStatus());
        } */
    	return domRep;
    }
    
    /**
     * Process a DELETE request to delete a List from the data base.
     * Please see REST API documentation for details on the entity 
     * body format.
     * 
     * @param entity HTTP entity body for the request
     * @return 
     */
    @Delete
    public Representation delete()
    {
    	// Get request XML
    	//DomRepresentation domRep = new DomRepresentation(entity);
    	// Determine UUID from URL
    	//List<String> list = null;
    	ResponseList responseList = null;
    	ListXMLRepresentation response  = null;
    	// 
    	try
        {	    	
	    	//this.tagName = request.getAttributes().get(StudyDesignConstants.TAG_LIST_NAME).toString();
	    	String tagName = this.getRequest().getAttributes().get(StudyDesignConstants.TAG_RESPONSE_LIST).toString();
	    	if (tagName == null || tagName.isEmpty()) ;
	    		//throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "no list name specified");
	    	
	    	responseList = new ResponseList();
    		responseList.setName(tagName);
    		responseList.setStudyUUID(this.StudyUUID);
	    		    	
	    	// Fetch list fom db -> Java
	    	/*
	    	 * Hibernate query (READ)
	    	 */
	    	
	    	// delete record
	    	/*
	    	 * Hibernate query (DELETE)
	    	 */
	    	
	    	// delete list from the StudyDesign Object
	    	
	    	// Java -> XML
	    	
	    	// Create our response XML   - how to represent for all lists?	    	
	    	//ListXMLRepresentation response = new ListXMLRepresentation(list, tagName);
	    	response = new ListXMLRepresentation(responseList);
	        getResponse().setEntity(response); 
	        getResponse().setStatus(Status.SUCCESS_CREATED);
        }
    	catch (IOException ioe)
        {
            StudyDesignLogger.getInstance().error(ioe.getMessage());
            try { getResponse().setEntity(new ErrorXMLRepresentation(ioe.getMessage())); }
            catch (IOException e) {}
            getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
        catch (IllegalArgumentException iae)
        {
        	StudyDesignLogger.getInstance().error(iae.getMessage());
            try { getResponse().setEntity(new ErrorXMLRepresentation(iae.getMessage())); }
            catch (IOException e) {}
            getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
        /*catch (ResourceException re)
        {
        	StudyDesignLogger.getInstance().error(re.getMessage());
            try { getResponse().setEntity(new ErrorXMLRepresentation(re.getMessage())); }
            catch (IOException e) {}
            getResponse().setStatus(re.getStatus());
        }*/
    	return response;
    }         
    
    
    /**
     * Process a PUT request to update a List in the data base.
     * Please see REST API documentation for details on the entity 
     * body format.
     * PUT works as create/update
     * @param entity HTTP entity body for the request
     */
    @Put
    public void storeRepresentation(Representation entity)
    		throws ResourceException 
    {
    	// TODO Auto-generated method stub
    	//super.storeRepresentation(entity);
    	// Get request XML
        DomRepresentation domRep = new DomRepresentation(entity);    
    	//List<String> list = null;
        ResponseList responseList = null;    	   
    	try
        {
	    	/*	    		    		    
    		// Determine List name
        	listName = ListResourceHelper.parseListForListName(domRep.getDocument().getDocumentElement());
        	// Parse request XML -> Java objects
        	list = ListResourceHelper.parseList(domRep.getDocument().getDocumentElement());
        	*/
    		//ResponseList = new ResponseList();
    		//ResponseList.setStudyUUID(this.StudyUUID);
    		//responseList = ListResourceHelper.parseList(domRep.getDocument().getDocumentElement());
    		responseList.setStudyUUID(this.StudyUUID);
        	// Validation        	
        	
	    	// Java -> DB (UPDATE)
	    	/*
	    	 * Hibernate different classes come in the picture
	    	 */
    		
    		// update list in the StudyDesign Object
	    	
	    	// Java -> XML
	    	
	    	// Create our response xml          	        	 
        	
        	ListXMLRepresentation response = new ListXMLRepresentation(responseList);
            getResponse().setEntity(response); 
            getResponse().setStatus(Status.SUCCESS_CREATED);	    	
        }
    	catch (IOException ioe)
        {
            StudyDesignLogger.getInstance().error(ioe.getMessage());
            try { getResponse().setEntity(new ErrorXMLRepresentation(ioe.getMessage())); }
            catch (IOException e) {}
            getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
        catch (IllegalArgumentException iae)
        {
        	StudyDesignLogger.getInstance().error(iae.getMessage());
            try { getResponse().setEntity(new ErrorXMLRepresentation(iae.getMessage())); }
            catch (IOException e) {}
            getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
       /* catch (ResourceException re)
        {
        	StudyDesignLogger.getInstance().error(re.getMessage());
            try { getResponse().setEntity(new ErrorXMLRepresentation(re.getMessage())); }
            catch (IOException e) {}
            getResponse().setStatus(re.getStatus());
        }*/
    }    
    
    
    /**
     * Process a POST request to store a List in data base.
     * Please see REST API documentation for details on the entity 
     * body format.
     * 
     * @param entity HTTP entity body for the request
     */
    @Post 
    public void acceptRepresentation(Representation entity)
    {
    	/*
    	// Get request XML
        DomRepresentation domRep = new DomRepresentation(entity);    
    	ResponseList ResponseList = null;
    	String listName = null;
        try
        {           
        	ResponseList = ListResourceHelper.parseList(domRep.getDocument().getDocumentElement());
        	// Validation        	
        	        	      	
        	// Java -> DB (CREATE)
        	
        	// Hibernate - store list in the StudyDesign Object
        	
        	// Java -> XML        	        	        	         	        
        	
        	ListXMLRepresentation response = new ListXMLRepresentation(ResponseList);
            getResponse().setEntity(response); 
            getResponse().setStatus(Status.SUCCESS_CREATED);
        
        }*/
    	try
    	{ storeRepresentation(entity);}        
        catch (IllegalArgumentException iae)
        {
        	StudyDesignLogger.getInstance().error(iae.getMessage());
            try { getResponse().setEntity(new ErrorXMLRepresentation(iae.getMessage())); }
            catch (IOException e) {}
            getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
        catch (ResourceException re)
        {
        	StudyDesignLogger.getInstance().error(re.getMessage());
            try { getResponse().setEntity(new ErrorXMLRepresentation(re.getMessage())); }
            catch (IOException e) {}
            getResponse().setStatus(re.getStatus());
        }    	
    }
}