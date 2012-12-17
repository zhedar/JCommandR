package de.hsl.rinterface;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public abstract class RCONSTANTS {
	/** Pfad zur R-Binary **/
	static public final String 	PATH;
	/** Timeout in Millisekunden, während dem der 
	 * R-Prozess terminieren soll.<br> Braucht er länger als erwartet,
	 * wird er zu beenden versucht.<br>Sollte bei langsameren Maschinen höher
	 * gesetzt werden, damit der Prozess ordentlich terminieren kann.**/
	static public final long	CLOSE_TIMEOUT,
	/** Zeit in ms, die zwischen den Versuchen, eine Antwort zu erhalten, gewartet wird.
	 * @see TRIES_TILL_FAIL**/
								TRY_IDLE_TIME;
	/** Anzahl der Versuche, die unternommen werden, um eine Antwort vom R-Prozess zu erhalten.
	 *  Wird benötigt, wenn eine blockierende Anfrage abgesendet wird, die ein Ergebnis erwartet.
	 *  Wird die Anzahl zu niedrig gewählt, kann eine Anfrage bei rechenintensiveren Anfragen fälschlicherweise
	 *  fehlschlagen. Eine zu hohe Anzahl bewirkt ein langes Blockieren, falls die Anfrage irrtümlich kein Ergebnis
	 *  zurückliefert. **/
	static public final int 	TRIES_TILL_FAIL;
	/** Name der Variablen, in die das Ergebnis
	 * der letzten Anfrage gespeichert werden soll.**/
	static public final String NAME_TMP_VAR,
	/** Name der Variablen, in der interne Referenzen auf 
	 * zu verarbeitende Datenstrukturen gesetzt werden.**/
								NAME_TMP_REF;
	/**Pfad zum verwendeten Logger**/
	public static final String LOGGERPATH = "de.hsl.rinterface";
	
	static {
		Properties prop = new Properties();
		try (InputStream is = new FileInputStream("rinterface.properties")) {
			prop.load(is);
			PATH = prop.getProperty("path");
			CLOSE_TIMEOUT = Long.parseLong(prop.getProperty("closetimeout",
					"200"));
			TRY_IDLE_TIME = Long.parseLong(prop
					.getProperty("tryidletime", "50"));
			TRIES_TILL_FAIL = Integer.parseInt(prop.getProperty(
					"triestillfail", "2000")); 
			// TODO dokumentieren, vars und tries-änderung
			NAME_TMP_VAR = prop.getProperty("nametempvar", "RInterfaceTempVar");
			NAME_TMP_REF = prop.getProperty("nametempref", "RInterfaceTempRef");
		}
	  catch (IOException ex) {
		Logger.getLogger("de.hsl.rinterface").throwing("RCONSTANTS", "init", ex);
	    throw new RuntimeException("Fehler beim Laden der Properties: " + ex.getMessage(), ex);
	  }
	}
}
