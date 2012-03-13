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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	/*MatrixManager matrixManager = null; 
	StudyDesignManager studyDesignManager = null;
	boolean uuidFlag;
	
	@Get("json")
	public NamedMatrix retrieve(byte[] uuid, String name) 
	{
		NamedMatrix namedMatrix = null;
		Map<String,NamedMatrix> namedMatrixMap = null;
		try
		{
			 ----------------------------------------------------
			 * Check for existence of a UUID in Study Design object 
			 * ----------------------------------------------------
			studyDesignManager = new StudyDesignManager();			
			studyDesignManager.beginTransaction();								
				if(uuid==null)
					throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
							"no study design UUID specified");
				uuidFlag = studyDesignManager.hasUUID(uuid);
				if(uuidFlag)
            	{		
					StudyDesign studyDesign = studyDesignManager.get(uuid);
					namedMatrixMap = studyDesign.getMatrixMap();
					if(namedMatrixMap.isEmpty())
						throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
								"no TypeIError is specified");									
					namedMatrix = namedMatrixMap.get(name);
					//System.out.println(namedMatrix.getMatrixCellList().size());
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
			namedMatrixMap = null;
		}								
		return namedMatrix;
	}
	
	@Get("json")
	public Map<String, NamedMatrix> retrieve(byte[] uuid) 
	{		
		Map<String,NamedMatrix> namedMatrixMap = null;
		try
		{
			 ----------------------------------------------------
			 * Check for existence of a UUID in Study Design object 
			 * ----------------------------------------------------
			studyDesignManager = new StudyDesignManager();			
			studyDesignManager.beginTransaction();								
				if(uuid==null)
					throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
							"no study design UUID specified");
				uuidFlag = studyDesignManager.hasUUID(uuid);
				if(uuidFlag)
            	{		
					StudyDesign studyDesign = studyDesignManager.get(uuid);
					namedMatrixMap = studyDesign.getMatrixMap();																
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
				{namedMatrixMap = null;}				
			}
			namedMatrixMap = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {namedMatrixMap = null;}					
			}
			namedMatrixMap = null;
		}								
		return namedMatrixMap;
	}

	@Post("json")
	public Map<String, NamedMatrix> create(Map<String,NamedMatrix> namedMatrixMap) 
	{		
		StudyDesign studyDesign = null;
		//Map<String,NamedMatrix> namedMatrixMap = studyDesign.getMatrixMap();
		try
		{
			Iterator itr = namedMatrixMap.entrySet().iterator();
			byte uuid[] = null;
			while(itr.hasNext())
			{
				Map.Entry<String, NamedMatrix> entry = (Map.Entry<String,NamedMatrix>)itr.next();
				uuid=entry.getValue().getStudyDesign().getUuid();
				break;
			}
			if(uuid==null)
				throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
						"no study design UUID specified");
			 ----------------------------------------------------
			 * Check for existence of a UUID in Study Design object 
			 * ----------------------------------------------------
			studyDesignManager = new StudyDesignManager();
			studyDesignManager.beginTransaction();				
			Iterator itr = namedMatrixMap.entrySet().iterator();
			while(itr.hasNext())
			{
				System.out.println("NamedMatrixMap Size() : "+namedMatrixMap.size());
				entry = (Map.Entry<String,NamedMatrix>)itr.next();
				uuid = entry.getValue().getStudyDesign().getUuid();
				break;
			}			
				uuidFlag = studyDesignManager.hasUUID(uuid);				
				if(uuidFlag)
            	{studyDesign = studyDesignManager.get(uuid);}																									            				
				else
				{throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
						"no study design UUID specified");}
			studyDesignManager.commit();
			 ----------------------------------------------------
			 * Remove existing Beta Scale for this object 
			 * ----------------------------------------------------
			Map<String,NamedMatrix> map = studyDesign.getMatrixMap();
			if(!map.isEmpty())
			{				
				itr = map.entrySet().iterator();
				while(itr.hasNext())
				{
					Map.Entry<String, NamedMatrix> entry = (Map.Entry<String,NamedMatrix>)itr.next();
					//List<NamedMatrixCell> namedMatrixCell= entry.getValue().getMatrixCellList();
					remove(uuid,entry.getKey());
				}
			}				
			 ----------------------------------------------------
			 * Set reference of Study Design Object to each Beta Scale element 
			 * ----------------------------------------------------
			
			for(NamedMatrix NamedMatrix : namedMatrixMap)					
				NamedMatrix.setStudyDesign(studyDesign);
			studyDesign.setBetaScaleList(namedMatrixMap);

			while(itr.hasNext())
			{
				entry = (Map.Entry<String,NamedMatrix>)itr.next();
				entry.getValue().setStudyDesign(studyDesign);
			}
			itr = namedMatrixMap.entrySet().iterator();
			while(itr.hasNext())
			{
				Map.Entry<String, NamedMatrix> entry = (Map.Entry<String,NamedMatrix>)itr.next();
				entry.getValue().setStudyDesign(studyDesign);
			}
			studyDesign.setMatrixMap(namedMatrixMap);
			
			
			 ----------------------------------------------------
			 * Save new Beta Scale List object 
			 * ----------------------------------------------------
			studyDesignManager = new StudyDesignManager();
			studyDesignManager.beginTransaction();
				studyDesignManager.saveOrUpdate(studyDesign, false);
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
				{namedMatrixMap = null;}				
			}
			namedMatrixMap = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {namedMatrixMap = null;}					
			}
			namedMatrixMap = null;
		}								
		return namedMatrixMap;
	}
	
	@Post("json")
	public NamedMatrix create(NamedMatrix namedMatrix) 
	{		
		StudyDesign studyDesign = null;
		byte uuid[] = null;
		//Map<String,NamedMatrix> namedMatrixMap = studyDesign.getMatrixMap();
		try
		{			
			uuid = namedMatrix.getStudyDesign().getUuid();			
			if(uuid==null)
				throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
						"no study design UUID specified");
			 ----------------------------------------------------
			 * Check for existence of a UUID in Study Design object 
			 * ----------------------------------------------------
			studyDesignManager = new StudyDesignManager();
			studyDesignManager.beginTransaction();								
				uuidFlag = studyDesignManager.hasUUID(uuid);				
				if(uuidFlag)
            	{studyDesign = studyDesignManager.get(uuid);}																									            				
				else
				{throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
						"no study design UUID specified");}
			studyDesignManager.commit();
			 ----------------------------------------------------
			 * Remove existing Matrix for this object 
			 * ----------------------------------------------------			
			Map<String,NamedMatrix> map = studyDesign.getMatrixMap();
			if(!map.isEmpty())
			{			
				
				Iterator itr = map.entrySet().iterator();
				while(itr.hasNext())
				{
					Map.Entry<String, NamedMatrix> entry = (Map.Entry<String,NamedMatrix>)itr.next();
					//List<NamedMatrixCell> namedMatrixCell= entry.getValue().getMatrixCellList();
					if(entry.getKey().equals(namedMatrix.getName()))
					{ remove(uuid,entry.getKey());}									
				}				
			}				
			else
			{
				 ----------------------------------------------------
				 * Set reference of Study Design Object to Matrix element 
				 * ----------------------------------------------------					
				map = new HashMap<String, NamedMatrix>();				
			}		
			namedMatrix.setStudyDesign(studyDesign);
			map.put(namedMatrix.getName(), namedMatrix);
			studyDesign.setMatrixMap(map);
						
			 ----------------------------------------------------
			 * Save new Beta Scale List object 
			 * ----------------------------------------------------
			studyDesignManager = new StudyDesignManager();
			studyDesignManager.beginTransaction();
				studyDesignManager.saveOrUpdate(studyDesign, false);
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
	
	@Delete("json")
	public NamedMatrix remove(byte[] uuid,String name) 
	{		
		NamedMatrix namedMatrix = null;
		Map<String,NamedMatrix> namedMatrixMap = null;
		StudyDesign studyDesign = null;
		try
		{
			----------------------------------------------------
			 * Check for existence of a UUID in Study Design object 
			 * ----------------------------------------------------
			studyDesignManager = new StudyDesignManager();			
			studyDesignManager.beginTransaction();								
				if(uuid==null)
					throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
							"no study design UUID specified");
				uuidFlag = studyDesignManager.hasUUID(uuid);
				if(uuidFlag)
            	{		
					studyDesign = studyDesignManager.get(uuid);
					namedMatrixMap = studyDesign.getMatrixMap();					
					if(namedMatrixMap.isEmpty())
						throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
								"no TypeIError is specified");						
            	}				
			studyDesignManager.commit();
			----------------------------------------------------
			 * Remove existing specified Matrix objects of this object 
			 * ----------------------------------------------------
			if(!namedMatrixMap.isEmpty() && namedMatrixMap.get(name)!=null)
			{
				matrixManager = new MatrixManager();
				matrixManager.beginTransaction();
					namedMatrix = matrixManager.delete(uuid,name);
				matrixManager.commit();
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

	@Delete("json")
	public List<NamedMatrix> remove(byte[] uuid) 
	{
		Map<String,NamedMatrix> namedMatrixMap = null;
		List<NamedMatrix> namedMatrixList = null;
		StudyDesign studyDesign = null;
		try
		{
			----------------------------------------------------
			 * Check for existence of a UUID in Study Design object 
			 * ----------------------------------------------------
			studyDesignManager = new StudyDesignManager();			
			studyDesignManager.beginTransaction();								
				if(uuid==null)
					throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
							"no study design UUID specified");
				uuidFlag = studyDesignManager.hasUUID(uuid);
				if(uuidFlag)
            	{		
					studyDesign = studyDesignManager.get(uuid);
					namedMatrixMap = studyDesign.getMatrixMap();					
					if(namedMatrixMap.isEmpty())
						throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, 
								"no TypeIError is specified");						
            	}				
			studyDesignManager.commit();
			----------------------------------------------------
			 * Remove existing specified Matrix objects of this object 
			 * ----------------------------------------------------
			if(!namedMatrixMap.isEmpty())
			{
				matrixManager = new MatrixManager();
				matrixManager.beginTransaction();
					namedMatrixList = matrixManager.delete(uuid);
				matrixManager.commit();
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
				{namedMatrixList = null;}				
			}
			namedMatrixList = null;
		}	
		catch(StudyDesignException sde)
		{
			System.out.println(sde.getMessage());
			StudyDesignLogger.getInstance().error(sde.getMessage());
			if(studyDesignManager!=null)
			{
				try {studyDesignManager.rollback();}
				catch(BaseManagerException re) {namedMatrixList = null;}					
			}
			namedMatrixList = null;
		}		
		return namedMatrixList;
	}

	@Put("json")
	public Map<String, NamedMatrix> update(Map<String, NamedMatrix> namedMatrixMap) 
	{return create(namedMatrixMap);}

	@Put("json")
	public NamedMatrix update(NamedMatrix namedMatrix) 
	{return create(namedMatrix);}*/

}
