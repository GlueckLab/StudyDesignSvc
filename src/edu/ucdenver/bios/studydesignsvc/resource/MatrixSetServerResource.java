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
import edu.ucdenver.bios.studydesignsvc.manager.MatrixManager;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrixSet;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

public class MatrixSetServerResource extends ServerResource
implements MatrixSetResource
{    
    /**
     * Retrieve a NamedMatrixSet object for specified UUID.
     * 
     * @param byte[]
     * @return NamedMatrixSet
     */
    @Get("application/json")
    public NamedMatrixSet retrieve(byte[] uuid) 
    {   
        StudyDesignManager studyDesignManager = null;
        boolean uuidFlag;
        
        NamedMatrixSet matrixSet = null;
        if(uuid==null)
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
                    "no study design UUID specified");      
        try
        {
             /*----------------------------------------------------
             * Check for existence of a UUID in Study Design object 
             * ----------------------------------------------------*/
            studyDesignManager = new StudyDesignManager();          
            studyDesignManager.beginTransaction();                              
                uuidFlag = studyDesignManager.hasUUID(uuid);
                if(uuidFlag)
                {       
                    StudyDesign studyDesign = studyDesignManager.get(uuid);
                    matrixSet = new NamedMatrixSet(uuid,studyDesign.getMatrixSet());                                                                
                }               
            studyDesignManager.commit();                    
        }
        catch (BaseManagerException bme)
        {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if(studyDesignManager!=null)
            {
                try {studyDesignManager.rollback();}
                catch(BaseManagerException re) {matrixSet = null;}                  
            }
            matrixSet = null;            
        }   
        catch(StudyDesignException sde)
        {
            System.out.println(sde.getMessage());
            StudyDesignLogger.getInstance().error(sde.getMessage());
            if(studyDesignManager!=null)
            {
                try {studyDesignManager.rollback();}
                catch(BaseManagerException re) {matrixSet = null;}                  
            }
            matrixSet = null;
        }                               
        return matrixSet;
    }
    
    /**
     * Create a NamedMatrixSet object for specified UUID.
     * 
     * @param byte[]
     * @return NamedMatrixSet
     */
    @Post("application/json")
    public NamedMatrixSet create(NamedMatrixSet matrixSet) 
    {      
        MatrixManager matrixManager = null; 
        StudyDesignManager studyDesignManager = null;
        boolean uuidFlag;
        
        StudyDesign studyDesign = null;
        byte[] uuid = matrixSet.getUuid();
        if(uuid==null)
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
                    "no study design UUID specified");
        try
        {                   
             /*----------------------------------------------------
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
                        "no study design for specified UUID is stored");}               
            studyDesignManager.commit();
             /*----------------------------------------------------
             * Remove existing NamedMatrix for this object 
             * ----------------------------------------------------*/           
            if(uuidFlag && studyDesign.getMatrixSet()!=null)
                removeFrom(studyDesign);
            if(uuidFlag)
            {
                matrixManager = new MatrixManager();
                matrixManager.beginTransaction();
                    matrixManager.saveOrUpdate(matrixSet.getMatrixSet(),true);
                matrixManager.commit();
                /*----------------------------------------------------
                 * Save new NamedMatrix List object 
                 * ----------------------------------------------------*/
                studyDesign.setMatrixSet(matrixSet.getMatrixSet());
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
            if(matrixManager!=null)
            {
                try
                {matrixManager.rollback();}             
                catch(BaseManagerException re)
                {matrixSet = null;}             
            }
            matrixSet = null;
        }   
        catch(StudyDesignException sde)
        {
            System.out.println(sde.getMessage());
            StudyDesignLogger.getInstance().error(sde.getMessage());
            if(studyDesignManager!=null)
            {
                try {studyDesignManager.rollback();}
                catch(BaseManagerException re) {matrixSet = null;}                  
            }
            matrixSet = null;
        }                               
        return matrixSet;
    }
    
    /**
     * Update a NamedMatrix object for specified UUID.
     * 
     * @param byte[]
     * @return NamedMatrixSet
     */
    @Put("application/json")
    public NamedMatrixSet update(NamedMatrixSet matrixSet) 
    {               
        return create(matrixSet);           
    }   
    
    /**
     * Delete a NamedMatrix object for specified UUID.
     * 
     * @param byte[]
     * @return NamedMatrixSet
     */
    @Delete("application/json")
    public NamedMatrixSet remove(byte[] uuid) 
    {
        MatrixManager matrixManager = null; 
        StudyDesignManager studyDesignManager = null;
        boolean uuidFlag;
        
        NamedMatrixSet matrixSet = null;
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
                        matrixSet = new NamedMatrixSet(uuid,studyDesign.getMatrixSet());                                
                }               
            studyDesignManager.commit();
            /* ----------------------------------------------------
             * Remove existing NamedMatrix objects for this object 
             * ----------------------------------------------------*/
            if(matrixSet!=null)
            {
                matrixManager = new MatrixManager();
                matrixManager.beginTransaction();
                    matrixSet = new NamedMatrixSet(uuid,matrixManager.delete(uuid,matrixSet.getMatrixSet()));
                matrixManager.commit();
                /* ----------------------------------------------------
                 * Set reference of NamedMatrix Object to Study Design object 
                 * ----------------------------------------------------*/
                /*studyDesign.setBetaScaleList(null);               
                studyDesignManager = new StudyDesignManager();
                studyDesignManager.beginTransaction();                  
                    studyDesign = studyDesignManager.saveOrUpdate(studyDesign, false);              
                studyDesignManager.commit();
                matrixSet=studyDesign.getMatrixSet();*/
            }
        }
        catch (BaseManagerException bme)
        {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if(matrixManager!=null)
            {
                try
                {matrixManager.rollback();}             
                catch(BaseManagerException re)
                {matrixSet = null;}             
            }
            matrixSet = null;
        }   
        catch(StudyDesignException sde)
        {
            System.out.println(sde.getMessage());
            StudyDesignLogger.getInstance().error(sde.getMessage());
            if(studyDesignManager!=null)
            {
                try {studyDesignManager.rollback();}
                catch(BaseManagerException re) {matrixSet = null;}                  
            }
            matrixSet = null;
        }       
        return matrixSet;
    }
    
    /**
     * Delete a NamedMatrix object for specified Study Design.
     * 
     * @param StudyDesign
     * @return NamedMatrixSet
     */
    public NamedMatrixSet removeFrom(StudyDesign studyDesign) 
    {
        MatrixManager matrixManager = null; 
        NamedMatrixSet matrixSet = null;    
        try
        {                               
            matrixManager = new MatrixManager();
            matrixManager.beginTransaction();
                matrixSet = new NamedMatrixSet(studyDesign.getUuid(),
                    matrixManager.delete(studyDesign.getUuid(),studyDesign.getMatrixSet()));
            matrixManager.commit();               
        }
        catch (BaseManagerException bme)
        {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error("Failed to load Study Design information: " + bme.getMessage());            
            if (matrixManager != null) try { matrixManager.rollback(); } catch (BaseManagerException e) {}
            matrixSet = null;           
        }       
        return matrixSet;
    }

}
