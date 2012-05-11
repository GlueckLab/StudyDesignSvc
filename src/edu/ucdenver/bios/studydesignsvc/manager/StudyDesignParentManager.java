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

import edu.ucdenver.bios.studydesignsvc.exceptions.StudyDesignException;

import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManager;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Manager class which provides Retrieve functionality for StudyDesign object.
 * 
 * @author Uttara Sakhadeo
 */
public class StudyDesignParentManager extends BaseManager {

    /**
     * Instantiates a new study design parent manager.
     * 
     * @throws BaseManagerException
     *             the base manager exception
     */
    public StudyDesignParentManager() throws BaseManagerException {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Retrieve a study design object by the specified UUID.
     * 
     * @param uuidBytes
     *            the uuid bytes
     * @return data feed object
     * @throws StudyDesignException
     *             the study design exception
     */
    public StudyDesign get(byte[] uuidBytes) throws StudyDesignException {
        if (!transactionStarted)
            throw new StudyDesignException("Transaction has not been started");
        try {
            // byte[] uuidBytes = UUIDUtils.asByteArray(uuid);
            StudyDesign studyDesign = (StudyDesign) session.get(
                    StudyDesign.class, uuidBytes);
            return studyDesign;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new StudyDesignException(
                    "Failed to retrieve StudyDesign for UUID '"
                            + uuidBytes.toString() + "': " + e.getMessage());
        }
    }
}
