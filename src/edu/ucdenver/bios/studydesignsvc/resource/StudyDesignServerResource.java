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
package edu.ucdenver.bios.studydesignsvc.resource;

import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.restlet.data.Status;
import org.restlet.resource.Delete;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;

/**
 * Server Resource class for handling (PUT, POST, DELETE) requests for the
 * complete study design object. See the StudyDesignApplication class for URI
 * mappings
 * 
 * @author Uttara Sakhadeo
 */
public class StudyDesignServerResource extends ServerResource implements
        StudyDesignResource {
    static Logger logger = StudyDesignLogger.getInstance();

    /**
     * Update the specified study design object. If the UUID of the study design
     * is not set , the design will be treated as new and a UUID assigned.
     * 
     * @param studyDesign
     *            study design object
     * @return the study design object
     */
    @Put("application/json")
    public StudyDesign update(StudyDesign studyDesign) {
        StudyDesignManager studyDesignManager = null;
        byte[] uuid = studyDesign.getUuid();
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
             * Update Study Design.
             */
            studyDesignManager = new StudyDesignManager();
            studyDesignManager.beginTransaction();
            studyDesign = studyDesignManager.saveOrUpdate(studyDesign, true);
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

    /**
     * Delete the study with the specified UUID.
     * 
     * @param uuid
     *            the uuid of the study to remove
     * @return the deleted study design object
     */
    @Delete("application/json")
    public StudyDesign remove(byte[] uuid) {
        StudyDesignManager studyDesignManager = null;
        StudyDesign studyDesign = null;
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
             * Delete Study Design.
             */
            studyDesignManager = new StudyDesignManager();
            studyDesignManager.beginTransaction();
            studyDesign = studyDesignManager.delete(uuid);
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

    /**
     * Store the study design to the database. This routine will create a new
     * UUID for the study design. And will return back an empty StudyDesign
     * 
     * @return study design object with updated UUID.
     */
    @Post("application/json")
    public StudyDesign create() {
        StudyDesignManager studyDesignManager = null;
        StudyDesign studyDesign = null;

        try {
            studyDesignManager = new StudyDesignManager();
            studyDesignManager.beginTransaction();
            studyDesign = studyDesignManager.create();
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
