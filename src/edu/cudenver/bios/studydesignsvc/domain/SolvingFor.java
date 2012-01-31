package edu.cudenver.bios.studydesignsvc.domain;

import java.lang.Character.UnicodeScript;

public enum SolvingFor {
	power("power"),samplesize("samplesize");
	
	private final String value;
	
	SolvingFor(String value)
	{
		this.value=value;
	}
	
	public static SolvingFor fromValue(String value) 
	{  
		   if (value != null) {  
		     for (SolvingFor solveFor : values()) {  
		       if (solveFor.value.equals(value)) {  
		         return solveFor;  
		       }  
		     }  
		   }  
		  
		   // you may return a default value  
		   return getDefault();  
		   // or throw an exception  
		   // throw new IllegalArgumentException("Invalid color: " + value);  
	}  
		  
	public String toValue() {  
		   return value;  
		 }  
		  
	public static SolvingFor getDefault() {  
		   return null;  
	}  
}
