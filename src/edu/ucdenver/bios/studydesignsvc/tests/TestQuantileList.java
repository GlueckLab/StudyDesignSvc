/*
 * Study Design Service for the GLIMMPSE Software System.
 * This service stores study design definitions for users
 * of the GLIMMSE interface. Service contain all information
 * related to a power or sample size calculation.
 * The Study Design Service simplifies communication between
 * different screens in the user interface.
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
 * Foundation, Inc. 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 */
package edu.ucdenver.bios.studydesignsvc.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import junit.framework.TestCase;

import org.junit.Test;

import com.google.gson.Gson;

import edu.ucdenver.bios.studydesignsvc.resource.QuantileServerResource;
import edu.ucdenver.bios.webservice.common.domain.Quantile;
import edu.ucdenver.bios.webservice.common.domain.QuantileList;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;

/**
 * JUnit test cases for 'Quantile' object - CRUD operations.
 *
 * @author Uttara Sakhadeo
 */
public class TestQuantileList extends TestCase {

    /** The STUDY_UUID. */
    private static UUID STUDY_UUID = UUID
            .fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");

    /** The resource. */
    private QuantileServerResource resource = new QuantileServerResource();

    /** The uuid. */
    private byte[] uuid = null;

    /*
     * (non-Javadoc)
     *
     * @see junit.framework.TestCase#setUp()
     */
    public void setUp() {
        uuid = UUIDUtils.asByteArray(STUDY_UUID);
    }

    /**
     * Test to create a Quantile List.
     */
    @Test
    public final void testCreate() {

        QuantileList quantileList = new QuantileList();
        Quantile quantile = new Quantile();
        quantile.setValue(0.5);
        quantileList.add(quantile);
        quantile = new Quantile();
        quantile.setValue(1);
        quantileList.add(quantile);

        try {
            quantileList = resource.create(uuid, quantileList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            quantileList = null;
            fail();
        }
        if (quantileList == null) {
            fail();
        } else {
            System.out.println("testCreate() : ");
            Gson gson = new Gson();
            String json = gson.toJson(quantileList);
            System.out.println(json);
            assertTrue(quantileList != null);
        }
    }

    /**
     * Test to update a Quantile List.
     */
    @Test
    private final void testUpdate() {
        StudyDesign studyDesign = new StudyDesign();
        studyDesign.setUuid(uuid);

        QuantileList quantileList = new QuantileList();
        Quantile quantile = new Quantile();
        quantile.setValue(0.11);
        quantileList.add(quantile);
        quantile = new Quantile();
        quantile.setValue(0.22);
        quantileList.add(quantile);

        try {
            quantileList = resource.update(uuid, quantileList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            quantileList = null;
            fail();
        }
        if (quantileList == null) {
            fail();
        } else {
            System.out.println("testUpdate() : ");
            Gson gson = new Gson();
            String json = gson.toJson(quantileList);
            System.out.println(json);
            assertTrue(quantileList != null);
        }
    }

    /**
     * Test to retrieve a Quantile List.
     */
    @Test
    public final void testRetrieve() {
        List<Quantile> quantileList = null;

        try {
            quantileList = resource.retrieve(uuid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            quantileList = null;
            fail();
        }
        if (quantileList == null) {
            System.err.println("No matching confidence interval found");
            fail();
        } else {
            System.out.println("testRetrieve() : ");
            Gson gson = new Gson();
            String json = gson.toJson(quantileList);
            System.out.println(json);
            assertTrue(quantileList != null);
        }
    }

    /**
     * Test to delete a Quantile List.
     */
    @Test
    private final void testDelete() {
        List<Quantile> quantileList = null;

        try {
            quantileList = resource.remove(uuid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            quantileList = null;
            fail();
        }
        if (quantileList == null) {
            System.err.println("No matching confidence interval found");
            fail();
        } else {
            System.out.println("testDelete() : ");
            Gson gson = new Gson();
            String json = gson.toJson(quantileList);
            System.out.println(json);
            assertTrue(quantileList != null);
        }
    }

}
