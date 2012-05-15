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

import edu.ucdenver.bios.studydesignsvc.resource.PowerCurveResource;
import edu.ucdenver.bios.studydesignsvc.resource.PowerCurveRetrieveResource;
import edu.ucdenver.bios.webservice.common.domain.PowerCurveDescription;
import edu.ucdenver.bios.webservice.common.domain.UuidPowerCurveDescription;
import edu.ucdenver.bios.webservice.common.enums.HorizontalAxisLabelEnum;
import edu.ucdenver.bios.webservice.common.enums.StratificationVariableEnum;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;

// TODO: Auto-generated Javadoc
/**
 * JUnit test cases for 'PowerCurveDescription' object - CRUD operations.
 * 
 * @author Uttara Sakhadeo
 */
public class TestPowerCurveDescription extends TestCase {

    /** The STUD y_ uuid. */
    private static UUID STUDY_UUID = UUID
            .fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");

    /** The uuid. */
    byte[] uuid = null;
    // private static int SAMPLE_SIZE = 100;
    /** The study design manager. */
    PowerCurveResource resource = null;
    PowerCurveRetrieveResource retrieveResource = null;

    /*
     * Sets tomcat connection properties while calling each Test method.
     */
    public void setUp() {
        uuid = UUIDUtils.asByteArray(STUDY_UUID);
        try {
            System.clearProperty("http.proxyHost");
            ClientResource clientResource = new ClientResource(
                    "http://localhost:8080/study/powerCurveDescription");
            resource = clientResource.wrap(PowerCurveResource.class);
            clientResource = new ClientResource(
                    "http://localhost:8080/study/powerCurveDescription/retrieve");
            retrieveResource = clientResource.wrap(PowerCurveRetrieveResource.class);
        } catch (Exception e) {
            System.err
                    .println("Failed to connect to server: " + e.getMessage());
            fail();
        }
    }

    /**
     * Test to create a PowerCurveDescription.
     */
    @Test
    public void testCreate() {
        PowerCurveDescription powerCurveDescription = new PowerCurveDescription();
        powerCurveDescription.setPowerCurveDescription("test1 description");
        powerCurveDescription.setSampleSize(100);
        powerCurveDescription.setBetaScale(0.2);
        powerCurveDescription.setTypeIError(0.8f);
        powerCurveDescription
                .setHorizontalAxisLabelEnum(HorizontalAxisLabelEnum.TOTAL_SAMPLE_SIZE);
        powerCurveDescription
                .setStratificationVarEnum(StratificationVariableEnum.TYPE_I_ERROR);
        UuidPowerCurveDescription uuidPowerCurve = new UuidPowerCurveDescription(
                uuid, powerCurveDescription);
        try {
            uuidPowerCurve = resource.create(uuidPowerCurve);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            uuidPowerCurve = null;
            fail();
        }
        if (uuidPowerCurve == null) {
            fail();
        } else {
            System.out.println("testCreate() : ");
            System.out.println(uuidPowerCurve);
        }
    }

    /**
     * Test to update a PowerCurveDescription.
     */
    @Test
    public void testUpdate() {
        PowerCurveDescription powerCurveDescription = new PowerCurveDescription();
        powerCurveDescription.setPowerCurveDescription("changed");
        powerCurveDescription.setSampleSize(100);
        powerCurveDescription.setBetaScale(0.2);
        powerCurveDescription.setTypeIError(0.8);
        UuidPowerCurveDescription uuidPowerCurve = new UuidPowerCurveDescription(
                uuid, powerCurveDescription);
        try {
            uuidPowerCurve = resource.update(uuidPowerCurve);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            uuidPowerCurve = null;
            fail();
        }
        if (uuidPowerCurve == null) {
            fail();
        } else {
            System.out.println("testUpdate() : ");
            System.out.println(uuidPowerCurve);
        }
    }

    /**
     * Test to retrieve a PowerCurveDescription.
     */
    @Test
    public void testRetrieve() {
        UuidPowerCurveDescription powerCurveDescription = null;

        try {
            powerCurveDescription = retrieveResource.retrieve(uuid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            powerCurveDescription = null;
            fail();
        }
        if (powerCurveDescription == null) {
            System.err.println("No matching confidence interval found");
            fail();
        } else {
            System.out.println("testRetrieve() : ");
            // System.out.println(powerCurveDescription);
            Gson gson = new Gson();
            String json = gson.toJson(powerCurveDescription);
            System.out.println(json);
            assertTrue(powerCurveDescription != null);
        }
    }

    /**
     * Test to delete a PowerCurveDescription.
     */
    @Test
    public void testDelete() {
        UuidPowerCurveDescription powerCurveDescription = null;

        try {
            powerCurveDescription = resource.remove(uuid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            powerCurveDescription = null;
            fail();
        }
        if (powerCurveDescription == null) {
            System.err.println("No matching confidence interval found");
            fail();
        } else {
            System.out.println("testDelete() : ");
            System.out.println(powerCurveDescription);
            assertTrue(powerCurveDescription != null);
        }
    }
}
