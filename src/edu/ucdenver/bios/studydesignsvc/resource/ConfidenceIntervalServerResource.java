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
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.ucdenver.bios.studydesignsvc.manager.ConfidenceIntervalManager;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.domain.ConfidenceIntervalDescription;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

/**
 * Resource class for handling requests for the complete study design object.
 * See the StudyDesignApplication class for URI mappings
 *
 * @author Uttara Sakhadeo
 */
public class ConfidenceIntervalServerResource extends ServerResource implements
        ConfidenceIntervalResource {

    /** The study design manager. */
    private StudyDesignManager studyDesignManager = null;

    /** The confidence interval manager. */
    private ConfidenceIntervalManager confidenceIntervalManager = null;

    /** The logger. */
    private Logger logger = StudyDesignLogger.getInstance();

    /** The study uuid. */
    private String studyUUID = null;

    /**
     * Retrieve a Confidence Interval object by the specified UUID.
     *
     * @param uuid
     *            : byte[]
     * @return ConfidenceIntervalDescription
     */
    @Get("application/json")
    public final ConfidenceIntervalDescription retrieve(final byte[] uuid) {
        if (uuid == null)  {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        boolean uuidFlag;
        StudyDesign studyDesign = null;
        ConfidenceIntervalDescription confidenceInterval = null;
        try {
            studyDesignManager = new StudyDesignManager();
            studyDesignManager.beginTransaction();
            uuidFlag = studyDesignManager.hasUUID(uuid);
            if (uuidFlag) {
                studyDesign = studyDesignManager.get(uuid);
            }
            studyDesignManager.commit();

            confidenceInterval = studyDesign
                    .getConfidenceIntervalDescriptions();
            /*
             * if(uuidFlag) { confidenceIntervalManager = new
             * ConfidenceIntervalManager();
             * confidenceIntervalManager.beginTransaction(); confidenceInterval
             * = confidenceIntervalManager.get(uuid);
             * confidenceIntervalManager.commit(); }
             */
        } catch (BaseManagerException bme) {
            StudyDesignLogger.getInstance().error(
                    "ConfidenceIntervalResource : " + bme.getMessage());
            if (confidenceIntervalManager != null) {
                try {
                    confidenceIntervalManager.rollback();
                } catch (BaseManagerException re) {
                    confidenceInterval = null;
                }
            }
        } catch (StudyDesignException sde) {
            StudyDesignLogger.getInstance().error(
                    "StudyDesignResource : " + sde.getMessage());
            if (studyDesignManager != null) {
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException re) {
                    confidenceInterval = null;
                }
            }
        }
        return confidenceInterval;
    }

    /**
     * Create a Confidence Interval object by the specified UUID.
     *
     * @param uuid
     *            : byte[]
     * @param confidenceInterval
     *            : ConfidenceIntervalDescription
     * @return ConfidenceIntervalDescription
     */
    @Post("application/json")
    public final ConfidenceIntervalDescription create(
            ConfidenceIntervalDescription confidenceInterval) {
        boolean uuidFlag;
        byte[] uuid = confidenceInterval.getUuid();
        StudyDesign studyDesign = null;
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        try {
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
            else {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                        "no study design UUID specified");
            }
            studyDesignManager.commit();
            /*
             * ---------------------------------------------------- Remove
             * existing Confidence Interval for this object
             * ----------------------------------------------------
             */
            if (uuidFlag
                && studyDesign.getConfidenceIntervalDescriptions() != null) {
                removeFrom(studyDesign);
            }
            /*
             * ---------------------------------------------------- Save new
             * Confidence Interval object
             * ----------------------------------------------------
             */
            if (uuidFlag) {
                confidenceIntervalManager = new ConfidenceIntervalManager();
                confidenceIntervalManager.beginTransaction();
                confidenceIntervalManager
                        .saveOrUpdate(confidenceInterval, true);
                confidenceIntervalManager.commit();
                /*
                 * ---------------------------------------------------- Set
                 * reference of Confidence Interval Object to Study Design
                 * object ----------------------------------------------------
                 */
                studyDesign
                        .setConfidenceIntervalDescriptions(confidenceInterval);
                studyDesignManager = new StudyDesignManager();
                studyDesignManager.beginTransaction();
                studyDesign = studyDesignManager.saveOrUpdate(studyDesign,
                        false);
                studyDesignManager.commit();
                confidenceInterval = studyDesign
                        .getConfidenceIntervalDescriptions();
            }
        } catch (BaseManagerException bme) {
            StudyDesignLogger.getInstance().error(
                    "ConfidenceIntervalResource : " + bme.getMessage());
            if (confidenceIntervalManager != null) {
                try {
                    confidenceIntervalManager.rollback();
                } catch (BaseManagerException re) {
                    confidenceInterval = null;
                }
            }
        } catch (StudyDesignException sde) {
            StudyDesignLogger.getInstance().error(
                    "StudyDesignResource : " + sde.getMessage());
            if (studyDesignManager != null) {
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException re) {
                    confidenceInterval = null;
                }
            }
        }
        return confidenceInterval;
    }

    /**
     * Update a Confidence Interval object by the specified UUID.
     *
     * @param uuid
     *            : byte[]
     * @param confidenceInterval
     *            : ConfidenceIntervalDescription
     * @return ConfidenceIntervalDescription
     */
    @Put("application/json")
    public final ConfidenceIntervalDescription update(
            ConfidenceIntervalDescription confidenceInterval) {
        boolean uuidFlag;
        byte[] uuid = confidenceInterval.getUuid();
        StudyDesign studyDesign = null;
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        try {
            /*
             * ---------------------------------------------------- Check for
             * existence of a UUID in Study Design object
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
             * ---------------------------------------------------- Remove
             * existing Confidence Interval for this object
             * ----------------------------------------------------
             */
            /*
             * if(uuidFlag &&
             * studyDesign.getConfidenceIntervalDescriptions()!=null)
             * remove(studyDesign);
             */
            /*
             * ---------------------------------------------------- Save new
             * Confidence Interval object
             * ----------------------------------------------------
             */
            ConfidenceIntervalDescription interval = studyDesign
                    .getConfidenceIntervalDescriptions();
            if (uuidFlag && interval != null) {
                confidenceInterval.setId(interval.getId());
                confidenceIntervalManager = new ConfidenceIntervalManager();
                confidenceIntervalManager.beginTransaction();
                confidenceIntervalManager.saveOrUpdate(confidenceInterval,
                        false);
                confidenceIntervalManager.commit();
                /*
                 * ---------------------------------------------------- Set
                 * reference of Confidence Interval Object to Study Design
                 * object ----------------------------------------------------
                 */
                /*
                 * studyDesign.setConfidenceIntervalDescriptions(confidenceInterval
                 * );
                 *
                 * studyDesignManager = new StudyDesignManager();
                 * studyDesignManager.beginTransaction(); studyDesign =
                 * studyDesignManager.saveOrUpdate(studyDesign, false);
                 * studyDesignManager.commit();
                 */
            } else {
                create(confidenceInterval);
            }
        } catch (BaseManagerException bme) {
            StudyDesignLogger.getInstance().error(
                    "ConfidenceIntervalResource : " + bme.getMessage());
            if (confidenceIntervalManager != null) {
                try {
                    confidenceIntervalManager.rollback();
                } catch (BaseManagerException re) {
                    confidenceInterval = null;
                }
            }
        } catch (StudyDesignException sde) {
            StudyDesignLogger.getInstance().error(
                    "StudyDesignResource : " + sde.getMessage());
            if (studyDesignManager != null) {
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException re) {
                    confidenceInterval = null;
                }
            }
        }
        return confidenceInterval;
    }

    /*
     * public ConfidenceIntervalDescription update(byte[]
     * uuid,ConfidenceIntervalDescription confidenceInterval) { boolean
     * uuidFlag; if(uuid==null) throw new
     * ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
     * "no study design UUID specified"); try { studyDesignManager = new
     * StudyDesignManager(); studyDesignManager.beginTransaction(); uuidFlag =
     * studyDesignManager.hasUUID(uuid); studyDesignManager.commit();
     *
     * if(uuidFlag) { confidenceIntervalManager = new
     * ConfidenceIntervalManager();
     * confidenceIntervalManager.beginTransaction();
     * confidenceIntervalManager.saveOrUpdate(confidenceInterval, false);
     * confidenceIntervalManager.commit(); } } catch (BaseManagerException bme)
     * { StudyDesignLogger.getInstance().error("ConfidenceIntervalResource : " +
     * bme.getMessage()); if(confidenceIntervalManager!=null) { try
     * {confidenceIntervalManager.rollback();} catch(BaseManagerException re)
     * {confidenceInterval = null;} } } catch(StudyDesignException sde) {
     * StudyDesignLogger.getInstance().error("StudyDesignResource : " +
     * sde.getMessage()); if(studyDesignManager!=null) { try
     * {studyDesignManager.rollback();} catch(BaseManagerException re)
     * {confidenceInterval = null;} } } return confidenceInterval; }
     */

    /**
     * Delete a Confidence Interval object by the specified UUID.
     *
     * @param uuid
     *            : byte[]
     * @return ConfidenceIntervalDescription
     */
    @Delete("application/json")
    public final ConfidenceIntervalDescription remove(final byte[] uuid) {
        boolean flag;
        ConfidenceIntervalDescription confidenceInterval = null;
        StudyDesign studyDesign = null;
        try {
            studyDesignManager = new StudyDesignManager();
            studyDesignManager.beginTransaction();
            flag = studyDesignManager.hasUUID(uuid);
            if (flag) {
                studyDesign = studyDesignManager.get(uuid);
            }
            studyDesignManager.commit();
            if (flag) {
                confidenceIntervalManager = new ConfidenceIntervalManager();
                confidenceIntervalManager.beginTransaction();
                confidenceInterval = confidenceIntervalManager.delete(uuid,
                        studyDesign.getConfidenceIntervalDescriptions());
                confidenceIntervalManager.commit();
                /*
                 * ---------------------------------------------------- Set
                 * reference of Confidence Interval Object to Study Design
                 * object ----------------------------------------------------
                 */
                /*
                 * studyDesign.setConfidenceIntervalDescriptions(null);
                 * studyDesignManager = new StudyDesignManager();
                 * studyDesignManager.beginTransaction(); studyDesign =
                 * studyDesignManager.saveOrUpdate(studyDesign, false);
                 * studyDesignManager.commit();
                 */
            } else {
                throw new StudyDesignException(
                        "No such studyUUID present in tableStudyDesign!!!");
            }
        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(
                    "Failed to load Study Design information: "
                            + bme.getMessage());
            if (studyDesignManager != null) {
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException e) {
                }
            }
            if (confidenceIntervalManager != null) {
                try {
                    confidenceIntervalManager.rollback();
                } catch (BaseManagerException e) {
                }
            }
            confidenceInterval = null;
        } catch (StudyDesignException sde) {
            StudyDesignLogger.getInstance().error(
                    "Failed to load Study Design information: "
                            + sde.getMessage());
            if (studyDesignManager != null) {
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException e) {
                }
            }
            if (confidenceIntervalManager != null) {
                try {
                    confidenceIntervalManager.rollback();
                } catch (BaseManagerException e) {
                }
            }
            confidenceInterval = null;
        }
        return confidenceInterval;
    }

    /**
     * Delete a Confidence Interval object for specified Study Design.
     * 
     * @param studyDesign
     *            the study design
     * @return ConfidenceIntervalDescription
     */
    public final ConfidenceIntervalDescription removeFrom(
            final StudyDesign studyDesign) {
        ConfidenceIntervalDescription confidenceInterval = null;
        try {
            confidenceIntervalManager = new ConfidenceIntervalManager();
            confidenceIntervalManager.beginTransaction();
            confidenceInterval = confidenceIntervalManager.delete(
                    studyDesign.getUuid(),
                    studyDesign.getConfidenceIntervalDescriptions());
            confidenceIntervalManager.commit();
            /*
             * ---------------------------------------------------- Set
             * reference of Confidence Interval Object to Study Design object
             * ----------------------------------------------------
             */
            /*
             * studyDesign.setConfidenceIntervalDescriptions(null);
             * studyDesignManager = new StudyDesignManager();
             * studyDesignManager.beginTransaction(); studyDesign =
             * studyDesignManager.saveOrUpdate(studyDesign, false);
             * studyDesignManager.commit(); confidenceInterval =
             * studyDesign.getConfidenceIntervalDescriptions();
             */
        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(
                    "Failed to load Study Design information: "
                            + bme.getMessage());
            if (studyDesignManager != null) {
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException e) {
                }
            }
            if (confidenceIntervalManager != null) {
                try {
                    confidenceIntervalManager.rollback();
                } catch (BaseManagerException e) {
                }
            }
            confidenceInterval = null;
        }
        /*
         * catch (StudyDesignException sde) {
         * StudyDesignLogger.getInstance().error
         * ("Failed to load Study Design information: " + sde.getMessage()); if
         * (studyDesignManager != null) try { studyDesignManager.rollback(); }
         * catch (BaseManagerException e) {} if (confidenceIntervalManager !=
         * null) try { confidenceIntervalManager.rollback(); } catch
         * (BaseManagerException e) {} confidenceInterval = null; }
         */
        return confidenceInterval;
    }

}
