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

import junit.framework.TestCase;

import org.junit.Test;

import edu.cudenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.domain.ConfidenceIntervalDescription;
import edu.cudenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.cudenver.bios.studydesignsvc.manager.ConfidenceIntervalManager;
import edu.cudenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

public class TestStudyDesignConfidenceInterval extends TestCase
{
	private static UUID STUDY_UUID = UUID.fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");
	private static boolean HAS_GAUSSIAN_COVARIATE = true;
	private static String STUDY_NAME = "Junit Test Study Design";
	
	
	private static int SAMPLE_SIZE = 100;
	
	@Test
	public void testCreate()
	{
		/*StudyDesignManager studyDesignManager = null;
		ConfidenceIntervalManager confidenceIntervalManager = null;
		boolean flag;		
		StudyDesign studyDesign = new StudyDesign();
		ConfidenceInterval confidenceIntervalDescription = new ConfidenceInterval();
		
		studyDesign.setStudyUUID(STUDY_UUID);
		studyDesign.setHasGaussianCovariate(HAS_GAUSSIAN_COVARIATE);
		studyDesign.setName(STUDY_NAME);
		
		confidenceIntervalDescription.setSigmaFixed(true);
		//confidenceIntervalDescription.setStudyUUID(STUDY_UUID);
		confidenceIntervalDescription.setSampleSize(SAMPLE_SIZE);
		confidenceIntervalDescription.setRankOfDesignMatrix(2);
		confidenceIntervalDescription.setBetaFixed(true);
		confidenceIntervalDescription.setSigmaFixed(true);
		confidenceIntervalDescription.setLowerTrailProbability(0.5f);
		confidenceIntervalDescription.setUpperTrailProbability(0.5f);
		
		//studyDesign.setConfidenceIntervalDescription(confidenceIntervalDescription);
		confidenceIntervalDescription.setStudyDesign(studyDesign);
						
        try
        {
        	studyDesignManager = new StudyDesignManager();
        	studyDesignManager.beginTransaction();            
            	studyDesign = studyDesignManager.saveOrUpdateStudyDesign(studyDesign, true);            
            studyDesignManager.commit();
            
            confidenceIntervalManager = new ConfidenceIntervalManager();
            confidenceIntervalManager.beginTransaction();            
            	confidenceIntervalDescription = confidenceIntervalManager.saveOrUpdateConfidenceInterval(confidenceIntervalDescription, true);            
            studyDesignManager.commit();
            
        }
        catch (BaseManagerException bme)
        {
        	System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error("Failed to create study design object: " + bme.getMessage());
            if (studyDesignManager != null) try { studyDesignManager.rollback(); } catch (BaseManagerException e) {}
            studyDesign = null;
            fail();
        }
        catch (StudyDesignException sde)
        {
        	System.out.println(sde.getMessage());
            StudyDesignLogger.getInstance().error("Failed to create study design object: " + sde.getMessage());
            if (studyDesignManager != null) try { studyDesignManager.rollback(); } catch (BaseManagerException e) {}
            studyDesign = null;
            fail();
        }
        
        assertTrue(studyDesign != null && confidenceIntervalDescription !=null);*/
		
	}
	
	@Test
	private void testRetrieve()
	{
		
	}
	
	@Test
	private void testDelete()
	{
		/*StudyDesignManager manager = null;
		StudyDesign studyDesign = null;
        try
        {
            manager = new StudyDesignManager();
            manager.beginTransaction();
            studyDesign = manager.deleteStudyDesign(STUDY_UUID);
            manager.commit();
        }
        catch (BaseManagerException bme)
        {
        	System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error("Failed to delete Study Design information: " + bme.getMessage());
            if (manager != null) try { manager.rollback(); } catch (BaseManagerException e) {}
            studyDesign = null;
            fail();
        }
        catch (StudyDesignException sde)
        {
        	System.out.println(sde.getMessage());
            StudyDesignLogger.getInstance().error("Failed to delete Study Design information: " + sde.getMessage());
            if (manager != null) try { manager.rollback(); } catch (BaseManagerException e) {}
            studyDesign = null;
            fail();
        }
        
        
        if (studyDesign == null)
        {
        	System.err.println("No matching studydesign found");
        	fail();
        }
        else
        {
            String name = studyDesign.getName();
            assertTrue(STUDY_NAME.equals(name));
        }*/
	}
}
