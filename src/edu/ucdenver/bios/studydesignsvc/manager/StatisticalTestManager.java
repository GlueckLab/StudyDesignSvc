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
import edu.ucdenver.bios.webservice.common.domain.BetaScale;
import edu.ucdenver.bios.webservice.common.domain.NominalPower;
import edu.ucdenver.bios.webservice.common.domain.StatisticalTest;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManager;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

/**
 * Manager class which provides CRUD functionality 
 * for MySQL table StatisticalTest object.
 * 
 * @author Uttara Sakhadeo
 */
public class StatisticalTestManager extends BaseManager
{
	public StatisticalTestManager() throws BaseManagerException
	{
		super();
	}
	
	/**
     * Check existence of a StatisticalTest object by the specified UUID
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
        	Query query = session.createQuery("from edu.ucdenver.bios.webservice.common.domain.StatisticalTest where studyDesign = :uuid");
            query.setBinary("uuid", uuidBytes);	                      
            List<StatisticalTest> testList= query.list(); 
        	if(testList!=null)
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
     * Retrieve a StatisticalTest object by the specified UUID.
     * 
     * @param studyUuid : byte[]
     * @return List<StatisticalTest>
     */
	public List<StatisticalTest> get(byte[] uuidBytes)
	{
		if(!transactionStarted) throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");
		List<StatisticalTest> testList = null;
		try
		{																				
			Query query = session.createQuery("from edu.ucdenver.bios.webservice.common.domain.StatisticalTest where studyDesign = :uuid");
            query.setBinary("uuid", uuidBytes);	                      
            testList = query.list();            
		}
		catch(Exception e)
		{
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to retrieve StatisticalTest object for UUID '" + uuidBytes + "': " + e.getMessage());
		}
		return testList;
	}
	
	/**
     * Delete a StatisticalTest object by the specified UUID.
     * 
     * @param studyUuid : byte[]
     * @return List<StatisticalTest>
     */
	public List<StatisticalTest> delete(byte[] uuidBytes)
	{
		if(!transactionStarted) 
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");
		List<StatisticalTest> testList = null;
		try
		{
			testList = get(uuidBytes);
			for(StatisticalTest test : testList)
				session.delete(test);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to delete StatisticalTest object for UUID '" + uuidBytes + "': " + e.getMessage());
		}
		return testList;
	}
	
	/**
     * Retrieve a StatisticalTest object by the specified UUID.
     * 
     * @param testList : List<StatisticalTest>
     * @param isCreation : boolean
     * @return testList : List<StatisticalTest>
     */
	public List<StatisticalTest> saveOrUpdate(List<StatisticalTest> testList,boolean isCreation)
	{
		if(!transactionStarted) throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");		
		try
		{			
			if(isCreation==true)
			{
				for(StatisticalTest test : testList)				
					session.save(test);				
			}
			else
			{
				for(StatisticalTest test : testList)
					session.update(test);
			}
		}
		catch(Exception e)
		{
			testList=null;
			System.out.println(e.getMessage());
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to save StatisticalTest object : " + e.getMessage());
		}
		return testList;
	}
}

