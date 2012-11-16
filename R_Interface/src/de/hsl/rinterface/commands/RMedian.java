package de.hsl.rinterface.commands;

import de.hsl.rinterface.objects.RObject;

/***********************************************************************
 * Module:  RMedian.java
 * Author:  Peggy Kübe
 * Purpose: Defines the Class RMedian
 ***********************************************************************/
//median(c(100,700,1))

public class RMedian implements RCommand {

	private RObject medianinput;
	
	public RMedian(RObject input) {
		this.medianinput = input;
	}
	
	public RObject getInput() {
		return medianinput;
	}

	public void setInput(RObject input) {
		this.medianinput = input;
	}


	@Override
	public String prepareForSending() {
		
		String medianoutput = "median("+medianinput.toRString()+")";
		
		return medianoutput;
	}
	
}
