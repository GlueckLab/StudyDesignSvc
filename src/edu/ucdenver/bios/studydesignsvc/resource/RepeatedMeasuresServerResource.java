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
import edu.ucdenver.bios.studydesignsvc.manager.RepeatedMeasuresManager;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.domain.RepeatedMeasuresNodeList;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;
// TODO: Auto-generated Javadoc
/**
 * Server Resource class for handling requests for the RepeatedMeasuresNode object. 
 * See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class RepeatedMeasuresServerResource  extends ServerResource
implements RepeatedMeasuresResource 
{
	
	/** The repeated measures manager. */
	RepeatedMeasuresManager repeatedMeasuresManager = null; 
	
	/** The study design manager. */
	StudyDesignManager studyDesignManager = null;
	
	/** The uuid flag. */
	boolean uuidFlag;
	
	/**
	 * Retrieve a RepeatedMeasuresNodeList object for specified UUID.
	 *
	 * @param uuid the uuid
	 * @return RepeatedMeasuresNodeList
	 */
	@Get("application/json")
	public RepeatedMeasuresNodeList retrieve(byte[] uuid) 
	{
		RepeatedMeasuresNodeList repeatedMeasuresTree = null;
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
						repeatedMeasuresTree = new RepeatedMeasuresNodeList(studyDesign.getRepeatedMeasuresTree());										
            	}				
			studyDesignManager.commit();					
		}
		catch (BaseManagerException bme)
		{
			System.out.println(bme.getMessage());
			StudyDesignLogger.getInstance().error(bme.getMessage());
			if(repeatedMeasuresManager!=null)
			{
				try
				{repeatedMeasuresManager.rollback();}				
				catch(BaseManagerException re)
				{repeatedMeasuresTree = null;}				
			}
			repeatedMeasuresTree = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {repeatedMeasuresTree = null;}					
			}
			repeatedMeasuresTree = null;
		}								
		return repeatedMeasuresTree;
	}


	/**
	 * Create a RepeatedMeasuresNodeList object for specified UUID.
	 *
	 * @param uuid the uuid
	 * @param repeatedMeasuresTree the repeated measures tree
	 * @return RepeatedMeasuresNodeList
	 */
	@Post("application/json")
	public RepeatedMeasuresNodeList create(RepeatedMeasuresNodeList repeatedMeasuresTree) 
	{		
		StudyDesign studyDesign =null;
		byte[] uuid = repeatedMeasuresTree.getUuid();
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
			 * Remove existing RepeatedMeasuresNode for this object 
			 * ----------------------------------------------------*/
			if(uuidFlag && studyDesign.getRepeatedMeasuresTree()!=null)
				removeFrom(studyDesign);				
			/* ----------------------------------------------------
			 * Save new RepeatedMeasuresNode List object 
			 * ----------------------------------------------------*/
			if(uuidFlag)
			{
				studyDesign.setRepeatedMeasuresTree(repeatedMeasuresTree.getRepeatedMeasuresList());
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
			if(repeatedMeasuresManager!=null)
			{
				try
				{repeatedMeasuresManager.rollback();}				
				catch(BaseManagerException re)
				{repeatedMeasuresTree = null;}				
			}
			repeatedMeasuresTree = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {repeatedMeasuresTree = null;}					
			}
			repeatedMeasuresTree = null;
		}								
		return repeatedMeasuresTree;
	}

	/**
	 * Update a RepeatedMeasuresNodeList object for specified UUID.
	 *
	 * @param uuid the uuid
	 * @param repeatedMeasuresTree the repeated measures tree
	 * @return RepeatedMeasuresNodeList
	 */
	@Put("application/json")
	public RepeatedMeasuresNodeList update(RepeatedMeasuresNodeList repeatedMeasuresTree) 
	{				
		return create(repeatedMeasuresTree);			
	}	

	/**
	 * Delete a RepeatedMeasuresNodeList object for specified UUID.
	 *
	 * @param uuid the uuid
	 * @return RepeatedMeasuresNodeList
	 */
	@Delete("application/json")
	public RepeatedMeasuresNodeList remove(byte[] uuid) 
	{
		RepeatedMeasuresNodeList repeatedMeasuresTree = null;
		StudyDesign studyDesign = null;
		try
		{
			/* ----------------------------------------------------
			 * Check for existence of a UUID in Study Design object 
			 * ----------------------------------------------------*/
			studyDesignManager = new StudyDesignManager();			
			studyDesignManager.beginTransaction();								
				if(uuid==null)
					throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
							"no study design UUID specified");
				uuidFlag = studyDesignManager.hasUUID(uuid);
				if(uuidFlag)
            	{		
					studyDesign = studyDesignManager.get(uuid);
					if(studyDesign!=null)
						repeatedMeasuresTree = new RepeatedMeasuresNodeList(studyDesign.getRepeatedMeasuresTree());									
            	}				
			studyDesignManager.commit();
			/* ----------------------------------------------------
			 * Remove existing RepeatedMeasuresNode objects for this object 
			 * ----------------------------------------------------*/
			if(studyDesign.getRepeatedMeasuresTree()!=null)
			{
				repeatedMeasuresManager = new RepeatedMeasuresManager();
				repeatedMeasuresManager.beginTransaction();
					repeatedMeasuresTree = new RepeatedMeasuresNodeList(repeatedMeasuresManager.delete(uuid,repeatedMeasuresTree.getRepeatedMeasuresList()));
				repeatedMeasuresManager.commit();
			}
		}
		catch (BaseManagerException bme)
		{
			System.out.println(bme.getMessage());
			StudyDesignLogger.getInstance().error(bme.getMessage());
			if(repeatedMeasuresManager!=null)
			{
				try
				{repeatedMeasuresManager.rollback();}				
				catch(BaseManagerException re)
				{repeatedMeasuresTree = null;}				
			}
			repeatedMeasuresTree = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {repeatedMeasuresTree = null;}					
			}
			repeatedMeasuresTree = null;
		}		
		return repeatedMeasuresTree;
	}

	/**
	 * Delete a RepeatedMeasuresNodeList object for specified Study Design.
	 *
	 * @param studyDesign the study design
	 * @return RepeatedMeasuresNodeList
	 */
	public RepeatedMeasuresNodeList removeFrom(StudyDesign studyDesign) 
	{
		RepeatedMeasuresNodeList repeatedMeasuresTree = null;	
        try
        {                    			
        	repeatedMeasuresManager = new RepeatedMeasuresManager();
        	repeatedMeasuresManager.beginTransaction();
        		repeatedMeasuresTree=new RepeatedMeasuresNodeList(repeatedMeasuresManager.delete(studyDesign.getUuid(),studyDesign.getRepeatedMeasuresTree()));
        	repeatedMeasuresManager.commit();        	       
        }
        catch (BaseManagerException bme)
        {
        	System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error("Failed to load Study Design information: " + bme.getMessage());
            if (studyDesignManager != null) try { studyDesignManager.rollback(); } catch (BaseManagerException e) {}
            if (repeatedMeasuresManager != null) try { repeatedMeasuresManager.rollback(); } catch (BaseManagerException e) {}
            repeatedMeasuresTree = null;           
        }
       return repeatedMeasuresTree;
	}

}
