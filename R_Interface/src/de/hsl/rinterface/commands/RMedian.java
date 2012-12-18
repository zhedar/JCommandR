package de.hsl.rinterface.commands;

import de.hsl.rinterface.objects.RObject;

/***********************************************************************
 * Module:  RMedian.java
 * Author:  Peggy Kübe
 * Purpose: Defines the Class RMedian
 ***********************************************************************/
/** 
 * Median: median(x, na.rm = FALSE)
 * x - an object for which a method has been defined, or a numeric vector containing the values whose median is to be computed.
 * na.rm - a logical value indicating whether NA values should be stripped before the computation proceeds.
 * 
 * Beispiel:
 * median(c(100,700,1))
 */

public class RMedian implements RCommand {

	private RObject x;
	private String na_rm;
	
	public RMedian(RObject x) {
		this.x = x;
	}
	
	public RObject getX() {
		return x;
	}

	public void setX(RObject x) {
		this.x = x;
	}

	public String getNa_rm() {
		return na_rm;
	}

	public void setNa_rm(String na_rm) {
		this.na_rm = na_rm.toUpperCase();
	}

	@Override
	public String prepareForSending() {
		
		return"median("+x.toRString() + 
				(na_rm !=null ? ", na.rm = " + na_rm : "") +")";
		
	}
	
}
