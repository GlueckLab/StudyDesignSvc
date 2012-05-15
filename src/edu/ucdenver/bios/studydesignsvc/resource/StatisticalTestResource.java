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

import org.restlet.resource.Delete;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

import edu.ucdenver.bios.webservice.common.domain.StatisticalTestList;

// TODO: Auto-generated Javadoc
// to-do: Auto-generated Javadoc
/**
 * Generic Resource class for handling requests for the domain
 * statisticalTestList object of a StatisticalTest. See the
 * StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public interface StatisticalTestResource {
    
    /**
     * Creates the StatisticalTestList.
     * 
     * @param statisticalTestList
     *            the statistical test list
     * @return the statistical test list
     */
    @Post("application/json")
    StatisticalTestList create(StatisticalTestList statisticalTestList);

    /**
     * Update the StatisticalTestList.
     * 
     * @param statisticalTestList
     *            the statistical test list
     * @return the statistical test list
     */
    @Put("application/json")
    StatisticalTestList update(StatisticalTestList statisticalTestList);

    /**
     * Removes the StatisticalTestList.
     * 
     * @param uuid
     *            the uuid
     * @return the statistical test list
     */
    @Delete("application/json")
    StatisticalTestList remove(byte[] uuid);

}
