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
import edu.ucdenver.bios.studydesignsvc.manager.PowerMethodManager;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.domain.PowerMethodList;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

/**
 * Server Resource class for handling requests for the PowerMethod object. 
 * See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class PowerMethodServerResource  extends ServerResource
implements PowerMethodResource 
{
	PowerMethodManager powerMethodManager = null; 
	StudyDesignManager studyDesignManager = null;
	boolean uuidFlag;

	/**
     * Retrieve a PowerMethodList object for specified UUID.
     * 
     * @param byte[]
     * @return PowerMethodList
     */
	@Get("json")
	public PowerMethodList retrieve(byte[] uuid) 
	{
		PowerMethodList powerMethodList = null;
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
						powerMethodList = new PowerMethodList(studyDesign.getPowerMethodList());					
            	}				
			studyDesignManager.commit();					
		}
		catch (BaseManagerException bme)
		{
			System.out.println(bme.getMessage());
			StudyDesignLogger.getInstance().error(bme.getMessage());
			if(powerMethodManager!=null)
			{
				try
				{powerMethodManager.rollback();}				
				catch(BaseManagerException re)
				{powerMethodList = null;}				
			}
			powerMethodList = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {powerMethodList = null;}					
			}
			powerMethodList = null;
		}								
		return powerMethodList;
	}

	/**
     * Create a PowerMethodList object for specified UUID.
     * 
     * @param byte[]
     * @param PowerMethodList
     * @return PowerMethodList
     */
	@Post("json")
	public PowerMethodList create(byte[] uuid,PowerMethodList powerMethodList) 
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
			 * Remove existing PowerMethod for this object 
			 * ----------------------------------------------------*/			
			if(powerMethodList!=null && studyDesign.getPowerMethodList()!=null)
				removeFrom(studyDesign);	
			/* ----------------------------------------------------
			 * Save new PowerMethod List object 
			 * ----------------------------------------------------*/
			if(uuidFlag)
			{
				studyDesign.setPowerMethodList(powerMethodList);
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
			if(powerMethodManager!=null)
			{
				try
				{powerMethodManager.rollback();}				
				catch(BaseManagerException re)
				{powerMethodList = null;}				
			}
			powerMethodList = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {powerMethodList = null;}					
			}
			powerMethodList = null;
		}								
		return powerMethodList;
	}

	/**
     * Update a PowerMethodList object for specified UUID.
     * 
     * @param byte[]
     * @param PowerMethodList
     * @return PowerMethodList
     */
	@Put("json")
	public PowerMethodList update(byte[] uuid,PowerMethodList powerMethodList) 
	{
		return create(uuid,powerMethodList);
	}

	/**
     * Delete a PowerMethod object for specified UUID.
     * 
     * @param byte[]
     * @return PowerMethodList
     */
	@Delete("json")
	public PowerMethodList remove(byte[] uuid) 
	{
		PowerMethodList powerMethodList = null;
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
						powerMethodList = new PowerMethodList(studyDesign.getPowerMethodList());								
            	}				
			studyDesignManager.commit();
			/* ----------------------------------------------------
			 * Remove existing PowerMethod objects for this object 
			 * ----------------------------------------------------*/
			if(studyDesign.getPowerMethodList()!=null)
			{
				powerMethodManager = new PowerMethodManager();
				powerMethodManager.beginTransaction();
					powerMethodList = new PowerMethodList(powerMethodManager.delete(uuid,powerMethodList));
				powerMethodManager.commit();
			}
		}
		catch (BaseManagerException bme)
		{
			System.out.println(bme.getMessage());
			StudyDesignLogger.getInstance().error(bme.getMessage());
			if(powerMethodManager!=null)
			{
				try
				{powerMethodManager.rollback();}				
				catch(BaseManagerException re)
				{powerMethodList = null;}				
			}
			powerMethodList = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {powerMethodList = null;}					
			}
			powerMethodList = null;
		}		
		return powerMethodList;
	}

	/**
     * Delete a PowerMethodList object for specified Study Design.
     * 
     * @param StudyDesign
     * @return PowerMethodList
     */
	public PowerMethodList removeFrom(StudyDesign studyDesign) 
	{
		PowerMethodList powerMethodList = null;	
        try
        {                    			
        	powerMethodManager = new PowerMethodManager();
        	powerMethodManager.beginTransaction();
        		powerMethodList=new PowerMethodList(powerMethodManager.delete(studyDesign.getUuid(),studyDesign.getPowerMethodList()));
        	powerMethodManager.commit();        	       
        }
        catch (BaseManagerException bme)
        {
        	System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error("Failed to load Study Design information: " + bme.getMessage());
            if (studyDesignManager != null) try { studyDesignManager.rollback(); } catch (BaseManagerException e) {}
            if (powerMethodManager != null) try { powerMethodManager.rollback(); } catch (BaseManagerException e) {}
            powerMethodList = null;           
        }
       return powerMethodList;
	}
}
