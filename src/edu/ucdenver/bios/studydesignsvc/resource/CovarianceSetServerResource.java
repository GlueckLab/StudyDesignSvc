package edu.ucdenver.bios.studydesignsvc.resource;

import java.util.Set;

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
import edu.ucdenver.bios.studydesignsvc.manager.CovarianceSetManager;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.domain.Covariance;
import edu.ucdenver.bios.webservice.common.domain.CovarianceSet;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

/**
 * Generic Resource class for handling (PUT, POST, DELETE) requests for the
 * CovarianceSet domain object. See the StudyDesignApplication class for URI
 * mappings
 * 
 * @author Uttara Sakhadeo
 */
public class CovarianceSetServerResource extends ServerResource implements
        CovarianceSetResource {

    /**
     * Creates the CovarianceSet.
     * 
     * @param covarianceSet
     *            the covariance set
     * @return the covariance set
     */
    @Post("application/json")
    public final CovarianceSet create(CovarianceSet covarianceSet) {
        CovarianceSetManager covarianceSetManager = null;
        byte[] uuid = covarianceSet.getUuid();
        /*
         * Check : empty uuid.
         */
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        /*
         * Check : empty Covariance Set .
         */
        Set<Covariance> set = covarianceSet.getCovarianceSet();
        if (set == null || set.isEmpty()) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no Beta Scale specified");
        }
        try {
            /*
             * Save Covariance Set .
             */
            covarianceSetManager = new CovarianceSetManager();
            covarianceSetManager.beginTransaction();
            covarianceSet = covarianceSetManager.saveOrUpdate(covarianceSet,
                    true);
            covarianceSetManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (covarianceSetManager != null) {
                try {
                    covarianceSetManager.rollback();
                } catch (BaseManagerException re) {
                    covarianceSet = null;
                }
            }
            covarianceSet = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (covarianceSetManager != null) {
                try {
                    covarianceSetManager.rollback();
                } catch (BaseManagerException re) {
                    covarianceSet = null;
                }
            }
            covarianceSet = null;
        }
        return covarianceSet;
    }

    /**
     * Updates the CovarianceSet.
     * 
     * @param covarianceSet
     *            the covariance set
     * @return the covariance set
     */
    @Put("application/json")
    public final CovarianceSet update(final CovarianceSet covarianceSet) {
        return create(covarianceSet);
    }

    /**
     * Removes the CovarianceSet.
     * 
     * @param uuid
     *            the uuid
     * @return the covariance set
     */
    @Delete("application/json")
    public final CovarianceSet remove(final byte[] uuid) {
        CovarianceSetManager covarianceSetManager = null;
        CovarianceSet covarianceSet = null;
        /*
         * Check : empty uuid.
         */
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        try {
            /*
             * Delete Covariance Set .
             */
            covarianceSetManager = new CovarianceSetManager();
            covarianceSetManager.beginTransaction();
            covarianceSet = covarianceSetManager.delete(uuid);
            covarianceSetManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (covarianceSetManager != null) {
                try {
                    covarianceSetManager.rollback();
                } catch (BaseManagerException re) {
                    covarianceSet = null;
                }
            }
            covarianceSet = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (covarianceSetManager != null) {
                try {
                    covarianceSetManager.rollback();
                } catch (BaseManagerException re) {
                    covarianceSet = null;
                }
            }
            covarianceSet = null;
        }
        return covarianceSet;
    }

    /*
     * @Post("application/json") public CovarianceSet create(CovarianceSet
     * covarianceSet) { CovarianceManager covarianceManager = null;
     * StudyDesignManager studyDesignManager = null; boolean uuidFlag;
     * 
     * StudyDesign studyDesign = null; byte[] uuid = covarianceSet.getUuid(); if
     * (uuid == null) throw new
     * ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
     * "no study design UUID specified"); try {
     * 
     * ---------------------------------------------------- Check for existence
     * of a UUID in Study Design object
     * ----------------------------------------------------
     * 
     * studyDesignManager = new StudyDesignManager();
     * studyDesignManager.beginTransaction(); uuidFlag =
     * studyDesignManager.hasUUID(uuid); if (uuidFlag) { studyDesign =
     * studyDesignManager.get(uuid); } else { throw new
     * ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
     * "no study design UUID specified"); } studyDesignManager.commit();
     * 
     * ---------------------------------------------------- Remove existing
     * Covariance for this object
     * ----------------------------------------------------
     * 
     * if (uuidFlag && !studyDesign.getCovariance().isEmpty())
     * removeFrom(studyDesign); if (uuidFlag) { covarianceManager = new
     * CovarianceManager(); covarianceManager.beginTransaction();
     * covarianceManager.saveOrUpdate( covarianceSet.getCovarianceSet(), true);
     * covarianceManager.commit();
     * 
     * ---------------------------------------------------- Set reference of
     * CovarianceSet Object to Study Design object
     * ----------------------------------------------------
     * 
     * studyDesign.setCovariance(covarianceSet.getCovarianceSet());
     * studyDesignManager = new StudyDesignManager();
     * studyDesignManager.beginTransaction(); studyDesign =
     * studyDesignManager.saveOrUpdate(studyDesign, false);
     * studyDesignManager.commit(); } } catch (BaseManagerException bme) {
     * System.out.println(bme.getMessage());
     * StudyDesignLogger.getInstance().error(bme.getMessage()); if
     * (covarianceManager != null) { try { covarianceManager.rollback(); } catch
     * (BaseManagerException re) { covarianceSet = null; } } covarianceSet =
     * null; } catch (StudyDesignException sde) {
     * System.out.println(sde.getMessage());
     * StudyDesignLogger.getInstance().error(sde.getMessage()); if
     * (studyDesignManager != null) { try { studyDesignManager.rollback(); }
     * catch (BaseManagerException re) { covarianceSet = null; } } covarianceSet
     * = null; } return covarianceSet; }
     *//**
     * Delete a Covariance object for specified UUID.
     * 
     * @param byte[]
     * @return CovarianceSet
     */
    /*
     * @Delete("application/json") public CovarianceSet remove(byte[] uuid) {
     * CovarianceManager covarianceManager = null; StudyDesignManager
     * studyDesignManager = null; boolean uuidFlag;
     * 
     * CovarianceSet covarianceSet = null; StudyDesign studyDesign = null; if
     * (uuid == null) throw new
     * ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
     * "no study design UUID specified"); try {
     * 
     * ---------------------------------------------------- Check for existence
     * of a UUID in Study Design object
     * ----------------------------------------------------
     * 
     * studyDesignManager = new StudyDesignManager();
     * studyDesignManager.beginTransaction(); uuidFlag =
     * studyDesignManager.hasUUID(uuid); if (uuidFlag) { studyDesign =
     * studyDesignManager.get(uuid); if (studyDesign != null) covarianceSet =
     * new CovarianceSet(uuid, studyDesign.getCovariance()); }
     * studyDesignManager.commit();
     * 
     * ---------------------------------------------------- Remove existing
     * Covariance objects for this object
     * ----------------------------------------------------
     * 
     * if (!covarianceSet.getCovarianceSet().isEmpty()) { covarianceManager =
     * new CovarianceManager(); covarianceManager.beginTransaction();
     * covarianceSet = new CovarianceSet(uuid, covarianceManager.delete(uuid,
     * covarianceSet.getCovarianceSet())); covarianceManager.commit();
     * 
     * ---------------------------------------------------- Set reference of
     * Covariance Object to Study Design object
     * ----------------------------------------------------
     * 
     * 
     * studyDesign.setBetaScaleList(null); studyDesignManager = new
     * StudyDesignManager(); studyDesignManager.beginTransaction(); studyDesign
     * = studyDesignManager.saveOrUpdate(studyDesign, false);
     * studyDesignManager.commit(); covarianceSet=studyDesign.getCovariance();
     * 
     * } } catch (BaseManagerException bme) {
     * System.out.println(bme.getMessage());
     * StudyDesignLogger.getInstance().error(bme.getMessage()); if
     * (covarianceManager != null) { try { covarianceManager.rollback(); } catch
     * (BaseManagerException re) { covarianceSet = null; } } covarianceSet =
     * null; } catch (StudyDesignException sde) {
     * System.out.println(sde.getMessage());
     * StudyDesignLogger.getInstance().error(sde.getMessage()); if
     * (studyDesignManager != null) { try { studyDesignManager.rollback(); }
     * catch (BaseManagerException re) { covarianceSet = null; } } covarianceSet
     * = null; } return covarianceSet; }
     *//**
     * Delete a Covariance object for specified Study Design.
     * 
     * @param StudyDesign
     * @return CovarianceSet
     */
    /*
     * public CovarianceSet removeFrom(StudyDesign studyDesign) {
     * CovarianceManager covarianceManager = null; CovarianceSet covarianceSet =
     * null; try { covarianceManager = new CovarianceManager();
     * covarianceManager.beginTransaction(); covarianceSet = new
     * CovarianceSet(covarianceManager.delete( studyDesign.getUuid(),
     * studyDesign.getCovariance())); covarianceManager.commit();
     * 
     * ---------------------------------------------------- Set reference of
     * Covariance Object to Study Design object
     * ----------------------------------------------------
     * 
     * 
     * studyDesign.setConfidenceIntervalDescriptions(null); studyDesignManager =
     * new StudyDesignManager(); studyDesignManager.beginTransaction();
     * studyDesign = studyDesignManager.saveOrUpdate(studyDesign, false);
     * studyDesignManager.commit();
     * 
     * } catch (BaseManagerException bme) {
     * System.out.println(bme.getMessage());
     * StudyDesignLogger.getInstance().error(
     * "Failed to load Study Design information: " + bme.getMessage()); if
     * (covarianceManager != null) try { covarianceManager.rollback(); } catch
     * (BaseManagerException e) { } covarianceSet = null; }
     * 
     * catch (StudyDesignException sde) { StudyDesignLogger.getInstance().error
     * ("Failed to load Study Design information: " + sde.getMessage()); if
     * (studyDesignManager != null) try { studyDesignManager.rollback(); } catch
     * (BaseManagerException e) {} if (covarianceManager != null) try {
     * covarianceManager.rollback(); } catch (BaseManagerException e) {}
     * covarianceSet = null; }
     * 
     * return covarianceSet; }
     */
}
