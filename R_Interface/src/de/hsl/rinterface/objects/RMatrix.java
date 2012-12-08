package de.hsl.rinterface.objects;

/***********************************************************************
 * Module:  RMatrix.java
 * Author:  Tobias Steinmetzer
 * Purpose: Defines the Class RMatrix
 ***********************************************************************/

/** @pdOid 29978f29-da45-474b-9c60-54845abcaa61 
 * RMatrix ist ein Datentyp, welcher 2 Demensionale Arrays in R und Java
 * widerspiegelt. 
 * ! Die Zellen in RMatrix sind nicht generisch, sondern sind vom Typ String !
 * */
public class RMatrix implements RObject {
	/** @pdOid a1d46f5d-b3f9-4b07-9894-50df3d61ac51 */
	
	protected String[][] matrix;
	private int rowLength;
	private int colLength;

	public RMatrix(int rowLength, int colLength){
		this.matrix= new String[rowLength][colLength];
		this.rowLength=rowLength;
		this.colLength=colLength;
	}
	
	public RMatrix(String[][] matrix){
		this.matrix=matrix;
	}
	
	public RMatrix(RMatrix matrix){
		this.matrix = matrix.getMatrix();
	}
	
	@Override
	public RObjectTypes getType() {
		return RObjectTypes.MATRIX;
	}
	
	/**
	 * Diese Methode gibt die Anzahl der Zeilen zur&uuml;ck
	 * @return int Zeilenanzahl
	 */
	public int getRowLength() {
		return rowLength;
	}

	/**
	 * Diese Methode gibt die Anzahl der Spalten zur&uuml;ck
	 * @return int Spaltenanzahl
	 */
	public int getColLength() {
		return colLength;
	}

	@Override
	public String toRString() {
		String mat = "matrix(";
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (i != matrix.length && j != matrix[0].length)
					mat += matrix[j][i].toString() + ",";
				else
					mat += matrix[j][i].toString();
			}
		}
		mat += "), nrow=" + matrix.length + ",ncol=" + matrix[0].length + ")";
		return mat;
	}

	/**
	 * Diese Methode gibt die Matrix zurück
	 * @return 2D-String der Matrix
	 */
	public String[][] getMatrix() {
		return matrix;
	}
	
	/**
	 * Diese Methode liefert den Inhalt einer bestimmten Zelle
	 * @param indexRow Index für die Zeile
	 * @param indexCol Index für die Spalte
	 * @return String Inhalt der Zelle an der Position(indexRow, indexCol) 
	 */
	public String getMatrixAt(int indexRow, int indexCol){
		if(indexRow <= matrix.length && indexCol <= matrix[0].length)
			return matrix[indexRow][indexCol];
		else
			throw new IndexOutOfBoundsException();
	}

	/**
	 * Setter zum setzen einer Matrix
	 * @param matrix
	 */
	public void setMatrix(String[][] matrix) {
		this.matrix = matrix;
	}
	
	/**
	 * Setter zum setzen einer Zeile in der Matrix
	 * @param index gibt den Index der Zeile an
	 * @param line ist die Zeile welche zur Ersetzung dient
	 */
	public void setMatrixLine(int index, String[] line){
		if(index <= matrix.length)
			matrix[index]=line;
		else
			throw new IndexOutOfBoundsException();
	}
	
	/**
	 * Setter zum ersetzen eines Wertes an einer bestimmten Position in der Matrix
	 * @param indexRow gibt den Index der Zeile an
	 * @param indexCol gibt den Index der Spalte an
	 * @param value ist der Wert welcher zur Ersetzung dient.
	 */
	public void setMatrixAt(int indexRow, int indexCol, String value){
		if(indexRow <= matrix.length && indexCol <= matrix[0].length)
			matrix[indexRow][indexCol]=value;
		else
			throw new IndexOutOfBoundsException();
	}

	@Override
	public String toString() {
		String result = "";
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				result += matrix[i][j] + " ";
			}
			result += "\n";
		}
		return result;
	}
}