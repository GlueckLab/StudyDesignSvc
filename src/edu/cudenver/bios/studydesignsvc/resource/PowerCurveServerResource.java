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

import java.util.Iterator;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.cudenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.cudenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.cudenver.bios.studydesignsvc.manager.ConfidenceIntervalManager;
import edu.cudenver.bios.studydesignsvc.manager.PowerCurveManager;
import edu.cudenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.domain.ConfidenceIntervalDescription;
import edu.ucdenver.bios.webservice.common.domain.PowerCurveDescription;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

/**
 * Concrete class which implements methods of 
 * Power Curve Resource interface. 
 * See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class PowerCurveServerResource extends ServerResource implements PowerCurveResource 
{
	PowerCurveDescription powerCurveDescription = null;
	StudyDesignManager studyDesignManager = null;
	PowerCurveManager powerCurveManager = null;
	
	@Override
	public PowerCurveDescription retrieve(byte[] uuid) 
	{	
		boolean uuidFlag;
		try
		{
			if (uuid == null) throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
					"no study design UUID specified");
	
			studyDesignManager = new StudyDesignManager();
			studyDesignManager.beginTransaction();
				uuidFlag = studyDesignManager.hasUUID(uuid);
				/*if(uuidFlag==true)
            	{
					powerCurveDescription = new PowerCurveDescription();
					StudyDesign studyDesign = studyDesignManager.getStudyDesign(uuid);
					powerCurveDescription.setStudyDesign(studyDesign);
            	}*/
			studyDesignManager.commit();
			
			if(uuidFlag==true)
			{			
				powerCurveManager = new PowerCurveManager();
				powerCurveManager.beginTransaction();
            	powerCurveDescription = powerCurveManager.get(uuid);
            	powerCurveManager.commit();
			}
		}
		catch (BaseManagerException bme)
		{
			StudyDesignLogger.getInstance().error("ConfidenceIntervalResource : " + bme.getMessage());
			if(powerCurveManager!=null)
			{
				try
				{powerCurveManager.rollback();}				
				catch(BaseManagerException re)
				{powerCurveDescription = null;}				
			}
		}	
		catch(StudyDesignException sde)
		{
			StudyDesignLogger.getInstance().error("PowerCurveResource : " + sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {powerCurveManager.rollback();}
				catch(BaseManagerException re) {powerCurveDescription = null;}					
			}
		}							
		return powerCurveDescription;
	}

	@Override
	public PowerCurveDescription create(
			PowerCurveDescription powerCurveDescription) 
	{		
		boolean uuidFlag;
		try
		{
			studyDesignManager = new StudyDesignManager();
			studyDesignManager.beginTransaction();
				byte[] uuid = powerCurveDescription.getStudyDesign().getUuid();
				if(uuid==null)
					throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
							"no study design UUID specified");
				uuidFlag = studyDesignManager.hasUUID(uuid);
				if(uuidFlag==true)
            	{
            		powerCurveDescription.setStudyDesign(studyDesignManager.get(uuid));
            	}
			studyDesignManager.commit();
			
			if(uuidFlag==true)
			{		
				powerCurveManager = new PowerCurveManager();
				powerCurveManager.beginTransaction();
					powerCurveManager.saveOrUpdate(powerCurveDescription, true);
				powerCurveManager.commit();
			}
		}
		catch (BaseManagerException bme)
		{
			StudyDesignLogger.getInstance().error("ConfidenceIntervalResource : " + bme.getMessage());
			if(powerCurveManager!=null)
			{
				try
				{powerCurveManager.rollback();}				
				catch(BaseManagerException re)
				{powerCurveDescription = null;}				
			}
		}	
		catch(StudyDesignException sde)
		{
			StudyDesignLogger.getInstance().error("StudyDesignResource : " + sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {powerCurveDescription = null;}					
			}
		}							
		return powerCurveDescription;
	}

	@Override
	public PowerCurveDescription update(
			PowerCurveDescription powerCurveDescription) 
	{
		boolean uuidFlag;
		try
		{
			studyDesignManager = new StudyDesignManager();
			studyDesignManager.beginTransaction();
				byte[] uuid = powerCurveDescription.getStudyDesign().getUuid();
				if(uuid==null)
					throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
							"no study design UUID specified");
				uuidFlag = studyDesignManager.hasUUID(uuid);
				
				if(uuidFlag==true)
            	{					
					StudyDesign studyDesign = studyDesignManager.get(uuid);
					Iterator<PowerCurveDescription> itr = studyDesign.getPowerCurveDescriptions().iterator();
					while(itr.hasNext())
					{
						PowerCurveDescription presentObject = itr.next();
						if(presentObject.getStudyDesign().getUuid()==powerCurveDescription.getStudyDesign().getUuid())
						{
							powerCurveDescription.setId(presentObject.getId());
							powerCurveDescription.setStudyDesign(studyDesign);
						}
					}
            		
            	}
			studyDesignManager.commit();
			
			if(uuidFlag==true)
			{					
				powerCurveManager = new PowerCurveManager();
				powerCurveManager.beginTransaction();
					powerCurveManager.saveOrUpdate(powerCurveDescription, false);
            	powerCurveManager.commit();
			}
		}
		catch (BaseManagerException bme)
		{
			StudyDesignLogger.getInstance().error("ConfidenceIntervalResource : " + bme.getMessage());
			if(powerCurveManager!=null)
			{
				try
				{powerCurveManager.rollback();}				
				catch(BaseManagerException re)
				{powerCurveDescription = null;}				
			}
		}	
		catch(StudyDesignException sde)
		{
			StudyDesignLogger.getInstance().error("StudyDesignResource : " + sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {powerCurveDescription = null;}					
			}
		}							
		return powerCurveDescription;
	}

	@Override
	public PowerCurveDescription remove(byte[] uuid) 
	{
		boolean flag;	
		PowerCurveDescription powerCurveDescription = null;
        try
        {
            studyDesignManager = new StudyDesignManager();
            studyDesignManager.beginTransaction();	           
				Boolean uuidFlag = studyDesignManager.hasUUID(uuid);							
            studyDesignManager.commit();
            if(uuidFlag)
            {
            	powerCurveManager = new PowerCurveManager();
            	powerCurveManager.beginTransaction();
            		uuidFlag = powerCurveManager.hasUUID(uuid);
            		if(uuidFlag)
            			powerCurveDescription=powerCurveManager.delete(uuid);
            	powerCurveManager.commit();
            }
            else
            	throw new StudyDesignException("No such studyUUID present in tableStudyDesign!!!");
        }
        catch (BaseManagerException bme)
        {
        	System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error("Failed to load Study Design information: " + bme.getMessage());
            if (studyDesignManager != null) try { studyDesignManager.rollback(); } catch (BaseManagerException e) {}
            if (powerCurveManager != null) try { powerCurveManager.rollback(); } catch (BaseManagerException e) {}
            powerCurveDescription = null;           
        }
        catch (StudyDesignException sde)
        {
            StudyDesignLogger.getInstance().error("Failed to load Study Design information: " + sde.getMessage());
            if (studyDesignManager != null) try { studyDesignManager.rollback(); } catch (BaseManagerException e) {}
            if (powerCurveManager != null) try { powerCurveManager.rollback(); } catch (BaseManagerException e) {}
            powerCurveDescription = null;            
        }
        return powerCurveDescription;
	}

}
