package de.hsl.rinterface.objects;

/***********************************************************************
 * Module:  RObject .java
 * Author:  Tobias Steinmetzer
 * Purpose: Defines the Enumeration RObjectTypes
 ***********************************************************************/

/**
 * Diese Enumeration dient zur Bestimmung des Datentypes eines RObject
 */

public enum RObjectTypes {
	
	/**
	 * Typen eines RObject
	 */
	VECTOR , MATRIX, VALUE, REFERENCE, TABLE, STRING;
}
