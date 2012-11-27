package de.hsl.rinterface.utils;

import java.util.List;

public abstract class RUtils 
{
	/**
	 * L��st eine {@link List} in einen R-Vector auf. c(1,2,3,...) 
	 * @param list die aufzul��sende {@link List}
	 * @return Textrepr��sentation des Strings
	 */
	static public String resolveList(List<? extends Number> list)
	{
		int count = 0;
		String listCmd = "c(";
		for(Number n : list)
		{
			if(++count != list.size())
				listCmd += n.toString() + ", ";
			else
				listCmd += n.toString() + ")";
		}
	
		return listCmd;
	}
	
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
	 * @return Gibt einen Pfad zur���ck mit dem R umgehen kann
	 */
	static public String getRPath(String absolutePath) {
		return absolutePath.replace("\\", "/");
	}
	
//	public static void main(String[] args) {
//		String test ="is.vector(RInterfaceTmpVar); is.matrix(RInterfaceTmpVar); is.table(RInterfaceTmpVar); is.data.frame(RInterfaceTmpVar)";
//		String test2 = "is.vector(RInterfaceTmpVar)";
//		System.out.println(splitCommand(test2));
//	}
}
