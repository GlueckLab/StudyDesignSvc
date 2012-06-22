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

import java.util.List;
import java.util.regex.Pattern;

import org.restlet.data.Status;
import org.restlet.resource.Delete;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.manager.RelativeGroupSizeManager;
import edu.ucdenver.bios.webservice.common.domain.RelativeGroupSize;
import edu.ucdenver.bios.webservice.common.domain.RelativeGroupSizeList;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;

// TODO: Auto-generated Javadoc
/**
 * Server Resource class for handling (PUT, POST, DELETE) requests for the
 * Relative Group Size object. See the StudyDesignApplication class for URI
 * mappings
 * 
 * @author Uttara Sakhadeo
 */
public class RelativeGroupSizeServerResource extends ServerResource implements
        RelativeGroupSizeResource {

    /**
     * Creates the RelativeGroupSizeList.
     * 
     * @param relatuveGPList
     *            the relatuve gp list
     * @return the relative group size list
     */
    @Post("application/json")
    public final RelativeGroupSizeList create(
            RelativeGroupSizeList relatuveGPList) {
        RelativeGroupSizeManager relativeGroupSizeManager = null;
        byte[] uuid = relatuveGPList.getUuid();
        /*
         * Check : empty uuid.
         */
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        /*
         * Check : empty RelativeGroupSize list.
         */
        List<RelativeGroupSize> list = relatuveGPList
                .getRelativeGroupSizeList();
        if (list == null || list.isEmpty()) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no Beta Scale specified");
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
             * Save RelativeGroupSize list.
             */
            relativeGroupSizeManager = new RelativeGroupSizeManager();
            relativeGroupSizeManager.beginTransaction();
            relatuveGPList = relativeGroupSizeManager.saveOrUpdate(
                    relatuveGPList, true);
            relativeGroupSizeManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (relativeGroupSizeManager != null) {
                try {
                    relativeGroupSizeManager.rollback();
                } catch (BaseManagerException re) {
                    relatuveGPList = null;
                }
            }
            relatuveGPList = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (relativeGroupSizeManager != null) {
                try {
                    relativeGroupSizeManager.rollback();
                } catch (BaseManagerException re) {
                    relatuveGPList = null;
                }
            }
            relatuveGPList = null;
        }
        return relatuveGPList;
    }

    /**
     * Updates the RelativeGroupSizeList.
     * 
     * @param relatuveGPList
     *            the relatuve gp list
     * @return the relative group size list
     */
    @Put("application/json")
    public final RelativeGroupSizeList update(
            final RelativeGroupSizeList relatuveGPList) {
        return create(relatuveGPList);
    }

    /**
     * Removes the RelativeGroupSizeList.
     * 
     * @param uuid
     *            the uuid
     * @return the relative group size list
     */
    @Delete("application/json")
    public final RelativeGroupSizeList remove(final byte[] uuid) {
        RelativeGroupSizeManager relativeGroupSizeManager = null;
        RelativeGroupSizeList relatuveGPList = null;
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
        
        try {
            /*
             * Delete RelativeGroupSize list.
             */
            relativeGroupSizeManager = new RelativeGroupSizeManager();
            relativeGroupSizeManager.beginTransaction();
            relatuveGPList = relativeGroupSizeManager.delete(uuid);
            relativeGroupSizeManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (relativeGroupSizeManager != null) {
                try {
                    relativeGroupSizeManager.rollback();
                } catch (BaseManagerException re) {
                    relatuveGPList = null;
                }
            }
            relatuveGPList = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (relativeGroupSizeManager != null) {
                try {
                    relativeGroupSizeManager.rollback();
                } catch (BaseManagerException re) {
                    relatuveGPList = null;
                }
            }
            relatuveGPList = null;
        }
        return relatuveGPList;
    }

}
