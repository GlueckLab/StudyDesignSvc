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

import edu.ucdenver.bios.webservice.common.domain.SampleSize;
import edu.ucdenver.bios.webservice.common.domain.SampleSizeList;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Manager class which provides CRUD functionality for MySQL table SampleSize
 * object.
 * 
 * @author Uttara Sakhadeo
 */
public class SampleSizeManager extends StudyDesignParentManager {

    /**
     * Instantiates a new sample size manager.
     * 
     * @throws BaseManagerException
     *             the base manager exception
     */
    public SampleSizeManager() throws BaseManagerException {
        super();
    }

    /**
     * Retrieves the SampleSizeList.
     * 
     * @param uuid
     *            the uuid
     * @return the sample size list
     */
    public final SampleSizeList retrieve(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        SampleSizeList sampleSizeList = null;
        try {
            /*
             * Retrieve Original SampleSize Object
             */
            List<SampleSize> originalList = get(uuid).getSampleSizeList();
            /*
             * Delete Existing SampleSize List Object
             */
            if (originalList != null && !originalList.isEmpty()) {
                sampleSizeList = new SampleSizeList(uuid, originalList);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete SampleSize object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return sampleSizeList;
    }

    /**
     * Deletes the SampleSizeList.
     * 
     * @param uuid
     *            the uuid
     * @return the sample size list
     */
    public final SampleSizeList delete(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        SampleSizeList sampleSizeList = null;
        StudyDesign studyDesign = null;
        try {
            /*
             * Retrieve Original SampleSize Object
             */
            studyDesign = get(uuid);
            List<SampleSize> originalList = studyDesign.getSampleSizeList();
            /*
             * Delete Existing SampleSize List Object
             */
            if (originalList != null && !originalList.isEmpty()) {
                sampleSizeList = delete(uuid, originalList);
            }
            /*
             * Update Study Design Object
             */
            studyDesign.setSampleSizeList(null);
            session.update(studyDesign);
            /*
             * Return Persisted SampleSizeList
             */
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete SampleSize object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return sampleSizeList;
    }

    /**
     * Deletes the SampleSizeList.
     * 
     * @param uuid
     *            the uuid
     * @param sampleSizeList
     *            the sample size list
     * @return the sample size list
     */
    private SampleSizeList delete(final byte[] uuid,
            final List<SampleSize> sampleSizeList) {
        SampleSizeList deletedList = null;
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        try {
            for (SampleSize sampleSize : sampleSizeList) {
                session.delete(sampleSize);
            }
            deletedList = new SampleSizeList(uuid, sampleSizeList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete SampleSize object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return deletedList;
    }

    /**
     * Saves or updates the SampleSizeList.
     * 
     * @param sampleSizeList
     *            the sample size list
     * @param isCreation
     *            the is creation
     * @return the sample size list
     */
    public final SampleSizeList saveOrUpdate(
            final SampleSizeList sampleSizeList, final boolean isCreation) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        StudyDesign studyDesign = null;
        List<SampleSize> originalList = null;
        SampleSizeList newSampleSizeList = null;
        byte[] uuid = sampleSizeList.getUuid();
        List<SampleSize> newList = sampleSizeList.getSampleSizeList();

        try {
            /*
             * Retrieve Study Design Object
             */
            studyDesign = get(uuid);
            originalList = studyDesign.getSampleSizeList();
            /*
             * Delete Existing SampleSize List Object
             */
            if (originalList != null && !originalList.isEmpty()) {
                delete(uuid, originalList);
            }
            if (isCreation) {
                for (SampleSize sampleSize : newList) {
                    session.save(sampleSize);
                    System.out.println("in save id: " + sampleSize.getId());
                }
            } else {
                for (SampleSize sampleSize : newList) {
                    session.update(sampleSize);
                }
            }
            /*
             * Update Study Design Object
             */
            studyDesign.setSampleSizeList(newList);
            session.update(studyDesign);
            /*
             * Return Persisted SampleSizeList
             */
            newSampleSizeList = new SampleSizeList(uuid, newList);
        } catch (Exception e) {
            newList = null;
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to save SampleSize object : " + e.getMessage());
        }
        return newSampleSizeList;
    }
}
