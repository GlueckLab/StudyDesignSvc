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
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.manager.SigmaScaleManager;
import edu.ucdenver.bios.webservice.common.domain.SigmaScale;
import edu.ucdenver.bios.webservice.common.domain.SigmaScaleList;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

/**
 * Server Resource class for handling requests for the Sigma Scale object. See
 * the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class SigmaScaleServerResource extends ServerResource implements
        SigmaScaleResource {
    /**
     * Retrieves the SigmaScaleList.
     * 
     * @param uuid
     *            the uuid
     * @return the sigma scale list
     */
    @Get("application/json")
    public final SigmaScaleList retrieve(final byte[] uuid) {
        SigmaScaleManager sigmaScaleManager = null;
        SigmaScaleList sigmaScaleList = null;
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
             * Delete SigmaScale list.
             */
            sigmaScaleManager = new SigmaScaleManager();
            sigmaScaleManager.beginTransaction();
            sigmaScaleList = sigmaScaleManager.retrieve(uuid);
            sigmaScaleManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (sigmaScaleManager != null) {
                try {
                    sigmaScaleManager.rollback();
                } catch (BaseManagerException re) {
                    sigmaScaleList = null;
                }
            }
            sigmaScaleList = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (sigmaScaleManager != null) {
                try {
                    sigmaScaleManager.rollback();
                } catch (BaseManagerException re) {
                    sigmaScaleList = null;
                }
            }
            sigmaScaleList = null;
        }
        return sigmaScaleList;
    }

    /**
     * Creates the SigmaScaleList.
     * 
     * @param sigmaScaleList
     *            the sigma scale list
     * @return the sigma scale list
     */
    @Post("application/json")
    public final SigmaScaleList create(SigmaScaleList sigmaScaleList) {
        SigmaScaleManager sigmaScaleManager = null;
        byte[] uuid = sigmaScaleList.getUuid();
        /*
         * Check : empty uuid.
         */
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        /*
         * Check : empty SigmaScale list.
         */
        List<SigmaScale> list = sigmaScaleList.getSigmaScaleList();
        if (list == null || list.isEmpty()) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no Beta Scale specified");
        }
        try {
            /*
             * Save SigmaScale list.
             */
            sigmaScaleManager = new SigmaScaleManager();
            sigmaScaleManager.beginTransaction();
            sigmaScaleList = sigmaScaleManager.saveOrUpdate(sigmaScaleList,
                    true);
            sigmaScaleManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (sigmaScaleManager != null) {
                try {
                    sigmaScaleManager.rollback();
                } catch (BaseManagerException re) {
                    sigmaScaleList = null;
                }
            }
            sigmaScaleList = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (sigmaScaleManager != null) {
                try {
                    sigmaScaleManager.rollback();
                } catch (BaseManagerException re) {
                    sigmaScaleList = null;
                }
            }
            sigmaScaleList = null;
        }
        return sigmaScaleList;
    }

    /**
     * Updates the SigmaScaleList.
     * 
     * @param sigmaScaleList
     *            the sigma scale list
     * @return the sigma scale list
     */
    @Put("application/json")
    public final SigmaScaleList update(final SigmaScaleList sigmaScaleList) {
        return create(sigmaScaleList);
    }

    /**
     * Removes the SigmaScaleList.
     * 
     * @param uuid
     *            the uuid
     * @return the sigma scale list
     */
    @Delete("application/json")
    public final SigmaScaleList remove(final byte[] uuid) {
        SigmaScaleManager sigmaScaleManager = null;
        SigmaScaleList sigmaScaleList = null;
        /*
         * Check : empty uuid.
         */
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        try {
            /*
             * Delete SigmaScale list.
             */
            sigmaScaleManager = new SigmaScaleManager();
            sigmaScaleManager.beginTransaction();
            sigmaScaleList = sigmaScaleManager.delete(uuid);
            sigmaScaleManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (sigmaScaleManager != null) {
                try {
                    sigmaScaleManager.rollback();
                } catch (BaseManagerException re) {
                    sigmaScaleList = null;
                }
            }
            sigmaScaleList = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (sigmaScaleManager != null) {
                try {
                    sigmaScaleManager.rollback();
                } catch (BaseManagerException re) {
                    sigmaScaleList = null;
                }
            }
            sigmaScaleList = null;
        }
        return sigmaScaleList;
    }

}
