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

package edu.cudenver.bios.studydesignsvc.resource;

import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.resource.Representation;
import org.restlet.resource.Resource;
import org.restlet.resource.StringRepresentation;
import org.restlet.resource.Variant;

import edu.cudenver.bios.studydesignsvc.application.StudyDesignConstants;

/**
 * Default request resource.  Called from the URI /power
 * Simply returns a self-identifying message for the server
 * 
 * @author Uttara Sakhadeo
 */
public class DefaultResource extends Resource
{
	/**
	 * Constructor.
	 * @param context restlet context
	 * @param request HTTP request information
	 * @param response HTTP response information
	 */
    public DefaultResource(Context context, Request request, Response response) 
    {
        super(context, request, response);

        // This representation has only one type of representation.
        getVariants().add(new Variant(MediaType.TEXT_PLAIN));
    }

    /**
     * Returns a full representation for a given variant.
     */
    @Override
    public Representation represent(Variant variant) {
        Representation representation = 
            new StringRepresentation("Study Design REST Service, version " + StudyDesignConstants.VERSION,
            		MediaType.TEXT_PLAIN);

        return representation;
    }
}
