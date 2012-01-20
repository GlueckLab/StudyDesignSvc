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
package edu.cudenver.bios.studydesignsvc.manager;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import edu.cudenver.bios.studydesignsvc.application.StudyDesignContext;
import edu.cudenver.bios.studydesignsvc.application.StudyDesignLogger;
/**
 * 
 * @author Uttara Sakhadeo
 *
 */
public class BaseManager 
{
	private static Logger logger = StudyDesignLogger.getInstance();	
	protected Session session = null;
    protected boolean transactionStarted = false;
    
    public BaseManager() throws ResourceException
    {
        try
        {        	
            session = StudyDesignContext.getInstance().getCurrentSession();        	
        }
        catch (Exception e)
        {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,"Failed to get database session: " + e.getMessage());
        }
    }
    
    public void beginTransaction() throws ResourceException
    {
        try
        {
            if (session != null)
            {
                session.beginTransaction();
                transactionStarted = true;
            }
        }
        catch (Exception e)
        {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,"Failed to get database session: " + e.getMessage());
        }
    }
    
    public void commit() throws ResourceException
    {
        try
        {
            if (session != null) session.getTransaction().commit();
            transactionStarted = false;
        }
        catch (Exception e)
        {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,"Failed to commit transaction: " + e.getMessage());
        }
    }
    
    public void rollback() throws ResourceException
    {
        try
        {
            if (session != null) session.getTransaction().rollback();
            transactionStarted = false;
        }
        catch (Exception e)
        {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST,"Failed to rollback transaction: " + e.getMessage());
        }
    }
}
