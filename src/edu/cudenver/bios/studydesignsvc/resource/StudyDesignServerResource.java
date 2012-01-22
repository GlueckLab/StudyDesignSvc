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

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.cudenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.cudenver.bios.studydesignsvc.domain.StudyDesign;
import edu.cudenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.cudenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

/**
 * Resource class for handling requests for the complete 
 * study design object. 
 * See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class StudyDesignServerResource extends ServerResource implements StudyDesignResource
{
	private static Logger logger = StudyDesignLogger.getInstance();

	/**
	 * Retrieve the study design matching the specified UUID.
	 * Returns "not found" if no matching designs are available
	 * @return study designs with specified UUID
	 */
	@Override
	public StudyDesign retrieve(UUID uuid)
	{
		StudyDesignManager manager = null;
		StudyDesign studyDesign = null; 
		
		try
		{
			if (uuid == null) throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
					"no study design UUID specified");
	
			manager = new StudyDesignManager();
			manager.beginTransaction();		
			studyDesign = manager.getStudyDesign(uuid);

			manager.commit();
		}
		catch(BaseManagerException bme)
		{
			StudyDesignLogger.getInstance().error("StudyDesignResource : " + bme.getMessage());
			if(manager!=null)
			{
				try {manager.rollback();}
				catch(BaseManagerException re) {studyDesign = null;}					
			}
		}	
		catch(StudyDesignException sde)
		{
			StudyDesignLogger.getInstance().error("StudyDesignResource : " + sde.getMessage());
			if(manager!=null)
			{
				try {manager.rollback();}
				catch(BaseManagerException re) {studyDesign = null;}					
			}
		}						
		return studyDesign;			
	}

	@Override
	public List<StudyDesign> retrieve()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StudyDesign create(StudyDesign studyDesign)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StudyDesign update(StudyDesign studyDesign)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StudyDesign remove(UUID uuid)
	{
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public StudyDesign retrieve() 
//	{
//		StudyDesignManager manager = null;
//		StudyDesign studyDesign = null; 
//		try
//		{
//			studyUUID = studyUUIDStr = this.getRequest().getAttributes().get(StudyDesignConstants.TAG_STUDY_UUID);
//			if (studyUUIDStr == null)
//			//System.out.println(this.studyUUID);
//			//studyDesign=new StudyDesign(this.studyUUID);			
//			manager = new StudyDesignManager();
//			manager.beginTransaction();		
//				studyDesign=manager.getStudyDesign(this.studyUUID);
//				/*List<UUID> list = manager.getStudyUUIDs();			
//				if(list!=null)
//				{
//					System.out.println(list.toString());
//				}
//				else
//				{
//					System.out.println("empty list");
//				}*/	
//			manager.commit();
//		}
//		catch(ResourceException e)
//		{
//			StudyDesignLogger.getInstance().error("StudyDesignResource : "+e.getMessage());
//			if(manager!=null)
//			{
//				try
//				{manager.rollback();}				
//				catch(ResourceException re)
//				{studyDesign = null;}				
//			}
//		}						
//		return studyDesign;			
//	}
//	
//	
//	@Override
//	public StudyDesign create(StudyDesign studyDesign) 
//	{
//		StudyDesignManager manager = null;    	
//		try
//		{
//			/* create a new UUID for the study design */
//			studyDesign.setStudyUUID(UUID.randomUUID());
//			/* save it to the database */
//			manager = new StudyDesignManager();
//			manager.beginTransaction();
//			manager.saveOrUpdateStudyDesign(studyDesign, true);
//			manager.commit();
//		}
//		catch(ResourceException e)
//		{
//			StudyDesignLogger.getInstance().error("StudyDesignResource.read - Failed to load UUIDs from database: "+e.getMessage());
//			if(manager!=null)
//			{
//				try
//				{manager.rollback();}				
//				catch(ResourceException re)
//				{studyDesign = null;}				
//			}
//		}		
//    	/* return the study design with updated UUID */
//		return studyDesign;	  
//	}
//	@Override
//	public StudyDesign update(StudyDesign contact) 
//	{
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	public StudyDesign remove() 
//	{
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	public StudyDesign retrieve(UUID uuid)
//	{
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	public StudyDesign remove(UUID uuid)
//	{
//		// TODO Auto-generated method stub
//		return null;
//	}
//	
	
}
