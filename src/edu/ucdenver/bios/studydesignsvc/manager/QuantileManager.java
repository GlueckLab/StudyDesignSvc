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

import edu.ucdenver.bios.webservice.common.domain.Quantile;
import edu.ucdenver.bios.webservice.common.domain.QuantileList;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Manager class which provides CRUD functionality for MySQL table Quantile
 * object.
 * 
 * @author Uttara Sakhadeo
 */
public class QuantileManager extends StudyDesignParentManager {

    /**
     * Instantiates a new quantile manager.
     * 
     * @throws BaseManagerException
     *             the base manager exception
     */
    public QuantileManager() throws BaseManagerException {
        super();
    }

    /**
     * Retrieves the QuantileList.
     * 
     * @param uuid
     *            the uuid
     * @return the quantile list
     */
    public final QuantileList retrieve(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        QuantileList quantileList = null;
        try {
            /*
             * Retrieve Original Quantile Object
             */
            List<Quantile> originalList = get(uuid).getQuantileList();
            /*
             * Delete Existing Quantile List Object
             */
            if (originalList != null && !originalList.isEmpty()) {
                quantileList = new QuantileList(uuid, originalList);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete Quantile object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return quantileList;
    }

    /**
     * Deletes the QuantileList.
     * 
     * @param uuid
     *            the uuid
     * @return the quantile list
     */
    public final QuantileList delete(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        QuantileList quantileList = null;
        StudyDesign studyDesign = null;
        try {
            /*
             * Retrieve Original Quantile Object
             */
            studyDesign = get(uuid);
            List<Quantile> originalList = studyDesign.getQuantileList();
            /*
             * Delete Existing Quantile List Object
             */
            if (originalList != null && !originalList.isEmpty()) {
                quantileList = delete(uuid, originalList);
            }
            /*
             * Update Study Design Object
             */
            studyDesign.setQuantileList(null);
            session.update(studyDesign);
            /*
             * Return Persisted QuantileList
             */
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete Quantile object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return quantileList;
    }

    /**
     * Deletes the QuantileList.
     * 
     * @param uuid
     *            the uuid
     * @param quantileList
     *            the quantile list
     * @return the quantile list
     */
    private QuantileList delete(final byte[] uuid,
            final List<Quantile> quantileList) {
        QuantileList deletedList = null;
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        try {
            for (Quantile quantile : quantileList) {
                session.delete(quantile);
            }
            deletedList = new QuantileList(uuid, quantileList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete Quantile object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return deletedList;
    }

    /**
     * Saves or updates the QuantileList.
     * 
     * @param quantileList
     *            the quantile list
     * @param isCreation
     *            the is creation
     * @return the quantile list
     */
    public final QuantileList saveOrUpdate(final QuantileList quantileList,
            final boolean isCreation) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        StudyDesign studyDesign = null;
        List<Quantile> originalList = null;
        QuantileList newQuantileList = null;
        byte[] uuid = quantileList.getUuid();
        List<Quantile> newList = quantileList.getQuantileList();

        try {
            /*
             * Retrieve Study Design Object
             */
            studyDesign = get(uuid);
            originalList = studyDesign.getQuantileList();
            /*
             * Delete Existing Quantile List Object
             */
            if (originalList != null && !originalList.isEmpty()) {
                delete(uuid, originalList);
            }
            if (isCreation) {
                for (Quantile quantile : newList) {
                    session.save(quantile);
                    System.out.println("in save id: " + quantile.getId());
                }
            } else {
                for (Quantile quantile : newList) {
                    session.update(quantile);
                }
            }
            /*
             * Update Study Design Object
             */
            studyDesign.setQuantileList(newList);
            session.update(studyDesign);
            /*
             * Return Persisted QuantileList
             */
            newQuantileList = new QuantileList(uuid, newList);
        } catch (Exception e) {
            newList = null;
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to save Quantile object : " + e.getMessage());
        }
        return newQuantileList;
    }
}
