package de.hsl.rinterface;

/***********************************************************************
 * Module:  RParser.java
 * Author:  tobo1987
 * Purpose: Defines the Class RParser
 ***********************************************************************/

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hsl.rinterface.exception.RException;
import de.hsl.rinterface.objects.RMatrix;
import de.hsl.rinterface.objects.RObject;
import de.hsl.rinterface.objects.RReference;
import de.hsl.rinterface.objects.RTable;
import de.hsl.rinterface.objects.RValue;
import de.hsl.rinterface.objects.RVector;

/** @pdOid 0331b484-3a04-4082-9cca-3c92db3656d8 */
public class RParser {

	/**
	 * @throws RException
	 * @pdOid b6537a14-6fd3-4cd8-9682-9ea62319ef7a
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
				default:
					// System.out.println("err");
					throw new RException("Datentyp nicht vorhanden.");
				}
			}
			lineCounter++;
		}
		scanner.close();
		throw new RException("Datentyp nicht vorhanden.");
	}

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

	private static RObject parsVector(String rawData, Connection con) throws RException {
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
					// System.out.println(cell[i]);
					cell[i] = cell[i].replaceAll("\"", "");
					result.add((String) cell[i]);
				}
			}
		}
		if (result.size() == 1) {
			RValue<String> rv = new RValue<>();
			rv.setValue(result.get(0));
			return rv;
		}
		scanner.close();
		return result;
	}


	private static String typeofRCmd(Connection con) throws RException {
		String answer = con.sendCmdRaw("typeof(" + RCONSTANTS.NAME_TMP_VAR
				+ ")");
		// System.out.println(answer);
		if (answer.contains("double"))
			return "double";
		if (answer.contains("integer"))
			return "integer";
		return null;
	}

}
