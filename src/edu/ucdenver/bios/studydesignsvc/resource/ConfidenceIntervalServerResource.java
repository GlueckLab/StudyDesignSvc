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
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.manager.ConfidenceIntervalManager;
import edu.ucdenver.bios.webservice.common.domain.ConfidenceIntervalDescription;
import edu.ucdenver.bios.webservice.common.domain.UuidConfidenceIntervalDescription;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Resource class for handling requests for the complete study design object.
 * See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class ConfidenceIntervalServerResource extends ServerResource implements
        ConfidenceIntervalResource {

    /**
     * Retrieve ConfidenceIntervalDescription.
     * 
     * @param uuid
     *            the uuid
     * @return the uuid confidence interval description
     */
    @Get("application/json")
    public final UuidConfidenceIntervalDescription retrieve(final byte[] uuid) {
        ConfidenceIntervalManager confidenceIntervalManager = null;
        UuidConfidenceIntervalDescription uuidConfidence = null;
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
             * Retrieve ConfidenceIntervalDescription.
             */
            confidenceIntervalManager = new ConfidenceIntervalManager();
            confidenceIntervalManager.beginTransaction();
            uuidConfidence = confidenceIntervalManager.retrieve(uuid);
            confidenceIntervalManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (confidenceIntervalManager != null) {
                try {
                    confidenceIntervalManager.rollback();
                } catch (BaseManagerException re) {
                    uuidConfidence = null;
                }
            }
            uuidConfidence = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (confidenceIntervalManager != null) {
                try {
                    confidenceIntervalManager.rollback();
                } catch (BaseManagerException re) {
                    uuidConfidence = null;
                }
            }
            uuidConfidence = null;
        }
        return uuidConfidence;
    }

    /**
     * Creates the ConfidenceIntervalDescription.
     * 
     * @param uuidConfidence
     *            the uuid confidence
     * @return the uuid confidence interval description
     */
    @Post("application/json")
    public final UuidConfidenceIntervalDescription create(
            UuidConfidenceIntervalDescription uuidConfidence) {
        ConfidenceIntervalManager confidenceIntervalManager = null;
        byte[] uuid = uuidConfidence.getUuid();
        /*
         * Check : empty uuid.
         */
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        /*
         * Check : empty ConfidenceIntervalDescription.
         */
        ConfidenceIntervalDescription newConfidence = uuidConfidence
                .getConfidenceInterval();
        if (newConfidence == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no Confidence Interval Description specified");
        }
        try {
            /*
             * Save ConfidenceIntervalDescription.
             */
            confidenceIntervalManager = new ConfidenceIntervalManager();
            confidenceIntervalManager.beginTransaction();
            uuidConfidence = confidenceIntervalManager.saveOrUpdate(
                    uuidConfidence, true);
            confidenceIntervalManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (confidenceIntervalManager != null) {
                try {
                    confidenceIntervalManager.rollback();
                } catch (BaseManagerException re) {
                    uuidConfidence = null;
                }
            }
            uuidConfidence = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (confidenceIntervalManager != null) {
                try {
                    confidenceIntervalManager.rollback();
                } catch (BaseManagerException re) {
                    uuidConfidence = null;
                }
            }
            uuidConfidence = null;
        }
        return uuidConfidence;
    }

    /**
     * Update ConfidenceIntervalDescription.
     * 
     * @param uuidConfidence
     *            the uuid confidence
     * @return the uuid confidence interval description
     */
    @Put("application/json")
    public final UuidConfidenceIntervalDescription update(
            final UuidConfidenceIntervalDescription uuidConfidence) {
        return create(uuidConfidence);
    }

    /**
     * Removes the ConfidenceIntervalDescription.
     * 
     * @param uuid
     *            the uuid
     * @return the uuid confidence interval description
     */
    @Delete("application/json")
    public final UuidConfidenceIntervalDescription remove(final byte[] uuid) {
        ConfidenceIntervalManager confidenceIntervalManager = null;
        UuidConfidenceIntervalDescription uuidConfidence = null;
        /*
         * Check : empty uuid.
         */
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        try {
            /*
             * Delete ConfidenceIntervalDescription.
             */
            confidenceIntervalManager = new ConfidenceIntervalManager();
            confidenceIntervalManager.beginTransaction();
            uuidConfidence = confidenceIntervalManager.delete(uuid);
            confidenceIntervalManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (confidenceIntervalManager != null) {
                try {
                    confidenceIntervalManager.rollback();
                } catch (BaseManagerException re) {
                    uuidConfidence = null;
                }
            }
            uuidConfidence = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (confidenceIntervalManager != null) {
                try {
                    confidenceIntervalManager.rollback();
                } catch (BaseManagerException re) {
                    uuidConfidence = null;
                }
            }
            uuidConfidence = null;
        }
        return uuidConfidence;
    }

}
