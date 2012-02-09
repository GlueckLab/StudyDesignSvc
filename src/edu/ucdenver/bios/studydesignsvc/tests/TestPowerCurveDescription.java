package edu.ucdenver.bios.studydesignsvc.tests;

import java.util.UUID;

import org.junit.Test;

import edu.cudenver.bios.studydesignsvc.resource.PowerCurveServerResource;
import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.ucdenver.bios.studydesignsvc.manager.ConfidenceIntervalManager;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.domain.ConfidenceIntervalDescription;
import edu.ucdenver.bios.webservice.common.domain.PowerCurveDescription;
import edu.ucdenver.bios.webservice.common.domain.PowerCurveDescription.StratificationVariable;

import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.enumclasses.HorizontalAxisLabel;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;
import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;
import junit.framework.TestCase;

public class TestPowerCurveDescription extends TestCase 
{
	private static UUID STUDY_UUID = UUID.fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");
	byte[] uuid = null;
	private static int SAMPLE_SIZE = 100;
	StudyDesignManager studyDesignManager = null;
	ConfidenceIntervalManager confidenceIntervalManager = null;
		
	public void setUp()
	{
		uuid = UUIDUtils.asByteArray(STUDY_UUID);
	}
	
	/**
	 * Test retrieving a UUID from the database
	 * Note, this test must run after testCreate of a 
	 * not found will be thrown
	 */
	@Test
	public void testCreate()
	{	
		
		boolean flag;
		StudyDesign studyDesign = new StudyDesign();
		studyDesign.setUuid(uuid);
		
		PowerCurveDescription powerCurveDescription = new PowerCurveDescription();
		powerCurveDescription.setPowerCurveDescription("test1 description");
		powerCurveDescription.setSampleSize(100);
		powerCurveDescription.setRegressionCoeeficientScaleFactor(0.2f);
		powerCurveDescription.setTypeIError(0.8f);		
		powerCurveDescription.setStudyDesign(studyDesign);
		powerCurveDescription.setHorizontalAxisLabel(HorizontalAxisLabel.TOTAL_SAMPLE_SIZE);
		powerCurveDescription.setStratificationVar(StratificationVariable.TYPE_I_ERROR);
		
		PowerCurveServerResource resource = new  PowerCurveServerResource();		
		
		try
		{
			resource.create(powerCurveDescription);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			powerCurveDescription=null;
			fail();
		}
	}
	
	/**
	 * Test retrieving a UUID from the database
	 * Note, this test must run after testCreate of a 
	 * not found will be thrown
	 */
	@Test
	private void testUpdate()
	{			
		boolean flag;
		StudyDesign studyDesign = new StudyDesign();
		studyDesign.setUuid(uuid);
		
		PowerCurveDescription powerCurveDescription = new PowerCurveDescription();
		powerCurveDescription.setPowerCurveDescription("changed");
		powerCurveDescription.setSampleSize(100);
		powerCurveDescription.setRegressionCoeeficientScaleFactor(0.2f);
		powerCurveDescription.setTypeIError(0.8f);		
		powerCurveDescription.setStudyDesign(studyDesign);
		
		PowerCurveServerResource resource = new  PowerCurveServerResource();		
		
		try
		{
			resource.update(powerCurveDescription);			
		}		
		catch(Exception e)
		{
			System.out.println("Error : "+e.getMessage()+"\n"+e.getStackTrace());
			powerCurveDescription=null;
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
		PowerCurveDescription powerCurveDescription = null;
		/*boolean flag;
		StudyDesign studyDesign = new StudyDesign();
		studyDesign.setUuid(uuid);
		
		PowerCurveDescription powerCurveDescription = new PowerCurveDescription();
		powerCurveDescription.setPowerCurveDescription("test1 description");
		powerCurveDescription.setSampleSize(100);
		powerCurveDescription.setRegressionCoeeficientScaleFactor(0.2f);
		powerCurveDescription.setTypeIError(0.8f);		
		powerCurveDescription.setStudyDesign(studyDesign);*/
		
		PowerCurveServerResource resource = new  PowerCurveServerResource();		
		
		try
		{
			powerCurveDescription = resource.retrieve(uuid);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			powerCurveDescription=null;
			fail();
		}
		if (powerCurveDescription == null)
        {
        	System.err.println("No matching confidence interval found");
        	fail();
        }
        else
        {              	                    
        	//System.out.println(powerCurveDescription.getPowerCurveDescription());
        	System.out.println(powerCurveDescription.getHorizontalAxisLabel());
            assertTrue(powerCurveDescription!=null);
        }
	}
	
	@Test
	private void testDelete()
	{		
		PowerCurveDescription powerCurveDescription = null;
		/*boolean flag;
		StudyDesign studyDesign = new StudyDesign();
		studyDesign.setUuid(uuid);
		
		PowerCurveDescription powerCurveDescription = new PowerCurveDescription();
		powerCurveDescription.setPowerCurveDescription("test1 description");
		powerCurveDescription.setSampleSize(100);
		powerCurveDescription.setRegressionCoeeficientScaleFactor(0.2f);
		powerCurveDescription.setTypeIError(0.8f);		
		powerCurveDescription.setStudyDesign(studyDesign);*/
		
		PowerCurveServerResource resource = new  PowerCurveServerResource();		
		
		try
		{
			powerCurveDescription = resource.remove(uuid);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			powerCurveDescription=null;
			fail();
		}
		if (powerCurveDescription == null)
        {
        	System.err.println("No matching confidence interval found");
        	fail();
        }
        else
        {              	                    
        	System.out.println(powerCurveDescription.getPowerCurveDescription());
            assertTrue(powerCurveDescription!=null);
        }
	}
	

}
