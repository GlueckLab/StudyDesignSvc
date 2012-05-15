/*
 * Study Design Service for the GLIMMPSE Software System.
 * This service stores study design definitions for users
 * of the GLIMMSE interface. Service contain all information
 * related to a power or sample size calculation.
 * The Study Design Service simplifies communication between
 * different screens in the user interface.
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
 * Foundation, Inc. 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 */
package edu.ucdenver.bios.studydesignsvc.resource;

import org.apache.log4j.Logger;
import org.restlet.data.Status;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.domain.StudyDesignList;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

public class StudyDesignUploadRetrieveServerResource extends ServerResource 
implements StudyDesignUploadRetrieveResource{
    
    static Logger logger = StudyDesignLogger.getInstance(); 
    StudyDesignManager studyDesignManager = null;
           
    /*
     * Called while uploading a Study Design.
     */
    @Post("application/json")
    public StudyDesign upload(StudyDesign studyDesign)
    {
        StudyDesignManager studyDesignManager = null;
        boolean uuidFlag = false;
        byte[] uuid = studyDesign.getUuid(); 
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }       
        try
        {       
            /*
             * ----------------------------------------------------
             * Check for existence of a UUID in Study Design object
             * ----------------------------------------------------
             */
            studyDesignManager = new StudyDesignManager();
            studyDesignManager.beginTransaction();
            uuidFlag = studyDesignManager.hasUUID(uuid);
            if (uuidFlag) {
                studyDesign = studyDesignManager.get(uuid);           
            }
            studyDesignManager.commit();
            /*
             * ---------------------------------------------------- 
             * Remove existing Study Design for this object
             * ----------------------------------------------------
             */            
            /*if (uuidFlag) {
                new StudyDesignServerResource().remove(uuid);
            }*/
            
                studyDesignManager = new StudyDesignManager();
                studyDesignManager.beginTransaction();      
                    studyDesign = studyDesignManager.saveOrUpdate(studyDesign,true);
                studyDesignManager.commit();
            
        }
        catch(BaseManagerException bme)
        {
            StudyDesignLogger.getInstance().error("StudyDesignResource : " + bme.getMessage());
            if(studyDesignManager!=null)
            {
                try {studyDesignManager.rollback();}
                catch(BaseManagerException re) {studyDesign = null;}                    
            }
        }   
        catch(StudyDesignException sde)
        {
            StudyDesignLogger.getInstance().error("StudyDesignResource : " + sde.getMessage());
            if(studyDesignManager!=null)
            {
                try {studyDesignManager.rollback();}
                catch(BaseManagerException re) {studyDesign = null;}                    
            }
        }                       
        return studyDesign;
    }
    
    @Put("application/json")
    public StudyDesignList retrieve()
    {
        studyDesignManager = null;
        StudyDesignList studyDesignList = null; 
        
        try
        {           
            studyDesignManager = new StudyDesignManager();
                studyDesignManager.beginTransaction();      
            studyDesignList = new StudyDesignList(studyDesignManager.getStudyDesigns());            
            
            studyDesignManager.commit();
        }
        catch(BaseManagerException bme)
        {
            StudyDesignLogger.getInstance().error("StudyDesignResource : " + bme.getMessage());
            if(studyDesignManager!=null)
            {
                try {studyDesignManager.rollback();}
                catch(BaseManagerException re) {studyDesignList = null;}                    
            }
        }   
        catch(StudyDesignException sde)
        {
            StudyDesignLogger.getInstance().error("StudyDesignResource : " + sde.getMessage());
            if(studyDesignManager!=null)
            {
                try {studyDesignManager.rollback();}
                catch(BaseManagerException re) {studyDesignList = null;}                    
            }
        }                       
        return studyDesignList;         

    }
}
