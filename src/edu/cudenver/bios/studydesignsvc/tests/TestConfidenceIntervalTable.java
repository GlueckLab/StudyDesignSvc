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
package edu.cudenver.bios.studydesignsvc.tests;

import java.util.UUID;

import org.junit.Test;

import edu.cudenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.webservice.common.domain.ConfidenceIntervalDescription;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.cudenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.cudenver.bios.studydesignsvc.manager.ConfidenceIntervalManager;
import edu.cudenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;

import junit.framework.TestCase;

public class TestConfidenceIntervalTable extends TestCase 
{
	private static UUID STUDY_UUID = UUID.fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");
	private static int SAMPLE_SIZE = 100;
		
	/**
	 * Test retrieving a UUID from the database
	 * Note, this test must run after testCreate of a 
	 * not found will be thrown
	 */
	@Test
	public void testCreate()
	{	
		StudyDesignManager studyDesignManager = null;
		ConfidenceIntervalManager confidenceIntervalManager = null;
		boolean flag;				
		
		ConfidenceIntervalDescription confidenceInterval = new ConfidenceIntervalDescription();
		confidenceInterval.setSigmaFixed(true);
		//confidenceInterval.setStudyUUID(STUDY_UUID);
		confidenceInterval.setSampleSize(SAMPLE_SIZE);
		confidenceInterval.setRankOfDesignMatrix(2);
		confidenceInterval.setBetaFixed(true);
		confidenceInterval.setSigmaFixed(true);
		confidenceInterval.setLowerTrailProbability(0.5f);
		confidenceInterval.setUpperTrailProbability(0.5f);
		try
		{
			studyDesignManager = new StudyDesignManager();
            studyDesignManager.beginTransaction();
            	flag = studyDesignManager.hasUUID(UUIDUtils.asByteArray(STUDY_UUID));    
            	if(flag==true)
            	{
            		confidenceInterval.setStudyDesign(studyDesignManager.getStudyDesign(UUIDUtils.asByteArray(STUDY_UUID)));
            	}
            studyDesignManager.commit();
            if(flag==true)
            {
            	confidenceIntervalManager = new ConfidenceIntervalManager();
            	confidenceIntervalManager.beginTransaction();
            		confidenceIntervalManager.saveOrUpdateConfidenceInterval(confidenceInterval, true);
            	confidenceIntervalManager.commit();
            }
            else
            	throw new StudyDesignException("No such studyUUID present in tableStudyDesign!!!");
		}
		catch (BaseManagerException bme)
        {
			System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error("Failed to load confidence interval information: " + bme.getMessage());
            if (studyDesignManager != null) try { studyDesignManager.rollback(); } catch (BaseManagerException e) {}
            if (confidenceIntervalManager != null) try { confidenceIntervalManager.rollback(); } catch (BaseManagerException e) {}
            confidenceInterval = null;
            fail();
        }
        catch (StudyDesignException sde)
        {
            StudyDesignLogger.getInstance().error("Failed to load confidence interval information: " + sde.getMessage());
            if (studyDesignManager != null) try { studyDesignManager.rollback(); } catch (BaseManagerException e) {}
            if (confidenceIntervalManager != null) try { confidenceIntervalManager.rollback(); } catch (BaseManagerException e) {}
            confidenceInterval = null;
            fail();
        }
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			confidenceInterval=null;
			fail();
		}
	}
	
	/**
	 * Test retrieving a UUID from the database
	 * Note, this test must run after testCreate of a 
	 * not found will be thrown
	 */
	@Test
	public void testRetrieve()
	{
		StudyDesignManager studyDesignManager = null;
		ConfidenceIntervalManager confidenceIntervalManager = null;
		boolean flag;	
		ConfidenceIntervalDescription confidenceInterval = null;
        try
        {
            studyDesignManager = new StudyDesignManager();
            studyDesignManager.beginTransaction();
            	flag = studyDesignManager.hasUUID(UUIDUtils.asByteArray(STUDY_UUID));
            	/*if(flag==true)
            	{
            		confidenceInterval.setStudyDesign(studyDesignManager.getStudyDesign(STUDY_UUID));
            	}*/
            studyDesignManager.commit();
            if(flag==true)
            {
            	confidenceIntervalManager = new ConfidenceIntervalManager();
            	confidenceIntervalManager.beginTransaction();
            		confidenceInterval=confidenceIntervalManager.getConfidenceInterval(UUIDUtils.asByteArray(STUDY_UUID));
            	confidenceIntervalManager.commit();
            }
            else
            	throw new StudyDesignException("No such studyUUID present in tableStudyDesign!!!");
        }
        catch (BaseManagerException bme)
        {
        	System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error("Failed to load Study Design information: " + bme.getMessage());
            if (studyDesignManager != null) try { studyDesignManager.rollback(); } catch (BaseManagerException e) {}
            if (confidenceIntervalManager != null) try { confidenceIntervalManager.rollback(); } catch (BaseManagerException e) {}
            confidenceInterval = null;
            fail();
        }
        catch (StudyDesignException sde)
        {
            StudyDesignLogger.getInstance().error("Failed to load Study Design information: " + sde.getMessage());
            if (studyDesignManager != null) try { studyDesignManager.rollback(); } catch (BaseManagerException e) {}
            if (confidenceIntervalManager != null) try { confidenceIntervalManager.rollback(); } catch (BaseManagerException e) {}
            confidenceInterval = null;
            fail();
        }
                
        if (confidenceInterval == null)
        {
        	System.err.println("No matching confidence interval found");
        	fail();
        }
        else
        {              	                    
            assertTrue(confidenceInterval!=null);
        }
	}
	
	@Test
	private void testDelete()
	{
		StudyDesignManager studyDesignManager = null;
		ConfidenceIntervalManager confidenceIntervalManager = null;
		boolean flag;	
		ConfidenceIntervalDescription confidenceInterval = null;
        try
        {
            studyDesignManager = new StudyDesignManager();
            studyDesignManager.beginTransaction();
            	flag = studyDesignManager.hasUUID(UUIDUtils.asByteArray(STUDY_UUID));
            	/*if(flag==true)
            	{
            		confidenceInterval.setStudyDesign(studyDesignManager.getStudyDesign(STUDY_UUID));
            	}*/
            studyDesignManager.commit();
            if(flag==true)
            {
            	confidenceIntervalManager = new ConfidenceIntervalManager();
            	confidenceIntervalManager.beginTransaction();
            		confidenceInterval=confidenceIntervalManager.deleteConfidenceInterval(UUIDUtils.asByteArray(STUDY_UUID));
            	confidenceIntervalManager.commit();
            }
            else
            	throw new StudyDesignException("No such studyUUID present in tableStudyDesign!!!");
        }
        catch (BaseManagerException bme)
        {
        	System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error("Failed to load Study Design information: " + bme.getMessage());
            if (studyDesignManager != null) try { studyDesignManager.rollback(); } catch (BaseManagerException e) {}
            if (confidenceIntervalManager != null) try { confidenceIntervalManager.rollback(); } catch (BaseManagerException e) {}
            confidenceInterval = null;
            fail();
        }
        catch (StudyDesignException sde)
        {
            StudyDesignLogger.getInstance().error("Failed to load Study Design information: " + sde.getMessage());
            if (studyDesignManager != null) try { studyDesignManager.rollback(); } catch (BaseManagerException e) {}
            if (confidenceIntervalManager != null) try { confidenceIntervalManager.rollback(); } catch (BaseManagerException e) {}
            confidenceInterval = null;
            fail();
        }
                
        if (confidenceInterval == null)
        {
        	System.err.println("No matching confidence interval found");
        	fail();
        }
        else
        {              	                    
            assertTrue(confidenceInterval!=null);
        }
	}
	
}
