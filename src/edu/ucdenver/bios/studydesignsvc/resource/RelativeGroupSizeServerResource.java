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
import edu.ucdenver.bios.studydesignsvc.manager.RelativeGroupSizeManager;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.domain.RelativeGroupSize;
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
     * @return List<RelativeGroupSize>
     */
	@Get("json")
	public List<RelativeGroupSize> retrieve(byte[] uuid) 
	{
		List<RelativeGroupSize> relativeGroupSizeList = null;
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
						relativeGroupSizeList = studyDesign.getRelativeGroupSizeList();					
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
     * @param List<RelativeGroupSize>
     * @return List<RelativeGroupSize>
     */
	@Post("json")
	public List<RelativeGroupSize> create(byte[] uuid,List<RelativeGroupSize> relativeGroupSizeList) 
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
			 * Remove existing RelativeGroupSize for this object 
			 * ----------------------------------------------------*/			
			 if(uuidFlag && studyDesign.getRelativeGroupSizeList()!=null)
				removeFrom(studyDesign);	
			/* ----------------------------------------------------
			 * Save new RelativeGroupSize List object 
			 * ----------------------------------------------------*/
			if(uuidFlag)
			{
				studyDesign.setRelativeGroupSizeList(relativeGroupSizeList);
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
     * @param List<RelativeGroupSize>
     * @return List<RelativeGroupSize>
     */
	@Put("json")
	public List<RelativeGroupSize> update(byte[] uuid,List<RelativeGroupSize> relativeGroupSizeList) 
	{
		return create(uuid,relativeGroupSizeList);
	}

	/**
     * Delete a RelativeGroupSize object for specified UUID.
     * 
     * @param byte[]
     * @return List<RelativeGroupSize>
     */
	@Delete("json")
	public List<RelativeGroupSize> remove(byte[] uuid) 
	{
		List<RelativeGroupSize> relativeGroupSizeList = null;
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
						relativeGroupSizeList = studyDesign.getRelativeGroupSizeList();
					if(relativeGroupSizeList.isEmpty())
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
					relativeGroupSizeList = relativeGroupSizeManager.delete(uuid,relativeGroupSizeList);
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
     * @return List<RelativeGroupSize>
     */
	@Override
	@Delete("json")
	public List<RelativeGroupSize> removeFrom(StudyDesign studyDesign) 
	{
		List<RelativeGroupSize> relativeGroupSizeList = null;	
        try
        {                    			
        	relativeGroupSizeManager = new RelativeGroupSizeManager();
        	relativeGroupSizeManager.beginTransaction();
        		relativeGroupSizeList=relativeGroupSizeManager.delete(studyDesign.getUuid(),studyDesign.getRelativeGroupSizeList());
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
