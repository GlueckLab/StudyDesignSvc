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

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import junit.framework.TestCase;

import org.junit.Test;
import org.restlet.resource.ClientResource;

import com.google.gson.Gson;

import edu.ucdenver.bios.studydesignsvc.resource.MatrixSetResource;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrixSet;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;

// TODO: Auto-generated Javadoc
/**
 * JUnit test cases for 'Covariance' object - CRUD operations.
 * 
 * @author Uttara Sakhadeo
 */
public class TestMatrixSet extends TestCase {
    /** The STUD y_ uuid. */
    private static UUID STUDY_UUID = UUID
            .fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");

    /** The THET a_ matri x_ name. */
    private static String THETA_MATRIX_NAME = "Theta Null Matrix";

    /** The BET a_ matri x_ name. */
    private static String BETA_MATRIX_NAME = "Beta Matrix";

    /** The set resource. */
    MatrixSetResource setResource = null;

    /** The client resource. */
    ClientResource clientResource = null;

    /** The uuid. */
    byte[] uuid = null;

    /** The columns. */
    int rows, columns;

    /*
     * Sets tomcat connection properties while calling each Test method.
     */
    public void setUp() {
        uuid = UUIDUtils.asByteArray(STUDY_UUID);
        try {
            System.clearProperty("http.proxyHost");
            clientResource = new ClientResource(
                    "http://localhost:8080/study/matrixSet");
            setResource = clientResource.wrap(MatrixSetResource.class);
        } catch (Exception e) {
            System.err
                    .println("Failed to connect to server: " + e.getMessage());
            fail();
        }
    }

    /**
     * Test to create a Set<NamedMatrix>.
     */
    @Test
    private void testCreateMatrixSet() {
        Set<NamedMatrix> matrixSet = new HashSet<NamedMatrix>();
        NamedMatrix matrix = new NamedMatrix(THETA_MATRIX_NAME);
        rows = 2;
        columns = 2;
        matrix.setColumns(columns);
        matrix.setRows(rows);
        double[][] data = new double[rows][columns];
        data[0][0] = 2.5;
        data[0][1] = 5.5;
        // matrix.setData(new Blob2DArray(data));
        // matrix.setDataFromArray(data);
        matrix.setDataFromArray(data);
        matrixSet.add(matrix);
        rows = 1;
        matrix = new NamedMatrix(BETA_MATRIX_NAME);
        matrix.setColumns(columns);
        matrix.setRows(rows);
        data = new double[rows][columns];
        data[0][0] = 10;
        data[0][1] = 50;
        // matrix.setData(new Blob2DArray(data));
        matrix.setDataFromArray(data);
        // matrix.setDataFromArray(data);
        matrixSet.add(matrix);
        NamedMatrixSet set = new NamedMatrixSet(uuid, matrixSet);
        try {
            set = setResource.create(set);
            // set = new MatrixSetServerResource().create(set);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            set = null;
            fail();
        }
        if (set == null) {
            fail();
        } else {
            System.out.println("testCreateMatrixSet() : ");
            Gson gson = new Gson();
            String json = gson.toJson(set);
            System.out.println(json);
            assertTrue(set != null);
        }
    }

    /**
     * Test to retrieve a Set<NamedMatrix>.
     */
    @Test
    public void testRetrieveMatrixSet() {
        NamedMatrixSet matrixSet = null;

        try {
            matrixSet = setResource.retrieve(uuid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            matrixSet = null;
            fail();
        }
        if (matrixSet == null) {
            System.err.println("No matching NamedMatrix found");
            fail();
        } else {
            System.out.println("testRetrieveMatrixSet() : ");
            Gson gson = new Gson();
            String json = gson.toJson(matrixSet);
            System.out.println(json);
            assertTrue(matrixSet != null);
        }
    }

    /**
     * Test to update a Set<NamedMatrix>.
     */
    @Test
    public void testUpdateMatrixSet() {
        Set<NamedMatrix> matrixSet = new HashSet<NamedMatrix>();
        NamedMatrix matrix = new NamedMatrix(THETA_MATRIX_NAME);
        rows = 10;
        columns = 10;
        matrix.setColumns(columns);
        matrix.setRows(rows);
        double[][] data = new double[rows][columns];
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                data[j][i] = 1.1;
            }
        }
        matrix.setDataFromArray(data);
        matrixSet.add(matrix);

        matrix = new NamedMatrix(BETA_MATRIX_NAME);
        rows = 5;
        columns = 5;
        matrix.setColumns(columns);
        matrix.setRows(rows);
        data = new double[rows][columns];
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                data[j][i] = 11;
            }
        }
        matrix.setDataFromArray(data);
        matrixSet.add(matrix);
        NamedMatrixSet set = new NamedMatrixSet(uuid, matrixSet);
        try {
            set = setResource.update(set);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            set = null;
            fail();
        }
        if (set == null) {
            fail();
        } else {
            System.out.println("testUpdateMatrixSet() : ");
            Gson gson = new Gson();
            String json = gson.toJson(set);
            System.out.println(json);
            assertTrue(set != null);
        }
    }

    /**
     * Test to delete a Set<NamedMatrix>.
     */
    @Test
    private void testDeleteMatrixSet() {
        NamedMatrixSet matrixSet = null;

        try {
            matrixSet = setResource.remove(uuid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            matrixSet = null;
            fail();
        }
        if (matrixSet == null) {
            System.err.println("No matching NamedMatrix found");
            fail();
        } else {
            System.out.println("testDeleteMatrixSet() : ");
            Gson gson = new Gson();
            String json = gson.toJson(matrixSet);
            System.out.println(json);
            assertTrue(matrixSet != null);
            assertTrue(matrixSet != null);
        }
    }
}
