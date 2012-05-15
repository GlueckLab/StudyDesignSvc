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
import edu.ucdenver.bios.studydesignsvc.manager.ClusterNodeManager;
import edu.ucdenver.bios.webservice.common.domain.ClusterNodeList;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;
/**
 * Generic Resource class for handling retrieve request for the domain objects of a
 * study design. See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class ClusterNodeRetrieveServerResource extends ServerResource implements
        ClusterNodeRetrieveResource {
    /**
     * Retrieve the ClusterNodeList.
     * 
     * @param uuid
     *            the uuid
     * @return the cluster node list
     */
    @Post("application/json")
    public final ClusterNodeList retrieve(final byte[] uuid) {
        ClusterNodeManager clusterNodeManager = null;
        ClusterNodeList clusterNodeList = null;
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
             * Retrieve ClusterNode list.
             */
            clusterNodeManager = new ClusterNodeManager();
            clusterNodeManager.beginTransaction();
            clusterNodeList = clusterNodeManager.retrieve(uuid);
            clusterNodeManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (clusterNodeManager != null) {
                try {
                    clusterNodeManager.rollback();
                } catch (BaseManagerException re) {
                    clusterNodeList = null;
                }
            }
            clusterNodeList = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (clusterNodeManager != null) {
                try {
                    clusterNodeManager.rollback();
                } catch (BaseManagerException re) {
                    clusterNodeList = null;
                }
            }
            clusterNodeList = null;
        }
        return clusterNodeList;
    }

}
