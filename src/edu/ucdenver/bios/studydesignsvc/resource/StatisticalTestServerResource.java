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
package edu.ucdenver.bios.studydesignsvc.resource;

import org.restlet.data.Status;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.ucdenver.bios.studydesignsvc.manager.StatisticalTestManager;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.domain.StatisticalTestList;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

/**
 * Server Resource class for handling requests for the StatisticalTest object. 
 * See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class StatisticalTestServerResource extends ServerResource
implements StatisticalTestResource
{
	StatisticalTestManager testManager = null; 
	StudyDesignManager studyDesignManager = null;
	boolean uuidFlag;

	@Get("json")
	public StatisticalTestList retrieve(byte[] uuid) 
	{
		StatisticalTestList testList = null;
		if(uuid==null)
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
					"no study design UUID specified");		
		try
		{
			/* ----------------------------------------------------
			 * Check for existence of a UUID in Study Design object 
			 * ----------------------------------------------------*/
			studyDesignManager = new StudyDesignManager();			
			studyDesignManager.beginTransaction();								
				uuidFlag = studyDesignManager.hasUUID(uuid);
				if(uuidFlag)
            	{		
					StudyDesign studyDesign = studyDesignManager.get(uuid);
					if(studyDesign!=null)
						testList = new StatisticalTestList(studyDesign.getStatisticalTestList());					
            	}				
			studyDesignManager.commit();					
		}
		catch (BaseManagerException bme)
		{
			System.out.println(bme.getMessage());
			StudyDesignLogger.getInstance().error(bme.getMessage());
			if(testManager!=null)
			{
				try
				{testManager.rollback();}				
				catch(BaseManagerException re)
				{testList = null;}				
			}
			testList = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {testList = null;}					
			}
			testList = null;
		}								
		return testList;
	}

	@Post("json")
	public StatisticalTestList create(byte[] uuid,StatisticalTestList testList) 
	{		
		StudyDesign studyDesign =null;
		if(uuid==null)
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
					"no study design UUID specified");		
		try
		{
			/* ----------------------------------------------------
			 * Check for existence of a UUID in Study Design object 
			 * ----------------------------------------------------*/
			studyDesignManager = new StudyDesignManager();
			studyDesignManager.beginTransaction();				
				uuidFlag = studyDesignManager.hasUUID(uuid);				
				if(uuidFlag)
            	{studyDesign = studyDesignManager.get(uuid);}																									            				
				else
				{throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
						"no study design UUID specified");}
			studyDesignManager.commit();
			/* ----------------------------------------------------
			 * Remove existing StatisticalTest for this object 
			 * ----------------------------------------------------*/			
			if(uuidFlag && studyDesign.getStatisticalTestList()!=null)
				removeFrom(studyDesign);	
			/* ----------------------------------------------------
			 * Set reference of Study Design Object to each StatisticalTest element 
			 * ----------------------------------------------------*/	
			/*for(StatisticalTest test : testList)					
				test.setStudyDesign(studyDesign);*/			
			/* ----------------------------------------------------
			 * Save new StatisticalTest List object 
			 * ----------------------------------------------------*/
			if(uuidFlag)
			{
				studyDesign.setStatisticalTestList(testList);
				studyDesignManager = new StudyDesignManager();
				studyDesignManager.beginTransaction();
					studyDesignManager.saveOrUpdate(studyDesign, false);
				studyDesignManager.commit();	
			}
		}
		catch (BaseManagerException bme)
		{
			System.out.println(bme.getMessage());
			StudyDesignLogger.getInstance().error(bme.getMessage());
			if(testManager!=null)
			{
				try
				{testManager.rollback();}				
				catch(BaseManagerException re)
				{testList = null;}				
			}
			testList = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {testList = null;}					
			}
			testList = null;
		}								
		return testList;
	}

	@Put("json")
	public StatisticalTestList update(byte[] uuid,StatisticalTestList testList) {
		return create(uuid,testList);
	}

	@Delete("json")
	public StatisticalTestList remove(byte[] uuid) 
	{
		StatisticalTestList testList = null;
		StudyDesign studyDesign = null;
		if(uuid==null)
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
					"no study design UUID specified");		
		try
		{
			/* ----------------------------------------------------
			 * Check for existence of a UUID in Study Design object 
			 * ----------------------------------------------------*/
			studyDesignManager = new StudyDesignManager();			
			studyDesignManager.beginTransaction();								
				uuidFlag = studyDesignManager.hasUUID(uuid);
				if(uuidFlag)
            	{		
					studyDesign = studyDesignManager.get(uuid);
					if(studyDesign!=null)
						testList = new StatisticalTestList(studyDesign.getStatisticalTestList());
					if(testList.isEmpty())
						throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
								"no StatisticalTest is specified");					
            	}				
			studyDesignManager.commit();
			/* ----------------------------------------------------
			 * Remove existing StatisticalTest objects for this object 
			 * ----------------------------------------------------*/
			if(testList!=null)
			{
				testManager = new StatisticalTestManager();
				testManager.beginTransaction();
					testList = new StatisticalTestList(testManager.delete(uuid,testList));
				testManager.commit();
			}
		}
		catch (BaseManagerException bme)
		{
			System.out.println(bme.getMessage());
			StudyDesignLogger.getInstance().error(bme.getMessage());
			if(testManager!=null)
			{
				try
				{testManager.rollback();}				
				catch(BaseManagerException re)
				{testList = null;}				
			}
			testList = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {testList = null;}					
			}
			testList = null;
		}		
		return testList;
	}
	
	/**
     * Delete a StatisticalTest object for specified Study Design.
     * 
     * @param StudyDesign
     * @return StatisticalTestList
     */
	public StatisticalTestList removeFrom(StudyDesign studyDesign) 
	{
		StatisticalTestList testList = null;	
        try
        {                    			
        	testManager = new StatisticalTestManager();
        	testManager.beginTransaction();
        		testList=new StatisticalTestList(testManager.delete(studyDesign.getUuid(),studyDesign.getStatisticalTestList()));
        	testManager.commit();        	       
        }
        catch (BaseManagerException bme)
        {
        	System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error("Failed to load Study Design information: " + bme.getMessage());
            if (studyDesignManager != null) try { studyDesignManager.rollback(); } catch (BaseManagerException e) {}
            if (testManager != null) try { testManager.rollback(); } catch (BaseManagerException e) {}
            testList = null;           
        }
       return testList;
	}
}
