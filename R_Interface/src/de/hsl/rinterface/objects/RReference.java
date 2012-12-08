package de.hsl.rinterface.objects;
/***********************************************************************
 * Module:  RReference.java
 * Author:  Tobias Steinmetzer
 * Purpose: Defines the Class RReference
 ***********************************************************************/

/** @pdOid 2bb12943-02c9-4e96-a12a-8b89dfc4c7d4 
 * 	Diese Klasse dient dazu einem R-Kommando eine Variable zu 
 *  zuordnen, damit man sie zu einem sp&auml;teren Zeitpunkt 
 *  widerverwenden kann.
 * */

public class RReference implements RObject {

	private String ref;

	public RReference(String ref) {
		this.ref = ref;
	}

	/**
	 * gibt den Namen der Reference zur&uuml;ck
	 * @return
	 */
	public String getRef() {
		return ref;
	}

	@Override
	public RObjectTypes getType() {
		return RObjectTypes.REFERENCE;
	}

	@Override
	public String toRString() {
		return ref;
	}

}