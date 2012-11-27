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

	/** @pdOid b6537a14-6fd3-4cd8-9682-9ea62319ef7a */
	static public RObject construct(String string, Connection con) {
		
		//con.sendCmdRaw(cmd)
		Scanner scanner = new Scanner(string);
		Matcher m;
		String line ="";
		if(scanner.hasNextLine()){
			line = scanner.nextLine();
			System.out.println(line);
			//Regularexpression für den Read-Befehl
			Pattern pRead = Pattern.compile(">.*read\\..*(.*).*");
			m = pRead.matcher(line);
			if (m.matches()){
				//System.out.println("Read Befehl erkannt");
				return parsRead(string);
			}		
		}
			
		return parsSolution(string);
		
		
		
	}

	private static RObject parsSolution(String string) {
		List<String> grobentwurf = new ArrayList<>();
		// unwichtige Zeilen lï¿½schen
		Pattern pGrob = Pattern.compile(".*\\[.*\\].*");
		
		// zeilenweises Verarbeiten, platformunabhï¿½ngig durch Scanner
		
		Matcher m;
		Scanner scanner = new Scanner(string);
		String line = "";
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			m = pGrob.matcher(line);
			if (m.matches())
				grobentwurf.add(line);
		}
		scanner.close();

		// Pattern zum Prï¿½fen einer Matrix
		Pattern pMatrix = Pattern.compile(".*\\[.*,.*\\].*");
		if (grobentwurf.size() == 0)
			throw new IllegalArgumentException(
					"Rï¿½ckgabetyp besitzt die Lï¿½nge == 0");
		
		m = pMatrix.matcher(grobentwurf.get(0));
		if (m.matches()) {
			// System.out.println("Matrix");
			// Parsen der Matrix
			RMatrix<Double> rm = new RMatrix<>();
			List<Double> zeilenListe = new ArrayList<>();
			// Prï¿½fung fï¿½r eine Tabellenkopfzeile
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
		// Pattern zum Prï¿½fen eines Vektors
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
			if (rv.size() == 1) {
				RValue<Double> rs = new RValue<>();
				rs.setValue(rv.get(0));
				return rs;
			}
			return rv;
		}
		return null;
	}

	private static RObject parsRead(String string) {
		ArrayList<ArrayList<String>> table = new ArrayList<>();
		Scanner scanner = new Scanner(string);
		String line ="";
		String lineNr="";
		String sep="";
		int maxzeile=-1;
		int lineCounter=1;
		if(scanner.hasNextLine()){
			line = scanner.nextLine();
			if (line.contains("read")){
				//System.out.println("sep rausfinden");
			}		
		}
		while(scanner.hasNextLine()){
			line = scanner.nextLine();
			int nextChar=0;
			boolean isLine =true;
			while(true){
				try{
					lineNr+= Integer.parseInt(line.substring(nextChar, nextChar+1));
				}catch(Exception e){
					isLine = false;
				}
				if(isLine == false)
					break;
				else
					nextChar++;
			}
			//System.out.println("nextChar: "+nextChar);
			//System.out.println("lineNr: "+lineNr+", Länge: "+lineNr.length());
			//Pattern um die Zeilennummer abzuspalten
			Pattern pNummer = Pattern.compile("\\d* *");
			Matcher m;
			
			if( lineNr.length()>0 ){
				lineCounter = Integer.parseInt(lineNr);
				line = line.substring(lineNr.length()).trim();
				String[] lineSplit =line.split(",");
				
				for (String cell : lineSplit) {
					if(!cell.equals("")){
						if (lineCounter >= maxzeile ) {
							// System.out.println(zeile);
							table.add(new ArrayList<String>());
							table.get(lineCounter-1).add(cell);
							//System.out.println(table.size());
							maxzeile=lineCounter;
						} else {
							// System.out.println(zeile);
							table.get(lineCounter-1).add(cell);
						}
					}
				}
				
				
			}
			lineNr="";
		}
		System.out.println("Zeilengröße: "+table.size());
		for (ArrayList<String> arrayList : table) {
			for (String string2 : arrayList) {
				System.out.print(string2+" | ");
			}
			System.out.println("");
		}
		return null;
	}


}
