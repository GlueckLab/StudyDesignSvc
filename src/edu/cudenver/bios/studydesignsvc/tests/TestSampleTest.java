package edu.cudenver.bios.studydesignsvc.tests;

import java.util.List;

import junit.framework.TestCase;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.junit.After;
import org.junit.Test;

import HibernateTesting.sampletest.SampleTest;

public class TestSampleTest extends TestCase 
{
	private SessionFactory sessionFactory ;
	private Session session;
	private Transaction transaction;	
		
	//public void before()
	public void setUp()
    {   				
		Configuration configuration =  new Configuration();
		configuration.setProperty(Environment.DRIVER,"com.mysql.jdbc.Driver");
		configuration.setProperty(Environment.USER, "root");
		configuration.setProperty(Environment.PASS, "root");
		//configuration.
		//configuration.setProperty("hibernate.hbm2ddl.auto", "drop-create");
		
		sessionFactory = configuration.configure().buildSessionFactory();
		session = sessionFactory.openSession();			
    }
	
	@Test
	public void test()
	{
		try
		{
			transaction = session.beginTransaction();
			/*show datavases*/
			List<String> allTables = session.createSQLQuery("SHOW DATABASES").list();	
			System.out.println(allTables.toString());
			/*save data*/							
			SampleTest sts = new SampleTest(1,"Test1");			
			//sts.setTestId(new Integer(1));
			session.save(sts);
			transaction.commit();					
			//SampleTest st = (SampleTest)session.get(SampleTest.class, new Integer(1));
			//System.out.println(st.getName());			
			/*SampleTest st = new SampleTest();
			st.setName("Test2");			
			session.save(st);*/
			/*read data*/
			SampleTest st = (SampleTest) session.load(SampleTest.class, new Integer(1));
			System.out.println(st.getTestId()+" "+st.getName());
			/*update data*/			
			st.setName("updatedTest1");
			session.update(st);
			st = (SampleTest) session.load(SampleTest.class, new Integer(1));
			System.out.println(st.getTestId()+" "+st.getName());
			transaction.commit();
			/*delete data*/		
			transaction = session.beginTransaction();
			session.delete(st);
			transaction.commit();			
			//assertNotNull(st);
			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			//transaction.rollback();
		}
		finally
		{
			session.close();
			sessionFactory.close();
		}			
	}
	
	@After
	public void After()
	{
		
	}
}
