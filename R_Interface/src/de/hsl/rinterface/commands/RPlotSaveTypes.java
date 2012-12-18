/***********************************************************************
 * Module:  RPlotSaveTypes.java
 * Author:  Peggy Kübe
 * Purpose: Defines the Class RPlotSaveTypes
 ***********************************************************************/

/**
 * Eine Enumeration für verschiedene Modi zum auslesen von Daten aus Dateien.
 */

package de.hsl.rinterface.commands;

public enum RPlotSaveTypes {

	JPEG ("jpeg"), PNG ("png"), WMF ("wmf"), PDF ("pdf"), POSTSCRIPT ("postscript");
	
	
	private final String type; 
	
	RPlotSaveTypes(String type){
		this.type = type;
	}
	
	@Override
	public String toString(){
		return type;		
	}
	
}
