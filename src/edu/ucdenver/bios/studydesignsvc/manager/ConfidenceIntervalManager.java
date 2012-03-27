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
package edu.ucdenver.bios.studydesignsvc.manager;

import org.hibernate.Query;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import edu.ucdenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.ucdenver.bios.webservice.common.domain.ConfidenceIntervalDescription;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManager;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;


// TODO: Auto-generated Javadoc
/**
 * Manager class which provides CRUD functionality 
 * for MySQL table ConfidenceIntervalDescription.
 * 
 * @author Uttara Sakhadeo
 */
public class ConfidenceIntervalManager extends BaseManager
{
	
	/**
	 * Instantiates a new confidence interval manager.
	 *
	 * @throws BaseManagerException the base manager exception
	 */
	public ConfidenceIntervalManager() throws BaseManagerException
	{
		super();
	}
	
	/**
	 * Check existance of a confidence interval description object by the specified UUID.
	 *
	 * @param uuid the uuid
	 * @param confidenceInterval the confidence interval
	 * @return boolean
	 */
    /*public boolean hasUUID(byte[] uuidBytes) throws StudyDesignException
    {
        if (!transactionStarted) throw new StudyDesignException("Transaction has not been started");
        try
        {
        	//byte[] uuidBytes = UUIDUtils.asByteArray(uuid);
        	ConfidenceIntervalDescription confidenceInterval = null;
        	Query query = session.createQuery("from edu.ucdenver.bios.webservice.common.domain.ConfidenceIntervalDescription where studyDesign = :uuid");
            query.setBinary("uuid", uuidBytes);	                      
            confidenceInterval = (ConfidenceIntervalDescription)query.uniqueResult(); 
        	if(confidenceInterval!=null)
        		return true;
        	else
        		return false;
        }
        catch (Exception e)
        {
            throw new StudyDesignException("Failed to retrieve StudyDesign for UUID '" + 
            		uuidBytes.toString() + "': " + e.getMessage());
        }
    }*/
	
	/**
     * Retrieve a Confidence Interval Description representation by the specified UUID.
     * 
     * @param studyUUID:UUID
     * @return study design object
     */
	/*public ConfidenceIntervalDescription get(byte[] uuidBytes)
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
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to retrieve Confidence Interval for UUID '" + uuidBytes + "': " + e.getMessage());
		}
		return confidenceInterval;
	}*/
	
	
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
     * Delete a Confidence Interval Description by the specified UUID.
     * 
     * @param studyUUID:UUID
     * @return study design object
     */
	/*public ConfidenceIntervalDescription delete(byte[] uuidBytes)
	{
		if(!transactionStarted) 
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");
		ConfidenceIntervalDescription confidenceInterval = null;
		try
		{
			confidenceInterval = get(uuidBytes);
			session.delete(confidenceInterval);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to delete study design for UUID '" + uuidBytes + "': " + e.getMessage());
		}
		return confidenceInterval;
	}*/
	
	/**
     * Delete given Confidence Interval Description
     * 
     * @param studyUUID:UUID
     * @return study design object
     */
	public ConfidenceIntervalDescription delete(byte[] uuid,ConfidenceIntervalDescription confidenceInterval)
	{
		if(!transactionStarted) 
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");		
		try
		{			
			session.delete(confidenceInterval);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to delete ConfidenceInterval for uuid '" + uuid + "': " + e.getMessage());
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
	 * Retrieve a Confidence Interval Description by the specified UUID.
	 *
	 * @param confidenceInterval the confidence interval
	 * @param isCreation the is creation
	 * @return study design object
	 */
	public ConfidenceIntervalDescription saveOrUpdate(ConfidenceIntervalDescription confidenceInterval,boolean isCreation)
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
