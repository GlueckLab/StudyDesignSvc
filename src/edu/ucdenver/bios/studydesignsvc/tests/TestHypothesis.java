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
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import junit.framework.TestCase;

import org.junit.Test;
import org.restlet.resource.ClientResource;

import com.google.gson.Gson;

import edu.ucdenver.bios.studydesignsvc.resource.BetweenParticipantServerResource;
import edu.ucdenver.bios.studydesignsvc.resource.HypothesisResource;
import edu.ucdenver.bios.studydesignsvc.resource.HypothesisSetResource;
import edu.ucdenver.bios.webservice.common.domain.BetweenParticipantFactor;
import edu.ucdenver.bios.webservice.common.domain.BetweenParticipantFactorList;
import edu.ucdenver.bios.webservice.common.domain.Hypothesis;
import edu.ucdenver.bios.webservice.common.domain.HypothesisBetweenParticipantMapping;
import edu.ucdenver.bios.webservice.common.domain.HypothesisSet;
import edu.ucdenver.bios.webservice.common.domain.UuidHypothesis;
import edu.ucdenver.bios.webservice.common.enums.HypothesisTrendTypeEnum;
import edu.ucdenver.bios.webservice.common.enums.HypothesisTypeEnum;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;
// TODO: Auto-generated Javadoc
/**
 * JUnit test cases for 'Hypothesis' object - CRUD operations.
 * 
 * @author Uttara Sakhadeo
 */
public class TestHypothesis extends TestCase
{
    
    /** The STUD y_ uuid. */
    private static UUID STUDY_UUID = UUID.fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");
    
    /** The Constant COVARIANCE_NAME_1. */
    private static final String COVARIANCE_NAME_1 = "Hypothesis 1";
    
    /** The Constant COVARIANCE_NAME_2. */
    private static final String COVARIANCE_NAME_2 = "Hypothesis 2";
    
    /** The resource. */
    HypothesisResource resource = null;
    
    HypothesisSetResource setResource = null;
    
    /** The uuid. */
    byte[] uuid = null; 
    
    /** The columns. */
    int rows, columns;
    
    /** The client resource. */
    ClientResource clientResource = null; 
        
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    public void setUp() {
        uuid = UUIDUtils.asByteArray(STUDY_UUID);        
    }

    /**
     * Test to create a HypothesisSet.
     */
    @Test
    private void testCreateHypothesisSet()
    {           
        Set<Hypothesis> hypothesisSet = new HashSet<Hypothesis>();
        Hypothesis hypothesis = new Hypothesis();                       
            hypothesis.setType(HypothesisTypeEnum.INTERACTION);
            
            ArrayList<HypothesisBetweenParticipantMapping> betweenParticipantList = new ArrayList<HypothesisBetweenParticipantMapping>(); 
            HypothesisBetweenParticipantMapping map = null;
            
            BetweenParticipantServerResource betResource = new BetweenParticipantServerResource();
            BetweenParticipantFactorList betweenParticipantFactorList = betResource.retrieve(uuid);
            
            for(BetweenParticipantFactor factor : betweenParticipantFactorList.getBetweenParticipantFactorList()) {
                map = new HypothesisBetweenParticipantMapping();
                map.setBetweenParticipantFactor(factor);
                map.setType(HypothesisTrendTypeEnum.LINEAR);
                betweenParticipantList.add(map);
            }
                             
           hypothesis.setBetweenParticipantFactorMapList(betweenParticipantList);
               
           /*List<HypothesisRepeatedMeasuresMapping> repeatedMeasuresTree = new ArrayList<HypothesisRepeatedMeasuresMapping>(); 
               HypothesisRepeatedMeasuresMapping repeatedMeasuresMap = new HypothesisRepeatedMeasuresMapping();
                   RepeatedMeasuresNode r = new RepeatedMeasuresNode();
                   r.setId(1);
               repeatedMeasuresMap.setRepeatedMeasuresNode(r);
               repeatedMeasuresMap.setType(HypothesisTypeEnum.TREND);
               repeatedMeasuresTree.add(repeatedMeasuresMap);
               
               repeatedMeasuresMap = new HypothesisRepeatedMeasuresMapping();
                   r = new RepeatedMeasuresNode();
                   r.setId(2);
               repeatedMeasuresMap.setRepeatedMeasuresNode(r);
               repeatedMeasuresMap.setType(HypothesisTypeEnum.INTERACTION);
               repeatedMeasuresTree.add(repeatedMeasuresMap);
                
               hypothesis.setRepeatedMeasuresMapTree(repeatedMeasuresTree);*/
               
      hypothesisSet.add(hypothesis);  
      HypothesisSet set = new HypothesisSet(uuid,hypothesisSet);                        
        try
        {
            System.clearProperty("http.proxyHost");
            ClientResource clientResource = new ClientResource("http://localhost:8080/study/hypothesisSet");
            setResource = clientResource.wrap(HypothesisSetResource.class);  
            set = setResource.create(set);            
        }       
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            hypothesisSet=null;
            fail();
        }
        if(hypothesisSet==null)
        {
            fail();
        }
        else
        {
            System.out.println("testCreate() :  "+hypothesisSet.size());
            try
            {
             Gson gson = new Gson();
             String json = gson.toJson(hypothesisSet);  
             System.out.println(json);
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
           assertTrue(hypothesisSet!=null);
        }       
    } 
    
    /**
     * Test to create a Hypothesis.
     */
    @Test
    public void testCreateHypothesis()
    {           
        Hypothesis hypothesis = new Hypothesis();                       
            hypothesis.setType(HypothesisTypeEnum.INTERACTION);
            
            ArrayList<HypothesisBetweenParticipantMapping> betweenParticipantList = new ArrayList<HypothesisBetweenParticipantMapping>(); 
            HypothesisBetweenParticipantMapping map = null;
            
            BetweenParticipantServerResource betResource = new BetweenParticipantServerResource();
            BetweenParticipantFactorList betweenParticipantFactorList = betResource.retrieve(uuid);
            
            for(BetweenParticipantFactor factor : betweenParticipantFactorList.getBetweenParticipantFactorList()) {
                map = new HypothesisBetweenParticipantMapping();
                map.setBetweenParticipantFactor(factor);
                map.setType(HypothesisTrendTypeEnum.LINEAR);
                betweenParticipantList.add(map);
            }
                             
           hypothesis.setBetweenParticipantFactorMapList(betweenParticipantList);
               
           /*List<HypothesisRepeatedMeasuresMapping> repeatedMeasuresTree = new ArrayList<HypothesisRepeatedMeasuresMapping>(); 
               HypothesisRepeatedMeasuresMapping repeatedMeasuresMap = new HypothesisRepeatedMeasuresMapping();
                   RepeatedMeasuresNode r = new RepeatedMeasuresNode();
                   r.setId(1);
               repeatedMeasuresMap.setRepeatedMeasuresNode(r);
               repeatedMeasuresMap.setType(HypothesisTypeEnum.TREND);
               repeatedMeasuresTree.add(repeatedMeasuresMap);
               
               repeatedMeasuresMap = new HypothesisRepeatedMeasuresMapping();
                   r = new RepeatedMeasuresNode();
                   r.setId(2);
               repeatedMeasuresMap.setRepeatedMeasuresNode(r);
               repeatedMeasuresMap.setType(HypothesisTypeEnum.INTERACTION);
               repeatedMeasuresTree.add(repeatedMeasuresMap);
                
               hypothesis.setRepeatedMeasuresMapTree(repeatedMeasuresTree);*/
                                                     
        try
        {
            System.clearProperty("http.proxyHost");
            ClientResource clientResource = new ClientResource("http://localhost:8080/study/hypothesis");
            resource = clientResource.wrap(HypothesisResource.class);  
            hypothesis = resource.create(new UuidHypothesis(uuid, hypothesis));            
        }       
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            hypothesis=null;
            fail();
        }
        if(hypothesis==null)
        {
            fail();
        }
        else
        {
            System.out.println("testCreate() :  ");
            try
            {
             Gson gson = new Gson();
             String json = gson.toJson(hypothesis);  
             System.out.println(json);
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
           assertTrue(hypothesis!=null);
        }       
    }
    
    /**
     * Test to retrieve a Hypothesis.
     */
    @Test
    public void testRetrieve()
    {
        HypothesisSet hypothesisSet = null;           
        
        try
        {
            System.clearProperty("http.proxyHost");
            ClientResource clientResource = new ClientResource("http://localhost:8080/study/hypothesisSet");
            setResource = clientResource.wrap(HypothesisSetResource.class);  
            hypothesisSet = setResource.retrieve(uuid);            
        }       
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            hypothesisSet=null;
            fail();
        }
        if (hypothesisSet == null)
        {
            System.err.println("No matching Hypothesis found");
            fail();
        }
        else
        {     
            System.out.println("testRetrieve() : ");
            try
            {
             Gson gson = new Gson();
             String json = gson.toJson(hypothesisSet);  
             System.out.println(json);
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
            assertTrue(hypothesisSet!=null);
        }
    }
    
    /**
     * Test to update a Hypothesis.
     */
    @Test
    private void testUpdate()
    {
        Set<Hypothesis> hypothesisSet = new HashSet<Hypothesis>();
        Hypothesis hypothesis = new Hypothesis();                       
            hypothesis.setType(HypothesisTypeEnum.INTERACTION);
        hypothesisSet.add(hypothesis);  
        HypothesisSet set = new HypothesisSet(uuid,hypothesisSet);
                        
        try
        {
            System.clearProperty("http.proxyHost");
            ClientResource clientResource = new ClientResource("http://localhost:8080/study/hypothesisSet");
            setResource = clientResource.wrap(HypothesisSetResource.class);  
            set = setResource.update(set);            
        }       
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            hypothesisSet=null;
            fail();
        }
        if(hypothesisSet==null)
        {
            fail();
        }
        else
        {
            System.out.println("testUpdate() : "+hypothesisSet.size());
            Gson gson = new Gson();
            String json = gson.toJson(hypothesisSet);  
            System.out.println(json);
           assertTrue(hypothesisSet!=null);
        }
    }
    
    /**
     * Test to delete a Hypothesis.
     */
    @Test
    private void testDelete()
    {
        HypothesisSet hypothesisSet = null;           
        
        try
        {
            System.clearProperty("http.proxyHost");
            ClientResource clientResource = new ClientResource("http://localhost:8080/study/hypothesisSet");
            setResource = clientResource.wrap(HypothesisSetResource.class);  
            hypothesisSet = setResource.remove(uuid);          
        }       
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            hypothesisSet=null;
            fail();
        }
        if (hypothesisSet == null)
        {
            System.err.println("No matching Hypothesis found");
            fail();
        }
        else
        {     
            System.out.println("testDelete() : ");
            Gson gson = new Gson();
            String json = gson.toJson(hypothesisSet);  
            System.out.println(json);
           assertTrue(hypothesisSet!=null);
            assertTrue(hypothesisSet!=null);
        }
    }
        
}

