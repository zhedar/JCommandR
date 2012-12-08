package de.hsl.rinterface;

/***********************************************************************
 * Module:  RParser.java
 * Author:  Tobias Steinmetzer
 * Purpose: Defines the Class RParser
 ***********************************************************************/

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import de.hsl.rinterface.exception.RException;
import de.hsl.rinterface.objects.RMatrix;
import de.hsl.rinterface.objects.RObject;
import de.hsl.rinterface.objects.RReference;
import de.hsl.rinterface.objects.RString;
import de.hsl.rinterface.objects.RTable;
import de.hsl.rinterface.objects.RValue;
import de.hsl.rinterface.objects.RVector;

/** @pdOid 0331b484-3a04-4082-9cca-3c92db3656d8 */
public class RParser {

	/**
	 * Diese Methode pr&uuml;ft, ob der Befehl in R in einer Matrix, Vektor, Einzelwert, Tabelle oder Data.Frame handelt. 
	 * Danach wird der dementsprechende Parser aufgerufen.
	 * @param rawData sind die Rohdaten von R
	 * @param con ist die aktuelle Verbindung
	 * @return liefert ein RObject zurück
	 * @throws RException diese Exception tritt auf, wenn verschiedene Abfragen an R fehlschlagen
	 */
	static public RObject construct(String rawData, Connection con)
			throws RException {

		String line = "";
		String typeRaw = con.sendCmdRaw("" + "is.vector("
				+ RCONSTANTS.NAME_TMP_VAR + "); " + "is.matrix("
				+ RCONSTANTS.NAME_TMP_VAR + ");" + "is.table("
				+ RCONSTANTS.NAME_TMP_VAR + ");" + "is.data.frame("
				+ RCONSTANTS.NAME_TMP_VAR + ")");
		Scanner scanner = new Scanner(typeRaw);
		int lineCounter = 0;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (line.toLowerCase().contains("true")) {
				switch (lineCounter) {
				case 0:
					// System.out.println("vec");
					return parsVector(rawData,con);
				case 1:
					// System.out.println("mat");
					return parsMatrix(rawData,con);
				case 2:
					// System.out.println("tab");
					return parsTable(rawData,con);
				case 3:
					// System.out.println("data.frame");
					return parsDataFrame(rawData,con);					
				}
			}
			lineCounter++;
		}
		scanner.close();
		return new RString(rawData);
	}

	/**
	 * Diese Methode bildet ein RTable-Objekt aus einem data.frame von R
	 * @param rawData sind die Rohdaten von R
	 * @param con ist die aktuelle Verbindung
	 * @return gibt ein RTable_Objekt zurück
	 * @throws RException diese Exception tritt auf, wenn verschiedene Abfragen an R fehlschlagen
	 */
	private static RObject parsDataFrame(String rawData, Connection con) throws RException {
		RReference tmpRef = con.sendCmd(RCONSTANTS.NAME_TMP_VAR,
				RCONSTANTS.NAME_TMP_REF);
		// Herausbekommen der Spaltentitel
		RVector<String> colTitleList = (RVector<String>) con.sendCmd("names("
				+ tmpRef.getRef() + ")");

		String[] colTitle = new String[colTitleList.size()];
		int i = 0;
		for (String string : colTitleList) {
			colTitle[i++] = string;
		}
		// Herausbekommen, ob Zeilentitel vorhanden sind
		String[] rowTitle = null;
		try {
			String rowTitleRaw = con.sendCmdRaw("row.names(" + tmpRef.getRef()
					+ ")");
			i = 0;
			for (String string : (RVector<String>) parsVector(rowTitleRaw, con)) {
				rowTitle[i++] = string;
			}

		} catch (Exception e) {

		}
		// Herausbekommen der größe der Tabelle
		int colLength = Integer.parseInt((con.sendCmd("length(names("
				+ tmpRef.getRef() + "))")).toRString());
		int rowLength = Integer.parseInt((con.sendCmd("length(row.names(" + tmpRef.getRef() + "))")).toRString());
		// Anlegen eines RTable Objektes und füllen der Attribute
		RTable table = new RTable(rowLength, colLength);
		table.setColTitle(colTitle);
		table.setRowTitle(rowTitle);
		String line;
		Scanner scanner = new Scanner(rawData);
		scanner.nextLine();
		int lineCounter = 1;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			
			line = line.substring(((String)""+lineCounter).length());
			line = line.trim();
			String[] cellLine = line.split(" +");
//			System.out.print("Z:"+lineCounter+ " | | :");
//			for (String string : cellLine) {
//				System.out.print(" / "+string);
//			}
			table.setMatrixLine(lineCounter-1, cellLine);
			lineCounter++;
		}
		scanner.close();
		return table;
	}

	/**
	 * Diese Methode bildet ein RTable-Objekt aus dem Output von R
	 * @param rawData sind die Rohdaten die von R verschickt werden
	 * @param con ist die derzeitige Verbindung
	 * @return liefert ein RTable-Objekt 
	 * @throws RException diese Exception tritt auf, wenn verschiedene Abfragen an R fehlschlagen
	 */
	private static RObject parsTable(String rawData, Connection con) throws RException {
		RReference tmpRef = con.sendCmd(RCONSTANTS.NAME_TMP_VAR,
				RCONSTANTS.NAME_TMP_REF);
		// Herausbekommen der Spaltentiteloptions
		RVector<String> colTitleList = (RVector<String>) con.sendCmd("names("
				+ tmpRef.getRef() + ")");

		String[] colTitle = new String[colTitleList.size()];
		int i = 0;
		for (String string : colTitleList) {
			colTitle[i++] = string;
		}
		// Herausbekommen, ob Zeilentitel vorhanden sind
		String[] rowTitle = null;
		try {
			String rowTitleRaw = con.sendCmdRaw("row.names(" + tmpRef.getRef()
					+ ")");
			i = 0;
			for (String string : (RVector<String>) parsVector(rowTitleRaw, con)) {
				rowTitle[i++] = string;
			}

		} catch (Exception e) {

		}
		// Herausbekommen der größe der Tabelle
		int dimX = Integer.parseInt((con.sendCmd("length(names("
				+ tmpRef.getRef() + "))")).toRString());
		int dimY = Integer.parseInt((con.sendCmd("length(" + tmpRef.getRef()
				+ "[\"" + colTitle[0] + "\"]" + ")")).toRString());
		System.out.println("Fff" + dimX + "d" + dimY);
		// Anlegen eines RTable Objektes und füllen der Attribute
		RTable table = new RTable(dimX, dimY);
		table.setColTitle(colTitle);
		table.setRowTitle(rowTitle);
		String line;
		Scanner scanner = new Scanner(rawData);
		scanner.nextLine();
		int lineCounter = 0;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			line = line.trim();
			String[] cellLine = line.split(" +");
			table.setMatrixLine(lineCounter++, cellLine);
		}
		scanner.close();
		return table;
	}

	/**
	 * Die Methode bildet aus einem Output von eine 2x2 Matrix
	 * @param rawData sind die Rohdaten von R
	 * @param con ist die derzeitige Verbindung
	 * @return gibt eine 2x2 Matrix zurück
	 * @throws diese Exception tritt auf, wenn verschiedene Abfragen an R fehlschlagen
	 */
	private static RObject parsMatrix(String rawData, Connection con) throws RException {
		RObject dim = con.sendCmd("dim(" + RCONSTANTS.NAME_TMP_VAR + ")");
		int dimX = 0, dimY = 0;
		// System.out.println(dim.toString());
		if (dim instanceof RVector) {
			dimX = Integer.parseInt(((RVector) dim).get(0).toString());
			dimY = Integer.parseInt(((RVector) dim).get(1).toString());
		}
		RMatrix result = new RMatrix(dimX, dimY);
		Scanner scanner = new Scanner(rawData);
		int j = 0;
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			line = line.trim();
			// System.out.println(line);
			Pattern pHead = Pattern.compile("\\[,\\d+\\].*");
			Matcher m = pHead.matcher(line);
			if (!m.matches()) {
				String[] cells = line.split(" +");
				for (int i = 1; i < cells.length; i++) {
					// System.out.println("|+"+cells[i]+"+|");
					result.setMatrixAt(j - 1, i - 1, cells[i]);
				}

			}
			j++;
		}
		scanner.close();
		return result;
	}

	/**
	 * Parst Daten und bildet aus diesen ein Vektor oder einen Einzelwert. 
	 * @param rawData Rohdaten die aus R kommen
	 * @param con ist ein Verbindungsobjekt der derzeitigen Connection
	 * @return gibt einen RVector oder RValue zur&uuml;ck
	 */
	private static RObject parsVector(String rawData, Connection con){
		RVector<String> result = new RVector<>();
		Scanner scanner = new Scanner(rawData);
		String line = "";
		Pattern pLine = Pattern.compile("\\[\\d*\\]");
		Matcher m;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			line = line.trim();
			String[] cell;
			if (line.contains("\""))
				cell = line.split(" +\"");
			else
				cell = line.split(" +");
			for (int i = 0; i < cell.length; i++) {
				m = pLine.matcher(cell[i]);
				if (!(m.matches())) {
					cell[i] = cell[i].replaceAll("\"", "");
					result.add((String) cell[i]);
				}
			}
		}
		scanner.close();
		if (result.size() == 1) {
			RValue<String> rv = new RValue<>();
			rv.setValue(result.get(0));
			return rv;
		}
		return result;
	}

	/**
	 * Diese Methode pr&uuml;ft um welchen Datentyp es sich bei den Daten handelt.
	 * @param con ist die derzeitige Connection
	 * @return gibt den Datentyp als String zur&uuml;ck
	 * @throws RException
	 */
	private static String typeofRCmd(Connection con) throws RException {
		String answer = con.sendCmdRaw("typeof(" + RCONSTANTS.NAME_TMP_VAR
				+ ")");
		if (answer.contains("double"))
			return "double";
		if (answer.contains("integer"))
			return "integer";
		return null;
	}

}
