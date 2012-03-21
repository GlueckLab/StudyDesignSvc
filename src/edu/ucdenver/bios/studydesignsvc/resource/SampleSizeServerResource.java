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

import java.util.List;

import org.restlet.data.Status;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.ucdenver.bios.studydesignsvc.manager.SampleSizeManager;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.domain.SampleSize;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

/**
 * Server Resource class for handling requests for the SampleSize object. 
 * See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class SampleSizeServerResource extends ServerResource 
implements SampleSizeResource
{
    SampleSizeManager sampleSizeManager = null; 
    StudyDesignManager studyDesignManager = null;
    boolean uuidFlag;

    /**
     * Retrieve a SampleSize object for specified UUID.
     * 
     * @param byte[]
     * @return List<SampleSize>
     */
    @Get("json")
    public List<SampleSize> retrieve(byte[] uuid) 
    {
        List<SampleSize> sampleSizeList = null;
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
                        sampleSizeList = studyDesign.getSampleSizeList();                   
                }               
            studyDesignManager.commit();                    
        }
        catch (BaseManagerException bme)
        {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if(sampleSizeManager!=null)
            {
                try
                {sampleSizeManager.rollback();}               
                catch(BaseManagerException re)
                {sampleSizeList = null;}              
            }
            sampleSizeList = null;
        }   
        catch(StudyDesignException sde)
        {
            System.out.println(sde.getMessage());
            StudyDesignLogger.getInstance().error(sde.getMessage());
            if(studyDesignManager!=null)
            {
                try {studyDesignManager.rollback();}
                catch(BaseManagerException re) {sampleSizeList = null;}                   
            }
            sampleSizeList = null;
        }                               
        return sampleSizeList;
    }

    /**
     * Create a SampleSize object for specified UUID.
     * 
     * @param byte[]
     * @param List<SampleSize>
     * @return List<SampleSize>
     */
    @Post("json")
    public List<SampleSize> create(byte[] uuid,List<SampleSize> sampleSizeList) 
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
                {
                    studyDesign = studyDesignManager.get(uuid);                 
                }                                                                                                                               
                else
                {throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
                        "no study design UUID specified");}
            studyDesignManager.commit();
            /* ----------------------------------------------------
             * Remove existing SampleSize for this object 
             * ----------------------------------------------------*/           
            if(uuidFlag && studyDesign.getSampleSizeList()!=null)
                removeFrom(studyDesign);    
            /* ----------------------------------------------------
             * Save new SampleSize List object 
             * ----------------------------------------------------*/
            if(uuidFlag)
            {
                studyDesign.setSampleSizeList(sampleSizeList);
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
            if(sampleSizeManager!=null)
            {
                try
                {sampleSizeManager.rollback();}               
                catch(BaseManagerException re)
                {sampleSizeList = null;}              
            }
            sampleSizeList = null;
        }   
        catch(StudyDesignException sde)
        {
            System.out.println(sde.getMessage());
            StudyDesignLogger.getInstance().error(sde.getMessage());
            if(studyDesignManager!=null)
            {
                try {studyDesignManager.rollback();}
                catch(BaseManagerException re) {sampleSizeList = null;}                   
            }
            sampleSizeList = null;
        }                               
        return sampleSizeList;
    }

    /**
     * Update a SampleSize object for specified UUID.
     * 
     * @param byte[]
     * @param List<SampleSize>
     * @return List<SampleSize>
     */
    @Put("json")
    public List<SampleSize> update(byte[] uuid,List<SampleSize> sampleSizeList) 
    {
        return create(uuid,sampleSizeList);
    }

    /**
     * Delete a SampleSize object for specified UUID.
     * 
     * @param byte[]
     * @return List<SampleSize>
     */
    @Delete("json")
    public List<SampleSize> remove(byte[] uuid) 
    {
        List<SampleSize> sampleSizeList = null;
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
                        sampleSizeList = studyDesign.getSampleSizeList();
                    if(sampleSizeList.isEmpty())
                        throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
                                "no SampleSize is specified");                    
                }               
            studyDesignManager.commit();
            /* ----------------------------------------------------
             * Remove existing SampleSize objects for this object 
             * ----------------------------------------------------*/
            if(studyDesign.getQuantileList()!=null)
            {
                sampleSizeManager = new SampleSizeManager();
                sampleSizeManager.beginTransaction();
                    sampleSizeList = sampleSizeManager.delete(uuid,sampleSizeList);
                sampleSizeManager.commit();
            }
        }
        catch (BaseManagerException bme)
        {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if(sampleSizeManager!=null)
            {
                try
                {sampleSizeManager.rollback();}               
                catch(BaseManagerException re)
                {sampleSizeList = null;}             
            }
            sampleSizeList = null;
        }   
        catch(StudyDesignException sde)
        {
            System.out.println(sde.getMessage());
            StudyDesignLogger.getInstance().error(sde.getMessage());
            if(studyDesignManager!=null)
            {
                try {studyDesignManager.rollback();}
                catch(BaseManagerException re) {sampleSizeList = null;}                   
            }
            sampleSizeList = null;
        }       
        return sampleSizeList;
    }

    /**
     * Delete a SampleSize object for specified Study Design.
     * 
     * @param StudyDesign
     * @return List<SampleSize>
     */
    @Override
    @Delete("json")
    public List<SampleSize> removeFrom(StudyDesign studyDesign) 
    {
        List<SampleSize> sampleSizeList = null; 
        try
        {                               
            sampleSizeManager = new SampleSizeManager();
            sampleSizeManager.beginTransaction();
                sampleSizeList=sampleSizeManager.delete(studyDesign.getUuid(),studyDesign.getSampleSizeList());
            sampleSizeManager.commit();                  
        }
        catch (BaseManagerException bme)
        {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error("Failed to load Study Design information: " + bme.getMessage());
            if (studyDesignManager != null) try { studyDesignManager.rollback(); } catch (BaseManagerException e) {}
            if (sampleSizeManager != null) try { sampleSizeManager.rollback(); } catch (BaseManagerException e) {}
            sampleSizeList = null;           
        }
       return sampleSizeList;
    }

}
