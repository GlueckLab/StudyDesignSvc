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

import org.hibernate.Query;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import edu.ucdenver.bios.webservice.common.domain.Category;
import edu.ucdenver.bios.webservice.common.domain.Category;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManager;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

/**
 * The Class CategoryManager.
 */
public class CategoryManager extends StudyDesignParentManager {

    /**
     * Instantiates a new category manager.
     * 
     * @throws BaseManagerException
     *             the base manager exception
     */
    public CategoryManager() throws BaseManagerException {
        super();
    }

    /**
     * Retrieve a Category object by the specified UUID.
     * 
     * @param uuidBytes
     *            the uuid bytes
     * @param categoryList
     *            the category list
     * @return List<Category>
     */
    /*
     * public List<Category> get(byte[] uuidBytes) { if(!transactionStarted)
     * throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
     * "Transaction has not been started."); List<Category> categoryList = null;
     * try { //byte[] uuidBytes = UUIDUtils.asByteArray(studyUUID); Query query
     * = session.createQuery(
     * "from edu.ucdenver.bios.webservice.common.domain.Category where uuid = :uuid"
     * ); query.setBinary("uuid", uuidBytes); categoryList = query.list(); }
     * catch(Exception e) { throw new
     * ResourceException(Status.CONNECTOR_ERROR_CONNECTION
     * ,"Failed to retrieve Category object for UUID '" + uuidBytes + "': " +
     * e.getMessage()); } return categoryList; }
     */

    /**
     * Delete a Category object by the specified UUID.
     * 
     * @param studyUuid
     *            : byte[]
     * @return List<Category>
     */
    public List<Category> delete(byte[] uuidBytes, List<Category> categoryList) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        try {
            for (Category category : categoryList) {
                session.delete(category);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete Category object for UUID '" + uuidBytes
                            + "': " + e.getMessage());
        }
        return categoryList;
    }

    /**
     * Retrieve a Category object by the specified UUID.
     * 
     * @param categoryList
     *            : List<Category>
     * @param isCreation
     *            : boolean
     * @return categoryList : List<Category>
     */
    public List<Category> saveOrUpdate(List<Category> categoryList,
            boolean isCreation) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        try {
            if (isCreation) {
                for (Category category : categoryList) {
                    session.save(category);
                    System.out.println("in save id: " + category.getId());
                }
            } else {
                for (Category category : categoryList) {
                    session.update(category);
                }
            }
        } catch (Exception e) {
            categoryList = null;
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to save Category object : " + e.getMessage());
        }
        return categoryList;
    }
}
