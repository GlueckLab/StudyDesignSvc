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
import java.util.UUID;

import org.hibernate.Query;

import edu.ucdenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManager;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Manager class which provides CRUD functionality 
 * for MySQL table StudyDesign.
 * 
 * @author Uttara Sakhadeo
 */
public class StudyDesignManager extends BaseManager
{
	
	/**
	 * Create a database manager class for study design objects.
	 *
	 * @throws BaseManagerException the base manager exception
	 */
	public StudyDesignManager() throws BaseManagerException
	{
		super();
	}
	
	/**
	 * Check existance of a study design object by the specified UUID.
	 *
	 * @param uuidBytes the uuid bytes
	 * @return boolean
	 * @throws StudyDesignException the study design exception
	 */
    public boolean hasUUID(byte[] uuidBytes) throws StudyDesignException
    {
        if (!transactionStarted) throw new StudyDesignException("Transaction has not been started");
        try
        {
        	//byte[] uuidBytes = UUIDUtils.asByteArray(uuid);
        	StudyDesign studyDesign = (StudyDesign) session.get(StudyDesign.class, uuidBytes);
        	if(studyDesign!=null)
        		return true;
        	else
        		return false;
        }
        catch (Exception e)
        {
            throw new StudyDesignException("Failed to retrieve StudyDesign for UUID '" + 
            		uuidBytes.toString() + "': " + e.getMessage());
        }
    }
	
    /**
     * Retrieve a study design object by the specified UUID.
     *
     * @param uuidBytes the uuid bytes
     * @return data feed object
     * @throws StudyDesignException the study design exception
     */
    public StudyDesign get(byte[] uuidBytes) throws StudyDesignException
    {
        if (!transactionStarted) throw new StudyDesignException("Transaction has not been started");
        try
        {
        	//byte[] uuidBytes = UUIDUtils.asByteArray(uuid);
        	StudyDesign studyDesign = (StudyDesign) session.get(StudyDesign.class, uuidBytes);
            return studyDesign;
        }
        catch (Exception e)
        {
        	System.out.println(e.getMessage());
            throw new StudyDesignException("Failed to retrieve StudyDesign for UUID '" + 
            		uuidBytes.toString() + "': " + e.getMessage());
        }
    }

	
	/*
	 * Retrieve 
	 */
	/**
	 * Gets the study uui ds.
	 *
	 * @return the study uui ds
	 * @throws StudyDesignException the study design exception
	 */
	public List<byte[]> getStudyUUIDs() throws StudyDesignException
	{
		if(!transactionStarted) throw new StudyDesignException("Transaction has not been started.");
		try
		{
			Query query = session.createQuery("select uuid from edu.ucdenver.bios.webservice.common.domain.StudyDesign");
			//Query query = session.createQuery("select StudyUUID from tablestudydesign");
			@SuppressWarnings("unchecked")
			List<byte[]> results = query.list();
			return results;
		}
		catch(Exception e)
		{
			throw new StudyDesignException("Failed to retrieve uuids: "+e.getMessage());
		}
	}
	
	/**
     * Gets the study uui ds.
     *
     * @return the study uui ds
     * @throws StudyDesignException the study design exception
     */
    public List<StudyDesign> getStudyDesigns() throws StudyDesignException
    {
        if(!transactionStarted) throw new StudyDesignException("Transaction has not been started.");
        try
        {
            Query query = session.createQuery("from edu.ucdenver.bios.webservice.common.domain.StudyDesign");
            //Query query = session.createQuery("select StudyUUID from tablestudydesign");
            @SuppressWarnings("unchecked")
            List<StudyDesign> results = query.list();
            return results;
        }
        catch(Exception e)
        {
            throw new StudyDesignException("Failed to retrieve uuids: "+e.getMessage());
        }
    }
	
//	 /**
//     * Retrieve a study design representation by the specified UUID
//     * 
//     * @param studyUUID:UUID
//     * @return study design object
//     */
//	public StudyDesign getStudyDesign(UUID studyUUID)
//	{
//		if (!transactionStarted) 
//			throw new StudyDesignException("Transaction has not been started.");
//		StudyDesign studyDesign = null;
//		try
//		{			
//			studyDesign = (StudyDesign)session.get(StudyDesign.class,studyUUID.toString());
//		}
//		catch(Exception e)
//		{
//			throw new StudyDesignException("Failed to retrieve study design for UUID '" + studyUUID + "': " + e.getMessage());
//		}
//		return studyDesign;
//	}
	
	/**
 * Retrieve a study design representation by the specified UUID.
 *
 * @param uuidBytes the uuid bytes
 * @return study design object
 * @throws StudyDesignException the study design exception
 */
	/*public StudyDesign get(String studyUUID)
	throws StudyDesignException
	{
		if(!transactionStarted) throw new StudyDesignException("Transaction has not been started.");
		StudyDesign studyDesign = null;
		try
		{
			Query q = session.createQuery("select name StudyDesign");
			List ls = q.list();			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			throw new StudyDesignException("Failed to retrieve study design for UUID '" + studyUUID + "': " + e.getMessage());
		}
		return studyDesign;
	}*/
	
	/**
     * Delete a study design representation by the specified UUID
     * 
     * @param studyUUID:UUID
     * @return study design object
     */
	public StudyDesign delete(byte[] uuidBytes)
	throws StudyDesignException
	{
		if (!transactionStarted) 
			throw new StudyDesignException("Transaction has not been started.");
		StudyDesign studyDesign = null;
		try
		{
			studyDesign = get(uuidBytes);
			session.delete(studyDesign);
		}
		catch(Exception e)
		{
			//throw new StudyDesignException("Failed to delete study design for UUID '" + studyUUID + "': " + e.getMessage());
			System.out.println(e.getMessage());
			throw new StudyDesignException("Failed to delete study design for UUID '" + uuidBytes + "': " + e.getMessage());
		}
		return studyDesign;
	}
		
	/**
	 * Create or update a study design object in the database.
	 *
	 * @param studyDesign the study design
	 * @param isCreation the is creation
	 * @return study design object
	 * @throws StudyDesignException the study design exception
	 */
	public StudyDesign saveOrUpdate(StudyDesign studyDesign, boolean isCreation)
	throws StudyDesignException
	{
		if (!transactionStarted) 
			throw new StudyDesignException("Transaction has not been started.");		
		
		try
		{			
			if (isCreation==true)
				session.save(studyDesign);
			else
				session.update(studyDesign);			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			throw new StudyDesignException("Failed to save study design : " + e.getMessage()+"\n"+e.getStackTrace());
		}
		return studyDesign;
	}
}
