/***********************************************************************
 * Module:  RReadTypes.java
 * Author:  Tobias Steinmetzer
 * Purpose: Defines the Class RReadTypes
 ***********************************************************************/

/**
 * Eine Enumeration für verschiedene Modi zum auslesen von Daten aus Dateien.
 */

package de.hsl.rinterface.commands;

public enum RReadTypes {

	TABLE ("table"), CVS ("cvs"), CVS2 ("cvs2"), DELIM ("delim"), DELIM2 ("delim2");
	
	
	private final String type; 
	
	RReadTypes(String type){
		this.type = type;
	}
	
	@Override
	public String toString(){
		return type;		
	}
	
	public static void main(String[] args) {
		System.out.println(RReadTypes.CVS2);
	}
}
