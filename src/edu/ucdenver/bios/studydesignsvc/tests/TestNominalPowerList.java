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
import org.restlet.resource.ClientResource;

import com.google.gson.Gson;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignConstants;
import edu.ucdenver.bios.studydesignsvc.resource.NominalPowerResource;
import edu.ucdenver.bios.webservice.common.domain.NominalPower;
import edu.ucdenver.bios.webservice.common.domain.NominalPowerList;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;

/**
 * JUnit test cases for 'NominalPower' object - CRUD operations.
 *
 * @author Uttara Sakhadeo
 */
public class TestNominalPowerList extends TestCase {

    /** The STUDY_UUID. */
    private static UUID STUDY_UUID = UUID
            .fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");

    /** The resource. */
    private NominalPowerResource resource = null;

    /** The uuid. */
    private byte[] uuid = null;

    /*
     * (non-Javadoc)
     *
     * @see junit.framework.TestCase#setUp()
     */
    public void setUp() {
        uuid = UUIDUtils.asByteArray(STUDY_UUID);
        try
        {
            System.clearProperty("http.proxyHost");
            ClientResource clientResource = new ClientResource(
                "http://localhost:8080/study/"+StudyDesignConstants.TAG_NOMINAL_POWER_LIST);
            resource = clientResource.wrap(NominalPowerResource.class);            
        }
        catch (Exception e)
        {
            System.err.println("Failed to connect to server: " + e.getMessage());
            fail();
        }
    }

    /**
     * Test to create a NominalPower List.
     */
    @Test
    public final void testCreate() {
        List<NominalPower> list = new ArrayList<NominalPower>();          
        NominalPower nominalPower = new NominalPower();
        nominalPower.setValue(0.5);
        list.add(nominalPower);
        nominalPower = new NominalPower();
        nominalPower.setValue(0.1);
        list.add(nominalPower);
        NominalPowerList nominalPowerList = new NominalPowerList(uuid,list);
        try {
            nominalPowerList = resource.create(nominalPowerList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            nominalPowerList = null;
            fail();
        }
        if (nominalPowerList == null) {
            fail();
        } else {
            System.out.println("testCreate() : ");
            System.out.println(nominalPowerList);
        }
    }

    /**
     * Test to update a NominalPower List.
     */
    @Test
    private final void testUpdate() {
        List<NominalPower> list = new ArrayList<NominalPower>();
        NominalPower nominalPower = new NominalPower();
        nominalPower.setValue(0.11);
        list.add(nominalPower);
        nominalPower = new NominalPower();
        nominalPower.setValue(0.22);
        list.add(nominalPower);
        NominalPowerList nominalPowerList = new NominalPowerList(uuid,list);
        try {
            nominalPowerList = resource.update(nominalPowerList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            nominalPowerList = null;
            fail();
        }
        if (nominalPowerList == null) {
            fail();
        } else {
            System.out.println("testUpdate() : ");
            System.out.println(nominalPowerList);
        }
    }

    /**
     * Test to delete a NominalPower List.
     */
    @Test
    private final void testDelete() {
        NominalPowerList nominalPowerList = null;

        try {
            nominalPowerList = resource.remove(uuid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            nominalPowerList = null;
            fail();
        }
        if (nominalPowerList == null) {
            System.err.println("No matching NominalPower List found");
            fail();
        } else {
            System.out.println("testDelete() : ");
            System.out.println(nominalPowerList);
            assertTrue(nominalPowerList != null);
        }
    }

    /**
     * Test to retrieve a NominalPower List.
     */
    @Test
    public final void testRetrieve() {
        NominalPowerList nominalPowerList = null;

        try {
            nominalPowerList = resource.retrieve(uuid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            nominalPowerList = null;
            fail();
        }
        if (nominalPowerList == null) {
            System.err.println("No matching confidence interval found");
            fail();
        } else {
            System.out.println("testRetrieve() : ");
            Gson gson = new Gson();
            String json = gson.toJson(nominalPowerList);
            System.out.println(json);
            assertTrue(nominalPowerList != null);
        }
    }
}
