package de.hsl.rinterface;
/***********************************************************************
 * Module:  RParser.java
 * Author:  tobo1987
 * Purpose: Defines the Class RParser
 ***********************************************************************/

import java.io.IOException;
import java.io.PrintWriter; 
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hsl.rinterface.exception.RException;
import de.hsl.rinterface.objects.RMatrix;
import de.hsl.rinterface.objects.RObject;
import de.hsl.rinterface.objects.RSolution;
import de.hsl.rinterface.objects.RVector;

/** @pdOid 0331b484-3a04-4082-9cca-3c92db3656d8 */
public class RParser {
   
	Connection con;
	
	public RParser(Connection con){
		this.con=con;
	}
	
	/** @pdRoleInfo migr=no name=RObject assc=association4 coll=java.util.Collection impl=java.util.HashSet mult=0..* type=Aggregation */
   public java.util.Collection<RObject> rObject;
   
   /** @pdOid b6537a14-6fd3-4cd8-9682-9ea62319ef7a */
   public RObject construct(String string) {
	   RObject ro = null;
		// String nach Zeilen seperieren
		String[] zeilen = string.split(System.getProperty("line.separator"));// TODO Pattern.quote()
		ArrayList<String> grobentwurf = new ArrayList<>();
		// unwichtige Zeilen löschen
		Pattern pGrob = Pattern.compile(".*\\[.*\\].*");
		Matcher m;
		for (int i = 0; i < zeilen.length; i++) {
			m = pGrob.matcher(zeilen[i]);
			if (m.matches())
				grobentwurf.add(zeilen[i]);
		}
		//System.out.println("\n\n" + grobentwurf.get(0));
		// Pattern zum Prüfen einer Matrix
		Pattern pMatrix = Pattern.compile(".*\\[.*,.*\\].*");
		m = pMatrix.matcher(grobentwurf.get(0));
		if (m.matches()) {
			//System.out.println("Matrix");
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
					//System.out.println(zeile);
					rm.add(new ArrayList<Number>(zeilenListe));
				}
				else{
					//System.out.println(zeile);
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
			if(rv.size()==1){
				RSolution rs = new RSolution();
				rs.setValue(rv.get(0));
				return rs;
			}
			return rv;
		}
		return null;
   }
   
   
   /** @pdGenerated default getter */
   public java.util.Collection<RObject> getRObject() {
      if (rObject == null)
         rObject = new java.util.HashSet<RObject>();
      return rObject;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorRObject() {
      if (rObject == null)
         rObject = new java.util.HashSet<RObject>();
      return rObject.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newRObject */
   public void setRObject(java.util.Collection<RObject> newRObject) {
      removeAllRObject();
      for (java.util.Iterator iter = newRObject.iterator(); iter.hasNext();)
         addRObject((RObject)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newRObject */
   public void addRObject(RObject newRObject) {
      if (newRObject == null)
         return;
      if (this.rObject == null)
         this.rObject = new java.util.HashSet<RObject>();
      if (!this.rObject.contains(newRObject))
         this.rObject.add(newRObject);
   }
   
   /** @pdGenerated default remove
     * @param oldRObject */
   public void removeRObject(RObject oldRObject) {
      if (oldRObject == null)
         return;
      if (this.rObject != null)
         if (this.rObject.contains(oldRObject))
            this.rObject.remove(oldRObject);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllRObject() {
      if (rObject != null)
         rObject.clear();
   }

}