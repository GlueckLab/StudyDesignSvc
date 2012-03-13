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
import edu.ucdenver.bios.studydesignsvc.manager.PowerMethodManager;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.studydesignsvc.resource.PowerMethodResource;
import edu.ucdenver.bios.webservice.common.domain.PowerMethod;
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

	@Get("json")
	public List<PowerMethod> retrieve(byte[] uuid) 
	{
		List<PowerMethod> powerMethodList = null;
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
					StudyDesign studyDesign = studyDesignManager.get(uuid);
					powerMethodList = studyDesign.getPowerMethodList();
					if(powerMethodList.isEmpty())
						throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
								"no TypeIError is specified");
					else
					{
						for(PowerMethod powerMethod : powerMethodList)					
							powerMethod.setStudyDesign(studyDesign);									
					}
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

	@Post("json")
	public List<PowerMethod> create(List<PowerMethod> powerMethodList) 
	{		
		StudyDesign studyDesign =null;
		try
		{
			/* ----------------------------------------------------
			 * Check for existence of a UUID in Study Design object 
			 * ----------------------------------------------------*/
			studyDesignManager = new StudyDesignManager();
			studyDesignManager.beginTransaction();				
				byte[] uuid = powerMethodList.get(0).getStudyDesign().getUuid();
				if(uuid==null)
					throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
							"no study design UUID specified");
				uuidFlag = studyDesignManager.hasUUID(uuid);				
				if(uuidFlag)
            	{studyDesign = studyDesignManager.get(uuid);}																									            				
				else
				{throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
						"no study design UUID specified");}
			studyDesignManager.commit();
			/* ----------------------------------------------------
			 * Remove existing Power Method for this object 
			 * ----------------------------------------------------*/			
			if(studyDesign.getPowerMethodList()!=null)
				remove(uuid);	
			/* ----------------------------------------------------
			 * Set reference of Study Design Object to each Power Method element 
			 * ----------------------------------------------------*/	
			for(PowerMethod PowerMethod : powerMethodList)					
				PowerMethod.setStudyDesign(studyDesign);
			studyDesign.setPowerMethodList(powerMethodList);
			/* ----------------------------------------------------
			 * Save new Power Method List object 
			 * ----------------------------------------------------*/
			studyDesignManager = new StudyDesignManager();
			studyDesignManager.beginTransaction();
				studyDesignManager.saveOrUpdate(studyDesign, false);
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

	@Put("json")
	public List<PowerMethod> update(List<PowerMethod> powerMethodList) 
	{
		return create(powerMethodList);
	}

	@Delete("json")
	public List<PowerMethod> remove(byte[] uuid) 
	{
		List<PowerMethod> powerMethodList = null;
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
					powerMethodList = studyDesign.getPowerMethodList();
					if(powerMethodList.isEmpty())
						throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
								"no TypeIError is specified");					
            	}				
			studyDesignManager.commit();
			/* ----------------------------------------------------
			 * Remove existing Power Method objects for this object 
			 * ----------------------------------------------------*/
			if(studyDesign.getPowerMethodList()!=null)
			{
				powerMethodManager = new PowerMethodManager();
				powerMethodManager.beginTransaction();
					powerMethodList = powerMethodManager.delete(uuid);
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

}
