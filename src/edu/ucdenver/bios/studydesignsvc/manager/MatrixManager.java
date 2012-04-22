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
import edu.ucdenver.bios.webservice.common.hibernate.BaseManager;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Manager class which provides CRUD functionality for MySQL table NamedMatrix
 * object.
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

    /**
     * Delete a Set<NamedMatrix> object by the specified UUID.
     * 
     * @param uuidBytes
     *            the uuid bytes
     * @param matrixSet
     *            the matrix set
     * @return Set<NamedMatrix>
     */
    public Set<NamedMatrix> delete(byte[] uuidBytes, Set<NamedMatrix> matrixSet) {
        if (!transactionStarted)
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        try {
            for (NamedMatrix matrix : matrixSet)
                session.delete(matrix);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete Set<NamedMatrix> object for UUID '"
                            + uuidBytes + "': " + e.getMessage());
        }
        return matrixSet;
    }

    /**
     * Delete a NamedMatrix object by the specified UUID.
     * 
     * @param uuidBytes
     *            the uuid bytes
     * @param matrix
     *            the matrix
     * @return NamedMatrix
     */
    public NamedMatrix delete(byte[] uuidBytes, NamedMatrix matrix) {
        if (!transactionStarted)
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        try {
            session.delete(matrix);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete NamedMatrix object for UUID '"
                            + uuidBytes + "': " + e.getMessage());
        }
        return matrix;
    }

    /**
     * Save/Update a Set<NamedMatrix> object by the specified UUID.
     * 
     * @param matrixSet
     *            : Set<NamedMatrix>
     * @param isCreation
     *            : boolean
     * @return matrixSet : Set<NamedMatrix>
     */
    public Set<NamedMatrix> saveOrUpdate(Set<NamedMatrix> matrixSet,
            boolean isCreation) {
        if (!transactionStarted)
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        try {
            if (isCreation == true) {
                for (NamedMatrix matrix : matrixSet) {
                    session.save(matrix);
                }
            } else {
                for (NamedMatrix matrix : matrixSet)
                    session.update(matrix);
            }
        } catch (Exception e) {
            matrixSet = null;
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to save NamedMatrix object : " + e.getMessage());
        }
        return matrixSet;
    }

    /**
     * Save/Update a NamedMatrix object by the specified UUID.
     * 
     * @param matrix
     *            : NamedMatrix
     * @param isCreation
     *            : boolean
     * @return matrix : NamedMatrix
     */
    public NamedMatrix saveOrUpdate(NamedMatrix matrix, boolean isCreation) {
        if (!transactionStarted)
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        try {
            if (isCreation == true) {
                session.save(matrix);
            } else {
                session.update(matrix);
            }
        } catch (Exception e) {
            matrix = null;
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to save NamedMatrix object : " + e.getMessage());
        }
        return matrix;
    }
}
