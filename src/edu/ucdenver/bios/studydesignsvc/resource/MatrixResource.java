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


/**
 * Generic Resource class for handling requests for the 
 * domain list object of a . 
 * See the StudyDesignApplication class for URI mappings
 * 
 * @author Uttara Sakhadeo
 */
public interface MatrixResource 
{
	/**
	 * Retrieve the object for the specified UUID.
	 * Returns "not found" if no matching Object is available.
	 * @return Object for specified UUID
	 */
	 /*@Get
     public Map<String,NamedMatrix> retrieve(byte[] uuid);*/
	 
	 /**
	 * Retrieve the object for the specified UUID.
	 * Returns "not found" if no matching Object is available.
	 * @return Object for specified UUID
	 */
	/* @Get
    public NamedMatrix retrieve(byte[] uuid,String name);*/
    
    /**
	 * Store Matrix object to the database.
	 * 
	 * @param Object
	 * @return updated Object.
	 */	 
    /*@Post
    public Map<String,NamedMatrix> create(Map<String,NamedMatrix> namedMatrixMap);*/
    
    /**
   	 * Store Matrix object to the database.
   	 * 
   	 * @param Object
   	 * @return updated Object.
   	 */	 
      /* @Post
       public NamedMatrix create(NamedMatrix namedMatrix);*/
           
    /**
     * Update the specified object. If there is no
     * object set for specified UUID, then this object  
     * will be treated as new and a UUID assigned.
     * 
     * @param Object
     * @return Object
     */    
    /*@Put
    public Map<String,NamedMatrix> update(Map<String,NamedMatrix> namedMatrixMap);*/
    
    /**
     * Update the specified object. If there is no
     * object set for specified UUID, then this object  
     * will be treated as new and a UUID assigned.
     * 
     * @param Object
     * @return Object
     */    
    /*@Put
    public NamedMatrix update(NamedMatrix namedMatrix);*/
    
    /** 
     * Delete the Beta Scale object with the specified UUID
     * 
     * @param uuid of the object to remove
     * @return the deleted object
     */
    /*@Delete
    public List<NamedMatrix> remove(byte[] uuid);*/
    
    /** 
     * Delete the Beta Scale object with the specified UUID
     * 
     * @param uuid of the object to remove
     * @return the deleted object
     */
    /*@Delete
    public NamedMatrix remove(byte[] uuid,String name);*/
}
