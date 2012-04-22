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
import edu.ucdenver.bios.webservice.common.domain.ClusterNode;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManager;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;
// TODO: Auto-generated Javadoc
/**
 * Manager class which provides CRUD functionality 
 * for MySQL table ClusterNode object.
 * 
 * @author Uttara Sakhadeo
 */
public class ClusterNodeManager extends StudyDesignParentManager {
    
	/**
	 * Instantiates a new cluster node manager.
	 *
	 * @throws BaseManagerException the base manager exception
	 */
	public ClusterNodeManager() throws BaseManagerException {super();}
	
	/**
	 * Check existance of a Cluster Node object by the specified UUID.
	 *
	 * @param uuidBytes the uuid bytes
	 * @param clusteringTree the clustering tree
	 * @return boolean
	 */
    /*public boolean hasUUID(byte[] uuidBytes) throws StudyDesignException
    {
        if (!transactionStarted) throw new StudyDesignException("Transaction has not been started");
        try
        {
        	//byte[] uuidBytes = UUIDUtils.asByteArray(uuid);        	
        	Query query = session.createQuery("from edu.ucdenver.bios.webservice.common.domain.ClusterNode where studyDesign = :uuid");
            query.setBinary("uuid", uuidBytes);	                      
            List<ClusterNode> clusterNodeList= (List<ClusterNode>)query.list(); 
        	if(clusterNodeList!=null)
        		return true;
        	else
        		return false;
        }
        catch (Exception e)
        {
            throw new StudyDesignException("Failed to retrieve Cluster Node for UUID '" + 
            		uuidBytes.toString() + "': " + e.getMessage());
        }
    }*/
    
    /**
     * Retrieve a Cluster Node by the specified UUID.
     * 
     * @param studyUUID:UUID
     * @return study design object
     */
	/*public List<ClusterNode> get(byte[] uuidBytes)
	{
		if(!transactionStarted) throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");
		List<ClusterNode> clusterNodeList = null;
		try
		{									
			//byte[] uuidBytes = UUIDUtils.asByteArray(studyUUID);									
			Query query = session.createQuery("from edu.ucdenver.bios.webservice.common.domain.ClusterNode where studyDesign = :uuid");
            query.setBinary("uuid", uuidBytes);	                      
            clusterNodeList = (List<ClusterNode>)query.list();            
		}
		catch(Exception e)
		{
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to retrieve Cluster Node for UUID '" + uuidBytes + "': " + e.getMessage());
		}
		return clusterNodeList;
	}*/
	
	/**
     * Delete a Confidence Interval Description by the specified UUID.
     * 
     * @param studyUUID:UUID
     * @return study design object
     */
	public List<ClusterNode> delete(byte[] uuidBytes,List<ClusterNode> clusteringTree)
	{
		if(!transactionStarted) 
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");		
		try
		{
			for(ClusterNode clusterNode : clusteringTree)
				session.delete(clusterNode);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to delete Cluster Node for UUID '" + uuidBytes + "': " + e.getMessage());
		}
		return clusteringTree;
	}
	
	/**
	 * Retrieve a Cluster Node by the specified UUID.
	 *
	 * @param clusterNodeList the cluster node list
	 * @param isCreation the is creation
	 * @return study design object
	 */
	public List<ClusterNode> saveOrUpdate(List<ClusterNode> clusterNodeList,boolean isCreation)
	{
		if(!transactionStarted) throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");		
		try
		{			
			if(isCreation==true)
			{
				for(ClusterNode clusterNode : clusterNodeList)				
					session.save(clusterNode);				
			}
			else
			{
				for(ClusterNode clusterNode : clusterNodeList)
					session.update(clusterNode);
			}
		}
		catch(Exception e)
		{
			clusterNodeList=null;
			System.out.println(e.getMessage());
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to save Cluster Node : " + e.getMessage());
		}
		return clusterNodeList;
	}

}
