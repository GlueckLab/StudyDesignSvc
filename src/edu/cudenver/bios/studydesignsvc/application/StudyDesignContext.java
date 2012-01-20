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
package edu.cudenver.bios.studydesignsvc.application;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.restlet.resource.ResourceException;
/**
 * 
 * 
 * @author Uttara Sakhadeo
 */
public class StudyDesignContext 
{
	// singleton instance variable
    private static StudyDesignContext instance = null;

    private SessionFactory sessionFactory = null;
    
    private StudyDesignContext() throws ResourceException
    {       
    	/*
         * Build a SessionFactory object from session-factory configuration
         * defined in the hibernate.cfg.xml file. In this file we register
         * the JDBC connection information, connection pool, the hibernate
         * dialect that we used and the mapping to our hbm.xml file for each
    	 * POJO (Plain Old Java Object).
         *
    	 */
    	Configuration configuration =  new Configuration();
    	configuration.setProperty(Environment.DRIVER,"com.mysql.jdbc.Driver");
		configuration.setProperty(Environment.USER, "root");
		configuration.setProperty(Environment.PASS, "root");
    	sessionFactory = configuration.configure().buildSessionFactory();
    	
    }
    
    public SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }
    
    public Session getCurrentSession()
    {
    	return sessionFactory.getCurrentSession();    	
    }
    
    public Session openSession()
    {
        return sessionFactory.openSession();
    }

    /*
     * Get an instance of the upload config
     */
    synchronized public static StudyDesignContext getInstance() throws ResourceException
    {
        if (instance == null) 
        {
            instance = new StudyDesignContext();
        }
        return instance;
    }

}
