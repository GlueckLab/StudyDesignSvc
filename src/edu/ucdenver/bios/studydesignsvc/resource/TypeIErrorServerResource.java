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

import java.util.List;
import java.util.regex.Pattern;

import org.restlet.data.Status;
import org.restlet.resource.Delete;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.manager.TypeIErrorManager;
import edu.ucdenver.bios.webservice.common.domain.TypeIError;
import edu.ucdenver.bios.webservice.common.domain.TypeIErrorList;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;

/**
 * Server Resource class for handling (PUT, POST, DELETE) requests for the Type
 * I Error object. See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class TypeIErrorServerResource extends ServerResource implements
        TypeIErrorResource {

    /**
     * Creates the TypeIErrorList.
     * 
     * @param typeIErrorList
     *            the type i error list
     * @return the type i error list
     */
    @Post("application/json")
    public final TypeIErrorList create(TypeIErrorList typeIErrorList) {
        TypeIErrorManager typeIErrorManager = null;
        byte[] uuid = typeIErrorList.getUuid();
        /*
         * Check : empty uuid.
         */
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        /*
         * Check : empty TypeIError list.
         */
        List<TypeIError> list = typeIErrorList.getTypeIErrorList();
        if (list == null || list.isEmpty()) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no Beta Scale specified");
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
             * Save TypeIError list.
             */
            typeIErrorManager = new TypeIErrorManager();
            typeIErrorManager.beginTransaction();
            typeIErrorList = typeIErrorManager.saveOrUpdate(typeIErrorList,
                    true);
            typeIErrorManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (typeIErrorManager != null) {
                try {
                    typeIErrorManager.rollback();
                } catch (BaseManagerException re) {
                    typeIErrorList = null;
                }
            }
            typeIErrorList = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (typeIErrorManager != null) {
                try {
                    typeIErrorManager.rollback();
                } catch (BaseManagerException re) {
                    typeIErrorList = null;
                }
            }
            typeIErrorList = null;
        }
        return typeIErrorList;
    }

    /**
     * Updates the TypeIErrorList.
     * 
     * @param typeIErrorList
     *            the type i error list
     * @return the type i error list
     */
    @Put("application/json")
    public final TypeIErrorList update(final TypeIErrorList typeIErrorList) {
        return create(typeIErrorList);
    }

    /**
     * Removes the TypeIErrorList.
     * 
     * @param uuid
     *            the uuid
     * @return the type i error list
     */
    @Delete("application/json")
    public final TypeIErrorList remove(final byte[] uuid) {
        TypeIErrorManager typeIErrorManager = null;
        TypeIErrorList typeIErrorList = null;
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
             * Delete TypeIError list.
             */
            typeIErrorManager = new TypeIErrorManager();
            typeIErrorManager.beginTransaction();
            typeIErrorList = typeIErrorManager.delete(uuid);
            typeIErrorManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (typeIErrorManager != null) {
                try {
                    typeIErrorManager.rollback();
                } catch (BaseManagerException re) {
                    typeIErrorList = null;
                }
            }
            typeIErrorList = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (typeIErrorManager != null) {
                try {
                    typeIErrorManager.rollback();
                } catch (BaseManagerException re) {
                    typeIErrorList = null;
                }
            }
            typeIErrorList = null;
        }
        return typeIErrorList;
    }

}
