package de.hsl.rinterface.commands;
/***********************************************************************
 * Module:  RCommand.java
 * Author:  tobo1987
 * Purpose: Defines the Interface RCommand
 ***********************************************************************/


/** @pdOid 0177da17-6661-4458-8fd8-f7b305c949a3 */
public interface RCommand
{
   /** @pdOid dede89b5-69d6-458f-be40-30cc9ae6ade4 
    	Fasst die Attribute der Klasse in einen Methodenaufrufzusammen und gibt
    	sie als Stringrepräsentation zurück.*/
		String prepareForSending();
}