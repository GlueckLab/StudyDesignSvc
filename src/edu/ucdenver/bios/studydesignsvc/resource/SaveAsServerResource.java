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

import java.io.IOException;

import org.restlet.data.Disposition;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.exceptions.ErrorXMLRepresentation;

public class SaveAsServerResource extends ServerResource
implements SaveAsResource{

    @Post("application/json")
    public Representation saveAs(Representation entity) {
        try
        {
            // build the response xml
            Form form = new Form(entity);
            String filename = form.getFirstValue("filename");
            if (filename == null || filename.isEmpty()) filename = "out.xml";
            String format = form.getFirstValue("format");
            String data = form.getFirstValue("data");
            if (data == null)
            {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "No data specified");
            }
            // TODO: format to pdf, word, ppt?
            Disposition disposition = new Disposition();
            disposition.setType(Disposition.TYPE_ATTACHMENT);
            disposition.setFilename(filename);
            StringRepresentation dataRepresentation = new StringRepresentation(data);
            dataRepresentation.setDisposition(disposition);
            return dataRepresentation;
        }
        catch (IllegalArgumentException iae)
        {
            StudyDesignLogger.getInstance().error(iae.getMessage());
            try { getResponse().setEntity(new ErrorXMLRepresentation(iae.getMessage())); }
            catch (IOException e) {}
            getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, iae.getMessage());
        }
    }

}
