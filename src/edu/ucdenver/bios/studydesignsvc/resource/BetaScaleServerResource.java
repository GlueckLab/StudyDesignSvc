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

import java.util.List;

import org.restlet.data.Status;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.manager.BetaScaleManager;
import edu.ucdenver.bios.webservice.common.domain.BetaScale;
import edu.ucdenver.bios.webservice.common.domain.BetaScaleList;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Server Resource class for handling requests for the Beta Scale object. See
 * the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class BetaScaleServerResource extends ServerResource implements
        BetaScaleResource {

    /**
     * Retrieves the Beta Scale List.
     * 
     * @param uuid
     *            the uuid
     * @return the beta scale list
     */
    @Get("application/json")
    public final BetaScaleList retrieve(final byte[] uuid) {
        BetaScaleManager betaScaleManager = null;
        BetaScaleList betaScaleList = null;
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
             * Delete beta scale list.
             */
            betaScaleManager = new BetaScaleManager();
            betaScaleManager.beginTransaction();
            betaScaleList = betaScaleManager.retrieve(uuid);
            betaScaleManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (betaScaleManager != null) {
                try {
                    betaScaleManager.rollback();
                } catch (BaseManagerException re) {
                    betaScaleList = null;
                }
            }
            betaScaleList = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (betaScaleManager != null) {
                try {
                    betaScaleManager.rollback();
                } catch (BaseManagerException re) {
                    betaScaleList = null;
                }
            }
            betaScaleList = null;
        }
        return betaScaleList;
    }

    /**
     * Creates the Beta Scale List.
     * 
     * @param betaScaleList
     *            the beta scale list
     * @return the beta scale list
     */
    @Post("application/json")
    public final BetaScaleList create(BetaScaleList betaScaleList) {
        BetaScaleManager betaScaleManager = null;
        byte[] uuid = betaScaleList.getUuid();
        /*
         * Check : empty uuid.
         */
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        /*
         * Check : empty beta scale list.
         */
        List<BetaScale> list = betaScaleList.getBetaScaleList();
        if (list == null || list.isEmpty()) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no Beta Scale specified");
        }
        try {
        /*
         * Save beta scale list.
         */
        betaScaleManager = new BetaScaleManager();
        betaScaleManager.beginTransaction();
            betaScaleList = betaScaleManager.saveOrUpdate(betaScaleList, true);
        betaScaleManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (betaScaleManager != null) {
                try {
                    betaScaleManager.rollback();
                } catch (BaseManagerException re) {
                    betaScaleList = null;
                }
            }
            betaScaleList = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (betaScaleManager != null) {
                try {
                    betaScaleManager.rollback();
                } catch (BaseManagerException re) {
                    betaScaleList = null;
                }
            }
            betaScaleList = null;
        }
        return betaScaleList;
    }

    /**
     * Updates the Beta Scale List.
     * 
     * @param betaScaleList
     *            the list
     * @return the beta scale list
     */
    @Put("application/json")
    public final BetaScaleList update(final BetaScaleList betaScaleList) {
        return create(betaScaleList);
    }

    /**
     * Removes the Beta Scale List.
     * 
     * @param uuid
     *            the uuid
     * @return the beta scale list
     */
    @Delete("application/json")
    public final BetaScaleList remove(final byte[] uuid) {
        BetaScaleManager betaScaleManager = null;
        BetaScaleList betaScaleList = null;
        /*
         * Check : empty uuid.
         */
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        try {
            /*
             * Delete beta scale list.
             */
            betaScaleManager = new BetaScaleManager();
            betaScaleManager.beginTransaction();
            betaScaleList = betaScaleManager.delete(uuid);
            betaScaleManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (betaScaleManager != null) {
                try {
                    betaScaleManager.rollback();
                } catch (BaseManagerException re) {
                    betaScaleList = null;
                }
            }
            betaScaleList = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (betaScaleManager != null) {
                try {
                    betaScaleManager.rollback();
                } catch (BaseManagerException re) {
                    betaScaleList = null;
                }
            }
            betaScaleList = null;
        }
        return betaScaleList;
    }

}
