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

import org.restlet.data.Status;
import org.restlet.resource.Delete;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.manager.MatrixManager;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;
import edu.ucdenver.bios.webservice.common.domain.UuidMatrix;
import edu.ucdenver.bios.webservice.common.domain.UuidMatrixName;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

// TODO: Auto-generated Javadoc
/**
 * Server Resource class for handling (PUT, POST, DELETE) requests for the
 * NamedMatrix object. See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class MatrixServerResource extends ServerResource implements
        MatrixResource {

    /**
     * Creates the NamedMatrix.
     * 
     * @param uuidMatrix
     *            the uuid matrix
     * @return the named matrix
     */
    @Post("application/json")
    public final NamedMatrix create(UuidMatrix uuidMatrix) {
        MatrixManager matrixManager = null;
        byte[] uuid = uuidMatrix.getUuid();
        /*
         * Check : empty uuid.
         */
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        /*
         * Check : empty NamedMatrix.
         */
        NamedMatrix covariance = uuidMatrix.getMatrix();
        if (covariance == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no Beta Scale specified");
        }
        try {
            /*
             * Save NamedMatrix Set .
             */
            matrixManager = new MatrixManager();
            matrixManager.beginTransaction();
            covariance = matrixManager.saveOrUpdate(uuidMatrix, true);
            matrixManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (matrixManager != null) {
                try {
                    matrixManager.rollback();
                } catch (BaseManagerException re) {
                    covariance = null;
                }
            }
            covariance = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (matrixManager != null) {
                try {
                    matrixManager.rollback();
                } catch (BaseManagerException re) {
                    covariance = null;
                }
            }
            covariance = null;
        }
        return covariance;
    }

    /**
     * Updates the NamedMatrix.
     * 
     * @param uuidMatrix
     *            the uuid matrix
     * @return the named matrix
     */
    @Put("application/json")
    public final NamedMatrix update(final UuidMatrix uuidMatrix) {
        return create(uuidMatrix);
    }

    /**
     * Removes the NamedMatrix.
     * 
     * @param uuidMatrixName
     *            the uuid matrix name
     * @return the named matrix
     */
    @Delete("application/json")
    public final NamedMatrix remove(final UuidMatrixName uuidMatrixName) {
        MatrixManager matrixManager = null;
        NamedMatrix covariance = null;
        byte[] uuid = uuidMatrixName.getUuid();
        String name = uuidMatrixName.getMatrixName();
        /*
         * Check : empty uuid.
         */
        if (uuid == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "no study design UUID specified");
        }
        /*
         * Check : empty name.
         */
        if (name == null || name.isEmpty()) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
                    "not valid NamedMatrix Name specified");
        }

        try {
            /*
             * Delete NamedMatrix Set .
             */
            matrixManager = new MatrixManager();
            matrixManager.beginTransaction();
            covariance = matrixManager.delete(uuidMatrixName);
            matrixManager.commit();

        } catch (BaseManagerException bme) {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if (matrixManager != null) {
                try {
                    matrixManager.rollback();
                } catch (BaseManagerException re) {
                    covariance = null;
                }
            }
            covariance = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            StudyDesignLogger.getInstance().error(e.getMessage());
            if (matrixManager != null) {
                try {
                    matrixManager.rollback();
                } catch (BaseManagerException re) {
                    covariance = null;
                }
            }
            covariance = null;
        }
        return covariance;
    }
    /*
     * @Get("application/json") public NamedMatrix retrieve(UuidMatrixName
     * uuidName) { StudyDesignManager studyDesignManager = null; boolean
     * uuidFlag;
     * 
     * NamedMatrix matrix = null; byte[] uuid = uuidName.getUuid(); String name
     * = uuidName.getMatrixName(); if(uuid==null) throw new
     * ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
     * "no study design UUID specified"); try {
     * /*---------------------------------------------------- Check for
     * existence of a UUID in Study Design object
     * ---------------------------------------------------- studyDesignManager =
     * new StudyDesignManager(); studyDesignManager.beginTransaction(); uuidFlag
     * = studyDesignManager.hasUUID(uuid); if(uuidFlag) { StudyDesign
     * studyDesign = studyDesignManager.get(uuid); matrix =
     * studyDesign.getNamedMatrix(name); } studyDesignManager.commit(); } catch
     * (BaseManagerException bme) { System.out.println(bme.getMessage());
     * StudyDesignLogger.getInstance().error(bme.getMessage());
     * if(studyDesignManager!=null) { try {studyDesignManager.rollback();}
     * catch(BaseManagerException re) {matrix = null;} } matrix = null; }
     * catch(StudyDesignException sde) { System.out.println(sde.getMessage());
     * StudyDesignLogger.getInstance().error(sde.getMessage());
     * if(studyDesignManager!=null) { try {studyDesignManager.rollback();}
     * catch(BaseManagerException re) {matrix = null;} } matrix = null; } return
     * matrix; }
     * 
     * 
     * 
     * 
     * 
     * 
     * @Post("application/json") public NamedMatrix create(UuidMatrix
     * uuidMatrix) { MatrixManager matrixManager = null; StudyDesignManager
     * studyDesignManager = null; boolean uuidFlag; byte[] uuid =
     * uuidMatrix.getUuid(); NamedMatrix namedMatrix = uuidMatrix.getMatrix();
     * StudyDesign studyDesign = null; if(uuid==null) throw new
     * ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
     * "no study design UUID specified"); String name = namedMatrix.getName();
     * try { ---------------------------------------------------- Check for
     * existence of a UUID in Study Design object
     * ---------------------------------------------------- studyDesignManager =
     * new StudyDesignManager(); studyDesignManager.beginTransaction(); uuidFlag
     * = studyDesignManager.hasUUID(uuid); if(uuidFlag) { studyDesign =
     * studyDesignManager.get(uuid); } else {throw new
     * ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
     * "no study design for specified UUID is stored");}
     * studyDesignManager.commit();
     * ---------------------------------------------------- Remove existing
     * Matrix for this object
     * ---------------------------------------------------- NamedMatrixSet map =
     * studyDesign.getMatrixSet(); if(map != null ) {
     * 
     * Iterator itr = map.entrySet().iterator(); while(itr.hasNext()) {
     * Map.Entry<String, NamedMatrix> entry =
     * (Map.Entry<String,NamedMatrix>)itr.next(); //List<NamedMatrixCell>
     * namedMatrixCell= entry.getValue().getMatrixCellList();
     * if(entry.getKey().equals(namedMatrix.getName())) {
     * remove(uuid,entry.getKey());} } } else {
     * ---------------------------------------------------- Set reference of
     * Study Design Object to Matrix element
     * ---------------------------------------------------- map = new
     * HashNamedMatrixSet(); } map.put(namedMatrix.getName(), namedMatrix);
     * boolean flag = studyDesign.hasNamedMatrix(name); if(uuidFlag &&
     * studyDesign.getMatrixSet()!=null && !studyDesign.getMatrixSet().isEmpty()
     * && flag) { ---------------------------------------------------- Remove
     * existing Matrix for this object
     * ----------------------------------------------------
     * removeFrom(studyDesign,name); } if(uuidFlag &&
     * studyDesign.getMatrixSet()!=null) { matrixManager = new MatrixManager();
     * matrixManager.beginTransaction();
     * matrixManager.saveOrUpdate(namedMatrix,true); matrixManager.commit();
     * ---------------------------------------------------- Save new NamedMatrix
     * List object ----------------------------------------------------
     * studyDesign.setNamedMatrix(namedMatrix); studyDesignManager = new
     * StudyDesignManager(); studyDesignManager.beginTransaction();
     * studyDesignManager.saveOrUpdate(studyDesign, false);
     * studyDesignManager.commit(); }
     * 
     * } catch (BaseManagerException bme) {
     * System.out.println(bme.getMessage());
     * StudyDesignLogger.getInstance().error(bme.getMessage());
     * if(matrixManager!=null) { try {matrixManager.rollback();}
     * catch(BaseManagerException re) {namedMatrix = null;} } namedMatrix =
     * null; } catch(StudyDesignException sde) {
     * System.out.println(sde.getMessage());
     * StudyDesignLogger.getInstance().error(sde.getMessage());
     * if(studyDesignManager!=null) { try {studyDesignManager.rollback();}
     * catch(BaseManagerException re) {namedMatrix = null;} } namedMatrix =
     * null; } return namedMatrix; }
     * 
     * 
     * @Delete("application/json") public NamedMatrix remove(UuidMatrixName
     * uuidName) { MatrixManager matrixManager = null; StudyDesignManager
     * studyDesignManager = null; boolean uuidFlag;
     * 
     * NamedMatrixSet matrixSet = null; NamedMatrix matrix = null; StudyDesign
     * studyDesign = null; byte[] uuid = uuidName.getUuid(); String name =
     * uuidName.getMatrixName(); if(uuid==null) throw new
     * ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,
     * "no study design UUID specified"); try {
     * ---------------------------------------------------- Check for existence
     * of a UUID in Study Design object
     * ---------------------------------------------------- studyDesignManager =
     * new StudyDesignManager(); studyDesignManager.beginTransaction(); uuidFlag
     * = studyDesignManager.hasUUID(uuid); if(uuidFlag) { studyDesign =
     * studyDesignManager.get(uuid); if(studyDesign!=null) matrixSet = new
     * NamedMatrixSet(uuid,studyDesign.getMatrixSet()); }
     * studyDesignManager.commit();
     * ---------------------------------------------------- Remove existing
     * NamedMatrix objects for this object
     * ---------------------------------------------------- if(matrixSet!=null
     * && studyDesign.hasNamedMatrix(name)) { matrixManager = new
     * MatrixManager(); matrixManager.beginTransaction(); matrix =
     * matrixManager.delete(uuid, studyDesign.getNamedMatrix(name));
     * matrixManager.commit();
     * ---------------------------------------------------- Set reference of
     * NamedMatrix Object to Study Design object
     * ----------------------------------------------------
     * studyDesign.setBetaScaleList(null); studyDesignManager = new
     * StudyDesignManager(); studyDesignManager.beginTransaction(); studyDesign
     * = studyDesignManager.saveOrUpdate(studyDesign, false);
     * studyDesignManager.commit(); matrixSet=studyDesign.getMatrixSet(); } }
     * catch (BaseManagerException bme) { System.out.println(bme.getMessage());
     * StudyDesignLogger.getInstance().error(bme.getMessage());
     * if(matrixManager!=null) { try {matrixManager.rollback();}
     * catch(BaseManagerException re) {matrix = null;} } matrix = null; }
     * catch(StudyDesignException sde) { System.out.println(sde.getMessage());
     * StudyDesignLogger.getInstance().error(sde.getMessage());
     * if(studyDesignManager!=null) { try {studyDesignManager.rollback();}
     * catch(BaseManagerException re) {matrix = null;} } matrix = null; } return
     * matrix; }
     *//**
     * Delete a NamedMatrix object for specified Study Design.
     * 
     * @param StudyDesign
     * @return NamedMatrixSet
     */
    /*
     * public NamedMatrix removeFrom(StudyDesign studyDesign,String name) {
     * MatrixManager matrixManager = null; NamedMatrix matrix = null; try {
     * matrixManager = new MatrixManager(); matrixManager.beginTransaction();
     * matrix
     * =matrixManager.delete(studyDesign.getUuid(),studyDesign.getNamedMatrix
     * (name)); matrixManager.commit(); } catch (BaseManagerException bme) {
     * System.out.println(bme.getMessage());
     * StudyDesignLogger.getInstance().error
     * ("Failed to load Study Design information: " + bme.getMessage()); if
     * (matrixManager != null) try { matrixManager.rollback(); } catch
     * (BaseManagerException e) {} matrix = null; } return matrix; }
     */
}
