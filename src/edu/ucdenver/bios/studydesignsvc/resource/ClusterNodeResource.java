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

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

import edu.ucdenver.bios.webservice.common.domain.ClusterNodeList;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;

// TODO: Auto-generated Javadoc
/**
 * Generic Resource class for handling requests for the domain objects of a
 * study design. See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public interface ClusterNodeResource {

    /**
     * Retrieve the ClusterNodeList.
     * 
     * @param uuid
     *            the uuid
     * @return the cluster node list
     */
    @Get("application/json")
    ClusterNodeList retrieve(byte[] uuid);

    /**
     * Creates the ClusterNodeList.
     * 
     * @param clusterNodeList
     *            the cluster node list
     * @return the cluster node list
     */
    @Post("application/json")
    ClusterNodeList create(ClusterNodeList clusterNodeList);

    /**
     * Update the ClusterNodeList.
     * 
     * @param clusterNode
     *            the cluster node
     * @return the cluster node list
     */
    @Put("application/json")
    ClusterNodeList update(ClusterNodeList clusterNode);

    /**
     * Removes the ClusterNodeList.
     * 
     * @param uuid
     *            the uuid
     * @return the cluster node list
     */
    @Delete("application/json")
    ClusterNodeList remove(byte[] uuid);

}
