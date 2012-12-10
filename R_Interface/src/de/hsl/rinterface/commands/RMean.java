package de.hsl.rinterface.commands;

import de.hsl.rinterface.objects.RObject;

/***********************************************************************
 * Module:  RMean.java
 * Author:  Peggy Kübe
 * Purpose: Defines the Class RMean
 ***********************************************************************/
/**
 * Mittelwert: mean(x, trim = 0, na.rm = FALSE, ...)
 * x - An R object. Currently there are methods for numeric/logical vectors and date,
 * date-time and time interval objects, and for data frames all of whose columns have a method.
 * Complex vectors are allowed for trim = 0, only.
 * trim - the fraction (0 to 0.5) of observations to be trimmed from each end of x before the mean is computed.
 * Values of trim outside that range are taken as the nearest endpoint. 
 * na.rm - a logical value indicating whether NA values should be stripped before the computation proceeds.
 * 
 * Beispiel:
 * mean(c(50,20))
 * mean(c(50,20), trim = 0.1)
 */
public class RMean implements RCommand {
	
	private RObject x;
	private double trim;
	private String na_rm;
	
	public RMean(RObject x) {
		this.x = x;
	}

	public RObject getX() {
		return x;
	}

	public void setX(RObject x) {
		this.x = x;
	}

	public double getTrim() {
		return trim;
	}

	public void setTrim(double trim) {
		this.trim = trim;
	}

	public String getNa_rm() {
		return na_rm;
	}

	public void setNa_rm(String na_rm) {
		this.na_rm = na_rm.toUpperCase();
	}

	@Override
	public String prepareForSending() {
		return "mean(" + x.toRString() + 
				(trim !=0d ? ", trim = " + trim : "")+
				(na_rm !=null ? ", na.rm = " + na_rm : "") +")";
	}
}