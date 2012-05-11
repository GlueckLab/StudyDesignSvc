package edu.ucdenver.bios.studydesignsvc.manager;

import java.util.Set;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrixSet;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Manager class which provides CRUD functionality for NamedMatrixSet object.
 * 
 * @author Uttara Sakhadeo
 */
public class MatrixSetManager extends StudyDesignParentManager {

    /**
     * Instantiates a new matrix set manager.
     * 
     * @throws BaseManagerException
     *             the base manager exception
     */
    public MatrixSetManager() throws BaseManagerException {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Retrieves the NamedMatrixSet.
     * 
     * @param uuid
     *            the uuid
     * @return the named matrix set
     */
    public final NamedMatrixSet retrieve(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        NamedMatrixSet namedMatrixSet = null;
        try {
            /*
             * Retrieve Study Design for given uuid
             */
            StudyDesign studyDesign = get(uuid);
            /*
             * Retrieve Original NamedMatrixSet Object
             */
            if (studyDesign != null) {
                Set<NamedMatrix> originalSet = studyDesign.getMatrixSet();
                if (originalSet != null && !originalSet.isEmpty()) {
                    namedMatrixSet = new NamedMatrixSet(uuid, originalSet);
                } else {
                    /*
                     * uuid exists but no NamedMatrixSet entry present. If uuid
                     * = null too; then it means no entry for this uuid.
                     */
                    namedMatrixSet = new NamedMatrixSet(uuid, null);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete NamedMatrix object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return namedMatrixSet;
    }

    /**
     * Deletes the NamedMatrixSet.
     * 
     * @param uuid
     *            the uuid
     * @return the named matrix set
     */
    public final NamedMatrixSet delete(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        NamedMatrixSet namedMatrixSet = null;
        StudyDesign studyDesign = null;
        try {
            /*
             * Retrieve Original NamedMatrix Object
             */
            studyDesign = get(uuid);
            if (studyDesign != null) {
                Set<NamedMatrix> originalSet = studyDesign.getMatrixSet();
                /*
                 * Delete Existing NamedMatrix Set Object
                 */
                if (originalSet != null && !originalSet.isEmpty()) {
                    namedMatrixSet = delete(uuid, originalSet);
                }
                /*
                 * Update Study Design Object
                 */
                studyDesign.setMatrixSet(null);
                session.update(studyDesign);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete NamedMatrix object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        /*
         * Return NamedMatrixSet
         */
        return namedMatrixSet;
    }

    /**
     * Deletes the NamedMatrixSet.
     * 
     * @param uuid
     *            the uuid
     * @param namedMatrixSet
     *            the named matrix set
     * @return the named matrix set
     */
    private NamedMatrixSet delete(final byte[] uuid,
            final Set<NamedMatrix> namedMatrixSet) {
        NamedMatrixSet deletedSet = null;
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        try {
            if (namedMatrixSet != null && !namedMatrixSet.isEmpty()) {
                for (NamedMatrix matrix : namedMatrixSet) {
                    session.delete(matrix);
                }
            }
            deletedSet = new NamedMatrixSet(uuid, namedMatrixSet);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete NamedMatrix object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return deletedSet;
    }

    /**
     * Save or update the NamedMatrixSet.
     * 
     * @param namedMatrixSet
     *            the named matrix set
     * @param isCreation
     *            the is creation
     * @return the named matrix set
     */
    public final NamedMatrixSet saveOrUpdate(
            final NamedMatrixSet namedMatrixSet, final boolean isCreation) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        StudyDesign studyDesign = null;
        Set<NamedMatrix> originalSet = null;
        NamedMatrixSet newMatrixSet = null;
        byte[] uuid = namedMatrixSet.getUuid();
        Set<NamedMatrix> newSet = namedMatrixSet.getMatrixSet();

        try {
            /*
             * Retrieve Study Design Object
             */
            studyDesign = get(uuid);
            if (studyDesign != null) {
                originalSet = studyDesign.getMatrixSet();
                /*
                 * Delete Existing NamedMatrix Set Object
                 */
                if (originalSet != null && !originalSet.isEmpty()) {
                    delete(uuid, originalSet);
                }
                if (isCreation) {
                    for (NamedMatrix matrix : newSet) {
                        session.save(matrix);
                    }
                } else {
                    for (NamedMatrix matrix : newSet) {
                        session.update(matrix);
                    }
                }
                /*
                 * Update Study Design Object
                 */
                studyDesign.setMatrixSet(newSet);
                session.update(studyDesign);
                /*
                 * Return Persisted NamedMatrixSet
                 */
                newMatrixSet = new NamedMatrixSet(uuid, newSet);
            }
        } catch (Exception e) {
            newMatrixSet.setMatrixSet(null);
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to save NamedMatrix object : " + e.getMessage());
        }
        return newMatrixSet;
    }
}
