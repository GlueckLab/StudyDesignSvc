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
import edu.ucdenver.bios.studydesignsvc.manager.CovarianceManager;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.domain.CovarianceSet;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

public class CovarianceSetServerResource extends ServerResource
implements CovarianceSetResource
{
    
    /**
     * Retrieve a Covariance object for specified UUID.
     * 
     * @param byte[]
     * @return CovarianceSet
     */
    @Get("application/json")
    public CovarianceSet retrieve(byte[] uuid) 
    {
        StudyDesignManager studyDesignManager = null;
        boolean uuidFlag;
        
        CovarianceSet covarianceSet = null;
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
                        covarianceSet = new CovarianceSet(uuid,
                                studyDesign.getCovariance());                   
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
                catch(BaseManagerException re) {covarianceSet = null;}                  
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

    @Post("application/json")
    public CovarianceSet create(CovarianceSet covarianceSet) 
    {
        CovarianceManager covarianceManager = null;
        StudyDesignManager studyDesignManager = null;
        boolean uuidFlag;
        
        StudyDesign studyDesign =null;
        byte[] uuid = covarianceSet.getUuid();
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
            if(uuidFlag && !studyDesign.getCovariance().isEmpty())
                removeFrom(studyDesign);                
            if(uuidFlag)
            {   
                covarianceManager = new CovarianceManager();
                covarianceManager.beginTransaction();
                    covarianceManager.saveOrUpdate(covarianceSet.getCovarianceSet(), true);
                covarianceManager.commit();             
                /* ----------------------------------------------------
                 * Set reference of CovarianceSet Object to Study Design object 
                 * ----------------------------------------------------*/
                studyDesign.setCovariance(covarianceSet.getCovarianceSet());                
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
     * @return CovarianceSet
     */
    @Put("application/json")
    public CovarianceSet update(CovarianceSet covarianceSet) 
    {               
        return create(covarianceSet);           
    }   

    /**
     * Delete a Covariance object for specified UUID.
     * 
     * @param byte[]
     * @return CovarianceSet
     */
    @Delete("application/json")
    public CovarianceSet remove(byte[] uuid) 
    {
        CovarianceManager covarianceManager = null;
        StudyDesignManager studyDesignManager = null;
        boolean uuidFlag;
        
        CovarianceSet covarianceSet = null;
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
                        covarianceSet = new CovarianceSet(uuid,studyDesign.getCovariance());                                
                }               
            studyDesignManager.commit();
            /* ----------------------------------------------------
             * Remove existing Covariance objects for this object 
             * ----------------------------------------------------*/
            if(!covarianceSet.getCovarianceSet().isEmpty())
            {
                covarianceManager = new CovarianceManager();
                covarianceManager.beginTransaction();
                    covarianceSet = new CovarianceSet(uuid,
                            covarianceManager.delete(uuid,covarianceSet.getCovarianceSet()));
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
     * @return CovarianceSet
     */
    public CovarianceSet removeFrom(StudyDesign studyDesign) 
    {
        CovarianceManager covarianceManager = null;
        CovarianceSet covarianceSet = null; 
        try
        {                               
            covarianceManager = new CovarianceManager();
            covarianceManager.beginTransaction();
                covarianceSet=new CovarianceSet(covarianceManager.delete(studyDesign.getUuid(),studyDesign.getCovariance()));
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
