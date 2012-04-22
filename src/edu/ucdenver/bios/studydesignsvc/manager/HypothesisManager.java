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

import edu.ucdenver.bios.webservice.common.domain.Hypothesis;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManager;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Manager class which provides CRUD functionality for MySQL table Hypothesis
 * object.
 * 
 * @author Uttara Sakhadeo
 */
public class HypothesisManager extends StudyDesignParentManager {

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
     * Delete a Hypothesis object by the specified UUID.
     * 
     * @param uuidBytes
     *            the uuid bytes
     * @param hypothesisSet
     *            the hypothesis set
     * @return Set<Hypothesis>
     */
    public Set<Hypothesis> delete(byte[] uuidBytes,
            Set<Hypothesis> hypothesisSet) {
        if (!transactionStarted)
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        try {
            // hypothesisSet = get(uuidBytes);
            for (Hypothesis hypothesis : hypothesisSet)
                session.delete(hypothesis);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete HypothesisSet object for UUID '"
                            + uuidBytes + "': " + e.getMessage());
        }
        return hypothesisSet;
    }

    /**
     * Delete Specified Hypothesis object.
     * 
     * @param uuidBytes
     *            the uuid bytes
     * @param hypothesis
     *            the hypothesis
     * @return the hypothesis
     */
    public Hypothesis delete(byte[] uuidBytes, Hypothesis hypothesis) {
        if (!transactionStarted)
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        try {
            session.delete(hypothesis);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete Hypothesis object for UUID '" + uuidBytes
                            + "': " + e.getMessage());
        }
        return hypothesis;
    }

    /**
     * Save or Upadte Set<Hypothesis> object by the specified UUID.
     * 
     * @param hypothesisSet
     *            : Set<Hypothesis>
     * @param isCreation
     *            : boolean
     * @return hypothesisSet : Set<Hypothesis>
     */
    public Set<Hypothesis> saveOrUpdate(Set<Hypothesis> hypothesisSet,
            boolean isCreation) {
        if (!transactionStarted)
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        try {
            if (isCreation == true) {
                for (Hypothesis hypothesis : hypothesisSet) {
                    session.save(hypothesis);
                    // System.out.println("in save id: "+hypothesis.getId());
                }
            } else {
                for (Hypothesis hypothesis : hypothesisSet)
                    session.update(hypothesis);
            }
        } catch (Exception e) {
            hypothesisSet = null;
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to save HypothesisSet object : " + e.getMessage());
        }
        return hypothesisSet;
    }

    /**
     * Save or update Specified Hypothesis object.
     * 
     * @param hypothesis
     *            the hypothesis
     * @param isCreation
     *            the is creation
     * @return the hypothesis
     */
    public Hypothesis saveOrUpdate(Hypothesis hypothesis, boolean isCreation) {
        if (!transactionStarted)
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        try {
            if (isCreation == true) {
                session.save(hypothesis);
            } else {
                session.update(hypothesis);
            }
        } catch (Exception e) {
            hypothesis = null;
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to save Hypothesis object : " + e.getMessage());
        }
        return hypothesis;
    }
}
