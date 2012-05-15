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

import edu.ucdenver.bios.studydesignsvc.resource.StatisticalTestResource;
import edu.ucdenver.bios.studydesignsvc.resource.StatisticalTestRetrieveResource;
import edu.ucdenver.bios.webservice.common.domain.StatisticalTest;
import edu.ucdenver.bios.webservice.common.domain.StatisticalTestList;
import edu.ucdenver.bios.webservice.common.enums.StatisticalTestTypeEnum;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;

// TODO: Auto-generated Javadoc
/**
 * JUnit test cases for 'StatisticalTest' object - CRUD operations.
 * 
 * @author Uttara Sakhadeo
 */
public class TestStatisticalTestList extends TestCase {

    /** The STUD y_ uuid. */
    private static UUID STUDY_UUID = UUID
            .fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");

    /** The STUD y_ name. */
    private static String STUDY_NAME = "Junit StatisticalTest Study Design";

    /** The resource. */
    private StatisticalTestResource resource = null;
    private StatisticalTestRetrieveResource retrieveResource = null;

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
                    "http://localhost:8080/study/testList");
            resource = clientResource.wrap(StatisticalTestResource.class);
            clientResource = new ClientResource(
                    "http://localhost:8080/study/testList/retrieve");
            retrieveResource = clientResource
                    .wrap(StatisticalTestRetrieveResource.class);
        } catch (Exception e) {
            System.err
                    .println("Failed to connect to server: " + e.getMessage());
            fail();
        }
    }

    /**
     * Test to create a BetaScale List.
     */
    @Test
    public void testCreate() {
        List<StatisticalTest> list = new ArrayList<StatisticalTest>();
        StatisticalTest test = new StatisticalTest();
        test.setType(StatisticalTestTypeEnum.UNIREP);
        list.add(test);
        test = new StatisticalTest();
        test.setType(StatisticalTestTypeEnum.UNIREPBOX);
        list.add(test);
        StatisticalTestList testList = new StatisticalTestList(uuid, list);
        try {
            testList = resource.create(testList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            testList = null;
            fail();
        }
        if (testList == null) {
            fail();
        } else {
            System.out.println("testCreate() ");
            System.out.println(testList);
            assertTrue(testList != null);
        }
    }

    /**
     * Test to update a BetaScale List.
     */
    @Test
    private void testUpdate() {
        List<StatisticalTest> list = new ArrayList<StatisticalTest>();
        StatisticalTest test = new StatisticalTest();
        test.setType(StatisticalTestTypeEnum.UNIREPGG);
        list.add(test);
        test = new StatisticalTest();
        test.setType(StatisticalTestTypeEnum.UNIREP);
        list.add(test);
        StatisticalTestList testList = new StatisticalTestList(uuid, list);
        try {
            testList = resource.update(testList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            testList = null;
            fail();
        }
        if (testList == null) {
            fail();
        } else {
            System.out.println("testUpdate() : ");
            System.out.println(testList);
            assertTrue(testList != null);
        }
    }

    /**
     * Test to delete a BetaScale List.
     */
    @Test
    private void testDelete() {
        StatisticalTestList testList = null;

        try {
            testList = resource.remove(uuid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            testList = null;
            fail();
        }
        if (testList == null) {
            System.err.println("No matching confidence interval found");
            fail();
        } else {
            System.out.println("testDelete() : ");
            System.out.println(testList);
            assertTrue(testList != null);
        }
    }

    /**
     * Test to retrieve a BetaScale List.
     */
    @Test
    public void testRetrieve() {
        StatisticalTestList testList = null;

        try {
            testList = retrieveResource.retrieve(uuid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            testList = null;
            fail();
        }
        if (testList == null) {
            System.err.println("No matching StatisticalTest found");
            fail();
        } else {
            System.out.println("testRetrieve() : ");
            Gson gson = new Gson();
            String json = gson.toJson(testList);
            System.out.println(json);
            assertTrue(testList != null);
        }
    }
}
