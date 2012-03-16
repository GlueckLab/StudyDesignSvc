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

import java.util.List;

import org.restlet.data.Status;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.studydesignsvc.manager.TypeIErrorManager;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.domain.TypeIError;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;
/**
 * Server Resource class for handling requests for the Type I Error object. 
 * See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class TypeIErrorServerResource  extends ServerResource
implements TypeIErrorResource 
{
	TypeIErrorManager typeIErrorManager = null; 
	StudyDesignManager studyDesignManager = null;
	boolean uuidFlag;

	/**
     * Retrieve a TypeIError object for specified UUID.
     * 
     * @param byte[]
     * @return List<TypeIError>
     */
	@Get("json")
	public List<TypeIError> retrieve(byte[] uuid) 
	{
		List<TypeIError> typeIErrorList = null;
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
						typeIErrorList = studyDesign.getAlphaList();					
            	}				
			studyDesignManager.commit();					
		}
		catch (BaseManagerException bme)
		{
			System.out.println(bme.getMessage());
			StudyDesignLogger.getInstance().error(bme.getMessage());
			if(typeIErrorManager!=null)
			{
				try
				{typeIErrorManager.rollback();}				
				catch(BaseManagerException re)
				{typeIErrorList = null;}				
			}
			typeIErrorList = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {typeIErrorList = null;}					
			}
			typeIErrorList = null;
		}								
		return typeIErrorList;
	}

	/**
     * Create a TypeIError object for specified UUID.
     * 
     * @param byte[]
     * @param List<TypeIError>
     * @return List<TypeIError>
     */
	@Post("json")
	public List<TypeIError> create(byte[] uuid,List<TypeIError> typeIErrorList) 
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
            	{
					studyDesign = studyDesignManager.get(uuid);					
				}																									            				
				else
				{throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
						"no study design UUID specified");}
			studyDesignManager.commit();
			/* ----------------------------------------------------
			 * Remove existing TypeIError for this object 
			 * ----------------------------------------------------*/			
			if(typeIErrorList!=null)
				removeFrom(studyDesign);	
			/* ----------------------------------------------------
			 * Save new TypeIError List object 
			 * ----------------------------------------------------*/
			if(uuidFlag)
			{
				studyDesign.setAlphaList(typeIErrorList);
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
			if(typeIErrorManager!=null)
			{
				try
				{typeIErrorManager.rollback();}				
				catch(BaseManagerException re)
				{typeIErrorList = null;}				
			}
			typeIErrorList = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {typeIErrorList = null;}					
			}
			typeIErrorList = null;
		}								
		return typeIErrorList;
	}

	/**
     * Update a TypeIError object for specified UUID.
     * 
     * @param byte[]
     * @param List<TypeIError>
     * @return List<TypeIError>
     */
	@Put("json")
	public List<TypeIError> update(byte[] uuid,List<TypeIError> typeIErrorList) 
	{
		return create(uuid,typeIErrorList);
	}

	/**
     * Delete a TypeIError object for specified UUID.
     * 
     * @param byte[]
     * @return List<TypeIError>
     */
	@Delete("json")
	public List<TypeIError> remove(byte[] uuid) 
	{
		List<TypeIError> typeIErrorList = null;
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
						typeIErrorList = studyDesign.getAlphaList();
					if(typeIErrorList.isEmpty())
						throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
								"no TypeIError is specified");					
            	}				
			studyDesignManager.commit();
			/* ----------------------------------------------------
			 * Remove existing TypeIError objects for this object 
			 * ----------------------------------------------------*/
			if(studyDesign.getAlphaList()!=null)
			{
				typeIErrorManager = new TypeIErrorManager();
				typeIErrorManager.beginTransaction();
					typeIErrorList = typeIErrorManager.delete(uuid,typeIErrorList);
				typeIErrorManager.commit();
			}
		}
		catch (BaseManagerException bme)
		{
			System.out.println(bme.getMessage());
			StudyDesignLogger.getInstance().error(bme.getMessage());
			if(typeIErrorManager!=null)
			{
				try
				{typeIErrorManager.rollback();}				
				catch(BaseManagerException re)
				{typeIErrorList = null;}				
			}
			typeIErrorList = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {typeIErrorList = null;}					
			}
			typeIErrorList = null;
		}		
		return typeIErrorList;
	}

	/**
     * Delete a TypeIError object for specified Study Design.
     * 
     * @param StudyDesign
     * @return List<TypeIError>
     */
	@Override
	@Delete("json")
	public List<TypeIError> removeFrom(StudyDesign studyDesign) 
	{
		List<TypeIError> typeIErrorList = null;	
        try
        {                    			
        	typeIErrorManager = new TypeIErrorManager();
        	typeIErrorManager.beginTransaction();
        		typeIErrorList=typeIErrorManager.delete(studyDesign.getUuid(),studyDesign.getAlphaList());
        	typeIErrorManager.commit();        	       
        }
        catch (BaseManagerException bme)
        {
        	System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error("Failed to load Study Design information: " + bme.getMessage());
            if (studyDesignManager != null) try { studyDesignManager.rollback(); } catch (BaseManagerException e) {}
            if (typeIErrorManager != null) try { typeIErrorManager.rollback(); } catch (BaseManagerException e) {}
            typeIErrorList = null;           
        }
       return typeIErrorList;
	}
}
