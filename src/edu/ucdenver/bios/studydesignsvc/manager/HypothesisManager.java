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
package edu.ucdenver.bios.studydesignsvc.manager;

import java.util.ArrayList;
import java.util.List;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import edu.ucdenver.bios.webservice.common.domain.BetweenParticipantFactor;
import edu.ucdenver.bios.webservice.common.domain.Hypothesis;
import edu.ucdenver.bios.webservice.common.domain.HypothesisBetweenParticipantMapping;
import edu.ucdenver.bios.webservice.common.domain.HypothesisRepeatedMeasuresMapping;
import edu.ucdenver.bios.webservice.common.domain.RepeatedMeasuresNode;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.domain.UuidHypothesis;
import edu.ucdenver.bios.webservice.common.domain.UuidHypothesisType;
import edu.ucdenver.bios.webservice.common.enums.HypothesisTypeEnum;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Manager class which provides CRUD functionality for Hypothesis object.
 * 
 * @author Uttara Sakhadeo
 */
public class HypothesisManager extends StudyDesignParentManager {

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

    /**
     * Delete.
     * 
     * @param uuid
     *            the uuid
     * @param hypothesis
     *            the hypothesis
     * @return the hypothesis
     */
    private Hypothesis delete(final byte[] uuid, final Hypothesis hypothesis) {
        // Hypothesis deletedSet = null;
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        try {
            if (hypothesis != null) {
                session.delete(hypothesis);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete Hypothesis object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return hypothesis;
    }

    /**
     * Instantiates a new hypothesis manager.
     * 
     * @throws BaseManagerException
     *             the base manager exception
     */
    public HypothesisManager() throws BaseManagerException {
        super();
    }

    /**
     * Retrieve Hypothesis.
     * 
     * @param uuidHypothesisType
     *            the uuid hypothesis type
     * @return the hypothesis
     */
    public final Hypothesis retrieve(final UuidHypothesisType uuidHypothesisType) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        Hypothesis originalHypothesis = null;
        HypothesisTypeEnum hypothesisType = uuidHypothesisType.getType();
        byte[] uuid = uuidHypothesisType.getUuid();
        try {
            /*
             * Retrieve Study Design for given uuid
             */
            StudyDesign studyDesign = get(uuid);
            /*
             * Retrieve Original Hypothesis Object
             */
            if (studyDesign != null) {
                originalHypothesis = studyDesign
                        .getHypothesisFromSet(hypothesisType);
                if (originalHypothesis == null) {
                    originalHypothesis = null;
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete Hypothesis object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return originalHypothesis;
    }

    /**
     * Delete Hypothesis.
     * 
     * @param uuidHypothesisType
     *            the uuid hypothesis type
     * @return the hypothesis
     */
    public final Hypothesis delete(final UuidHypothesisType uuidHypothesisType) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        Hypothesis hypothesis = null;
        StudyDesign studyDesign = null;
        byte[] uuid = uuidHypothesisType.getUuid();
        HypothesisTypeEnum hypothesisType = uuidHypothesisType.getType();
        try {
            /*
             * Retrieve Original Hypothesis Object
             */
            studyDesign = get(uuid);
            if (studyDesign != null) {
                hypothesis = studyDesign.getHypothesisFromSet(hypothesisType);
                /*
                 * Delete Existing Hypothesis Set Object
                 */
                if (hypothesis != null) {
                    hypothesis = delete(uuid, hypothesis);
                }
                /*
                 * Update Study Design Object
                 */
                /*
                 * studyDesign.setC(null); session.update(studyDesign);
                 */
            }
        } catch (Exception e) {
            hypothesis = null;
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete Hypothesis object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        /*
         * Return Hypothesis
         */
        return hypothesis;
    }

    /**
     * Save or update Hypothesis.
     * 
     * @param uuidHypothesis
     *            the uuid hypothesis
     * @param isCreation
     *            the is creation
     * @return the hypothesis
     */
    public final Hypothesis saveOrUpdate(final UuidHypothesis uuidHypothesis,
            final boolean isCreation) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        StudyDesign studyDesign = null;
        Hypothesis originalHypothesis = null;
        Hypothesis newHypothesis = uuidHypothesis.getHypothesis();
        byte[] uuid = uuidHypothesis.getUuid();
        HypothesisTypeEnum hypothesisType = newHypothesis.getType();
        boolean flagBetweenParticipant = false;
        boolean flagRepeatedMeasures = false;
        List<BetweenParticipantFactor> studyBetweenParticipantList = null;
        List<RepeatedMeasuresNode> studyRepeatedMeasuresTree = null;

        try {
            /*
             * Retrieve Study Design Object
             */
            studyDesign = get(uuid);
            if (studyDesign != null) {
                originalHypothesis = studyDesign
                        .getHypothesisFromSet(hypothesisType);
                /*
                 * Delete Existing Hypothesis Set Object
                 */
                if (originalHypothesis != null) {
                    delete(uuid, originalHypothesis);
                }

                /*
                 * Fetch List of BetweenParticipantFactors from existing
                 * StudyDesign object.
                 */
                if (!studyDesign.getBetweenParticipantFactorList().isEmpty()) {
                    studyBetweenParticipantList = studyDesign
                            .getBetweenParticipantFactorList();
                }
                /*
                 * Fetch List of RepeatedMeasures from existing StudyDesign
                 * object.
                 */
                if (!studyDesign.getRepeatedMeasuresTree().isEmpty()) {
                    studyRepeatedMeasuresTree = studyDesign
                            .getRepeatedMeasuresTree();
                }

                /*
                 * Check validity of each BetweenParticipantFactors sent through
                 * new Hypothesis object.
                 */
                if (studyBetweenParticipantList != null
                        && !studyBetweenParticipantList.isEmpty()) {
                    flagBetweenParticipant = checkBetweenParticipantFactorEntry(
                            studyBetweenParticipantList, newHypothesis);
                }
                /*
                 * Check validity of each RepeatedMeasures sent through new
                 * Hypothesis object.
                 */
                if (studyRepeatedMeasuresTree != null
                        && !studyRepeatedMeasuresTree.isEmpty()) {
                    flagRepeatedMeasures = checkRepeatedMeasuresNodeEntry(
                            studyRepeatedMeasuresTree, newHypothesis);
                }
                /*
                 * Update Hypothesis object for IDs. Case : Hypothesis involving
                 * both BetweenParticipantFactors and RepeatedMeasures.
                 */
                if (flagBetweenParticipant && flagRepeatedMeasures) {
                    newHypothesis = setEntry(studyBetweenParticipantList,
                            studyRepeatedMeasuresTree, newHypothesis);
                }
                /*
                 * Update Hypothesis object for IDs. Case : Hypothesis involving
                 * only BetweenParticipantFactors.
                 */
                else if (flagBetweenParticipant) {
                    newHypothesis = setEntry(studyBetweenParticipantList, null,
                            newHypothesis);
                }
                /*
                 * Update Hypothesis object for IDs. Case : Hypothesis involving
                 * only RepeatedMeasures.
                 */
                else if (flagRepeatedMeasures) {
                    newHypothesis = setEntry(null, studyRepeatedMeasuresTree,
                            newHypothesis);
                }
                if (isCreation) {
                    session.save(newHypothesis);
                } else {
                    session.update(newHypothesis);
                }
                /*
                 * Update Study Design Object
                 */
                studyDesign.setHypothesisToSet(newHypothesis);
                session.update(studyDesign);
                /*
                 * Return Persisted Hypothesis
                 */
            }
        } catch (Exception e) {
            newHypothesis = null;
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to save Hypothesis object : " + e.getMessage());
        }
        return newHypothesis;
    }
}
