package edu.ucdenver.bios.studydesignsvc.resource;

import java.util.Set;

import org.restlet.data.Status;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.manager.HypothesisSetManager;
import edu.ucdenver.bios.webservice.common.domain.Hypothesis;
import edu.ucdenver.bios.webservice.common.domain.HypothesisSet;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * The Class HypothesisSetServerResource.
 */
public class HypothesisSetServerResource extends ServerResource implements
        HypothesisSetResource {

    @Get("application/json")
    public final HypothesisSet retrieve(final byte[] uuid) {
        HypothesisSetManager hypothesisSetManager = null;
        HypothesisSet hypothesisSet = null;
        /*
         * Check : empty uuid.
         */
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        /*
         * Check : length of uuid.
         */

        try {
            /*
             * Retrieve Hypothesis Set.
             */
            hypothesisSetManager = new HypothesisSetManager();
            hypothesisSetManager.beginTransaction();
            hypothesisSet = hypothesisSetManager.retrieve(uuid);
            hypothesisSetManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (hypothesisSetManager != null) {
                try {
                    hypothesisSetManager.rollback();
                } catch (BaseManagerException re) {
                    hypothesisSet = null;
                }
            }
            hypothesisSet = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (hypothesisSetManager != null) {
                try {
                    hypothesisSetManager.rollback();
                } catch (BaseManagerException re) {
                    hypothesisSet = null;
                }
            }
            hypothesisSet = null;
        }
        return hypothesisSet;
    }
    
    @Post("application/json")
    public final HypothesisSet create(HypothesisSet hypothesisSet) {
        HypothesisSetManager hypothesisSetManager = null;
        byte[] uuid = hypothesisSet.getUuid();
        /*
         * Check : empty uuid.
         */
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        /*
         * Check : empty Hypothesis Set.
         */
        Set<Hypothesis> set = hypothesisSet.getHypothesisSet();
        if (set == null || set.isEmpty()) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no Beta Scale specified");
        }
        try {
            /*
             * Save Hypothesis Set .
             */
            hypothesisSetManager = new HypothesisSetManager();
            hypothesisSetManager.beginTransaction();
            hypothesisSet = hypothesisSetManager.saveOrUpdate(hypothesisSet,
                    true);
            hypothesisSetManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (hypothesisSetManager != null) {
                try {
                    hypothesisSetManager.rollback();
                } catch (BaseManagerException re) {
                    hypothesisSet = null;
                }
            }
            hypothesisSet = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (hypothesisSetManager != null) {
                try {
                    hypothesisSetManager.rollback();
                } catch (BaseManagerException re) {
                    hypothesisSet = null;
                }
            }
            hypothesisSet = null;
        }
        return hypothesisSet;
    }
    
    @Put("application/json")
    public final HypothesisSet update(final HypothesisSet hypothesisSet) {
        return create(hypothesisSet);
    }
    
    @Delete("application/json")
    public final HypothesisSet remove(final byte[] uuid) {
        HypothesisSetManager hypothesisSetManager = null;
        HypothesisSet hypothesisSet = null;
        /*
         * Check : empty uuid.
         */
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        try {
            /*
             * Delete Hypothesis Set.
             */
            hypothesisSetManager = new HypothesisSetManager();
            hypothesisSetManager.beginTransaction();
            hypothesisSet = hypothesisSetManager.delete(uuid);
            hypothesisSetManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (hypothesisSetManager != null) {
                try {
                    hypothesisSetManager.rollback();
                } catch (BaseManagerException re) {
                    hypothesisSet = null;
                }
            }
            hypothesisSet = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (hypothesisSetManager != null) {
                try {
                    hypothesisSetManager.rollback();
                } catch (BaseManagerException re) {
                    hypothesisSet = null;
                }
            }
            hypothesisSet = null;
        }
        return hypothesisSet;
    }
    
    /*@Get("application/json")
    public HypothesisSet retrieve(byte[] uuid) {
        StudyDesignManager studyDesignManager = null;
        boolean uuidFlag;

        HypothesisSet hypothesisSet = null;
        if (uuid == null)
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        try {
            
             * ---------------------------------------------------- Check for
             * existence of a UUID in Study Design object
             * ----------------------------------------------------
             
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
    }*/

    

    
    /*@Post("application/json")
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
            
             * ---------------------------------------------------- Check for
             * existence of a UUID in Study Design object
             * ----------------------------------------------------
             
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
            
             * ---------------------------------------------------- Check
             * existance of BetweenParticipantEfect
             * ----------------------------------------------------
             
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
            
             * ---------------------------------------------------- Remove
             * existing Hypothesis for this object
             * ----------------------------------------------------
             
            if (uuidFlag && !studyDesign.getHypothesis().isEmpty())
                removeFrom(studyDesign);
            if (uuidFlag) {
                hypothesisManager = new HypothesisManager();
                hypothesisManager.beginTransaction();
                hypothesisManager.saveOrUpdate(
                        hypothesisSet.getHypothesisSet(), true);
                hypothesisManager.commit();
                
                 * ---------------------------------------------------- Set
                 * reference of HypothesisSet Object to Study Design object
                 * ----------------------------------------------------
                 
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
    } */   

    
    /*@Delete("application/json")
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
            
             * ---------------------------------------------------- Check for
             * existence of a UUID in Study Design object
             * ----------------------------------------------------
             
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
            
             * ---------------------------------------------------- Remove
             * existing Hypothesis objects for this object
             * ----------------------------------------------------
             
            if (!hypothesisSet.getHypothesisSet().isEmpty()) {
                hypothesisManager = new HypothesisManager();
                hypothesisManager.beginTransaction();
                hypothesisSet = new HypothesisSet(uuid,
                        hypothesisManager.delete(uuid,
                                hypothesisSet.getHypothesisSet()));
                hypothesisManager.commit();
                
                 * ---------------------------------------------------- Set
                 * reference of Hypothesis Object to Study Design object
                 * ----------------------------------------------------
                 
                
                 * studyDesign.setBetaScaleList(null); studyDesignManager = new
                 * StudyDesignManager(); studyDesignManager.beginTransaction();
                 * studyDesign = studyDesignManager.saveOrUpdate(studyDesign,
                 * false); studyDesignManager.commit();
                 * hypothesisSet=studyDesign.getHypothesis();
                 
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
    }*/
      
}
