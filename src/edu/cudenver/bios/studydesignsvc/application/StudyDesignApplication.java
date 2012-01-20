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

package edu.cudenver.bios.studydesignsvc.application;

import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import edu.cudenver.bios.studydesignsvc.resource.BetweenSubjectEffectResource;
import edu.cudenver.bios.studydesignsvc.resource.ConfidenceIntervalServerResource;
import edu.cudenver.bios.studydesignsvc.resource.DefaultResource;
import edu.cudenver.bios.studydesignsvc.resource.ListResource;
import edu.cudenver.bios.studydesignsvc.resource.MatrixResource;
import edu.cudenver.bios.studydesignsvc.resource.ResponseListResource;
import edu.cudenver.bios.studydesignsvc.resource.StudyDesignServerResource;

/**
 * Main Restlet application class for the Study Design Service.
 * Defines URI mappings to the appropriate requests
 * 
 * @author Uttara Sakhadeo
 */
public class StudyDesignApplication extends Application 
{
	/**
     * @method Constructor
     * @description 
     * @param parentContext
     */
	public StudyDesignApplication(Context parentContext) throws Exception
	{
		super(parentContext);		
		
		StudyDesignLogger.getInstance().info("Study Design service starting.");
	}
	
	 /**
	  * @method  createRoot()
	  * @description Define URI mappings
     */
    @Override
    public Restlet createInboundRoot() 
    {
    	// Create a router Restlet that routes each call to a new instance of Resource.
        Router router = new Router(getContext());       
        // Defines only one default route, self-identifies server
        router.attachDefault(DefaultResource.class);              
        /*-----------------------
         * Study Design Resource
         * ----------------------*/
        // for updating/deleting an existing study design AND
        router.attach("/study/{"+StudyDesignConstants.TAG_STUDY_UUID+"}/", StudyDesignServerResource.class); 
        // for creating a new study design AND
        router.attach("/study/", StudyDesignServerResource.class); 
        // for reading an existing verbose study design
        router.attach("/study/{"+StudyDesignConstants.TAG_STUDY_UUID+"}/"+StudyDesignConstants.TAG_VERBOSE_STUDY_DESIGN, 
        		StudyDesignServerResource.class); 
        // for reading a study design in matrix form
        //router.attach("/study/{"+StudyDesignConstants.TAG_STUDY_UUID+"}/", StudyDesignObjectResource.class);
        // to delete current study design
        //router.attach("/study/{"+StudyDesignConstants.TAG_STUDY_UUID+"}/", StudyDesignObjectResource.class);      
        /*-----------------
         * ConfidenceInterval Resource
         * -----------------*/        
        router.attach("/study/{"+StudyDesignConstants.TAG_STUDY_UUID+"}/"+StudyDesignConstants.TAG_CONFIDENCE_INTERVAL, 
        		ConfidenceIntervalServerResource.class);
        /*-----------------
         * List Resource
         * -----------------*/
        // for updating/deleting a list
        /*router.attach("/study/{"+StudyDesignConstants.TAG_STUDY_UUID+"}/"+StudyDesignConstants.TAG_LIST, ListResource.class);     
        // for creating a list
        router.attach("/study/{"+StudyDesignConstants.TAG_STUDY_UUID+"}/"+StudyDesignConstants.TAG_LIST+
        		"/{"+StudyDesignConstants.TAG_LIST_NAME+"}", ListResource.class); */
        /*-----------------
         * Matrix Resource
         *-----------------*/
        // for creating/updating a matrix
       /* router.attach("/study/{"+StudyDesignConstants.TAG_STUDY_UUID+"}/"+StudyDesignConstants.TAG_MATRIX, MatrixResource.class);
        // for reading/deleting a matrix
        router.attach("/study/{"+StudyDesignConstants.TAG_STUDY_UUID+"}/"+StudyDesignConstants.TAG_MATRIX+
        		"/{"+StudyDesignConstants.TAG_MATRIX_NAME+"}", MatrixResource.class); */
        /*----------------------------------------
         * Between Subject Effect object Resource
         *----------------------------------------*/
       /* router.attach("/study/{"+StudyDesignConstants.TAG_STUDY_UUID+"}/"+StudyDesignConstants.TAG_PREDICTOR_LIST,BetweenSubjectEffectResource.class);*/
        /*----------------------------------------
         * Within Subject Effect object Resource
         *----------------------------------------*/
    	/* Response List */
        /*router.attach("/study/{"+StudyDesignConstants.TAG_STUDY_UUID+"}/"+StudyDesignConstants.TAG_RESPONSE_LIST,ResponseListResource.class);*/
        /* Clustering */
        //router.attach("/study/{"+StudyDesignConstants.TAG_STUDY_UUID+"}/"+StudyDesignConstants.TAG_CLUSTERING,.class);
        /* Repeated Measures */ 
        //router.attach("/study/{"+StudyDesignConstants.TAG_STUDY_UUID+"}/"+StudyDesignConstants.TAG_,.class);
        /*----------------------------------------
         * Hypothesis object Resource
         *----------------------------------------*/
        //router.attach("/study/{"+StudyDesignConstants.TAG_STUDY_UUID+"}/"+StudyDesignConstants.TAG_,.class);        
        
        return router;
    }
}
