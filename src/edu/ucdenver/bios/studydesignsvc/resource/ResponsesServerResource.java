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
package edu.ucdenver.bios.studydesignsvc.resource;

import org.restlet.data.Status;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.ucdenver.bios.studydesignsvc.manager.ResponsesManager;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.domain.ResponseList;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

/**
 * Server Resource class for handling requests for the ResponseNode object. 
 * See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class ResponsesServerResource  extends ServerResource
implements ResponsesResource 
{

	ResponsesManager responsesManager = null; 
	StudyDesignManager studyDesignManager = null;
	boolean uuidFlag;

	/**
     * Retrieve a ResponseNode object for specified UUID.
     * 
     * @param byte[]
     * @return ResponseList
     */
	@Get("json")
	public ResponseList retrieve(byte[] uuid) 
	{
		ResponseList responseNodeList = null;
		if(uuid==null)
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
					"no study design UUID specified");		
		try
		{
			/* ----------------------------------------------------
			 * Check for existence of a UUID in Study Design object 
			 * ----------------------------------------------------*/
			studyDesignManager = new StudyDesignManager();			
			studyDesignManager.beginTransaction();								
				uuidFlag = studyDesignManager.hasUUID(uuid);
				if(uuidFlag)
            	{		
					StudyDesign studyDesign = studyDesignManager.get(uuid);
					if(studyDesign!=null)
						responseNodeList = new ResponseList(studyDesign.getResponseList());					
            	}				
			studyDesignManager.commit();					
		}
		catch (BaseManagerException bme)
		{
			System.out.println(bme.getMessage());
			StudyDesignLogger.getInstance().error(bme.getMessage());
			if(responsesManager!=null)
			{
				try
				{responsesManager.rollback();}				
				catch(BaseManagerException re)
				{responseNodeList = null;}				
			}
			responseNodeList = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {responseNodeList = null;}					
			}
			responseNodeList = null;
		}								
		return responseNodeList;
	}

	/**
     * Create a ResponseNode object for specified UUID.
     * 
     * @param byte[]
     * @return ResponseList
     */
	@Post("json")
	public ResponseList create(byte[] uuid,ResponseList responseNodeList) 
	{		
		StudyDesign studyDesign =null;
		if(uuid==null)
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
					"no study design UUID specified");		
		try
		{
			/* ----------------------------------------------------
			 * Check for existence of a UUID in Study Design object 
			 * ----------------------------------------------------*/
			studyDesignManager = new StudyDesignManager();
			studyDesignManager.beginTransaction();				
				uuidFlag = studyDesignManager.hasUUID(uuid);				
				if(uuidFlag)
            	{studyDesign = studyDesignManager.get(uuid);}																									            				
				if(!uuidFlag)
				{throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
						"no study design UUID specified");}
			studyDesignManager.commit();
			/* ----------------------------------------------------
			 * Remove existing ResponseNode for this object 
			 * ----------------------------------------------------*/
			if(uuidFlag && studyDesign.getResponseList()!=null)
				removeFrom(studyDesign);	
			/* ----------------------------------------------------
			 * Set reference of Study Design Object to each ResponseNode element 
			 * ----------------------------------------------------*/	
			/*for(ResponseNode ResponseNode : responseNodeList)					
				ResponseNode.setStudyDesign(studyDesign);*/			
			/* ----------------------------------------------------
			 * Save new ResponseNode List object 
			 * ----------------------------------------------------*/
			if(uuidFlag)
			{
				studyDesign.setResponseList(responseNodeList);
				studyDesignManager = new StudyDesignManager();
				studyDesignManager.beginTransaction();
					studyDesignManager.saveOrUpdate(studyDesign, false);
				studyDesignManager.commit();			
			}
		}
		catch (BaseManagerException bme)
		{
			System.out.println(bme.getMessage());
			StudyDesignLogger.getInstance().error(bme.getMessage());
			if(responsesManager!=null)
			{
				try
				{responsesManager.rollback();}				
				catch(BaseManagerException re)
				{responseNodeList = null;}				
			}
			responseNodeList = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {responseNodeList = null;}					
			}
			responseNodeList = null;
		}								
		return responseNodeList;
	}

	/**
     * Update a ResponseNode object for specified UUID.
     * 
     * @param byte[]
     * @return ResponseList
     */
	@Put("json")
	public ResponseList update(byte[] uuid,ResponseList responseNodeList) 
	{				
		return create(uuid,responseNodeList);			
	}	

	/**
     * Delete a ResponseNode object for specified UUID.
     * 
     * @param byte[]
     * @return ResponseList
     */
	@Delete("json")
	public ResponseList remove(byte[] uuid) 
	{
		ResponseList responseNodeList = null;
		StudyDesign studyDesign = null;
		if(uuid==null)
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
					"no study design UUID specified");		
		try
		{
			/* ----------------------------------------------------
			 * Check for existence of a UUID in Study Design object 
			 * ----------------------------------------------------*/
			studyDesignManager = new StudyDesignManager();			
			studyDesignManager.beginTransaction();								
				uuidFlag = studyDesignManager.hasUUID(uuid);
				if(uuidFlag)
            	{		
					studyDesign = studyDesignManager.get(uuid);
					if(studyDesign!=null)
						responseNodeList = new ResponseList(studyDesign.getResponseList());									
            	}				
			studyDesignManager.commit();
			/* ----------------------------------------------------
			 * Remove existing ResponseNode objects for this object 
			 * ----------------------------------------------------*/
			if(responseNodeList!=null)
			{
				responsesManager = new ResponsesManager();
				responsesManager.beginTransaction();
					responseNodeList = new ResponseList(responsesManager.delete(uuid,responseNodeList));
				responsesManager.commit();
			}
		}
		catch (BaseManagerException bme)
		{
			System.out.println(bme.getMessage());
			StudyDesignLogger.getInstance().error(bme.getMessage());
			if(responsesManager!=null)
			{
				try
				{responsesManager.rollback();}				
				catch(BaseManagerException re)
				{responseNodeList = null;}				
			}
			responseNodeList = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {responseNodeList = null;}					
			}
			responseNodeList = null;
		}		
		return responseNodeList;
	}

	/**
     * Update a ResponseNode object for specified studyDesign.
     * 
     * @param byte[]
     * @return ResponseList
     */
	public ResponseList removeFrom(StudyDesign studyDesign) 
	{
		boolean flag;	
		ResponseList responseNodeList = null;	
        try
        {                    			
        	responsesManager = new ResponsesManager();
        	responsesManager.beginTransaction();
        		responseNodeList=new ResponseList(responsesManager.delete(studyDesign.getUuid(),studyDesign.getResponseList()));
        	responsesManager.commit();        	       
        }
        catch (BaseManagerException bme)
        {
        	System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error("Failed to load Study Design information: " + bme.getMessage());
            if (studyDesignManager != null) try { studyDesignManager.rollback(); } catch (BaseManagerException e) {}
            if (responsesManager != null) try { responsesManager.rollback(); } catch (BaseManagerException e) {}
            responseNodeList = null;           
        }
        return responseNodeList;
	}
}
