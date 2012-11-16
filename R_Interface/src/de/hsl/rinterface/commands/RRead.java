package de.hsl.rinterface.commands;

/***********************************************************************
 * Module: RRead.java Author: tobo1987 Purpose: Defines the Class RRead
 ***********************************************************************/

import java.util.*;

import de.hsl.rinterface.objects.RObject;

/** @pdOid 2fd80f5b-c8a9-4d29-aa6f-077440217b27 */
public class RRead implements RCommand
{

	private String path;
	private boolean header;
	private String sep;
	private String quote;
	private String dec;
	private String rowName;
	private String colName;
	private String naStrings;
	private String commentChar;
	
	public RRead(String path){
		this.path=path;
		this.header=false;
		this.sep="\"\"";
		this.quote="\"\\\"\'";
		this.dec="\".\"";
		this.rowName="";
		this.colName="";
		this.naStrings="\"NA\"";
		this.commentChar="\"#\"";
	}
	

	@Override
	public String prepareForSending() {
		// TODO Auto-generated method stub
		return null;
	}


}