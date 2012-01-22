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
package edu.cudenver.bios.studydesignsvc.manager;

import java.util.List;
import java.util.UUID;


import org.hibernate.Query;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import edu.cudenver.bios.studydesignsvc.domain.ConfidenceInterval;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManager;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;


/**
 * 
 * @author Uttara Sakhadeo
 *
 */
public class ConfidenceIntervalManager extends BaseManager
{
	public ConfidenceIntervalManager() throws BaseManagerException
	{
		super();
	}
	
	/*
	 * Retrieve 
	 */
	public List<UUID> getStudyUUIDs() throws ResourceException
	{
		if(!transactionStarted) throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");
		try
		{
			Query query = session.createQuery("select studyUUID from ConfidenceInterval");
			//Query query = session.createQuery("select StudyUUID from tablestudydesign");
			@SuppressWarnings("unchecked")
			List<UUID> results = query.list();
			return results;
		}
		catch(Exception e)
		{
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,"Failed to retrieve uuids: "+e.getMessage());
		}
	}
	
	 /**
     * Retrieve a study design representation by the specified UUID
     * 
     * @param studyUUID:UUID
     * @return study design object
     */
	public ConfidenceInterval getConfidenceInterval(UUID studyUUID)
	{
		if(!transactionStarted) throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");
		ConfidenceInterval confidenceInterval = null;
		try
		{			
			confidenceInterval = (ConfidenceInterval)session.get(ConfidenceInterval.class,studyUUID.toString());
		}
		catch(Exception e)
		{
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to retrieve study design for UUID '" + studyUUID + "': " + e.getMessage());
		}
		return confidenceInterval;
	}
	
	/**
     * Retrieve a study design representation by the specified UUID
     * 
     * @param studyUUID:String
     * @return study design object
     */
	public ConfidenceInterval getConfidenceInterval(String studyUUID)
	{
		if(!transactionStarted) 
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");
		ConfidenceInterval confidenceInterval = null;
		try
		{			
			Query q = session.createQuery("FROM edu.cudenver.bios.studydesignsvc.domain.ConfidenceInterval");			
			confidenceInterval = (ConfidenceInterval)q.uniqueResult();
			System.out.println(""+confidenceInterval.getLowerTailProbability());
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to retrieve study design for UUID '" + studyUUID + "': " + e.getMessage());
		}
		return confidenceInterval;
	}
	
	/**
     * Delete a study design representation by the specified UUID
     * 
     * @param studyUUID:UUID
     * @return study design object
     */
	public ConfidenceInterval deleteConfidenceInterval(UUID studyUUID)
	{
		if(!transactionStarted) 
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");
		ConfidenceInterval confidenceInterval = null;
		try
		{
			confidenceInterval = getConfidenceInterval(studyUUID.toString());
			session.delete(confidenceInterval);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to delete study design for UUID '" + studyUUID + "': " + e.getMessage());
		}
		return confidenceInterval;
	}
	
	/**
     * Delete a study design representation by the specified UUID
     * 
     * @param studyUUID:String
     * @return study design object
     */
	public ConfidenceInterval deleteConfidenceInterval(String studyUUID)
	{
		if(!transactionStarted) throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");
		ConfidenceInterval confidenceInterval = null;
		try
		{
			confidenceInterval = getConfidenceInterval(studyUUID);
			session.delete(confidenceInterval);
		}
		catch(Exception e)
		{
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to delete study design for UUID '" + studyUUID + "': " + e.getMessage());
		}
		return confidenceInterval;
	}
	
	/**
     * Retrieve a study design representation by the specified UUID
     * 
     * @param studyUUID:UUID
     * @return study design object
     */
	public String saveOrUpdateConfidenceInterval(ConfidenceInterval confidenceInterval,boolean isCreation)
	{
		if(!transactionStarted) throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");		
		try
		{			
			if(isCreation==true)
				session.save(confidenceInterval);
			else
				session.update(confidenceInterval);
			UUID studyUUID = (UUID)session.getIdentifier(confidenceInterval);
		}
		catch(Exception e)
		{
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to save study design : " + e.getMessage());
		}
		return confidenceInterval.getStudyUUID().toString();
	}
}
