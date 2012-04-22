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

import edu.ucdenver.bios.webservice.common.domain.RelativeGroupSize;
import edu.ucdenver.bios.webservice.common.domain.RelativeGroupSizeList;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Manager class which provides CRUD functionality for MySQL table
 * RelativeGroupSize object.
 * 
 * @author Uttara Sakhadeo
 */
public class RelativeGroupSizeManager extends StudyDesignParentManager {

    /**
     * Instantiates a new relative group size manager.
     * 
     * @throws BaseManagerException
     *             the base manager exception
     */
    public RelativeGroupSizeManager() throws BaseManagerException {
        super();
    }

    /**
     * Retrieves the RelativeGroupSizeList.
     * 
     * @param uuid
     *            the uuid
     * @return the relative group size list
     */
    public final RelativeGroupSizeList retrieve(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        RelativeGroupSizeList relativeGroupSizeList = null;
        try {
            /*
             * Retrieve Original RelativeGroupSize Object
             */
            List<RelativeGroupSize> originalList = get(uuid)
                    .getRelativeGroupSizeList();
            /*
             * Delete Existing RelativeGroupSize List Object
             */
            if (originalList != null && !originalList.isEmpty()) {
                relativeGroupSizeList = new RelativeGroupSizeList(uuid,
                        originalList);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete RelativeGroupSize object for UUID '"
                            + uuid + "': " + e.getMessage());
        }
        return relativeGroupSizeList;
    }

    /**
     * Deletes the RelativeGroupSizeList.
     * 
     * @param uuid
     *            the uuid
     * @return the relative group size list
     */
    public final RelativeGroupSizeList delete(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        RelativeGroupSizeList relativeGroupSizeList = null;
        StudyDesign studyDesign = null;
        try {
            /*
             * Retrieve Original RelativeGroupSize Object
             */
            studyDesign = get(uuid);
            List<RelativeGroupSize> originalList = studyDesign
                    .getRelativeGroupSizeList();
            /*
             * Delete Existing RelativeGroupSize List Object
             */
            if (originalList != null && !originalList.isEmpty()) {
                relativeGroupSizeList = delete(uuid, originalList);
            }
            /*
             * Update Study Design Object
             */
            studyDesign.setRelativeGroupSizeList(null);
            session.update(studyDesign);
            /*
             * Return Persisted RelativeGroupSizeList
             */
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete RelativeGroupSize object for UUID '"
                            + uuid + "': " + e.getMessage());
        }
        return relativeGroupSizeList;
    }

    /**
     * Deletes the RelativeGroupSizeList.
     * 
     * @param uuid
     *            the uuid
     * @param relativeGroupSizeList
     *            the relative group size list
     * @return the relative group size list
     */
    private RelativeGroupSizeList delete(final byte[] uuid,
            final List<RelativeGroupSize> relativeGroupSizeList) {
        RelativeGroupSizeList deletedList = null;
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        try {
            for (RelativeGroupSize relativeGroupSize : relativeGroupSizeList) {
                session.delete(relativeGroupSize);
            }
            deletedList = 
                    new RelativeGroupSizeList(uuid, relativeGroupSizeList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete RelativeGroupSize object for UUID '"
                            + uuid + "': " + e.getMessage());
        }
        return deletedList;
    }

    /**
     * Saves or updates the RelativeGroupSizeList.
     * 
     * @param relativeGroupSizeList
     *            the relative group size list
     * @param isCreation
     *            the is creation
     * @return the relative group size list
     */
    public final RelativeGroupSizeList saveOrUpdate(
            final RelativeGroupSizeList relativeGroupSizeList,
            final boolean isCreation) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        StudyDesign studyDesign = null;
        List<RelativeGroupSize> originalList = null;
        RelativeGroupSizeList newGroupSizeList = null;
        byte[] uuid = relativeGroupSizeList.getUuid();
        List<RelativeGroupSize> newList = relativeGroupSizeList
                .getRelativeGroupSizeList();

        try {
            /*
             * Retrieve Study Design Object
             */
            studyDesign = get(uuid);
            originalList = studyDesign.getRelativeGroupSizeList();
            /*
             * Delete Existing RelativeGroupSize List Object
             */
            if (originalList != null && !originalList.isEmpty()) {
                delete(uuid, originalList);
            }
            if (isCreation) {
                for (RelativeGroupSize relativeGroupSize : newList) {
                    session.save(relativeGroupSize);
                    System.out.println("in save id: "
                            + relativeGroupSize.getId());
                }
            } else {
                for (RelativeGroupSize relativeGroupSize : newList) {
                    session.update(relativeGroupSize);
                }
            }
            /*
             * Update Study Design Object
             */
            studyDesign.setRelativeGroupSizeList(newList);
            session.update(studyDesign);
            /*
             * Return Persisted RelativeGroupSizeList
             */
            newGroupSizeList = new RelativeGroupSizeList(uuid, newList);
        } catch (Exception e) {
            newList = null;
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to save RelativeGroupSize object : "
                            + e.getMessage());
        }
        return newGroupSizeList;
    }
}
