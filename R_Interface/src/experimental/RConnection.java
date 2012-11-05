package experimental;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class RConnection
{
	private Process proc;
	private final BufferedReader errRd, outRd;
	private final BufferedWriter inWr;

	public RConnection(String path, List<String> args) throws IOException
	{
		//"c:\\r\\R-2.15.0\\bin\\x64\\r.exe"
		List<String> pathAndArgs = new ArrayList<>();
			pathAndArgs.add(path);
			pathAndArgs.addAll(args);
		
		proc = new ProcessBuilder(pathAndArgs).start();
		
		errRd = new BufferedReader(new InputStreamReader(
				proc.getErrorStream()));
		outRd = new BufferedReader(new InputStreamReader(
				proc.getInputStream()));
		inWr = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(
					proc.getOutputStream())));
	}

	/**
	 * Schließt sowohl alle offenen Ressourcen, als auch den ausgeführten R-Prozess.
	 * Kehrt zurück, wenn der Prozess beendet wurde.
	 * @throws InterruptedException
	 * @throws IOException falls der Prozess bereits beendet sein sollte
	 */
	public void closeConnection() throws InterruptedException, IOException
	{
		//Gebe Befehl zum Schlie�en der Anwendung
		sendCommand("q()");
		//Warten bis Prozess terminiert hat
		proc.waitFor();
		System.out.println(proc.exitValue());
		
		errRd.close();
		outRd.close();
		inWr.close();
		//kill, falls noch nicht erfolgt
		proc.destroy();
	}
	
	/**
	 * Sendet den übergebenen Befehl sofort an die Konsole. Fügt einen Zeilenumbruch an. 
	 * @param cmd der auszuführende Befehl
	 * @throws IOException falls bei der Übermittlung zum Prozess ein Fehler auftritt
	 */
	public void sendCommand(String cmd) throws IOException
	{
		inWr.write(cmd);
		inWr.newLine();
		inWr.flush();
	}
	
	public void testSth() throws IOException
	{
		sendCommand("");
	}
	
	public void max(List<? extends Number> list) throws IOException
	{
		sendCommand("max(" + resolveList(list) + ")");
	}
	
	public String resolveList(List<? extends Number> list)
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
	
	public static void main(String[] args)
	{
		int count = 0;
		List<Integer> list = new ArrayList<>();
			list.add(2);
			list.add(354);
			list.add(1);
			list.add(5);
			list.add(-5);
		String listCmd = "c(";
		
		for(Number n : list)
		{
			if(++count != list.size())
				listCmd += n.toString() + ", ";
			else
				listCmd += n.toString() + ")";
		}
	
		System.out.println(listCmd);
	}

}
