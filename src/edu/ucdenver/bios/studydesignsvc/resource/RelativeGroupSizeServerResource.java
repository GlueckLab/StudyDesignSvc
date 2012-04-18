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
import edu.ucdenver.bios.studydesignsvc.manager.RelativeGroupSizeManager;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.domain.RelativeGroupSizeList;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

/**
 * Server Resource class for handling requests for the Relative Group Size object. 
 * See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class RelativeGroupSizeServerResource  extends ServerResource
implements RelativeGroupSizeResource
{
	RelativeGroupSizeManager relativeGroupSizeManager = null; 
	StudyDesignManager studyDesignManager = null;
	boolean uuidFlag;

	/**
     * Retrieve a RelativeGroupSize object for specified UUID.
     * 
     * @param byte[]
     * @return RelativeGroupSizeList
     */
	@Get("application/json")
	public RelativeGroupSizeList retrieve(byte[] uuid) 
	{
		RelativeGroupSizeList relativeGroupSizeList = null;
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
						relativeGroupSizeList = new RelativeGroupSizeList(studyDesign.getRelativeGroupSizeList());					
            	}				
			studyDesignManager.commit();					
		}
		catch (BaseManagerException bme)
		{
			System.out.println(bme.getMessage());
			StudyDesignLogger.getInstance().error(bme.getMessage());
			if(relativeGroupSizeManager!=null)
			{
				try
				{relativeGroupSizeManager.rollback();}				
				catch(BaseManagerException re)
				{relativeGroupSizeList = null;}				
			}
			relativeGroupSizeList = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {relativeGroupSizeList = null;}					
			}
			relativeGroupSizeList = null;
		}								
		return relativeGroupSizeList;
	}

	/**
     * Create a RelativeGroupSize object for specified UUID.
     * 
     * @param byte[]
     * @param RelativeGroupSizeList
     * @return RelativeGroupSizeList
     */
	@Post("application/json")
	public RelativeGroupSizeList create(RelativeGroupSizeList relativeGroupSizeList) 
	{		
		StudyDesign studyDesign =null;
		byte[] uuid = relativeGroupSizeList.getUuid();
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
			 * Remove existing RelativeGroupSize for this object 
			 * ----------------------------------------------------*/			
			 if(uuidFlag && studyDesign.getRelativeGroupSizeList()!=null)
				removeFrom(studyDesign);	
			/* ----------------------------------------------------
			 * Save new RelativeGroupSize List object 
			 * ----------------------------------------------------*/
			if(uuidFlag)
			{
				studyDesign.setRelativeGroupSizeList(relativeGroupSizeList.getRelativeGroupSizeList());
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
			if(relativeGroupSizeManager!=null)
			{
				try
				{relativeGroupSizeManager.rollback();}				
				catch(BaseManagerException re)
				{relativeGroupSizeList = null;}				
			}
			relativeGroupSizeList = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {relativeGroupSizeList = null;}					
			}
			relativeGroupSizeList = null;
		}								
		return relativeGroupSizeList;
	}

	/**
     * Update a RelativeGroupSize object for specified UUID.
     * 
     * @param byte[]
     * @param RelativeGroupSizeList
     * @return RelativeGroupSizeList
     */
	@Put("application/json")
	public RelativeGroupSizeList update(RelativeGroupSizeList relativeGroupSizeList) 
	{
		return create(relativeGroupSizeList);
	}

	/**
     * Delete a RelativeGroupSize object for specified UUID.
     * 
     * @param byte[]
     * @return RelativeGroupSizeList
     */
	@Delete("application/json")
	public RelativeGroupSizeList remove(byte[] uuid) 
	{
		RelativeGroupSizeList relativeGroupSizeList = null;
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
						relativeGroupSizeList = new RelativeGroupSizeList(studyDesign.getRelativeGroupSizeList());
					if(relativeGroupSizeList.getRelativeGroupSizeList().isEmpty())
						throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
								"no RelativeGroupSize is specified");					
            	}				
			studyDesignManager.commit();
			/* ----------------------------------------------------
			 * Remove existing RelativeGroupSize objects for this object 
			 * ----------------------------------------------------*/
			if(studyDesign.getRelativeGroupSizeList()!=null)
			{
				relativeGroupSizeManager = new RelativeGroupSizeManager();
				relativeGroupSizeManager.beginTransaction();
					relativeGroupSizeList = new RelativeGroupSizeList(relativeGroupSizeManager.delete(uuid,relativeGroupSizeList.getRelativeGroupSizeList()));
				relativeGroupSizeManager.commit();
			}
		}
		catch (BaseManagerException bme)
		{
			System.out.println(bme.getMessage());
			StudyDesignLogger.getInstance().error(bme.getMessage());
			if(relativeGroupSizeManager!=null)
			{
				try
				{relativeGroupSizeManager.rollback();}				
				catch(BaseManagerException re)
				{relativeGroupSizeList = null;}				
			}
			relativeGroupSizeList = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {relativeGroupSizeList = null;}					
			}
			relativeGroupSizeList = null;
		}		
		return relativeGroupSizeList;
	}

	/**
     * Delete a RelativeGroupSize object for specified Study Design.
     * 
     * @param StudyDesign
     * @return RelativeGroupSizeList
     */
	public RelativeGroupSizeList removeFrom(StudyDesign studyDesign) 
	{
		RelativeGroupSizeList relativeGroupSizeList = null;	
        try
        {                    			
        	relativeGroupSizeManager = new RelativeGroupSizeManager();
        	relativeGroupSizeManager.beginTransaction();
        		relativeGroupSizeList=new RelativeGroupSizeList(relativeGroupSizeManager.delete(studyDesign.getUuid(),studyDesign.getRelativeGroupSizeList()));
        	relativeGroupSizeManager.commit();        	       
        }
        catch (BaseManagerException bme)
        {
        	System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error("Failed to load Study Design information: " + bme.getMessage());
            if (studyDesignManager != null) try { studyDesignManager.rollback(); } catch (BaseManagerException e) {}
            if (relativeGroupSizeManager != null) try { relativeGroupSizeManager.rollback(); } catch (BaseManagerException e) {}
            relativeGroupSizeList = null;           
        }
       return relativeGroupSizeList;
	}
}
