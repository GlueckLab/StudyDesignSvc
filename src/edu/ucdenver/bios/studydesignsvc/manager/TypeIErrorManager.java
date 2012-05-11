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
package edu.ucdenver.bios.studydesignsvc.manager;

import java.util.List;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.domain.TypeIError;
import edu.ucdenver.bios.webservice.common.domain.TypeIErrorList;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Manager class which provides CRUD functionality for TypeIError object.
 * 
 * @author Uttara Sakhadeo
 */
public class TypeIErrorManager extends StudyDesignParentManager {

    /**
     * Instantiates a new type i error manager.
     * 
     * @throws BaseManagerException
     *             the base manager exception
     */
    public TypeIErrorManager() throws BaseManagerException {
        super();
    }

    /**
     * Retrieve TypeIErrorList.
     * 
     * @param uuid
     *            the uuid
     * @return the type i error list
     */
    public final TypeIErrorList retrieve(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        TypeIErrorList alphaList = null;
        try {
            /*
             * Retrieve Study Design for given uuid
             */
            StudyDesign studyDesign = get(uuid);
            if (studyDesign != null) {
                /*
                 * Retrieve Original TypeIError Object
                 */
                List<TypeIError> originalList = studyDesign.getAlphaList();
                if (originalList != null && !originalList.isEmpty()) {
                    alphaList = new TypeIErrorList(uuid, originalList);
                } else {
                    /*
                     * uuid exists but no TypeIErrorList entry present. If uuid
                     * = null too; then it means no entry for this uuid.
                     */
                    alphaList = new TypeIErrorList(uuid, null);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete TypeIError object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return alphaList;
    }

    /**
     * Delete TypeIErrorList.
     * 
     * @param uuid
     *            the uuid
     * @return the type i error list
     */
    public final TypeIErrorList delete(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        TypeIErrorList alphaList = null;
        StudyDesign studyDesign = null;
        try {
            /*
             * Retrieve Original TypeIError Object
             */
            studyDesign = get(uuid);
            if (studyDesign != null) {
                List<TypeIError> originalList = studyDesign.getAlphaList();
                /*
                 * Delete Existing TypeIError List Object
                 */
                if (originalList != null && !originalList.isEmpty()) {
                    alphaList = delete(uuid, originalList);
                }
                /*
                 * Update Study Design Object
                 */
                studyDesign.setAlphaList(null);
                session.update(studyDesign);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete TypeIError object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        /*
         * Return Deleted TypeIErrorList
         */
        return alphaList;
    }

    /**
     * Delete TypeIErrorList.
     * 
     * @param uuid
     *            the uuid
     * @param alphaList
     *            the alpha list
     * @return the type i error list
     */
    private TypeIErrorList delete(final byte[] uuid,
            final List<TypeIError> alphaList) {
        TypeIErrorList deletedList = null;
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        try {
            for (TypeIError betaScale : alphaList) {
                session.delete(betaScale);
            }
            deletedList = new TypeIErrorList(uuid, alphaList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete TypeIError object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return deletedList;
    }

    /**
     * Saves or update TypeIErrorList.
     * 
     * @param alphaList
     *            the alpha list
     * @param isCreation
     *            the is creation
     * @return the type i error list
     */
    public final TypeIErrorList saveOrUpdate(final TypeIErrorList alphaList,
            final boolean isCreation) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        StudyDesign studyDesign = null;
        List<TypeIError> originalList = null;
        TypeIErrorList newAlphaList = null;
        byte[] uuid = alphaList.getUuid();
        List<TypeIError> newList = alphaList.getTypeIErrorList();

        try {
            /*
             * Retrieve Study Design Object
             */
            studyDesign = get(uuid);
            if (studyDesign != null) {
                originalList = studyDesign.getAlphaList();
                /*
                 * Delete Existing TypeIError List Object
                 */
                if (originalList != null && !originalList.isEmpty()) {
                    delete(uuid, originalList);
                }
                if (isCreation) {
                    for (TypeIError betaScale : newList) {
                        session.save(betaScale);
                        System.out.println("in save id: " + betaScale.getId());
                    }
                } else {
                    for (TypeIError betaScale : newList) {
                        session.update(betaScale);
                    }
                }
                /*
                 * Update Study Design Object
                 */
                studyDesign.setAlphaList(newList);
                session.update(studyDesign);
                /*
                 * Return Persisted TypeIErrorList
                 */
                newAlphaList = new TypeIErrorList(uuid, newList);
            }
        } catch (Exception e) {
            newList = null;
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to save TypeIError object : " + e.getMessage());
        }
        return newAlphaList;
    }
}
