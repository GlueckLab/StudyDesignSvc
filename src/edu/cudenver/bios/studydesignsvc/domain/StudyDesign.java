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

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import edu.cudenver.bios.studydesignsvc.application.UUIDConversion;

/**
 * Main Study Design object which holds
 * all lists, matrices etc.
 * 
 * @author Uttara Sakhadeo
 */

@Entity
@Table(name="tablestudydesign")
public class StudyDesign
{	
	
	// UUID for the study design.  Main unique identifier for the design
	@Id
	@Column(name="uuid")
	private byte[] uuid = null;
	private UUID studyUUID = null;
	// name of the study design
	@Column(name="name")
	private String name = null;	
	// flag indicating whether we are solving for power or sample size	
//	private SolvingFor flagSolveFor = null;
	@Column(name="flagSolveFor")
	private String flagSolveFor =null;
	// flag indicating if the design includes a baseline covariate
	@Column(name="hasGaussianCovariate")
	private boolean hasGaussianCovariate = false;
	//private ConfidenceInterval confidenceIntervalDescription = null;
	private Set<ConfidenceInterval> confidenceIntervalDescriptions = new HashSet<ConfidenceInterval>();
	/**
	 * Create an empty study design without a UUID assigned
	 */
	public StudyDesign() 
	{}	
	
	/**
	 * Create a study design object with the specified UUID
	 * @param studyUUID unique identifier for the study design
	 */
	public StudyDesign(UUID studyUUID) 
	{
		this.studyUUID = studyUUID;
		this.uuid = UUIDConversion.asByteArray(studyUUID);
	}
	/*--------------------
	 * Getter/Setter Methods
	 *--------------------*/
	public UUID getStudyUUID() 
	{
		return studyUUID;
	}
		
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
		this.uuid = UUIDConversion.asByteArray(studyUUID);
	}
		
	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
		
	public boolean isHasGaussianCovariate() {
		return hasGaussianCovariate;
	}

	public void setHasGaussianCovariate(boolean hasGaussianCovariate) {
		this.hasGaussianCovariate = hasGaussianCovariate;
	}

	public SolvingFor getFlagSolvingFor() {
		return SolvingFor.fromValue(this.flagSolveFor);
	}

	public void setFlagSolvingFor(SolvingFor flagSolvingFor) {
		this.flagSolveFor = flagSolvingFor.toValue();
	}

	public Set<ConfidenceInterval> getConfidenceIntervalDescriptions() {
		return confidenceIntervalDescriptions;
	}

	public void setConfidenceIntervalDescriptions(
			Set<ConfidenceInterval> confidenceIntervalDescriptions) {
		this.confidenceIntervalDescriptions = confidenceIntervalDescriptions;
	}

	/*public ConfidenceInterval getConfidenceIntervalDescription() {
		return confidenceIntervalDescription;
	}

	public void setConfidenceIntervalDescription(
			ConfidenceInterval confidenceIntervalDescription) {
		this.confidenceIntervalDescription = confidenceIntervalDescription;
	}*/
		
}
