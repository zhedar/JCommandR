package de.hsl.rinterface.objects;
/***********************************************************************
 * Module:  RMatrix.java
 * Author:  tobo1987
 * Purpose: Defines the Class RMatrix
 ***********************************************************************/

import java.util.*;

/** @pdOid 29978f29-da45-474b-9c60-54845abcaa61 */
public class RMatrix extends ArrayList<ArrayList<Number>> implements RObject {
   /** @pdOid a1d46f5d-b3f9-4b07-9894-50df3d61ac51 */
    private ArrayList<ArrayList<Number>> matrix;

	@Override
	public void save(String name) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public RObject load(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public void add(int zeile, ArrayList<Double> zeilenListe) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RTypes getType() {
		return RTypes.MATRIX;
	}

}