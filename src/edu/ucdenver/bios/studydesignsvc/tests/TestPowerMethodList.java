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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import junit.framework.TestCase;

import org.junit.Test;
import org.restlet.resource.ClientResource;

import com.google.gson.Gson;

import edu.ucdenver.bios.studydesignsvc.resource.PowerMethodResource;
import edu.ucdenver.bios.studydesignsvc.resource.PowerMethodRetrieveResource;
import edu.ucdenver.bios.webservice.common.domain.PowerMethod;
import edu.ucdenver.bios.webservice.common.domain.PowerMethodList;
import edu.ucdenver.bios.webservice.common.enums.PowerMethodEnum;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;

// TODO: Auto-generated Javadoc
/**
 * JUnit test cases for 'PowerMethod' object - CRUD operations.
 * 
 * @author Uttara Sakhadeo
 */
public class TestPowerMethodList extends TestCase {

    /** The STUD y_ uuid. */
    private static UUID STUDY_UUID = UUID
            .fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");

    /** The resource. */
    PowerMethodResource resource = null;
    PowerMethodRetrieveResource retrieveResource = null;

    /** The uuid. */
    byte[] uuid = null;

    /*
     * Sets tomcat connection properties while calling each Test method.
     */
    public void setUp() {
        uuid = UUIDUtils.asByteArray(STUDY_UUID);
        try {
            System.clearProperty("http.proxyHost");
            ClientResource clientResource = new ClientResource(
                    "http://localhost:8080/study/powerMethodList");
            resource = clientResource.wrap(PowerMethodResource.class);
            clientResource = new ClientResource(
                    "http://localhost:8080/study/powerMethodList/retrieve");
            retrieveResource = clientResource
                    .wrap(PowerMethodRetrieveResource.class);
        } catch (Exception e) {
            System.err
                    .println("Failed to connect to server: " + e.getMessage());
            fail();
        }
    }

    /**
     * Test to create a PowerMethod List.
     */
    @Test
    public void testCreate() {

        List<PowerMethod> list = new ArrayList<PowerMethod>();
        PowerMethod powerMethod = new PowerMethod();
        powerMethod.setPowerMethodEnum(PowerMethodEnum.UNCONDITIONAL);
        list.add(powerMethod);
        powerMethod = new PowerMethod();
        powerMethod.setPowerMethodEnum(PowerMethodEnum.QUANTILE);
        list.add(powerMethod);
        PowerMethodList powerMethodList = new PowerMethodList(uuid, list);
        try {
            powerMethodList = resource.create(powerMethodList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            powerMethodList = null;
            fail();
        }
        if (powerMethodList == null) {
            fail();
        } else {
            System.out.println("testCreate() : ");
            Gson gson = new Gson();
            String json = gson.toJson(powerMethodList);
            System.out.println(json);
            assertTrue(powerMethodList != null);
        }
    }

    /**
     * Test to update a PowerMethod List.
     */
    @Test
    public void testUpdate() {
        List<PowerMethod> list = new ArrayList<PowerMethod>();
        PowerMethod powerMethod = new PowerMethod();
        powerMethod.setPowerMethodEnum(PowerMethodEnum.QUANTILE);
        list.add(powerMethod);
        PowerMethodList powerMethodList = new PowerMethodList(uuid, list);

        try {
            powerMethodList = resource.update(powerMethodList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            powerMethodList = null;
            fail();
        }
        if (powerMethodList == null) {
            fail();
        } else {
            System.out.println("testUpdate() : ");
            Gson gson = new Gson();
            String json = gson.toJson(powerMethodList);
            System.out.println(json);
            assertTrue(powerMethodList != null);
        }
    }

    /**
     * Test to retrieve a PowerMethod List.
     */
    @Test
    public void testRetrieve() {
        PowerMethodList powerMethodList = null;

        try {
            powerMethodList = retrieveResource.retrieve(uuid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            powerMethodList = null;
            fail();
        }
        if (powerMethodList == null) {
            System.err.println("No matching confidence interval found");
            fail();
        } else {
            System.out.println("testRetrieve() : ");
            Gson gson = new Gson();
            String json = gson.toJson(powerMethodList);
            System.out.println(json);
            assertTrue(powerMethodList != null);
        }
    }

    /**
     * Test to delete a PowerMethod List.
     */
    @Test
    public void testDelete() {
        PowerMethodList powerMethodList = null;

        try {
            powerMethodList = resource.remove(uuid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            powerMethodList = null;
            fail();
        }
        if (powerMethodList == null) {
            System.err.println("No matching confidence interval found");
            fail();
        } else {
            System.out.println("testDelete() : ");
            Gson gson = new Gson();
            String json = gson.toJson(powerMethodList);
            System.out.println(json);
            assertTrue(powerMethodList != null);
        }
    }

}
