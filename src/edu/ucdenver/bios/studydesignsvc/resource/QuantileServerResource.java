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
import edu.ucdenver.bios.studydesignsvc.manager.QuantileManager;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.domain.Quantile;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

/**
 * Server Resource class for handling requests for the Quantile object. 
 * See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class QuantileServerResource  extends ServerResource
implements QuantileResource
{
	QuantileManager quantileManager = null; 
	StudyDesignManager studyDesignManager = null;
	boolean uuidFlag;

	/**
     * Retrieve a Quantile object for specified UUID.
     * 
     * @param byte[]
     * @return List<Quantile>
     */
	@Get("json")
	public List<Quantile> retrieve(byte[] uuid) 
	{
		List<Quantile> quantileList = null;
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
						quantileList = studyDesign.getQuantileList();					
            	}				
			studyDesignManager.commit();					
		}
		catch (BaseManagerException bme)
		{
			System.out.println(bme.getMessage());
			StudyDesignLogger.getInstance().error(bme.getMessage());
			if(quantileManager!=null)
			{
				try
				{quantileManager.rollback();}				
				catch(BaseManagerException re)
				{quantileList = null;}				
			}
			quantileList = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {quantileList = null;}					
			}
			quantileList = null;
		}								
		return quantileList;
	}

	/**
     * Create a Quantile object for specified UUID.
     * 
     * @param byte[]
     * @param List<Quantile>
     * @return List<Quantile>
     */
	@Post("json")
	public List<Quantile> create(byte[] uuid,List<Quantile> quantileList) 
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
			 * Remove existing Quantile for this object 
			 * ----------------------------------------------------*/			
			if(quantileList!=null)
				removeFrom(studyDesign);	
			/* ----------------------------------------------------
			 * Save new Quantile List object 
			 * ----------------------------------------------------*/
			if(uuidFlag)
			{
				studyDesign.setQuantileList(quantileList);
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
			if(quantileManager!=null)
			{
				try
				{quantileManager.rollback();}				
				catch(BaseManagerException re)
				{quantileList = null;}				
			}
			quantileList = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {quantileList = null;}					
			}
			quantileList = null;
		}								
		return quantileList;
	}

	/**
     * Update a Quantile object for specified UUID.
     * 
     * @param byte[]
     * @param List<Quantile>
     * @return List<Quantile>
     */
	@Put("json")
	public List<Quantile> update(byte[] uuid,List<Quantile> quantileList) 
	{
		return create(uuid,quantileList);
	}

	/**
     * Delete a Quantile object for specified UUID.
     * 
     * @param byte[]
     * @return List<Quantile>
     */
	@Delete("json")
	public List<Quantile> remove(byte[] uuid) 
	{
		List<Quantile> quantileList = null;
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
						quantileList = studyDesign.getQuantileList();
					if(quantileList.isEmpty())
						throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
								"no Quantile is specified");					
            	}				
			studyDesignManager.commit();
			/* ----------------------------------------------------
			 * Remove existing Quantile objects for this object 
			 * ----------------------------------------------------*/
			if(studyDesign.getQuantileList()!=null)
			{
				quantileManager = new QuantileManager();
				quantileManager.beginTransaction();
					quantileList = quantileManager.delete(uuid,quantileList);
				quantileManager.commit();
			}
		}
		catch (BaseManagerException bme)
		{
			System.out.println(bme.getMessage());
			StudyDesignLogger.getInstance().error(bme.getMessage());
			if(quantileManager!=null)
			{
				try
				{quantileManager.rollback();}				
				catch(BaseManagerException re)
				{quantileList = null;}				
			}
			quantileList = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {quantileList = null;}					
			}
			quantileList = null;
		}		
		return quantileList;
	}

	/**
     * Delete a Quantile object for specified Study Design.
     * 
     * @param StudyDesign
     * @return List<Quantile>
     */
	@Override
	@Delete("json")
	public List<Quantile> removeFrom(StudyDesign studyDesign) 
	{
		List<Quantile> quantileList = null;	
        try
        {                    			
        	quantileManager = new QuantileManager();
        	quantileManager.beginTransaction();
        		quantileList=quantileManager.delete(studyDesign.getUuid(),studyDesign.getQuantileList());
        	quantileManager.commit();        	       
        }
        catch (BaseManagerException bme)
        {
        	System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error("Failed to load Study Design information: " + bme.getMessage());
            if (studyDesignManager != null) try { studyDesignManager.rollback(); } catch (BaseManagerException e) {}
            if (quantileManager != null) try { quantileManager.rollback(); } catch (BaseManagerException e) {}
            quantileList = null;           
        }
       return quantileList;
	}
}
