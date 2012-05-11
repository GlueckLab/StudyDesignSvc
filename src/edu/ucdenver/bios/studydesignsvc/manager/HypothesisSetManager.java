package edu.ucdenver.bios.studydesignsvc.manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import edu.ucdenver.bios.webservice.common.domain.BetweenParticipantFactor;
import edu.ucdenver.bios.webservice.common.domain.Hypothesis;
import edu.ucdenver.bios.webservice.common.domain.HypothesisBetweenParticipantMapping;
import edu.ucdenver.bios.webservice.common.domain.HypothesisRepeatedMeasuresMapping;
import edu.ucdenver.bios.webservice.common.domain.HypothesisSet;
import edu.ucdenver.bios.webservice.common.domain.RepeatedMeasuresNode;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Manager class which provides CRUD functionality for HypothesisSet object.
 * 
 * @author Uttara Sakhadeo
 */
public class HypothesisSetManager extends StudyDesignParentManager {

    /**
     * A method for Returning a BetweenParticipantFactor with specified id from
     * the list.
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
     * A method for Returning a RepeatedMeasuresNode with specified id from the
     * list.
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
     * A method for Checking existence of a BetweenParticipantFactor object in
     * given list.
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
     * A method for Checking existence of a RepeatedMeasuresNode object in given
     * list.
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
     * A method for Checking existence of RepeatedMeasuresNode objects in given
     * list.
     * 
     * @param studyBetweenParticipantList
     *            the study between participant list
     * @param hypothesisSet
     *            the hypothesis set
     * @return true, if successful
     */
    private boolean checkBetweenParticipantFactorEntry(
            List<BetweenParticipantFactor> studyBetweenParticipantList,
            HypothesisSet hypothesisSet) {
        try {
            Iterator<Hypothesis> itr = hypothesisSet.getHypothesisSet()
                    .iterator();
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
     * A method for Checking existence of RepeatedMeasuresNode objects in given
     * list.
     * 
     * @param studyRepeatedMeasuresTree
     *            the study repeated measures tree
     * @param hypothesisSet
     *            the hypothesis set
     * @return flag
     */
    private boolean checkRepeatedMeasuresNodeEntry(
            List<RepeatedMeasuresNode> studyRepeatedMeasuresTree,
            HypothesisSet hypothesisSet) {
        try {
            Iterator<Hypothesis> itr = hypothesisSet.getHypothesisSet()
                    .iterator();
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
     * A method returning set of Hypothesis objects containing passed
     * BetweenParticipantList and RepeatedMeasuresTree.
     * 
     * @param studyBetweenParticipantList
     *            the study between participant list
     * @param studyRepeatedMeasuresTree
     *            the study repeated measures tree
     * @param hypothesisSet
     *            the hypothesis set
     * @return the hypothesis set
     */
    private HypothesisSet setEntry(
            List<BetweenParticipantFactor> studyBetweenParticipantList,
            List<RepeatedMeasuresNode> studyRepeatedMeasuresTree,
            HypothesisSet hypothesisSet) {
        HypothesisSet hpSet = new HypothesisSet(hypothesisSet.getUuid());
        Iterator<Hypothesis> itr = hypothesisSet.getHypothesisSet().iterator();
        Set<Hypothesis> set = new HashSet<Hypothesis>();

        while (itr.hasNext()) {
            Hypothesis hypothesis = itr.next();
            List<HypothesisBetweenParticipantMapping> newBList = null;
            List<HypothesisRepeatedMeasuresMapping> newRList = null;
            if (studyBetweenParticipantList != null
                    && !studyBetweenParticipantList.isEmpty()
                    && hypothesis.getBetweenParticipantFactorMapList() != null
                    && !hypothesis.getBetweenParticipantFactorMapList()
                            .isEmpty()) {
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
                    && !studyRepeatedMeasuresTree.isEmpty()
                    && hypothesis.getRepeatedMeasuresMapTree() != null
                    && !hypothesis.getRepeatedMeasuresMapTree().isEmpty()) {
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
        hpSet.setHypothesisSet(set);
        return hpSet;
    }

    /**
     * Delete.
     * 
     * @param uuid
     *            the uuid
     * @param hypothesisSet
     *            the hypothesis set
     * @return the hypothesis set
     */
    private HypothesisSet delete(final byte[] uuid,
            final Set<Hypothesis> hypothesisSet) {
        HypothesisSet deletedSet = null;
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        try {
            if (hypothesisSet != null && !hypothesisSet.isEmpty()) {
                for (Hypothesis hypothesis : hypothesisSet) {
                    session.delete(hypothesis);
                }
            }
            deletedSet = new HypothesisSet(uuid, hypothesisSet);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete Hypothesis object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return deletedSet;
    }

    /**
     * Instantiates a new hypothesis set manager.
     * 
     * @throws BaseManagerException
     *             the base manager exception
     */
    public HypothesisSetManager() throws BaseManagerException {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Retrieves the HypothesisSet.
     * 
     * @param uuid
     *            the uuid
     * @return the hypothesis set
     */
    public final HypothesisSet retrieve(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        HypothesisSet hypothesisSet = null;
        try {
            /*
             * Retrieve Study Design for given uuid
             */
            StudyDesign studyDesign = get(uuid);
            /*
             * Retrieve Original HypothesisSet Object
             */
            if (studyDesign != null) {
                Set<Hypothesis> originalSet = studyDesign.getHypothesis();
                if (originalSet != null && !originalSet.isEmpty()) {
                    hypothesisSet = new HypothesisSet(uuid, originalSet);
                } else {
                    /*
                     * uuid exists but no HypothesisSet entry present. If uuid =
                     * null too; then it means no entry for this uuid.
                     */
                    hypothesisSet = new HypothesisSet(uuid, null);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete Hypothesis object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return hypothesisSet;
    }

    /**
     * Deletes the HypothesisSet.
     * 
     * @param uuid
     *            the uuid
     * @return the hypothesis set
     */
    public final HypothesisSet delete(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        HypothesisSet hypothesisSet = null;
        StudyDesign studyDesign = null;
        try {
            /*
             * Retrieve Original Hypothesis Object
             */
            studyDesign = get(uuid);
            if (studyDesign != null) {
                Set<Hypothesis> originalSet = studyDesign.getHypothesis();
                /*
                 * Delete Existing Hypothesis Set Object
                 */
                if (originalSet != null && !originalSet.isEmpty()) {
                    hypothesisSet = delete(uuid, originalSet);
                }
                /*
                 * Update Study Design Object
                 */
                studyDesign.setHypothesis(null);
                session.update(studyDesign);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete Hypothesis object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        /*
         * Return HypothesisSet
         */
        return hypothesisSet;
    }

    /**
     * Saves or updates the HypothesisSet.
     * 
     * @param hypothesisSet
     *            the hypothesis set
     * @param isCreation
     *            the is creation
     * @return the hypothesis set
     */
    public final HypothesisSet saveOrUpdate(final HypothesisSet hypothesisSet,
            final boolean isCreation) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        StudyDesign studyDesign = null;
        Set<Hypothesis> originalSet = null;
        Set<Hypothesis> newSet = null;
        HypothesisSet newHypothesisSet = null;
        boolean flagBetweenParticipant = false;
        boolean flagRepeatedMeasures = false;
        byte[] uuid = hypothesisSet.getUuid();
        // Set<Hypothesis> setFromUser = hypothesisSet.getHypothesisSet();
        List<BetweenParticipantFactor> studyBetweenParticipantList = null;
        List<RepeatedMeasuresNode> studyRepeatedMeasuresTree = null;

        try {
            /*
             * Retrieve Study Design Object
             */
            studyDesign = get(uuid);
            if (studyDesign != null) {
                /*
                 * Retrieve HypothesisSet object from StudyDesign object.
                 */
                originalSet = studyDesign.getHypothesis();

                /*
                 * Delete Existing Hypothesis Set Object
                 */
                if (originalSet != null && !originalSet.isEmpty()) {
                    delete(uuid, originalSet);
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
                 * new HypothesisSet object.
                 */
                if (studyBetweenParticipantList != null
                        && !studyBetweenParticipantList.isEmpty()) {
                    flagBetweenParticipant = checkBetweenParticipantFactorEntry(
                            studyBetweenParticipantList, hypothesisSet);
                }
                /*
                 * Check validity of each RepeatedMeasures sent through new
                 * HypothesisSet object.
                 */
                if (studyRepeatedMeasuresTree != null
                        && !studyRepeatedMeasuresTree.isEmpty()) {
                    flagRepeatedMeasures = checkRepeatedMeasuresNodeEntry(
                            studyRepeatedMeasuresTree, hypothesisSet);
                }
                /*
                 * Update HypothesisSet object for IDs. Case : Hypothesis
                 * involving both BetweenParticipantFactors and
                 * RepeatedMeasures.
                 */
                if (flagBetweenParticipant && flagRepeatedMeasures) {
                    // hypothesisSet =
                    // setBetweenParticipantFactorEntry(studyBetweenParticipantList,
                    // hypothesisSet);
                    newHypothesisSet = setEntry(studyBetweenParticipantList,
                            studyRepeatedMeasuresTree, hypothesisSet);
                }
                /*
                 * Update HypothesisSet object for IDs. Case : Hypothesis
                 * involving only BetweenParticipantFactors.
                 */
                else if (flagBetweenParticipant) {
                    // hypothesisSet =
                    // setBetweenParticipantFactorEntry(studyBetweenParticipantList,
                    // hypothesisSet);
                    newHypothesisSet = setEntry(studyBetweenParticipantList,
                            null, hypothesisSet);
                }
                /*
                 * Update HypothesisSet object for IDs. Case : Hypothesis
                 * involving only RepeatedMeasures.
                 */
                else if (flagRepeatedMeasures) {
                    // hypothesisSet =
                    // setBetweenParticipantFactorEntry(studyBetweenParticipantList,
                    // hypothesisSet);
                    newHypothesisSet = setEntry(null,
                            studyRepeatedMeasuresTree, hypothesisSet);
                }
                /*
                 * Update HypothesisSet object. Case : Hypothesis involving
                 * neither Between Participant Factors nor RepeatedMeasures.
                 */
                else {
                    /*
                     * Set basic properties.
                     */
                    newHypothesisSet = new HypothesisSet(
                            hypothesisSet.getUuid());
                    Iterator<Hypothesis> itr = hypothesisSet.getHypothesisSet()
                            .iterator();
                    Set<Hypothesis> set = new HashSet<Hypothesis>();
                    /*
                     * Iterate over set for dealing with set<Hypothesis> object
                     */
                    while (itr.hasNext()) {
                        Hypothesis hypothesis = itr.next();
                        set.add(hypothesis);
                    }
                    newHypothesisSet.setHypothesisSet(set);
                }
                if (newHypothesisSet != null)
                    newSet = newHypothesisSet.getHypothesisSet();
                if (isCreation) {
                    for (Hypothesis hypothesis : newSet) {
                        session.save(hypothesis);
                    }
                } else {
                    for (Hypothesis hypothesis : newSet) {
                        session.update(hypothesis);
                    }
                }
                /*
                 * Update Study Design Object.
                 */
                studyDesign.setHypothesis(newSet);
                session.update(studyDesign);
            }
        } catch (Exception e) {
            newHypothesisSet.setHypothesisSet(null);
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to save Hypothesis object : " + e.getMessage());
        }
        /*
         * Return Persisted HypothesisSet.
         */
        return newHypothesisSet;
    }
}
