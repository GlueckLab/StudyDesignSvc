package edu.ucdenver.bios.studydesignsvc.resource;

import java.util.ArrayList;
import java.util.Iterator;
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
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * The Class HypothesisSetServerResource.
 */
public class HypothesisSetServerResource extends ServerResource implements
        HypothesisSetResource {

    /**
     * Retrieve a Hypothesis object for specified UUID.
     * 
     * @param uuid
     *            the uuid
     * @return HypothesisSet
     */
    @Get("application/json")
    public HypothesisSet retrieve(byte[] uuid) {
        StudyDesignManager studyDesignManager = null;
        boolean uuidFlag;

        HypothesisSet hypothesisSet = null;
        if (uuid == null)
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
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
                    hypothesisSet = new HypothesisSet(uuid,
                            studyDesign.getHypothesis());
            }
            studyDesignManager.commit();
        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (studyDesignManager != null) {
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException re) {
                    hypothesisSet = null;
                }
            }
            hypothesisSet = null;
        } catch (StudyDesignException sde) {
            System.out.println(sde.getMessage());
            StudyDesignLogger.getInstance().error(sde.getMessage());
            if (studyDesignManager != null) {
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException re) {
                    hypothesisSet = null;
                }
            }
            hypothesisSet = null;
        }
        return hypothesisSet;
    }

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
    public BetweenParticipantFactor getBetweenParticipantFactor(
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
    public RepeatedMeasuresNode getRepeatedMeasuresNode(
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
    public boolean checkBetweenParticipantFactorId(
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
    public boolean checkRepeatedMeasuresNodeId(
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
    public boolean checkBetweenParticipantFactorEntry(
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
    public boolean checkRepeatedMeasuresNodeEntry(
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
    public HypothesisSet setEntry(
            List<BetweenParticipantFactor> studyBetweenParticipantList,
            List<RepeatedMeasuresNode> studyRepeatedMeasuresTree,
            HypothesisSet hypothesisSet) {
        HypothesisSet set = new HypothesisSet(hypothesisSet.getUuid());
        Iterator<Hypothesis> itr = hypothesisSet.getHypothesisSet().iterator();

        while (itr.hasNext()) {
            Hypothesis hypothesis = itr.next();
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
            set.getHypothesisSet().add(
                    new Hypothesis(hypothesis.getType(), newBList, newRList));
        }
        return set;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.ucdenver.bios.studydesignsvc.resource.HypothesisSetResource#create
     * (edu.ucdenver.bios.webservice.common.domain.HypothesisSet)
     */
    @Post("application/json")
    public HypothesisSet create(HypothesisSet hypothesisSet) {
        HypothesisManager hypothesisManager = null;
        StudyDesignManager studyDesignManager = null;
        boolean uuidFlag;

        StudyDesign studyDesign = null;
        byte[] uuid = hypothesisSet.getUuid();
        if (uuid == null)
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
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
            } else {
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
            if (uuidFlag && studyDesign != null) {
                boolean flagBetweenParticipant = false;
                boolean flagRepeatedMeasures = false;
                if (studyBetweenParticipantList != null
                        && !studyBetweenParticipantList.isEmpty()) {
                    flagBetweenParticipant = checkBetweenParticipantFactorEntry(
                            studyBetweenParticipantList, hypothesisSet);
                }
                if (studyRepeatedMeasuresTree != null
                        && !studyRepeatedMeasuresTree.isEmpty()) {
                    flagRepeatedMeasures = checkRepeatedMeasuresNodeEntry(
                            studyRepeatedMeasuresTree, hypothesisSet);
                }
                if (flagBetweenParticipant && flagRepeatedMeasures) {
                    // hypothesisSet =
                    // setBetweenParticipantFactorEntry(studyBetweenParticipantList,
                    // hypothesisSet);
                    hypothesisSet = setEntry(studyBetweenParticipantList,
                            studyRepeatedMeasuresTree, hypothesisSet);
                } else if (flagBetweenParticipant) {
                    // hypothesisSet =
                    // setBetweenParticipantFactorEntry(studyBetweenParticipantList,
                    // hypothesisSet);
                    hypothesisSet = setEntry(studyBetweenParticipantList, null,
                            hypothesisSet);
                } else if (flagRepeatedMeasures) {
                    // hypothesisSet =
                    // setBetweenParticipantFactorEntry(studyBetweenParticipantList,
                    // hypothesisSet);
                    hypothesisSet = setEntry(null, studyRepeatedMeasuresTree,
                            hypothesisSet);
                }
            }
            /*
             * ---------------------------------------------------- Remove
             * existing Hypothesis for this object
             * ----------------------------------------------------
             */
            if (uuidFlag && !studyDesign.getHypothesis().isEmpty())
                removeFrom(studyDesign);
            if (uuidFlag) {
                hypothesisManager = new HypothesisManager();
                hypothesisManager.beginTransaction();
                hypothesisManager.saveOrUpdate(
                        hypothesisSet.getHypothesisSet(), true);
                hypothesisManager.commit();
                /*
                 * ---------------------------------------------------- Set
                 * reference of HypothesisSet Object to Study Design object
                 * ----------------------------------------------------
                 */
                studyDesign.setHypothesis(hypothesisSet.getHypothesisSet());
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
                    hypothesisSet = null;
                }
            }
            hypothesisSet = null;
        } catch (StudyDesignException sde) {
            System.out.println(sde.getMessage());
            StudyDesignLogger.getInstance().error(sde.getMessage());
            if (studyDesignManager != null) {
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException re) {
                    hypothesisSet = null;
                }
            }
            hypothesisSet = null;
        }
        return hypothesisSet;
    }

    /**
     * Update a Hypothesis object for specified UUID.
     * 
     * @param hypothesisSet
     *            the hypothesis set
     * @return HypothesisSet
     */
    @Put("application/json")
    public HypothesisSet update(HypothesisSet hypothesisSet) {
        return create(hypothesisSet);
    }

    /**
     * Delete a Hypothesis object for specified UUID.
     * 
     * @param uuid
     *            the uuid
     * @return HypothesisSet
     */
    @Delete("application/json")
    public HypothesisSet remove(byte[] uuid) {
        HypothesisManager hypothesisManager = null;
        StudyDesignManager studyDesignManager = null;
        boolean uuidFlag;

        HypothesisSet hypothesisSet = null;
        StudyDesign studyDesign = null;
        if (uuid == null)
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
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
            if (!hypothesisSet.getHypothesisSet().isEmpty()) {
                hypothesisManager = new HypothesisManager();
                hypothesisManager.beginTransaction();
                hypothesisSet = new HypothesisSet(uuid,
                        hypothesisManager.delete(uuid,
                                hypothesisSet.getHypothesisSet()));
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
                    hypothesisSet = null;
                }
            }
            hypothesisSet = null;
        } catch (StudyDesignException sde) {
            System.out.println(sde.getMessage());
            StudyDesignLogger.getInstance().error(sde.getMessage());
            if (studyDesignManager != null) {
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException re) {
                    hypothesisSet = null;
                }
            }
            hypothesisSet = null;
        }
        return hypothesisSet;
    }

    /**
     * Delete a Hypothesis object for specified Study Design.
     * 
     * @param studyDesign
     *            the study design
     * @return HypothesisSet
     */
    public HypothesisSet removeFrom(StudyDesign studyDesign) {
        HypothesisManager hypothesisManager = null;
        StudyDesignManager studyDesignManager = null;
        HypothesisSet hypothesisSet = null;
        try {
            hypothesisManager = new HypothesisManager();
            hypothesisManager.beginTransaction();
            hypothesisSet = new HypothesisSet(studyDesign.getUuid(),
                    hypothesisManager.delete(studyDesign.getUuid(),
                            studyDesign.getHypothesis()));
            hypothesisManager.commit();
            /*
             * ---------------------------------------------------- Set
             * reference of Hypothesis Object to Study Design object
             * ----------------------------------------------------
             */
            /*
             * studyDesign.setConfidenceIntervalDescriptions(null);
             * studyDesignManager = new StudyDesignManager();
             * studyDesignManager.beginTransaction(); studyDesign =
             * studyDesignManager.saveOrUpdate(studyDesign, false);
             * studyDesignManager.commit();
             */
        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(
                    "Failed to load Study Design information: "
                            + bme.getMessage());
            if (studyDesignManager != null)
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException e) {
                }
            if (hypothesisManager != null)
                try {
                    hypothesisManager.rollback();
                } catch (BaseManagerException e) {
                }
            hypothesisSet = null;
        }
        /*
         * catch (StudyDesignException sde) {
         * StudyDesignLogger.getInstance().error
         * ("Failed to load Study Design information: " + sde.getMessage()); if
         * (studyDesignManager != null) try { studyDesignManager.rollback(); }
         * catch (BaseManagerException e) {} if (hypothesisManager != null) try
         * { hypothesisManager.rollback(); } catch (BaseManagerException e) {}
         * hypothesisSet = null; }
         */
        return hypothesisSet;
    }
}
