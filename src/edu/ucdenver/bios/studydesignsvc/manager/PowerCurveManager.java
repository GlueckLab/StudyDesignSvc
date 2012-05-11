/*
 * Power Curve Description Service for the GLIMMPSE Software System.  
 * This service stores Power Curve Description definitions for users of the GLIMMSE interface.
 * Service contain all information related to a power or sample size calculation.  
 * The Power Curve Description Service simplifies communication between different screens in the user interface.
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

import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import edu.ucdenver.bios.webservice.common.domain.PowerCurveDescription;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.domain.UuidPowerCurveDescription;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Manager class which provides CRUD functionality for PowerCurveDescription
 * object.
 * 
 * @author Uttara Sakhadeo
 */
public class PowerCurveManager extends StudyDesignParentManager {
    /**
     * Instantiates a new power curve manager.
     * 
     * @throws BaseManagerException
     *             the base manager exception
     */
    public PowerCurveManager() throws BaseManagerException {
        super();
    }

    /**
     * Retrieve UuidPowerCurveDescription.
     * 
     * @param uuid
     *            the uuid
     * @return the uuid power curve description
     */
    public final UuidPowerCurveDescription retrieve(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        UuidPowerCurveDescription uuidPowerCurveDescription = null;
        try {
            /*
             * Retrieve Study Design for given uuid
             */
            StudyDesign studyDesign = get(uuid);
            /*
             * Retrieve Original PowerCurveDescription Object
             */
            if (studyDesign != null) {
                PowerCurveDescription original = studyDesign
                        .getPowerCurveDescriptions();
                if (original != null) {
                    uuidPowerCurveDescription = new UuidPowerCurveDescription(
                            uuid, original);
                } else {
                    /*
                     * uuid exists but no PowerCurveDescription entry present.
                     * If uuid = null too; then it means no entry for this uuid.
                     */
                    uuidPowerCurveDescription = new UuidPowerCurveDescription(
                            uuid, null);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete PowerCurveDescription object for UUID '"
                            + uuid + "': " + e.getMessage());
        }
        return uuidPowerCurveDescription;
    }

    /**
     * Delete UuidPowerCurveDescription.
     * 
     * @param uuid
     *            the uuid
     * @return the uuid power curve description
     */
    public final UuidPowerCurveDescription delete(final byte[] uuid) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        UuidPowerCurveDescription deleted = null;
        StudyDesign studyDesign = null;
        try {
            /*
             * Retrieve Original PowerCurveDescription Object
             */
            studyDesign = get(uuid);
            if (studyDesign != null) {
                PowerCurveDescription original = studyDesign
                        .getPowerCurveDescriptions();
                /*
                 * Delete Existing PowerCurveDescription List Object
                 */
                if (original != null) {
                    deleted = delete(uuid, original);
                }
                /*
                 * Update Study Design Object
                 */
                studyDesign.setPowerCurveDescriptions(null);
                session.update(studyDesign);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete PowerCurveDescription object for UUID '"
                            + uuid + "': " + e.getMessage());
        }
        /*
         * Return PowerCurveDescription
         */
        return deleted;
    }

    /**
     * Delete UuidPowerCurveDescription.
     * 
     * @param uuid
     *            the uuid
     * @param original
     *            the original
     * @return the uuid power curve description
     */
    private UuidPowerCurveDescription delete(final byte[] uuid,
            final PowerCurveDescription original) {
        UuidPowerCurveDescription deleted = null;
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        try {
            session.delete(original);
            deleted = new UuidPowerCurveDescription(uuid, original);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to delete PowerCurveDescription object for UUID '"
                            + uuid + "': " + e.getMessage());
        }
        return deleted;
    }

    /**
     * Save or update UuidPowerCurveDescription.
     * 
     * @param uuidConfidence
     *            the uuid confidence
     * @param isCreation
     *            the is creation
     * @return the uuid power curve description
     */
    public final UuidPowerCurveDescription saveOrUpdate(
            final UuidPowerCurveDescription uuidConfidence,
            final boolean isCreation) {
        if (!transactionStarted) {
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Transaction has not been started.");
        }
        StudyDesign studyDesign = null;
        UuidPowerCurveDescription uuidPowerCurveDescription = null;
        PowerCurveDescription originalPowerCurve = null;
        byte[] uuid = uuidConfidence.getUuid();
        PowerCurveDescription newPowerCurve = uuidConfidence
                .getPowerCurveDescription();

        try {
            /*
             * Retrieve Study Design Object
             */
            studyDesign = get(uuid);
            if (studyDesign != null) {
                originalPowerCurve = studyDesign.getPowerCurveDescriptions();
                /*
                 * Delete Existing PowerCurveDescription Object
                 */
                if (originalPowerCurve != null) {
                    delete(uuid, originalPowerCurve);
                }
                if (isCreation) {
                    session.save(newPowerCurve);
                } else {
                    session.update(newPowerCurve);
                }
                /*
                 * Update Study Design Object
                 */
                studyDesign.setPowerCurveDescriptions(newPowerCurve);
                session.update(studyDesign);
                /*
                 * Return Persisted PowerCurveDescription
                 */
                uuidPowerCurveDescription = new UuidPowerCurveDescription(uuid,
                        newPowerCurve);
            }
        } catch (Exception e) {
            uuidPowerCurveDescription = null;
            System.out.println(e.getMessage());
            throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,
                    "Failed to save PowerCurveDescription object : "
                            + e.getMessage());
        }
        return uuidPowerCurveDescription;
    }

}
