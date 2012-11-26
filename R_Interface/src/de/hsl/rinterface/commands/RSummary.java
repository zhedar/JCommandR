package de.hsl.rinterface.commands;

import de.hsl.rinterface.objects.RObject;

/***********************************************************************
 * Module:  RSummary.java
 * Author:  Peggy Kübe
 * Purpose: Defines the Class RSummary
 ***********************************************************************/
/**
 * summary(object, ..., digits = max(3, getOption("digits")-3))
 * object - an object for which a summary is desired.
 * maxsum - integer, indicating how many levels should be shown for factors.
 * digits - integer, used for number formatting with signif() (for summary.default) or format() (for summary.data.frame).
 * ... - additional arguments affecting the summary produced.
 * 
 * Beispiel:
 * test <-c(1,4,25,80,500)
 * summary(test)
 */
public class RSummary implements RCommand {

	private RObject object;
	private int maxsum;
	private int digits;
	
	
	public RSummary(RObject object) {
		this.object = object;
	}

	public RObject getObject() {
		return object;
	}

	public void setObject(RObject object) {
		this.object = object;
	}

	public int getMaxsum() {
		return maxsum;
	}

	public void setMaxsum(int maxsum) {
		this.maxsum = maxsum;
	}

	public int getDigits() {
		return digits;
	}

	public void setDigits(int digits) {
		this.digits = digits;
	}

	@Override
	public String prepareForSending() {
		return "summary(" + object.toRString() + 
				(maxsum != 0 ? ", maxsum = " + maxsum : "")+
				(digits != 0 ? ", diguits = " + digits : "")
				 +")";
	}

}
