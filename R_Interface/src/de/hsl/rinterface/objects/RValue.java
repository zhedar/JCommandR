package de.hsl.rinterface.objects;
/***********************************************************************
 * Module:  RSolution.java
 * Author:  tobo1987
 * Purpose: Defines the Class RSolution
 ***********************************************************************/


/** @pdOid 00a6c982-6c07-4b58-8392-21cf5ea4998b */
public class RValue<T> implements RObject {
   /** @pdOid 003a2e7e-3469-4f32-9301-05b8c3faa09d */
   private T value;

public T getValue() {
	return value;
}

public void setValue(T t) {
	this.value = t;
}

@Override
public RObjectTypes getType() {
	return RObjectTypes.VALUE;
}

@Override
public String toRString()
{
	return value.toString();
}




}