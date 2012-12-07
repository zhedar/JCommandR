package de.hsl.rinterface.objects;

/***********************************************************************
 * Module:  RVector.java
 * Author:  tobo1987
 * Purpose: Defines the Class RVector
 ***********************************************************************/

import java.util.*;

/** @param <T>
 * @pdOid f54642ba-723d-49b6-af4c-54be26349140 */
public class RVector<T> extends ArrayList<T> implements RObject {
//	/** @pdOid fdfa3923-ec66-40e2-8f8e-f694a76e1b6f */
//	private List<T> values;
	private static final long serialVersionUID = -3085837660892387760L;
	
	public RVector() {
		super();
	}

	public RVector(Collection<? extends T> c) {
		super(c);
	}

	@Override
	public RObjectTypes getType() {
		return RObjectTypes.VECTOR;
	}

	@Override
	public String toRString()
	{
		int count = 0;
		String rRepresentation = "c(";
		for(T n : this)
		{
			if(++count != size())
				rRepresentation += n.toString() + ", ";
			else
				rRepresentation += n.toString() + ")";
		}
	
		return rRepresentation;
	}

	@Override
	public String toString(){
		String result="";
		for (T value : this) {
			result+=value.toString()+" ";
		}
		return result;
	}
}