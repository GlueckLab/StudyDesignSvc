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


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;


import edu.ucdenver.bios.webservice.common.domain.ConfidenceIntervalDescription;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManager;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;


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
	
	/**
     * Retrieve a study design representation by the specified UUID
     * 
     * @param studyUUID:UUID
     * @return study design object
     */
	public ConfidenceIntervalDescription getConfidenceInterval(byte[] uuidBytes)
	{
		if(!transactionStarted) throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");
		ConfidenceIntervalDescription confidenceInterval = null;
		try
		{									
			//byte[] uuidBytes = UUIDUtils.asByteArray(studyUUID);									
			Query query = session.createQuery("from edu.ucdenver.bios.webservice.common.domain.ConfidenceIntervalDescription where studyDesign = :uuid");
            query.setBinary("uuid", uuidBytes);	                      
            confidenceInterval = (ConfidenceIntervalDescription)query.list().get(0);            
		}
		catch(Exception e)
		{
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to retrieve study design for UUID '" + uuidBytes + "': " + e.getMessage());
		}
		return confidenceInterval;
	}
	
	
	/*public ConfidenceInterval getConfidenceInterval(String studyUUID)
	{
		if(!transactionStarted) 
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");
		ConfidenceInterval confidenceInterval = null;
		try
		{			
			
			byte[] uuidBytes = UUIDUtils.asByteArray(UUID.fromString(studyUUID));
			
			Criteria criteria= session.createCriteria(ConfidenceInterval.class, "studyDesign").add(Restrictions.eq("studyDesign", uuidBytes));
			confidenceInterval = (ConfidenceInterval)criteria.uniqueResult();			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to retrieve study design for UUID '" + studyUUID + "': " + e.getMessage());
		}
		return confidenceInterval;
	}*/
	
	/**
     * Delete a study design representation by the specified UUID
     * 
     * @param studyUUID:UUID
     * @return study design object
     */
	public ConfidenceIntervalDescription deleteConfidenceInterval(byte[] uuidBytes)
	{
		if(!transactionStarted) 
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");
		ConfidenceIntervalDescription confidenceInterval = null;
		try
		{
			confidenceInterval = getConfidenceInterval(uuidBytes);
			session.delete(confidenceInterval);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to delete study design for UUID '" + uuidBytes + "': " + e.getMessage());
		}
		return confidenceInterval;
	}
	
	
	/*public ConfidenceInterval deleteConfidenceInterval(String studyUUID)
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
	}*/
	
	/**
     * Retrieve a study design representation by the specified UUID
     * 
     * @param studyUUID:UUID
     * @return study design object
     */
	public ConfidenceIntervalDescription saveOrUpdateConfidenceInterval(ConfidenceIntervalDescription confidenceInterval,boolean isCreation)
	{
		if(!transactionStarted) throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");		
		try
		{			
			if(isCreation==true)
				session.save(confidenceInterval);
			else
				session.update(confidenceInterval);			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to save confidence interval : " + e.getMessage());
		}
		return confidenceInterval;
	}
}
