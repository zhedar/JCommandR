package de.hsl.rinterface.commands;

import de.hsl.rinterface.objects.RObject;

/***********************************************************************
 * Module:  RSD.java
 * Author:  Peggy Kübe
 * Purpose: Defines the Class RSD
 ***********************************************************************/
/** Standardabweichung
 * sd(x, na.rm = FALSE)
 * x - a numeric vector or an R object which is coercible to one by as.vector
 * Earlier versions of R allowed matrices or data frames for x, see below.
 * na.rm - logical. Should missing values be removed?
 * 
 * Beispiel:
 * sd(1:2), sd(k)
 */

public class RSD implements RCommand{
	
	private RObject x;
	private String na_rm;
	
	public RSD(RObject x) {
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
		return "sd(" + x.toRString() + 
				(na_rm !=null ? ", na.rm = " + na_rm : "") +")";
	}
}
