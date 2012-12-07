/***********************************************************************
 * Module:  RReadTypes.java
 * Author:  Tobias Steinmetzer
 * Purpose: Defines the Class RReadTypes
 ***********************************************************************/

/**
 * Eine Enumeration für verschiedene Modi zum auslesen von Daten aus Dateien.
 */

package de.hsl.rinterface.commands;

public enum RPlotTypes {

	POINTS ("p"), LINES ("l"), BOTH ("b"), PARTALONE ("c"), OVERPLOTTED ("o"), HISTOGRAMM ("h"), STAIRSTEPS ("s"), OTHERSTEPS ("S"), NONPLOTTING ("n");
	
	
	private final String type; 
	
	RPlotTypes(String type){
		this.type = type;
	}
	
	@Override
	public String toString(){
		return type;		
	}
	
}
