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
package edu.ucdenver.bios.studydesignsvc.resource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializer;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.ucdenver.bios.studydesignsvc.manager.BetaScaleManager;
import edu.ucdenver.bios.studydesignsvc.manager.BetweenParticipantFactorManager;
import edu.ucdenver.bios.studydesignsvc.manager.ClusterNodeManager;
import edu.ucdenver.bios.studydesignsvc.manager.ConfidenceIntervalManager;
import edu.ucdenver.bios.studydesignsvc.manager.CovarianceManager;
import edu.ucdenver.bios.studydesignsvc.manager.HypothesisManager;
import edu.ucdenver.bios.studydesignsvc.manager.MatrixManager;
import edu.ucdenver.bios.studydesignsvc.manager.NominalPowerManager;
import edu.ucdenver.bios.studydesignsvc.manager.PowerCurveManager;
import edu.ucdenver.bios.studydesignsvc.manager.PowerMethodManager;
import edu.ucdenver.bios.studydesignsvc.manager.QuantileManager;
import edu.ucdenver.bios.studydesignsvc.manager.RelativeGroupSizeManager;
import edu.ucdenver.bios.studydesignsvc.manager.RepeatedMeasuresManager;
import edu.ucdenver.bios.studydesignsvc.manager.ResponsesManager;
import edu.ucdenver.bios.studydesignsvc.manager.SampleSizeManager;
import edu.ucdenver.bios.studydesignsvc.manager.SigmaScaleManager;
import edu.ucdenver.bios.studydesignsvc.manager.StatisticalTestManager;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.studydesignsvc.manager.TypeIErrorManager;
import edu.ucdenver.bios.webservice.common.domain.BetaScale;
import edu.ucdenver.bios.webservice.common.domain.BetaScaleList;
import edu.ucdenver.bios.webservice.common.domain.BetweenParticipantFactor;
import edu.ucdenver.bios.webservice.common.domain.ClusterNode;
import edu.ucdenver.bios.webservice.common.domain.ConfidenceIntervalDescription;
import edu.ucdenver.bios.webservice.common.domain.Covariance;
import edu.ucdenver.bios.webservice.common.domain.Hypothesis;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;
import edu.ucdenver.bios.webservice.common.domain.NominalPower;
import edu.ucdenver.bios.webservice.common.domain.PowerCurveDescription;
import edu.ucdenver.bios.webservice.common.domain.PowerMethod;
import edu.ucdenver.bios.webservice.common.domain.Quantile;
import edu.ucdenver.bios.webservice.common.domain.RelativeGroupSize;
import edu.ucdenver.bios.webservice.common.domain.RepeatedMeasuresNode;
import edu.ucdenver.bios.webservice.common.domain.ResponseNode;
import edu.ucdenver.bios.webservice.common.domain.SampleSize;
import edu.ucdenver.bios.webservice.common.domain.SigmaScale;
import edu.ucdenver.bios.webservice.common.domain.StatisticalTest;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.domain.TypeIError;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;

/**
 * Server Resource class for handling requests for the complete 
 * study design object. 
 * See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class StudyDesignServerResource extends ServerResource 
implements StudyDesignResource
{
	static Logger logger = StudyDesignLogger.getInstance();	
	StudyDesignManager studyDesignManager = null;
		
	/**
	 * Retrieve the study design matching the specified UUID.
	 * Returns "not found" if no matching designs are available
	 * @return study designs with specified UUID
	 */
	/*@Get
	public StudyDesign retrieve(byte[] uuid)
	{
		studyDesignManager = null;
		StudyDesign studyDesign = null; 
		
		try
		{
			if (uuid == null) throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
					"no study design UUID specified");
	
			studyDesignManager = new StudyDesignManager();
			studyDesignManager.beginTransaction();		
			    studyDesign = studyDesignManager.get(uuid);
			studyDesignManager.commit();
		}
		catch(BaseManagerException bme)
		{
			StudyDesignLogger.getInstance().error("StudyDesignResource : " + bme.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {studyDesign = null;}					
			}
		}	
		catch(StudyDesignException sde)
		{
			StudyDesignLogger.getInstance().error("StudyDesignResource : " + sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {studyDesign = null;}					
			}
		}						
		return studyDesign;			
	}*/
	

	
	@Put
	public StudyDesign update(StudyDesign studyDesign)
	{
	    StudyDesignManager studyDesignManager = null;
        boolean uuidFlag = false;
        byte[] uuid = studyDesign.getUuid(); 
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        try
        {       
            /*
             * ----------------------------------------------------
             * Check for existence of a UUID in Study Design object
             * ----------------------------------------------------
             */
            studyDesignManager = new StudyDesignManager();
            studyDesignManager.beginTransaction();
            uuidFlag = studyDesignManager.hasUUID(uuid);
            /*if (uuidFlag) {
                studyDesign = studyDesignManager.get(uuid);           
            }*/
            studyDesignManager.commit();
            /*
             * ---------------------------------------------------- 
             * Remove existing Study Design for this object
             * ----------------------------------------------------
             */            
            if (uuidFlag) {
                studyDesign = remove(uuid);                
            }            
            studyDesignManager = new StudyDesignManager();
            studyDesignManager.beginTransaction();      
                studyDesign = studyDesignManager.saveOrUpdate(studyDesign,true);
            studyDesignManager.commit();          
        }
        catch(BaseManagerException bme)
        {
            StudyDesignLogger.getInstance().error("StudyDesignResource : " + bme.getMessage());
            if(studyDesignManager!=null)
            {
                try {studyDesignManager.rollback();}
                catch(BaseManagerException re) {studyDesign = null;}                    
            }
        }   
        catch(StudyDesignException sde)
        {
            StudyDesignLogger.getInstance().error("StudyDesignResource : " + sde.getMessage());
            if(studyDesignManager!=null)
            {
                try {studyDesignManager.rollback();}
                catch(BaseManagerException re) {studyDesign = null;}                    
            }
        }                       
        return studyDesign;
	}

	/*
	 * to-do : 
	 * 1) While deleting study design; manually deleting first
	 * from all the children.
	 * 2) after deleting every child -> delete study design ->
	 * set the objects in the study design to obtained objects
	 * 3) change in this approach required
	 */
	@Delete
	public StudyDesign remove(byte[] uuid)
	{
		StudyDesignManager studyDesignManager = null;
		/*
         * Study Design
         */
		StudyDesign studyDesign = null; 
		/*
		 * Lists
		 */
    		/** The alpha list. */
    	    List<TypeIError> alphaList = null;
    
    	    /** The beta scale list. */
    	    List<BetaScale> betaScaleList = null;
    
    	    /** The sigma scale list. */
    	    List<SigmaScale> sigmaScaleList = null;
    
    	    /** The relative group size list. */
    	    List<RelativeGroupSize> relativeGroupSizeList = null;
    
    	    /** The sample size list. */
    	    List<SampleSize> sampleSizeList = null;
    
    	    /** The statistical test list. */
    	    List<StatisticalTest> statisticalTestList = null;
    
    	    /** The power method list. */
    	    List<PowerMethod> powerMethodList = null;
    
    	    /** The quantile list. */
    	    List<Quantile> quantileList = null;
    
    	    /** The nominal power list. */
    	    List<NominalPower> nominalPowerList = null;
    
    	    /** The response list. */
    	    List<ResponseNode> responseList = null;
    	    
    	    /** The confidence interval descriptions. */
    	    ConfidenceIntervalDescription confidenceIntervalDescriptions = null;

    	    /** The power curve descriptions. */
    	    PowerCurveDescription powerCurveDescriptions = null;
    	    
    	    /** The between participant factor list. */
    	    List<BetweenParticipantFactor> betweenParticipantFactorList = null;

    	    // Set<StudyDesignNamedMatrix> matrixSet = null;
    	    /** The repeated measures tree. */
    	    List<RepeatedMeasuresNode> repeatedMeasuresTree = null;

    	    /** The clustering tree. */
    	    List<ClusterNode> clusteringTree = null;

    	    /** The hypothesis. */
    	    Set<Hypothesis> hypothesis = new HashSet<Hypothesis>();

    	    /** The covariance. */
    	    Set<Covariance> covariance = null;

    	    /** The matrix set. */
    	    Set<NamedMatrix> matrixSet = null;
		try
		{
			if (uuid == null) throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
					"no study design UUID specified");
			studyDesignManager = new StudyDesignManager();
            studyDesignManager.beginTransaction();      
                studyDesign = studyDesignManager.get(uuid);
            studyDesignManager.commit();
            if(studyDesign!=null){
            /*
             * Lists
             */
                BetaScaleManager betaScaleManager = new BetaScaleManager();
                if(studyDesign.getBetaScaleList()!=null){
                    betaScaleManager.beginTransaction();      
                        betaScaleList = betaScaleManager.delete(uuid,studyDesign.getBetaScaleList());
                    betaScaleManager.commit();
                }
                
                SigmaScaleManager sigmaScaleManager = new SigmaScaleManager();
                if(studyDesign.getSigmaScaleList()!=null){
                    sigmaScaleManager.beginTransaction();      
                        sigmaScaleList = sigmaScaleManager.delete(uuid,studyDesign.getSigmaScaleList());
                    sigmaScaleManager.commit();
                }
                
                SampleSizeManager sampleSizeManager = new SampleSizeManager();
                if(studyDesign.getSampleSizeList()!=null){
                    sampleSizeManager.beginTransaction();      
                        sampleSizeList = sampleSizeManager.delete(uuid,studyDesign.getSampleSizeList());
                    sampleSizeManager.commit();
                }
                
                TypeIErrorManager typeIErrorManager = new TypeIErrorManager();
                if(studyDesign.getAlphaList()!=null){
                    typeIErrorManager.beginTransaction();      
                        alphaList = typeIErrorManager.delete(uuid,studyDesign.getAlphaList());
                    typeIErrorManager.commit();
                }
                
                StatisticalTestManager statisticalTestManager = new StatisticalTestManager();
                if(studyDesign.getStatisticalTestList()!=null){
                    statisticalTestManager.beginTransaction();      
                        statisticalTestList=statisticalTestManager.delete(uuid,studyDesign.getStatisticalTestList());
                    statisticalTestManager.commit();
                }
                
                RelativeGroupSizeManager relativeGroupSizeManager = new RelativeGroupSizeManager();
                if(studyDesign.getRelativeGroupSizeList()!=null){
                    relativeGroupSizeManager.beginTransaction();      
                        relativeGroupSizeList=relativeGroupSizeManager.delete(uuid,studyDesign.getRelativeGroupSizeList());
                    relativeGroupSizeManager.commit();
                }
                
                QuantileManager quantileManager = new QuantileManager();
                if(studyDesign.getQuantileList()!=null){
                    quantileManager.beginTransaction();      
                        quantileList=quantileManager.delete(uuid,studyDesign.getQuantileList());
                    quantileManager.commit();
                }
                
                ResponsesManager responsesManager = new ResponsesManager();
                if(studyDesign.getResponseList()!=null){
                    responsesManager.beginTransaction();      
                        responseList=responsesManager.delete(uuid,studyDesign.getResponseList());
                    responsesManager.commit();
                }
                
                NominalPowerManager nominalPowerManager = new NominalPowerManager();
                if(studyDesign.getNominalPowerList()!=null){
                    nominalPowerManager.beginTransaction();      
                        nominalPowerList=nominalPowerManager.delete(uuid,studyDesign.getNominalPowerList());
                    nominalPowerManager.commit();
                }
                
                PowerMethodManager powerMethodManager = new PowerMethodManager();
                if(studyDesign.getPowerMethodList()!=null){
                    powerMethodManager.beginTransaction();      
                        powerMethodList=powerMethodManager.delete(uuid,studyDesign.getPowerMethodList());
                    powerMethodManager.commit();
                }
            /*
             * Matrix
             */
                MatrixManager matrixManager = new MatrixManager();
                if(studyDesign.getMatrixSet()!=null){
                    matrixManager.beginTransaction();      
                        matrixSet=matrixManager.delete(uuid,studyDesign.getMatrixSet());
                    matrixManager.commit();
                }
            /*
             * Confidence Interval Description
             */
                ConfidenceIntervalManager confidenceIntervalManager = new ConfidenceIntervalManager();
                if(studyDesign.getConfidenceIntervalDescriptions()!=null){
                    confidenceIntervalManager.beginTransaction();      
                        confidenceIntervalDescriptions=confidenceIntervalManager.delete(uuid,studyDesign.getConfidenceIntervalDescriptions());
                    confidenceIntervalManager.commit();
                }
            /*
             * Power Curve Description
             */
                PowerCurveManager powerCurveManager = new PowerCurveManager();
                if(studyDesign.getPowerCurveDescriptions()!=null){
                    powerCurveManager.beginTransaction();      
                        powerCurveDescriptions=powerCurveManager.delete(uuid,studyDesign.getPowerCurveDescriptions());
                    powerCurveManager.commit();
                }
            /*
             * Clustering
             */
                ClusterNodeManager clusterNodeManager = new ClusterNodeManager();
                if(studyDesign.getClusteringTree()!=null){
                    clusterNodeManager.beginTransaction();      
                        clusteringTree=clusterNodeManager.delete(uuid,studyDesign.getClusteringTree());
                    clusterNodeManager.commit();
                }            
            /*
             * Covariance
             */
                CovarianceManager covarianceManager = new CovarianceManager();
                if(studyDesign.getCovariance()!=null){
                    covarianceManager.beginTransaction();      
                        covariance=covarianceManager.delete(uuid,studyDesign.getCovariance());
                    covarianceManager.commit();
                }
            /*
             * Repeated Measures
             */
                RepeatedMeasuresManager repeatedMeasuresManager = new RepeatedMeasuresManager();
                if(studyDesign.getRepeatedMeasuresTree()!=null){
                    repeatedMeasuresManager.beginTransaction();      
                        repeatedMeasuresTree=repeatedMeasuresManager.delete(uuid,studyDesign.getRepeatedMeasuresTree());
                    repeatedMeasuresManager.commit();
                }
            /*
             * Between Participant Factor
             */
                BetweenParticipantFactorManager betweenParticipantFactorManager = new BetweenParticipantFactorManager();
                if(studyDesign.getBetweenParticipantFactorList()!=null){
                    betweenParticipantFactorManager.beginTransaction();      
                        betweenParticipantFactorList=betweenParticipantFactorManager.delete(uuid,studyDesign.getBetweenParticipantFactorList());
                    betweenParticipantFactorManager.commit();
                }
            /*
             * Hypothesis
             */
                HypothesisManager hypothesisManager = new HypothesisManager();
                if(studyDesign.getHypothesis()!=null){
                    hypothesisManager.beginTransaction();      
                        hypothesis=hypothesisManager.delete(uuid,studyDesign.getHypothesis());
                    hypothesisManager.commit();
                }
            /*
             * Study Design
             */    
                studyDesignManager = new StudyDesignManager();
                studyDesignManager.beginTransaction();      
                    studyDesign=studyDesignManager.delete(uuid);
                studyDesignManager.commit();
                
            /*
             * Set Lists to the study Design
             */
                studyDesign.setBetaScaleList(betaScaleList);
                studyDesign.setSampleSizeList(sampleSizeList);
                studyDesign.setSigmaScaleList(sigmaScaleList);
                studyDesign.setAlphaList(alphaList);
                studyDesign.setNominalPowerList(nominalPowerList);
                studyDesign.setPowerMethodList(powerMethodList);
                studyDesign.setRelativeGroupSizeList(relativeGroupSizeList);
                studyDesign.setResponseList(responseList);
                studyDesign.setQuantileList(quantileList);
                studyDesign.setStatisticalTestList(statisticalTestList);
                
                studyDesign.setBetweenParticipantFactorList(betweenParticipantFactorList);
                studyDesign.setClusteringTree(clusteringTree);
                studyDesign.setConfidenceIntervalDescriptions(confidenceIntervalDescriptions);
                studyDesign.setPowerCurveDescriptions(powerCurveDescriptions);
                studyDesign.setHypothesis(hypothesis);
                studyDesign.setCovariance(covariance);
                studyDesign.setMatrixSet(matrixSet);
                studyDesign.setRepeatedMeasuresTree(repeatedMeasuresTree);
            }
		}
		catch(BaseManagerException bme)
		{
			StudyDesignLogger.getInstance().error("StudyDesignResource : " + bme.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {studyDesign = null;}					
			}
		}	
		catch(StudyDesignException sde)
		{
			StudyDesignLogger.getInstance().error("StudyDesignResource : " + sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {studyDesign = null;}					
			}
		}						
		return studyDesign;
	}

	@Post
	public StudyDesign create() 
	{
		StudyDesignManager studyDesignManager = null;		
		StudyDesign studyDesign = new StudyDesign();
		
		try
		{			
			studyDesignManager = new StudyDesignManager();
			studyDesignManager.beginTransaction();						
				byte[] uuidBytes = UUIDUtils.asByteArray(UUID.randomUUID());
				studyDesign.setUuid(uuidBytes);
				studyDesign = studyDesignManager.saveOrUpdate(studyDesign,true);
			studyDesignManager.commit();
		}
		catch(BaseManagerException bme)
		{
			StudyDesignLogger.getInstance().error("StudyDesignResource : " + bme.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {studyDesign = null;}					
			}
		}	
		catch(StudyDesignException sde)
		{
			StudyDesignLogger.getInstance().error("StudyDesignResource : " + sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {studyDesign = null;}					
			}
		}						
		return studyDesign;
	}	
}
