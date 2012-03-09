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
package edu.ucdenver.bios.studydesignsvc.tests;

import junit.framework.TestCase;

/**
 * JUnit test cases for 'NamedMatrix' object - CRUD operations.
 * 
 * @author Uttara Sakhadeo
 */
public class TestMatrix extends TestCase
{
	/*private static UUID STUDY_UUID = UUID.fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");
	private static String STUDY_NAME = "Junit Test Study Design";
	private static String THETA_MATRIX_NAME = "Theta Null Matrix";
	private static String BETA_MATRIX_NAME = "Beta Matrix";
	MatrixServerResource resource = new MatrixServerResource();
	byte[] uuid = null;		
		
	public void setUp()
	{
		uuid = UUIDUtils.asByteArray(STUDY_UUID);
	}
	
	@Test
	private void testCreateMatrixMap()
	{	
		StudyDesign studyDesign = new StudyDesign();		
		studyDesign.setUuid(uuid);
		studyDesign.setName(STUDY_NAME);
		studyDesign.setGaussianCovariate(true);				
		studyDesign.setSolutionTypeEnum(SolutionTypeEnum.POWER);		
				
		Map<String,NamedMatrix> namedMatrixMap = new HashMap<String,NamedMatrix>();	
		NamedMatrix namedMatrix = new NamedMatrix();
		List<NamedMatrixCell> matrixCellList = new ArrayList<NamedMatrixCell>();		
			
			NamedMatrixCell matrixCell = new NamedMatrixCell();				
			matrixCell.setRow(0);
			matrixCell.setColumn(0);
			matrixCell.setValue(2.5);
				
			matrixCellList.add(matrixCell);
			namedMatrix.setMatrixCellList(matrixCellList);
				
				matrixCell = new NamedMatrixCell();				
				matrixCell.setRow(0);
				matrixCell.setColumn(1);
				matrixCell.setValue(5.5);
				matrixCell.setNamedMatrix(namedMatrix);
				
			matrixCellList.add(matrixCell);				
			namedMatrix.setMatrixCellList(matrixCellList);
			//namedMatrix.setStudyDesign(studyDesign);			
			
			namedMatrix.setStudyDesign(studyDesign);
		namedMatrixMap.put(THETA_MATRIX_NAME,namedMatrix);			
		namedMatrix = new NamedMatrix();		
		matrixCellList = new ArrayList<NamedMatrixCell>();		
		
			matrixCell = new NamedMatrixCell();				
			matrixCell.setRow(0);
			matrixCell.setColumn(0);
			matrixCell.setValue(50);
				
			matrixCellList.add(matrixCell);
			namedMatrix.setMatrixCellList(matrixCellList);
				
				matrixCell = new NamedMatrixCell();				
				matrixCell.setRow(0);
				matrixCell.setColumn(1);
				matrixCell.setValue(50.5);
				matrixCell.setNamedMatrix(namedMatrix);
				
		matrixCellList.add(matrixCell);				
		namedMatrix.setMatrixCellList(matrixCellList);
		namedMatrix.setStudyDesign(studyDesign);			
		namedMatrixMap.put(BETA_MATRIX_NAME,namedMatrix);	
				
		try
		{
			namedMatrixMap = resource.create(namedMatrixMap);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			namedMatrixMap=null;
			fail();
		}
		if(namedMatrixMap==null)
		{
			fail();
		}
		else
		{
			System.out.println("testCreate() : Matrix size after persistance: "+namedMatrixMap.size());
		}
	}
	
	@Test
	private void testCreateMatrix()
	{	
		StudyDesign studyDesign = new StudyDesign();		
		studyDesign.setUuid(uuid);
		studyDesign.setName(STUDY_NAME);
		studyDesign.setGaussianCovariate(true);				
		studyDesign.setSolutionTypeEnum(SolutionTypeEnum.POWER);		
						
		NamedMatrix namedMatrix = new NamedMatrix();
		List<NamedMatrixCell> matrixCellList = new ArrayList<NamedMatrixCell>();		
			
			NamedMatrixCell matrixCell = new NamedMatrixCell();				
			matrixCell.setRow(0);
			matrixCell.setColumn(0);
			matrixCell.setValue(2.5);
				
			matrixCellList.add(matrixCell);
			namedMatrix.setMatrixCellList(matrixCellList);
				
				matrixCell = new NamedMatrixCell();				
				matrixCell.setRow(0);
				matrixCell.setColumn(1);
				matrixCell.setValue(5.5);
				matrixCell.setNamedMatrix(namedMatrix);
				
			matrixCellList.add(matrixCell);				
		namedMatrix.setMatrixCellList(matrixCellList);
		namedMatrix.setName(THETA_MATRIX_NAME);
		namedMatrix.setStudyDesign(studyDesign);			
						
		try
		{
			namedMatrix = resource.create(namedMatrix);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			namedMatrix=null;
			fail();
		}
		if(namedMatrix==null)
		{
			fail();
		}
		else
		{
			System.out.println("testCreate() : Matrix size after persistance: "+namedMatrix.getName());
		}
	}

	
	@Test
	private void testRetrieveMatrix()
	{
		NamedMatrix namedMatrix = null;			
		
		try
		{
			namedMatrix = resource.retrieve(uuid,THETA_MATRIX_NAME);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			namedMatrix=null;
			fail();
		}
		if (namedMatrix == null)
        {
        	System.err.println("No matching confidence interval found");
        	fail();
        }
        else
        {     
        	System.out.println("testRetrieve() : ");        	
        	System.out.println(namedMatrix.getName());
    		System.out.println(namedMatrix.getMatrixCellList().size());
            assertTrue(namedMatrix!=null);
        }
	}
	
	
	@Test
	private void testRetrieveMatrixMap()
	{
		Map<String,NamedMatrix> namedMatrixMap = null;			
		
		try
		{
			namedMatrixMap = resource.retrieve(uuid);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			namedMatrixMap=null;
			fail();
		}
		if (namedMatrixMap == null)
        {
        	System.err.println("No matching confidence interval found");
        	fail();
        }
        else
        {     
        	System.out.println("testRetrieve() : ");        	
        	System.out.println(namedMatrixMap.size());
    		System.out.println(namedMatrixMap.toString());
            assertTrue(namedMatrixMap!=null);
        }
	}
	
	private void testDeleteMatrix()
	{		
		NamedMatrix namedMatrix = null; 		
		try
		{
			namedMatrix = resource.remove(uuid, BETA_MATRIX_NAME);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			namedMatrix=null;
			fail();
		}
		if (namedMatrix == null)
        {
        	System.err.println("No matching confidence interval found");
        	fail();
        }
        else
        {         
        	System.out.println(namedMatrix.getName());
    		System.out.println(namedMatrix.getMatrixCellList().size());
            assertTrue(namedMatrix!=null);
        }
	}
	
	private void testDeleteMatrixMap()
	{		
		List<NamedMatrix> namedMatrixList = null;			 	
		try
		{
			namedMatrixList = resource.remove(uuid);			
		}		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			namedMatrixList=null;
			fail();
		}
		if (namedMatrixList == null)
        {
        	System.err.println("No matching confidence interval found");
        	fail();
        }
        else
        {         
        	System.out.println(namedMatrixList.size());
    		System.out.println(namedMatrixList.toString());
            assertTrue(namedMatrixList!=null);
        }
	}
	
	@Test
	public void testUpdateMatrix()
	{
		
	}
	
	@Test
	public void testUpdateMatrixMap()
	{
		
	}*/
	
}
