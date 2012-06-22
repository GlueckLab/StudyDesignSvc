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
package edu.ucdenver.bios.studydesignsvc.resource;

import java.util.regex.Pattern;

import org.restlet.data.Status;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.manager.HypothesisManager;
import edu.ucdenver.bios.webservice.common.domain.Hypothesis;
import edu.ucdenver.bios.webservice.common.domain.UuidHypothesis;
import edu.ucdenver.bios.webservice.common.domain.UuidHypothesisType;
import edu.ucdenver.bios.webservice.common.enums.HypothesisTypeEnum;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;

// TODO: Auto-generated Javadoc
/**
 * Server Resource Interface for handling requests for the Hypothesis object. See
 * the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class HypothesisServerResource extends ServerResource implements
        HypothesisResource {

    /**
     * Creates the Hypothesis.
     * 
     * @param uuidHypothesis
     *            the uuid hypothesis
     * @return the hypothesis
     */
    @Post("application/json")
    public final Hypothesis create(UuidHypothesis uuidHypothesis) {
        HypothesisManager hypothesisManager = null;
        byte[] uuid = uuidHypothesis.getUuid();
        /*
         * Check : empty uuid.
         */
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        /*
         * Validate Uuid.
         */
        boolean uuidFlag = false;
        try {
            uuidFlag = Pattern.matches("[0-9a-fA-F]{32}",
                    UUIDUtils.bytesToHex(uuid));
        } catch (Exception e) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "invalid UUID specified");
        }
        if (!uuidFlag) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "invalid UUID specified");
        }
        
        /*
         * Check : empty Hypothesis.
         */
        Hypothesis hypothesis = uuidHypothesis.getHypothesis();
        if (hypothesis == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no Beta Scale specified");
        }
        try {
            /*
             * Save Hypothesis Set .
             */
            hypothesisManager = new HypothesisManager();
            hypothesisManager.beginTransaction();
            hypothesis = hypothesisManager.saveOrUpdate(uuidHypothesis, true);
            hypothesisManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (hypothesisManager != null) {
                try {
                    hypothesisManager.rollback();
                } catch (BaseManagerException re) {
                    hypothesis = null;
                }
            }
            hypothesis = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (hypothesisManager != null) {
                try {
                    hypothesisManager.rollback();
                } catch (BaseManagerException re) {
                    hypothesis = null;
                }
            }
            hypothesis = null;
        }
        return hypothesis;
    }

    /**
     * Updates the Hypothesis.
     * 
     * @param uuidHypothesis
     *            the uuid hypothesis
     * @return the hypothesis
     */
    @Put("application/json")
    public final Hypothesis update(final UuidHypothesis uuidHypothesis) {
        return create(uuidHypothesis);
    }

    /**
     * Removes the Hypothesis.
     * 
     * @param uuidHypothesisType
     *            the uuid hypothesis type
     * @return the hypothesis
     */
    @Delete("application/json")
    public final Hypothesis remove(final UuidHypothesisType uuidHypothesisType) {
        HypothesisManager hypothesisManager = null;
        Hypothesis hypothesis = null;
        byte[] uuid = uuidHypothesisType.getUuid();
        HypothesisTypeEnum hypothesisType = uuidHypothesisType.getType();
        /*
         * Check : empty uuid.
         */
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        /*
         * Check : empty hypothesisType.
         */
        if (hypothesisType == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "not valid Hypothesis Name specified");
        }
        /*
         * Validate Uuid.
         */
        boolean uuidFlag = false;
        try {
            uuidFlag = Pattern.matches("[0-9a-fA-F]{32}",
                    UUIDUtils.bytesToHex(uuid));
        } catch (Exception e) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "invalid UUID specified");
        }
        if (!uuidFlag) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "invalid UUID specified");
        }
        
        try {
            /*
             * Delete Hypothesis Set .
             */
            hypothesisManager = new HypothesisManager();
            hypothesisManager.beginTransaction();
            hypothesis = hypothesisManager.delete(uuidHypothesisType);
            hypothesisManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (hypothesisManager != null) {
                try {
                    hypothesisManager.rollback();
                } catch (BaseManagerException re) {
                    hypothesis = null;
                }
            }
            hypothesis = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (hypothesisManager != null) {
                try {
                    hypothesisManager.rollback();
                } catch (BaseManagerException re) {
                    hypothesis = null;
                }
            }
            hypothesis = null;
        }
        return hypothesis;
    }
}
