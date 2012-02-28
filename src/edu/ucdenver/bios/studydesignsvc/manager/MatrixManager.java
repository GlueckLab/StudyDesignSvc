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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import edu.ucdenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrixCell;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManager;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

/**
 * Manager class which provides CRUD functionality 
 * for MySQL table NamedMatrix object.
 * 
 * @author Uttara Sakhadeo
 */
public class MatrixManager extends BaseManager
{
	public MatrixManager() throws BaseManagerException
	{
		super();
	}
	
	/**
     * Check existence of a Matrix object by the specified UUID
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
        	Query query = session.createQuery("from edu.ucdenver.bios.webservice.common.domain.NamedMatrix where studyDesign = :uuid");
            query.setBinary("uuid", uuidBytes);	                      
            List<NamedMatrix> namedMatrixList= query.list(); 
        	if(namedMatrixList!=null)
        		return true;
        	else
        		return false;
        }
        catch (Exception e)
        {
            throw new StudyDesignException("Failed to retrieve Matrix object for UUID '" + 
            		uuidBytes.toString() + "': " + e.getMessage());
        }
    }
    
    /**
     * Retrieve a Matrix object by the specified UUID.
     * 
     * @param studyUuid : byte[]
     * @return List<NamedMatrix>
     */
	public NamedMatrix get(byte[] uuidBytes,String name)
	{
		if(!transactionStarted) throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");
		//Map<String,NamedMatrix> namedMatrixMap = new HashMap<String,NamedMatrix>();
		NamedMatrix namedMatrixResult = null;
		try
		{									
			//byte[] uuidBytes = UUIDUtils.asByteArray(studyUUID);									
			Query query = session.createQuery("from edu.ucdenver.bios.webservice.common.domain.NamedMatrix where studyDesign = :uuid");
            query.setBinary("uuid", uuidBytes);	                            
            List<NamedMatrix> list = query.list();
            for(NamedMatrix matrix : list)
            {
            	if(matrix.getName().equals(name))
            	{	namedMatrixResult = matrix;}
            }		
            //Map<String,NamedMatrix> namedMatrixMap = list.
		}
		catch(Exception e)
		{
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to retrieve NamedMatrix object for UUID '" + uuidBytes + "': " + e.getMessage());
		}
		return namedMatrixResult;
	}
	
	/**
     * Retrieve a Matrix object by the specified UUID.
     * 
     * @param studyUuid : byte[]
     * @return List<NamedMatrix>
     */
	public List<NamedMatrix> get(byte[] uuidBytes)
	{
		if(!transactionStarted) throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");
		//Map<String,NamedMatrix> namedMatrixMap = new HashMap<String,NamedMatrix>();
		List<NamedMatrix> namedMatrixList = null;
		try
		{									
			//byte[] uuidBytes = UUIDUtils.asByteArray(studyUUID);									
			Query query = session.createQuery("from edu.ucdenver.bios.webservice.common.domain.NamedMatrix where studyDesign = :uuid");
            query.setBinary("uuid", uuidBytes);	                            
            namedMatrixList = query.list();            
		}
		catch(Exception e)
		{
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to retrieve NamedMatrix object for UUID '" + uuidBytes + "': " + e.getMessage());
		}
		return namedMatrixList;
	}
	
	
	
	/**
     * Delete a Matrix object by the specified UUID.
     * 
     * @param studyUuid : byte[]
     * @return List<NamedMatrix>
     */
	public NamedMatrix delete(byte[] uuidBytes,String name)
	{
		if(!transactionStarted) 
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");
		NamedMatrix namedMatrix = null;
		try
		{
			/*Map<String,NamedMatrix> namedMatrixMap = get(uuidBytes,name);
			Iterator itr = namedMatrixMap.entrySet().iterator();
			while(itr.hasNext())
			{
				Map.Entry<String, NamedMatrix> entry = (Map.Entry<String,NamedMatrix>)itr.next();
				//List<NamedMatrixCell> namedMatrixCell= entry.getValue().getMatrixCellList();
				if(entry.getKey().equals(name))
				{
					namedMatrix = entry.getValue();
					session.delete(entry.getValue());
				}
			}*/
			namedMatrix = get(uuidBytes,name);
			session.delete(namedMatrix);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to delete NamedMatrix object for UUID '" + uuidBytes + "': " + e.getMessage());
		}
		return namedMatrix;
	}
	
	/**
     * Delete a Matrix object by the specified UUID.
     * 
     * @param studyUuid : byte[]
     * @return List<NamedMatrix>
     */
	public List<NamedMatrix> delete(byte[] uuidBytes)
	{
		if(!transactionStarted) 
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");
		List<NamedMatrix> namedMatrixList = null;
		try
		{
			/*Map<String,NamedMatrix> namedMatrixMap = get(uuidBytes,name);
			Iterator itr = namedMatrixMap.entrySet().iterator();
			while(itr.hasNext())
			{
				Map.Entry<String, NamedMatrix> entry = (Map.Entry<String,NamedMatrix>)itr.next();
				//List<NamedMatrixCell> namedMatrixCell= entry.getValue().getMatrixCellList();
				if(entry.getKey().equals(name))
				{
					namedMatrix = entry.getValue();
					session.delete(entry.getValue());
				}
			}*/
			namedMatrixList = get(uuidBytes);
			for(NamedMatrix namedMatrix : namedMatrixList)
			{	session.delete(namedMatrix);}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to delete NamedMatrix object for UUID '" + uuidBytes + "': " + e.getMessage());
		}
		return namedMatrixList;
	}
	
	/**
     * Retrieve a NamedMatrix object by the specified UUID.
     * 
     * @param namedMatrixList : List<NamedMatrix>
     * @param isCreation : boolean
     * @return namedMatrixList : List<NamedMatrix>
     */
	public List<NamedMatrix> saveOrUpdate(List<NamedMatrix> namedMatrixList,boolean isCreation)
	{
		if(!transactionStarted) throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");		
		try
		{			
			if(isCreation==true)
			{
				for(NamedMatrix NamedMatrix : namedMatrixList)				
					session.save(NamedMatrix);				
			}
			else
			{
				for(NamedMatrix NamedMatrix : namedMatrixList)
					session.update(NamedMatrix);
			}
		}
		catch(Exception e)
		{
			namedMatrixList=null;
			System.out.println(e.getMessage());
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to save NamedMatrix object : " + e.getMessage());
		}
		return namedMatrixList;
	}
}
