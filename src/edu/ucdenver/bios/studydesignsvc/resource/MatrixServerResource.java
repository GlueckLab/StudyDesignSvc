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
package edu.ucdenver.bios.studydesignsvc.resource;

import java.util.Set;

import org.codehaus.jackson.map.util.Named;
import org.restlet.data.Status;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import edu.ucdenver.bios.studydesignsvc.application.StudyDesignLogger;
import edu.ucdenver.bios.studydesignsvc.exceptions.StudyDesignException;
import edu.ucdenver.bios.studydesignsvc.manager.MatrixManager;
import edu.ucdenver.bios.studydesignsvc.manager.StudyDesignManager;
import edu.ucdenver.bios.webservice.common.domain.NamedMatrix;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

/**
 * Server Resource class for handling requests for the NamedMatrix object. 
 * See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public class MatrixServerResource  extends ServerResource
implements MatrixResource 
{
	MatrixManager matrixManager = null; 
	StudyDesignManager studyDesignManager = null;
	boolean uuidFlag;
	
	/**
     * Retrieve a NamedMatrix object for specified UUID.
     * 
     * @param byte[]
     * @return NamedMatrix
     */
	@Get
	public NamedMatrix retrieve(byte[] uuid, String name) 
	{
		NamedMatrix matrix = null;		
		if(uuid==null)
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
					"no study design UUID specified");					 
		try
		{
			/*----------------------------------------------------
			 * Check for existence of a UUID in Study Design object 
			 * ----------------------------------------------------*/
			studyDesignManager = new StudyDesignManager();			
			studyDesignManager.beginTransaction();								
				uuidFlag = studyDesignManager.hasUUID(uuid);
				if(uuidFlag)
            	{		
					StudyDesign studyDesign = studyDesignManager.get(uuid);
					matrix = studyDesign.getNamedMatrix(name);									
            	}				
			studyDesignManager.commit();					
		}
		catch (BaseManagerException bme)
		{
			System.out.println(bme.getMessage());
			StudyDesignLogger.getInstance().error(bme.getMessage());
			if(matrixManager!=null)
			{
				try
				{matrixManager.rollback();}				
				catch(BaseManagerException re)
				{matrix = null;}				
			}
			matrix = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {matrix = null;}					
			}
			matrix = null;
		}								
		return matrix;
	}
	
	/**
     * Retrieve a Set<NamedMatrix> object for specified UUID.
     * 
     * @param byte[]
     * @return Set<NamedMatrix>
     */
	@Get
	public Set<NamedMatrix> retrieve(byte[] uuid) 
	{		
		Set<NamedMatrix> matrixSet = null;
		if(uuid==null)
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
					"no study design UUID specified");		
		try
		{
			 /*----------------------------------------------------
			 * Check for existence of a UUID in Study Design object 
			 * ----------------------------------------------------*/
			studyDesignManager = new StudyDesignManager();			
			studyDesignManager.beginTransaction();								
				uuidFlag = studyDesignManager.hasUUID(uuid);
				if(uuidFlag)
            	{		
					StudyDesign studyDesign = studyDesignManager.get(uuid);
					matrixSet = studyDesign.getMatrixSet();																
            	}				
			studyDesignManager.commit();					
		}
		catch (BaseManagerException bme)
		{
			System.out.println(bme.getMessage());
			StudyDesignLogger.getInstance().error(bme.getMessage());
			if(matrixManager!=null)
			{
				try
				{matrixManager.rollback();}				
				catch(BaseManagerException re)
				{matrixSet = null;}				
			}
			matrixSet = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {matrixSet = null;}					
			}
			matrixSet = null;
		}								
		return matrixSet;
	}

	/**
     * Create a Set<NamedMatrix> object for specified UUID.
     * 
     * @param byte[]
     * @return Set<NamedMatrix>
     */
	@Post
	public Set<NamedMatrix> create(byte[] uuid,Set<NamedMatrix> matrixSet) 
	{		
		StudyDesign studyDesign = null;
		if(uuid==null)
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
					"no study design UUID specified");
		try
		{					
			 /*----------------------------------------------------
			 * Check for existence of a UUID in Study Design object 
			 * ----------------------------------------------------*/
			studyDesignManager = new StudyDesignManager();
			studyDesignManager.beginTransaction();
				uuidFlag = studyDesignManager.hasUUID(uuid);
				if(uuidFlag)
	        	{		
					studyDesign = studyDesignManager.get(uuid);																			
	        	}		
				else
				{throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
						"no study design for specified UUID is stored");}				
			studyDesignManager.commit();
			 /*----------------------------------------------------
			 * Remove existing NamedMatrix for this object 
			 * ----------------------------------------------------*/			
			if(uuidFlag && studyDesign.getMatrixSet()!=null)
				removeFrom(studyDesign);
			if(uuidFlag)
			{
				matrixManager = new MatrixManager();
				matrixManager.beginTransaction();
					matrixManager.saveOrUpdate(matrixSet,true);
				matrixManager.commit();
				/*----------------------------------------------------
				 * Save new NamedMatrix List object 
				 * ----------------------------------------------------*/
				studyDesign.setMatrixSet(matrixSet);
				studyDesignManager = new StudyDesignManager();
				studyDesignManager.beginTransaction();
					studyDesignManager.saveOrUpdate(studyDesign, false);
				studyDesignManager.commit();
			}								 					
		}
		catch (BaseManagerException bme)
		{
			System.out.println(bme.getMessage());
			StudyDesignLogger.getInstance().error(bme.getMessage());
			if(matrixManager!=null)
			{
				try
				{matrixManager.rollback();}				
				catch(BaseManagerException re)
				{matrixSet = null;}				
			}
			matrixSet = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {matrixSet = null;}					
			}
			matrixSet = null;
		}								
		return matrixSet;
	}
	
	/**
     * Create a NamedMatrix object for specified UUID.
     * 
     * @param byte[]
     * @return NamedMatrix
     */
	@Post
	public NamedMatrix create(byte[] uuid,NamedMatrix namedMatrix) 
	{		
		StudyDesign studyDesign = null;
		if(uuid==null)
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
					"no study design UUID specified");
		String name = namedMatrix.getName();
		try
		{			
			 /*----------------------------------------------------
			 * Check for existence of a UUID in Study Design object 
			 * ----------------------------------------------------*/
			studyDesignManager = new StudyDesignManager();
			studyDesignManager.beginTransaction();
				uuidFlag = studyDesignManager.hasUUID(uuid);
				if(uuidFlag)
	        	{		
					studyDesign = studyDesignManager.get(uuid);																			
	        	}		
				else
				{throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
						"no study design for specified UUID is stored");}				
			studyDesignManager.commit();
			 /*----------------------------------------------------
			 * Remove existing Matrix for this object 
			 * ----------------------------------------------------*/			
			/*Set<NamedMatrix> map = studyDesign.getMatrixSet();
			if(map != null )
			{			
				
				Iterator itr = map.entrySet().iterator();
				while(itr.hasNext())
				{
					Map.Entry<String, NamedMatrix> entry = (Map.Entry<String,NamedMatrix>)itr.next();
					//List<NamedMatrixCell> namedMatrixCell= entry.getValue().getMatrixCellList();
					if(entry.getKey().equals(namedMatrix.getName()))
					{ remove(uuid,entry.getKey());}									
				}				
			}	*/			
			/*else
			{*/
				 /*----------------------------------------------------
				 * Set reference of Study Design Object to Matrix element 
				 * ----------------------------------------------------*/					
			/*	map = new HashSet<NamedMatrix>();				
			}		
			map.put(namedMatrix.getName(), namedMatrix);*/
			boolean flag = studyDesign.hasNamedMatrix(name);
			if(uuidFlag && studyDesign.getMatrixSet()!=null && flag)
			{
				/*----------------------------------------------------
				 * Remove existing Matrix for this object 
				 * ----------------------------------------------------*/					
				removeFrom(studyDesign,name);	
			}
			if(uuidFlag && studyDesign.getMatrixSet()!=null) 
			{
				matrixManager = new MatrixManager();
				matrixManager.beginTransaction();
					matrixManager.saveOrUpdate(namedMatrix,true);
				matrixManager.commit();
				 /*----------------------------------------------------
				 * Save new NamedMatrix List object 
				 * ----------------------------------------------------*/
				studyDesign.setNamedMatrix(namedMatrix);
				studyDesignManager = new StudyDesignManager();
				studyDesignManager.beginTransaction();
					studyDesignManager.saveOrUpdate(studyDesign, false);
				studyDesignManager.commit();	
			}
								
		}
		catch (BaseManagerException bme)
		{
			System.out.println(bme.getMessage());
			StudyDesignLogger.getInstance().error(bme.getMessage());
			if(matrixManager!=null)
			{
				try
				{matrixManager.rollback();}				
				catch(BaseManagerException re)
				{namedMatrix = null;}				
			}
			namedMatrix = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {namedMatrix = null;}					
			}
			namedMatrix = null;
		}								
		return namedMatrix;
	}
	
	/**
     * Update a NamedMatrix object for specified UUID.
     * 
     * @param byte[]
     * @return Set<NamedMatrix>
     */
	@Put("json")
	public Set<NamedMatrix> update(byte[] uuid,Set<NamedMatrix> matrixSet) 
	{				
		return create(uuid,matrixSet);			
	}	
	
	/**
     * Update a NamedMatrix object for specified UUID.
     * 
     * @param byte[]
     * @return Set<NamedMatrix>
     */
	@Put("json")
	public NamedMatrix update(byte[] uuid,NamedMatrix namedMatrix) 
	{				
		return create(uuid,namedMatrix);			
	}

	/**
     * Delete a NamedMatrix object for specified UUID.
     * 
     * @param byte[]
     * @return Set<NamedMatrix>
     */
	@Delete("json")
	public Set<NamedMatrix> remove(byte[] uuid) 
	{
		Set<NamedMatrix> matrixSet = null;
		StudyDesign studyDesign = null;
		if(uuid==null)
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
					"no study design UUID specified");
		try
		{			
			/* ----------------------------------------------------
			 * Check for existence of a UUID in Study Design object 
			 * ----------------------------------------------------*/
			studyDesignManager = new StudyDesignManager();			
			studyDesignManager.beginTransaction();												
				uuidFlag = studyDesignManager.hasUUID(uuid);
				if(uuidFlag)
            	{		
					studyDesign = studyDesignManager.get(uuid);
					if(studyDesign!=null)
						matrixSet = studyDesign.getMatrixSet();								
            	}				
			studyDesignManager.commit();
			/* ----------------------------------------------------
			 * Remove existing NamedMatrix objects for this object 
			 * ----------------------------------------------------*/
			if(matrixSet!=null)
			{
				matrixManager = new MatrixManager();
				matrixManager.beginTransaction();
					matrixSet = matrixManager.delete(uuid,matrixSet);
				matrixManager.commit();
				/* ----------------------------------------------------
    			 * Set reference of NamedMatrix Object to Study Design object 
    			 * ----------------------------------------------------*/
            	/*studyDesign.setBetaScaleList(null);            	
            	studyDesignManager = new StudyDesignManager();
            	studyDesignManager.beginTransaction();            		
            		studyDesign = studyDesignManager.saveOrUpdate(studyDesign, false);				
				studyDesignManager.commit();
				matrixSet=studyDesign.getMatrixSet();*/
			}
		}
		catch (BaseManagerException bme)
		{
			System.out.println(bme.getMessage());
			StudyDesignLogger.getInstance().error(bme.getMessage());
			if(matrixManager!=null)
			{
				try
				{matrixManager.rollback();}				
				catch(BaseManagerException re)
				{matrixSet = null;}				
			}
			matrixSet = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {matrixSet = null;}					
			}
			matrixSet = null;
		}		
		return matrixSet;
	}
	
	/**
     * Delete a NamedMatrix object for specified UUID.
     * 
     * @param byte[]
     * @return Set<NamedMatrix>
     */
	@Delete("json")
	public NamedMatrix remove(byte[] uuid,String name) 
	{
		Set<NamedMatrix> matrixSet = null;
		NamedMatrix matrix = null;
		StudyDesign studyDesign = null;
		if(uuid==null)
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
					"no study design UUID specified");
		try
		{			
			/* ----------------------------------------------------
			 * Check for existence of a UUID in Study Design object 
			 * ----------------------------------------------------*/
			studyDesignManager = new StudyDesignManager();			
			studyDesignManager.beginTransaction();												
				uuidFlag = studyDesignManager.hasUUID(uuid);
				if(uuidFlag)
            	{		
					studyDesign = studyDesignManager.get(uuid);
					if(studyDesign!=null)
						matrixSet = studyDesign.getMatrixSet();								
            	}				
			studyDesignManager.commit();
			/* ----------------------------------------------------
			 * Remove existing NamedMatrix objects for this object 
			 * ----------------------------------------------------*/
			if(matrixSet!=null && studyDesign.hasNamedMatrix(name))
			{
				matrixManager = new MatrixManager();
				matrixManager.beginTransaction();
					matrix = matrixManager.delete(uuid, studyDesign.getNamedMatrix(name));
				matrixManager.commit();
				/* ----------------------------------------------------
    			 * Set reference of NamedMatrix Object to Study Design object 
    			 * ----------------------------------------------------*/
            	/*studyDesign.setBetaScaleList(null);            	
            	studyDesignManager = new StudyDesignManager();
            	studyDesignManager.beginTransaction();            		
            		studyDesign = studyDesignManager.saveOrUpdate(studyDesign, false);				
				studyDesignManager.commit();
				matrixSet=studyDesign.getMatrixSet();*/
			}
		}
		catch (BaseManagerException bme)
		{
			System.out.println(bme.getMessage());
			StudyDesignLogger.getInstance().error(bme.getMessage());
			if(matrixManager!=null)
			{
				try
				{matrixManager.rollback();}				
				catch(BaseManagerException re)
				{matrix = null;}				
			}
			matrix = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {matrix = null;}					
			}
			matrix = null;
		}		
		return matrix;
	}	
	
	/**
     * Delete a NamedMatrix object for specified Study Design.
     * 
     * @param StudyDesign
     * @return Set<NamedMatrix>
     */
	@Delete
	public Set<NamedMatrix> removeFrom(StudyDesign studyDesign) 
	{
		boolean flag;	
		Set<NamedMatrix> matrixSet = null;	
        try
        {                    			
        	matrixManager = new MatrixManager();
        	matrixManager.beginTransaction();
        		matrixSet=matrixManager.delete(studyDesign.getUuid(),studyDesign.getMatrixSet());
        	matrixManager.commit();        	      
        }
        catch (BaseManagerException bme)
        {
        	System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error("Failed to load Study Design information: " + bme.getMessage());
            if (studyDesignManager != null) try { studyDesignManager.rollback(); } catch (BaseManagerException e) {}
            if (matrixManager != null) try { matrixManager.rollback(); } catch (BaseManagerException e) {}
            matrixSet = null;           
        }       
        return matrixSet;
	}

	/**
     * Delete a NamedMatrix object for specified Study Design.
     * 
     * @param StudyDesign
     * @return Set<NamedMatrix>
     */
	@Delete
	public NamedMatrix removeFrom(StudyDesign studyDesign,String name) 
	{
		boolean flag;	
		NamedMatrix matrix = null;	
        try
        {                    			
        	matrixManager = new MatrixManager();
        	matrixManager.beginTransaction();
        		matrix=matrixManager.delete(studyDesign.getUuid(),studyDesign.getNamedMatrix(name));
        	matrixManager.commit();        	      
        }
        catch (BaseManagerException bme)
        {
        	System.out.println(bme.getMessage());
            StudyDesignLogger.getInstance().error("Failed to load Study Design information: " + bme.getMessage());
            if (studyDesignManager != null) try { studyDesignManager.rollback(); } catch (BaseManagerException e) {}
            if (matrixManager != null) try { matrixManager.rollback(); } catch (BaseManagerException e) {}
            matrix = null;           
        }       
        return matrix;
	}
}
