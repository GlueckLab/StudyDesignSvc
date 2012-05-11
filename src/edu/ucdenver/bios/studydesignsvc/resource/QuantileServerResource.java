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
import edu.ucdenver.bios.studydesignsvc.manager.QuantileManager;
import edu.ucdenver.bios.webservice.common.domain.Quantile;
import edu.ucdenver.bios.webservice.common.domain.QuantileList;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

/**
 * Server Resource class for handling requests for the Quantile object. See the
 * StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class QuantileServerResource extends ServerResource implements
        QuantileResource {
    /**
     * Retrieves the QuantileList.
     * 
     * @param uuid
     *            the uuid
     * @return the quantile list
     */
    @Get("application/json")
    public final QuantileList retrieve(final byte[] uuid) {
        QuantileManager quantileManager = null;
        QuantileList quantileList = null;
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
             * Retrieve Quantile list.
             */
            quantileManager = new QuantileManager();
            quantileManager.beginTransaction();
            quantileList = quantileManager.retrieve(uuid);
            quantileManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (quantileManager != null) {
                try {
                    quantileManager.rollback();
                } catch (BaseManagerException re) {
                    quantileList = null;
                }
            }
            quantileList = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (quantileManager != null) {
                try {
                    quantileManager.rollback();
                } catch (BaseManagerException re) {
                    quantileList = null;
                }
            }
            quantileList = null;
        }
        return quantileList;
    }

    /**
     * Creates the QuantileList.
     * 
     * @param quantileList
     *            the quantile list
     * @return the quantile list
     */
    @Post("application/json")
    public final QuantileList create(QuantileList quantileList) {
        QuantileManager quantileManager = null;
        byte[] uuid = quantileList.getUuid();
        /*
         * Check : empty uuid.
         */
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        /*
         * Check : empty Quantile list.
         */
        List<Quantile> list = quantileList.getQuantileList();
        if (list == null || list.isEmpty()) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no Beta Scale specified");
        }
        try {
            /*
             * Save Quantile list.
             */
            quantileManager = new QuantileManager();
            quantileManager.beginTransaction();
            quantileList = quantileManager.saveOrUpdate(quantileList, true);
            quantileManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (quantileManager != null) {
                try {
                    quantileManager.rollback();
                } catch (BaseManagerException re) {
                    quantileList = null;
                }
            }
            quantileList = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (quantileManager != null) {
                try {
                    quantileManager.rollback();
                } catch (BaseManagerException re) {
                    quantileList = null;
                }
            }
            quantileList = null;
        }
        return quantileList;
    }

    /**
     * Updates the QuantileList.
     * 
     * @param quantileList
     *            the quantile list
     * @return the quantile list
     */
    @Put("application/json")
    public final QuantileList update(final QuantileList quantileList) {
        return create(quantileList);
    }

    /**
     * Removes the QuantileList.
     * 
     * @param uuid
     *            the uuid
     * @return the quantile list
     */
    @Delete("application/json")
    public final QuantileList remove(final byte[] uuid) {
        QuantileManager quantileManager = null;
        QuantileList quantileList = null;
        /*
         * Check : empty uuid.
         */
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        try {
            /*
             * Delete Quantile list.
             */
            quantileManager = new QuantileManager();
            quantileManager.beginTransaction();
            quantileList = quantileManager.delete(uuid);
            quantileManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (quantileManager != null) {
                try {
                    quantileManager.rollback();
                } catch (BaseManagerException re) {
                    quantileList = null;
                }
            }
            quantileList = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (quantileManager != null) {
                try {
                    quantileManager.rollback();
                } catch (BaseManagerException re) {
                    quantileList = null;
                }
            }
            quantileList = null;
        }
        return quantileList;
    }

}
