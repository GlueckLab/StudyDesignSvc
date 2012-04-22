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
import edu.ucdenver.bios.webservice.common.hibernate.BaseManager;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Manager class which provides CRUD functionality for MySQL table Covariance
 * object.
 * 
 * @author Uttara Sakhadeo
 */
public class CovarianceManager extends StudyDesignParentManager {

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
     * Delete a Set<Covariance> object by the specified UUID.
     * 
     * @param uuidBytes
     *            the uuid bytes
     * @param covarianceSet
     *            the covariance set
     * @return Set<Covariance>
     */
    public Set<Covariance> delete(byte[] uuidBytes,
            Set<Covariance> covarianceSet) {
        if (!transactionStarted)
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        try {
            // covarianceSet = get(uuidBytes);
            for (Covariance covariance : covarianceSet)
                session.delete(covariance);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete Covariance object for UUID '" + uuidBytes
                            + "': " + e.getMessage());
        }
        return covarianceSet;
    }

    /**
     * Delete Specified Covariance.
     * 
     * @param uuidBytes
     *            the uuid bytes
     * @param covariance
     *            the covariance
     * @return the covariance
     */
    public Covariance delete(byte[] uuidBytes, Covariance covariance) {
        if (!transactionStarted)
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        try {
            session.delete(covariance);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete Covariance object for UUID '" + uuidBytes
                            + "': " + e.getMessage());
        }
        return covariance;
    }

    /**
     * Save or Update a Set<Covariance> object by the specified UUID.
     * 
     * @param covarianceSet
     *            : Set<Covariance>
     * @param isCreation
     *            : boolean
     * @return covarianceSet : Set<Covariance>
     */
    public Set<Covariance> saveOrUpdate(Set<Covariance> covarianceSet,
            boolean isCreation) {
        if (!transactionStarted)
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        try {
            if (isCreation == true) {
                for (Covariance covariance : covarianceSet) {
                    session.save(covariance);
                    // System.out.println("in save id: "+covariance.getId());
                }
            } else {
                for (Covariance covariance : covarianceSet)
                    session.update(covariance);
            }
        } catch (Exception e) {
            covarianceSet = null;
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to save Covariance object : " + e.getMessage());
        }
        return covarianceSet;
    }

    /**
     * Save or update Specified Covariance.
     * 
     * @param covariance
     *            the covariance
     * @param isCreation
     *            the is creation
     * @return the covariance
     */
    public Covariance saveOrUpdate(Covariance covariance, boolean isCreation) {
        if (!transactionStarted)
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        try {
            if (isCreation == true) {
                session.save(covariance);
            } else {
                session.update(covariance);
            }
        } catch (Exception e) {
            covariance = null;
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to save Covariance object : " + e.getMessage());
        }
        return covariance;
    }
}
