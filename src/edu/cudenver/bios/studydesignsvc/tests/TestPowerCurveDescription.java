package edu.cudenver.bios.studydesignsvc.tests;

import java.util.UUID;

import org.junit.Test;

import edu.cudenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.cudenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.cudenver.bios.studydesignsvc.manager.ConfidenceIntervalManager;
import edu.cudenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.cudenver.bios.studydesignsvc.resource.PowerCurveServerResource;
import edu.ucdenver.bios.webservice.common.domain.ConfidenceIntervalDescription;
import edu.ucdenver.bios.webservice.common.domain.PowerCurveDescription;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
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
	private void testCreate()
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
	public void testUpdate()
	{	
		
		boolean flag;
		StudyDesign studyDesign = new StudyDesign();
		studyDesign.setUuid(uuid);
		
		PowerCurveDescription powerCurveDescription = new PowerCurveDescription();
		powerCurveDescription.setPowerCurveDescription("test description");
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
	public void testRetrieve()
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
        	System.out.println(powerCurveDescription.getPowerCurveDescription());
            assertTrue(powerCurveDescription!=null);
        }
	}
	
	@Test
	private void testDelete()
	{		
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
