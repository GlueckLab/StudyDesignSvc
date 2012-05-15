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

import edu.ucdenver.bios.studydesignsvc.resource.BetaScaleRetrieveServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.BetaScaleServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.BetweenParticipantRetrieveServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.BetweenParticipantServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.ClusterNodeRetrieveServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.ClusterNodeServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.ConfidenceIntervalRetrieveServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.ConfidenceIntervalServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.CovarianceRetrieveServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.CovarianceServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.CovarianceSetRetrieveServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.CovarianceSetServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.DefaultResource;
import edu.ucdenver.bios.studydesignsvc.resource.HypothesisRetrieveServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.HypothesisServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.HypothesisSetRetrieveServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.HypothesisSetServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.MatrixRetrieveServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.MatrixServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.MatrixSetRetrieveServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.MatrixSetServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.NominalPowerRetrieveServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.NominalPowerServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.PowerCurveRetrieveServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.PowerCurveServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.PowerMethodRetrieveServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.PowerMethodServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.QuantileRetrieveServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.QuantileServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.RelativeGroupSizeRetrieveServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.RelativeGroupSizeServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.RepeatedMeasuresRetrieveServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.RepeatedMeasuresServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.ResponsesRetrieveServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.ResponsesServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.SampleSizeRetrieveServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.SampleSizeServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.SaveAsServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.SigmaScaleRetrieveServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.SigmaScaleServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.StatisticalTestRetrieveServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.StatisticalTestServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.StudyDesignServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.StudyDesignUploadRetrieveServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.StudyRetrieveServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.TypeIErrorRetrieveServerResource;
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
        router.attach("/study/retrieve", StudyRetrieveServerResource.class);
        /* Study Design Retrieve Resource */// TODO: REMOVE THIS RESOURCE
        // router.attach("/studyRetrieve",
        // StudyDesignRetrieveServerResource.class);
        /* Study Design Upload Retrieve Resource */
        router.attach("/studyUpload", UploadServerResource.class);
        router.attach("/studyUploadRetrieve",
                StudyDesignUploadRetrieveServerResource.class);
        /* ConfidenceInterval Resource */

        router.attach("/confidenceIntervalDescription",
                ConfidenceIntervalServerResource.class);
        router.attach("/confidenceIntervalDescription/retrieve",
                ConfidenceIntervalRetrieveServerResource.class);
        /* Power Curve Description */
        router.attach("/powerCurveDescription", PowerCurveServerResource.class);
        router.attach("/powerCurveDescription/retrieve",
                PowerCurveRetrieveServerResource.class);

        /* Beta Scale List Resource */
        router.attach("/betaScaleList", BetaScaleServerResource.class);
        router.attach("/betaScaleList/retrieve",
                BetaScaleRetrieveServerResource.class);

        /* Alpha List Resource */
        router.attach("/alphaList", TypeIErrorServerResource.class);
        router.attach("/alphaList/retrieve",
                TypeIErrorRetrieveServerResource.class);

        /* Relative Group Size List Resource */
        router.attach("/relativeGroupSizeList",
                RelativeGroupSizeServerResource.class);
        router.attach("/relativeGroupSizeList/retrieve",
                RelativeGroupSizeRetrieveServerResource.class);

        /* Sample Size List Resource */
        router.attach("/sampleSizeList", SampleSizeServerResource.class);
        router.attach("/sampleSizeList/retrieve",
                SampleSizeRetrieveServerResource.class);

        /* Sigma Scale List Resource */
        router.attach("/sigmaScaleList", SigmaScaleServerResource.class);
        router.attach("/sigmaScaleList/retrieve",
                SigmaScaleRetrieveServerResource.class);

        /* Statistical Test List Resource */
        router.attach("/testList", StatisticalTestServerResource.class);
        router.attach("/testList/retrieve",
                StatisticalTestRetrieveServerResource.class);

        /* Power Method List Resource */
        router.attach("/powerMethodList", PowerMethodServerResource.class);
        router.attach("/powerMethodList/retrieve",
                PowerMethodRetrieveServerResource.class);

        /* Quantile List Resource */
        router.attach("/quantileList", QuantileServerResource.class);
        router.attach("/quantileList/retrieve",
                QuantileRetrieveServerResource.class);

        /* Response List */
        router.attach("/responseList", ResponsesServerResource.class);
        router.attach("/responseList/retrieve",
                ResponsesRetrieveServerResource.class);

        /* Nominal Power List */
        router.attach("/nominalPowerList", NominalPowerServerResource.class);
        router.attach("/nominalPowerList/retrieve",
                NominalPowerRetrieveServerResource.class);

        /* Matrix Resource */
        router.attach("/matrix", MatrixServerResource.class);
        router.attach("/matrix/retrieve", MatrixRetrieveServerResource.class);
        router.attach("/matrixSet", MatrixSetServerResource.class);
        router.attach("/matrixSet/retrieve",
                MatrixSetRetrieveServerResource.class);

        /* Between Subject Effect object Resource */
        router.attach("/betweenParticipantFactor",
                BetweenParticipantServerResource.class);
        router.attach("/betweenParticipantFactor/retrieve",
                BetweenParticipantRetrieveServerResource.class);

        /* Clustering */
        router.attach("/clustering", ClusterNodeServerResource.class);
        router.attach("/clustering/retrieve",
                ClusterNodeRetrieveServerResource.class);

        /* Repeated Measures */
        router.attach("/repeatedMeasures", RepeatedMeasuresServerResource.class);
        router.attach("/repeatedMeasures/retrieve",
                RepeatedMeasuresRetrieveServerResource.class);

        /* Hypothesis object Resource */
        router.attach("/hypothesis", HypothesisServerResource.class);
        router.attach("/hypothesis/retrieve",
                HypothesisRetrieveServerResource.class);
        router.attach("/hypothesisSet", HypothesisSetServerResource.class);
        router.attach("/hypothesisSet/retrieve",
                HypothesisSetRetrieveServerResource.class);

        /* Covariance */
        router.attach("/covariance", CovarianceServerResource.class);
        router.attach("/covariance/retrieve",
                CovarianceRetrieveServerResource.class);
        router.attach("/covarianceSet", CovarianceSetServerResource.class);
        router.attach("/covarianceSet/retrieve",
                CovarianceSetRetrieveServerResource.class);

        /* Study Design Save */
        router.attach("/saveas", SaveAsServerResource.class);
        /* Study Design Upload */
        router.attach("/upload", UploadServerResource.class);

        return router;

    }
}
