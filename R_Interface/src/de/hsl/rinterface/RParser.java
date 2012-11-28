package de.hsl.rinterface;

/***********************************************************************
 * Module:  RParser.java
 * Author:  tobo1987
 * Purpose: Defines the Class RParser
 ***********************************************************************/

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hsl.rinterface.exception.RException;
import de.hsl.rinterface.objects.RMatrix;
import de.hsl.rinterface.objects.RObject;
import de.hsl.rinterface.objects.RValue;
import de.hsl.rinterface.objects.RVector;

/** @pdOid 0331b484-3a04-4082-9cca-3c92db3656d8 */
public class RParser {

	private static Connection con;
	private static String rawData;
	private static Scanner scanner;

	/**
	 * @throws RException
	 * @pdOid b6537a14-6fd3-4cd8-9682-9ea62319ef7a
	 */

	static public RObject construct(String rawData, Connection con)
			throws RException {

		setCon(con);
		setString(rawData);
		String line = "";
		String type = "";
		String typeRaw = con.sendCmdRaw("" + "is.vector("
				+ con.getTempVarName() + "); " + "is.matrix("
				+ con.getTempVarName() + ");" + "is.table("
				+ con.getTempVarName() + ");" + "is.data.frame("
				+ con.getTempVarName() + ")");
		Scanner scanner = new Scanner(typeRaw);
		int lineCounter = 0;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (line.toLowerCase().contains("true")) {
				switch (lineCounter) {
				case 0:
					return parsVector();
				case 1:
					return parsMatrix();
				case 2:
					return parsTable();
				case 3:
					return parsDataFrame();
				default:
					throw new RException("Datentyp nicht vorhanden.");
				}
			}

		}
		throw new RException("Datentyp nicht vorhanden.");
	}

	private static RObject parsDataFrame() {
		// TODO Auto-generated method stub
		return null;
	}

	private static RObject parsTable() {
		// TODO Auto-generated method stub
		return null;
	}

	private static RObject parsMatrix() {
		// TODO Auto-generated method stub
		return null;
	}

	private static <T> RObject parsVector() throws RException {
		RVector<T> result = new RVector<>();
		scanner = new Scanner(rawData);
		String line = "";
		Pattern pLine = Pattern.compile("[\\d+]");
		Matcher m;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			String[] cell = line.split(" +");
			for (int i = 0; i < cell.length; i++) {
				m = pLine.matcher(cell[i]);
				if (m.matches()) {
					result.add((T) cell[i]);
				}
			}
		}
		if (result.size() == 1) {
			RValue<T> rv = new RValue<>();
			rv.setValue(result.get(0));
			return rv;
		}
		return result;
	}

	public static String getString() {
		return rawData;
	}

	public static void setString(String string) {
		RParser.rawData = string;
	}

	public static Connection getCon() {
		return con;
	}

	public static void setCon(Connection con) {
		RParser.con = con;
	}

	private static String typeofRCmd() throws RException {
		String answer = con.sendCmdRaw("typeof(" + con.getTempVarName() + ")");
		System.out.println(answer);
		if (answer.contains("double"))
			return "double";
		if (answer.contains("integer"))
			return "integer";
		return null;
	}

}
