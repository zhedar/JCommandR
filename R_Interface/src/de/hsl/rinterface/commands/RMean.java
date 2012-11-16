package de.hsl.rinterface.commands;

import de.hsl.rinterface.objects.RObject;

/***********************************************************************
 * Module:  RMean.java
 * Author:  Peggy Kübe
 * Purpose: Defines the Class RMean
 ***********************************************************************/
//mean(c(50,20)) /  mean(c(50,20), trim = 0.1)
public class RMean implements RCommand {
	
	private RObject meaninput;
	private double trim;
	
	public RMean(RObject meaninput) {
		this.meaninput = meaninput;
	}

	public RObject getMeaninput() {
		return meaninput;
	}

	public void setMeaninput(RObject meaninput) {
		this.meaninput = meaninput;
	}

	public double getTrim() {
		return trim;
	}

	public void setTrim(double trim) {
		this.trim = trim;
	}

	@Override
	public String prepareForSending() {
		return "mean(" + meaninput.toRString() + 
				(trim !=0d ? ", trim = " + trim : "") +")";
	}
}
//Duoble geändert