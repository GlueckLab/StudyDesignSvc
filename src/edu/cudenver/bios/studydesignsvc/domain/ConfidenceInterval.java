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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.ForeignKey;

import edu.cudenver.bios.studydesignsvc.application.UUIDConversion;

/**
 * This is a wrapper for the Confidence Interval information.
 * @author Uttara Sakhadeo
 *
 */
@Entity
public class ConfidenceInterval 
{
	/*--------------------
	 * Member Variables
	 *--------------------*/
	private int id;
	//private byte[] uuid;
	// UUID for the study design.  referencing a unique identifier for the design	
	private StudyDesign studyDesign;
	private UUID studyUUID;
	@Column(name="isBetaFixed")
	private boolean betaFixed;
	@Column(name="isSigmaFixed")
	private boolean sigmaFixed;
	private float lowerTrailProbability;
	private float upperTrailProbability;
	@Column(name="sampleSize")
	private int sampleSize;
	@Column(name="rankOfDesignMatrix")
	private  int rankOfDesignMatrix;	
	/*--------------------
	 * Constructors
	 *--------------------*/	
	public ConfidenceInterval() {
	}	
	/**
	 * @param studyUUID
	 * @param isBetaFixed
	 * @param isSigmaFixed
	 * @param lowerTailProbability
	 * @param upperTailProbability
	 * @param sampleSize
	 * @param rankOfDesignMatrix
	 */
	public ConfidenceInterval(UUID studyUUID, boolean isBetaFixed,
			boolean isSigmaFixed, float lowerTailProbability,
			float upperTailProbability, int sampleSize, int rankOfDesignMatrix) {
		this.studyUUID = studyUUID;
		this.betaFixed = isBetaFixed;
		this.sigmaFixed = isSigmaFixed;
		this.lowerTrailProbability = lowerTailProbability;
		this.upperTrailProbability = upperTailProbability;
		this.sampleSize = sampleSize;
		this.rankOfDesignMatrix = rankOfDesignMatrix;
	}
	/**
	 * @param id
	 * @param studyUUID
	 * @param isBetaFixed
	 * @param isSigmaFixed
	 * @param lowerTailProbability
	 * @param upperTailProbability
	 * @param sampleSize
	 * @param rankOfDesignMatrix
	 */
	public ConfidenceInterval(int id, UUID studyUUID, boolean isBetaFixed,
			boolean isSigmaFixed, float lowerTailProbability,
			float upperTailProbability, int sampleSize, int rankOfDesignMatrix) {
		this.id = id;
		this.studyUUID = studyUUID;
		this.betaFixed = isBetaFixed;
		this.sigmaFixed = isSigmaFixed;
		this.lowerTrailProbability = lowerTailProbability;
		this.upperTrailProbability = upperTailProbability;
		this.sampleSize = sampleSize;
		this.rankOfDesignMatrix = rankOfDesignMatrix;
	}			
	/*--------------------
	 * Getter/Setter Methods
	 *--------------------*/	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public UUID getStudyUUID() {
		return studyUUID;
	}
	public void setStudyUUID(UUID studyUUID) {
		this.studyUUID = studyUUID;		
	}
	
	public boolean isBetaFixed() {
		return betaFixed;
	}
	public void setBetaFixed(boolean isBetaFixed) {
		this.betaFixed = isBetaFixed;
	}	
	public boolean isSigmaFixed() {
		return sigmaFixed;
	}
	public void setSigmaFixed(boolean isSigmaFixed) {
		this.sigmaFixed = isSigmaFixed;
	}	
	public int getSampleSize() {
		return sampleSize;
	}
	public void setSampleSize(int sampleSize) {
		this.sampleSize = sampleSize;
	}	
	public int getRankOfDesignMatrix() {
		return rankOfDesignMatrix;
	}
	public void setRankOfDesignMatrix(int rankOfDesignMatrix) {
		this.rankOfDesignMatrix = rankOfDesignMatrix;
	}
	public StudyDesign getStudyDesign() {
		return studyDesign;
	}
	public void setStudyDesign(StudyDesign studyDesign) {
		this.studyDesign = studyDesign;
	}
	public float getLowerTrailProbability() {
		return lowerTrailProbability;
	}
	public void setLowerTrailProbability(float lowerTrailProbability) {
		this.lowerTrailProbability = lowerTrailProbability;
	}
	public float getUpperTrailProbability() {
		return upperTrailProbability;
	}
	public void setUpperTrailProbability(float upperTrailProbability) {
		this.upperTrailProbability = upperTrailProbability;
	}
	/*public byte[] getUuid() {
		return uuid;
	}
	public void setUuid(byte[] uuid) {
		this.uuid = uuid;
	}*/
	
}
