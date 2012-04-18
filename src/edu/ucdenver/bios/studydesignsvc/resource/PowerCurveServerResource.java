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
import edu.ucdenver.bios.studydesignsvc.manager.PowerCurveManager;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.domain.PowerCurveDescription;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;


/**
 * Concrete class which implements methods of Power Curve Resource interface.
 * See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class PowerCurveServerResource extends ServerResource implements
        PowerCurveResource {

    /** The study design manager. */
    StudyDesignManager studyDesignManager = null;

    /** The power curve manager. */
    PowerCurveManager powerCurveManager = null;

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.ucdenver.bios.studydesignsvc.resource.PowerCurveResource#retrieve
     * (byte[])
     */
    @Get("application/json")
    public PowerCurveDescription retrieve(byte[] uuid) {
        boolean uuidFlag;
        PowerCurveDescription powerCurveDescription = null;
        StudyDesign studyDesign = null;
        try {
            if (uuid == null)
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                        "no study design UUID specified");

            studyDesignManager = new StudyDesignManager();
            studyDesignManager.beginTransaction();
            uuidFlag = studyDesignManager.hasUUID(uuid);
            if (uuidFlag) {
                studyDesign = studyDesignManager.get(uuid);
                if (studyDesign != null)
                    powerCurveDescription = studyDesign
                            .getPowerCurveDescriptions();
            }
            studyDesignManager.commit();
        } catch (BaseManagerException bme) {
            StudyDesignLogger.getInstance().error(
                    "ConfidenceIntervalResource : " + bme.getMessage());
            if (powerCurveManager != null) {
                try {
                    powerCurveManager.rollback();
                } catch (BaseManagerException re) {
                    powerCurveDescription = null;
                }
            }
        } catch (StudyDesignException sde) {
            StudyDesignLogger.getInstance().error(
                    "PowerCurveResource : " + sde.getMessage());
            if (studyDesignManager != null) {
                try {
                    powerCurveManager.rollback();
                } catch (BaseManagerException re) {
                    powerCurveDescription = null;
                }
            }
        }
        return powerCurveDescription;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.ucdenver.bios.studydesignsvc.resource.PowerCurveResource#create(byte
     * [], edu.ucdenver.bios.webservice.common.domain.PowerCurveDescription)
     */
    @Post("application/json")
    public PowerCurveDescription create(
            PowerCurveDescription powerCurveDescription) {
        boolean uuidFlag;
        StudyDesign studyDesign = null;
        byte[] uuid = powerCurveDescription.getUuid();
        if (uuid == null)
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        try {
            /*
             * ---------------------------------------------------- Check for
             * existence of a UUID in Study Design object
             * ----------------------------------------------------
             */
            studyDesignManager = new StudyDesignManager();
            studyDesignManager.beginTransaction();
            uuidFlag = studyDesignManager.hasUUID(uuid);
            if (uuidFlag)
                studyDesign = studyDesignManager.get(uuid);
            studyDesignManager.commit();
            /*
             * ---------------------------------------------------- Remove
             * existing Power Curve for this object
             * ----------------------------------------------------
             */
            if (uuidFlag && studyDesign.getPowerCurveDescriptions() != null)
                removeFrom(studyDesign);
            /*
             * ---------------------------------------------------- Save new
             * Power Curve object
             * ----------------------------------------------------
             */
            if (uuidFlag) {
                powerCurveManager = new PowerCurveManager();
                powerCurveManager.beginTransaction();
                powerCurveManager.saveOrUpdate(powerCurveDescription, true);
                powerCurveManager.commit();
                /*
                 * ---------------------------------------------------- Set
                 * reference of Power Curve Object to Study Design object
                 * ----------------------------------------------------
                 */
                studyDesign.setPowerCurveDescriptions(powerCurveDescription);

                studyDesignManager = new StudyDesignManager();
                studyDesignManager.beginTransaction();
                studyDesign = studyDesignManager.saveOrUpdate(studyDesign,
                        false);
                studyDesignManager.commit();
            }
        } catch (BaseManagerException bme) {
            StudyDesignLogger.getInstance().error(
                    "ConfidenceIntervalResource : " + bme.getMessage());
            if (powerCurveManager != null) {
                try {
                    powerCurveManager.rollback();
                } catch (BaseManagerException re) {
                    powerCurveDescription = null;
                }
            }
        } catch (StudyDesignException sde) {
            StudyDesignLogger.getInstance().error(
                    "StudyDesignResource : " + sde.getMessage());
            if (studyDesignManager != null) {
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException re) {
                    powerCurveDescription = null;
                }
            }
        }
        return powerCurveDescription;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.ucdenver.bios.studydesignsvc.resource.PowerCurveResource#update(byte
     * [], edu.ucdenver.bios.webservice.common.domain.PowerCurveDescription)
     */
    @Put("application/json")
    public PowerCurveDescription update(
            PowerCurveDescription powerCurveDescription) {
        boolean uuidFlag;
        StudyDesign studyDesign = null;
        byte[] uuid = powerCurveDescription.getUuid(); 
        if (uuid == null)
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        try {
            /*
             * ---------------------------------------------------- Check for
             * existence of a UUID in Study Design object
             * ----------------------------------------------------
             */
            studyDesignManager = new StudyDesignManager();
            studyDesignManager.beginTransaction();
            uuidFlag = studyDesignManager.hasUUID(uuid);
            if (uuidFlag)
                studyDesign = studyDesignManager.get(uuid);
            studyDesignManager.commit();
            /*
             * ---------------------------------------------------- Update Power
             * Curve object ----------------------------------------------------
             */
            PowerCurveDescription powerCurve = studyDesign
                    .getPowerCurveDescriptions();
            if (uuidFlag && powerCurve != null) {
                powerCurveDescription.setId(powerCurve.getId());
                powerCurveManager = new PowerCurveManager();
                powerCurveManager.beginTransaction();
                powerCurveManager.saveOrUpdate(powerCurveDescription, false);
                powerCurveManager.commit();
            } else
                create(powerCurveDescription);
        } catch (BaseManagerException bme) {
            StudyDesignLogger.getInstance().error(
                    "ConfidenceIntervalResource : " + bme.getMessage());
            if (powerCurveManager != null) {
                try {
                    powerCurveManager.rollback();
                } catch (BaseManagerException re) {
                    powerCurveDescription = null;
                }
            }
        } catch (StudyDesignException sde) {
            StudyDesignLogger.getInstance().error(
                    "StudyDesignResource : " + sde.getMessage());
            if (studyDesignManager != null) {
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException re) {
                    powerCurveDescription = null;
                }
            }
        }
        return powerCurveDescription;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.ucdenver.bios.studydesignsvc.resource.PowerCurveResource#remove(byte
     * [])
     */
    @Delete("application/json")
    public PowerCurveDescription remove(byte[] uuid) {
        boolean flag;
        PowerCurveDescription powerCurveDescription = null;
        StudyDesign studyDesign = null;
        try {
            studyDesignManager = new StudyDesignManager();
            studyDesignManager.beginTransaction();
            flag = studyDesignManager.hasUUID(uuid);
            if (flag)
                studyDesign = studyDesignManager.get(uuid);
            studyDesignManager.commit();
            if (flag) {
                powerCurveManager = new PowerCurveManager();
                powerCurveManager.beginTransaction();
                powerCurveDescription = powerCurveManager.delete(uuid,
                        studyDesign.getPowerCurveDescriptions());
                powerCurveManager.commit();
                /*
                 * ---------------------------------------------------- Set
                 * reference of Confidence Interval Object to Study Design
                 * object ----------------------------------------------------
                 */
                /*
                 * studyDesign.setPowerCurveDescriptions(null);
                 * studyDesignManager = new StudyDesignManager();
                 * studyDesignManager.beginTransaction(); studyDesign =
                 * studyDesignManager.saveOrUpdate(studyDesign, false);
                 * studyDesignManager.commit();
                 */
            } else
                throw new StudyDesignException(
                        "No such studyUUID present in tableStudyDesign!!!");
        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(
                    "Failed to load Study Design information: "
                            + bme.getMessage());
            if (studyDesignManager != null)
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException e) {
                }
            if (powerCurveManager != null)
                try {
                    powerCurveManager.rollback();
                } catch (BaseManagerException e) {
                }
            powerCurveDescription = null;
        } catch (StudyDesignException sde) {
            StudyDesignLogger.getInstance().error(
                    "Failed to load Study Design information: "
                            + sde.getMessage());
            if (studyDesignManager != null)
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException e) {
                }
            if (powerCurveManager != null)
                try {
                    powerCurveManager.rollback();
                } catch (BaseManagerException e) {
                }
            powerCurveDescription = null;
        }
        return powerCurveDescription;
    }

    /**
     * Delete a Power Curve object for specified Study Design.
     * 
     * @param studyDesign
     *            the study design
     * @return PowerCurveDescription
     */
    public PowerCurveDescription removeFrom(StudyDesign studyDesign) {
        boolean flag;
        // byte
        PowerCurveDescription powerCurveDescription = null;
        try {
            powerCurveManager = new PowerCurveManager();
            powerCurveManager.beginTransaction();
            powerCurveDescription = powerCurveManager.delete(
                    studyDesign.getUuid(),
                    studyDesign.getPowerCurveDescriptions());
            powerCurveManager.commit();
            /*
             * ---------------------------------------------------- Set
             * reference of Power Curve Object to Study Design object
             * ----------------------------------------------------
             */
            /*
             * studyDesign.setPowerCurveDescriptions(null); studyDesignManager =
             * new StudyDesignManager(); studyDesignManager.beginTransaction();
             * studyDesign = studyDesignManager.saveOrUpdate(studyDesign,
             * false); studyDesignManager.commit();
             */
        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(
                    "Failed to load Study Design information: "
                            + bme.getMessage());
            if (studyDesignManager != null)
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException e) {
                }
            if (powerCurveManager != null)
                try {
                    powerCurveManager.rollback();
                } catch (BaseManagerException e) {
                }
            powerCurveDescription = null;
        }
        /*
         * catch (StudyDesignException sde) {
         * StudyDesignLogger.getInstance().error
         * ("Failed to load Study Design information: " + sde.getMessage()); if
         * (studyDesignManager != null) try { studyDesignManager.rollback(); }
         * catch (BaseManagerException e) {} if (powerCurveManager != null) try
         * { powerCurveManager.rollback(); } catch (BaseManagerException e) {}
         * powerCurveDescription = null; }
         */
        return powerCurveDescription;
    }

}
