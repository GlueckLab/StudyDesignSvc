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

import java.util.List;

import org.hibernate.Query;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import edu.ucdenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.ucdenver.bios.webservice.common.domain.PowerMethod;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManager;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

/**
 * Manager class which provides CRUD functionality 
 * for MySQL table Power Method object.
 * 
 * @author Uttara Sakhadeo
 */
public class PowerMethodManager extends BaseManager
{
	public PowerMethodManager() throws BaseManagerException
	{
		super();
	}
	
	/**
     * Check existence of a Power Method object by the specified UUID
     * 
     * @param studyUuid : byte[]
     * @return boolean
     */
    public boolean hasUUID(byte[] uuidBytes) throws StudyDesignException
    {
        if (!transactionStarted) throw new StudyDesignException("Transaction has not been started");
        try
        {
        	//byte[] uuidBytes = UUIDUtils.asByteArray(uuid);
        	Query query = session.createQuery("from edu.ucdenver.bios.webservice.common.domain.PowerMethod where studyDesign = :uuid");
            query.setBinary("uuid", uuidBytes);	                      
            List<PowerMethod> powerMethodList= query.list(); 
        	if(powerMethodList!=null)
        		return true;
        	else
        		return false;
        }
        catch (Exception e)
        {
            throw new StudyDesignException("Failed to retrieve Beta Scale object for UUID '" + 
            		uuidBytes.toString() + "': " + e.getMessage());
        }
    }
    
    /**
     * Retrieve a PowerMethod object by the specified UUID.
     * 
     * @param studyUuid : byte[]
     * @return List<PowerMethod>
     */
	public List<PowerMethod> get(byte[] uuidBytes)
	{
		if(!transactionStarted) throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");
		List<PowerMethod> powerMethodList = null;
		try
		{																				
			Query query = session.createQuery("from edu.ucdenver.bios.webservice.common.domain.PowerMethod where studyDesign = :uuid");
            query.setBinary("uuid", uuidBytes);	                      
            powerMethodList = query.list();            
		}
		catch(Exception e)
		{
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to retrieve PowerMethod object for UUID '" + uuidBytes + "': " + e.getMessage());
		}
		return powerMethodList;
	}
	
	/**
     * Delete a PowerMethod object by the specified UUID.
     * 
     * @param studyUuid : byte[]
     * @return List<PowerMethod>
     */
	public List<PowerMethod> delete(byte[] uuidBytes)
	{
		if(!transactionStarted) 
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");
		List<PowerMethod> powerMethodList = null;
		try
		{
			powerMethodList = get(uuidBytes);
			for(PowerMethod powerMethod : powerMethodList)
				session.delete(powerMethod);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to delete PowerMethod object for UUID '" + uuidBytes + "': " + e.getMessage());
		}
		return powerMethodList;
	}
	
	/**
     * Retrieve a PowerMethod object by the specified UUID.
     * 
     * @param powerMethodList : List<PowerMethod>
     * @param isCreation : boolean
     * @return powerMethodList : List<PowerMethod>
     */
	public List<PowerMethod> saveOrUpdate(List<PowerMethod> powerMethodList,boolean isCreation)
	{
		if(!transactionStarted) throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");		
		try
		{			
			if(isCreation==true)
			{
				for(PowerMethod powerMethod : powerMethodList)				
					session.save(powerMethod);				
			}
			else
			{
				for(PowerMethod powerMethod : powerMethodList)
					session.update(powerMethod);
			}
		}
		catch(Exception e)
		{
			powerMethodList=null;
			System.out.println(e.getMessage());
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to save PowerMethod object : " + e.getMessage());
		}
		return powerMethodList;
	}
}

