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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import edu.ucdenver.bios.webservice.common.domain.BetweenParticipantFactor;
import edu.ucdenver.bios.webservice.common.domain.BetweenParticipantFactorList;
import edu.ucdenver.bios.webservice.common.domain.Hypothesis;
import edu.ucdenver.bios.webservice.common.domain.HypothesisBetweenParticipantMapping;
import edu.ucdenver.bios.webservice.common.domain.HypothesisRepeatedMeasuresMapping;
import edu.ucdenver.bios.webservice.common.domain.HypothesisSet;
import edu.ucdenver.bios.webservice.common.domain.RepeatedMeasuresNode;
import edu.ucdenver.bios.webservice.common.domain.RepeatedMeasuresNodeList;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Manager class which provides CRUD functionality for RepeatedMeasuresNode
 * object.
 * 
 * @author Uttara Sakhadeo
 */
public class RepeatedMeasuresManager extends StudyDesignParentManager {

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
     * Check between participant factor entry.
     * 
     * @param studyBetweenParticipantList
     *            the study between participant list
     * @param hypothesisSet
     *            the hypothesis set
     * @return true, if successful
     */
    private boolean checkBetweenParticipantFactorEntry(
            List<BetweenParticipantFactor> studyBetweenParticipantList,
            Set<Hypothesis> hypothesisSet) {
        try {
            Iterator<Hypothesis> itr = hypothesisSet.iterator();
            boolean flag = false;
            while (itr.hasNext()) {
                Hypothesis hypothesis = itr.next();
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
            }
            return flag;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Check repeated measures node entry.
     * 
     * @param studyRepeatedMeasuresTree
     *            the study repeated measures tree
     * @param hypothesisSet
     *            the hypothesis set
     * @return true, if successful
     */
    private boolean checkRepeatedMeasuresNodeEntry(
            List<RepeatedMeasuresNode> studyRepeatedMeasuresTree,
            Set<Hypothesis> hypothesisSet) {
        try {
            Iterator<Hypothesis> itr = hypothesisSet.iterator();
            boolean flag = false;
            while (itr.hasNext()) {
                Hypothesis hypothesis = itr.next();
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
            }
            return flag;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Sets the entry.
     * 
     * @param studyBetweenParticipantList
     *            the study between participant list
     * @param studyRepeatedMeasuresTree
     *            the study repeated measures tree
     * @param hypothesisSet
     *            the hypothesis set
     * @return the sets the
     */
    private Set<Hypothesis> setEntry(
            List<BetweenParticipantFactor> studyBetweenParticipantList,
            List<RepeatedMeasuresNode> studyRepeatedMeasuresTree,
            Set<Hypothesis> hypothesisSet) {
        Set<Hypothesis> set = new HashSet<Hypothesis>();
        Iterator<Hypothesis> itr = hypothesisSet.iterator();

        while (itr.hasNext()) {
            Hypothesis hypothesis = itr.next();
            List<HypothesisBetweenParticipantMapping> newBList = null;
            List<HypothesisRepeatedMeasuresMapping> newRList = null;
            /*
             * Update Hypothesis-BetweenParticipantFactor Mapping
             */
            if (studyBetweenParticipantList != null
                    && !studyBetweenParticipantList.isEmpty()) {
                List<HypothesisBetweenParticipantMapping> bList = hypothesis
                        .getBetweenParticipantFactorMapList();
                newBList = new ArrayList<HypothesisBetweenParticipantMapping>();
                for (HypothesisBetweenParticipantMapping betweenParticipantFactorMap : bList) {
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
            /*
             * Update Hypothesis-RepeatedMeasures Mapping
             */
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
            set.add(new Hypothesis(hypothesis.getType(), newBList, newRList));
        }
        return set;
    }

    /**
     * Deletes RepeatedMeasuresNodeList.
     * 
     * @param uuid
     *            the uuid
     * @param repeatedMeasuresNodeList
     *            the repeated measures node list
     * @return the repeated measures node list
     */
    private RepeatedMeasuresNodeList delete(final byte[] uuid,
            final List<RepeatedMeasuresNode> repeatedMeasuresNodeList) {
        RepeatedMeasuresNodeList deletedList = null;
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        try {
            for (RepeatedMeasuresNode repeatedMeasuresNode : repeatedMeasuresNodeList) {
                session.delete(repeatedMeasuresNode);
            }
            deletedList = new RepeatedMeasuresNodeList(uuid,
                    repeatedMeasuresNodeList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete RepeatedMeasuresNode object for UUID '"
                            + uuid + "': " + e.getMessage());
        }
        return deletedList;
    }

    /**
     * Instantiates a new repeated measures manager.
     * 
     * @throws BaseManagerException
     *             the base manager exception
     */
    public RepeatedMeasuresManager() throws BaseManagerException {
        super();
    }

    /**
     * Retrieves RepeatedMeasuresNodeList.
     * 
     * @param uuid
     *            the uuid
     * @return the repeated measures node list
     */
    public final RepeatedMeasuresNodeList retrieve(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        RepeatedMeasuresNodeList repeatedMeasuresNodeList = null;
        try {
            /*
             * Retrieve Study Design for given uuid
             */
            StudyDesign studyDesign = get(uuid);
            /*
             * Retrieve Original RepeatedMeasuresNode Objects
             */
            if (studyDesign != null) {
                List<RepeatedMeasuresNode> originalRPList = studyDesign
                        .getRepeatedMeasuresTree();
                if (originalRPList != null && !originalRPList.isEmpty()) {
                    repeatedMeasuresNodeList = new RepeatedMeasuresNodeList(
                            uuid, originalRPList);
                } else {
                    /*
                     * uuid exists but no RepeatedMeasuresNodeList entry
                     * present. If uuid = null too; then it means no entry for
                     * this uuid.
                     */
                    repeatedMeasuresNodeList = new RepeatedMeasuresNodeList(
                            uuid, null);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete RepeatedMeasuresNode object for UUID '"
                            + uuid + "': " + e.getMessage());
        }
        return repeatedMeasuresNodeList;
    }

    /**
     * Deletes RepeatedMeasuresNodeList.
     * 
     * @param uuid
     *            the uuid
     * @return the repeated measures node list
     */
    public final RepeatedMeasuresNodeList delete(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        RepeatedMeasuresNodeList repeatedMeasuresNodeList = null;
        StudyDesign studyDesign = null;
        List<BetweenParticipantFactor> originalBPList = null;
        List<RepeatedMeasuresNode> originalRPList = null;
        try {
            /*
             * Retrieve Original RepeatedMeasuresNode List Object
             */
            studyDesign = get(uuid);
            if (studyDesign != null) {
                originalBPList = studyDesign.getBetweenParticipantFactorList();
                originalRPList = studyDesign.getRepeatedMeasuresTree();
                /*
                 * Delete Existing RepeatedMeasuresNode List Object
                 */
                if (originalRPList != null && !originalRPList.isEmpty()) {
                    repeatedMeasuresNodeList = delete(uuid, originalRPList);

                    Set<Hypothesis> hypothesisSet = studyDesign.getHypothesis();
                    if (hypothesisSet != null && !hypothesisSet.isEmpty()) {
                        boolean flagBetweenParticipant = checkBetweenParticipantFactorEntry(
                                originalBPList, hypothesisSet);
                        if (flagBetweenParticipant) {
                            hypothesisSet = setEntry(originalBPList, null,
                                    hypothesisSet);
                            session.update(hypothesisSet);
                        } else {
                            hypothesisSet = null;
                            session.delete(hypothesisSet);
                        }
                        /*
                         * Update Study Design Object
                         */
                        studyDesign.setHypothesis(hypothesisSet);
                    }
                }
                /*
                 * Update Study Design Object
                 */
                studyDesign.setRepeatedMeasuresTree(null);
                session.update(studyDesign);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete RepeatedMeasuresNode object for UUID '"
                            + uuid + "': " + e.getMessage());
        }
        /*
         * Return Persisted RepeatedMeasuresNodeList
         */
        return repeatedMeasuresNodeList;
    }

    /**
     * Saves or updates RepeatedMeasuresNodeList.
     * 
     * @param repeatedMeasuresNodeList
     *            the repeated measures node list
     * @param isCreation
     *            the is creation
     * @return the repeated measures node list
     */
    public final RepeatedMeasuresNodeList saveOrUpdate(
            final RepeatedMeasuresNodeList repeatedMeasuresNodeList,
            final boolean isCreation) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        StudyDesign studyDesign = null;
        List<BetweenParticipantFactor> originalBPList = null;
        List<RepeatedMeasuresNode> originalRPList = null;
        RepeatedMeasuresNodeList newRMTree = null;
        byte[] uuid = repeatedMeasuresNodeList.getUuid();
        List<RepeatedMeasuresNode> newList = repeatedMeasuresNodeList
                .getRepeatedMeasuresList();

        try {
            /*
             * Retrieve Study Design Object
             */
            studyDesign = get(uuid);
            if (studyDesign != null) {
                originalBPList = studyDesign.getBetweenParticipantFactorList();
                originalRPList = studyDesign.getRepeatedMeasuresTree();
                /*
                 * Delete Existing RepeatedMeasuresNode List Object
                 */
                if (originalRPList != null && !originalRPList.isEmpty()) {
                    /*
                     * Check for Hypothesis-BetweenParticipant Mapping. Delete
                     * if any such mapping exists. Keep
                     * Hypothesis-RepeatedMeasures Mapping.
                     */
                    Set<Hypothesis> hypothesisSet = studyDesign.getHypothesis();
                    if (hypothesisSet != null && !hypothesisSet.isEmpty()) {
                        boolean flagBetweenParticipant = checkBetweenParticipantFactorEntry(
                                originalBPList, hypothesisSet);
                        if (flagBetweenParticipant) {
                            hypothesisSet = setEntry(originalBPList, null,
                                    hypothesisSet);
                            session.update(hypothesisSet);
                        } else {
                            hypothesisSet = null;
                            session.delete(hypothesisSet);
                        }
                        /*
                         * Update Study Design Object
                         */
                        studyDesign.setHypothesis(hypothesisSet);
                    }
                    /*
                     * Delete RepeatedMeasuresNodeList Mapping
                     */
                    delete(uuid, originalRPList);
                }
                if (isCreation) {
                    for (RepeatedMeasuresNode repeatedMeasuresNode : newList) {
                        session.save(repeatedMeasuresNode);
                    }
                } else {
                    for (RepeatedMeasuresNode repeatedMeasuresNode : newList) {
                        session.update(repeatedMeasuresNode);
                    }
                }
                /*
                 * Update Study Design Object
                 */
                studyDesign.setRepeatedMeasuresTree(newList);
                session.update(studyDesign);
                /*
                 * Return Persisted RepeatedMeasuresNodeList
                 */
                newRMTree = new RepeatedMeasuresNodeList(uuid, newList);
            }
        } catch (Exception e) {
            newList = null;
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to save RepeatedMeasuresNode object : "
                            + e.getMessage());
        }
        return newRMTree;
    }

}
