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
package HibernateTesting.sampletest;

import java.util.ArrayList;
import java.util.UUID;

import junit.framework.TestCase;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.junit.After;
import org.junit.Test;

import edu.cudenver.bios.studydesignsvc.domain.BetweenSubjectEffect;

/**
 * Unit test for Database Connection.
 * 
 * @author Uttara Sakhadeo
 */
public class TestDatabaseConnection extends TestCase
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
			BetweenSubjectEffect bts = new BetweenSubjectEffect();
			bts.setCategoryList(new ArrayList<String>());
			bts.setPredictorName("abc");
			bts.setStudyUUID(UUID.randomUUID());
			transaction = session.beginTransaction();
			session.save(bts);
			transaction.commit();
			assertNotNull(bts);
			//assertEquals(1, heroes.size());
		}
		catch(Exception e)
		{
			transaction.rollback();
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
