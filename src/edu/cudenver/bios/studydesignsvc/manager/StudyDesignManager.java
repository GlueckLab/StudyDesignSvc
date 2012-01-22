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
import java.util.UUID;

import org.hibernate.Query;
import edu.cudenver.bios.studydesignsvc.domain.StudyDesign;
import edu.cudenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManager;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;

/**
 * 
 * @author Uttara Sakhadeo
 *
 */
public class StudyDesignManager extends BaseManager
{
	/**
	 * Create a database manager class for study design objects
	 * 
	 * @throws StudyDesignException
	 */
	public StudyDesignManager() throws BaseManagerException
	{
		super();
	}
	
    /**
     * Retrieve a study design object by the specified UUID
     * 
     * @param dataFeedUuid
     * @return data feed object
     */
    public StudyDesign getStudyDesign(UUID uuid) throws StudyDesignException
    {
        if (!transactionStarted) throw new StudyDesignException("Transaction has not been started");
        try
        {
        	byte[] uuidBytes = UUIDUtils.asByteArray(uuid);
        	StudyDesign studyDesign = (StudyDesign) session.get(StudyDesign.class, uuidBytes);
            return studyDesign;
        }
        catch (Exception e)
        {
            throw new StudyDesignException("Failed to retrieve StudyDesign for UUID '" + 
            		uuid.toString() + "': " + e.getMessage());
        }
    }

	
	/*
	 * Retrieve 
	 */
	public List<UUID> getStudyUUIDs() throws StudyDesignException
	{
		if(!transactionStarted) throw new StudyDesignException("Transaction has not been started.");
		try
		{
			Query query = session.createQuery("select studyUUID from StudyDesign");
			//Query query = session.createQuery("select StudyUUID from tablestudydesign");
			@SuppressWarnings("unchecked")
			List<UUID> results = query.list();
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
     * Retrieve a study design representation by the specified UUID
     * 
     * @param studyUUID:String
     * @return study design object
     */
	public StudyDesign getStudyDesign(String studyUUID)
	throws StudyDesignException
	{
		if(!transactionStarted) throw new StudyDesignException("Transaction has not been started.");
		StudyDesign studyDesign = null;
		try
		{
			Query q = session.createQuery("select name from edu.cudenver.bios.studydesignsvc.domain.StudyDesign");
			List ls = q.list();			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			throw new StudyDesignException("Failed to retrieve study design for UUID '" + studyUUID + "': " + e.getMessage());
		}
		return studyDesign;
	}
	
	/**
     * Delete a study design representation by the specified UUID
     * 
     * @param studyUUID:UUID
     * @return study design object
     */
	public StudyDesign deleteStudyDesign(UUID studyUUID)
	throws StudyDesignException
	{
		if (!transactionStarted) 
			throw new StudyDesignException("Transaction has not been started.");
		StudyDesign studyDesign = null;
		try
		{
			studyDesign = getStudyDesign(studyUUID);
			session.delete(studyDesign);
		}
		catch(Exception e)
		{
			//throw new StudyDesignException("Failed to delete study design for UUID '" + studyUUID + "': " + e.getMessage());
			System.out.println(e.getMessage());
			throw new StudyDesignException("Failed to delete study design for UUID '" + studyUUID + "': " + e.getMessage());
		}
		return studyDesign;
	}
		
	/**
     * Create or update a study design object in the database
     * 
     * @param studyUUID:UUID
     * @return study design object
     */
	public StudyDesign saveOrUpdateStudyDesign(StudyDesign studyDesign, boolean isCreation)
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
			byte[] studyUUID = (byte[]) session.getIdentifier(studyDesign);
		}
		catch(Exception e)
		{
			throw new StudyDesignException("Failed to save study design : " + e.getMessage());
		}
		return studyDesign;
	}
}
