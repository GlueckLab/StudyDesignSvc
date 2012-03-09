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
import edu.ucdenver.bios.webservice.common.domain.ResponseNode;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManager;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;
/**
 * Manager class which provides CRUD functionality 
 * for MySQL table Responses object.
 * 
 * @author Uttara Sakhadeo
 */
public class ResponsesManager extends BaseManager 
{
	public ResponsesManager() throws BaseManagerException
	{
		super();
	}
	
	/**
     * Check existence of a ResponseNode object by the specified UUID
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
        	Query query = session.createQuery("from edu.ucdenver.bios.webservice.common.domain.ResponseNode where studyDesign = :uuid");
            query.setBinary("uuid", uuidBytes);	                      
            List<ResponseNode> responseNodeList= (List<ResponseNode>)query.list(); 
        	if(responseNodeList!=null)
        		return true;
        	else
        		return false;
        }
        catch (Exception e)
        {
            throw new StudyDesignException("Failed to retrieve ResponseNode object for UUID '" + 
            		uuidBytes.toString() + "': " + e.getMessage());
        }
    }
    
    /**
     * Retrieve a ResponseNode object by the specified UUID.
     * 
     * @param studyUuid : byte[]
     * @return List<ResponseNode>
     */
	public List<ResponseNode> get(byte[] uuidBytes)
	{
		if(!transactionStarted) throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");
		List<ResponseNode> responseNodeList = null;
		try
		{									
			//byte[] uuidBytes = UUIDUtils.asByteArray(studyUUID);									
			Query query = session.createQuery("from edu.ucdenver.bios.webservice.common.domain.ResponseNode where studyDesign = :uuid");
            query.setBinary("uuid", uuidBytes);	                      
            responseNodeList = (List<ResponseNode>)query.list();            
		}
		catch(Exception e)
		{
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to retrieve ResponseNode object for UUID '" + uuidBytes + "': " + e.getMessage());
		}
		return responseNodeList;
	}
	
	/**
     * Delete a ResponseNode object by the specified UUID.
     * 
     * @param studyUuid : byte[]
     * @return List<ResponseNode>
     */
	public List<ResponseNode> delete(byte[] uuidBytes)
	{
		if(!transactionStarted) 
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");
		List<ResponseNode> responseNodeList = null;
		try
		{
			responseNodeList = get(uuidBytes);
			for(ResponseNode ResponseNode : responseNodeList)
				session.delete(ResponseNode);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to delete ResponseNode object for UUID '" + uuidBytes + "': " + e.getMessage());
		}
		return responseNodeList;
	}
	
	/**
     * Retrieve a ResponseNode object by the specified UUID.
     * 
     * @param responseNodeList : List<ResponseNode>
     * @param isCreation : boolean
     * @return responseNodeList : List<ResponseNode>
     */
	public List<ResponseNode> saveOrUpdate(List<ResponseNode> responseNodeList,boolean isCreation)
	{
		if(!transactionStarted) throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");		
		try
		{			
			if(isCreation==true)
			{
				for(ResponseNode responseNode : responseNodeList)				
					session.save(responseNode);				
			}
			else
			{
				for(ResponseNode responseNode : responseNodeList)
					session.update(responseNode);
			}
		}
		catch(Exception e)
		{
			responseNodeList=null;
			System.out.println(e.getMessage());
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to save ResponseNode object : " + e.getMessage());
		}
		return responseNodeList;
	}
}
