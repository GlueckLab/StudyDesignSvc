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

import java.util.ArrayList;
import java.util.List;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import edu.ucdenver.bios.webservice.common.domain.SampleSize;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManager;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Manager class which provides CRUD functionality
 * for MySQL table SampleSize object.
 *
 * @author Uttara Sakhadeo
 */
public class SampleSizeManager extends BaseManager {

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
     * Delete a SampleSize object by the specified UUID.
     *
     * @param uuidBytes
     *            the uuid bytes
     * @param sampleSizeList
     *            the sample size list
     * @return ArrayList<SampleSize>
     */
    public List<SampleSize> delete(final byte[] uuidBytes,
            final List<SampleSize> sampleSizeList) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        try {
            for (SampleSize sampleSize : sampleSizeList) {
                session.delete(sampleSize);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete SampleSize object for UUID '" + uuidBytes
                            + "': " + e.getMessage());
        }
        return sampleSizeList;
    }

    /**
     * Retrieve a SampleSize object by the specified UUID.
     *
     * @param sampleSizeList
     *            : ArrayList<SampleSize>
     * @param isCreation
     *            : boolean
     * @return sampleSizeList : ArrayList<SampleSize>
     */
    public ArrayList<SampleSize> saveOrUpdate(
            ArrayList<SampleSize> sampleSizeList, boolean isCreation) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        try {
            if (isCreation) {
                for (SampleSize sampleSize : sampleSizeList) {
                    session.save(sampleSize);
                }
            } else {
                for (SampleSize sampleSize : sampleSizeList) {
                    session.update(sampleSize);
                }
            }
        } catch (Exception e) {
            sampleSizeList = null;
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to save SampleSize object : " + e.getMessage());
        }
        return sampleSizeList;
    }
}
