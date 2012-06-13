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
import edu.ucdenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

/**
 * Generic Resource Class for handling retrieve request for the StudyDesign
 * domain object. See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class StudyRetrieveServerResource extends ServerResource implements
        StudyRetrieveResource {
    /**
     * Retrieve the study design matching the specified UUID. Returns
     * "not found" if no matching designs are available
     * 
     * @return study designs with specified UUID
     */
    @Post("application/json")
    public StudyDesign retrieve(byte[] uuid) {
        // byte[] uuid = studyUuid.getUuid();
        StudyDesignManager studyDesignManager = null;
        StudyDesign studyDesign = null;

        try {
            if (uuid == null)
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                        "no study design UUID specified");

            studyDesignManager = new StudyDesignManager();
            studyDesignManager.beginTransaction();
            studyDesign = studyDesignManager.get(uuid);
            studyDesignManager.commit();
        } catch (BaseManagerException bme) {
            StudyDesignLogger.getInstance().error(
                    "StudyDesignResource : " + bme.getMessage());
            if (studyDesignManager != null) {
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException re) {
                    studyDesign = null;
                }
            }
        } catch (StudyDesignException sde) {
            StudyDesignLogger.getInstance().error(
                    "StudyDesignResource : " + sde.getMessage());
            if (studyDesignManager != null) {
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException re) {
                    studyDesign = null;
                }
            }
        }
        return studyDesign;
    }
}
