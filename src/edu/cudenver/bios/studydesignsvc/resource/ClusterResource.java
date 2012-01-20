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
import java.util.ArrayList;
import java.util.List;
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
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.cudenver.bios.studydesignsvc.application.StudyDesignConstants;
import edu.cudenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.cudenver.bios.studydesignsvc.domain.NamedList;
import edu.cudenver.bios.studydesignsvc.representation.ErrorXMLRepresentation;
import edu.cudenver.bios.studydesignsvc.representation.ListListXMLRepresentation;
import edu.cudenver.bios.studydesignsvc.representation.ListXMLRepresentation;

public class ClusterResource extends ServerResource 
{
	private static Logger logger = StudyDesignLogger.getInstance();	
	private UUID StudyUUID = null;
	
	public ClusterResource(Context context, Request request, Response response) 
    {
		//super(context, request, response);
		super();
        getVariants().add(new Variant(MediaType.APPLICATION_XML));
        // Determine UUID from URL
        this.StudyUUID = UUID.fromString(request.getAttributes().get(StudyDesignConstants.TAG_STUDY_UUID).toString());  
    }
		
    
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
    	NamedList namedList = null;
    	ListXMLRepresentation responseForSingleListRead = null;
    	ListListXMLRepresentation responseForMultipleListReads = null;
    	try
        {
	    	// Get request XML
	    	/* not required remove it
	    	 * domRep = new DomRepresentation(this.getRepresentation(variant));
	    	 */
	    	//this.tagName = request.getAttributes().get(StudyDesignConstants.TAG_LIST_NAME).toString();
	    	String tagName = this.getRequest().getAttributes().get(StudyDesignConstants.TAG_LIST_NAME).toString();
	    	
	    	if(tagName == null)
	    	{
	    		//this.tagName = StudyDesignConstants.TAG_All_LISTS;
	    		List<NamedList> list = new ArrayList<NamedList>();
	    		// Fetch list fom db -> Java
		    	/*
		    	 * Hibernate query (READ)
		    	 */
	    		/* check for UUID of requested design and current design if they match then put the data retrieved from db -> study design object
	    		 * compare data if same no prob ... if defferent?
	    		 */
	    		//ListXMLRepresentation response = new ListXMLRepresentation(list, this.tagName);
	    		responseForMultipleListReads = new ListListXMLRepresentation(list,this.StudyUUID);	
	    		return responseForMultipleListReads;
	    		/*getResponse().setEntity(responseForMultipleListReads); 
	 	        getResponse().setStatus(Status.SUCCESS_CREATED);*/
	    	}
	    	else
	    	{
	    		//list = ListResourceHelper.parseList(domRep.getDocument().getDocumentElement());
	    		namedList = new NamedList();
	    		namedList.setName(tagName);
	    		namedList.setStudyUUID(this.StudyUUID);
	    		
	    		// Fetch list fom db -> Java
		    	/*
		    	 * Hibernate query (READ)
		    	 */
	    		
	    		// Java -> XML : Create our response XML
	    		responseForSingleListRead = new ListXMLRepresentation(namedList);
	    		return responseForSingleListRead;
	    		/*getResponse().setEntity(responseForSingleListRead); 
		        getResponse().setStatus(Status.SUCCESS_CREATED);	*/    
	    	}
	    		    	   		    		    		    	          	    		         
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
    
}
