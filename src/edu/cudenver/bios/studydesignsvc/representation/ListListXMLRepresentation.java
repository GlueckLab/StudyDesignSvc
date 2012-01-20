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
import java.util.List;
import java.util.UUID;

import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.cudenver.bios.studydesignsvc.application.StudyDesignConstants;
import edu.cudenver.bios.studydesignsvc.domain.NamedList;

/**
 * Class which converts a list of List objects to an XML DOM
 * 
 * @author Uttara Sakhadeo
 */
public class ListListXMLRepresentation extends DomRepresentation
{
	/**
	 * Constructor
	 * @param generic List object
	 * @throws IOException
	 */
	public ListListXMLRepresentation(List<NamedList> list,UUID uuid) throws IOException 
	 {
	        super(MediaType.APPLICATION_XML);	        
	        Document doc = getDocument();
	        	       	       
	        Element rootElement = createListofListNodeElement(doc,list,uuid);
	       	doc.appendChild(rootElement);
	        doc.normalizeDocument();
	 }
	
	 /**
     * Create a DOM element from a a generic List object.  
     * 
     * @param doc the DOM document
     * @param List object
     * @return DOM node for a single power result
     */
	public Element createListofListNodeElement(Document doc,List<NamedList> list,UUID uuid) 
    {
        // build extraction rule list
		/* where to display common basic root node? */
        Element listElem = doc.createElement(StudyDesignConstants.TAG_STUDY_DESIGN);        
        listElem.setAttribute(StudyDesignConstants.TAG_STUDY_UUID, uuid.toString());
       
        for (NamedList namedList: list) 
        {
        	//GLMMPower glmmPower = (GLMMPower) power;
            //Element powerElem = GLMMPowerXMLRepresentation.createGLMMPowerElement(doc, glmmPower);
        	Element namedListElem = ListXMLRepresentation.createListNodeElement(doc, namedList);
        	listElem.appendChild(namedListElem);
        }
        return listElem;
    }
	
	
	/**
     * Create a DOM element from a a generic List object.  
     * This overloaded function will be used by Study Design object.
     * @param doc the DOM document
     * @param List object
     * @return DOM node for a single power result
     */
	public static Element createListofListNodeElement(Document doc,List<NamedList> list) 
    {
        // build extraction rule list
		/* where to display common basic root node? */
		Element rootElem = doc.getDocumentElement();
        
        for (NamedList namedList: list) 
        {
        	//GLMMPower glmmPower = (GLMMPower) power;
            //Element powerElem = GLMMPowerXMLRepresentation.createGLMMPowerElement(doc, glmmPower);
        	Element namedListElem = ListXMLRepresentation.createListNodeElement(doc, namedList);
        	rootElem.appendChild(namedListElem);
        }
        return rootElem;
    }


}
