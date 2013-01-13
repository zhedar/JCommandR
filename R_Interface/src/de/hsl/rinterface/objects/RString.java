/***********************************************************************
 * Module:  RString.java
 * Author:  Tobias Steinmetzer
 * Purpose: Defines the Class RString
 ***********************************************************************/

package de.hsl.rinterface.objects;

/**
 * Diese Klasse dient zur Darstellung, falls der RParser keinen geeigneten Datentyp findet.
 */
public class RString implements RObject {
	
	private String result;
	
	public RString(){
	}
	
	public RString(String string){
		this.result=string;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toRString() {
		return null;
	}

	@Override
	public RObjectTypes getType() {
		return RObjectTypes.STRING;
	}
	
	@Override
	public String toString(){
		return this.result;
	}
}
