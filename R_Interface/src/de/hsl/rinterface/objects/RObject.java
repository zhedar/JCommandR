package de.hsl.rinterface.objects;

/***********************************************************************
 * Module:  RObject.java
 * Author:  Tobias Steinmetzer
 * Purpose: Defines the Interface RObject
 ***********************************************************************/

/**
 * @pdOid cefcaac4-11e4-4207-8026-4f884e70f39b 
 * 	Diese Schnittstelle bildet die
 *  Basis für alle Datentypen, die durch R erstellt werden.
 */
public interface RObject {

	/**
	 * Bildet aus dem Datentyp einen String, 
	 * welcher in R benutzt werden kann als Eingabe.
	 * @return String in R-Notation
	 */
	String toRString();

	/**
	 * Liefert den Datentyp eines RObjects wieder, 
	 * welche in der Enumeration RObjectTypes erfasst sind.
	 * @return String Spezialisierung eines RObject
	 */
	RObjectTypes getType();

}