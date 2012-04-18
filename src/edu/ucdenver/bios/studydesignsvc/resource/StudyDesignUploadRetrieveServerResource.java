package edu.ucdenver.bios.studydesignsvc.resource;

import java.util.List;

import org.apache.log4j.Logger;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
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
    @Post
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
            if (uuidFlag) {
                new StudyDesignServerResource().remove(uuid);
            }
            
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
    
    @Get
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
