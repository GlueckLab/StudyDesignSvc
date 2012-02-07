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

import org.hibernate.Query;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import edu.cudenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.ucdenver.bios.webservice.common.domain.ConfidenceIntervalDescription;
import edu.ucdenver.bios.webservice.common.domain.PowerCurveDescription;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManager;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

/**
 * Manager class which provides CRUD functionality 
 * for MySQL table PowerCurveDescription.
 * 
 * @author Uttara Sakhadeo
 */
public class PowerCurveManager extends BaseManager 
{
	public PowerCurveManager() throws BaseManagerException
	{
		super();
	}
	
	/**
     * Check existance of a power curve description object by the specified UUID
     * 
     * @param studyUuid
     * @return boolean
     */
    public boolean hasUUID(byte[] uuidBytes) throws StudyDesignException
    {
        if (!transactionStarted) throw new StudyDesignException("Transaction has not been started");
        try
        {
        	//byte[] uuidBytes = UUIDUtils.asByteArray(uuid);
        	PowerCurveDescription powerCurveDescription = null;
        	Query query = session.createQuery("from edu.ucdenver.bios.webservice.common.domain.PowerCurveDescription where studyDesign = :uuid");
            query.setBinary("uuid", uuidBytes);	      
            /*List<PowerCurveDescription> list = query.list();
            powerCurveDescription = list.get(0);*/
            powerCurveDescription = (PowerCurveDescription)query.uniqueResult(); 
        	if(powerCurveDescription!=null)
        		return true;
        	else
        		return false;
        }
        catch (Exception e)
        {
        	System.out.println(e.getMessage());
            throw new StudyDesignException("Failed to retrieve StudyDesign for UUID '" + 
            		uuidBytes.toString() + "': " + e.getMessage());
        }
    }
	
	/**
     * Retrieve a power curve description by the specified UUID.
     * 
     * @param studyUUID:UUID
     * @return study design object
     */
	public PowerCurveDescription get(byte[] uuidBytes)
	{
		if(!transactionStarted) throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");
		PowerCurveDescription powerCurveDescription = null;
		try
		{									
			//byte[] uuidBytes = UUIDUtils.asByteArray(studyUUID);									
			Query query = session.createQuery("from edu.ucdenver.bios.webservice.common.domain.PowerCurveDescription where studyDesign = :uuid");
            query.setBinary("uuid", uuidBytes);	                      
            powerCurveDescription = (PowerCurveDescription)query.uniqueResult();            
		}
		catch(Exception e)
		{
			//System.out.println(e.getMessage());
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to retrieve study design for UUID '" + uuidBytes + "': " + e.getMessage());
		}
		return powerCurveDescription;
	}
	
	/**
     * Delete a power curve description by the specified UUID.
     * 
     * @param studyUUID:UUID
     * @return study design object
     */
	public PowerCurveDescription delete(byte[] uuidBytes)
	{
		if(!transactionStarted) 
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");
		PowerCurveDescription powerCurveDescription = null;
		try
		{
			powerCurveDescription = get(uuidBytes);
			session.delete(powerCurveDescription);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to delete study design for UUID '" + uuidBytes + "': " + e.getMessage());
		}
		return powerCurveDescription;
	}
	
	/**
     * Retrieve a power curve description by the specified UUID.
     * 
     * @param studyUUID:UUID
     * @return study design object
     */
	public PowerCurveDescription saveOrUpdate(PowerCurveDescription powerCurveDescription,boolean isCreation)
	{
		if(!transactionStarted) throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");		
		try
		{			
			if(isCreation==true)
				session.save(powerCurveDescription);
			else
				session.update(powerCurveDescription);			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to save confidence interval : " + e.getMessage());
		}
		return powerCurveDescription;
	}
}
