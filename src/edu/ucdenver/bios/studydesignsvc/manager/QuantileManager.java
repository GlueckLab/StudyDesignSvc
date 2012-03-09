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

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import edu.ucdenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.ucdenver.bios.webservice.common.domain.Quantile;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManager;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

/**
 * Manager class which provides CRUD functionality 
 * for MySQL table Quantile object.
 * 
 * @author Uttara Sakhadeo
 */
public class QuantileManager extends BaseManager
{
	public QuantileManager() throws BaseManagerException
	{
		super();
	}
	
	/**
     * Check existence of a Quantile object by the specified UUID
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
        	Query query = session.createQuery("from edu.ucdenver.bios.webservice.common.domain.Quantile where studyDesign = :uuid");
            query.setBinary("uuid", uuidBytes);	                      
            ArrayList<Quantile> quantileList= (ArrayList<Quantile>)query.list(); 
        	if(quantileList!=null)
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
     * Retrieve a Quantile object by the specified UUID.
     * 
     * @param studyUuid : byte[]
     * @return ArrayList<Quantile>
     */
	public List<Quantile> get(byte[] uuidBytes)
	{
		if(!transactionStarted) throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");
		ArrayList<Quantile> quantileList = null;
		try
		{																				
			Query query = session.createQuery("from edu.ucdenver.bios.webservice.common.domain.Quantile where studyDesign = :uuid");
            query.setBinary("uuid", uuidBytes);	                      
            quantileList = (ArrayList<Quantile>)query.list();            
		}
		catch(Exception e)
		{
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to retrieve Quantile object for UUID '" + uuidBytes + "': " + e.getMessage());
		}
		return quantileList;
	}
    
	/**
     * Delete a Quantile object by the specified UUID.
     * 
     * @param studyUuid : byte[]
     * @return ArrayList<Quantile>
     */
	public List<Quantile> delete(byte[] uuidBytes)
	{
		if(!transactionStarted) 
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");
		List<Quantile> quantileList = null;
		try
		{
			quantileList = get(uuidBytes);
			for(Quantile quantile : quantileList)
				session.delete(quantile);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to delete quantile object for UUID '" + uuidBytes + "': " + e.getMessage());
		}
		return quantileList;
	}
	
	/**
     * Retrieve a Quantile object by the specified UUID.
     * 
     * @param quantileList : ArrayList<Quantile>
     * @param isCreation : boolean
     * @return quantileList : ArrayList<Quantile>
     */
	public List<Quantile> saveOrUpdate(List<Quantile> quantileList,boolean isCreation)
	{
		if(!transactionStarted) throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");		
		try
		{			
			if(isCreation==true)
			{
				for(Quantile quantile : quantileList)				
					session.save(quantile);				
			}
			else
			{
				for(Quantile quantile : quantileList)
					session.update(quantile);
			}
		}
		catch(Exception e)
		{
			quantileList=null;
			System.out.println(e.getMessage());
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to save Quantile object : " + e.getMessage());
		}
		return quantileList;
	}
}
