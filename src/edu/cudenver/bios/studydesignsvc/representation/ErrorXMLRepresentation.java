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
package edu.cudenver.bios.studydesignsvc.representation;

import java.io.IOException;

import org.restlet.data.MediaType;
import org.restlet.resource.DomRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.cudenver.bios.studydesignsvc.application.StudyDesignConstants;

/**
 * XML representation of an error message.  
 * Avoids using server default and allows easier parsing/presentation
 * of error message on the client side
 * 
 * @author Uttara Sakhadeo
 * 
 */
public class ErrorXMLRepresentation extends DomRepresentation
{
	/**
     * Create an XML representation of the specified error message
     * 
     * @param msg
     * @throws IOException
     */
    public ErrorXMLRepresentation(String msg) throws IOException
    {
        super(MediaType.APPLICATION_XML);
        
        Document doc = getDocument();
        Element errorElem = doc.createElement(StudyDesignConstants.TAG_ERROR);
        errorElem.appendChild(doc.createTextNode(msg));
        doc.appendChild(errorElem);
        doc.normalizeDocument();
    }
}
