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
import edu.ucdenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.ucdenver.bios.studydesignsvc.manager.PowerMethodManager;
import edu.ucdenver.bios.studydesignsvc.manager.PowerMethodManager;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.domain.PowerMethod;
import edu.ucdenver.bios.webservice.common.domain.PowerMethodList;
import edu.ucdenver.bios.webservice.common.domain.PowerMethodList;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

/**
 * Server Resource class for handling requests for the PowerMethod object. See
 * the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class PowerMethodServerResource extends ServerResource implements
        PowerMethodResource {
    
    /**
     * Creates the PowerMethodList.
     * 
     * @param powerMethodList
     *            the power method list
     * @return the power method list
     */
    @Post("application/json")
    public final PowerMethodList create(PowerMethodList powerMethodList) {
        PowerMethodManager powerMethodManager = null;
        byte[] uuid = powerMethodList.getUuid();
        /*
         * Check : empty uuid.
         */
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        /*
         * Check : empty PowerMethod list.
         */
        List<PowerMethod> list = powerMethodList.getPowerMethodList();
        if (list == null || list.isEmpty()) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no Beta Scale specified");
        }
        try {
            /*
             * Save PowerMethod list.
             */
            powerMethodManager = new PowerMethodManager();
            powerMethodManager.beginTransaction();
            powerMethodList = powerMethodManager.saveOrUpdate(powerMethodList,
                    true);
            powerMethodManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (powerMethodManager != null) {
                try {
                    powerMethodManager.rollback();
                } catch (BaseManagerException re) {
                    powerMethodList = null;
                }
            }
            powerMethodList = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (powerMethodManager != null) {
                try {
                    powerMethodManager.rollback();
                } catch (BaseManagerException re) {
                    powerMethodList = null;
                }
            }
            powerMethodList = null;
        }
        return powerMethodList;
    }

    /**
     * Updates the PowerMethodList.
     * 
     * @param powerMethodList
     *            the power method list
     * @return the power method list
     */
    @Put("application/json")
    public final PowerMethodList update(final PowerMethodList powerMethodList) {
        return create(powerMethodList);
    }

    /**
     * Removes the PowerMethodList.
     * 
     * @param uuid
     *            the uuid
     * @return the power method list
     */
    @Delete("application/json")
    public final PowerMethodList remove(final byte[] uuid) {
        PowerMethodManager powerMethodManager = null;
        PowerMethodList powerMethodList = null;
        /*
         * Check : empty uuid.
         */
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        try {
            /*
             * Delete PowerMethod list.
             */
            powerMethodManager = new PowerMethodManager();
            powerMethodManager.beginTransaction();
            powerMethodList = powerMethodManager.delete(uuid);
            powerMethodManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (powerMethodManager != null) {
                try {
                    powerMethodManager.rollback();
                } catch (BaseManagerException re) {
                    powerMethodList = null;
                }
            }
            powerMethodList = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (powerMethodManager != null) {
                try {
                    powerMethodManager.rollback();
                } catch (BaseManagerException re) {
                    powerMethodList = null;
                }
            }
            powerMethodList = null;
        }
        return powerMethodList;
    }

}
