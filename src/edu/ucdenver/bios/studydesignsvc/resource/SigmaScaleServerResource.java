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
import edu.ucdenver.bios.studydesignsvc.manager.SigmaScaleManager;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.domain.SigmaScale;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

/**
 * Server Resource class for handling requests for the Sigma Scale object. 
 * See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class SigmaScaleServerResource extends ServerResource
implements SigmaScaleResource
{
	SigmaScaleManager sigmaScaleManager = null; 
	StudyDesignManager studyDesignManager = null;
	boolean uuidFlag;

	/**
     * Retrieve a SigmaScale object for specified UUID.
     * 
     * @param byte[]
     * @return List<SigmaScale>
     */
	@Get("json")
	public List<SigmaScale> retrieve(byte[] uuid) 
	{
		List<SigmaScale> sigmaScaleList = null;
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
						sigmaScaleList = studyDesign.getSigmaScaleList();					
            	}				
			studyDesignManager.commit();					
		}
		catch (BaseManagerException bme)
		{
			System.out.println(bme.getMessage());
			StudyDesignLogger.getInstance().error(bme.getMessage());
			if(sigmaScaleManager!=null)
			{
				try
				{sigmaScaleManager.rollback();}				
				catch(BaseManagerException re)
				{sigmaScaleList = null;}				
			}
			sigmaScaleList = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {sigmaScaleList = null;}					
			}
			sigmaScaleList = null;
		}								
		return sigmaScaleList;
	}

	/**
     * Create a SigmaScale object for specified UUID.
     * 
     * @param byte[]
     * @param List<SigmaScale>
     * @return List<SigmaScale>
     */
	@Post("json")
	public List<SigmaScale> create(byte[] uuid,List<SigmaScale> sigmaScaleList) 
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
			 * Remove existing SigmaScale for this object 
			 * ----------------------------------------------------*/			
			if(sigmaScaleList!=null)
				removeFrom(studyDesign);	
			/* ----------------------------------------------------
			 * Save new SigmaScale List object 
			 * ----------------------------------------------------*/
			if(uuidFlag)
			{
				studyDesign.setSigmaScaleList(sigmaScaleList);
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
			if(sigmaScaleManager!=null)
			{
				try
				{sigmaScaleManager.rollback();}				
				catch(BaseManagerException re)
				{sigmaScaleList = null;}				
			}
			sigmaScaleList = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {sigmaScaleList = null;}					
			}
			sigmaScaleList = null;
		}								
		return sigmaScaleList;
	}

	/**
     * Update a SigmaScale object for specified UUID.
     * 
     * @param byte[]
     * @param List<SigmaScale>
     * @return List<SigmaScale>
     */
	@Put("json")
	public List<SigmaScale> update(byte[] uuid,List<SigmaScale> sigmaScaleList) 
	{
		return create(uuid,sigmaScaleList);
	}

	/**
     * Delete a SigmaScale object for specified UUID.
     * 
     * @param byte[]
     * @return List<SigmaScale>
     */
	@Delete("json")
	public List<SigmaScale> remove(byte[] uuid) 
	{
		List<SigmaScale> sigmaScaleList = null;
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
						sigmaScaleList = studyDesign.getSigmaScaleList();
					if(sigmaScaleList.isEmpty())
						throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
								"no SigmaScale is specified");					
            	}				
			studyDesignManager.commit();
			/* ----------------------------------------------------
			 * Remove existing SigmaScale objects for this object 
			 * ----------------------------------------------------*/
			if(studyDesign.getSigmaScaleList()!=null)
			{
				sigmaScaleManager = new SigmaScaleManager();
				sigmaScaleManager.beginTransaction();
					sigmaScaleList = sigmaScaleManager.delete(uuid,sigmaScaleList);
				sigmaScaleManager.commit();
			}
		}
		catch (BaseManagerException bme)
		{
			System.out.println(bme.getMessage());
			StudyDesignLogger.getInstance().error(bme.getMessage());
			if(sigmaScaleManager!=null)
			{
				try
				{sigmaScaleManager.rollback();}				
				catch(BaseManagerException re)
				{sigmaScaleList = null;}				
			}
			sigmaScaleList = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {sigmaScaleList = null;}					
			}
			sigmaScaleList = null;
		}		
		return sigmaScaleList;
	}

	/**
     * Delete a SigmaScale object for specified Study Design.
     * 
     * @param StudyDesign
     * @return List<SigmaScale>
     */
	@Override
	@Delete("json")
	public List<SigmaScale> removeFrom(StudyDesign studyDesign) 
	{
		List<SigmaScale> sigmaScaleList = null;	
        try
        {                    			
        	sigmaScaleManager = new SigmaScaleManager();
        	sigmaScaleManager.beginTransaction();
        		sigmaScaleList=sigmaScaleManager.delete(studyDesign.getUuid(),studyDesign.getSigmaScaleList());
        	sigmaScaleManager.commit();        	       
        }
        catch (BaseManagerException bme)
        {
        	System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error("Failed to load Study Design information: " + bme.getMessage());
            if (studyDesignManager != null) try { studyDesignManager.rollback(); } catch (BaseManagerException e) {}
            if (sigmaScaleManager != null) try { sigmaScaleManager.rollback(); } catch (BaseManagerException e) {}
            sigmaScaleList = null;           
        }
       return sigmaScaleList;
	}
}
