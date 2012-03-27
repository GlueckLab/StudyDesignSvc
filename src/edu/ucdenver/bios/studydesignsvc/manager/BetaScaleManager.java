/*
 * Study Design Service for the GLIMMPSE Software System.
 * This service stores study design definitions for users
 * of the GLIMMSE interface. Service contain all information
 * related to a power or sample size calculation.
 * The Study Design Service simplifies communication between
 * different screens in the user interface.
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
 * Foundation, Inc. 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 */
package edu.ucdenver.bios.studydesignsvc.manager;

import java.util.List;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import edu.ucdenver.bios.webservice.common.domain.BetaScale;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManager;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

/**
 * Manager class which provides CRUD functionality for MySQL table Beta Scale
 * object.
 * 
 * @author Uttara Sakhadeo
 */
public class BetaScaleManager extends BaseManager {
    /**
     * Instantiates a new beta scale manager.
     * 
     * @throws BaseManagerException
     *             the base manager exception
     */
    public BetaScaleManager() throws BaseManagerException {
        super();
    }

    /**
     * Check existence of a Beta Scale object by the specified UUID.
     * 
     * @param uuidBytes
     *            the uuid bytes
     * @param betaScaleList
     *            the beta scale list
     * @return boolean
     */
    /*
     * public boolean hasUUID(byte[] uuidBytes) throws StudyDesignException { if
     * (!transactionStarted) throw new
     * StudyDesignException("Transaction has not been started"); try { //byte[]
     * uuidBytes = UUIDUtils.asByteArray(uuid); Query query =
     * session.createQuery(
     * "from edu.ucdenver.bios.webservice.common.domain.BetaScale where studyDesign = :uuid"
     * ); query.setBinary("uuid", uuidBytes); List<BetaScale> betaScaleList=
     * query.list(); if (betaScaleList!=null) return true; else return false; }
     * catch (Exception e) { throw new
     * StudyDesignException("Failed to retrieve Beta Scale object for UUID '" +
     * uuidBytes.toString() + "': " + e.getMessage()); } }
     */

    /**
     * Retrieve a Beta Scale object by the specified UUID.
     * 
     * @param studyUuid
     *            : byte[]
     * @return List<BetaScale>
     */
    /*
     * public List<BetaScale> get(byte[] uuidBytes) { if (!transactionStarted)
     * throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
     * "Transaction has not been started."); List<BetaScale> betaScaleList =
     * null; try { //byte[] uuidBytes = UUIDUtils.asByteArray(studyUUID); Query
     * query = session.createQuery(
     * "from edu.ucdenver.bios.webservice.common.domain.BetaScale where uuid = :uuid"
     * ); query.setBinary("uuid", uuidBytes); betaScaleList = query.list(); }
     * catch (Exception e) { throw new
     * ResourceException(Status.CONNECTOR_ERROR_CONNECTION
     * ,"Failed to retrieve BetaScale object for UUID '" + uuidBytes + "': " +
     * e.getMessage()); } return betaScaleList; }
     */

    /**
     * Delete a Beta Scale object by the specified UUID.
     * 
     * @param studyUuid
     *            : byte[]
     * @return List<BetaScale>
     */
    public List<BetaScale> delete(byte[] uuidBytes,
            List<BetaScale> betaScaleList) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        try {
            // betaScaleList = get(uuidBytes);
            for (BetaScale betaScale : betaScaleList) {
                session.delete(betaScale);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete BetaScale object for UUID '" + uuidBytes
                            + "': " + e.getMessage());
        }
        return betaScaleList;
    }

    /**
     * Retrieve a BetaScale object by the specified UUID.
     * 
     * @param betaScaleList
     *            : List<BetaScale>
     * @param isCreation
     *            : boolean
     * @return betaScaleList : List<BetaScale>
     */
    public List<BetaScale> saveOrUpdate(List<BetaScale> betaScaleList,
            boolean isCreation) {
        if (!transactionStarted)
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        try {
            if (isCreation) {
                for (BetaScale betaScale : betaScaleList) {
                    session.save(betaScale);
                    System.out.println("in save id: " + betaScale.getId());
                }
            } else {
                for (BetaScale betaScale : betaScaleList)
                    session.update(betaScale);
            }
        } catch (Exception e) {
            betaScaleList = null;
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to save BetaScale object : " + e.getMessage());
        }
        return betaScaleList;
    }
}
