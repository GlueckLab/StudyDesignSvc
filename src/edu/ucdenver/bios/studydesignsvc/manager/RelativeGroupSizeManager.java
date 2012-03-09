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
import edu.ucdenver.bios.webservice.common.domain.RelativeGroupSize;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManager;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

/**
 * Manager class which provides CRUD functionality 
 * for MySQL table RelativeGroupSize object.
 * 
 * @author Uttara Sakhadeo
 */
public class RelativeGroupSizeManager extends BaseManager
{
	public RelativeGroupSizeManager() throws BaseManagerException
	{
		super();
	}
	
	/**
     * Check existence of a RelativeGroupSize object by the specified UUID
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
        	Query query = session.createQuery("from edu.ucdenver.bios.webservice.common.domain.RelativeGroupSize where studyDesign = :uuid");
            query.setBinary("uuid", uuidBytes);	                      
            List<RelativeGroupSize> relativeGroupSizeList= (List<RelativeGroupSize>)query.list(); 
        	if(relativeGroupSizeList!=null)
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
     * Retrieve a RelativeGroupSize object by the specified UUID.
     * 
     * @param studyUuid : byte[]
     * @return List<RelativeGroupSize>
     */
	public List<RelativeGroupSize> get(byte[] uuidBytes)
	{
		if(!transactionStarted) throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");
		List<RelativeGroupSize> relativeGroupSizeList = null;
		try
		{																				
			Query query = session.createQuery("from edu.ucdenver.bios.webservice.common.domain.RelativeGroupSize where studyDesign = :uuid");
            query.setBinary("uuid", uuidBytes);	                      
            relativeGroupSizeList = (List<RelativeGroupSize>)query.list();            
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to retrieve RelativeGroupSize object for UUID '" + uuidBytes + "': " + e.getMessage());
		}
		return relativeGroupSizeList;
	}
	
	/**
     * Delete a RelativeGroupSize object by the specified UUID.
     * 
     * @param studyUuid : byte[]
     * @return List<RelativeGroupSize>
     */
	public List<RelativeGroupSize> delete(byte[] uuidBytes)
	{
		if(!transactionStarted) 
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");
		List<RelativeGroupSize> relativeGroupSizeList = null;
		try
		{
			relativeGroupSizeList = get(uuidBytes);
			for(RelativeGroupSize relativeGroupSize : relativeGroupSizeList)
				session.delete(relativeGroupSize);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to delete RelativeGroupSize object for UUID '" + uuidBytes + "': " + e.getMessage());
		}
		return relativeGroupSizeList;
	}
	
	/**
     * Retrieve a RelativeGroupSize object by the specified UUID.
     * 
     * @param relativeGroupSizeList : List<RelativeGroupSize>
     * @param isCreation : boolean
     * @return relativeGroupSizeList : List<RelativeGroupSize>
     */
	public List<RelativeGroupSize> saveOrUpdate(List<RelativeGroupSize> relativeGroupSizeList,boolean isCreation)
	{
		if(!transactionStarted) throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");		
		try
		{			
			if(isCreation==true)
			{
				for(RelativeGroupSize relativeGroupSize : relativeGroupSizeList)				
					session.save(relativeGroupSize);				
			}
			else
			{
				for(RelativeGroupSize relativeGroupSize : relativeGroupSizeList)
					session.update(relativeGroupSize);
			}
		}
		catch(Exception e)
		{
			relativeGroupSizeList=null;
			System.out.println(e.getMessage());
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to save RelativeGroupSize object : " + e.getMessage());
		}
		return relativeGroupSizeList;
	}
}
