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

import org.apache.log4j.Logger;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.cudenver.bios.studydesignsvc.application.StudyDesignConstants;
import edu.cudenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.cudenver.bios.studydesignsvc.domain.ConfidenceInterval;
import edu.cudenver.bios.studydesignsvc.manager.ConfidenceIntervalManager;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

/**
 * Resource class for handling requests for the complete 
 * study design object. 
 * See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class ConfidenceIntervalServerResource extends ServerResource implements ConfidenceIntervalResource
{
	private Logger logger = StudyDesignLogger.getInstance();
	private String studyUUID = null;
	@Override
	public ConfidenceInterval retrieve() 
	{
		ConfidenceIntervalManager manager = null;
		ConfidenceInterval confidenceInterval = null; 
		try
		{
			this.studyUUID = this.getRequest().getAttributes().get(StudyDesignConstants.TAG_STUDY_UUID).toString();
			//System.out.println(this.studyUUID);
			//studyDesign=new StudyDesign(this.studyUUID);			
			manager = new ConfidenceIntervalManager();
			manager.beginTransaction();		
				//check wheather this UUID is existing
				/*StudyDesignManager studyDesignManager = new StudyDesignManager();
				if(studyDesignManager.searchStudyUUID(this.studyUUID))
					confidenceInterval=manager.getConfidenceInterval(this.studyUUID);
				else
					throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,"No such UUID exists!!!");*/
				confidenceInterval=manager.getConfidenceInterval(this.studyUUID);
				/*List<UUID> list = manager.getStudyUUIDs();			
				if(list!=null)
				{
					System.out.println(list.toString());
				}
				else
				{
					System.out.println("empty list");
				}*/	
			manager.commit();
		}
		catch (BaseManagerException bme)
		{
			StudyDesignLogger.getInstance().error("ConfidenceIntervalResource : " + bme.getMessage());
			if(manager!=null)
			{
				try
				{manager.rollback();}				
				catch(BaseManagerException re)
				{confidenceInterval = null;}				
			}
		}	
		catch (ResourceException e)
		{
			StudyDesignLogger.getInstance().error("ConfidenceIntervalResource : " + e.getMessage());
			if(manager!=null)
			{
				try
				{manager.rollback();}				
				catch(BaseManagerException re)
				{confidenceInterval = null;}				
			}
		}						
		return confidenceInterval;			
	}
	@Override
	public ConfidenceInterval create(ConfidenceInterval confidenceInterval) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ConfidenceInterval update(ConfidenceInterval instance) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ConfidenceInterval remove() {
		// TODO Auto-generated method stub
		return null;
	}
		
}
