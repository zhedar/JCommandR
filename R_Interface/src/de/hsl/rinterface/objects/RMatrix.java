package de.hsl.rinterface.objects;
/***********************************************************************
 * Module:  RMatrix.java
 * Author:  tobo1987
 * Purpose: Defines the Class RMatrix
 ***********************************************************************/

import java.util.ArrayList;

/** @pdOid 29978f29-da45-474b-9c60-54845abcaa61 */
public class RMatrix<T> extends ArrayList<ArrayList<T>> implements RObject {
   /** @pdOid a1d46f5d-b3f9-4b07-9894-50df3d61ac51 */
    private ArrayList<ArrayList<T>> matrix;

	public void add(int zeile, ArrayList<Double> zeilenListe) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RTypes getType() {
		return RTypes.MATRIX;
	}

	@Override
	public String toRString()
	{
		String mat = "matrix(";
		for (int i = 0; i < matrix.size(); i++) {
			for (int j = 0; j < matrix.get(0).size(); j++) {
				
				if(i != matrix.size() && j != matrix.get(0).size())
					mat += matrix.get(j).get(i).toString()+",";
				else
					mat += matrix.get(j).get(i).toString();
			}
		}
		mat += "), nrow="+matrix.size()+",ncol="+matrix.get(0).size()+")";
		return mat;
	}

}