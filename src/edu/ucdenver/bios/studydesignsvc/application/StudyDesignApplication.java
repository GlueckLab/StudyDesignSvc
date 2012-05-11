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
import edu.ucdenver.bios.studydesignsvc.resource.CovarianceServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.CovarianceSetServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.DefaultResource;
import edu.ucdenver.bios.studydesignsvc.resource.HypothesisServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.HypothesisSetServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.MatrixServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.MatrixSetServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.NominalPowerServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.PowerCurveServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.PowerMethodServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.QuantileServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.RelativeGroupSizeServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.RepeatedMeasuresServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.ResponsesServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.SampleSizeServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.SaveAsServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.SigmaScaleServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.StatisticalTestServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.StudyDesignServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.StudyDesignUploadRetrieveServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.TypeIErrorServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.UploadServerResource;

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
     * @param parentContext
     *            the parent context
     * @throws Exception
     *             the exception
     */
    public StudyDesignApplication(final Context parentContext) throws Exception {
        super(parentContext);

        StudyDesignLogger.getInstance().info("Study Design service starting.");
    }

    /**
     * Creates the inbound root.
     * 
     * @return the restlet
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
        /* Study Design Retrieve Resource */// TODO: REMOVE THIS RESOURCE
        //router.attach("/studyRetrieve", StudyDesignRetrieveServerResource.class);
        /* Study Design Upload Retrieve Resource */
        router.attach("/studyUpload",
                UploadServerResource.class);
        router.attach("/studyUploadRetrieve",
                StudyDesignUploadRetrieveServerResource.class);
        /* ConfidenceInterval Resource */
        router.attach("/confidenceIntervalDescription",
                ConfidenceIntervalServerResource.class);
        /* Power Curve Description */
        router.attach("/powerCurveDescription", PowerCurveServerResource.class);

        /* Beta Scale List Resource */
        router.attach("/betaScaleList", BetaScaleServerResource.class);
        router.attach("/betaScaleList/{uuid}", BetaScaleServerResource.class);
        /* Alpha List Resource */
        router.attach("/alphaList", TypeIErrorServerResource.class);
        /* Relative Group Size List Resource */
        router.attach("/relativeGroupSizeList",
                RelativeGroupSizeServerResource.class);
        /* Sample Size List Resource */
        router.attach("/sampleSizeList", SampleSizeServerResource.class);
        /* Sigma Scale List Resource */
        router.attach("/sigmaScaleList", SigmaScaleServerResource.class);
        /* Statistical Test List Resource */
        router.attach("/testList", StatisticalTestServerResource.class);
        /* Power Method List Resource */
        router.attach("/powerMethodList", PowerMethodServerResource.class);
        /* Quantile List Resource */
        router.attach("/quantileList", QuantileServerResource.class);
        /* Response List */
        router.attach("/responseList", ResponsesServerResource.class);
        /* Nominal Power List */
        router.attach("/nominalPowerList", NominalPowerServerResource.class);

        /* Matrix Resource */
        router.attach("/matrix", MatrixServerResource.class);
        router.attach("/matrixSet", MatrixSetServerResource.class);
        /* Between Subject Effect object Resource */
        router.attach("/betweenParticipantFactor",
                BetweenParticipantServerResource.class);
        /* Clustering */
        router.attach("/clustering", ClusterNodeServerResource.class);
        /* Repeated Measures */
        router.attach("/repeatedMeasures", RepeatedMeasuresServerResource.class);
        /* Hypothesis object Resource */
        router.attach("/hypothesis", HypothesisServerResource.class);
        router.attach("/hypothesisSet", HypothesisSetServerResource.class);
        /* Covariance */
        router.attach("/covariance", CovarianceServerResource.class);
        router.attach("/covarianceSet", CovarianceSetServerResource.class);
        
        /* Study Design Save*/
        router.attach("/saveas",SaveAsServerResource.class);
        /*Study Design Upload*/
        router.attach("/upload",UploadServerResource.class);
        
        return router;

    }
}
