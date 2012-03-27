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
 * of the License ,  or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful ,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not ,  write to the Free Software
 * Foundation ,  Inc. 51 Franklin Street ,  Fifth Floor ,  Boston ,  MA
 * 02110-1301 ,  USA.
 */
package edu.ucdenver.bios.studydesignsvc.resource;

import java.util.List;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

import edu.ucdenver.bios.webservice.common.domain.StudyDesign;


/**
 * Resource class for handling requests for the complete study design object.
 * See the StudyDesignApplication class for URI mappings
 *
 * @author Uttara Sakhadeo
 */
public interface StudyDesignResource {

    /**
     * Retrieve the study design matching the specified UUID. Returns
     * "not found" if no matching designs are available
     *
     * @param uuid
     *            the uuid
     * @return study designs with specified UUID
     */
    @Get
    StudyDesign retrieve(byte[] uuid);

    /**
     * Gets a list of all study designs in the database.
     *
     * @return list of study designs
     */
    @Get
    List<StudyDesign> retrieve();

    /**
     * Store the study design to the database. This routine will set the UUID of
     * the study design
     *
     * @param studyDesign
     *            study design object
     * @return study design object with updated UUID.
     */
    @Post
    StudyDesign create(StudyDesign studyDesign);

    /**
     * Store the study design to the database. This routine will create a new
     * UUID for the study design. And will return back an empty StudyDesign
     *
     * @return study design object with updated UUID.
     */
    @Post
    StudyDesign create();

    /**
     * Update the specified study design object. If the UUID of the study design
     * is not set , the design will be treated as new and a UUID assigned.
     *
     * @param studyDesign
     *            study design object
     * @return the study design object
     */
    @Put
    StudyDesign update(StudyDesign studyDesign);

    /**
     * Delete the study with the specified UUID.
     *
     * @param uuid
     *            the uuid of the study to remove
     * @return the deleted study design object
     */
    @Delete
    StudyDesign remove(byte[] uuid);
}
