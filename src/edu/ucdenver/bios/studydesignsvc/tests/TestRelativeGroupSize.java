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

import edu.ucdenver.bios.studydesignsvc.resource.RelativeGroupSizeResource;
import edu.ucdenver.bios.studydesignsvc.resource.RelativeGroupSizeRetrieveResource;
import edu.ucdenver.bios.webservice.common.domain.RelativeGroupSize;
import edu.ucdenver.bios.webservice.common.domain.RelativeGroupSizeList;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;

// TODO: Auto-generated Javadoc
/**
 * JUnit test cases for 'RelativeGroupSize' object - CRUD operations.
 * 
 * @author Uttara Sakhadeo
 */
public class TestRelativeGroupSize extends TestCase {

    /** The STUD y_ uuid. */
    private static UUID STUDY_UUID = UUID
            .fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");

    /** The resource. */
    RelativeGroupSizeResource resource = null;
    RelativeGroupSizeRetrieveResource retrieveResource = null;

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
                    "http://localhost:8080/study/relativeGroupSizeList");
            resource = clientResource.wrap(RelativeGroupSizeResource.class);
            clientResource = new ClientResource(
                    "http://localhost:8080/study/relativeGroupSizeList/retrieve");
            retrieveResource = clientResource
                    .wrap(RelativeGroupSizeRetrieveResource.class);
        } catch (Exception e) {
            System.err
                    .println("Failed to connect to server: " + e.getMessage());
            fail();
        }
    }

    /**
     * Test to create a RelativeGroupSize List.
     */
    @Test
    public void testCreate() {
        List<RelativeGroupSize> list = new ArrayList<RelativeGroupSize>();
        RelativeGroupSize relativeGroupSize = new RelativeGroupSize();
        relativeGroupSize.setValue(5);
        list.add(relativeGroupSize);
        relativeGroupSize = new RelativeGroupSize();
        relativeGroupSize.setValue(1);
        list.add(relativeGroupSize);
        RelativeGroupSizeList relativeGroupSizeList = new RelativeGroupSizeList(
                uuid, list);
        try {
            relativeGroupSizeList = resource.create(relativeGroupSizeList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            relativeGroupSizeList = null;
            fail();
        }
        if (relativeGroupSizeList == null) {
            fail();
        } else {
            System.out.println("testCreate() ");
            Gson gson = new Gson();
            String json = gson.toJson(relativeGroupSizeList);
            System.out.println(json);
            assertTrue(relativeGroupSizeList != null);
        }
    }

    /**
     * Test to update a RelativeGroupSize List.
     */
    @Test
    private void testUpdate() {
        StudyDesign studyDesign = new StudyDesign();
        studyDesign.setUuid(uuid);

        List<RelativeGroupSize> list = new ArrayList<RelativeGroupSize>();
        RelativeGroupSize relativeGroupSize = new RelativeGroupSize();
        relativeGroupSize.setValue(11);
        list.add(relativeGroupSize);
        relativeGroupSize = new RelativeGroupSize();
        relativeGroupSize.setValue(22);
        list.add(relativeGroupSize);
        RelativeGroupSizeList relativeGroupSizeList = new RelativeGroupSizeList(
                uuid, list);
        try {
            relativeGroupSizeList = resource.update(relativeGroupSizeList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            relativeGroupSizeList = null;
            fail();
        }
        if (relativeGroupSizeList == null) {
            fail();
        } else {
            System.out.println("testUpdate() : ");
            Gson gson = new Gson();
            String json = gson.toJson(relativeGroupSizeList);
            System.out.println(json);
            assertTrue(relativeGroupSizeList != null);
        }
    }

    /**
     * Test to retrieve a RelativeGroupSize List.
     */
    @Test
    public void testRetrieve() {
        RelativeGroupSizeList relativeGroupSizeList = null;

        try {
            relativeGroupSizeList = retrieveResource.retrieve(uuid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            relativeGroupSizeList = null;
            fail();
        }
        if (relativeGroupSizeList == null) {
            System.err.println("No matching confidence interval found");
            fail();
        } else {
            System.out.println("testRetrieve() : ");
            Gson gson = new Gson();
            String json = gson.toJson(relativeGroupSizeList);
            System.out.println(json);
            assertTrue(relativeGroupSizeList != null);
        }
    }

    /**
     * Test to delete a RelativeGroupSize List.
     */
    @Test
    private void testDelete() {
        RelativeGroupSizeList relativeGroupSizeList = null;

        try {
            relativeGroupSizeList = resource.remove(uuid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            relativeGroupSizeList = null;
            fail();
        }
        if (relativeGroupSizeList == null) {
            System.err.println("No matching confidence interval found");
            fail();
        } else {
            System.out.println("testDelete() : ");
            Gson gson = new Gson();
            String json = gson.toJson(relativeGroupSizeList);
            System.out.println(json);
            assertTrue(relativeGroupSizeList != null);
        }
    }

}
