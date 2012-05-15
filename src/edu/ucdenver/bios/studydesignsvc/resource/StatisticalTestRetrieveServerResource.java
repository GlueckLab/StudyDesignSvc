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
package edu.ucdenver.bios.studydesignsvc.resource;

import org.restlet.data.Status;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.manager.StatisticalTestManager;
import edu.ucdenver.bios.webservice.common.domain.StatisticalTestList;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

public class StatisticalTestRetrieveServerResource extends ServerResource implements
StatisticalTestRetrieveResource{
    
    /**
     * Retrieve the StatisticalTestList.
     * 
     * @param uuid
     *            the uuid
     * @return the statistical test list
     */
    @Post("application/json")
    public final StatisticalTestList retrieve(final byte[] uuid) {
        StatisticalTestManager statisticalTestManager = null;
        StatisticalTestList statisticalTestList = null;
        /*
         * Check : empty uuid.
         */
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        /*
         * Check : length of uuid.
         */

        try {
            /*
             * Retrieve StatisticalTest list.
             */
            statisticalTestManager = new StatisticalTestManager();
            statisticalTestManager.beginTransaction();
            statisticalTestList = statisticalTestManager.retrieve(uuid);
            statisticalTestManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (statisticalTestManager != null) {
                try {
                    statisticalTestManager.rollback();
                } catch (BaseManagerException re) {
                    statisticalTestList = null;
                }
            }
            statisticalTestList = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (statisticalTestManager != null) {
                try {
                    statisticalTestManager.rollback();
                } catch (BaseManagerException re) {
                    statisticalTestList = null;
                }
            }
            statisticalTestList = null;
        }
        return statisticalTestList;
    }

}
