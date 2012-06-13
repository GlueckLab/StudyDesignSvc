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

import edu.ucdenver.bios.studydesignsvc.resource.BetaScaleResource;
import edu.ucdenver.bios.studydesignsvc.resource.BetaScaleRetrieveResource;
import edu.ucdenver.bios.studydesignsvc.resource.BetaScaleServerResource;
import edu.ucdenver.bios.webservice.common.domain.BetaScale;
import edu.ucdenver.bios.webservice.common.domain.BetaScaleList;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;

/**
 * JUnit test cases for 'BetaScale' object - CRUD operations.
 * 
 * @author Uttara Sakhadeo
 */
public class TestBetaScaleList extends TestCase {
    /** The STUD y_ uuid. */
    private static UUID STUDY_UUID = UUID
            .fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");
    /** The resource. */
    private BetaScaleServerResource resource = new BetaScaleServerResource();
    /** The uuid. */
    private byte[] uuid = null;
    /** The client resource. */
    ClientResource clientResource = null;

    /*
     * Sets tomcat connection properties while calling each Test method.
     */
    public final void setUp() {
        uuid = UUIDUtils.asByteArray(STUDY_UUID);

        /*
         * byte[] bytes = new byte[16]; ByteBuffer bb = ByteBuffer.wrap(bytes);
         * bb.order(ByteOrder.LITTE_ENDIAN | BIG_ENDIAN);
         * bb.putLong(UUID.getMostSignificantBits());
         * bb.putLong(UUID.getLeastSignificantBits());
         * 
         * // to reverse bb.flip(); UUID uuidB = new UUID(bb.getLong(),
         * bb.getLong());
         */

        Gson gson = new Gson();
        String json = gson.toJson(uuid);
        System.out.println(json);

        /*
         * UUID id = UUID.nameUUIDFromBytes(uuid); byte[] byteId =
         * UUIDUtils.asByteArray(id); System.out.println(id); Gson gson = new
         * Gson(); String json = gson.toJson(byteId); System.out.println(json);
         * 
         * UUID tempUuid = UUID.nameUUIDFromBytes(byteId);
         * System.out.println(tempUuid);
         */

        /*
         * try { System.clearProperty("http.proxyHost"); clientResource = new
         * ClientResource("http://localhost:8080/study/betaScaleList");
         * clientResource = new
         * ClientResource("http://localhost:8080/study/betaScaleList");
         * //betaScaleResource = clientResource.wrap(BetaScaleResource.class);
         * betaScaleResource = clientResource.wrap(BetaScaleResource.class); }
         * catch (Exception e) {
         * System.err.println("Failed to connect to server: " + e.getMessage());
         * fail(); }
         */
    }

    /**
     * Test to create a BetaScale List.
     */
    @Test
    public final void testCreate() {

        List<BetaScale> list = new ArrayList<BetaScale>();
        BetaScale betaScale = new BetaScale();
        betaScale.setValue(0.5);
        list.add(betaScale);
        betaScale = new BetaScale();
        betaScale.setValue(1);
        list.add(betaScale);
        BetaScaleList betaScaleList = new BetaScaleList(uuid, list);

        try {
            System.clearProperty("http.proxyHost");
            ClientResource clientResource = new ClientResource(
                    "http://localhost:8080/study/betaScaleList");
            // betaScaleResource = clientResource.wrap(BetaScaleResource.class);
            BetaScaleResource betaScaleResource = clientResource
                    .wrap(BetaScaleResource.class);
            /* betaScaleList = resource.create(uuid, betaScaleList); */
            betaScaleList = betaScaleResource.create(betaScaleList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            betaScaleList = null;
            fail();
        }
        if (betaScaleList == null) {
            fail();
        } else {
            System.out.println("testCreate() :  ");
            Gson gson = new Gson();
            String json = gson.toJson(betaScaleList);
            System.out.println(json);
            assertTrue(betaScaleList != null);
        }
    }

    /**
     * Test to update a BetaScale List.
     */
    @Test
    private final void testUpdate() {
        BetaScaleList betaScaleList = new BetaScaleList();
        List<BetaScale> list = new ArrayList<BetaScale>();
        betaScaleList.setUuid(uuid);
        BetaScale betaScale = new BetaScale();
        betaScale.setValue(0.11);
        list.add(betaScale);
        betaScale = new BetaScale();
        betaScale.setValue(0.22);
        list.add(betaScale);
        betaScale = new BetaScale();
        betaScale.setValue(0.33);
        list.add(betaScale);
        betaScaleList.setBetaScaleList(list);
        try {
            System.clearProperty("http.proxyHost");
            ClientResource clientResource = new ClientResource(
                    "http://localhost:8080/study/betaScaleList");
            BetaScaleResource betaScaleResource = clientResource
                    .wrap(BetaScaleResource.class);
            betaScaleList = betaScaleResource.update(betaScaleList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            betaScaleList = null;
            fail();
        }
        if (betaScaleList == null) {
            fail();
        } else {
            System.out.println("testUpdate() : ");
            Gson gson = new Gson();
            String json = gson.toJson(betaScaleList);
            System.out.println(json);
            assertTrue(betaScaleList != null);
        }
    }

    /**
     * Test to retrieve a BetaScale List.
     */
    @Test
    private final void testRetrieve() {
        BetaScaleList betaScaleList = null;

        try {
            System.clearProperty("http.proxyHost");
            ClientResource clientResource = new ClientResource(
                    "http://localhost:8080/study/betaScaleList/retrieve");
            /*
             * BetaScaleResource betaScaleResource =
             * clientResource.wrap(BetaScaleResource.class); betaScaleList =
             * betaScaleResource.retrieve(uuid);
             */
            BetaScaleRetrieveResource betaScaleResource = clientResource
                    .wrap(BetaScaleRetrieveResource.class);
            betaScaleList = betaScaleResource.retrieve(uuid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            betaScaleList = null;
            fail();
        }
        if (betaScaleList == null) {
            System.err.println("No matching Beta Scale found");
            fail();
        } else {
            System.out.println("testRetrieve() : ");
            Gson gson = new Gson();
            String json = gson.toJson(betaScaleList);
            System.out.println(json);
            assertTrue(betaScaleList != null);
        }
    }

    /**
     * Test to delete a BetaScale List.
     */
    @Test
    private final void testDelete() {
        BetaScaleList betaScaleList = null;

        try {
            System.clearProperty("http.proxyHost");
            ClientResource clientResource = new ClientResource(
                    "http://localhost:8080/study/betaScaleList");
            BetaScaleResource betaScaleResource = clientResource
                    .wrap(BetaScaleResource.class);
            betaScaleList = betaScaleResource.remove(uuid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            betaScaleList = null;
            fail();
        }
        if (betaScaleList == null) {
            System.err.println("No matching Beta Scale found");
            fail();
        } else {
            System.out.println("testDelete() : ");
            Gson gson = new Gson();
            String json = gson.toJson(betaScaleList);
            System.out.println(json);
            assertTrue(betaScaleList != null);
        }
    }
}
