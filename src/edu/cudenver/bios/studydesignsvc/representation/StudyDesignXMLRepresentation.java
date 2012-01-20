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
import org.restlet.ext.xml.DomRepresentation;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.cudenver.bios.studydesignsvc.application.StudyDesignConstants;
import edu.cudenver.bios.studydesignsvc.domain.StudyDesign;

/**
 * Class which converts a study design record to an XML DOM
 * 
 * @author Uttara Sakhadeo
 */
public class StudyDesignXMLRepresentation extends DomRepresentation 
{
	/**
	 * Constructor
	 * @param generic List object
	 * @throws IOException
	 */
	public StudyDesignXMLRepresentation(StudyDesign studyDesign) throws IOException 
	 {
	        super(MediaType.APPLICATION_XML);	        
	        Document doc = getDocument();
	        
	        Element rootElement = createStudyDesignNodeElement(doc, studyDesign);
	       	doc.appendChild(rootElement);
	        doc.normalizeDocument();
	 }
	
	public static Element createStudyDesignNodeElement(Document doc, StudyDesign studyDesign)  throws IOException 
	{
		Element studyDesignRootElement = doc.createElement(StudyDesignConstants.TAG_STUDY_DESIGN);		
		studyDesignRootElement.setAttribute(StudyDesignConstants.TAG_STUDY_UUID, studyDesign.getStudyUUID().toString());
		
		/*
		 * How to add all the lists in the XML?
		 * How to know how many times .... size
		 */
		// Display all the Lists present
		/*for (NamedList namedList: studyDesign.getListOfNamedLists()) 
        {        	
        	Element namedListElem = ListXMLRepresentation.createListNodeElement(doc, namedList);
        	studyDesignRootElement.appendChild(namedListElem);
        }*/
		
		// Display all the Lists
		/*studyDesignRootElement.appendChild(
				ListListXMLRepresentation.createListofListNodeElement(doc, studyDesign.getListOfNamedLists()));*/
				
		// Display all the Matrices
		/*studyDesignRootElement.appendChild(
				ListMatrixXMLRepresentation.createListofListNodeElement(doc, studyDesign.getListOfNamedRealMatrices()));*/
		
		return studyDesignRootElement;
	}
}
