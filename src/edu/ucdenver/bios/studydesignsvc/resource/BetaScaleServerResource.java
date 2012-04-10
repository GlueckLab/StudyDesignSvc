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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.restlet.data.Status;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.ucdenver.bios.studydesignsvc.manager.BetaScaleManager;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.domain.BetaScale;
import edu.ucdenver.bios.webservice.common.domain.BetaScaleList;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;

/**
 * Server Resource class for handling requests for the Beta Scale object. See
 * the StudyDesignApplication class for URI mappings
 *
 * @author Uttara Sakhadeo
 */
public class BetaScaleServerResource extends ServerResource implements
        BetaScaleResource {

    /** The beta scale manager. */
    private BetaScaleManager betaScaleManager = null;

    /** The study design manager. */
    private StudyDesignManager studyDesignManager = null;

    /** The uuid flag. */
    private boolean uuidFlag;

    /**
     * Retrieve a BetaScale object for specified UUID.
     * <br>
     * Use {@link edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager#hasUUID(byte[])} to check existence of
     * a Study Design with given UUID.
     * 
     * @param uuid
     *            the uuid
     * @return ArrayList<BetaScale>
     */
    @Get("application/json")
    public final BetaScaleList retrieve(final byte[] uuid) {
        BetaScaleList betaScaleList = null;
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
                StudyDesign studyDesign = studyDesignManager.get(uuid);
                if (studyDesign != null) {
                    betaScaleList = new BetaScaleList(studyDesign.getBetaScaleList());
                }
            }
            studyDesignManager.commit();
        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (betaScaleManager != null) {
                try {
                    betaScaleManager.rollback();
                } catch (BaseManagerException re) {
                    betaScaleList = null;
                }
            }
            betaScaleList = null;
        } catch (StudyDesignException sde) {
            System.out.println(sde.getMessage());
            StudyDesignLogger.getInstance().error(sde.getMessage());
            if (studyDesignManager != null) {
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException re) {
                    betaScaleList = null;
                }
            }
            betaScaleList = null;
        }
        return betaScaleList;
    }

    /**
     * Create a BetaScale object for specified UUID.
     *
     * @param uuid
     *            the uuid
     * @param betaScaleList
     *            the beta scale list
     * @return ArrayList<BetaScale>
     */
    @Post("application/json")
    /*public final BetaScaleList create(final byte[] uuid ,
            BetaScaleList betaScaleList) {*/
    public final BetaScaleList create(
            BetaScaleList betaScaleList, final byte[] uuid ) {
        StudyDesign studyDesign = null;
        /*byte[] uuid = UUIDUtils.asByteArray(UUID.fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a"));*/
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
            } else {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                        "no study design UUID specified");
            }
            studyDesignManager.commit();
            /*
             * ---------------------------------------------------- Remove
             * existing Beta Scale for this object
             * ----------------------------------------------------
             */            
            if (uuidFlag && !studyDesign.getBetaScaleList().isEmpty()) {
                removeFrom(studyDesign);
            }
            /*
             * ---------------------------------------------------- Set
             * reference of Study Design Object to each Beta Scale element
             * ----------------------------------------------------
             */
            /*
             * for(BetaScale betaScale : betaScaleList) {
             * //betaScale.setStudyDesign(studyDesign); betaScale.setUuid(uuid);
             * }
             */
            if (uuidFlag) {
                /*
                 * betaScaleManager = new BetaScaleManager();
                 * betaScaleManager.beginTransaction();
                 * betaScaleList=betaScaleManager.saveOrUpdate(betaScaleList,
                 * true); betaScaleManager.commit();
                 */
                /*
                 * ---------------------------------------------------- Set
                 * reference of BetaScale Object to Study Design object
                 * ----------------------------------------------------
                 */
                /*
                 * for(BetaScale betaScale : betaScaleList) {
                 * System.out.println(
                 * "in ServerResource id: "+betaScale.getId()); }
                 */
                studyDesign.setBetaScaleList(betaScaleList);
                studyDesignManager = new StudyDesignManager();
                studyDesignManager.beginTransaction();
                studyDesign = studyDesignManager.saveOrUpdate(studyDesign,
                        false);
                studyDesignManager.commit();
            }
        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (betaScaleManager != null) {
                try {
                    betaScaleManager.rollback();
                } catch (BaseManagerException re) {
                    betaScaleList = null;
                }
            }
            betaScaleList = null;
        } catch (StudyDesignException sde) {
            System.out.println(sde.getMessage());
            StudyDesignLogger.getInstance().error(sde.getMessage());
            if (studyDesignManager != null) {
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException re) {
                    betaScaleList = null;
                }
            }
            betaScaleList = null;
        }
        return betaScaleList;
    }

    /**
     * Update a BetaScale object for specified UUID.
     *
     * @param uuid
     *            the uuid
     * @param betaScaleList
     *            the beta scale list
     * @return ArrayList<BetaScale>
     */
    @Put("application/json")
    public final BetaScaleList update(final byte[] uuid ,
            final BetaScaleList betaScaleList) {
        /*return create(uuid, betaScaleList);*/
        return create(betaScaleList, uuid);
    }

    /**
     * Delete a BetaScale object for specified UUID.
     *
     * @param uuid
     *            the uuid
     * @return ArrayList<BetaScale>
     */
    @Delete("application/json")
    public final BetaScaleList remove(final byte[] uuid) {
        BetaScaleList betaScaleList = null;
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
                if (studyDesign != null) {
                    betaScaleList = new BetaScaleList(studyDesign.getBetaScaleList());
                }
            }
            studyDesignManager.commit();
            /*
             * ---------------------------------------------------- Remove
             * existing Beta Scale objects for this object
             * ----------------------------------------------------
             */
            if (!betaScaleList.isEmpty()) {
                betaScaleManager = new BetaScaleManager();
                betaScaleManager.beginTransaction();
                betaScaleList = new BetaScaleList(betaScaleManager.delete(uuid, betaScaleList));
                betaScaleManager.commit();
                /*
                 * ---------------------------------------------------- Set
                 * reference of BetaScale Object to Study Design object
                 * ----------------------------------------------------
                 */
                /*
                 * studyDesign.setBetaScaleList(null); studyDesignManager = new
                 * StudyDesignManager(); studyDesignManager.beginTransaction();
                 * studyDesign = studyDesignManager.saveOrUpdate(studyDesign,
                 * false); studyDesignManager.commit();
                 * betaScaleList=studyDesign.getBetaScaleList();
                 */
            }
        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (betaScaleManager != null) {
                try {
                    betaScaleManager.rollback();
                } catch (BaseManagerException re) {
                    betaScaleList = null;
                }
            }
            betaScaleList = null;
        } catch (StudyDesignException sde) {
            System.out.println(sde.getMessage());
            StudyDesignLogger.getInstance().error(sde.getMessage());
            if (studyDesignManager != null) {
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException re) {
                    betaScaleList = null;
                }
            }
            betaScaleList = null;
        }
        return betaScaleList;
    }

    /**
     * Delete a BetaScale object for specified Study Design.
     *
     * @param studyDesign
     *            the study design
     * @return ArrayList<BetaScale>
     */    
    public final BetaScaleList removeFrom(final StudyDesign studyDesign) {
        BetaScaleList betaScaleList = null;
        try {
            betaScaleManager = new BetaScaleManager();
            betaScaleManager.beginTransaction();
            betaScaleList = new BetaScaleList(betaScaleManager.delete(studyDesign.getUuid(),
                    studyDesign.getBetaScaleList()));
            betaScaleManager.commit();
            /*
             * ----------------------------------------------------
             * Set reference of BetaScale Object to Study Design object
             * ----------------------------------------------------
             */
            /*
             * studyDesign.setConfidenceIntervalDescriptions(null);
             * studyDesignManager = new StudyDesignManager();
             * studyDesignManager.beginTransaction(); studyDesign =
             * studyDesignManager.saveOrUpdate(studyDesign, false);
             * studyDesignManager.commit();
             */
        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(
                    "Failed to load Study Design information: "
                            + bme.getMessage());
            if (studyDesignManager != null) {
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException e) {}
            }
            if (betaScaleManager != null) {
                try {
                    betaScaleManager.rollback();
                } catch (BaseManagerException e) {}
            }
            betaScaleList = null;
        }
        /*
         * catch (StudyDesignException sde) {
         * StudyDesignLogger.getInstance().error
         * ("Failed to load Study Design information: " + sde.getMessage()); if
         * (studyDesignManager != null) try { studyDesignManager.rollback(); }
         * catch (BaseManagerException e) {} if (betaScaleManager != null) try {
         * betaScaleManager.rollback(); } catch (BaseManagerException e) {}
         * betaScaleList = null; }
         */
        return betaScaleList;
    }
}
