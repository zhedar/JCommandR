package de.hsl.rinterface.objects;
/***********************************************************************
 * Module:  RTable.java
 * Author:  Tobias Steinmetzer
 * Purpose: Defines the Class RTable
 ***********************************************************************/

import de.hsl.rinterface.RCONSTANTS;

/**
 * Diese Klasse dient zur Erstellung von Tabellen. Die 
 * Zellen bestehen dabei aus einer RMatrix und die 
 * Zeilen- bzw. Spaltentitel aus String-Arrays
 */

public class RTable extends RMatrix implements RObject {

	private String[] colTitle;
	private String[] rowTitle;

	public RTable(int rowLength, int colLength) {
		super(rowLength, colLength);
		colTitle = new String[rowLength];
		rowTitle = new String[colLength];
	}

	public RTable(RTable rTable) {
		super(rTable.getMatrix());
		this.colTitle = rTable.getColTitle();
		this.rowTitle = rTable.getRowTitle();
	}

	/**
	 * Gibt die Spaltentitel zur&uuml;ck
	 * @return String-Array der Spaltentitel
	 */
	public String[] getColTitle() {
		return colTitle;
	}

	/**
	 * Gibt eine Zelle an einem bestimmten index zur&uuml;ck aus dem Spaltentiteln.
	 * @param index Position im colTitle-Array
	 * @return Wert von der Zelle an der Position index
	 */
	public String getColTitleAt(int index) {
		if (index <= colTitle.length)
			return colTitle[index];
		else
			throw new IndexOutOfBoundsException();
	}

	/**
	 * Dient zum setzten von Spaltentiteln
	 * @param colTitle Array für die Titel
	 */
	public void setColTitle(String[] colTitle) {
		this.colTitle = colTitle;
	}

	/**
	 * Methode zum setzten eines Titels an einer bestimmten Stelle in den Spaltentiteln
	 * @param index Position im Array, welches Element ersetzt werden soll
	 * @param name Wert, der die Zelle ersetzt
	 */
	public void setcolTitleAt(int index, String name) {
		if (index <= colTitle.length)
			colTitle[index] = name;
		else
			throw new IndexOutOfBoundsException();
	}

	/**
	 * Gibt die Zeilentitel zur&uuml;ck
	 * @return String-Array der Zeilentitel
	 */
	public String[] getRowTitle() {
		return rowTitle;
	}

	/**
	 * Gibt eine Zelle an einem bestimmten index zur&uuml;ck aus den Zeilentiteln.
	 * @param index Position im rowTitle-Array
	 * @return Wert von der Zelle, an der Position index
	 */
	public String getRowTitleAt(int index) {
		if (index <= rowTitle.length)
			return rowTitle[index];
		else
			throw new IndexOutOfBoundsException();
	}

	/**
	 * Dient zum setzten von Zeilentiteln
	 * @param rowTitle Array für die Titel
	 */
	public void setRowTitle(String[] rowTitle) {
		this.rowTitle = rowTitle;
	}

	/**
	 * Methode zum setzten eines Titels an einer bestimmten Stelle in den Zeilentiteln
	 * @param index Position im Array, welches Element ersetzt werden soll
	 * @param name Wert, der die Zelle ersetzt
	 */
	public void setRowTitleAt(int index, String name) {
		if (index <= rowTitle.length)
			rowTitle[index] = name;
		else
			throw new IndexOutOfBoundsException();
	}

	@Override
	public String toRString() {
		String result = "";

		if (matrix.length > 0) {
			// Tabellenzellen hinzuf�gen
			result += RCONSTANTS.NAME_TMP_REF + "<- data.frame( ";
			for (int i = 0; i < matrix.length; i++) {
				if (i == 0)
					result += "c( ";
				else
					result += ", c( ";
				for (int j = 0; j < matrix[i].length; j++) {
					if (j == 0)
						result += matrix[i][j];
					else
						result += ", " + matrix[i][j];
				}
				result += " ) ";
			}

			// Tabellenkopf hinzuf�gen
			if (colTitle != null) {
				result += " ; colnames( " + RCONSTANTS.NAME_TMP_REF + ")<- (";
				result += "\"" + colTitle[0] + "\"";
				for (int i = 1; i < colTitle.length; i++) {
					result += ", \"" + colTitle[i] + "\"";
				}
				result += ")";
			}
			// Titel f�r Tabellenzeilen hinzuf�gen
			if (rowTitle != null) {
				result += " ; rownames( " + RCONSTANTS.NAME_TMP_REF	+ ")<- (";
				result += "\"" + rowTitle[0] + "\"";
				for (int i = 1; i < rowTitle.length; i++) {
					result += ", \"" + rowTitle[i] + "\"";
				}
				result += ")";
			}
		}
		return result;
	}

	@Override
	public RObjectTypes getType() {
		return RObjectTypes.TABLE;
	}

	@Override
	public String toString() {
		String result = "";
//		System.out.println( matrix.length);
		if (rowTitle != null && rowTitle.length > 0)
			result += " \t";
		for (int i = 0; i < colTitle.length; i++) {
			result += colTitle[i] + "\t";
		}
		result += "\n";
		for (int i = 0; i < getRowLength(); i++) {
			if (rowTitle != null || rowTitle.length != 0)
				result += rowTitle[i] + "\t"; //FIXME indexoutofbounds  
			
			for (int j = 0; j < getColLength(); j++) {
				result += matrix[i][j] + "\t";
			}
			result += "\n";
		}
		return result;
	}
}
