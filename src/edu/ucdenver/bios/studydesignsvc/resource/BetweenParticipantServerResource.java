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
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.manager.BetweenParticipantFactorManager;
import edu.ucdenver.bios.webservice.common.domain.BetweenParticipantFactor;
import edu.ucdenver.bios.webservice.common.domain.BetweenParticipantFactorList;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * The Class BetweenParticipantServerResource.
 */
public class BetweenParticipantServerResource extends ServerResource implements
        BetweenParticipantResource {

    /**
     * Creates the BetweenParticipantFactorList.
     * 
     * @param betweenParticipantFactorList
     *            the between participant factor list
     * @return the repeated measures node list
     */
    @Post("application/json")
    public final BetweenParticipantFactorList create(
            BetweenParticipantFactorList betweenParticipantFactorList) {
        BetweenParticipantFactorManager betweenParticipantFactorManager = null;
        byte[] uuid = betweenParticipantFactorList.getUuid();
        /*
         * Check : empty uuid.
         */
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        /*
         * Check : empty BetweenParticipantFactor list.
         */
        List<BetweenParticipantFactor> list = betweenParticipantFactorList
                .getBetweenParticipantFactorList();
        if (list == null || list.isEmpty()) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no Beta Scale specified");
        }
        try {
            /*
             * Save BetweenParticipantFactor list.
             */
            betweenParticipantFactorManager = new BetweenParticipantFactorManager();
            betweenParticipantFactorManager.beginTransaction();
            betweenParticipantFactorList = betweenParticipantFactorManager
                    .saveOrUpdate(betweenParticipantFactorList, true);
            betweenParticipantFactorManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (betweenParticipantFactorManager != null) {
                try {
                    betweenParticipantFactorManager.rollback();
                } catch (BaseManagerException re) {
                    betweenParticipantFactorList = null;
                }
            }
            betweenParticipantFactorList = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (betweenParticipantFactorManager != null) {
                try {
                    betweenParticipantFactorManager.rollback();
                } catch (BaseManagerException re) {
                    betweenParticipantFactorList = null;
                }
            }
            betweenParticipantFactorList = null;
        }
        return betweenParticipantFactorList;
    }

    /**
     * Update the BetweenParticipantFactorList.
     * 
     * @param betweenParticipantFactorList
     *            the between participant factor list
     * @return the repeated measures node list
     */
    @Put("application/json")
    public final BetweenParticipantFactorList update(
            BetweenParticipantFactorList betweenParticipantFactorList) {
        return create(betweenParticipantFactorList);
    }

    /**
     * Removes the BetweenParticipantFactorList.
     * 
     * @param uuid
     *            the uuid
     * @return the repeated measures node list
     */
    @Delete("application/json")
    public final BetweenParticipantFactorList remove(final byte[] uuid) {
        BetweenParticipantFactorManager betweenParticipantFactorManager = null;
        BetweenParticipantFactorList betweenParticipantFactorList = null;
        /*
         * Check : empty uuid.
         */
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        try {
            /*
             * Delete BetweenParticipantFactor list.
             */
            betweenParticipantFactorManager = new BetweenParticipantFactorManager();
            betweenParticipantFactorManager.beginTransaction();
            betweenParticipantFactorList = betweenParticipantFactorManager
                    .delete(uuid);
            betweenParticipantFactorManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (betweenParticipantFactorManager != null) {
                try {
                    betweenParticipantFactorManager.rollback();
                } catch (BaseManagerException re) {
                    betweenParticipantFactorList = null;
                }
            }
            betweenParticipantFactorList = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (betweenParticipantFactorManager != null) {
                try {
                    betweenParticipantFactorManager.rollback();
                } catch (BaseManagerException re) {
                    betweenParticipantFactorList = null;
                }
            }
            betweenParticipantFactorList = null;
        }
        return betweenParticipantFactorList;
    }

}
