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

import edu.ucdenver.bios.webservice.common.domain.NominalPower;
import edu.ucdenver.bios.webservice.common.domain.NominalPowerList;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Manager class which provides CRUD functionality for NominalPower object.
 * 
 * @author Uttara Sakhadeo
 */
public class NominalPowerManager extends StudyDesignParentManager {

    /**
     * Instantiates a new nominal power manager.
     * 
     * @throws BaseManagerException
     *             the base manager exception
     */
    public NominalPowerManager() throws BaseManagerException {
        super();
    }

    /**
     * Retrieve.
     * 
     * @param uuid
     *            the uuid
     * @return the nominal power list
     */
    public final NominalPowerList retrieve(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        NominalPowerList nominalPowerList = null;
        try {
            /*
             * Retrieve Study Design for given uuid
             */
            StudyDesign studyDesign = get(uuid);
            if (studyDesign != null) {
                /*
                 * Retrieve Original NominalPower Object
                 */
                List<NominalPower> originalList = get(uuid)
                        .getNominalPowerList();
                if (originalList != null && !originalList.isEmpty()) {
                    nominalPowerList = new NominalPowerList(uuid, originalList);
                } else {
                    /*
                     * uuid exists but no NominalPowerList entry present. If
                     * uuid = null too; then it means no entry for this uuid.
                     */
                    nominalPowerList = new NominalPowerList(uuid, null);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete NominalPower object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return nominalPowerList;
    }

    /**
     * Delete.
     * 
     * @param uuid
     *            the uuid
     * @return the nominal power list
     */
    public final NominalPowerList delete(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        NominalPowerList nominalPowerList = null;
        StudyDesign studyDesign = null;
        try {
            /*
             * Retrieve Original NominalPower Object
             */
            studyDesign = get(uuid);
            if (studyDesign != null) {
                List<NominalPower> originalList = studyDesign
                        .getNominalPowerList();
                /*
                 * Delete Existing NominalPower List Object
                 */
                if (originalList != null && !originalList.isEmpty()) {
                    nominalPowerList = delete(uuid, originalList);
                }
                /*
                 * Update Study Design Object
                 */
                studyDesign.setNominalPowerList(null);
                session.update(studyDesign);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete NominalPower object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        /*
         * Return Deleted NominalPowerList
         */
        return nominalPowerList;
    }

    /**
     * Delete.
     * 
     * @param uuid
     *            the uuid
     * @param nominalPowerList
     *            the nominal power list
     * @return the nominal power list
     */
    private NominalPowerList delete(final byte[] uuid,
            final List<NominalPower> nominalPowerList) {
        NominalPowerList deletedList = null;
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        try {
            for (NominalPower nominalPower : nominalPowerList) {
                session.delete(nominalPower);
            }
            deletedList = new NominalPowerList(uuid, nominalPowerList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete NominalPower object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return deletedList;
    }

    /**
     * Save or update.
     * 
     * @param nominalPowerList
     *            the nominal power list
     * @param isCreation
     *            the is creation
     * @return the nominal power list
     */
    public final NominalPowerList saveOrUpdate(
            final NominalPowerList nominalPowerList, final boolean isCreation) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        StudyDesign studyDesign = null;
        List<NominalPower> originalList = null;
        NominalPowerList newNominalPList = null;
        byte[] uuid = nominalPowerList.getUuid();
        List<NominalPower> newList = nominalPowerList.getNominalPowerList();

        try {
            /*
             * Retrieve Study Design Object
             */
            studyDesign = get(uuid);
            if (studyDesign != null) {
                originalList = studyDesign.getNominalPowerList();
                /*
                 * Delete Existing NominalPower List Object
                 */
                if (originalList != null && !originalList.isEmpty()) {
                    delete(uuid, originalList);
                }
                if (isCreation) {
                    for (NominalPower nominalPower : newList) {
                        session.save(nominalPower);
                        System.out.println("in save id: "
                                + nominalPower.getId());
                    }
                } else {
                    for (NominalPower nominalPower : newList) {
                        session.update(nominalPower);
                    }
                }
                /*
                 * Update Study Design Object
                 */
                studyDesign.setNominalPowerList(newList);
                session.update(studyDesign);
                /*
                 * Return Persisted NominalPowerList
                 */
                newNominalPList = new NominalPowerList(uuid, newList);
            }
        } catch (Exception e) {
            newList = null;
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to save NominalPower object : " + e.getMessage());
        }
        return newNominalPList;
    }
}
