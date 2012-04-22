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

import java.util.ArrayList;
import java.util.List;

import org.restlet.data.Status;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.ucdenver.bios.studydesignsvc.manager.HypothesisManager;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.domain.BetweenParticipantFactor;
import edu.ucdenver.bios.webservice.common.domain.Hypothesis;
import edu.ucdenver.bios.webservice.common.domain.HypothesisBetweenParticipantMapping;
import edu.ucdenver.bios.webservice.common.domain.HypothesisRepeatedMeasuresMapping;
import edu.ucdenver.bios.webservice.common.domain.HypothesisSet;
import edu.ucdenver.bios.webservice.common.domain.RepeatedMeasuresNode;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.domain.UuidHypothesis;
import edu.ucdenver.bios.webservice.common.domain.UuidHypothesisType;
import edu.ucdenver.bios.webservice.common.enums.HypothesisTypeEnum;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Server Resource class for handling requests for the Hypothesis object. 
 * See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
/**
 * @author sakhadeu
 * 
 */
public class HypothesisServerResource extends ServerResource implements
        HypothesisResource {

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.ucdenver.bios.studydesignsvc.resource.HypothesisResource#retrieve
     * (edu.ucdenver.bios.webservice.common.domain.UuidHypothesisType)
     */
    @Get("application/json")
    public Hypothesis retrieve(UuidHypothesisType uuidType) {
        StudyDesignManager studyDesignManager = null;
        boolean uuidFlag;
        byte[] uuid = uuidType.getUuid();
        HypothesisTypeEnum type = uuidType.getType();
        Hypothesis hypothesis = null;
        if (uuid == null)
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        if (type == null)
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no type of Hypothesis specified");
        try {
            /*
             * ---------------------------------------------------- Check for
             * existence of a UUID in Study Design object
             * ----------------------------------------------------
             */
            studyDesignManager = new StudyDesignManager();
            studyDesignManager.beginTransaction();
            uuidFlag = studyDesignManager.hasUUID(uuid);
            if (uuidFlag) {
                StudyDesign studyDesign = studyDesignManager.get(uuid);
                if (studyDesign != null)
                    hypothesis = studyDesign.getHypothesisFromSet(type);
            }
            studyDesignManager.commit();
        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (studyDesignManager != null) {
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException re) {
                    hypothesis = null;
                }
            }
            hypothesis = null;
        } catch (StudyDesignException sde) {
            System.out.println(sde.getMessage());
            StudyDesignLogger.getInstance().error(sde.getMessage());
            if (studyDesignManager != null) {
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException re) {
                    hypothesis = null;
                }
            }
            hypothesis = null;
        }
        return hypothesis;
    }

    /**
     * Check between participant factor id.
     * 
     * @param studyBetweenParticipantList
     *            the study between participant list
     * @param id
     *            the id
     * @return true, if successful
     */
    private boolean checkBetweenParticipantFactorId(
            List<BetweenParticipantFactor> studyBetweenParticipantList, int id) {
        for (BetweenParticipantFactor betweenParticipantFactor : studyBetweenParticipantList) {
            if (betweenParticipantFactor.getId() == id)
                return true;
        }
        return false;
    }

    /**
     * Check between participant factor entry.
     * 
     * @param studyBetweenParticipantList
     *            the study between participant list
     * @param hypothesis
     *            the hypothesis
     * @return true, if successful
     */
    private boolean checkBetweenParticipantFactorEntry(
            List<BetweenParticipantFactor> studyBetweenParticipantList,
            Hypothesis hypothesis) {
        try {
            boolean flag = false;
            List<BetweenParticipantFactor> list = hypothesis
                    .getBetweenParticipantFactorList();
            for (BetweenParticipantFactor betweenParticipantFactor : list) {
                if (checkBetweenParticipantFactorId(
                        studyBetweenParticipantList,
                        betweenParticipantFactor.getId()))
                    flag = true;
                else {
                    flag = false;
                    break;
                }
            }
            return flag;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Check repeated measures node id.
     * 
     * @param studyRepeatedMeasuresTree
     *            the study repeated measures tree
     * @param id
     *            the id
     * @return true, if successful
     */
    private boolean checkRepeatedMeasuresNodeId(
            List<RepeatedMeasuresNode> studyRepeatedMeasuresTree, int id) {
        for (RepeatedMeasuresNode repeatedMeasuresNode : studyRepeatedMeasuresTree) {
            if (repeatedMeasuresNode.getId() == id)
                return true;
        }
        return false;
    }

    /**
     * Check repeated measures node entry.
     * 
     * @param studyRepeatedMeasuresTree
     *            the study repeated measures tree
     * @param hypothesis
     *            the hypothesis
     * @return true, if successful
     */
    private boolean checkRepeatedMeasuresNodeEntry(
            List<RepeatedMeasuresNode> studyRepeatedMeasuresTree,
            Hypothesis hypothesis) {
        try {
            boolean flag = false;
            List<RepeatedMeasuresNode> list = hypothesis
                    .getRepeatedMeasuresList();
            for (RepeatedMeasuresNode repeatedMeasuresNode : list) {
                if (checkRepeatedMeasuresNodeId(studyRepeatedMeasuresTree,
                        repeatedMeasuresNode.getId()))
                    flag = true;
                else {
                    flag = false;
                    break;
                }
            }
            return flag;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Gets the between participant factor.
     * 
     * @param studyBetweenParticipantList
     *            the study between participant list
     * @param id
     *            the id
     * @return the between participant factor
     */
    private BetweenParticipantFactor getBetweenParticipantFactor(
            List<BetweenParticipantFactor> studyBetweenParticipantList, int id) {
        for (BetweenParticipantFactor betweenParticipantFactor : studyBetweenParticipantList) {
            if (betweenParticipantFactor.getId() == id)
                return betweenParticipantFactor;
        }
        return null;
    }

    /**
     * Gets the repeated measures node.
     * 
     * @param studyRepeatedMeasuresTree
     *            the study repeated measures tree
     * @param id
     *            the id
     * @return the repeated measures node
     */
    private RepeatedMeasuresNode getRepeatedMeasuresNode(
            List<RepeatedMeasuresNode> studyRepeatedMeasuresTree, int id) {
        for (RepeatedMeasuresNode repeatedMeasuresNode : studyRepeatedMeasuresTree) {
            if (repeatedMeasuresNode.getId() == id)
                return repeatedMeasuresNode;
        }
        return null;
    }

    /**
     * Sets the entry.
     * 
     * @param studyBetweenParticipantList
     *            the study between participant list
     * @param studyRepeatedMeasuresTree
     *            the study repeated measures tree
     * @param hypothesis
     *            the hypothesis
     * @return the hypothesis
     */
    private Hypothesis setEntry(
            List<BetweenParticipantFactor> studyBetweenParticipantList,
            List<RepeatedMeasuresNode> studyRepeatedMeasuresTree,
            Hypothesis hypothesis) {
        Hypothesis newHypothesis = new Hypothesis();
        /*
         * HypothesisSet set = new HypothesisSet(hypothesisSet.getUuid());
         * Iterator<Hypothesis> itr =
         * hypothesisSet.getHypothesisSet().iterator();
         * 
         * while(itr.hasNext()) {
         */
        List<HypothesisBetweenParticipantMapping> newBList = null;
        List<HypothesisRepeatedMeasuresMapping> newRList = null;
        if (studyBetweenParticipantList != null
                && !studyBetweenParticipantList.isEmpty()) {
            List<HypothesisBetweenParticipantMapping> bList = hypothesis
                    .getBetweenParticipantFactorMapList();
            newBList = new ArrayList<HypothesisBetweenParticipantMapping>();
            for (HypothesisBetweenParticipantMapping betweenParticipantFactorMap : bList) {
                // BetweenParticipantFactor b =
                // getBetweenParticipantFactor(studyBetweenParticipantList,betweenParticipantFactor.getId());
                BetweenParticipantFactor b = getBetweenParticipantFactor(
                        studyBetweenParticipantList,
                        betweenParticipantFactorMap
                                .getBetweenParticipantFactor().getId());
                if (b != null) {
                    newBList.add(new HypothesisBetweenParticipantMapping(
                            betweenParticipantFactorMap.getType(), b));
                }
            }
        }
        if (studyRepeatedMeasuresTree != null
                && !studyRepeatedMeasuresTree.isEmpty()) {
            List<HypothesisRepeatedMeasuresMapping> rList = hypothesis
                    .getRepeatedMeasuresMapTree();
            newRList = new ArrayList<HypothesisRepeatedMeasuresMapping>();
            for (HypothesisRepeatedMeasuresMapping repeatedMeasuresMap : rList) {
                RepeatedMeasuresNode r = getRepeatedMeasuresNode(
                        studyRepeatedMeasuresTree, repeatedMeasuresMap
                                .getRepeatedMeasuresNode().getId());
                if (r != null) {
                    newRList.add(new HypothesisRepeatedMeasuresMapping(
                            repeatedMeasuresMap.getType(), r));
                }
            }
        }
        newHypothesis.setBetweenParticipantFactorMapList(newBList);
        newHypothesis.setType(hypothesis.getType());
        newHypothesis.setRepeatedMeasuresMapTree(newRList);

        return newHypothesis;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.ucdenver.bios.studydesignsvc.resource.HypothesisResource#create(edu
     * .ucdenver.bios.webservice.common.domain.UuidHypothesis)
     */    
    @Post("application/json")
    public Hypothesis create(UuidHypothesis uuidHypothesis) {
        HypothesisManager hypothesisManager = null;
        StudyDesignManager studyDesignManager = null;
        StudyDesign studyDesign = null;
        boolean uuidFlag = false;
        Hypothesis hypothesis = uuidHypothesis.getHypothesis();
        byte[] uuid = uuidHypothesis.getUuid();
        HypothesisTypeEnum type = hypothesis.getType();
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        if (hypothesis == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no Hypothesis specified");
        }
        try {
            /*
             * ---------------------------------------------------- Check for
             * existence of a UUID in Study Design object
             * ----------------------------------------------------
             */
            studyDesignManager = new StudyDesignManager();
            studyDesignManager.beginTransaction();            
                studyDesign = studyDesignManager.get(uuid);
                if(studyDesign != null)
                    uuidFlag = true;
                else {
                    throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                        "no study design UUID specified");
            }
            studyDesignManager.commit();
            /*
             * ---------------------------------------------------- Check
             * existance of BetweenParticipantEfect
             * ----------------------------------------------------
             */
            List<BetweenParticipantFactor> studyBetweenParticipantList = null;
            List<RepeatedMeasuresNode> studyRepeatedMeasuresTree = null;
            if (!studyDesign.getBetweenParticipantFactorList().isEmpty()) {
                studyBetweenParticipantList = studyDesign
                        .getBetweenParticipantFactorList();
            }
            if (!studyDesign.getRepeatedMeasuresTree().isEmpty()) {
                studyRepeatedMeasuresTree = studyDesign
                        .getRepeatedMeasuresTree();
            }
            boolean flag = studyDesign.hasHypothesis(type);
            if (uuidFlag && studyDesign != null) {
                boolean flagBetweenParticipant = false;
                boolean flagRepeatedMeasures = false;
                if (studyBetweenParticipantList != null
                        && !studyBetweenParticipantList.isEmpty()) {
                    flagBetweenParticipant = checkBetweenParticipantFactorEntry(
                            studyBetweenParticipantList, hypothesis);
                }
                if (studyRepeatedMeasuresTree != null
                        && !studyRepeatedMeasuresTree.isEmpty()) {
                    flagRepeatedMeasures = checkRepeatedMeasuresNodeEntry(
                            studyRepeatedMeasuresTree, hypothesis);
                }
                if (flagBetweenParticipant && flagRepeatedMeasures) {
                    // hypothesisSet =
                    // setBetweenParticipantFactorEntry(studyBetweenParticipantList,
                    // hypothesisSet);
                    hypothesis = setEntry(studyBetweenParticipantList,
                            studyRepeatedMeasuresTree, hypothesis);
                } else if (flagBetweenParticipant) {
                    // hypothesisSet =
                    // setBetweenParticipantFactorEntry(studyBetweenParticipantList,
                    // hypothesisSet);
                    hypothesis = setEntry(studyBetweenParticipantList, null,
                            hypothesis);
                } else if (flagRepeatedMeasures) {
                    // hypothesisSet =
                    // setBetweenParticipantFactorEntry(studyBetweenParticipantList,
                    // hypothesisSet);
                    hypothesis = setEntry(null, studyRepeatedMeasuresTree,
                            hypothesis);
                }
            }
            /*
             * ---------------------------------------------------- Remove
             * existing Hypothesis for this object
             * ----------------------------------------------------
             */
            if (uuidFlag && studyDesign.getHypothesis() != null
                    && !studyDesign.getHypothesis().isEmpty() && flag) {
                removeFrom(studyDesign, type);
            }
            if (uuidFlag && studyDesign.getHypothesis() != null) {
                hypothesisManager = new HypothesisManager();
                hypothesisManager.beginTransaction();
                hypothesisManager.saveOrUpdate(hypothesis, true);
                hypothesisManager.commit();
                /*
                 * ---------------------------------------------------- Set
                 * reference of HypothesisSet Object to Study Design object
                 * ----------------------------------------------------
                 */
                studyDesign.setHypothesisToSet(hypothesis);
                studyDesignManager = new StudyDesignManager();
                studyDesignManager.beginTransaction();
                studyDesign = studyDesignManager.saveOrUpdate(studyDesign,
                        false);
                studyDesignManager.commit();
            }
        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (hypothesisManager != null) {
                try {
                    hypothesisManager.rollback();
                } catch (BaseManagerException re) {
                    hypothesis = null;
                }
            }
            hypothesis = null;
        } catch (StudyDesignException sde) {
            System.out.println(sde.getMessage());
            StudyDesignLogger.getInstance().error(sde.getMessage());
            if (studyDesignManager != null) {
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException re) {
                    hypothesis = null;
                }
            }
            hypothesis = null;
        }
        return hypothesis;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.ucdenver.bios.studydesignsvc.resource.HypothesisResource#update(edu
     * .ucdenver.bios.webservice.common.domain.UuidHypothesis)
     */
    @Put("application/json")
    public Hypothesis update(UuidHypothesis uuidHypothesis) {
        return create(uuidHypothesis);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.ucdenver.bios.studydesignsvc.resource.HypothesisResource#remove(edu
     * .ucdenver.bios.webservice.common.domain.UuidHypothesisType)
     */
    @Delete("application/json")
    public Hypothesis remove(UuidHypothesisType uuidType) {
        HypothesisManager hypothesisManager = null;
        StudyDesignManager studyDesignManager = null;
        boolean uuidFlag;
        byte[] uuid = uuidType.getUuid();
        HypothesisTypeEnum type = uuidType.getType();
        HypothesisSet hypothesisSet = null;
        Hypothesis hypothesis = null;
        StudyDesign studyDesign = null;
        if (uuid == null)
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        if (type == null)
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no type of Hypothesis specified");
        try {
            /*
             * ---------------------------------------------------- Check for
             * existence of a UUID in Study Design object
             * ----------------------------------------------------
             */
            studyDesignManager = new StudyDesignManager();
            studyDesignManager.beginTransaction();
            uuidFlag = studyDesignManager.hasUUID(uuid);
            if (uuidFlag) {
                studyDesign = studyDesignManager.get(uuid);
                if (studyDesign != null)
                    hypothesisSet = new HypothesisSet(uuid,
                            studyDesign.getHypothesis());
            }
            studyDesignManager.commit();
            /*
             * ---------------------------------------------------- Remove
             * existing Hypothesis objects for this object
             * ----------------------------------------------------
             */
            if (hypothesisSet != null && studyDesign.hasHypothesis(type)) {
                hypothesisManager = new HypothesisManager();
                hypothesisManager.beginTransaction();
                hypothesis = hypothesisManager.delete(uuid,
                        studyDesign.getHypothesisFromSet(type));
                hypothesisManager.commit();
                /*
                 * ---------------------------------------------------- Set
                 * reference of Hypothesis Object to Study Design object
                 * ----------------------------------------------------
                 */
                /*
                 * studyDesign.setBetaScaleList(null); studyDesignManager = new
                 * StudyDesignManager(); studyDesignManager.beginTransaction();
                 * studyDesign = studyDesignManager.saveOrUpdate(studyDesign,
                 * false); studyDesignManager.commit();
                 * hypothesisSet=studyDesign.getHypothesis();
                 */
            }
        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (hypothesisManager != null) {
                try {
                    hypothesisManager.rollback();
                } catch (BaseManagerException re) {
                    hypothesis = null;
                }
            }
            hypothesis = null;
        } catch (StudyDesignException sde) {
            System.out.println(sde.getMessage());
            StudyDesignLogger.getInstance().error(sde.getMessage());
            if (studyDesignManager != null) {
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException re) {
                    hypothesis = null;
                }
            }
            hypothesis = null;
        }
        return hypothesis;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.ucdenver.bios.studydesignsvc.resource.HypothesisResource#removeFrom
     * (edu.ucdenver.bios.webservice.common.domain.StudyDesign,
     * edu.ucdenver.bios.webservice.common.enums.HypothesisTypeEnum)
     */
    public Hypothesis removeFrom(StudyDesign studyDesign,
            HypothesisTypeEnum type) {
        HypothesisManager hypothesisManager = null;
        Hypothesis hypothesis = null;
        try {
            hypothesisManager = new HypothesisManager();
            hypothesisManager.beginTransaction();
            hypothesis = hypothesisManager.delete(studyDesign.getUuid(),
                    studyDesign.getHypothesisFromSet(type));
            hypothesisManager.commit();
        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(
                    "Failed to load Study Design information: "
                            + bme.getMessage());
            if (hypothesisManager != null)
                try {
                    hypothesisManager.rollback();
                } catch (BaseManagerException e) {
                }
            hypothesis = null;
        }
        return hypothesis;
    }
}
