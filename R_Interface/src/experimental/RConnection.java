package experimental;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import de.hsl.rinterface.Connection;
import de.hsl.rinterface.commands.RCommand;
import de.hsl.rinterface.exception.RException;
import de.hsl.rinterface.objects.RObject;

public class RConnection implements Connection
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
		//Blocke bis zum Eintreffen der Willkommensnachricht
		outRd.readLine();
		while(outRd.ready())
		{	//Verwerfen
			outRd.readLine();
		}	
	}
	
	public void max(List<? extends Number> list) throws RException
	{
		sendCmd("max(" + resolveList(list) + ")");
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
		System.out.println(proc.exitValue());
				
		try
		{
			errRd.close();
			outRd.close();
			inWr.close();
		} 
		catch (IOException e)
		{
			throw new RException(e);
		}
		//kill, falls noch nicht erfolgt
		proc.destroy();
	}

	@Override
	public boolean isAlive()
	{
		// TODO Auto-generated method stub
		return outRd != null;
	}

	@Override
	/**
	 * Sendet den übergebenen Befehl sofort an die Konsole. Fügt einen Zeilenumbruch an. 
	 * @param cmd der auszuführende Befehl
	 * @throws IOException falls bei der Übermittlung zum Prozess ein Fehler auftritt
	 */
		
	public RObject sendCmd(String cmd) throws RException
	{
		try
		{
			PrintWriter pw = new PrintWriter(inWr);
			pw.println(cmd);
			pw.flush();
//			inWr.write(cmd);
//			inWr.newLine();
//			inWr.flush();
			//Eingabe drin, auf Ausgabe horchen
			System.out.println("gesendet");
			while(outRd.ready())
			{
				System.out.println("lese");
				String readLine = outRd.readLine();
				System.out.println(readLine);
				
			}	
		}
		catch (IOException e) 
		{e.printStackTrace();
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
