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
import edu.ucdenver.bios.webservice.common.domain.NominalPower;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManager;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Manager class which provides CRUD functionality 
 * for MySQL table NominalPower object.
 * 
 * @author Uttara Sakhadeo
 */
public class NominalPowerManager extends BaseManager
{
	
	/**
	 * Instantiates a new nominal power manager.
	 *
	 * @throws BaseManagerException the base manager exception
	 */
	public NominalPowerManager() throws BaseManagerException
	{
		super();
	}
	
	/**
	 * Check existence of a Nominal Power object by the specified UUID.
	 *
	 * @param uuidBytes the uuid bytes
	 * @param nominalPowerList the nominal power list
	 * @return boolean
	 */
    /*public boolean hasUUID(byte[] uuidBytes) throws StudyDesignException
    {
        if (!transactionStarted) throw new StudyDesignException("Transaction has not been started");
        try
        {
        	//byte[] uuidBytes = UUIDUtils.asByteArray(uuid);
        	Query query = session.createQuery("from edu.ucdenver.bios.webservice.common.domain.NominalPower where studyDesign = :uuid");
            query.setBinary("uuid", uuidBytes);	                      
            ArrayList<NominalPower> nominalPowerList= (ArrayList<NominalPower>)query.list(); 
        	if(nominalPowerList!=null)
        		return true;
        	else
        		return false;
        }
        catch (Exception e)
        {
            throw new StudyDesignException("Failed to retrieve Beta Scale object for UUID '" + 
            		uuidBytes.toString() + "': " + e.getMessage());
        }
    }*/
    
    /**
     * Retrieve a Nominal Power object by the specified UUID.
     * 
     * @param studyUuid : byte[]
     * @return ArrayList<NominalPower>
     */
	/*public List<NominalPower> get(byte[] uuidBytes)
	{
		if(!transactionStarted) throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");
		List<NominalPower> nominalPowerList = null;
		try
		{																				
			Query query = session.createQuery("from edu.ucdenver.bios.webservice.common.domain.NominalPower where studyDesign = :uuid");
            query.setBinary("uuid", uuidBytes);	                      
            nominalPowerList = query.list();            
		}
		catch(Exception e)
		{
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to retrieve Nominal Power object for UUID '" + uuidBytes + "': " + e.getMessage());
		}
		return nominalPowerList;
	}*/
	
	/**
     * Delete a NominalPower object by the specified UUID.
     * 
     * @param studyUuid : byte[]
     * @return ArrayList<NominalPower>
     */
	public List<NominalPower> delete(byte[] uuidBytes,List<NominalPower> nominalPowerList)
	{
		if(!transactionStarted) 
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");
		try
		{
			for(NominalPower nominalPower : nominalPowerList)
				session.delete(nominalPower);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to delete NominalPower object for UUID '" + uuidBytes + "': " + e.getMessage());
		}
		return nominalPowerList;
	}
	
	/**
     * Retrieve a NominalPower object by the specified UUID.
     * 
     * @param nominalPowerList : ArrayList<NominalPower>
     * @param isCreation : boolean
     * @return nominalPowerList : ArrayList<NominalPower>
     */
	public ArrayList<NominalPower> saveOrUpdate(ArrayList<NominalPower> nominalPowerList,boolean isCreation)
	{
		if(!transactionStarted) throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");		
		try
		{			
			if(isCreation==true)
			{
				for(NominalPower nominalPower : nominalPowerList)				
					session.save(nominalPower);				
			}
			else
			{
				for(NominalPower nominalPower : nominalPowerList)
					session.update(nominalPower);
			}
		}
		catch(Exception e)
		{
			nominalPowerList=null;
			System.out.println(e.getMessage());
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to save NominalPower object : " + e.getMessage());
		}
		return nominalPowerList;
	}
}
