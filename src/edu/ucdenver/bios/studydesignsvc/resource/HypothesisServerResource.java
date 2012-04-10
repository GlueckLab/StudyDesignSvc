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
package edu.ucdenver.bios.studydesignsvc.resource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.restlet.data.Status;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.ucdenver.bios.studydesignsvc.manager.HypothesisManager;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.domain.BetweenParticipantFactor;
import edu.ucdenver.bios.webservice.common.domain.Hypothesis;
import edu.ucdenver.bios.webservice.common.domain.HypothesisBetweenParticipantMapping;
import edu.ucdenver.bios.webservice.common.domain.HypothesisRepeatedMeasuresMapping;
import edu.ucdenver.bios.webservice.common.domain.HypothesisSet;
import edu.ucdenver.bios.webservice.common.domain.RepeatedMeasuresNode;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

/**
 * Server Resource class for handling requests for the Hypothesis object. 
 * See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
/**
 * @author sakhadeu
 *
 */
public class HypothesisServerResource extends ServerResource
implements HypothesisResource
{
    HypothesisManager hypothesisManager = null;
    StudyDesignManager studyDesignManager = null;
    boolean uuidFlag;       
    
    /**
     * Retrieve a Hypothesis object for specified UUID.
     * 
     * @param byte[]
     * @return HypothesisSet
     */
    @Get
    public HypothesisSet retrieve(byte[] uuid) 
    {
        HypothesisSet hypothesisSet = null;
        if(uuid==null)
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
                    "no study design UUID specified");
        try
        {
            /* ----------------------------------------------------
             * Check for existence of a UUID in Study Design object 
             * ----------------------------------------------------*/
            studyDesignManager = new StudyDesignManager();          
            studyDesignManager.beginTransaction();                                              
                uuidFlag = studyDesignManager.hasUUID(uuid);
                if(uuidFlag)
                {       
                    StudyDesign studyDesign = studyDesignManager.get(uuid);
                    if(studyDesign!=null)
                        hypothesisSet = new HypothesisSet(studyDesign.getHypothesis());                    
                }               
            studyDesignManager.commit();                    
        }
        catch (BaseManagerException bme)
        {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if(hypothesisManager!=null)
            {
                try
                {hypothesisManager.rollback();}             
                catch(BaseManagerException re)
                {hypothesisSet = null;}             
            }
            hypothesisSet = null;
        }   
        catch(StudyDesignException sde)
        {
            System.out.println(sde.getMessage());
            StudyDesignLogger.getInstance().error(sde.getMessage());
            if(studyDesignManager!=null)
            {
                try {studyDesignManager.rollback();}
                catch(BaseManagerException re) {hypothesisSet = null;}                  
            }
            hypothesisSet = null;
        }                               
        return hypothesisSet;
    }
        
    /**
     * 
     * A method for Returning a BetweenParticipantFactor with specified id from the list.
     * 
     * @param studyBetweenParticipantList
     * @param id
     * @return
     */
    public BetweenParticipantFactor getBetweenParticipantFactor(List<BetweenParticipantFactor> studyBetweenParticipantList, int id)
    {
        for(BetweenParticipantFactor betweenParticipantFactor : studyBetweenParticipantList)
        {
            if(betweenParticipantFactor.getId() == id)
                return betweenParticipantFactor;
        }
        return null;
    }
    
    /**
     * 
     * A method for Returning a RepeatedMeasuresNode with specified id from the list.
     * 
     * @param studyRepeatedMeasuresTree
     * @param id
     * @return
     */
    public RepeatedMeasuresNode getRepeatedMeasuresNode(List<RepeatedMeasuresNode> studyRepeatedMeasuresTree, int id)
    {
        for(RepeatedMeasuresNode repeatedMeasuresNode : studyRepeatedMeasuresTree)
        {
            if(repeatedMeasuresNode.getId() == id)
                return repeatedMeasuresNode;
        }
        return null;
    }
    
    /**
     * 
     * A method for Checking existence of a BetweenParticipantFactor object in given list.
     * 
     * @param studyBetweenParticipantList
     * @param id
     * @return
     */
    public boolean checkBetweenParticipantFactorId(List<BetweenParticipantFactor> studyBetweenParticipantList, int id)
    {
        for(BetweenParticipantFactor betweenParticipantFactor : studyBetweenParticipantList)
        {
            if(betweenParticipantFactor.getId() == id)
                return true;
        }
        return false;
    }
    
    /**
     * 
     * A method for Checking existence of a RepeatedMeasuresNode object in given list.
     * 
     * @param studyRepeatedMeasuresTree
     * @param id
     * @return
     */
    public boolean checkRepeatedMeasuresNodeId(List<RepeatedMeasuresNode> studyRepeatedMeasuresTree, int id)
    {
        for(RepeatedMeasuresNode repeatedMeasuresNode : studyRepeatedMeasuresTree)
        {
            if(repeatedMeasuresNode.getId() == id)
                return true;
        }
        return false;
    }

    /**
     * 
     * A method for Checking existence of RepeatedMeasuresNode objects in given list.
     * 
     * @param studyBetweenParticipantList
     * @param hypothesisSet
     * @return
     */
    public boolean checkBetweenParticipantFactorEntry(List<BetweenParticipantFactor> studyBetweenParticipantList,HypothesisSet hypothesisSet)
    {
        try
        {
            Iterator<Hypothesis> itr = hypothesisSet.iterator();
            boolean flag = false;
            while(itr.hasNext())
            {
                Hypothesis hypothesis = itr.next();
                List<BetweenParticipantFactor> list = hypothesis.getBetweenParticipantFactorList();
                for(BetweenParticipantFactor betweenParticipantFactor : list)
                {
                    if(checkBetweenParticipantFactorId(studyBetweenParticipantList, betweenParticipantFactor.getId()))
                        flag = true;
                    else
                    {
                        flag = false;
                        break;
                    }
                }
            } 
            return flag;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }        
    }
    
    /**
     * 
     * A method for Checking existence of RepeatedMeasuresNode objects in given list.
     * 
     * @param studyRepeatedMeasuresTree
     * @param hypothesisSet
     * @return flag
     */
    public boolean checkRepeatedMeasuresNodeEntry(List<RepeatedMeasuresNode> studyRepeatedMeasuresTree,HypothesisSet hypothesisSet)
    {
        try
        {
            Iterator<Hypothesis> itr = hypothesisSet.iterator();
            boolean flag = false;
            while(itr.hasNext())
            {
                Hypothesis hypothesis = itr.next();
                List<RepeatedMeasuresNode> list = hypothesis.getRepeatedMeasuresList();
                for(RepeatedMeasuresNode repeatedMeasuresNode : list)
                {
                    if(checkRepeatedMeasuresNodeId(studyRepeatedMeasuresTree, repeatedMeasuresNode.getId()))
                        flag = true;
                    else
                    {
                        flag = false;
                        break;
                    }
                }
            }
            return flag;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }         
    }
    
    /**
     * 
     * A method returning set of Hypothesis objects containing passed BetweenParticipantList and RepeatedMeasuresTree.
     * 
     * @param studyBetweenParticipantList
     * @param 
     * @param hypothesisSet
     * @return
     */
    public HypothesisSet setEntry(List<BetweenParticipantFactor> studyBetweenParticipantList,List<RepeatedMeasuresNode> studyRepeatedMeasuresTree,HypothesisSet hypothesisSet)
    {
        HypothesisSet set = new HypothesisSet();        
        Iterator<Hypothesis> itr = hypothesisSet.iterator();
        
        while(itr.hasNext())
        {
            Hypothesis hypothesis = itr.next();
            List<HypothesisBetweenParticipantMapping> newBList = null;
            List<HypothesisRepeatedMeasuresMapping> newRList = null;
            if(studyBetweenParticipantList !=null && !studyBetweenParticipantList.isEmpty())
            {
                List<HypothesisBetweenParticipantMapping> bList = hypothesis.getBetweenParticipantFactorMapList();
                newBList = new ArrayList<HypothesisBetweenParticipantMapping>();
                for(HypothesisBetweenParticipantMapping betweenParticipantFactorMap : bList)
                {
                    //BetweenParticipantFactor b = getBetweenParticipantFactor(studyBetweenParticipantList,betweenParticipantFactor.getId());    
                    BetweenParticipantFactor b = getBetweenParticipantFactor(studyBetweenParticipantList,betweenParticipantFactorMap.getBetweenParticipantFactor().getId()); 
                    if(b!=null)
                    {    
                        newBList.add(new HypothesisBetweenParticipantMapping(betweenParticipantFactorMap.getType(),b));
                    }
                }
            }
            if(studyRepeatedMeasuresTree != null && !studyRepeatedMeasuresTree.isEmpty())
            {
                List<HypothesisRepeatedMeasuresMapping> rList = hypothesis.getRepeatedMeasuresMapTree();
                newRList = new ArrayList<HypothesisRepeatedMeasuresMapping>();
                for(HypothesisRepeatedMeasuresMapping repeatedMeasuresMap : rList)
                {
                    RepeatedMeasuresNode r = getRepeatedMeasuresNode(studyRepeatedMeasuresTree,repeatedMeasuresMap.getRepeatedMeasuresNode().getId()); 
                    if(r!=null)
                    {    
                        newRList.add(new HypothesisRepeatedMeasuresMapping(repeatedMeasuresMap.getType(),r));
                    }
                } 
            }
           set.add(new Hypothesis(hypothesis.getType(),newBList,newRList));
        }  
       return set;
    }
           
    @Post
    public HypothesisSet create(byte[] uuid, HypothesisSet hypothesisSet) 
    {
        StudyDesign studyDesign =null;
        if(uuid==null)
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
                    "no study design UUID specified");
        try
        {
            /* ----------------------------------------------------
             * Check for existence of a UUID in Study Design object 
             * ----------------------------------------------------*/
            studyDesignManager = new StudyDesignManager();
            studyDesignManager.beginTransaction();                                                      
                uuidFlag = studyDesignManager.hasUUID(uuid);                
                if(uuidFlag)
                {studyDesign = studyDesignManager.get(uuid);}                                                                                                                               
                else
                {throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
                        "no study design UUID specified");}
            studyDesignManager.commit();
            /* ----------------------------------------------------
             * Check existance of BetweenParticipantEfect 
             * ----------------------------------------------------*/
            List<BetweenParticipantFactor> studyBetweenParticipantList = null;
            List<RepeatedMeasuresNode> studyRepeatedMeasuresTree = null;
            if(!studyDesign.getBetweenParticipantFactorList().isEmpty()) {
                studyBetweenParticipantList = studyDesign.getBetweenParticipantFactorList();
            }
            if(!studyDesign.getRepeatedMeasuresTree().isEmpty()){
                studyRepeatedMeasuresTree = studyDesign.getRepeatedMeasuresTree();
            }
            if(uuidFlag && studyDesign!=null)
            {
               boolean flagBetweenParticipant = false;
               boolean flagRepeatedMeasures = false;
               if(studyBetweenParticipantList != null && !studyBetweenParticipantList.isEmpty()){
                   flagBetweenParticipant = checkBetweenParticipantFactorEntry(studyBetweenParticipantList, hypothesisSet);
               }
               if(studyRepeatedMeasuresTree !=null && !studyRepeatedMeasuresTree.isEmpty()){
                   flagRepeatedMeasures = checkRepeatedMeasuresNodeEntry(studyRepeatedMeasuresTree, hypothesisSet);
               }
               if(flagBetweenParticipant && flagRepeatedMeasures)
               {
                   //hypothesisSet = setBetweenParticipantFactorEntry(studyBetweenParticipantList, hypothesisSet);
                   hypothesisSet = setEntry(studyBetweenParticipantList, studyRepeatedMeasuresTree, hypothesisSet);
               }
               else if(flagBetweenParticipant)
               {
                   //hypothesisSet = setBetweenParticipantFactorEntry(studyBetweenParticipantList, hypothesisSet);
                   hypothesisSet = setEntry(studyBetweenParticipantList, null, hypothesisSet);
               }
               else if(flagRepeatedMeasures)
               {
                   //hypothesisSet = setBetweenParticipantFactorEntry(studyBetweenParticipantList, hypothesisSet);
                   hypothesisSet = setEntry(null, studyRepeatedMeasuresTree, hypothesisSet);
               }
            }
            /* ----------------------------------------------------
             * Remove existing Hypothesis for this object 
             * ----------------------------------------------------*/            
            if(uuidFlag && !studyDesign.getHypothesis().isEmpty())
                removeFrom(studyDesign);                
            if(uuidFlag)
            {   
                hypothesisManager = new HypothesisManager();
                hypothesisManager.beginTransaction();
                    hypothesisManager.saveOrUpdate(hypothesisSet, true);
                hypothesisManager.commit();             
                /* ----------------------------------------------------
                 * Set reference of HypothesisSet Object to Study Design object 
                 * ----------------------------------------------------*/
                studyDesign.setHypothesis(hypothesisSet);               
                studyDesignManager = new StudyDesignManager();
                studyDesignManager.beginTransaction();                  
                    studyDesign = studyDesignManager.saveOrUpdate(studyDesign, false);              
                studyDesignManager.commit();
            }       
        }
        catch (BaseManagerException bme)
        {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if(hypothesisManager!=null)
            {
                try
                {hypothesisManager.rollback();}             
                catch(BaseManagerException re)
                {hypothesisSet = null;}             
            }
            hypothesisSet = null;
        }   
        catch(StudyDesignException sde)
        {
            System.out.println(sde.getMessage());
            StudyDesignLogger.getInstance().error(sde.getMessage());
            if(studyDesignManager!=null)
            {
                try {studyDesignManager.rollback();}
                catch(BaseManagerException re) {hypothesisSet = null;}                  
            }
            hypothesisSet = null;
        }                               
        return hypothesisSet;
    }

    /**
     * Update a Hypothesis object for specified UUID.
     * 
     * @param byte[]
     * @return HypothesisSet
     */
    @Put("json")
    public HypothesisSet update(byte[] uuid,HypothesisSet hypothesisSet) 
    {               
        return create(uuid,hypothesisSet);          
    }   

    /**
     * Delete a Hypothesis object for specified UUID.
     * 
     * @param byte[]
     * @return HypothesisSet
     */
    @Delete("json")
    public HypothesisSet remove(byte[] uuid) 
    {
        HypothesisSet hypothesisSet = null;
        StudyDesign studyDesign = null;
        if(uuid==null)
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
                    "no study design UUID specified");
        try
        {           
            /* ----------------------------------------------------
             * Check for existence of a UUID in Study Design object 
             * ----------------------------------------------------*/
            studyDesignManager = new StudyDesignManager();          
            studyDesignManager.beginTransaction();                                              
                uuidFlag = studyDesignManager.hasUUID(uuid);
                if(uuidFlag)
                {       
                    studyDesign = studyDesignManager.get(uuid);
                    if(studyDesign!=null)
                        hypothesisSet = new HypothesisSet(studyDesign.getHypothesis());                                
                }               
            studyDesignManager.commit();
            /* ----------------------------------------------------
             * Remove existing Hypothesis objects for this object 
             * ----------------------------------------------------*/
            if(!hypothesisSet.isEmpty())
            {
                hypothesisManager = new HypothesisManager();
                hypothesisManager.beginTransaction();
                    hypothesisSet = new HypothesisSet(hypothesisManager.delete(uuid,hypothesisSet));
                hypothesisManager.commit();
                /* ----------------------------------------------------
                 * Set reference of Hypothesis Object to Study Design object 
                 * ----------------------------------------------------*/
                /*studyDesign.setBetaScaleList(null);               
                studyDesignManager = new StudyDesignManager();
                studyDesignManager.beginTransaction();                  
                    studyDesign = studyDesignManager.saveOrUpdate(studyDesign, false);              
                studyDesignManager.commit();
                hypothesisSet=studyDesign.getHypothesis();*/
            }
        }
        catch (BaseManagerException bme)
        {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error(bme.getMessage());
            if(hypothesisManager!=null)
            {
                try
                {hypothesisManager.rollback();}             
                catch(BaseManagerException re)
                {hypothesisSet = null;}             
            }
            hypothesisSet = null;
        }   
        catch(StudyDesignException sde)
        {
            System.out.println(sde.getMessage());
            StudyDesignLogger.getInstance().error(sde.getMessage());
            if(studyDesignManager!=null)
            {
                try {studyDesignManager.rollback();}
                catch(BaseManagerException re) {hypothesisSet = null;}                  
            }
            hypothesisSet = null;
        }       
        return hypothesisSet;
    }

    /**
     * Delete a Hypothesis object for specified Study Design.
     * 
     * @param StudyDesign
     * @return HypothesisSet
     */
    public HypothesisSet removeFrom(StudyDesign studyDesign) 
    {
        boolean flag;   
        HypothesisSet hypothesisSet = null;   
        try
        {                               
            hypothesisManager = new HypothesisManager();
            hypothesisManager.beginTransaction();
                hypothesisSet=new HypothesisSet(hypothesisManager.delete(studyDesign.getUuid(),studyDesign.getHypothesis()));
            hypothesisManager.commit();
            /* ----------------------------------------------------
             * Set reference of Hypothesis Object to Study Design object 
             * ----------------------------------------------------*/
            /*studyDesign.setConfidenceIntervalDescriptions(null);              
            studyDesignManager = new StudyDesignManager();
            studyDesignManager.beginTransaction();                  
                studyDesign = studyDesignManager.saveOrUpdate(studyDesign, false);              
            studyDesignManager.commit();    */        
        }
        catch (BaseManagerException bme)
        {
            System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error("Failed to load Study Design information: " + bme.getMessage());
            if (studyDesignManager != null) try { studyDesignManager.rollback(); } catch (BaseManagerException e) {}
            if (hypothesisManager != null) try { hypothesisManager.rollback(); } catch (BaseManagerException e) {}
            hypothesisSet = null;           
        }
        /*catch (StudyDesignException sde)
        {
            StudyDesignLogger.getInstance().error("Failed to load Study Design information: " + sde.getMessage());
            if (studyDesignManager != null) try { studyDesignManager.rollback(); } catch (BaseManagerException e) {}
            if (hypothesisManager != null) try { hypothesisManager.rollback(); } catch (BaseManagerException e) {}
            hypothesisSet = null;            
        }*/
        return hypothesisSet;
    }
}
