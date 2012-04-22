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

import edu.ucdenver.bios.webservice.common.domain.PowerMethod;
import edu.ucdenver.bios.webservice.common.domain.PowerMethodList;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Manager class which provides CRUD functionality for MySQL table Power Method
 * object.
 * 
 * @author Uttara Sakhadeo
 */
public class PowerMethodManager extends StudyDesignParentManager {

    /**
     * Instantiates a new power method manager.
     * 
     * @throws BaseManagerException
     *             the base manager exception
     */
    public PowerMethodManager() throws BaseManagerException {
        super();
    }

    /**
     * Retrieve.
     * 
     * @param uuid
     *            the uuid
     * @return the power method list
     */
    public final PowerMethodList retrieve(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        PowerMethodList powerMethodList = null;
        try {
            /*
             * Retrieve Original PowerMethod Object
             */
            List<PowerMethod> originalList = get(uuid).getPowerMethodList();
            /*
             * Delete Existing PowerMethod List Object
             */
            if (originalList != null && !originalList.isEmpty()) {
                powerMethodList = new PowerMethodList(uuid, originalList);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete PowerMethod object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return powerMethodList;
    }

    /**
     * Delete.
     * 
     * @param uuid
     *            the uuid
     * @return the power method list
     */
    public final PowerMethodList delete(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        PowerMethodList powerMethodList = null;
        StudyDesign studyDesign = null;
        try {
            /*
             * Retrieve Original PowerMethod Object
             */
            studyDesign = get(uuid);
            List<PowerMethod> originalList = studyDesign.getPowerMethodList();
            /*
             * Delete Existing PowerMethod List Object
             */
            if (originalList != null && !originalList.isEmpty()) {
                powerMethodList = delete(uuid, originalList);
            }
            /*
             * Update Study Design Object
             */
            studyDesign.setPowerMethodList(null);
            session.update(studyDesign);
            /*
             * Return Persisted PowerMethodList
             */
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete PowerMethod object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return powerMethodList;
    }

    /**
     * Delete.
     * 
     * @param uuid
     *            the uuid
     * @param powerMethodList
     *            the power method list
     * @return the power method list
     */
    private PowerMethodList delete(final byte[] uuid,
            final List<PowerMethod> powerMethodList) {
        PowerMethodList deletedList = null;
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        try {
            for (PowerMethod powerMethod : powerMethodList) {
                session.delete(powerMethod);
            }
            deletedList = new PowerMethodList(uuid, powerMethodList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete PowerMethod object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return deletedList;
    }

    /**
     * Save or update.
     * 
     * @param powerMethodList
     *            the power method list
     * @param isCreation
     *            the is creation
     * @return the power method list
     */
    public final PowerMethodList saveOrUpdate(
            final PowerMethodList powerMethodList, final boolean isCreation) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        StudyDesign studyDesign = null;
        List<PowerMethod> originalList = null;
        PowerMethodList newPowerMethodList = null;
        byte[] uuid = powerMethodList.getUuid();
        List<PowerMethod> newList = powerMethodList.getPowerMethodList();

        try {
            /*
             * Retrieve Study Design Object
             */
            studyDesign = get(uuid);
            originalList = studyDesign.getPowerMethodList();
            /*
             * Delete Existing PowerMethod List Object
             */
            if (originalList != null && !originalList.isEmpty()) {
                delete(uuid, originalList);
            }
            if (isCreation) {
                for (PowerMethod powerMethod : newList) {
                    session.save(powerMethod);
                    System.out.println("in save id: " + powerMethod.getId());
                }
            } else {
                for (PowerMethod powerMethod : newList) {
                    session.update(powerMethod);
                }
            }
            /*
             * Update Study Design Object
             */
            studyDesign.setPowerMethodList(newList);
            session.update(studyDesign);
            /*
             * Return Persisted PowerMethodList
             */
            newPowerMethodList = new PowerMethodList(uuid, newList);
        } catch (Exception e) {
            newList = null;
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to save PowerMethod object : " + e.getMessage());
        }
        return newPowerMethodList;
    }
}
