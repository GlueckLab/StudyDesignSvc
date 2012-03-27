package edu.ucdenver.bios.studydesignsvc.resource;

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
import edu.ucdenver.bios.studydesignsvc.manager.CategoryManager;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.domain.BetweenParticipantFactor;
import edu.ucdenver.bios.webservice.common.domain.Category;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * The Class CategoryServerResource.
 */
public class CategoryServerResource extends ServerResource implements
        CategoryResource {

    /** The category manager. */
    private CategoryManager categoryManager = null;

    /** The study design manager. */
    private StudyDesignManager studyDesignManager = null;

    /** The uuid flag. */
    private boolean uuidFlag;

    
    @Get
    public List<Category> retrieve(final byte[] uuid) {
        List<Category> categoryList = null;
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
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
                List<BetweenParticipantFactor> betweenParticipantFactorList = studyDesign
                        .getBetweenParticipantFactorList();
            }
            studyDesignManager.commit();
        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (categoryManager != null) {
                try {
                    categoryManager.rollback();
                } catch (BaseManagerException re) {
                    categoryList = null;
                }
            }
            categoryList = null;
        } catch (StudyDesignException sde) {
            System.out.println(sde.getMessage());
            StudyDesignLogger.getInstance().error(sde.getMessage());
            if (studyDesignManager != null) {
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException re) {
                    categoryList = null;
                }
            }
            categoryList = null;
        }
        return categoryList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.ucdenver.bios.studydesignsvc.resource.CategoryResource#create(byte[],
     * java.util.List)
     */
    @Post("json")
    public List<Category> create(byte[] uuid, List<Category> categoryList) {
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
            } else {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                        "no study design UUID specified");
            }
            studyDesignManager.commit();
            /*
             * ---------------------------------------------------- Remove
             * existing Beta Scale for this object
             * ----------------------------------------------------
             */
            if (uuidFlag
                    && studyDesign.getBetweenParticipantFactorList() != null)
                remove(studyDesign);
            /*
             * ---------------------------------------------------- Set
             * reference of Study Design Object to each Beta Scale element
             * ----------------------------------------------------
             */
            /*
             * for(Category Category : categoryList) {
             * //Category.setStudyDesign(studyDesign); Category.setUuid(uuid); }
             */
            if (uuidFlag) {
                categoryManager = new CategoryManager();
                categoryManager.beginTransaction();
                categoryList = categoryManager.saveOrUpdate(categoryList, true);
                categoryManager.commit();
                /*
                 * ---------------------------------------------------- Set
                 * reference of Confidence Interval Object to Study Design
                 * object ----------------------------------------------------
                 */
                for (Category Category : categoryList) {
                    System.out.println("in ServerResource id: "
                            + Category.getId());
                }
                // studyDesign.setBetaScaleList(categoryList);
                studyDesignManager = new StudyDesignManager();
                studyDesignManager.beginTransaction();
                studyDesign = studyDesignManager.saveOrUpdate(studyDesign,
                        false);
                studyDesignManager.commit();
            }
        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (categoryManager != null) {
                try {
                    categoryManager.rollback();
                } catch (BaseManagerException re) {
                    categoryList = null;
                }
            }
            categoryList = null;
        } catch (StudyDesignException sde) {
            System.out.println(sde.getMessage());
            StudyDesignLogger.getInstance().error(sde.getMessage());
            if (studyDesignManager != null) {
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException re) {
                    categoryList = null;
                }
            }
            categoryList = null;
        }
        return categoryList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.ucdenver.bios.studydesignsvc.resource.CategoryResource#update(byte[],
     * java.util.List)
     */
    @Put
    public List<Category> update(byte[] uuid, List<Category> categoryList) {
        return create(uuid, categoryList);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.ucdenver.bios.studydesignsvc.resource.CategoryResource#remove(byte[])
     */
    @Delete
    public List<Category> remove(final byte[] uuid) {
        List<Category> categoryList = null;
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
                // categoryList = studyDesign.getBetweenParticipantFactorList();
                /*
                 * if(categoryList.isEmpty()) throw new
                 * ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                 * "no TypeIError is specified");
                 */
            }
            studyDesignManager.commit();
            /*
             * ---------------------------------------------------- Remove
             * existing Beta Scale objects for this object
             * ----------------------------------------------------
             */
            if (categoryList != null) {
                categoryManager = new CategoryManager();
                categoryManager.beginTransaction();
                categoryList = categoryManager.delete(uuid, categoryList);
                categoryManager.commit();
                /*
                 * ---------------------------------------------------- Set
                 * reference of Confidence Interval Object to Study Design
                 * object ----------------------------------------------------
                 */
                studyDesign.setBetaScaleList(null);
                studyDesignManager = new StudyDesignManager();
                studyDesignManager.beginTransaction();
                studyDesign = studyDesignManager.saveOrUpdate(studyDesign,
                        false);
                studyDesignManager.commit();
            }
        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (categoryManager != null) {
                try {
                    categoryManager.rollback();
                } catch (BaseManagerException re) {
                    categoryList = null;
                }
            }
            categoryList = null;
        } catch (StudyDesignException sde) {
            System.out.println(sde.getMessage());
            StudyDesignLogger.getInstance().error(sde.getMessage());
            if (studyDesignManager != null) {
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException re) {
                    categoryList = null;
                }
            }
            categoryList = null;
        }
        return categoryList;
    }

    /**
     * Delete a Confidence Interval object for specified Study Design.
     * 
     * @param studyDesign
     *            the study design
     * @return ConfidenceIntervalDescription
     */
    @Delete("json")
    public final List<Category> remove(StudyDesign studyDesign) {
        boolean flag;
        List<Category> categoryList = null;
        try {
            categoryManager = new CategoryManager();
            categoryManager.beginTransaction();
            // categoryList=categoryManager.delete(studyDesign.getUuid(),studyDesign.getBetaScaleList());
            categoryManager.commit();
            /*
             * ---------------------------------------------------- Set
             * reference of Confidence Interval Object to Study Design object
             * ----------------------------------------------------
             */
            studyDesign.setConfidenceIntervalDescriptions(null);
            studyDesignManager = new StudyDesignManager();
            studyDesignManager.beginTransaction();
                studyDesign = studyDesignManager.saveOrUpdate(studyDesign, false);
                
            studyDesignManager.commit();
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
            if (categoryManager != null)
                try {
                    categoryManager.rollback();
                } catch (BaseManagerException e) {
                }
            categoryList = null;
        } catch (StudyDesignException sde) {
            StudyDesignLogger.getInstance().error(
                    "Failed to load Study Design information: "
                            + sde.getMessage());
            if (studyDesignManager != null)
                try {
                    studyDesignManager.rollback();
                } catch (BaseManagerException e) {
                }
            if (categoryManager != null)
                try {
                    categoryManager.rollback();
                } catch (BaseManagerException e) {
                }
            categoryList = null;
        }
        return categoryList;
    }
}
