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
import edu.ucdenver.bios.studydesignsvc.resource.BetweenParticipantServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.ClusterNodeServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.ConfidenceIntervalServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.DefaultResource;
import edu.ucdenver.bios.studydesignsvc.resource.HypothesisServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.MatrixServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.NominalPowerServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.PowerMethodServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.QuantileServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.RelativeGroupSizeServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.RepeatedMeasuresServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.ResponsesServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.SampleSizeServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.SigmaScaleServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.StatisticalTestServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.StudyDesignRetrieveServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.StudyDesignServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.StudyDesignUploadRetrieveResource;
import edu.ucdenver.bios.studydesignsvc.resource.StudyDesignUploadRetrieveServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.TypeIErrorServerResource;

// TODO: Auto-generated Javadoc
/**
 * Main Restlet application class for the Study Design Service. Defines URI
 * mappings to the appropriate requests
 * 
 * @author Uttara Sakhadeo
 */
public class StudyDesignApplication extends Application {
	
	/**
	 * Instantiates a new study design application.
	 *
	 * @param parentContext the parent context
	 * @throws Exception the exception
	 * @method Constructor
	 * @description
	 */
	public StudyDesignApplication(final Context parentContext) throws Exception {
		super(parentContext);

		StudyDesignLogger.getInstance().info("Study Design service starting.");
	}

	/**
	 * Creates the inbound root.
	 *
	 * @return the restlet
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
		router.attach("/study", StudyDesignServerResource.class);
		/* Study Design Retrieve Resource */
        router.attach("/studyRetrieve", StudyDesignRetrieveServerResource.class);
		/* Study Design Upload Retrieve Resource */
        router.attach("/studyUploadRetrieve", StudyDesignUploadRetrieveServerResource.class);
		/* ConfidenceInterval Resource */
		router.attach("/confidenceIntervalDescription",
				ConfidenceIntervalServerResource.class);
		/* Power Curve Description */
		router.attach("/"+StudyDesignConstants.TAG_POWER_CURVE_DESCRIPTION,
				ConfidenceIntervalServerResource.class);
		
		/* Beta Scale List Resource */
		router.attach("/betaScaleList",BetaScaleServerResource.class);
		/* Alpha List Resource */
        router.attach("/"+StudyDesignConstants.TAG_ALPHA_LIST,
                TypeIErrorServerResource.class);
        /* Relative Group Size List Resource */
        router.attach("/"+StudyDesignConstants.TAG_RELATIVE_GROUP_SIZE_LIST,
                RelativeGroupSizeServerResource.class);
        /* Sample Size List Resource */
        router.attach("/"+StudyDesignConstants.TAG_SAMPLE_SIZE_LIST,
                SampleSizeServerResource.class);
        /* Sigma Scale List Resource */
        router.attach("/"+StudyDesignConstants.TAG_SIGMA_SCALE_LIST,
                SigmaScaleServerResource.class);
        /* Statistical Test List Resource */
        router.attach("/"+StudyDesignConstants.TAG_TEST_LIST,
                StatisticalTestServerResource.class);
        /* Power Method List Resource */
        router.attach("/"+StudyDesignConstants.TAG_POWER_METHOD_LIST,
                PowerMethodServerResource.class);
        /* Quantile List Resource */
        router.attach("/"+StudyDesignConstants.TAG_QUANTILE_LIST,
                QuantileServerResource.class);
        /* Response List */
        router.attach("/"+StudyDesignConstants.TAG_RESPONSE_LIST,
                ResponsesServerResource.class);
        /* Nominal Power List */
        router.attach("/"+StudyDesignConstants.TAG_NOMINAL_POWER_LIST,
                NominalPowerServerResource.class);
        
		/* Matrix Resource */
		router.attach("/"+StudyDesignConstants.TAG_MATRIX,
                MatrixServerResource.class);
		/* Between Subject Effect object Resource */
		router.attach("/"+StudyDesignConstants.TAG_BETWEEN_PARTICIPANT_FACTOR,
                BetweenParticipantServerResource.class);		
		/* Clustering */
		router.attach("/"+StudyDesignConstants.TAG_CLUSTERING,
                ClusterNodeServerResource.class);
		/* Repeated Measures */
		router.attach("/"+StudyDesignConstants.TAG_REPEATEDMEASURES,
                RepeatedMeasuresServerResource.class);
		/* Hypothesis object Resource */
		router.attach("/"+StudyDesignConstants.TAG_HYPOTHESIS,
                HypothesisServerResource.class);
		return router;
	}
}
