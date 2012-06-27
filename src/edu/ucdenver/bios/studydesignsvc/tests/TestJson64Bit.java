package edu.ucdenver.bios.studydesignsvc.tests;

import java.math.BigInteger;
import java.util.Random;

import junit.framework.TestCase;

import org.junit.Test;

public class TestJson64Bit extends TestCase{
    BigInteger bint = null;
        //1999999991;
    static boolean flag;
    
    /*public void setUp()
    {
    
    }*/
    
    @Test
    private void testByteCompatibility()
    {
    bint = new BigInteger(64, new Random());
    System.out.println(bint);
    }

}
