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

import java.util.UUID;

import org.apache.log4j.Logger;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.cudenver.bios.studydesignsvc.application.StudyDesignConstants;
import edu.cudenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.webservice.common.domain.ConfidenceIntervalDescription;
import edu.cudenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.cudenver.bios.studydesignsvc.manager.ConfidenceIntervalManager;
import edu.cudenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.domain.ConfidenceIntervalDescription;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;

/**
 * Resource class for handling requests for the complete 
 * study design object. 
 * See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class ConfidenceIntervalServerResource extends ServerResource implements ConfidenceIntervalResource
{
	StudyDesignManager studyDesignManager = null;
	ConfidenceIntervalManager confidenceIntervalManager = null;
	ConfidenceIntervalDescription confidenceInterval = null;
	
	private Logger logger = StudyDesignLogger.getInstance();
	private String studyUUID = null;
	@Override
	public ConfidenceIntervalDescription retrieve(byte[] uuid) 
	{			
		boolean uuidFlag;
		try
		{
			if (uuid == null) throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
					"no study design UUID specified");
	
			studyDesignManager = new StudyDesignManager();
			studyDesignManager.beginTransaction();
				uuidFlag = studyDesignManager.hasUUID(uuid);
				if(uuidFlag==true)
            	{
            		confidenceInterval.setStudyDesign(studyDesignManager.get(uuid));
            	}
			studyDesignManager.commit();
			
			if(uuidFlag==true)
			{			
				confidenceIntervalManager = new ConfidenceIntervalManager();
            	confidenceIntervalManager.beginTransaction();
            		confidenceInterval = confidenceIntervalManager.get(uuid);
            	confidenceIntervalManager.commit();
			}
		}
		catch (BaseManagerException bme)
		{
			StudyDesignLogger.getInstance().error("ConfidenceIntervalResource : " + bme.getMessage());
			if(confidenceIntervalManager!=null)
			{
				try
				{confidenceIntervalManager.rollback();}				
				catch(BaseManagerException re)
				{confidenceInterval = null;}				
			}
		}	
		catch(StudyDesignException sde)
		{
			StudyDesignLogger.getInstance().error("StudyDesignResource : " + sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {confidenceInterval = null;}					
			}
		}							
		return confidenceInterval;			
	}
	
	@Override
	public ConfidenceIntervalDescription create(
			ConfidenceIntervalDescription confidenceInterval) 
	{		boolean uuidFlag;
		try
		{
			studyDesignManager = new StudyDesignManager();
			studyDesignManager.beginTransaction();
				byte[] uuid = confidenceInterval.getStudyDesign().getUuid();
				if(uuid==null)
					throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
							"no study design UUID specified");
				uuidFlag = studyDesignManager.hasUUID(uuid);
				if(uuidFlag==true)
            	{					
            		confidenceInterval.setStudyDesign(studyDesignManager.get(uuid));
            	}
			studyDesignManager.commit();
			
			if(uuidFlag==true)
			{		
				confidenceIntervalManager = new ConfidenceIntervalManager();
            	confidenceIntervalManager.beginTransaction();
            		confidenceIntervalManager.saveOrUpdate(confidenceInterval, true);
            	confidenceIntervalManager.commit();
			}
		}
		catch (BaseManagerException bme)
		{
			StudyDesignLogger.getInstance().error("ConfidenceIntervalResource : " + bme.getMessage());
			if(confidenceIntervalManager!=null)
			{
				try
				{confidenceIntervalManager.rollback();}				
				catch(BaseManagerException re)
				{confidenceInterval = null;}				
			}
		}	
		catch(StudyDesignException sde)
		{
			StudyDesignLogger.getInstance().error("StudyDesignResource : " + sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {confidenceInterval = null;}					
			}
		}							
		return confidenceInterval;
	}
	@Override
	public ConfidenceIntervalDescription update(
			ConfidenceIntervalDescription confidenceInterval) 
	{
		boolean uuidFlag;
		try
		{
			studyDesignManager = new StudyDesignManager();
			studyDesignManager.beginTransaction();
				byte[] uuid = confidenceInterval.getStudyDesign().getUuid();
				if(uuid==null)
					throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
							"no study design UUID specified");
				uuidFlag = studyDesignManager.hasUUID(uuid);
				if(uuidFlag==true)
            	{
            		confidenceInterval.setStudyDesign(studyDesignManager.get(uuid));
            	}
			studyDesignManager.commit();
			
			if(uuidFlag==true)
			{
				confidenceIntervalManager = new ConfidenceIntervalManager();
            	confidenceIntervalManager.beginTransaction();
            		confidenceIntervalManager.saveOrUpdate(confidenceInterval, false);
            	confidenceIntervalManager.commit();
			}
		}
		catch (BaseManagerException bme)
		{
			StudyDesignLogger.getInstance().error("ConfidenceIntervalResource : " + bme.getMessage());
			if(confidenceIntervalManager!=null)
			{
				try
				{confidenceIntervalManager.rollback();}				
				catch(BaseManagerException re)
				{confidenceInterval = null;}				
			}
		}	
		catch(StudyDesignException sde)
		{
			StudyDesignLogger.getInstance().error("StudyDesignResource : " + sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {confidenceInterval = null;}					
			}
		}							
		return confidenceInterval;
	}

	@Override
	public ConfidenceIntervalDescription remove(byte[] uuid) 
	{
		boolean flag;	
		ConfidenceIntervalDescription confidenceInterval = null;
        try
        {
            studyDesignManager = new StudyDesignManager();
            studyDesignManager.beginTransaction();
            	flag = studyDesignManager.hasUUID(uuid);
            	/*if(flag==true)
            	{
            		confidenceInterval.setStudyDesign(studyDesignManager.getStudyDesign(STUDY_UUID));
            	}*/
            studyDesignManager.commit();
            if(flag==true)
            {
            	confidenceIntervalManager = new ConfidenceIntervalManager();
            	confidenceIntervalManager.beginTransaction();
            		confidenceInterval=confidenceIntervalManager.delete(uuid);
            	confidenceIntervalManager.commit();
            }
            else
            	throw new StudyDesignException("No such studyUUID present in tableStudyDesign!!!");
        }
        catch (BaseManagerException bme)
        {
        	System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error("Failed to load Study Design information: " + bme.getMessage());
            if (studyDesignManager != null) try { studyDesignManager.rollback(); } catch (BaseManagerException e) {}
            if (confidenceIntervalManager != null) try { confidenceIntervalManager.rollback(); } catch (BaseManagerException e) {}
            confidenceInterval = null;           
        }
        catch (StudyDesignException sde)
        {
            StudyDesignLogger.getInstance().error("Failed to load Study Design information: " + sde.getMessage());
            if (studyDesignManager != null) try { studyDesignManager.rollback(); } catch (BaseManagerException e) {}
            if (confidenceIntervalManager != null) try { confidenceIntervalManager.rollback(); } catch (BaseManagerException e) {}
            confidenceInterval = null;            
        }
        return confidenceInterval;
	}
		
}
