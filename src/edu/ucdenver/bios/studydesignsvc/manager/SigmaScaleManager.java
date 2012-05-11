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

import edu.ucdenver.bios.webservice.common.domain.SigmaScale;
import edu.ucdenver.bios.webservice.common.domain.SigmaScaleList;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Manager class which provides CRUD functionality for SigmaScale object.
 * 
 * @author Uttara Sakhadeo
 */
public class SigmaScaleManager extends StudyDesignParentManager {

    /**
     * Instantiates a new sigma scale manager.
     * 
     * @throws BaseManagerException
     *             the base manager exception
     */
    public SigmaScaleManager() throws BaseManagerException {
        super();
    }

    /**
     * Retrieves the SigmaScaleList.
     * 
     * @param uuid
     *            the uuid
     * @return the sigma scale list
     */
    public final SigmaScaleList retrieve(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        SigmaScaleList sigmaScaleList = null;
        try {
            /*
             * Retrieve Study Design for given uuid
             */
            StudyDesign studyDesign = get(uuid);
            if (studyDesign != null) {
                /*
                 * Retrieve Original SigmaScale Object
                 */
                List<SigmaScale> originalList = get(uuid).getSigmaScaleList();
                if (originalList != null && !originalList.isEmpty()) {
                    sigmaScaleList = new SigmaScaleList(uuid, originalList);
                } else {
                    /*
                     * uuid exists but no SigmaScaleList entry present. If uuid
                     * = null too; then it means no entry for this uuid.
                     */
                    sigmaScaleList = new SigmaScaleList(uuid, null);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete SigmaScale object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return sigmaScaleList;
    }

    /**
     * Deletes the SigmaScaleList.
     * 
     * @param uuid
     *            the uuid
     * @return the sigma scale list
     */
    public final SigmaScaleList delete(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        SigmaScaleList sigmaScaleList = null;
        StudyDesign studyDesign = null;
        try {
            /*
             * Retrieve Original SigmaScale Object
             */
            studyDesign = get(uuid);
            if (studyDesign != null) {
                List<SigmaScale> originalList = studyDesign.getSigmaScaleList();
                /*
                 * Delete Existing SigmaScale List Object
                 */
                if (originalList != null && !originalList.isEmpty()) {
                    sigmaScaleList = delete(uuid, originalList);
                }
                /*
                 * Update Study Design Object
                 */
                studyDesign.setSigmaScaleList(null);
                session.update(studyDesign);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete SigmaScale object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        /*
         * Return Deleted SigmaScaleList
         */
        return sigmaScaleList;
    }

    /**
     * Delete the SigmaScaleList.
     * 
     * @param uuid
     *            the uuid
     * @param sigmaScaleList
     *            the beta scale list
     * @return the sigma scale list
     */
    private SigmaScaleList delete(final byte[] uuid,
            final List<SigmaScale> sigmaScaleList) {
        SigmaScaleList deletedList = null;
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        try {
            for (SigmaScale sigmaScale : sigmaScaleList) {
                session.delete(sigmaScale);
            }
            deletedList = new SigmaScaleList(uuid, sigmaScaleList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete SigmaScale object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return deletedList;
    }

    /**
     * Saves or updates the SigmaScaleList.
     * 
     * @param sigmaScaleList
     *            the beta scale list
     * @param isCreation
     *            the is creation
     * @return the sigma scale list
     */
    public final SigmaScaleList saveOrUpdate(
            final SigmaScaleList sigmaScaleList, final boolean isCreation) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        StudyDesign studyDesign = null;
        List<SigmaScale> originalList = null;
        SigmaScaleList newSigmaScaleList = null;
        byte[] uuid = sigmaScaleList.getUuid();
        List<SigmaScale> newList = sigmaScaleList.getSigmaScaleList();

        try {
            /*
             * Retrieve Study Design Object
             */
            studyDesign = get(uuid);
            if (studyDesign != null) {
                originalList = studyDesign.getSigmaScaleList();
                /*
                 * Delete Existing SigmaScale List Object
                 */
                if (originalList != null && !originalList.isEmpty()) {
                    delete(uuid, originalList);
                }
                if (isCreation) {
                    for (SigmaScale sigmaScale : newList) {
                        session.save(sigmaScale);
                        System.out.println("in save id: " + sigmaScale.getId());
                    }
                } else {
                    for (SigmaScale sigmaScale : newList) {
                        session.update(sigmaScale);
                    }
                }
                /*
                 * Update Study Design Object
                 */
                studyDesign.setSigmaScaleList(newList);
                session.update(studyDesign);
                /*
                 * Return Persisted SigmaScaleList
                 */
                newSigmaScaleList = new SigmaScaleList(uuid, newList);
            }
        } catch (Exception e) {
            newList = null;
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to save SigmaScale object : " + e.getMessage());
        }
        return newSigmaScaleList;
    }
}
