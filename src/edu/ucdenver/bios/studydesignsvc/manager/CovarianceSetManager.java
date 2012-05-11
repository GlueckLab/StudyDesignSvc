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

import java.util.Set;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import edu.ucdenver.bios.webservice.common.domain.Covariance;
import edu.ucdenver.bios.webservice.common.domain.CovarianceSet;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Manager class which provides CRUD functionality for CovarianceSet object.
 * 
 * @author Uttara Sakhadeo
 */
public class CovarianceSetManager extends StudyDesignParentManager {

    /**
     * Instantiates a new covariance set manager.
     * 
     * @throws BaseManagerException
     *             the base manager exception
     */
    public CovarianceSetManager() throws BaseManagerException {
        super();
    }

    /**
     * Retrieve CovarianceSet.
     * 
     * @param uuid
     *            the uuid
     * @return the covariance set
     */
    public final CovarianceSet retrieve(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        CovarianceSet covarianceSet = null;
        try {
            /*
             * Retrieve Study Design for given uuid
             */
            StudyDesign studyDesign = get(uuid);
            /*
             * Retrieve Original CovarianceSet Object
             */
            if (studyDesign != null) {
                Set<Covariance> originalSet = studyDesign.getCovariance();
                if (originalSet != null && !originalSet.isEmpty()) {
                    covarianceSet = new CovarianceSet(uuid, originalSet);
                } else {
                    /*
                     * uuid exists but no CovarianceSet entry present. If uuid =
                     * null too; then it means no entry for this uuid.
                     */
                    covarianceSet = new CovarianceSet(uuid, null);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete Covariance object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return covarianceSet;
    }

    /**
     * Delete CovarianceSet.
     * 
     * @param uuid
     *            the uuid
     * @return the covariance set
     */
    public final CovarianceSet delete(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        CovarianceSet covarianceSet = null;
        StudyDesign studyDesign = null;
        try {
            /*
             * Retrieve Original Covariance Object
             */
            studyDesign = get(uuid);
            if (studyDesign != null) {
                Set<Covariance> originalSet = studyDesign.getCovariance();
                /*
                 * Delete Existing Covariance Set Object
                 */
                if (originalSet != null && !originalSet.isEmpty()) {
                    covarianceSet = delete(uuid, originalSet);
                }
                /*
                 * Update Study Design Object
                 */
                studyDesign.setCovariance(null);
                session.update(studyDesign);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete Covariance object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        /*
         * Return CovarianceSet
         */
        return covarianceSet;
    }

    /**
     * Delete CovarianceSet.
     * 
     * @param uuid
     *            the uuid
     * @param covarianceSet
     *            the covariance set
     * @return the covariance set
     */
    private CovarianceSet delete(final byte[] uuid,
            final Set<Covariance> covarianceSet) {
        CovarianceSet deletedSet = null;
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        try {
            if (covarianceSet != null && !covarianceSet.isEmpty()) {
                for (Covariance covariance : covarianceSet) {
                    session.delete(covariance);
                }
            }
            deletedSet = new CovarianceSet(uuid, covarianceSet);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete Covariance object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return deletedSet;
    }

    /**
     * Save or update CovarianceSet.
     * 
     * @param covarianceSet
     *            the covariance set
     * @param isCreation
     *            the is creation
     * @return the covariance set
     */
    public final CovarianceSet saveOrUpdate(final CovarianceSet covarianceSet,
            final boolean isCreation) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        StudyDesign studyDesign = null;
        Set<Covariance> originalSet = null;
        CovarianceSet newCovarianceSet = null;
        byte[] uuid = covarianceSet.getUuid();
        Set<Covariance> newSet = covarianceSet.getCovarianceSet();

        try {
            /*
             * Retrieve Study Design Object
             */
            studyDesign = get(uuid);
            if (studyDesign != null) {
                originalSet = studyDesign.getCovariance();
                /*
                 * Delete Existing Covariance Set Object
                 */
                if (originalSet != null && !originalSet.isEmpty()) {
                    delete(uuid, originalSet);
                }
                if (isCreation) {
                    for (Covariance covariance : newSet) {
                        session.save(covariance);
                    }
                } else {
                    for (Covariance covariance : newSet) {
                        session.update(covariance);
                    }
                }
                /*
                 * Update Study Design Object
                 */
                studyDesign.setCovariance(newSet);
                session.update(studyDesign);
                /*
                 * Return Persisted CovarianceSet
                 */
                newCovarianceSet = new CovarianceSet(uuid, newSet);
            }
        } catch (Exception e) {
            newCovarianceSet.setCovarianceSet(null);
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to save Covariance object : " + e.getMessage());
        }
        return newCovarianceSet;
    }
}
