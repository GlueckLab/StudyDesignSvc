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


/**
 * Main Study Design object which holds
 * all lists, matrices etc.
 * 
 * @author Uttara Sakhadeo
 */
public class StudyDesign 
{
	/*--------------------
	 * Member Variables
	 *--------------------*/
	private int id;
	private String studyUUID;
	private String name;	
	/*private String flagSolveFor = null;
	private Boolean isGuassianSelection;*/
	/*--------------------
	 * Constructors
	 *--------------------*/
	public StudyDesign() 
	{}
	public StudyDesign(String studyUUID) 
	{
		this.studyUUID = studyUUID;
	}
	/*--------------------
	 * Getter/Setter Methods
	 *--------------------*/
	public String getStudyUUID() {
		return studyUUID;
	}
	public void setStudyUUID(String studyUuid) {
		this.studyUUID = studyUuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/*public String getFlagSolveFor() {
		return flagSolveFor;
	}
	public void setFlagSolveFor(String flagSolveFor) {
		this.flagSolveFor = flagSolveFor;
	}
	public Boolean getIsGuassianSelection() {
		return isGuassianSelection;
	}
	public void setIsGuassianSelection(Boolean isGuassianSelection) {
		this.isGuassianSelection = isGuassianSelection;
	}		*/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
