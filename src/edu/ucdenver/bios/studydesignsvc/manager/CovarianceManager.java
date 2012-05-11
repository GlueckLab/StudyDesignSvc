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

import edu.ucdenver.bios.webservice.common.domain.Covariance;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.domain.UuidCovariance;
import edu.ucdenver.bios.webservice.common.domain.UuidCovarianceName;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Manager class which provides CRUD functionality for Covariance object.
 * 
 * @author Uttara Sakhadeo
 */
public class CovarianceManager extends StudyDesignParentManager {

    /**
     * Deletes the Covariance.
     * 
     * @param uuid
     *            the uuid
     * @param covariance
     *            the covariance
     * @return the covariance
     */
    private Covariance delete(final byte[] uuid, final Covariance covariance) {
        // Covariance deletedSet = null;
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        try {
            if (covariance != null) {
                session.delete(covariance);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete Covariance object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return covariance;
    }

    /**
     * Instantiates a new covariance manager.
     * 
     * @throws BaseManagerException
     *             the base manager exception
     */
    public CovarianceManager() throws BaseManagerException {
        super();
    }

    /**
     * Retrieves the Covariance.
     * 
     * @param uuidCovarianceName
     *            the uuid covariance name
     * @return the covariance
     */
    public final Covariance retrieve(final UuidCovarianceName uuidCovarianceName) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        Covariance originalCovariance = null;
        String covarianceName = uuidCovarianceName.getCovarianceName();
        byte[] uuid = uuidCovarianceName.getUuid();
        try {
            /*
             * Retrieve Study Design for given uuid
             */
            StudyDesign studyDesign = get(uuid);
            /*
             * Retrieve Original Covariance Object
             */
            if (studyDesign != null) {
                originalCovariance = studyDesign
                        .getCovarianceFromSet(covarianceName);
                if (originalCovariance == null) {
                    originalCovariance = null;
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete Covariance object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return originalCovariance;
    }

    /**
     * Deletes the Covariance.
     * 
     * @param uuidCovarianceName
     *            the uuid covariance name
     * @return the covariance
     */
    public final Covariance delete(final UuidCovarianceName uuidCovarianceName) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        Covariance covariance = null;
        StudyDesign studyDesign = null;
        byte[] uuid = uuidCovarianceName.getUuid();
        String name = uuidCovarianceName.getCovarianceName();
        try {
            /*
             * Retrieve Original Covariance Object
             */
            studyDesign = get(uuid);
            if (studyDesign != null) {
                covariance = studyDesign.getCovarianceFromSet(name);
                /*
                 * Delete Existing Covariance Set Object
                 */
                if (covariance != null) {
                    covariance = delete(uuid, covariance);
                }
                /*
                 * Update Study Design Object
                 */
                /*
                 * studyDesign.setC(null); session.update(studyDesign);
                 */
            }
        } catch (Exception e) {
            covariance = null;
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete Covariance object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        /*
         * Return Covariance
         */
        return covariance;
    }

    /**
     * Saves or updates the Covariance.
     * 
     * @param uuidCovariance
     *            the uuid covariance
     * @param isCreation
     *            the is creation
     * @return the covariance
     */
    public final Covariance saveOrUpdate(final UuidCovariance uuidCovariance,
            final boolean isCreation) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        StudyDesign studyDesign = null;
        Covariance originalCovariance = null;
        Covariance newCovariance = uuidCovariance.getCovariance();
        byte[] uuid = uuidCovariance.getUuid();
        String covarianceName = newCovariance.getName();

        try {
            /*
             * Retrieve Study Design Object
             */
            studyDesign = get(uuid);
            if (studyDesign != null) {
                originalCovariance = studyDesign
                        .getCovarianceFromSet(covarianceName);
                /*
                 * Delete Existing Covariance Set Object
                 */
                if (originalCovariance != null) {
                    delete(uuid, originalCovariance);
                }
                if (isCreation) {
                    session.save(newCovariance);
                } else {
                    session.update(newCovariance);
                }
                /*
                 * Update Study Design Object
                 */
                studyDesign.addCovariance(newCovariance);
                session.update(studyDesign);
                /*
                 * Return Persisted Covariance
                 */
            }
        } catch (Exception e) {
            newCovariance = null;
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to save Covariance object : " + e.getMessage());
        }
        return newCovariance;
    }
}
