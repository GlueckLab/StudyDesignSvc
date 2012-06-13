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
import org.restlet.resource.Post;
import org.restlet.resource.Put;

import edu.ucdenver.bios.webservice.common.domain.Hypothesis;
import edu.ucdenver.bios.webservice.common.domain.UuidHypothesis;
import edu.ucdenver.bios.webservice.common.domain.UuidHypothesisType;

// TODO: Auto-generated Javadoc
/**
 * Generic Resource Interface for handling (PUT, POST, DELETE) requests for the
 * HypothesisList domain object. See the StudyDesignApplication class for URI
 * mappings
 * 
 * @author Uttara Sakhadeo
 */
public interface HypothesisResource {

    /**
     * Creates the Hypothesis.
     * 
     * @param uuidHypothesis
     *            the uuid hypothesis
     * @return the hypothesis
     */
    @Post("application/json")
    Hypothesis create(UuidHypothesis uuidHypothesis);

    /**
     * Updates the Hypothesis.
     * 
     * @param uuidHypothesis
     *            the uuid hypothesis
     * @return the hypothesis
     */
    @Put("application/json")
    Hypothesis update(UuidHypothesis uuidHypothesis);

    /**
     * Removes the Hypothesis.
     * 
     * @param uuidType
     *            the uuid type
     * @return the hypothesis
     */
    @Delete("application/json")
    Hypothesis remove(UuidHypothesisType uuidType);

}
