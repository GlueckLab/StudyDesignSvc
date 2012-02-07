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

import java.util.UUID;

import org.apache.log4j.Logger;

import org.restlet.data.MediaType;
import org.restlet.ext.json.JsonConverter;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.restlet.resource.UniformResource;

import edu.cudenver.bios.studydesignsvc.application.StudyDesignConstants;
import edu.cudenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.cudenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.cudenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

/**
 * Resource class for handling requests for the complete 
 * study design object. 
 * See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class StudyDesignObjectResource extends ServerResource
{
	private static Logger logger = StudyDesignLogger.getInstance();
	private UUID studyUUID = null;
	
	/**
	 * Create a new resource to handle list requests.  Data
	 * is returned as XML.
	 * 
	 * @param context restlet context
	 * @param request http request object
	 * @param response http response object
	 */
   /* public StudyDesignObjectResource(Context context, Request request, Response response) 
    {
        //super(context, request, response);
    	//super();
        //getVariants().add(new Variant(MediaType.APPLICATION_XML));
        // Determine UUID from URL
        this.studyUUID = UUID.fromString(request.getAttributes().get(StudyDesignConstants.TAG_STUDY_UUID).toString());         
    }*/
	/*public StudyDesignObjectResource()
	{
		getVariants().add(new Variant(MediaType.APPLICATION_JSON));		
	}*/
    
	 /**
     * Process a GET request to Return a full representation of a given variant.
     * Please see REST API documentation for details on the entity 
     * body format.
     * 
     * @param entity variant
     */ 
	/*@Get
    public JsonRepresentation read() throws ResourceException
    {		
		StudyDesignManager manager = null;
		StudyDesign studyDesign = null; 
		try
		{
			this.studyUUID = UUID.fromString((this.getRequest().getAttributes().get(StudyDesignConstants.TAG_STUDY_UUID).toString()));
			//System.out.println(this.studyUUID);
			studyDesign=new StudyDesign(this.studyUUID);			
			manager = new StudyDesignManager();
			manager.beginTransaction();			
				List<UUID> list = manager.getStudyUUIDs();			
				if(list!=null)
				{
					System.out.println(list.toString());
				}
				else
				{
					System.out.println("empty list");
				}	
			manager.commit();
		}
		catch(ResourceException e)
		{
			StudyDesignLogger.getInstance().error("StudyDesignResource.read - Failed to load UUIDs from database: "+e.getMessage());
			if(manager!=null)
			{
				try
				{manager.rollback();}				
				catch(ResourceException re)
				{studyDesign = null;}				
			}
		}				
		//return JSONObject.fromObject(studyDesign);
		JSONObject obj = new JSONObject(studyDesign);
		return obj;		
		//return JSONObject.fromObject(studyDesign);
		JsonRepresentation jsonRep = new JsonRepresentation(studyDesign);
		return jsonRep;		
    }*/
	/*@Get
    public StudyDesign read() throws ResourceException
    {		
		StudyDesignManager manager = null;
		StudyDesign studyDesign = null; 
		try
		{
			this.studyUUID = UUID.fromString((this.getRequest().getAttributes().get(StudyDesignConstants.TAG_STUDY_UUID).toString()));
			//System.out.println(this.studyUUID);
			studyDesign=new StudyDesign(this.studyUUID);			
			manager = new StudyDesignManager();
			manager.beginTransaction();			
				List<UUID> list = manager.getStudyUUIDs();			
				if(list!=null)
				{
					System.out.println(list.toString());
				}
				else
				{
					System.out.println("empty list");
				}	
			manager.commit();
		}
		catch(ResourceException e)
		{
			StudyDesignLogger.getInstance().error("StudyDesignResource.read - Failed to load UUIDs from database: "+e.getMessage());
			if(manager!=null)
			{
				try
				{manager.rollback();}				
				catch(ResourceException re)
				{studyDesign = null;}				
			}
		}						
		return studyDesign;	
    }*/
		
	 /**
     * Process a DELETE request to delete a 'Study Design Object' from the data base.
     * Please see REST API documentation for details on the entity 
     * body format.
     * 
     * @param entity HTTP entity body for the request
     * @return 
     */    
   /* @Delete
    public StudyDesign remove()
    {    
    	StudyDesignManager manager = null;
		StudyDesign studyDesign = null; 
		try
		{
			this.studyUUID = UUID.fromString((this.getRequest().getAttributes().get(StudyDesignConstants.TAG_STUDY_UUID).toString()));
			//System.out.println(this.studyUUID);
			studyDesign=new StudyDesign(this.studyUUID);			
			manager = new StudyDesignManager();
			manager.beginTransaction();			
				studyDesign=manager.deleteStudyDesign(studyUUID);			
			manager.commit();
		}
		catch(ResourceException e)
		{
			StudyDesignLogger.getInstance().error("StudyDesignResource.read - Failed to load UUIDs from database: "+e.getMessage());
			if(manager!=null)
			{
				try
				{manager.rollback();}				
				catch(ResourceException re)
				{studyDesign = null;}
				
			}
		}		
		return studyDesign;
    }*/
    
    /**
     * Process a PUT request to update a 'Study Design Object' in the data base.
     * Please see REST API documentation for details on the entity 
     * body format.
     * PUT works as create/update
     * @param entity HTTP entity body for the request
     */
    @Put
    public StudyDesign update(StudyDesign studyDesign)
    throws ResourceException 
    {    	
    	StudyDesignManager manager = null;		
		try
		{
			manager = new StudyDesignManager();
			manager.beginTransaction();				
				manager.saveOrUpdate(studyDesign, false);
			manager.commit();
		}
		catch(BaseManagerException bme)
		{
			StudyDesignLogger.getInstance().error("StudyDesignResource.read - Failed to load UUIDs from database: " + bme.getMessage());
			if(manager!=null)
			{
				try {manager.rollback();}				
				catch(BaseManagerException re)
				{studyDesign = null;}
			}
		}	
		catch(StudyDesignException sde)
		{
			StudyDesignLogger.getInstance().error("StudyDesignResource.read - Failed to load UUIDs from database: " + sde.getMessage());
			if(manager!=null)
			{
				try {manager.rollback();}				
				catch(BaseManagerException re)
				{studyDesign = null;}
			}
		}		
		return studyDesign;
    }
	
    /**
     * Process a POST request to store a List in data base.
     * Please see REST API documentation for details on the entity 
     * body format.
     * 
     * @param entity HTTP entity body for the request
     */
    /*@Post     
    public StudyDesign create(JsonRepresentation jsonStudyDesign)
    {  
    	StudyDesignManager manager = null;
    	//StudyDesign studyDesign = (StudyDesign)JSONObject.toBean(jsonStudyDesign);
    	
    	JsonConverter converter = new JsonConverter();
    	StudyDesign studyDesign = (StudyDesign)converter.toObject(jsonStudyDesign, StudyDesign.class,);
    	try
		{
			manager = new StudyDesignManager();
			manager.beginTransaction();
				studyDesign.generateUUID();
				manager.saveOrUpdateStudyDesign(studyDesign, true);
			manager.commit();
		}
		catch(ResourceException e)
		{
			StudyDesignLogger.getInstance().error("StudyDesignResource.read - Failed to load UUIDs from database: "+e.getMessage());
			if(manager!=null)
			{
				try
				{manager.rollback();}				
				catch(ResourceException re)
				{studyDesign = null;}				
			}
		}		
    	
		return studyDesign;
    }*/
    /*@Post 
    public StudyDesign create(JsonRepresentation jsonStudyDesign)
    {  
    	StudyDesignManager manager = null;
    	//StudyDesign studyDesign = (StudyDesign)JSONObject.toBean(jsonStudyDesign);
    	StudyDesign studyDesign = null; 	
    	try
		{
    		JSONObject jsonObject= jsonStudyDesign.getJsonObject();
    		
    		//studyDesign = new ObjectMapper().readValue(jsonStudyDesign.getJsonObject(), StudyDesign.class);
			manager = new StudyDesignManager();
			manager.beginTransaction();
				studyDesign.generateUUID();
				manager.saveOrUpdateStudyDesign(studyDesign, true);
			manager.commit();
		}
		catch(ResourceException e)
		{
			StudyDesignLogger.getInstance().error("StudyDesignResource.read - Failed to load UUIDs from database: "+e.getMessage());
			if(manager!=null)
			{
				try
				{manager.rollback();}				
				catch(ResourceException re)
				{studyDesign = null;}				
			}
		}	    	
    	
		return studyDesign;
    }*/
}
