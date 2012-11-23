package de.hsl.rinterface.commands;

public enum RReadTypes {

	TABLE ("table"), CSV ("csv"), CSV2 ("csv2"), DELIM ("delim"), DELIM2 ("delim2");
	
	
	private final String type; 
	
	RReadTypes(String type){
		this.type = type;
	}
	
	@Override
	public String toString(){
		return type;		
	}
	
}
