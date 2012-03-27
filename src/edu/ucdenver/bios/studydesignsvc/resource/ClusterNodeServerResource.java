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

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.ucdenver.bios.studydesignsvc.manager.ClusterNodeManager;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.domain.ClusterNode;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

/**
 * Server Resource class for handling requests for the Cluster Node object. See
 * the StudyDesignApplication class for URI mappings
 *
 * @author Uttara Sakhadeo
 */
public class ClusterNodeServerResource implements ClusterNodeResource {
    /** The cluster node manager. */
    private ClusterNodeManager clusterNodeManager = null;

    /** The study design manager. */
    private StudyDesignManager studyDesignManager = null;

    /** The uuid flag. */
    private boolean uuidFlag;

    /**
     * Retrieve a ClusterNode object for specified UUID.
     *
     * @param uuid
     *            the uuid
     * @return List<ClusterNode>
     */
    @Get("json")
    public final List<ClusterNode> retrieve(final byte[] uuid) {
        List<ClusterNode> clusterNodeList = null;
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        try {
            /*
             * ---------------------------------------------------- Check for
             * existence of a UUID in Study Design object
             * ----------------------------------------------------
             */
            studyDesignManager = new StudyDesignManager();
            studyDesignManager.beginTransaction();
            uuidFlag = studyDesignManager.hasUUID(uuid);
            if (uuidFlag) {
                StudyDesign studyDesign = studyDesignManager.get(uuid);
                if (studyDesign != null) {
                    clusterNodeList = studyDesign.getClusteringTree();
                }
            }
            studyDesignManager.commit();
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
        } catch (StudyDesignException sde) {
            System.out.println(sde.getMessage());
            StudyDesignLogger.getInstance().error(sde.getMessage());
            if (studyDesignManager != null) {
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException re) {
                    clusterNodeList = null;
                }
            }
            clusterNodeList = null;
        }
        return clusterNodeList;
    }

    /**
     * Create a ClusterNode object for specified UUID.
     *
     * @param uuid
     *            the uuid
     * @param clusterNodeList
     *            the cluster node list
     * @return List<ClusterNode>
     */
    @Post("json")
    public final List<ClusterNode> create(final byte[] uuid,
            List<ClusterNode> clusterNodeList) {
        StudyDesign studyDesign = null;
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        try {
            /*
             * ----------------------------------------------------
             * Check for existence of a UUID in Study Design object
             * ----------------------------------------------------
             */
            studyDesignManager = new StudyDesignManager();
            studyDesignManager.beginTransaction();
            uuidFlag = studyDesignManager.hasUUID(uuid);
            if (uuidFlag) {
                studyDesign = studyDesignManager.get(uuid);
            } else {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                        "no study design UUID specified");
            }
            studyDesignManager.commit();
            /*
             * ----------------------------------------------------
             * Remove existing ClusterNode for this object
             * ----------------------------------------------------
             */
            if (uuidFlag && studyDesign.getClusteringTree() != null) {
                removeFrom(studyDesign);
            }
            /*
             * ----------------------------------------------------
             * Save new ClusterNode List object
             * ----------------------------------------------------
             */
            if (uuidFlag) {
                /*
                 * clusterNodeManager = new ClusterNodeManager();
                 * clusterNodeManager.beginTransaction();
                 * clusterNodeManager.saveOrUpdate(clusterNodeList, true);
                 * clusterNodeManager.commit();
                 */
                /*
                 * ---------------------------------------------------- Set
                 * reference of ClusterNode Object to Study Design object
                 * ----------------------------------------------------
                 */
                studyDesign.setClusteringTree(clusterNodeList);
                studyDesignManager = new StudyDesignManager();
                studyDesignManager.beginTransaction();
                studyDesign = studyDesignManager.saveOrUpdate(studyDesign,
                        false);
                studyDesignManager.commit();
            }
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
        } catch (StudyDesignException sde) {
            System.out.println(sde.getMessage());
            StudyDesignLogger.getInstance().error(sde.getMessage());
            if (studyDesignManager != null) {
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException re) {
                    clusterNodeList = null;
                }
            }
            clusterNodeList = null;
        }
        return clusterNodeList;
    }

    /**
     * Update a ClusterNode object for specified UUID.
     *
     * @param uuid
     *            the uuid
     * @param clusterNodeList
     *            the cluster node list
     * @return List<ClusterNode>
     */
    @Put("json")
    public final List<ClusterNode> update(final byte[] uuid,
            final List<ClusterNode> clusterNodeList) {
        return create(uuid, clusterNodeList);
    }

    /**
     * Delete a ClusterNode object for specified UUID.
     *
     * @param uuid
     *            the uuid
     * @return List<ClusterNode>
     */
    @Delete("json")
    public final List<ClusterNode> remove(final byte[] uuid) {
        List<ClusterNode> clusterNodeList = null;
        StudyDesign studyDesign = null;
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        try {
            /*
             * ---------------------------------------------------- Check for
             * existence of a UUID in Study Design object
             * ----------------------------------------------------
             */
            studyDesignManager = new StudyDesignManager();
            studyDesignManager.beginTransaction();
            uuidFlag = studyDesignManager.hasUUID(uuid);
            if (uuidFlag) {
                studyDesign = studyDesignManager.get(uuid);
                if (studyDesign != null) {
                    clusterNodeList = studyDesign.getClusteringTree();
                }
            }
            studyDesignManager.commit();
            /*
             * ---------------------------------------------------- Remove
             * existing ClusterNode objects for this object
             * ----------------------------------------------------
             */
            if (clusterNodeList != null) {
                clusterNodeManager = new ClusterNodeManager();
                clusterNodeManager.beginTransaction();
                clusterNodeList = clusterNodeManager.delete(uuid,
                        clusterNodeList);
                clusterNodeManager.commit();
            }
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
        } catch (StudyDesignException sde) {
            System.out.println(sde.getMessage());
            StudyDesignLogger.getInstance().error(sde.getMessage());
            if (studyDesignManager != null) {
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException re) {
                    clusterNodeList = null;
                }
            }
            clusterNodeList = null;
        }
        return clusterNodeList;
    }

    /**
     * Delete a ClusterNode object for specified Study Design.
     *
     * @param studyDesign
     *            the study design
     * @return List<ClusterNode>
     */
    @Delete("json")
    public final List<ClusterNode> removeFrom(final StudyDesign studyDesign) {
        List<ClusterNode> clusteringTree = null;
        try {
            clusterNodeManager = new ClusterNodeManager();
            clusterNodeManager.beginTransaction();
            clusteringTree = clusterNodeManager.delete(studyDesign.getUuid(),
                    studyDesign.getClusteringTree());
            clusterNodeManager.commit();
        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(
                    "Failed to load Study Design information: "
                            + bme.getMessage());
            if (studyDesignManager != null) {
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException e) {
                }
            }
            if (clusterNodeManager != null) {
                try {
                    clusterNodeManager.rollback();
                } catch (BaseManagerException e) {
                }
            }
            clusteringTree = null;
        }
        return clusteringTree;
    }
}
