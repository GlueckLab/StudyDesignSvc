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
package edu.ucdenver.bios.studydesignsvc.manager;

import java.util.List;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import edu.ucdenver.bios.webservice.common.domain.BetaScale;
import edu.ucdenver.bios.webservice.common.domain.BetaScaleList;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Manager class which provides CRUD functionality for BetaScale object.
 * 
 * @author Uttara Sakhadeo
 */
public class BetaScaleManager extends StudyDesignParentManager {
    /**
     * Instantiates a new beta scale manager.
     * 
     * @throws BaseManagerException
     *             the base manager exception
     */
    public BetaScaleManager() throws BaseManagerException {
        super();
    }

    /**
     * Retrieve BetaScaleList.
     * 
     * @param uuid
     *            the uuid
     * @return the beta scale list
     */
    public final BetaScaleList retrieve(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        BetaScaleList betaScaleList = null;
        try {
            /*
             * Retrieve Study Design for given uuid
             */
            StudyDesign studyDesign = get(uuid);
            /*
             * Retrieve Original BetaScaleList Object
             */
            if (studyDesign != null) {
                List<BetaScale> originalList = studyDesign.getBetaScaleList();
                if (originalList != null && !originalList.isEmpty()) {
                    betaScaleList = new BetaScaleList(uuid, originalList);
                } else {
                    /*
                     * uuid exists but no BetaScaleList entry present. If uuid =
                     * null too; then it means no entry for this uuid.
                     */
                    betaScaleList = new BetaScaleList(uuid, null);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete BetaScale object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return betaScaleList;
    }

    /**
     * Delete BetaScaleList.
     * 
     * @param uuid
     *            the uuid
     * @return the beta scale list
     */
    public final BetaScaleList delete(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        BetaScaleList betaScaleList = null;
        StudyDesign studyDesign = null;
        try {
            /*
             * Retrieve Original Beta Scale Object
             */
            studyDesign = get(uuid);
            if (studyDesign != null) {
                List<BetaScale> originalList = studyDesign.getBetaScaleList();
                /*
                 * Delete Existing Beta Scale List Object
                 */
                if (originalList != null && !originalList.isEmpty()) {
                    betaScaleList = delete(uuid, originalList);
                }
                /*
                 * Update Study Design Object
                 */
                studyDesign.setBetaScaleList(null);
                session.update(studyDesign);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete BetaScale object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        /*
         * Return BetaScaleList
         */
        return betaScaleList;
    }

    /**
     * Delete BetaScaleList.
     * 
     * @param uuid
     *            the uuid
     * @param betaScaleList
     *            the beta scale list
     * @return the beta scale list
     */
    private BetaScaleList delete(final byte[] uuid,
            final List<BetaScale> betaScaleList) {
        BetaScaleList deletedList = null;
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        try {
            if (betaScaleList != null && !betaScaleList.isEmpty()) {
                for (BetaScale betaScale : betaScaleList) {
                    session.delete(betaScale);
                }
            }
            deletedList = new BetaScaleList(uuid, betaScaleList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete BetaScale object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return deletedList;
    }

    /**
     * Saves or update BetaScaleList.
     * 
     * @param betaScaleList
     *            the beta scale list
     * @param isCreation
     *            the is creation
     * @return the beta scale list
     */
    public final BetaScaleList saveOrUpdate(final BetaScaleList betaScaleList,
            final boolean isCreation) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        StudyDesign studyDesign = null;
        List<BetaScale> originalList = null;
        BetaScaleList newBetaScaleList = null;
        byte[] uuid = betaScaleList.getUuid();
        List<BetaScale> newList = betaScaleList.getBetaScaleList();

        try {
            /*
             * Retrieve Study Design Object
             */
            studyDesign = get(uuid);
            if (studyDesign != null) {
                originalList = studyDesign.getBetaScaleList();
                /*
                 * Delete Existing Beta Scale List Object
                 */
                if (originalList != null && !originalList.isEmpty()) {
                    delete(uuid, originalList);
                }
                if (isCreation) {
                    for (BetaScale betaScale : newList) {
                        session.save(betaScale);
                    }
                } else {
                    for (BetaScale betaScale : newList) {
                        session.update(betaScale);
                    }
                }
                /*
                 * Update Study Design Object
                 */
                studyDesign.setBetaScaleList(newList);
                session.update(studyDesign);
                /*
                 * Return Persisted BetaScaleList
                 */
                newBetaScaleList = new BetaScaleList(uuid, newList);
            }
        } catch (Exception e) {
            newList = null;
            newBetaScaleList.setBetaScaleList(newList);
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to save BetaScale object : " + e.getMessage());
        }
        return newBetaScaleList;
    }
}
