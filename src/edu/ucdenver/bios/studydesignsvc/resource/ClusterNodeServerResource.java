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
import edu.ucdenver.bios.studydesignsvc.manager.ClusterNodeManager;
import edu.ucdenver.bios.webservice.common.domain.ClusterNode;
import edu.ucdenver.bios.webservice.common.domain.ClusterNodeList;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Server Resource class for handling (PUT, POST, DELETE) requests for the
 * Cluster Node object. See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class ClusterNodeServerResource extends ServerResource implements
        ClusterNodeResource {

    /**
     * Creates the ClusterNodeList.
     * 
     * @param clusterNodeList
     *            the cluster node list
     * @return the cluster node list
     */
    @Post("application/json")
    public final ClusterNodeList create(ClusterNodeList clusterNodeList) {
        ClusterNodeManager clusterNodeManager = null;
        byte[] uuid = clusterNodeList.getUuid();
        /*
         * Check : empty uuid.
         */
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        /*
         * Check : empty ClusterNode list.
         */
        List<ClusterNode> list = clusterNodeList.getClusterNodeList();
        if (list == null || list.isEmpty()) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no Beta Scale specified");
        }
        try {
            /*
             * Save ClusterNode list.
             */
            clusterNodeManager = new ClusterNodeManager();
            clusterNodeManager.beginTransaction();
            clusterNodeList = clusterNodeManager.saveOrUpdate(clusterNodeList,
                    true);
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

    /**
     * Update the ClusterNodeList.
     * 
     * @param clusterNodeList
     *            the cluster node list
     * @return the cluster node list
     */
    @Put("application/json")
    public final ClusterNodeList update(final ClusterNodeList clusterNodeList) {
        return create(clusterNodeList);
    }

    /**
     * Removes the ClusterNodeList.
     * 
     * @param uuid
     *            the uuid
     * @return the cluster node list
     */
    @Delete("application/json")
    public final ClusterNodeList remove(final byte[] uuid) {
        ClusterNodeManager clusterNodeManager = null;
        ClusterNodeList clusterNodeList = null;
        /*
         * Check : empty uuid.
         */
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        try {
            /*
             * Delete ClusterNode list.
             */
            clusterNodeManager = new ClusterNodeManager();
            clusterNodeManager.beginTransaction();
            clusterNodeList = clusterNodeManager.delete(uuid);
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
