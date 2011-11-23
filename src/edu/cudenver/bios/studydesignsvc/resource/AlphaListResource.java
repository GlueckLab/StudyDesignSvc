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

import java.io.IOException;
import java.util.List;

import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;
import org.restlet.resource.DomRepresentation;
import org.restlet.resource.Representation;
import org.restlet.resource.Resource;
import org.restlet.resource.ResourceException;
import org.restlet.resource.StringRepresentation;
import org.restlet.resource.Variant;

import edu.cudenver.bios.power.GLMMPowerCalculator;
import edu.cudenver.bios.power.Power;
import edu.cudenver.bios.power.parameters.GLMMPowerParameters;
import edu.cudenver.bios.powersvc.application.PowerLogger;
import edu.cudenver.bios.powersvc.representation.ErrorXMLRepresentation;
import edu.cudenver.bios.powersvc.representation.GLMMPowerListXMLRepresentation;
import edu.cudenver.bios.powersvc.resource.ParameterResourceHelper;
import edu.cudenver.bios.studydesignsvc.application.StudyDesignConstants;

/**
 * Resource for handling requests for Alpha List.
 * See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class AlphaListResource extends Resource 
{
	/**
	 * Create a new resource to handle alpha list requests.  Data
	 * is returned as XML.
	 * 
	 * @param context restlet context
	 * @param request http request object
	 * @param response http response object
	 */
    public AlphaListResource(Context context, Request request, Response response) 
    {
        super(context, request, response);
        getVariants().add(new Variant(MediaType.APPLICATION_XML));
    }
    
    /**
     * Disallow GET requests
     */
    @Override
    public boolean allowGet()
    {
        return true;
    }

    /**
     * Disallow PUT requests
     */
    @Override
    public boolean allowPut()
    {
        return true;
    }

    /**
     * Allow POST requests to create a power list
     */
    @Override
    public boolean allowPost() 
    {
        return  true;
    }
    
    /**
     * Allow DELETE requests to delete a power list
     */
    @Override
    public boolean allowDelete() 
    {
        return  true;
    }
    
    /**
     * Returns a full representation for a given variant.
     */
    @Override
    public Representation represent(Variant variant) 
    {
        Representation representation = 
            new StringRepresentation("Study Design REST Service, version " + StudyDesignConstants.VERSION,
            		MediaType.TEXT_PLAIN);
        /*
         * get request -> GET
         * determine UUID ... from incoming URL
         * retrieve alphalist from db
         * serialize java object to XML
         * and return XML
         */
        return representation;
    }
    
 // removeRepresentations()   for -> DELETE
    /*
     * determine studyUUID from URL
     * fetch list/... from db -> Java
     * delete the record
     * Java -> XML
     * return XML
     * 
     */
    
    // storeRepresentation() -> UPDATE
    /*
     * detrmine UUID
     * Pare XML -> Java
     * Hibernate query for update
     * Java -> XML representation
     * return XML
     */
    
    /**
     * Process a POST request to perform a set of power
     * calculations.  Please see REST API documentation for details on
     * the entity body format.
     * 
     * @param entity HTTP entity body for the request
     */
    @Override 
    public void acceptRepresentation(Representation entity)
    {
        DomRepresentation rep = new DomRepresentation(entity);
        
        try
        {
           /*
            * determine studyUUID from URL   -> POST
            * user's request XML ... parse it to Java object
            * validate the list for numbers words etc
            * persist Java object -> db through Hibernate
            * represent Java object as XML
            * return that XML
            */
        }
        catch (IOException ioe)
        {
            PowerLogger.getInstance().error(ioe.getMessage());
            try { getResponse().setEntity(new ErrorXMLRepresentation(ioe.getMessage())); }
            catch (IOException e) {}
            getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
        catch (IllegalArgumentException iae)
        {
            PowerLogger.getInstance().error(iae.getMessage());
            try { getResponse().setEntity(new ErrorXMLRepresentation(iae.getMessage())); }
            catch (IOException e) {}
            getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
        catch (ResourceException re)
        {
            PowerLogger.getInstance().error(re.getMessage());
            try { getResponse().setEntity(new ErrorXMLRepresentation(re.getMessage())); }
            catch (IOException e) {}
            getResponse().setStatus(re.getStatus());
        }
    }
}
