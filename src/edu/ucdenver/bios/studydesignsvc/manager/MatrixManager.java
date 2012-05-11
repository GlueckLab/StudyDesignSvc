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

import java.util.Set;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.domain.UuidMatrix;
import edu.ucdenver.bios.webservice.common.domain.UuidMatrixName;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManager;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Manager class which provides CRUD functionality for NamedMatrix object.
 * 
 * @author Uttara Sakhadeo
 */
public class MatrixManager extends StudyDesignParentManager {

    /**
     * Instantiates a new matrix manager.
     * 
     * @throws BaseManagerException
     *             the base manager exception
     */
    public MatrixManager() throws BaseManagerException {
        super();
    }

    /*
     * public NamedMatrix delete(byte[] uuidBytes, NamedMatrix matrix) { if
     * (!transactionStarted) throw new
     * ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
     * "Transaction has not been started."); try { session.delete(matrix); }
     * catch (Exception e) { System.out.println(e.getMessage()); throw new
     * ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
     * "Failed to delete NamedMatrix object for UUID '" + uuidBytes + "': " +
     * e.getMessage()); } return matrix; }
     * 
     * public NamedMatrix saveOrUpdate(NamedMatrix matrix, boolean isCreation) {
     * if (!transactionStarted) throw new
     * ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
     * "Transaction has not been started."); try { if (isCreation == true) {
     * session.save(matrix); } else { session.update(matrix); } } catch
     * (Exception e) { matrix = null; System.out.println(e.getMessage()); throw
     * new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
     * "Failed to save NamedMatrix object : " + e.getMessage()); } return
     * matrix; }
     */

    private NamedMatrix delete(final byte[] uuid, final NamedMatrix namedMatrix) {
        // NamedMatrix deletedSet = null;
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        try {
            if (namedMatrix != null) {
                session.delete(namedMatrix);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete NamedMatrix object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return namedMatrix;
    }

    public final NamedMatrix retrieve(final UuidMatrixName uuidMatrixName) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        NamedMatrix originalMatrix = null;
        String matrixName = uuidMatrixName.getMatrixName();
        byte[] uuid = uuidMatrixName.getUuid();
        try {
            /*
             * Retrieve Study Design for given uuid
             */
            StudyDesign studyDesign = get(uuid);
            /*
             * Retrieve Original NamedMatrix Object
             */
            if (studyDesign != null) {
                originalMatrix = studyDesign.getNamedMatrix(matrixName);
                if (originalMatrix == null) {
                    originalMatrix = null;
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete NamedMatrix object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        return originalMatrix;
    }

    public final NamedMatrix delete(final UuidMatrixName uuidMatrixName) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        NamedMatrix namedMatrix = null;
        StudyDesign studyDesign = null;
        byte[] uuid = uuidMatrixName.getUuid();
        String name = uuidMatrixName.getMatrixName();
        try {
            /*
             * Retrieve Original NamedMatrix Object
             */
            studyDesign = get(uuid);
            if (studyDesign != null) {
                namedMatrix = studyDesign.getNamedMatrix(name);
                /*
                 * Delete Existing NamedMatrix Set Object
                 */
                if (namedMatrix != null) {
                    namedMatrix = delete(uuid, namedMatrix);
                }
                /*
                 * Update Study Design Object
                 */
                /*
                 * studyDesign.setC(null); session.update(studyDesign);
                 */
            }
        } catch (Exception e) {
            namedMatrix = null;
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete NamedMatrix object for UUID '" + uuid
                            + "': " + e.getMessage());
        }
        /*
         * Return NamedMatrix
         */
        return namedMatrix;
    }

    public final NamedMatrix saveOrUpdate(final UuidMatrix uuidMatrix,
            final boolean isCreation) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        StudyDesign studyDesign = null;
        NamedMatrix originalMatrix = null;
        NamedMatrix newMatrix = uuidMatrix.getMatrix();
        byte[] uuid = uuidMatrix.getUuid();
        String matrixName = newMatrix.getName();

        try {
            /*
             * Retrieve Study Design Object
             */
            studyDesign = get(uuid);
            if (studyDesign != null) {
                originalMatrix = studyDesign.getNamedMatrix(matrixName);
                /*
                 * Delete Existing NamedMatrix Set Object
                 */
                if (originalMatrix != null) {
                    delete(uuid, originalMatrix);
                }
                if (isCreation) {
                    session.save(newMatrix);
                } else {
                    session.update(newMatrix);
                }
                /*
                 * Update Study Design Object
                 */
                studyDesign.setNamedMatrix(newMatrix);
                session.update(studyDesign);
                /*
                 * Return Persisted NamedMatrix
                 */
            }
        } catch (Exception e) {
            newMatrix = null;
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to save NamedMatrix object : " + e.getMessage());
        }
        return newMatrix;
    }
}
