package experimental;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hsl.rinterface.objects.RMatrix;
import de.hsl.rinterface.objects.RObject;
import de.hsl.rinterface.objects.RVector;

public class ParserTest {
	public static void main(String[] args) {
		String vector = "> a" + System.getProperty("line.separator")
				+ "[1] 3 4 5 6 8" + System.getProperty("line.separator")
				+ "[6] 5 7 3" + System.getProperty("line.separator") + ">";
		String matrix = "> b " + System.getProperty("line.separator")
				+ "[,1] [,2] [,3] [,4] [,5]"
				+ System.getProperty("line.separator")
				+ "[1,] 1 0 4 0 0"
				+ System.getProperty("line.separator")
				+ "[,1] [,2] [,3] [,4] [,5]"
				+ System.getProperty("line.separator")
				+ "[2,]    0    6    0    0    0"
				+ System.getProperty("line.separator")
				+ "[3,]    0    0    8    0    0"
				+ System.getProperty("line.separator")
				+ "[4,]    0    0    0    0    9"
				+ System.getProperty("line.separator")
				+ "[,1] [,2] [,3] [,4] [,5]"
				+ System.getProperty("line.separator")+ ">";

		// System.out.println(vector);
		System.out.println(matrix);

		RObject ro = parser(matrix);
		if (ro instanceof RVector) {
			RVector name = (RVector) ro;
			for (int i = 0; i < name.size(); i++) {
				System.out.println("" + name.get(i));
			}
		}
		if (ro instanceof RMatrix) {
			RMatrix name = (RMatrix) ro;
			System.out.println(name.size());
			for (ArrayList<Number> arrayList : name) {
				for (Number number : arrayList) {
					System.out.print(" " + number);
				}
				System.out.print(System.getProperty("line.separator"));
			}
		}

	}

	private static RObject parser(String str) {
		RObject ro = null;
		// String nach Zeilen seperieren
		String[] zeilen = str.split(System.getProperty("line.separator"));
		ArrayList<String> grobentwurf = new ArrayList<>();
		// unwichtige Zeilen löschen
		Pattern pGrob = Pattern.compile(".*\\[.*\\].*");
		Matcher m;
		for (int i = 0; i < zeilen.length; i++) {
			m = pGrob.matcher(zeilen[i]);
			if (m.matches())
				grobentwurf.add(zeilen[i]);
		}
		System.out.println("\n\n" + grobentwurf.get(0));
		// Pattern zum Prüfen einer Matrix
		Pattern pMatrix = Pattern.compile(".*\\[.*,.*\\].*");
		m = pMatrix.matcher(grobentwurf.get(0));
		if (m.matches()) {
			System.out.println("Matrix");
			// Parsen der Matrix
			RMatrix rm = new RMatrix();
			ArrayList<Double> zeilenListe = new ArrayList<>();
			// Prüfung für eine Tabellenkopfzeile
			Pattern pHead = Pattern.compile(".*\\[,\\d.*\\].*");
			int maxzeile=-1;
			int zeile=0;
			for (int i = 0; i < grobentwurf.size(); i++) {
				m = pHead.matcher(grobentwurf.get(i));
				if(m.matches()){
					maxzeile = zeile;
					zeile = 0;
					continue;
				}
				String[] zeil=grobentwurf.get(i).split("\\s+");
				for (int j = 1; j < zeil.length; j++) {
					//System.out.println(Double.parseDouble(zeil[j]));
					zeilenListe.add(Double.parseDouble(zeil[j]));
				}
				
				if(zeile >= maxzeile){
					System.out.println(zeile);
					rm.add(new ArrayList<Number>(zeilenListe));
				}
				else{
					System.out.println(zeile);
					rm.get(zeile).addAll(zeilenListe);
				}
				zeilenListe.clear();
				zeile++;
				
			}
			ro=rm;
			return ro;
		}
		// Pattern zum Prüfen eines Vektors
		Pattern pVector = Pattern.compile(".*\\[.*\\].*");
		m = pVector.matcher(grobentwurf.get(0));
		if (m.matches()) {
			System.out.println("Vektor");
			// Parsen des Vektors
			RVector rv = new RVector();
			for (int i = 0; i < grobentwurf.size(); i++) {
				String[] zeil = grobentwurf.get(i).split("\\s");
				for (int j = 1; j < zeil.length; j++) {
					rv.add(Double.parseDouble(zeil[j]));
				}
			}
			ro = rv;
			return ro;
		}
		System.out.println("unbekannt");
		return ro;
	}
}
