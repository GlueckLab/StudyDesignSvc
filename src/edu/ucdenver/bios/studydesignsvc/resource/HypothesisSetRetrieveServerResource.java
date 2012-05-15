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
import edu.ucdenver.bios.studydesignsvc.manager.HypothesisSetManager;
import edu.ucdenver.bios.webservice.common.domain.HypothesisSet;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

/**
 * Generic Resource class for handling retrieve requests for the domain
 * hypothesisSet object of a Hypothesis. See the StudyDesignApplication class
 * for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class HypothesisSetRetrieveServerResource extends ServerResource
        implements HypothesisSetRetrieveResource {
    
    /**
     * Retrieves the HypothesisSet.
     * 
     * @param uuid
     *            the uuid
     * @return the hypothesis set
     */
    @Post("application/json")
    public final HypothesisSet retrieve(final byte[] uuid) {
        HypothesisSetManager hypothesisSetManager = null;
        HypothesisSet hypothesisSet = null;
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
             * Retrieve Hypothesis Set.
             */
            hypothesisSetManager = new HypothesisSetManager();
            hypothesisSetManager.beginTransaction();
            hypothesisSet = hypothesisSetManager.retrieve(uuid);
            hypothesisSetManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (hypothesisSetManager != null) {
                try {
                    hypothesisSetManager.rollback();
                } catch (BaseManagerException re) {
                    hypothesisSet = null;
                }
            }
            hypothesisSet = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (hypothesisSetManager != null) {
                try {
                    hypothesisSetManager.rollback();
                } catch (BaseManagerException re) {
                    hypothesisSet = null;
                }
            }
            hypothesisSet = null;
        }
        return hypothesisSet;
    }
    
}
