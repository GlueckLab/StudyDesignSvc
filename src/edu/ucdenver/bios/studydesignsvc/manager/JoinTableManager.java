package edu.ucdenver.bios.studydesignsvc.manager;

import edu.ucdenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManager;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

public class JoinTableManager extends BaseManager
{
	/**
	 * Create a database manager class for JoinTableManager
	 * 
	 * @throws StudyDesignException
	 */
	public JoinTableManager() throws BaseManagerException {super();}
	
	/**
     * Create or update an object in the database
     * 
     * @param studyUUID:UUID
     * @return study design object
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
