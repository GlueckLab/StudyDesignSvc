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

import java.util.List;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import edu.ucdenver.bios.webservice.common.domain.ResponseList;
import edu.ucdenver.bios.webservice.common.domain.ResponseNode;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Manager class which provides CRUD functionality for Responses object.
 * 
 * @author Uttara Sakhadeo
 */
public class ResponsesManager extends StudyDesignParentManager {

    /**
     * Instantiates a new responses manager.
     * 
     * @throws BaseManagerException
     *             the base manager exception
     */
    public ResponsesManager() throws BaseManagerException {
        super();
    }

    /**
     * Retrieves the ResponseList.
     * 
     * @param uuid
     *            the uuid
     * @return the response list
     */
    public final ResponseList retrieve(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        ResponseList responseList = null;
        try {
            /*
             * Retrieve Study Design for given uuid
             */
            StudyDesign studyDesign = get(uuid);
            /*
             * Retrieve Original ResponseList Object
             */
            if (studyDesign != null) {
                List<ResponseNode> originalList = studyDesign.getResponseList();
                if (originalList != null && !originalList.isEmpty()) {
                    responseList = new ResponseList(uuid, originalList);
                } else {
                    /*
                     * uuid exists but no ResponseList entry present. If uuid =
                     * null too; then it means no entry for this uuid.
                     */
                    responseList = new ResponseList(uuid, null);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete ResponseNode object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return responseList;
    }

    /**
     * Deletes the ResponseList.
     * 
     * @param uuid
     *            the uuid
     * @return the response list
     */
    public final ResponseList delete(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        ResponseList responseList = null;
        StudyDesign studyDesign = null;
        try {
            /*
             * Retrieve Original ResponseList Object
             */
            studyDesign = get(uuid);
            if (studyDesign != null) {
                List<ResponseNode> originalList = studyDesign.getResponseList();
                /*
                 * Delete Existing ResponseList List Object
                 */
                if (originalList != null && !originalList.isEmpty()) {
                    responseList = delete(uuid, originalList);
                }
                /*
                 * Update Study Design Object
                 */
                studyDesign.setResponseList(null);
                session.update(studyDesign);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete ResponseNode object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        /*
         * Return ResponseList
         */
        return responseList;
    }

    /**
     * Deletes the ResponseList.
     * 
     * @param uuid
     *            the uuid
     * @param responseList
     *            the response list
     * @return the response list
     */
    private ResponseList delete(final byte[] uuid,
            final List<ResponseNode> responseList) {
        ResponseList deletedList = null;
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        try {
            if (responseList != null && !responseList.isEmpty())
                for (ResponseNode response : responseList) {
                    session.delete(response);
                }
            deletedList = new ResponseList(uuid, responseList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete ResponseNode object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return deletedList;
    }

    /**
     * Saves or updates the ResponseList.
     * 
     * @param responseList
     *            the response list
     * @param isCreation
     *            the is creation
     * @return the response list
     */
    public final ResponseList saveOrUpdate(final ResponseList responseList,
            final boolean isCreation) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        StudyDesign studyDesign = null;
        List<ResponseNode> originalList = null;
        ResponseList newResponseList = null;
        byte[] uuid = responseList.getUuid();
        List<ResponseNode> newList = responseList.getResponseNodeList();

        try {
            /*
             * Retrieve Study Design Object
             */
            studyDesign = get(uuid);
            if (studyDesign != null) {
                originalList = studyDesign.getResponseList();
                /*
                 * Delete Existing ResponseList List Object
                 */
                if (originalList != null && !originalList.isEmpty()) {
                    delete(uuid, originalList);
                }
                if (isCreation) {
                    for (ResponseNode response : newList) {
                        session.save(response);
                        System.out.println("in save id: " + response.getId());
                    }
                } else {
                    for (ResponseNode response : newList) {
                        session.update(response);
                    }
                }
                /*
                 * Update Study Design Object
                 */
                studyDesign.setResponseList(newList);
                session.update(studyDesign);
                /*
                 * Return Persisted ResponseList
                 */
                newResponseList = new ResponseList(uuid, newList);
            }
        } catch (Exception e) {
            newList = null;
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to save ResponseNode object : " + e.getMessage());
        }
        return newResponseList;
    }
}
