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
import edu.ucdenver.bios.studydesignsvc.manager.ResponsesManager;
import edu.ucdenver.bios.webservice.common.domain.ResponseList;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

/**
 * Generic Resource Interface for handling (PUT, POST, DELETE) requests for the
 * ResponseNode domain object. See the StudyDesignApplication class for URI
 * mappings
 * 
 * @author Uttara Sakhadeo
 */
public class ResponsesRetrieveServerResource extends ServerResource implements
        ResponsesRetrieveResource {
    /**
     * Retrieves the ResponseList.
     * 
     * @param uuid
     *            the uuid
     * @return the response list
     */
    @Post("application/json")
    public final ResponseList retrieve(final byte[] uuid) {
        ResponsesManager responsesManager = null;
        ResponseList responsesList = null;
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
             * Retrieve ResponseList list.
             */
            responsesManager = new ResponsesManager();
            responsesManager.beginTransaction();
            responsesList = responsesManager.retrieve(uuid);
            responsesManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (responsesManager != null) {
                try {
                    responsesManager.rollback();
                } catch (BaseManagerException re) {
                    responsesList = null;
                }
            }
            responsesList = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (responsesManager != null) {
                try {
                    responsesManager.rollback();
                } catch (BaseManagerException re) {
                    responsesList = null;
                }
            }
            responsesList = null;
        }
        return responsesList;
    }

}
