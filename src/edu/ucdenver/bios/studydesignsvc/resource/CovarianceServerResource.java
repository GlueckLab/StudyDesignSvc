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

import java.util.Set;

import org.restlet.data.Status;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.ucdenver.bios.studydesignsvc.manager.CovarianceManager;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.domain.Covariance;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;
/**
 * Server Resource class for handling requests for the Covariance object. 
 * See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class CovarianceServerResource extends ServerResource 
implements CovarianceResource
{
	CovarianceManager covarianceManager = null;
	StudyDesignManager studyDesignManager = null;
	boolean uuidFlag;
	
	/**
     * Retrieve a Covariance object for specified UUID.
     * 
     * @param byte[]
     * @return Set<Covariance>
     */
	@Get
	public Set<Covariance> retrieve(byte[] uuid) 
	{
		Set<Covariance> covarianceSet = null;
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
						covarianceSet = studyDesign.getCovariance();					
            	}				
			studyDesignManager.commit();					
		}
		catch (BaseManagerException bme)
		{
			System.out.println(bme.getMessage());
			StudyDesignLogger.getInstance().error(bme.getMessage());
			if(covarianceManager!=null)
			{
				try
				{covarianceManager.rollback();}				
				catch(BaseManagerException re)
				{covarianceSet = null;}				
			}
			covarianceSet = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {covarianceSet = null;}					
			}
			covarianceSet = null;
		}								
		return covarianceSet;
	}

	@Override
	public Set<Covariance> create(byte[] uuid, Set<Covariance> covarianceSet) 
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
			 * Remove existing Covariance for this object 
			 * ----------------------------------------------------*/
			if(uuidFlag && studyDesign.getCovariance()!=null)
				removeFrom(studyDesign);				
			if(uuidFlag)
			{	
				covarianceManager = new CovarianceManager();
            	covarianceManager.beginTransaction();
            		covarianceManager.saveOrUpdate(covarianceSet, true);
            	covarianceManager.commit();            	
            	/* ----------------------------------------------------
    			 * Set reference of Set<Covariance> Object to Study Design object 
    			 * ----------------------------------------------------*/
            	studyDesign.setCovariance(covarianceSet);            	
            	studyDesignManager = new StudyDesignManager();
            	studyDesignManager.beginTransaction();            		
            		studyDesign = studyDesignManager.saveOrUpdate(studyDesign, false);				
				studyDesignManager.commit();
			}		
		}
		catch (BaseManagerException bme)
		{
			System.out.println(bme.getMessage());
			StudyDesignLogger.getInstance().error(bme.getMessage());
			if(covarianceManager!=null)
			{
				try
				{covarianceManager.rollback();}				
				catch(BaseManagerException re)
				{covarianceSet = null;}				
			}
			covarianceSet = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {covarianceSet = null;}					
			}
			covarianceSet = null;
		}								
		return covarianceSet;
	}

	/**
     * Update a Covariance object for specified UUID.
     * 
     * @param byte[]
     * @return Set<Covariance>
     */
	@Put("json")
	public Set<Covariance> update(byte[] uuid,Set<Covariance> covarianceSet) 
	{				
		return create(uuid,covarianceSet);			
	}	

	/**
     * Delete a Covariance object for specified UUID.
     * 
     * @param byte[]
     * @return Set<Covariance>
     */
	@Delete("json")
	public Set<Covariance> remove(byte[] uuid) 
	{
		Set<Covariance> covarianceSet = null;
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
						covarianceSet = studyDesign.getCovariance();								
            	}				
			studyDesignManager.commit();
			/* ----------------------------------------------------
			 * Remove existing Covariance objects for this object 
			 * ----------------------------------------------------*/
			if(covarianceSet!=null)
			{
				covarianceManager = new CovarianceManager();
				covarianceManager.beginTransaction();
					covarianceSet = covarianceManager.delete(uuid,covarianceSet);
				covarianceManager.commit();
				/* ----------------------------------------------------
    			 * Set reference of Covariance Object to Study Design object 
    			 * ----------------------------------------------------*/
            	/*studyDesign.setBetaScaleList(null);            	
            	studyDesignManager = new StudyDesignManager();
            	studyDesignManager.beginTransaction();            		
            		studyDesign = studyDesignManager.saveOrUpdate(studyDesign, false);				
				studyDesignManager.commit();
				covarianceSet=studyDesign.getCovariance();*/
			}
		}
		catch (BaseManagerException bme)
		{
			System.out.println(bme.getMessage());
			StudyDesignLogger.getInstance().error(bme.getMessage());
			if(covarianceManager!=null)
			{
				try
				{covarianceManager.rollback();}				
				catch(BaseManagerException re)
				{covarianceSet = null;}				
			}
			covarianceSet = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {covarianceSet = null;}					
			}
			covarianceSet = null;
		}		
		return covarianceSet;
	}

	/**
     * Delete a Covariance object for specified Study Design.
     * 
     * @param StudyDesign
     * @return Set<Covariance>
     */
	@Delete("json")
	public Set<Covariance> removeFrom(StudyDesign studyDesign) 
	{
		boolean flag;	
		Set<Covariance> covarianceSet = null;	
        try
        {                    			
        	covarianceManager = new CovarianceManager();
        	covarianceManager.beginTransaction();
        		covarianceSet=covarianceManager.delete(studyDesign.getUuid(),studyDesign.getCovariance());
        	covarianceManager.commit();
        	/* ----------------------------------------------------
			 * Set reference of Covariance Object to Study Design object 
			 * ----------------------------------------------------*/
        	/*studyDesign.setConfidenceIntervalDescriptions(null);            	
        	studyDesignManager = new StudyDesignManager();
        	studyDesignManager.beginTransaction();            		
        		studyDesign = studyDesignManager.saveOrUpdate(studyDesign, false);				
			studyDesignManager.commit();    */        
        }
        catch (BaseManagerException bme)
        {
        	System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error("Failed to load Study Design information: " + bme.getMessage());
            if (studyDesignManager != null) try { studyDesignManager.rollback(); } catch (BaseManagerException e) {}
            if (covarianceManager != null) try { covarianceManager.rollback(); } catch (BaseManagerException e) {}
            covarianceSet = null;           
        }
        /*catch (StudyDesignException sde)
        {
            StudyDesignLogger.getInstance().error("Failed to load Study Design information: " + sde.getMessage());
            if (studyDesignManager != null) try { studyDesignManager.rollback(); } catch (BaseManagerException e) {}
            if (covarianceManager != null) try { covarianceManager.rollback(); } catch (BaseManagerException e) {}
            covarianceSet = null;            
        }*/
        return covarianceSet;
	}
}
