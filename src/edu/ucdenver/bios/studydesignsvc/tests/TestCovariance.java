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
package edu.ucdenver.bios.studydesignsvc.tests;

import java.util.UUID;

import junit.framework.TestCase;

import org.junit.Test;
import org.restlet.resource.ClientResource;

import com.google.gson.Gson;

import edu.ucdenver.bios.studydesignsvc.resource.CovarianceResource;
import edu.ucdenver.bios.studydesignsvc.resource.CovarianceRetrieveResource;
import edu.ucdenver.bios.webservice.common.domain.Blob2DArray;
import edu.ucdenver.bios.webservice.common.domain.Covariance;
import edu.ucdenver.bios.webservice.common.domain.UuidCovariance;
import edu.ucdenver.bios.webservice.common.domain.UuidCovarianceName;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;

// TODO: Auto-generated Javadoc
/**
 * JUnit test cases for 'Covariance' object - CRUD operations.
 * 
 * @author Uttara Sakhadeo
 */
public class TestCovariance extends TestCase {

    /** The STUD y_ uuid. */
    private static UUID STUDY_UUID_1 = UUID
            .fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");

    /** The STUD y_ uui d_2. */
    private static UUID STUDY_UUID_2 = UUID
            .fromString("66ccfd20-4478-11e1-9641-0002a5d5c51b");

    /** The Constant COVARIANCE_NAME_1. */
    private static final String COVARIANCE_NAME_1 = "Covariance 1";

    /** The Constant COVARIANCE_NAME_2. */
    private static final String COVARIANCE_NAME_2 = "Covariance 2";

    /** The resource. */
    CovarianceResource resource = null;
    CovarianceRetrieveResource retrieveResource = null;

    /** The uuid. */
    byte[] uuid1 = null;

    /** The uuid2. */
    byte[] uuid2 = null;

    /** The columns. */
    int rows, columns;

    /** The client resource. */
    ClientResource clientResource = null;

    /*
     * Sets tomcat connection properties while calling each Test method.
     */
    public void setUp() {
        uuid1 = UUIDUtils.asByteArray(STUDY_UUID_1);
        uuid2 = UUIDUtils.asByteArray(STUDY_UUID_2);
        try {
            System.clearProperty("http.proxyHost");
            clientResource = new ClientResource(
                    "http://localhost:8080/study/covariance");
            resource = clientResource.wrap(CovarianceResource.class);
            clientResource = new ClientResource(
                    "http://localhost:8080/study/covariance/retrieve");
            retrieveResource = clientResource
                    .wrap(CovarianceRetrieveResource.class);
        } catch (Exception e) {
            System.err
                    .println("Failed to connect to server: " + e.getMessage());
            fail();
        }
    }

    /**
     * Test to create a Covariance.
     */
    @Test
    public void testCreate() {
        Covariance newCovariance = null;
        /*
         * Create a covariance object
         */
        Covariance covariance = new Covariance();
        covariance.setName(COVARIANCE_NAME_1);
        rows = 2;
        columns = 3;
        covariance.setColumns(columns);
        covariance.setRows(rows);
        covariance.setRho(1.2);
        covariance.setDelta(2.5);
        double[] value = new double[1];
        value[0] = 10;
        covariance.setStandardDeviationListFromArray(value);
        double[][] data = new double[rows][columns];
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                data[j][i] = 2.0;
            }
        }
        // covariance.setBlobFromArray(data);
        covariance.setBlob(new Blob2DArray(data));
        /*
         * Set covariance object to wrapper class along with uuid.
         */
        UuidCovariance uuidCovariance = new UuidCovariance(uuid1, covariance);

        try {
            newCovariance = resource.create(uuidCovariance);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            newCovariance = null;
            fail();
        }
        if (newCovariance == null) {
            fail();
        } else {
            System.out.println("testCreate() :  ");
            Gson gson = new Gson();
            String json = gson.toJson(newCovariance);
            System.out.println(json);
            assertTrue(newCovariance != null);
        }
    }

    /**
     * Test to retrieve a Covariance.
     */
    @Test
    public void testRetrieve() {
        Covariance covariance = null;
        UuidCovarianceName uuidCovarianceName = new UuidCovarianceName(uuid1,
                COVARIANCE_NAME_1);

        try {
            covariance = retrieveResource.retrieve(uuidCovarianceName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            covariance = null;
            fail();
        }
        if (covariance == null) {
            System.err.println("No matching Covariance found");
            fail();
        } else {
            System.out.println("testRetrieve() : ");
            Gson gson = new Gson();
            String json = gson.toJson(covariance);
            System.out.println(json);
            assertTrue(covariance != null);
        }
    }

    /**
     * Test to update a Covariance.
     */
    @Test
    private void testUpdate() {
        Covariance newCovariance = null;
        /*
         * Create a covariance object
         */
        Covariance covariance = new Covariance();
        covariance.setName(COVARIANCE_NAME_1 + " Updated");
        rows = 10;
        columns = 10;
        covariance.setColumns(columns);
        covariance.setRows(rows);
        covariance.setRho(100);
        covariance.setDelta(200);
        double[] value = new double[1];
        value[0] = 5;
        covariance.setStandardDeviationListFromArray(value);
        double[][] data = new double[rows][columns];
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                data[j][i] = 90;
            }
        }
        // covariance.setBlobFromArray(data);
        covariance.setBlob(new Blob2DArray(data));
        /*
         * Set covariance object to wrapper class along with uuid.
         */
        UuidCovariance uuidCovariance = new UuidCovariance(uuid1, covariance);
        try {
            newCovariance = resource.update(uuidCovariance);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            newCovariance = null;
            fail();
        }
        if (newCovariance == null) {
            fail();
        } else {
            System.out.println("testUpdate() : ");
            Gson gson = new Gson();
            String json = gson.toJson(newCovariance);
            System.out.println(json);
            assertTrue(newCovariance != null);
        }
    }

    /**
     * Test to delete a Covariance.
     */
    @Test
    public void testDelete() {
        Covariance covariance = null;
        UuidCovarianceName uuidCovarianceName = new UuidCovarianceName(uuid1,
                COVARIANCE_NAME_1);

        try {
            covariance = resource.remove(uuidCovarianceName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            covariance = null;
            fail();
        }
        if (covariance == null) {
            System.err.println("No matching Covariance found");
            fail();
        } else {
            System.out.println("testDelete() : ");
            Gson gson = new Gson();
            String json = gson.toJson(covariance);
            System.out.println(json);
            assertTrue(covariance != null);
            assertTrue(covariance != null);
        }
    }

}
