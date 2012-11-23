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

import de.hsl.rinterface.objects.RMatrix;
import de.hsl.rinterface.objects.RObject;
import de.hsl.rinterface.objects.RValue;
import de.hsl.rinterface.objects.RVector;

/** @pdOid 0331b484-3a04-4082-9cca-3c92db3656d8 */
public class RParser {

	// /** @pdRoleInfo migr=no name=RObject assc=association4
	// coll=java.util.Collection impl=java.util.HashSet mult=0..*
	// type=Aggregation */
	// public java.util.Collection<RObject> rObject;

	/** @pdOid b6537a14-6fd3-4cd8-9682-9ea62319ef7a */
	public RObject construct(String string) {
		// System.out.println(string);
		// RObject ro = null;
		// String nach Zeilen seperieren
		// String[] zeilen =
		// string.split(Pattern.quote(System.getProperty("line.separator")));
		List<String> grobentwurf = new ArrayList<>();
		// unwichtige Zeilen l�schen
		Pattern pGrob = Pattern.compile(".*\\[.*\\].*");
		Matcher m;
		// zeilenweises Verarbeiten, platformunabh�ngig durch Scanner
		Scanner scanner = new Scanner(string);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			m = pGrob.matcher(line);
			if (m.matches())
				grobentwurf.add(line);
		   }
		   scanner.close();
//		System.out.println(grobentwurf.get(0));
//		for (int i = 0; i < zeilen.length; i++) {
//			m = pGrob.matcher(zeilen[i]);
//			System.out.println(zeilen[i] + m.matches());
//			if (m.matches())
//				grobentwurf.add(zeilen[i]); //FIXME hier geht er nicht rein, stimmt das mit Pattern nicht?
//		}
		//System.out.println("\n\n" + grobentwurf.get(0));
		// Pattern zum Pr�fen einer Matrix
		Pattern pMatrix = Pattern.compile(".*\\[.*,.*\\].*");
		if(grobentwurf.size()==0)
			throw new IllegalArgumentException("R�ckgabetyp besitzt die L�nge == 0");
		if (grobentwurf.size() == 0)
			throw new IllegalArgumentException(
					"R�ckgabetyp besitzt die L�nge == 0");
		m = pMatrix.matcher(grobentwurf.get(0));
		if (m.matches()) {
			// System.out.println("Matrix");
			// Parsen der Matrix
			RMatrix<Double> rm = new RMatrix<>();
			List<Double> zeilenListe = new ArrayList<>();
			// Pr�fung f�r eine Tabellenkopfzeile
			Pattern pHead = Pattern.compile(".*\\[,\\d.*\\].*");
			int maxzeile = -1;
			int zeile = 0;
			for (int i = 0; i < grobentwurf.size(); i++) {
				m = pHead.matcher(grobentwurf.get(i));
				if (m.matches()) {
					maxzeile = zeile;
					zeile = 0;
					continue;
				}
				String[] zeil = grobentwurf.get(i).split("\\s+");
				for (int j = 1; j < zeil.length; j++) {
					// System.out.println(Double.parseDouble(zeil[j]));
					zeilenListe.add(Double.parseDouble(zeil[j]));
				}

				if (zeile >= maxzeile) {
					// System.out.println(zeile);
					rm.add(new ArrayList<Double>(zeilenListe));
				} else {
					// System.out.println(zeile);
					rm.get(zeile).addAll(zeilenListe);
				}
				zeilenListe.clear();
				zeile++;

			}
			// ro=rm;
			return rm;
		}
		// Pattern zum Pr�fen eines Vektors
		Pattern pVector = Pattern.compile(".*\\[.*\\].*");
		m = pVector.matcher(grobentwurf.get(0));
		if (m.matches()) {
			System.out.println("Vektor");
			// Parsen des Vektors
			RVector<Double> rv = new RVector<>();
			for (int i = 0; i < grobentwurf.size(); i++) {
				String[] zeil = grobentwurf.get(i).split("\\s");
				for (int j = 1; j < zeil.length; j++) {
					rv.add(Double.parseDouble(zeil[j]));
				}
			}
			if(rv.size()==1){
				RValue<Double> rs = new RValue<>();
				rs.setValue(rv.get(0));
				return rs;
			}
			return rv;
		}
		return null;
	}

	/**
	 * Diese Methode ersetzt alle "\" durch "/", da R den Pfad mit Slash trennt.
	 * @param absolutePath - Ist der Absolute Pfad der Datei
	 * @return Gibt einen Pfad zur�ck mit dem R umgehen kann
	 */
	public String getRPath(String absolutePath) {
		return absolutePath.replace("\\", "/");
	}
}
