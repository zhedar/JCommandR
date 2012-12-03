package de.hsl.rinterface.objects;
/***********************************************************************
 * Module:  RObject.java
 * Author:  tobo1987
 * Purpose: Defines the Interface RObject
 ***********************************************************************/


/** @pdOid cefcaac4-11e4-4207-8026-4f884e70f39b */
public interface RObject {
	
   String toRString();
   RObjectTypes getType();

}