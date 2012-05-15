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

import org.restlet.data.Status;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.manager.CovarianceManager;
import edu.ucdenver.bios.webservice.common.domain.Covariance;
import edu.ucdenver.bios.webservice.common.domain.UuidCovarianceName;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

/**
 * Generic Resource class for handling retrieve request for the domain list
 * object of a Covariance. See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class CovarianceRetrieveServerResource extends ServerResource implements
        CovarianceRetrieveResource {
    /**
     * Retrieve Covariance.
     * 
     * @param uuidCovarianceName
     *            the uuid covariance name
     * @return the covariance
     */
    @Post("application/json")
    public final Covariance retrieve(final UuidCovarianceName uuidCovarianceName) {
        CovarianceManager covarianceManager = null;
        Covariance covariance = null;
        byte[] uuid = uuidCovarianceName.getUuid();
        /*
         * Check : empty uuid.
         */
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        /*
         * Check : length of uuid.
         */

        try {
            /*
             * Retrieve Covariance.
             */
            covarianceManager = new CovarianceManager();
            covarianceManager.beginTransaction();
            covariance = covarianceManager.retrieve(uuidCovarianceName);
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

}
