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

import java.util.regex.Pattern;

import org.restlet.data.Status;
import org.restlet.resource.Delete;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.manager.CovarianceManager;
import edu.ucdenver.bios.webservice.common.domain.Covariance;
import edu.ucdenver.bios.webservice.common.domain.UuidCovariance;
import edu.ucdenver.bios.webservice.common.domain.UuidCovarianceName;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;

// TODO: Auto-generated Javadoc
/**
 * Server Resource class for handling (PUT, POST, DELETE) requests for the
 * Covariance object. See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class CovarianceServerResource extends ServerResource implements
        CovarianceResource {

    /**
     * Creates the Covariance.
     * 
     * @param uuidCovariance
     *            the uuid covariance
     * @return the covariance
     */
    @Post("application/json")
    public final Covariance create(UuidCovariance uuidCovariance) {
        CovarianceManager covarianceManager = null;
        byte[] uuid = uuidCovariance.getUuid();
        /*
         * Check : empty uuid.
         */
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        /*
         * Validate Uuid.
         */
        boolean uuidFlag = false;
        try {
            uuidFlag = Pattern.matches("[0-9a-fA-F]{32}",
                    UUIDUtils.bytesToHex(uuid));
        } catch (Exception e) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "invalid UUID specified");
        }
        if (!uuidFlag) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "invalid UUID specified");
        }
        
        /*
         * Check : empty Covariance.
         */
        Covariance covariance = uuidCovariance.getCovariance();
        if (covariance == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no Beta Scale specified");
        }
        try {
            /*
             * Save Covariance Set .
             */
            covarianceManager = new CovarianceManager();
            covarianceManager.beginTransaction();
            covariance = covarianceManager.saveOrUpdate(uuidCovariance, true);
            covarianceManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (covarianceManager != null) {
                try {
                    covarianceManager.rollback();
                } catch (BaseManagerException re) {
                    covariance = null;
                }
            }
            covariance = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (covarianceManager != null) {
                try {
                    covarianceManager.rollback();
                } catch (BaseManagerException re) {
                    covariance = null;
                }
            }
            covariance = null;
        }
        return covariance;
    }

    /**
     * Update Covariance.
     * 
     * @param uuidCovariance
     *            the uuid covariance
     * @return the covariance
     */
    @Put("application/json")
    public final Covariance update(final UuidCovariance uuidCovariance) {
        return create(uuidCovariance);
    }

    /**
     * Delete the Covariance.
     * 
     * @param uuidCovarianceName
     *            the uuid covariance name
     * @return the covariance
     */
    @Delete("application/json")
    public final Covariance remove(final UuidCovarianceName uuidCovarianceName) {
        CovarianceManager covarianceManager = null;
        Covariance covariance = null;
        byte[] uuid = uuidCovarianceName.getUuid();
        String name = uuidCovarianceName.getCovarianceName();
        /*
         * Check : empty uuid.
         */
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        /*
         * Check : empty name.
         */
        if (name == null || name.isEmpty()) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "not valid Covariance Name specified");
        }
        /*
         * Validate Uuid.
         */
        boolean uuidFlag = false;
        try {
            uuidFlag = Pattern.matches("[0-9a-fA-F]{32}",
                    UUIDUtils.bytesToHex(uuid));
        } catch (Exception e) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "invalid UUID specified");
        }
        if (!uuidFlag) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "invalid UUID specified");
        }
        

        try {
            /*
             * Delete Covariance Set .
             */
            covarianceManager = new CovarianceManager();
            covarianceManager.beginTransaction();
            covariance = covarianceManager.delete(uuidCovarianceName);
            covarianceManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (covarianceManager != null) {
                try {
                    covarianceManager.rollback();
                } catch (BaseManagerException re) {
                    covariance = null;
                }
            }
            covariance = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (covarianceManager != null) {
                try {
                    covarianceManager.rollback();
                } catch (BaseManagerException re) {
                    covariance = null;
                }
            }
            covariance = null;
        }
        return covariance;
    }

    /*
     * @Get("application/json") public Covariance retrieve(UuidCovarianceName
     * uuidName) { StudyDesignManager studyDesignManager = null; boolean
     * uuidFlag; byte[] uuid = uuidName.getUuid(); String name =
     * uuidName.getCovarianceName(); Covariance covariance = null; if (uuid ==
     * null) throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
     * "no study design UUID specified"); try { /*
     * ---------------------------------------------------- Check for existence
     * of a UUID in Study Design object
     * ----------------------------------------------------
     * 
     * studyDesignManager = new StudyDesignManager();
     * studyDesignManager.beginTransaction(); uuidFlag =
     * studyDesignManager.hasUUID(uuid); if (uuidFlag) { StudyDesign studyDesign
     * = studyDesignManager.get(uuid); if (studyDesign != null) covariance =
     * studyDesign.getCovarianceFromSet(name); } studyDesignManager.commit(); }
     * catch (BaseManagerException bme) { System.out.println(bme.getMessage());
     * StudyDesignLogger.getInstance().error(bme.getMessage()); if
     * (studyDesignManager != null) { try { studyDesignManager.rollback(); }
     * catch (BaseManagerException re) { covariance = null; } } covariance =
     * null; } catch (StudyDesignException sde) {
     * System.out.println(sde.getMessage());
     * StudyDesignLogger.getInstance().error(sde.getMessage()); if
     * (studyDesignManager != null) { try { studyDesignManager.rollback(); }
     * catch (BaseManagerException re) { covariance = null; } } covariance =
     * null; } return covariance; }
     * 
     * 
     * @Post("application/json") public Covariance create(UuidCovariance
     * uuidCovariance) { CovarianceManager covarianceManager = null;
     * StudyDesignManager studyDesignManager = null; boolean uuidFlag; byte[]
     * uuid = uuidCovariance.getUuid(); Covariance covariance =
     * uuidCovariance.getCovariance(); String name = covariance.getName();
     * StudyDesign studyDesign = null; if (uuid == null) throw new
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
     * boolean flag = studyDesign.hasCovariance(name); if (uuidFlag &&
     * studyDesign.getCovariance() != null &&
     * !studyDesign.getCovariance().isEmpty() && flag) { removeFrom(studyDesign,
     * name); } if (uuidFlag && studyDesign.getCovariance() != null) {
     * covarianceManager = new CovarianceManager();
     * covarianceManager.beginTransaction();
     * covarianceManager.saveOrUpdate(covariance, true);
     * covarianceManager.commit();
     * 
     * ---------------------------------------------------- Set reference of
     * Covariance Object to Study Design object
     * ----------------------------------------------------
     * 
     * studyDesign.addCovariance(covariance); studyDesignManager = new
     * StudyDesignManager(); studyDesignManager.beginTransaction(); studyDesign
     * = studyDesignManager.saveOrUpdate(studyDesign, false);
     * studyDesignManager.commit(); } } catch (BaseManagerException bme) {
     * System.out.println(bme.getMessage());
     * StudyDesignLogger.getInstance().error(bme.getMessage()); if
     * (covarianceManager != null) { try { covarianceManager.rollback(); } catch
     * (BaseManagerException re) { covariance = null; } } covariance = null; }
     * catch (StudyDesignException sde) { System.out.println(sde.getMessage());
     * StudyDesignLogger.getInstance().error(sde.getMessage()); if
     * (studyDesignManager != null) { try { studyDesignManager.rollback(); }
     * catch (BaseManagerException re) { covariance = null; } } covariance =
     * null; } return covariance; }
     * 
     * 
     * @Put("application/json") public Covariance update(UuidCovariance
     * uuidCovariance) { return create(uuidCovariance); }
     * 
     * 
     * @Delete("application/json") public Covariance remove(UuidCovarianceName
     * uuidName) { CovarianceManager covarianceManager = null;
     * StudyDesignManager studyDesignManager = null; boolean uuidFlag; byte[]
     * uuid = uuidName.getUuid(); String name = uuidName.getCovarianceName();
     * Covariance covariance = null; Covariance covariance = null; StudyDesign
     * studyDesign = null; if (uuid == null) throw new
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
     * studyDesignManager.get(uuid); if (studyDesign != null) covariance = new
     * Covariance(uuid, studyDesign.getCovariance()); }
     * studyDesignManager.commit();
     * 
     * ---------------------------------------------------- Remove existing
     * Covariance objects for this object
     * ----------------------------------------------------
     * 
     * if (covariance != null && studyDesign.hasCovariance(name)) {
     * covarianceManager = new CovarianceManager();
     * covarianceManager.beginTransaction(); covariance =
     * covarianceManager.delete(uuid, studyDesign.getCovarianceFromSet(name));
     * covarianceManager.commit();
     * 
     * ---------------------------------------------------- Set reference of
     * Covariance Object to Study Design object
     * ----------------------------------------------------
     * 
     * 
     * studyDesign.setBetaScaleList(null); studyDesignManager = new
     * StudyDesignManager(); studyDesignManager.beginTransaction(); studyDesign
     * = studyDesignManager.saveOrUpdate(studyDesign, false);
     * studyDesignManager.commit(); covariance=studyDesign.getCovariance();
     * 
     * } } catch (BaseManagerException bme) {
     * System.out.println(bme.getMessage());
     * StudyDesignLogger.getInstance().error(bme.getMessage()); if
     * (covarianceManager != null) { try { covarianceManager.rollback(); } catch
     * (BaseManagerException re) { covariance = null; } } covariance = null; }
     * catch (StudyDesignException sde) { System.out.println(sde.getMessage());
     * StudyDesignLogger.getInstance().error(sde.getMessage()); if
     * (studyDesignManager != null) { try { studyDesignManager.rollback(); }
     * catch (BaseManagerException re) { covariance = null; } } covariance =
     * null; } return covariance; }
     * 
     * 
     * public Covariance removeFrom(StudyDesign studyDesign, String name) {
     * CovarianceManager covarianceManager = null; Covariance covariance = null;
     * try { covarianceManager = new CovarianceManager();
     * covarianceManager.beginTransaction(); covariance =
     * covarianceManager.delete(studyDesign.getUuid(),
     * studyDesign.getCovarianceFromSet(name)); covarianceManager.commit(); }
     * catch (BaseManagerException bme) { System.out.println(bme.getMessage());
     * StudyDesignLogger.getInstance().error(
     * "Failed to load Study Design information: " + bme.getMessage()); if
     * (covarianceManager != null) try { covarianceManager.rollback(); } catch
     * (BaseManagerException e) { } covariance = null; } return covariance; }
     */
}
