package de.hsl.rinterface.objects;
/***********************************************************************
 * Module:  RSolution.java
 * Author:  tobo1987
 * Purpose: Defines the Class RSolution
 ***********************************************************************/

import java.util.*;

/** @pdOid 00a6c982-6c07-4b58-8392-21cf5ea4998b */
public class RValue implements RObject {
   /** @pdOid 003a2e7e-3469-4f32-9301-05b8c3faa09d */
   private Number value;

@Override
public void save(String name) {
	// TODO Auto-generated method stub
	
}

@Override
public RObject load(String name) {
	// TODO Auto-generated method stub
	return null;
}

public Number getValue() {
	return value;
}

public void setValue(Number value) {
	this.value = value;
}

@Override
public RTypes getType() {
	return RTypes.SOLUTION;
}

@Override
public String toRString()
{
	// TODO Auto-generated method stub
	return null;
}




}