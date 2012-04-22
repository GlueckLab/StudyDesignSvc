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

import edu.ucdenver.bios.webservice.common.domain.StatisticalTest;
import edu.ucdenver.bios.webservice.common.domain.StatisticalTestList;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Manager class which provides CRUD functionality for MySQL table
 * StatisticalTest object.
 * 
 * @author Uttara Sakhadeo
 */
public class StatisticalTestManager extends StudyDesignParentManager {

    /**
     * Instantiates a new statistical test manager.
     * 
     * @throws BaseManagerException
     *             the base manager exception
     */
    public StatisticalTestManager() throws BaseManagerException {
        super();
    }

    /**
     * Retrieves the StatisticalTestList.
     * 
     * @param uuid
     *            the uuid
     * @return the statistical test list
     */
    public final StatisticalTestList retrieve(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        StatisticalTestList statisticalTestList = null;
        try {
            /*
             * Retrieve Original StatisticalTest Object
             */
            List<StatisticalTest> originalList = get(uuid)
                    .getStatisticalTestList();
            /*
             * Delete Existing StatisticalTest List Object
             */
            if (originalList != null && !originalList.isEmpty()) {
                statisticalTestList = new StatisticalTestList(uuid,
                        originalList);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete StatisticalTest object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return statisticalTestList;
    }

    /**
     * Deletes the StatisticalTestList.
     * 
     * @param uuid
     *            the uuid
     * @return the statistical test list
     */
    public final StatisticalTestList delete(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        StatisticalTestList statisticalTestList = null;
        StudyDesign studyDesign = null;
        try {
            /*
             * Retrieve Original StatisticalTest Object
             */
            studyDesign = get(uuid);
            List<StatisticalTest> originalList = studyDesign
                    .getStatisticalTestList();
            /*
             * Delete Existing StatisticalTest List Object
             */
            if (originalList != null && !originalList.isEmpty()) {
                statisticalTestList = delete(uuid, originalList);
            }
            /*
             * Update Study Design Object
             */
            studyDesign.setStatisticalTestList(null);
            session.update(studyDesign);
            /*
             * Return Persisted StatisticalTestList
             */
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete StatisticalTest object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return statisticalTestList;
    }

    /**
     * Deletes the StatisticalTestList.
     * 
     * @param uuid
     *            the uuid
     * @param statisticalTestList
     *            the statistical test list
     * @return the statistical test list
     */
    private StatisticalTestList delete(final byte[] uuid,
            final List<StatisticalTest> statisticalTestList) {
        StatisticalTestList deletedList = null;
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        try {
            for (StatisticalTest statisticalTest : statisticalTestList) {
                session.delete(statisticalTest);
            }
            deletedList = new StatisticalTestList(uuid, statisticalTestList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete StatisticalTest object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return deletedList;
    }

    /**
     * Saves or updates the StatisticalTestList.
     * 
     * @param statisticalTestList
     *            the statistical test list
     * @param isCreation
     *            the is creation
     * @return the statistical test list
     */
    public final StatisticalTestList saveOrUpdate(
            final StatisticalTestList statisticalTestList,
            final boolean isCreation) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        StudyDesign studyDesign = null;
        List<StatisticalTest> originalList = null;
        StatisticalTestList newStatisticalTestList = null;
        byte[] uuid = statisticalTestList.getUuid();
        List<StatisticalTest> newList = statisticalTestList
                .getStatisticalTestList();

        try {
            /*
             * Retrieve Study Design Object
             */
            studyDesign = get(uuid);
            originalList = studyDesign.getStatisticalTestList();
            /*
             * Delete Existing StatisticalTest List Object
             */
            if (originalList != null && !originalList.isEmpty()) {
                delete(uuid, originalList);
            }
            if (isCreation) {
                for (StatisticalTest statisticalTest : newList) {
                    session.save(statisticalTest);
                    System.out
                            .println("in save id: " + statisticalTest.getId());
                }
            } else {
                for (StatisticalTest statisticalTest : newList) {
                    session.update(statisticalTest);
                }
            }
            /*
             * Update Study Design Object
             */
            studyDesign.setStatisticalTestList(newList);
            session.update(studyDesign);
            /*
             * Return Persisted StatisticalTestList
             */
            newStatisticalTestList = new StatisticalTestList(uuid, newList);
        } catch (Exception e) {
            newList = null;
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                "Failed to save StatisticalTest object : " + e.getMessage());
        }
        return newStatisticalTestList;
    }
}
