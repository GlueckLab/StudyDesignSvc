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

package edu.ucdenver.bios.studydesignsvc.application;

import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import edu.ucdenver.bios.studydesignsvc.resource.BetaScaleServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.ConfidenceIntervalServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.DefaultResource;
import edu.ucdenver.bios.studydesignsvc.resource.StudyDesignServerResource;

/**
 * Main Restlet application class for the Study Design Service. Defines URI
 * mappings to the appropriate requests
 * 
 * @author Uttara Sakhadeo
 */
public class StudyDesignApplication extends Application {
	/**
	 * @method Constructor
	 * @description
	 * @param parentContext
	 */
	public StudyDesignApplication(Context parentContext) throws Exception {
		super(parentContext);

		StudyDesignLogger.getInstance().info("Study Design service starting.");
	}

	/**
	 * @method createRoot()
	 * @description Define URI mappings
	 */
	@Override
	public Restlet createInboundRoot() {
		/*
		 * Create a router Restlet that routes each call to a new instance of
		 * Resource.
		 */
		Router router = new Router(getContext());
		/* Defines only one default route, self-identifies server */
		router.attachDefault(DefaultResource.class);
		/* Study Design Resource */
		router.attach("/study/", StudyDesignServerResource.class);
		/* ConfidenceInterval Resource */
		router.attach("/"+StudyDesignConstants.TAG_CONFIDENCE_INTERVAL_DESCRIPTION,
				ConfidenceIntervalServerResource.class);
		/* Power Curve Description */
		router.attach("/"+StudyDesignConstants.TAG_POWER_CURVE_DESCRIPTION,
				ConfidenceIntervalServerResource.class);
		/* Beta Scale List Resource */
		router.attach("/"+StudyDesignConstants.TAG_BETA_SCALE_LIST,
				BetaScaleServerResource.class);
		/* Matrix Resource */

		/* Between Subject Effect object Resource */

		/* Within Subject Effect object Resource */

		/* Response List */

		/* Clustering */

		/* Repeated Measures */

		/* Hypothesis object Resource */

		return router;
	}
}
