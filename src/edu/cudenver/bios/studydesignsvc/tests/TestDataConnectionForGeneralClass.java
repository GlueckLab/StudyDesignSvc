package edu.cudenver.bios.studydesignsvc.tests;

import java.util.List;
import java.util.UUID;

import junit.framework.TestCase;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.junit.After;
import org.junit.Test;

import edu.ucdenver.bios.webservice.common.domain.ConfidenceIntervalDescription;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.cudenver.bios.studydesignsvc.manager.ConfidenceIntervalManager;
import edu.cudenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

public class TestDataConnectionForGeneralClass extends TestCase 
{
	private SessionFactory sessionFactory ;
	//private Session session;
	StudyDesignManager studyDesignManager = null;
	ConfidenceIntervalManager confidenceIntervalManager = null;
		
	//public void before()
	public void setUp()
    {   				
		/*Configuration configuration =  new Configuration();
		configuration.setProperty(Environment.DRIVER,"com.mysql.jdbc.Driver");
		configuration.setProperty(Environment.USER, "root");
		configuration.setProperty(Environment.PASS, "root");
		//configuration.
		//configuration.setProperty("hibernate.hbm2ddl.auto", "drop-create");
		
		sessionFactory = configuration.configure().buildSessionFactory();
		session = sessionFactory.openSession();		*/
    }
	
	@Test
	public void test()
	{		
		StudyDesign studyDesign = null; 
		ConfidenceIntervalDescription confidenceInterval = null;
		try
		{
			
			studyDesignManager = new StudyDesignManager();
			studyDesignManager.beginTransaction();
								
				studyDesign=studyDesignManager.getStudyDesign("00000000-0000-002a-0000-00000000002a");				
			
			System.out.println(studyDesign.getName());
						
			studyDesignManager.commit();
			
			/*confidenceIntervalManager = new ConfidenceIntervalManager();
			confidenceIntervalManager.beginTransaction();
								
				confidenceInterval=confidenceIntervalManager.getConfidenceInterval("00000000-0000-002a-0000-00000000002a");				
			
			System.out.println(""+confidenceInterval.getLowerTailProbability());
						
			confidenceIntervalManager.commit();*/
			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			try
			{
				studyDesignManager.rollback();
				confidenceIntervalManager.rollback();
			}
			catch (BaseManagerException bme) 
			{
				System.out.println("rollback failed: " + bme.getMessage());
			}
			//transaction.rollback();
		}
		finally
		{
			
		}			
	}
	
	@After
	public void After()
	{
		
	}
}
