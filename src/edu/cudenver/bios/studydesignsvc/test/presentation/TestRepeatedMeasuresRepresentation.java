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
package edu.cudenver.bios.studydesignsvc.test.presentation;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import edu.cudenver.bios.studydesignsvc.application.StudyDesignConstants;
import edu.cudenver.bios.studydesignsvc.domain.GenericTreeNode;
import edu.cudenver.bios.studydesignsvc.domain.RepeatedMeasures;

/**
 * Unit test for parsing of outgoing representation of a repeated measures object.
 * 
 * @author Uttara Sakhadeo
 */
public class TestRepeatedMeasuresRepresentation extends TestCase  
{
	private RepeatedMeasures repeatedMeasures = null;
	
	private static final String expectedXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"+
			"<"+StudyDesignConstants.TAG_REPEATEDMEASURES+">"+
			"<"+StudyDesignConstants.TAG_DIMENSION+" "+StudyDesignConstants.ATTR_NAME+"=\"time\" "+StudyDesignConstants.ATTR_TYPE+" =\"numeric\" "+
			StudyDesignConstants.ATTR_COUNT+" =3>"+
				"<"+StudyDesignConstants.TAG_SPACING+">"+
				   "<"+StudyDesignConstants.TAG_LIST_ELEMENT+">"+"5"+"</"+StudyDesignConstants.TAG_LIST_ELEMENT+">"+
				   "<"+StudyDesignConstants.TAG_LIST_ELEMENT+">"+"10"+"</"+StudyDesignConstants.TAG_LIST_ELEMENT+">"+	
				"</"+StudyDesignConstants.TAG_SPACING+">"+
				"<"+StudyDesignConstants.TAG_DIMENSION+" "+StudyDesignConstants.ATTR_NAME+"=\"timeOfDay\" "+StudyDesignConstants.ATTR_TYPE+" =\"numeric\" "+
					StudyDesignConstants.ATTR_COUNT+" =2>"+
					"<"+StudyDesignConstants.TAG_DIMENSION+" "+StudyDesignConstants.ATTR_NAME+"=\"bodyPosition\" "+StudyDesignConstants.ATTR_TYPE+" =\"categorical\" "+
					StudyDesignConstants.ATTR_COUNT+" =3/>"+
				"</"+StudyDesignConstants.TAG_DIMENSION+">"+
			"</"+StudyDesignConstants.TAG_DIMENSION+">"+
			"</"+StudyDesignConstants.TAG_REPEATEDMEASURES+">";
	
	/**
	 * Create List
	 */
    public void setUp()
    {    	
		GenericTreeNode<RepeatedMeasures> rootNode = new GenericTreeNode<RepeatedMeasures>();
		repeatedMeasures=new RepeatedMeasures();
		repeatedMeasures.setName("time");
		repeatedMeasures.setType("numeric");
		repeatedMeasures.setCount(new Integer(3));
		List<Integer> list = new ArrayList<Integer>();
		list.add(5);
		list.add(10);
		repeatedMeasures.setSpacingList(list);
		rootNode.setData(repeatedMeasures);
		
		GenericTreeNode<RepeatedMeasures> treeNode = new GenericTreeNode<RepeatedMeasures>();
		repeatedMeasures=new RepeatedMeasures();
		repeatedMeasures.setName("timeOfDay");
		repeatedMeasures.setType("numeric");
		repeatedMeasures.setCount(new Integer(2));
		treeNode.setData(repeatedMeasures);
		rootNode.addChild(treeNode);
		
		treeNode = new GenericTreeNode<RepeatedMeasures>();
		repeatedMeasures=new RepeatedMeasures();
		repeatedMeasures.setName("bodyPosition");
		repeatedMeasures.setType("categorical");
		repeatedMeasures.setCount(new Integer(3));
		treeNode.setData(repeatedMeasures);
		rootNode.addChild(treeNode);
    }
}
