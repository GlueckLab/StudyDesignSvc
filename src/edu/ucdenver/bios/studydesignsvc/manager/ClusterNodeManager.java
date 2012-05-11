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
package edu.ucdenver.bios.studydesignsvc.manager;

import java.util.List;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import edu.ucdenver.bios.webservice.common.domain.ClusterNode;
import edu.ucdenver.bios.webservice.common.domain.ClusterNodeList;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Manager class which provides CRUD functionality for ClusterNode object.
 * 
 * @author Uttara Sakhadeo
 */
public class ClusterNodeManager extends StudyDesignParentManager {

    /**
     * Delete the ClusterNodeList.
     * 
     * @param uuid
     *            the uuid
     * @param clusteNodeList
     *            the cluste node list
     * @return the cluster node list
     */
    private ClusterNodeList delete(final byte[] uuid,
            final List<ClusterNode> clusteNodeList) {
        ClusterNodeList deletedList = null;
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        try {
            if (clusteNodeList != null && !clusteNodeList.isEmpty()) {
                for (ClusterNode clusterNode : clusteNodeList) {
                    session.delete(clusterNode);
                }
            }
            deletedList = new ClusterNodeList(uuid, clusteNodeList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete ClusterNode object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return deletedList;
    }

    /**
     * Instantiates a new cluster node manager.
     * 
     * @throws BaseManagerException
     *             the base manager exception
     */
    public ClusterNodeManager() throws BaseManagerException {
        super();
    }

    /**
     * Retrieves the ClusterNodeList.
     * 
     * @param uuid
     *            the uuid
     * @return the cluster node list
     */
    public final ClusterNodeList retrieve(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        ClusterNodeList clusteNodeList = null;
        try {
            /*
             * Retrieve Study Design for given uuid
             */
            StudyDesign studyDesign = get(uuid);
            /*
             * Retrieve Original ClusterNodeList Object
             */
            if (studyDesign != null) {
                List<ClusterNode> originalList = studyDesign
                        .getClusteringTree();
                if (originalList != null && !originalList.isEmpty()) {
                    clusteNodeList = new ClusterNodeList(uuid, originalList);
                } else {
                    /*
                     * uuid exists but no ClusterNodeList entry present. If uuid
                     * = null too; then it means no entry for this uuid.
                     */
                    clusteNodeList = new ClusterNodeList(uuid, null);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete ClusterNode object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return clusteNodeList;
    }

    /**
     * Deletes the ClusterNodeList.
     * 
     * @param uuid
     *            the uuid
     * @return the cluster node list
     */
    public final ClusterNodeList delete(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        ClusterNodeList clusteNodeList = null;
        StudyDesign studyDesign = null;
        try {
            /*
             * Retrieve Original ClusterNode Object
             */
            studyDesign = get(uuid);
            if (studyDesign != null) {
                List<ClusterNode> originalList = studyDesign
                        .getClusteringTree();
                /*
                 * Delete Existing ClusterNode List Object
                 */
                if (originalList != null && !originalList.isEmpty()) {
                    clusteNodeList = delete(uuid, originalList);
                }
                /*
                 * Update Study Design Object
                 */
                studyDesign.setClusteringTree(null);
                session.update(studyDesign);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete ClusterNode object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        /*
         * Return ClusterNodeList
         */
        return clusteNodeList;
    }

    /**
     * Saves or updates the ClusterNodeList.
     * 
     * @param clusteNodeList
     *            the cluste node list
     * @param isCreation
     *            the is creation
     * @return the cluster node list
     */
    public final ClusterNodeList saveOrUpdate(
            final ClusterNodeList clusteNodeList, final boolean isCreation) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        StudyDesign studyDesign = null;
        List<ClusterNode> originalList = null;
        ClusterNodeList newClusterNodeList = null;
        byte[] uuid = clusteNodeList.getUuid();
        List<ClusterNode> newList = clusteNodeList.getClusterNodeList();

        try {
            /*
             * Retrieve Study Design Object
             */
            studyDesign = get(uuid);
            if (studyDesign != null) {
                originalList = studyDesign.getClusteringTree();
                /*
                 * Delete Existing ClusterNode List Object
                 */
                if (originalList != null && !originalList.isEmpty()) {
                    delete(uuid, originalList);
                }
                if (isCreation) {
                    for (ClusterNode clusterNode : newList) {
                        session.save(clusterNode);
                        System.out
                                .println("in save id: " + clusterNode.getId());
                    }
                } else {
                    for (ClusterNode clusterNode : newList) {
                        session.update(clusterNode);
                    }
                }
                /*
                 * Update Study Design Object
                 */
                studyDesign.setClusteringTree(newList);
                session.update(studyDesign);
                /*
                 * Return Persisted ClusterNodeList
                 */
                newClusterNodeList = new ClusterNodeList(uuid, newList);
            }
        } catch (Exception e) {
            newList = null;
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to save ClusterNode object : " + e.getMessage());
        }
        return newClusterNodeList;
    }

}
