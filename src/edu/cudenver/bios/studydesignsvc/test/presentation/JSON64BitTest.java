package edu.cudenver.bios.studydesignsvc.test.presentation;

import java.math.BigInteger;
import java.util.Random;

import org.junit.Test;

import junit.framework.TestCase;

public class JSON64BitTest extends TestCase 
{
	BigInteger bint = null;
			//1999999991;
	
		
	/*public void setUp()
	{
		
	}*/
	
	@Test
	public void test()
	{
		bint = new BigInteger(64, new Random());
		System.out.println(bint);
	}
}
