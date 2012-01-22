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
package edu.cudenver.bios.studydesignsvc.domain;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Main Study Design object which holds
 * all lists, matrices etc.
 * 
 * @author Uttara Sakhadeo
 */
@Entity
public class StudyDesign
{

	public enum SolvingFor
	{
		POWER,
		SAMPLE_SIZE
	};
	
	// UUID for the study design.  Main unique identifier for the design
	private byte[] uuid = null;
	private UUID studyUUID = null;
	// name of the study design
	private String name = null;	
	// flag indicating whether we are solving for power or sample size
	private SolvingFor flagSolveFor = null;
	// flag indicating if the design includes a baseline covariate
	private boolean hasGaussianCovariate = false;
	
	/**
	 * Create an empty study design without a UUID assigned
	 */
	public StudyDesign() 
	{}
	
	public static byte[] asByteArray(UUID uuid) 
	 {
	    long msb = uuid.getMostSignificantBits();
	    long lsb = uuid.getLeastSignificantBits();
	    byte[] buffer = new byte[16];

	    for (int i = 0; i < 8; i++) {
	            buffer[i] = (byte) (msb >>> 8 * (7 - i));
	    }
	    for (int i = 8; i < 16; i++) {
	            buffer[i] = (byte) (lsb >>> 8 * (7 - i));
	    }

	    return buffer;

	}

	
	/**
	 * Create a study design object with the specified UUID
	 * @param studyUUID unique identifier for the study design
	 */
	public StudyDesign(UUID studyUUID) 
	{
		this.studyUUID = studyUUID;
		this.uuid = asByteArray(studyUUID);
	}
	/*--------------------
	 * Getter/Setter Methods
	 *--------------------*/
	public UUID getStudyUUID() 
	{
		return studyUUID;
	}
	
	@Id
	public byte[] getUuid() 
	{
		return uuid;
	}
	
	   public void setUuid(byte [] uuid) 
	    {
	        this.uuid = uuid;
	    }
	
	public void setStudyUUID(UUID studyUuid) 
	{
		this.studyUUID = studyUuid;
		this.uuid = asByteArray(studyUUID);
	}
	
	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public SolvingFor getSolvingFor() 
	{
		return flagSolveFor;
	}
	
	public void setSolvingFor(SolvingFor flagSolveFor) 
	{
		this.flagSolveFor = flagSolveFor;
	}
	
	public boolean hasGaussianCovariate() 
	{
		return hasGaussianCovariate;
	}
	
	public void setIsGuassianSelection(boolean hasGaussianCovariate) 
	{
		this.hasGaussianCovariate = hasGaussianCovariate;
	}	
}
