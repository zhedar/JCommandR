package de.hsl.rinterface.objects;

import java.util.ArrayList;

import de.hsl.rinterface.ConsoleConnection;
import de.hsl.rinterface.RCONSTANTS;

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

	public String[] getColTitle() {
		return colTitle;
	}

	public String getColTitleAt(int index) {
		if (index <= colTitle.length)
			return colTitle[index];
		else
			throw new IndexOutOfBoundsException();
	}

	public void setColTitle(String[] colTitle) {
		this.colTitle = colTitle;
	}

	public void setcolTitleAt(int index, String name) {
		if (index <= colTitle.length)
			colTitle[index] = name;
		else
			throw new IndexOutOfBoundsException();
	}

	public String[] getRowTitle() {
		return rowTitle;
	}

	public String getRowTitleAt(int index) {
		if (index <= rowTitle.length)
			return rowTitle[index];
		else
			throw new IndexOutOfBoundsException();
	}

	public void setRowTitle(String[] rowTitle) {
		this.rowTitle = rowTitle;
	}

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
			if (rowTitle != null)
				result += rowTitle[i] + "\t";
			
			for (int j = 0; j < getColLength(); j++) {
				result += matrix[i][j] + "\t";
			}
			result += "\n";
		}
		return result;
	}

	public static void main(String[] args) {
		RTable t1 = new RTable(2, 2);
		t1.setColTitle(new String[] { "a", "b" });
		t1.setRowTitle(new String[] { "c", "d" });
		t1.setMatrix(new String[][] { { "1", "2" }, { "3", "4" } });
		System.out.println(t1.toString());
	}

}
