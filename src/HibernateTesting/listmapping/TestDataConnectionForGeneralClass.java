package HibernateTesting.listmapping;

import java.util.List;

import junit.framework.TestCase;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.junit.After;
import org.junit.Test;

public class TestDataConnectionForGeneralClass extends TestCase 
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
			List<String> allTables = session.createSQLQuery("SHOW DATABASES").list();	
			System.out.println(allTables.toString());
			
			Student st = new Student();
			st.setSchoolBench(1);				
			session.save(st);	
			transaction.commit();
			
			transaction = session.beginTransaction();
			//st = (Student) session.load(Student.class, new Integer(1));
			Query q = session.createQuery("FROM HibernateTesting.listmapping.Student WHERE schoolBench =:schoolbench");
			q.setInteger("schoolbench", st.getSchoolBench());
			//q.setInteger("schoolbench", 1);
			st = (Student) q.uniqueResult();
			/*List ls = session.createCriteria(Student.class).add(Restrictions.eq("schoolbench", 1)).list();
			Student st = (Student)ls.get(0);*/
			NameList nm = new NameList();
			nm.setListId(st.getId());
			nm.setName("a");
			NameList nm1 = new NameList();
			nm1.setListId(st.getId());
			nm1.setName("b");			
			session.save(nm);			
			session.save(nm1);				
			transaction.commit();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			transaction.rollback();
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
