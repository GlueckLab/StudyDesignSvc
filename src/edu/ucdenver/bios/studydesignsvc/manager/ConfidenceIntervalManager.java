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
package edu.ucdenver.bios.studydesignsvc.manager;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import edu.ucdenver.bios.webservice.common.domain.ConfidenceIntervalDescription;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.domain.UuidConfidenceIntervalDescription;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Manager class which provides CRUD functionality for
 * ConfidenceIntervalDescription object.
 * 
 * @author Uttara Sakhadeo
 */
public class ConfidenceIntervalManager extends StudyDesignParentManager {

    /**
     * Instantiates a new confidence interval manager.
     * 
     * @throws BaseManagerException
     *             the base manager exception
     */
    public ConfidenceIntervalManager() throws BaseManagerException {
        super();
    }

    /**
     * Retrieve UuidConfidenceIntervalDescription.
     * 
     * @param uuid
     *            the uuid
     * @return the uuid confidence interval description
     */
    public final UuidConfidenceIntervalDescription retrieve(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        UuidConfidenceIntervalDescription uuidConfidenceIntervalDescription = null;
        try {
            /*
             * Retrieve Study Design for given uuid
             */
            StudyDesign studyDesign = get(uuid);
            /*
             * Retrieve Original ConfidenceIntervalDescription Object
             */
            if (studyDesign != null) {
                ConfidenceIntervalDescription original = studyDesign
                        .getConfidenceIntervalDescriptions();
                if (original != null) {
                    uuidConfidenceIntervalDescription = new UuidConfidenceIntervalDescription(
                            uuid, original);
                } else {
                    /*
                     * uuid exists but no ConfidenceIntervalDescription entry
                     * present. If uuid = null too; then it means no entry for
                     * this uuid.
                     */
                    uuidConfidenceIntervalDescription = new UuidConfidenceIntervalDescription(
                            uuid, null);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete ConfidenceIntervalDescription object for UUID '"
                            + uuid + "': " + e.getMessage());
        }
        return uuidConfidenceIntervalDescription;
    }

    /**
     * Delete UuidConfidenceIntervalDescription.
     * 
     * @param uuid
     *            the uuid
     * @return the uuid confidence interval description
     */
    public final UuidConfidenceIntervalDescription delete(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        UuidConfidenceIntervalDescription deleted = null;
        StudyDesign studyDesign = null;
        try {
            /*
             * Retrieve Original ConfidenceIntervalDescription Object
             */
            studyDesign = get(uuid);
            if (studyDesign != null) {
                ConfidenceIntervalDescription original = studyDesign
                        .getConfidenceIntervalDescriptions();
                /*
                 * Delete Existing ConfidenceIntervalDescription List Object
                 */
                if (original != null) {
                    deleted = delete(uuid, original);
                }
                /*
                 * Update Study Design Object
                 */
                studyDesign.setConfidenceIntervalDescriptions(null);
                session.update(studyDesign);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete ConfidenceIntervalDescription object for UUID '"
                            + uuid + "': " + e.getMessage());
        }
        /*
         * Return ConfidenceIntervalDescription
         */
        return deleted;
    }

    /**
     * Delete UuidConfidenceIntervalDescription.
     * 
     * @param uuid
     *            the uuid
     * @param original
     *            the original
     * @return the uuid confidence interval description
     */
    private UuidConfidenceIntervalDescription delete(final byte[] uuid,
            final ConfidenceIntervalDescription original) {
        UuidConfidenceIntervalDescription deleted = null;
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        try {
            session.delete(original);
            deleted = new UuidConfidenceIntervalDescription(uuid, original);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete ConfidenceIntervalDescription object for UUID '"
                            + uuid + "': " + e.getMessage());
        }
        return deleted;
    }

    /**
     * Save or update UuidConfidenceIntervalDescription.
     * 
     * @param uuidConfidence
     *            the uuid confidence
     * @param isCreation
     *            the is creation
     * @return the uuid confidence interval description
     */
    public final UuidConfidenceIntervalDescription saveOrUpdate(
            final UuidConfidenceIntervalDescription uuidConfidence,
            final boolean isCreation) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        StudyDesign studyDesign = null;
        UuidConfidenceIntervalDescription uuidConfidenceIntervalDescription = null;
        ConfidenceIntervalDescription originalConfidence = null;
        byte[] uuid = uuidConfidence.getUuid();
        ConfidenceIntervalDescription newConfidence = uuidConfidence
                .getConfidenceInterval();

        try {
            /*
             * Retrieve Study Design Object
             */
            studyDesign = get(uuid);
            if (studyDesign != null) {
                originalConfidence = studyDesign
                        .getConfidenceIntervalDescriptions();
                /*
                 * Delete Existing ConfidenceIntervalDescription Object
                 */
                if (originalConfidence != null) {
                    delete(uuid, originalConfidence);
                }
                if (isCreation) {
                    session.save(newConfidence);
                } else {
                    session.update(newConfidence);
                }
                /*
                 * Update Study Design Object
                 */
                studyDesign.setConfidenceIntervalDescriptions(newConfidence);
                session.update(studyDesign);
                /*
                 * Return Persisted ConfidenceIntervalDescription
                 */
                uuidConfidenceIntervalDescription = new UuidConfidenceIntervalDescription(
                        uuid, newConfidence);
            }
        } catch (Exception e) {
            uuidConfidenceIntervalDescription = null;
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to save ConfidenceIntervalDescription object : "
                            + e.getMessage());
        }
        return uuidConfidenceIntervalDescription;
    }

}
