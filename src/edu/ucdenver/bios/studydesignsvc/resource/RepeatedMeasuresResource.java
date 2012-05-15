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

import edu.ucdenver.bios.webservice.common.domain.RepeatedMeasuresNodeList;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;

// TODO: Auto-generated Javadoc
/**
 * Generic Resource class for handling requests for the domain List object of a
 * RepeatedMeasuresNode. See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public interface RepeatedMeasuresResource {

    
    /**
     * Creates the RepeatedMeasuresNodeList.
     * 
     * @param repeatedMeasuresTree
     *            the repeated measures tree
     * @return the repeated measures node list
     */
    @Post("application/json")
    RepeatedMeasuresNodeList create(
            RepeatedMeasuresNodeList repeatedMeasuresTree);

    /**
     * Update the RepeatedMeasuresNodeList.
     * 
     * @param repeatedMeasuresTree
     *            the repeated measures tree
     * @return the repeated measures node list
     */
    @Put("application/json")
    RepeatedMeasuresNodeList update(
            RepeatedMeasuresNodeList repeatedMeasuresTree);

    /**
     * Removes the RepeatedMeasuresNodeList.
     * 
     * @param uuid
     *            the uuid
     * @return the repeated measures node list
     */
    @Delete("application/json")
    RepeatedMeasuresNodeList remove(byte[] uuid);

}
