package de.hsl.rinterface;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import de.hsl.rinterface.commands.RCommand;
import de.hsl.rinterface.exception.RException;
import de.hsl.rinterface.objects.RObject;

public class ConsoleConnection implements Connection
{
	private Process proc;
	private final BufferedReader errRd, outRd;
//	private final BufferedWriter inWr;
	private final PrintWriter pWr;

	public ConsoleConnection(String path, List<String> args) throws IOException
	{
		//"c:\\r\\R-2.15.0\\bin\\x64\\r.exe" - Testpfad für Windows
		//Pfad und Argumente zusammenstecken
		List<String> pathAndArgs = new ArrayList<>();
			pathAndArgs.add(path);
			pathAndArgs.addAll(args);
		//Prozess starten
		proc = new ProcessBuilder(pathAndArgs).start();
		
		errRd = new BufferedReader(new InputStreamReader(
				proc.getErrorStream()));
		outRd = new BufferedReader(new InputStreamReader(
				proc.getInputStream()));
		BufferedWriter inWr = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(
					proc.getOutputStream())));
		pWr = new PrintWriter(inWr);
		
		//Blocke bis zum Eintreffen der Willkommensnachricht
		outRd.readLine();
		while(outRd.ready())	
			outRd.readLine(); //Verwerfen des Texts
		//Initialisierung fertig, block lösen
	}
	
//	public void max(List<? extends Number> list) throws RException
//	{
//		sendCmd("max(" + resolveList(list) + ")");
//	}
	

	/**
	 * Schließt sowohl alle offenen Ressourcen, als auch den ausgeführten R-Prozess.
	 * Kehrt zurück, wenn der Prozess beendet wurde.
	 * @throws InterruptedException
	 * @throws RException 
	 * @throws IOException falls der Prozess bereits beendet sein sollte
	 */
	@Override
	public void close() throws InterruptedException, RException
	{
		//Gebe Befehl zum Schließen der Anwendung
		sendCmd("q()");
		//Warten bis Prozess terminiert hat
		proc.waitFor();
		System.out.println("Beendet. Exitvalue: " + proc.exitValue());
				
		try
		{
			errRd.close();
			outRd.close();
			pWr.close();
		} 
		catch (IOException e)
		{
			//kann nicht sinnvoll behandelt werden, ignorieren
//			throw new RException(e);
		}
		
//		//kill, falls noch nicht erfolgt
//		proc.destroy();
	}

	@Override
	public boolean isAlive()
	{
		// TODO Auto-generated method stub
		return outRd != null;
	}

	/**
	 * Sendet den übergebenen Befehl sofort an die Konsole. Fügt einen Zeilenumbruch an. 
	 * @param cmd der auszuführende Befehl
	 * @throws IOException falls bei der Übermittlung zum Prozess ein Fehler auftritt
	 */
	@Override	
	public RObject sendCmd(String cmd) throws RException
	{
		try
		{
			pWr.println(cmd);
			pWr.flush();
			//Eingabe drin, auf Ausgabe horchen
			while(outRd.ready())
			{
				String readLine = outRd.readLine();
				System.out.println(readLine);
			}	
		}
		catch (IOException e) 
		{
			throw new RException(e);
		}
		
		return null;
	}

	@Override
	public RObject sendCmd(RCommand cmd)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStatus()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAllVars()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
