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
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.cudenver.bios.studydesignsvc.application.StudyDesignConstants;
import edu.cudenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.cudenver.bios.studydesignsvc.domain.NamedRealMatrix;
import edu.cudenver.bios.studydesignsvc.representation.ErrorXMLRepresentation;
import edu.cudenver.bios.studydesignsvc.representation.ListMatrixXMLRepresentation;
import edu.cudenver.bios.studydesignsvc.representation.MatrixXMLRepresentation;
import edu.cudenver.bios.studydesignsvc.resourcehelper.MatrixParamParser;

/**
 * Resource class for handling requests for all types of Matrices.
 *
 * See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class MatrixResource extends ServerResource 
{
	private static Logger logger = StudyDesignLogger.getInstance();
	private byte[] StudyUUID = null;
	
	/**
	 * Create a new resource to handle alpha list requests.  Data
	 * is returned as XML.
	 * 
	 * @param context restlet context
	 * @param request http request object
	 * @param response http response object
	 */
    public MatrixResource(Context context, Request request, Response response) 
    {
        //super(context, request, response);
    	super();
        getVariants().add(new Variant(MediaType.APPLICATION_XML));
        // Determine UUID from URL
        this.StudyUUID = request.getAttributes().get(StudyDesignConstants.TAG_STUDY_UUID).toString().getBytes();         
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
    	NamedRealMatrix namedRealMatrix = null;
    	MatrixXMLRepresentation responseForSingleMatrixRead = null;
    	String tagName = null;
    	// change
    	ListMatrixXMLRepresentation responseForMultipleMatricesReads = null;
    	try
        {
	    	// Get request XML
	    	//domRep = new DomRepresentation(this.getRepresentation(variant));
	    	//this.tagName = request.getAttributes().get(StudyDesignConstants.TAG_LIST_NAME).toString();
	    	tagName = this.getRequest().getAttributes().get(StudyDesignConstants.TAG_LIST_NAME).toString();
	    	
	    	if(tagName == null)
	    	{
	    		tagName = StudyDesignConstants.TAG_All_MATRICES;
	    		List<NamedRealMatrix> list = new ArrayList<NamedRealMatrix>();
	    		// Fetch list fom db -> Java
		    	/*
		    	 * Hibernate query (READ)
		    	 */
	    		//ListXMLRepresentation response = new ListXMLRepresentation(list, this.tagName);
	    		responseForMultipleMatricesReads = new ListMatrixXMLRepresentation(list,StudyUUID);	    		
	    		getResponse().setEntity(responseForMultipleMatricesReads); 
	 	        getResponse().setStatus(Status.SUCCESS_CREATED);
	    	}
	    	else
	    	{
	    		//list = ListResourceHelper.parseList(domRep.getDocument().getDocumentElement());
	    		namedRealMatrix = new NamedRealMatrix();
	    		namedRealMatrix.setName(tagName);
	    		
	    		// Fetch list fom db -> Java
		    	/*
		    	 * Hibernate query (READ)
		    	 */
	    		
	    		// Java -> XML : Create our response XML
	    		responseForSingleMatrixRead = new MatrixXMLRepresentation(namedRealMatrix);
	    		getResponse().setEntity(responseForSingleMatrixRead); 
		        getResponse().setStatus(Status.SUCCESS_CREATED);	    
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
    	String tagName = null;
    	// Determine UUID from URL
    	//List<String> list = null;
    	NamedRealMatrix matrix = null;
    	MatrixXMLRepresentation response = null;
    	// 
    	try
        {
	    	
	    	//this.tagName = request.getAttributes().get(StudyDesignConstants.TAG_LIST_NAME).toString();
	    	tagName = this.getRequest().getAttributes().get(StudyDesignConstants.TAG_LIST_NAME).toString();
	    	if (tagName == null)
	    	{
	    		// error message display
	    	}
	    	// Fetch list fom db -> Java
	    	/*
	    	 * Hibernate query (READ)
	    	 */
	    	
	    	// delete record
	    	/*
	    	 * Hibernate query (DELETE)
	    	 */
	    	
	    	// Java -> XML
	    	
	    	// Create our response XML   - how to represent for all lists?	    	
	    	//ListXMLRepresentation response = new ListXMLRepresentation(list, tagName);
	    	response = new MatrixXMLRepresentation(matrix);
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
     * Process a UPDATE request to update a List in the data base.
     * Please see REST API documentation for details on the entity 
     * body format.
     * 
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
        NamedRealMatrix namedRealMatrix = null;    	
    	try
        {
	    	/*	    		    		    
    		// Determine List name
        	listName = ListResourceHelper.parseListForListName(domRep.getDocument().getDocumentElement());
        	// Parse request XML -> Java objects
        	list = ListResourceHelper.parseList(domRep.getDocument().getDocumentElement());
        	*/
    		//namedList = ListResourceHelper.parseList(domRep.getDocument().getDocumentElement());
    		namedRealMatrix = MatrixParamParser.getPositiveDefiniteParamsFromDomNode(domRep.getDocument().getDocumentElement());
        	// Validation        	
        	
	    	// Java -> DB (UPDATE)
	    	/*
	    	 * Hibernate different classes come in the picture
	    	 */
	    	
	    	// Java -> XML
	    	
	    	// Create our response xml          	        	 
        	
        	MatrixXMLRepresentation response = new MatrixXMLRepresentation(namedRealMatrix);
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
        catch (ResourceException re)
        {
        	StudyDesignLogger.getInstance().error(re.getMessage());
            try { getResponse().setEntity(new ErrorXMLRepresentation(re.getMessage())); }
            catch (IOException e) {}
            getResponse().setStatus(re.getStatus());
        }
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
    	// Get request XML
        DomRepresentation domRep = new DomRepresentation(entity);    
        NamedRealMatrix namedRealMatrix = null;    
        try
        {   
        	/*
        	// Determine List name
        	listName = ListResourceHelper.parseListForListName(domRep.getDocument().getDocumentElement());
        	// Parse request XML -> Java objects
        	list = ListResourceHelper.parseList(domRep.getDocument().getDocumentElement());
        	*/
        	namedRealMatrix = MatrixParamParser.getPositiveDefiniteParamsFromDomNode(domRep.getDocument().getDocumentElement());
        	// Validation        	
        	
        	/*
        	// Parse request XML -> Java objects    ....... for now local Java class is used, in future might be changed to Hibernate Pojo classes
        	List<Double> alphaList = 
        			ListResourceHelper.parseDoubleList(domRep.getDocument().getDocumentElement(), 
        					StudyDesignConstants.TAG_ALPHA_LIST);
        	// Validations
        	if(alphaList.size()==0)
        	{
        		String msg = "Alpha List must contain atleast one value in it.";
            	logger.info(msg);
             	throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, msg);
        	}
        	
        	// Java -> DB (CREATE)
        	
        	// Hibernate different classes come in the picture
        	
        	// Java -> XML
        	
        	// Create our response xml            
        	ListXMLRepresentation response = new ListXMLRepresentation(alphaList, StudyDesignConstants.TAG_ALPHA_LIST);
            getResponse().setEntity(response); 
            getResponse().setStatus(Status.SUCCESS_CREATED);
            */        	      
        	
        	MatrixXMLRepresentation response = new MatrixXMLRepresentation(namedRealMatrix);
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
        catch (ResourceException re)
        {
        	StudyDesignLogger.getInstance().error(re.getMessage());
            try { getResponse().setEntity(new ErrorXMLRepresentation(re.getMessage())); }
            catch (IOException e) {}
            getResponse().setStatus(re.getStatus());
        }
    }
    
} 