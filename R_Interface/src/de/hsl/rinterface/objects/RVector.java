package de.hsl.rinterface.objects;

/***********************************************************************
 * Module:  RVector.java
 * Author:  tobo1987
 * Purpose: Defines the Class RVector
 ***********************************************************************/

import java.util.*;

/** @pdOid f54642ba-723d-49b6-af4c-54be26349140 */
public class RVector extends ArrayList<Number> implements RObject {
	/** @pdOid fdfa3923-ec66-40e2-8f8e-f694a76e1b6f */
	private List<Number> values;

	@Override
	public RTypes getType() {
		return RTypes.VECTOR;
	}

	@Override
	public String toRString()
	{
		int count = 0;
		String rRepresentation = "c(";
		for(Number n : this)
		{
			if(++count != size())
				rRepresentation += n.toString() + ", ";
			else
				rRepresentation += n.toString() + ")";
		}
	
		return rRepresentation;
	}

}