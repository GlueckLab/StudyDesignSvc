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
package edu.ucdenver.bios.studydesignsvc.resource;

import java.util.List;

import org.restlet.data.Status;
import org.restlet.resource.Delete;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.manager.RepeatedMeasuresManager;
import edu.ucdenver.bios.webservice.common.domain.RepeatedMeasuresNode;
import edu.ucdenver.bios.webservice.common.domain.RepeatedMeasuresNodeList;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Server Resource class for handling (PUT, POST, DELETE) requests for the
 * RepeatedMeasuresNode object. See the StudyDesignApplication class for URI
 * mappings
 * 
 * @author Uttara Sakhadeo
 */
public class RepeatedMeasuresServerResource extends ServerResource implements
        RepeatedMeasuresResource {
    
    /**
     * Creates the RepeatedMeasuresNodeList.
     * 
     * @param repeatedMeasuresNodeList
     *            the repeated measures node list
     * @return the repeated measures node list
     */
    @Post("application/json")
    public final RepeatedMeasuresNodeList create(
            RepeatedMeasuresNodeList repeatedMeasuresNodeList) {
        RepeatedMeasuresManager repeatedMeasuresManager = null;
        byte[] uuid = repeatedMeasuresNodeList.getUuid();
        /*
         * Check : empty uuid.
         */
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        /*
         * Check : empty RepeatedMeasuresNode list.
         */
        List<RepeatedMeasuresNode> list = repeatedMeasuresNodeList
                .getRepeatedMeasuresList();
        if (list == null || list.isEmpty()) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no Beta Scale specified");
        }
        try {
            /*
             * Save RepeatedMeasuresNode list.
             */
            repeatedMeasuresManager = new RepeatedMeasuresManager();
            repeatedMeasuresManager.beginTransaction();
            repeatedMeasuresNodeList = repeatedMeasuresManager.saveOrUpdate(
                    repeatedMeasuresNodeList, true);
            repeatedMeasuresManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (repeatedMeasuresManager != null) {
                try {
                    repeatedMeasuresManager.rollback();
                } catch (BaseManagerException re) {
                    repeatedMeasuresNodeList = null;
                }
            }
            repeatedMeasuresNodeList = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (repeatedMeasuresManager != null) {
                try {
                    repeatedMeasuresManager.rollback();
                } catch (BaseManagerException re) {
                    repeatedMeasuresNodeList = null;
                }
            }
            repeatedMeasuresNodeList = null;
        }
        return repeatedMeasuresNodeList;
    }

    /**
     * Update the RepeatedMeasuresNodeList.
     * 
     * @param repeatedMeasuresNodeList
     *            the repeated measures node list
     * @return the repeated measures node list
     */
    @Put("application/json")
    public final RepeatedMeasuresNodeList update(
            final RepeatedMeasuresNodeList repeatedMeasuresNodeList) {
        return create(repeatedMeasuresNodeList);
    }

    /**
     * Removes the RepeatedMeasuresNodeList.
     * 
     * @param uuid
     *            the uuid
     * @return the repeated measures node list
     */
    @Delete("application/json")
    public final RepeatedMeasuresNodeList remove(final byte[] uuid) {
        RepeatedMeasuresManager repeatedMeasuresManager = null;
        RepeatedMeasuresNodeList repeatedMeasuresNodeList = null;
        /*
         * Check : empty uuid.
         */
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        try {
            /*
             * Delete RepeatedMeasuresNode list.
             */
            repeatedMeasuresManager = new RepeatedMeasuresManager();
            repeatedMeasuresManager.beginTransaction();
            repeatedMeasuresNodeList = repeatedMeasuresManager.delete(uuid);
            repeatedMeasuresManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (repeatedMeasuresManager != null) {
                try {
                    repeatedMeasuresManager.rollback();
                } catch (BaseManagerException re) {
                    repeatedMeasuresNodeList = null;
                }
            }
            repeatedMeasuresNodeList = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (repeatedMeasuresManager != null) {
                try {
                    repeatedMeasuresManager.rollback();
                } catch (BaseManagerException re) {
                    repeatedMeasuresNodeList = null;
                }
            }
            repeatedMeasuresNodeList = null;
        }
        return repeatedMeasuresNodeList;
    }
}
