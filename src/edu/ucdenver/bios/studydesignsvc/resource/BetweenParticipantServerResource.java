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

import java.util.List;
import java.util.Set;

import org.restlet.data.Status;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.ucdenver.bios.studydesignsvc.manager.BetweenParticipantFactorManager;
import edu.ucdenver.bios.studydesignsvc.manager.CategoryManager;
import edu.ucdenver.bios.studydesignsvc.manager.HypothesisManager;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.domain.BetweenParticipantFactor;
import edu.ucdenver.bios.webservice.common.domain.BetweenParticipantFactorList;
import edu.ucdenver.bios.webservice.common.domain.Hypothesis;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

/**
 * The Class BetweenParticipantServerResource.
 */
public class BetweenParticipantServerResource implements
        BetweenParticipantResource {

    /** The category manager. */
    private CategoryManager categoryManager = null;

    /** The between participant factor manager. */
    private BetweenParticipantFactorManager betweenParticipantFactorManager =
            null;

    /** The study design manager. */
    private StudyDesignManager studyDesignManager = null;

    /** The uuid flag. */
    private boolean uuidFlag;

    /**
     * Retrieve a BetweenParticipantFactor object for specified UUID.
     *
     * @param uuid
     *            the uuid
     * @return List<BetweenParticipantFactor>
     */
    @Get("application/json")
    public final BetweenParticipantFactorList retrieve(final byte[] uuid) {
        BetweenParticipantFactorList betweenParticipantFactorList = null;
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
                    betweenParticipantFactorList = new BetweenParticipantFactorList(studyDesign
                            .getBetweenParticipantFactorList());
                }
            }
            studyDesignManager.commit();
        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (betweenParticipantFactorManager != null) {
                try {
                    betweenParticipantFactorManager.rollback();
                } catch (BaseManagerException re) {
                    betweenParticipantFactorList = null;
                }
            }
            betweenParticipantFactorList = null;
        } catch (StudyDesignException sde) {
            System.out.println(sde.getMessage());
            StudyDesignLogger.getInstance().error(sde.getMessage());
            if (studyDesignManager != null) {
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException re) {
                    betweenParticipantFactorList = null;
                }
            }
            betweenParticipantFactorList = null;
        }
        return betweenParticipantFactorList;
    }

    /**
     * Create a BetweenParticipantFactor object for specified UUID.
     *
     * @param uuid
     *            the uuid
     * @param betweenParticipantFactorList
     *            the between participant factor list
     * @return List<BetweenParticipantFactor>
     */
    @Post("application/json")
    public final BetweenParticipantFactorList create(
            BetweenParticipantFactorList betweenParticipantFactorList) {
        StudyDesign studyDesign = null;
        byte[] uuid = betweenParticipantFactorList.getUuid();
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
             * ----------------------------------------------------
             * Remove existing Between Participant for this object
             * ----------------------------------------------------
             */
            if (uuidFlag
                    && !studyDesign.getBetweenParticipantFactorList().isEmpty()) {
                removeFrom(studyDesign);
            }
            /*
             * ---------------------------------------------------- Set
             * reference of Study Design Object to each Between Participant
             * element ----------------------------------------------------
             */
            /*
             * for(BetweenParticipantFactor BetweenParticipantFactor :
             * betweenParticipantFactorList)
             * BetweenParticipantFactor.setStudyDesign(studyDesign);
             * studyDesign.
             * setBetweenParticipantFactorList(betweenParticipantFactorList);
             */
            /*
             * ---------------------------------------------------- Save new
             * Between Participant Effects object
             * ----------------------------------------------------
             */
            if (uuidFlag) {

                /*
                 * betweenParticipantFactorManager = new
                 * BetweenParticipantFactorManager();
                 * betweenParticipantFactorManager.beginTransaction();
                 * betweenParticipantFactorManager
                 * .saveOrUpdate(betweenParticipantFactorList, true);
                 * betweenParticipantFactorManager.commit();
                 */
                /*
                 * ---------------------------------------------------- Set
                 * reference of Power Curve Object to Study Design object
                 * ----------------------------------------------------
                 */
                studyDesign
                .setBetweenParticipantFactorList(betweenParticipantFactorList.getBetweenParticipantFactorList());
                studyDesignManager = new StudyDesignManager();
                studyDesignManager.beginTransaction();
                studyDesign = studyDesignManager.saveOrUpdate(studyDesign,
                        false);
                studyDesignManager.commit();
                betweenParticipantFactorList = new BetweenParticipantFactorList(studyDesign
                        .getBetweenParticipantFactorList());
            }
        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (betweenParticipantFactorManager != null) {
                try {
                    betweenParticipantFactorManager.rollback();
                } catch (BaseManagerException re) {
                    betweenParticipantFactorList = null;
                }
            }
            betweenParticipantFactorList = null;
        } catch (StudyDesignException sde) {
            System.out.println(sde.getMessage());
            StudyDesignLogger.getInstance().error(sde.getMessage());
            if (studyDesignManager != null) {
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException re) {
                    betweenParticipantFactorList = null;
                }
            }
            betweenParticipantFactorList = null;
        }
        return betweenParticipantFactorList;
    }

    /**
     * Update a BetweenParticipantFactor object for specified UUID.
     *
     * @param uuid
     *            the uuid
     * @param betweenParticipantFactorList
     *            the between participant factor list
     * @return List<BetweenParticipantFactor>
     */
    @Put("application/json")
    public final BetweenParticipantFactorList update(
            final BetweenParticipantFactorList betweenParticipantFactorList) {
        return create(betweenParticipantFactorList);
    }

    /**
     * Delete a BetweenParticipantFactor object for specified UUID.
     *
     * @param uuid
     *            the uuid
     * @return List<BetweenParticipantFactor>
     */
    @Delete("application/json")
    public final BetweenParticipantFactorList remove(final byte[] uuid) {
        BetweenParticipantFactorList betweenParticipantFactorList = null;
        StudyDesign studyDesign = null;
        try {
            /*
             * ---------------------------------------------------- Check for
             * existence of a UUID in Study Design object
             * ----------------------------------------------------
             */
            studyDesignManager = new StudyDesignManager();
            studyDesignManager.beginTransaction();
            if (uuid == null) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                        "no study design UUID specified");
            }
            uuidFlag = studyDesignManager.hasUUID(uuid);
            if (uuidFlag) {
                studyDesign = studyDesignManager.get(uuid);
                if (studyDesign != null) {
                    betweenParticipantFactorList = new BetweenParticipantFactorList(uuid,studyDesign
                            .getBetweenParticipantFactorList());
                }
                /*
                 * if(betweenParticipantFactorList.isEmpty()) throw new
                 * ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                 * "no TypeIError is specified");
                 */
            }
            studyDesignManager.commit();
            /*
             * ---------------------------------------------------- Remove
             * existing Between Participant objects for this object
             * ----------------------------------------------------
             */
            if (!studyDesign.getHypothesis().isEmpty()) {
                HypothesisManager hypothesisManager =
                    new HypothesisManager();
                hypothesisManager.beginTransaction();
                   hypothesisManager
                        .delete(uuid, studyDesign.getHypothesis());
                hypothesisManager.commit();
            }
            if (!studyDesign.getBetweenParticipantFactorList().isEmpty()) {
                betweenParticipantFactorManager =
                    new BetweenParticipantFactorManager();
                betweenParticipantFactorManager.beginTransaction();
                betweenParticipantFactorList = new BetweenParticipantFactorList(betweenParticipantFactorManager
                        .delete(uuid, betweenParticipantFactorList.getBetweenParticipantFactorList()));
                betweenParticipantFactorManager.commit();
            }
        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (betweenParticipantFactorManager != null) {
                try {
                    betweenParticipantFactorManager.rollback();
                } catch (BaseManagerException re) {
                    betweenParticipantFactorList = null;
                }
            }
            betweenParticipantFactorList = null;
        } catch (StudyDesignException sde) {
            System.out.println(sde.getMessage());
            StudyDesignLogger.getInstance().error(sde.getMessage());
            if (studyDesignManager != null) {
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException re) {
                    betweenParticipantFactorList = null;
                }
            }
            betweenParticipantFactorList = null;
        }
        return betweenParticipantFactorList;
    }

    /**
     * Delete a BetweenParticipantFactor object for specified Study Design.
     *
     * @param studyDesign
     *            the study design
     * @return List<BetweenParticipantFactor>
     */    
    public final BetweenParticipantFactorList removeFrom(
            final StudyDesign studyDesign) {
        BetweenParticipantFactorList betweenParticipantFactorList = null;
        try {
            if (!studyDesign.getHypothesis().isEmpty()) {
                HypothesisManager hypothesisManager =
                    new HypothesisManager();
                hypothesisManager.beginTransaction();
                   hypothesisManager
                        .delete(studyDesign.getUuid(), studyDesign.getHypothesis());
                hypothesisManager.commit();
            }
            betweenParticipantFactorManager =
                new BetweenParticipantFactorManager();
            betweenParticipantFactorManager.beginTransaction();
            betweenParticipantFactorList = new BetweenParticipantFactorList(betweenParticipantFactorManager
                    .delete(studyDesign.getUuid(),
                            studyDesign.getBetweenParticipantFactorList()));
            betweenParticipantFactorManager.commit();
            /*
             * ---------------------------------------------------- Set
             * reference of BetweenParticipantFactor Object to Study Design
             * object ----------------------------------------------------
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
                } catch (BaseManagerException e) {
                    betweenParticipantFactorList = null;
                }
                betweenParticipantFactorList = null;
            }
            if (betweenParticipantFactorManager != null) {
                try {
                    betweenParticipantFactorManager.rollback();
                } catch (BaseManagerException e) {
                    betweenParticipantFactorList = null;
                }
                betweenParticipantFactorList = null;
            }
        }
        /*
         * catch (StudyDesignException sde) {
         * StudyDesignLogger.getInstance().error
         * ("Failed to load Study Design information: " + sde.getMessage()); if
         * (studyDesignManager != null) try { studyDesignManager.rollback(); }
         * catch (BaseManagerException e) {} if (betweenParticipantFactorManager
         * != null) try { betweenParticipantFactorManager.rollback(); } catch
         * (BaseManagerException e) {} betweenParticipantFactorList = null; }
         */
        return betweenParticipantFactorList;
    }
}
