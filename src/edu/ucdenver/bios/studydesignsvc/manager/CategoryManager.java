package edu.ucdenver.bios.studydesignsvc.manager;

import java.util.List;

import org.hibernate.Query;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import edu.ucdenver.bios.webservice.common.domain.Category;
import edu.ucdenver.bios.webservice.common.domain.Category;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManager;
import edu.ucdenver.bios.webservice.common.hibernate.BaseManagerException;

public class CategoryManager extends BaseManager
{
	public CategoryManager() throws BaseManagerException 
	{
		super();
	}

	/**
     * Retrieve a Category object by the specified UUID.
     * 
     * @param studyUuid : byte[]
     * @return List<Category>
     */
	/*public List<Category> get(byte[] uuidBytes)
	{
		if(!transactionStarted) throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");
		List<Category> categoryList = null;
		try
		{									
			//byte[] uuidBytes = UUIDUtils.asByteArray(studyUUID);									
			Query query = session.createQuery("from edu.ucdenver.bios.webservice.common.domain.Category where uuid = :uuid");
            query.setBinary("uuid", uuidBytes);	                      
            categoryList = query.list();            
		}
		catch(Exception e)
		{
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to retrieve Category object for UUID '" + uuidBytes + "': " + e.getMessage());
		}
		return categoryList;
	}*/
	
	/**
     * Delete a Category object by the specified UUID.
     * 
     * @param studyUuid : byte[]
     * @return List<Category>
     */
	public List<Category> delete(byte[] uuidBytes,List<Category> categoryList)
	{
		if(!transactionStarted) 
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");
		try
		{			
			for(Category category : categoryList)
				session.delete(category);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to delete Category object for UUID '" + uuidBytes + "': " + e.getMessage());
		}
		return categoryList;
	}
	
	/**
     * Retrieve a Category object by the specified UUID.
     * 
     * @param categoryList : List<Category>
     * @param isCreation : boolean
     * @return categoryList : List<Category>
     */
	public List<Category> saveOrUpdate(List<Category> categoryList,boolean isCreation)
	{
		if(!transactionStarted) throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Transaction has not been started.");		
		try
		{			
			if(isCreation==true)
			{
				for(Category category : categoryList)				
				{
					session.save(category);
					System.out.println("in save id: "+category.getId());
				}				
			}
			else
			{
				for(Category category : categoryList)
					session.update(category);
			}
		}
		catch(Exception e)
		{
			categoryList=null;
			System.out.println(e.getMessage());
			throw new ResourceException(Status.CONNECTOR_ERROR_CONNECTION,"Failed to save Category object : " + e.getMessage());
		}
		return categoryList;
	}
}
