package de.hsl.rinterface.objects;

/***********************************************************************
 * Module:  RVector.java
 * Author:  Tobias Steinmetzer
 * Purpose: Defines the Class RVector
 ***********************************************************************/

import java.util.*;

/** 
 * @pdOid f54642ba-723d-49b6-af4c-54be26349140 
 * RVector ist eine generische Klasse, welche dazu dient Vektoren aus R oder Java abzubilden.
 * @param <T> gibt den Datentyp der Klasse an
 * */
public class RVector<T> extends ArrayList<T> implements RObject {
	
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