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

import java.util.ArrayList;
import java.util.List;

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import edu.ucdenver.bios.webservice.common.domain.TypeIError;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManager;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Manager class which provides CRUD functionality
 * for MySQL table TypeIError object.
 * 
 * @author Uttara Sakhadeo
 */
public class TypeIErrorManager extends BaseManager {

	/**
	 * Instantiates a new type i error manager.
	 *
	 * @throws BaseManagerException the base manager exception
	 */
	public TypeIErrorManager() throws BaseManagerException
	{
		super();
	}

	/**
	 * Delete a TypeIError object by the specified UUID.
	 *
	 * @param uuidBytes the uuid bytes
	 * @param typeIErrorList the type i error list
	 * @return ArrayList<TypeIError>
	 */
    public List<TypeIError> delete(byte[] uuidBytes,
            List<TypeIError> typeIErrorList) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        try {
            for (TypeIError nominalPower : typeIErrorList) {
            session.delete(nominalPower); }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete TypeIError object for UUID '" + uuidBytes
                    + "': " + e.getMessage());
        }
        return typeIErrorList;
    }

	/**
     * Retrieve a TypeIError object by the specified UUID.
     *
     * @param typeIErrorList : ArrayList<TypeIError>
     * @param isCreation : boolean
     * @return typeIErrorList : ArrayList<TypeIError>
     */
    public ArrayList<TypeIError> saveOrUpdate(
            ArrayList<TypeIError> typeIErrorList, boolean isCreation) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        try {
            if (isCreation) {
                for (TypeIError nominalPower : typeIErrorList) {
                   session.save(nominalPower);
                }
            } else {
                for (TypeIError nominalPower : typeIErrorList) {
                    session.update(nominalPower);
                }
            }
        } catch (Exception e) {
            typeIErrorList = null;
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to save TypeIError object : " + e.getMessage());
        }
        return typeIErrorList;
    }
}
