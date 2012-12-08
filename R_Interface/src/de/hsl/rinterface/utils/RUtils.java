package de.hsl.rinterface.utils;

/**
 * @author Philipp Gruhn, Tobias Steinmetzer
 */

public abstract class RUtils 
{
	
	/**
	 * Diese Methode Formt R-Befehle um, so dass diese mit einem "try(...)" umschlossen werden, damit der Prozess nicht geschlossen wird.
	 * @param string - es werden ein oder mehrere R-Befehle erwartet die durch ein ";" getrennt sind
	 * @return ist der neue Befehl als String
	 */
	static public  String splitCommand(String string){
		String[] split = string.split(";");
		String result="";
		for (int i = 0; i < split.length; i++)
			result+="try("+split[i]+"); ";
			
		return result;
	}
	
	/**
	 * Diese Methode ersetzt alle "\" durch "/", da R den Pfad mit Slash trennt.
	 * 
	 * @param absolutePath
	 *            - Ist der Absolute Pfad der Datei
	 * @return Gibt einen Pfad zur&uuml;ck mit dem R umgehen kann
	 */
	static public String getRPath(String absolutePath) {
		return absolutePath.replace("\\", "/");
	}
}
