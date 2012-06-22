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

import java.util.regex.Pattern;

import org.restlet.data.Status;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.manager.MatrixSetManager;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrixSet;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;
/**
 * Server Resource class for handling retrieve requests for the NamedMatrixSet
 * object. See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class MatrixSetRetrieveServerResource extends ServerResource implements
MatrixSetRetrieveResource{
    
    /**
     * Retrieves the NamedMatrixSet.
     * 
     * @param uuid
     *            the uuid
     * @return the named matrix set
     */
    @Post("application/json")
    public final NamedMatrixSet retrieve(final byte[] uuid) {
        MatrixSetManager matrixSetManager = null;
        NamedMatrixSet namedMatrixSet = null;
        /*
         * Check : empty uuid.
         */
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        /*
         * Validate Uuid.
         */
        boolean uuidFlag = false;
        try {
            uuidFlag = Pattern.matches("[0-9a-fA-F]{32}",
                    UUIDUtils.bytesToHex(uuid));
        } catch (Exception e) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "invalid UUID specified");
        }
        if (!uuidFlag) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "invalid UUID specified");
        }
        

        try {
            /*
             * Retrieve NamedMatrix Set .
             */
            matrixSetManager = new MatrixSetManager();
            matrixSetManager.beginTransaction();
            namedMatrixSet = matrixSetManager.retrieve(uuid);
            matrixSetManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (matrixSetManager != null) {
                try {
                    matrixSetManager.rollback();
                } catch (BaseManagerException re) {
                    namedMatrixSet = null;
                }
            }
            namedMatrixSet = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (matrixSetManager != null) {
                try {
                    matrixSetManager.rollback();
                } catch (BaseManagerException re) {
                    namedMatrixSet = null;
                }
            }
            namedMatrixSet = null;
        }
        return namedMatrixSet;
    }
}
