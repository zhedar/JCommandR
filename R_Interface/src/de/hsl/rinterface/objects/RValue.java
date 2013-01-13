/***********************************************************************
 * Module:  RValue.java
 * Author:  Tobias Steinmetzer
 * Purpose: Defines the Class RValue
 ***********************************************************************/

package de.hsl.rinterface.objects;

/**
 * RValue dient zur Abbildung von Einzelwerten. 
 */

public class RValue<T> implements RObject {
	/** @pdOid 003a2e7e-3469-4f32-9301-05b8c3faa09d */
	private T value;
	
	public RValue()
	{
	}
	
	public RValue(T val)
	{
		value = val;
	}
	
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
	public String toRString() {
		return value.toString();
	}

	@Override
	public String toString() {
		return "" + value;
	}

}