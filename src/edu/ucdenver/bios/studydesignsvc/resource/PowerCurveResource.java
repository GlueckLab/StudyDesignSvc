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

import edu.ucdenver.bios.webservice.common.domain.PowerCurveDescription;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;

/**
 * Resource class for handling requests for the
 * power curve object.
 * See the StudyDesignApplication class for URI mappings
 *
 * @author Uttara Sakhadeo
 */
public interface PowerCurveResource {
    /**
     * Retrieve the PowerCurveDescription for the specified UUID.
     * Returns "not found" if no matching PowerCurveDescription
     * is available.
     *
     * @param uuid the uuid
     * @return PowerCurveDescription with specified UUID
     */
     @Get
    PowerCurveDescription retrieve(byte[] uuid);
    /**
     * Store the PowerCurveDescription to the database.
     *
     * @param uuid the uuid
     * @param powerCurveDescription the power curve description
     * @return PowerCurveDescription object with updated UUID.
     */
    @Post
    PowerCurveDescription create(byte[] uuid ,
            PowerCurveDescription powerCurveDescription);
    /**
     * Update the specified PowerCurveDescription object. If there is no
     * PowerCurveDescription set for specified UUID ,  then this object
     * will be treated as new and a UUID assigned.
     *
     * @param uuid the uuid
     * @param powerCurveDescription the power curve description
     * @return the PowerCurveDescription object
     */
    @Put
    PowerCurveDescription update(byte[] uuid ,
            PowerCurveDescription powerCurveDescription);
    /**
     * Delete the object with the specified UUID.
     *
     * @param uuid the uuid of the object to remove
     * @return the deleted study design object
     */
    @Delete
    PowerCurveDescription remove(byte[] uuid);
    /**
     * Delete the object with the specified UUID.
     *
     * @param studyDesign from which object is to be removed
     * @return the deleted study design object
     */
    @Delete
    PowerCurveDescription removeFrom(StudyDesign studyDesign);
}
