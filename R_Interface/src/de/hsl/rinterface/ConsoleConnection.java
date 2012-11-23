package de.hsl.rinterface;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import de.hsl.rinterface.commands.RCommand;
import de.hsl.rinterface.exception.RException;
import de.hsl.rinterface.objects.RObject;
import de.hsl.rinterface.objects.RReference;

public class ConsoleConnection implements Connection
{
	private Process proc;
	private final BufferedReader errRd, outRd;
	private final PrintWriter pWr;
	private boolean isRunning;
	
	public ConsoleConnection(String path, List<String> args) throws IOException, RException
	{
		//Pfad und Argumente zusammenstecken
		List<String> pathAndArgs = new ArrayList<>();
			pathAndArgs.add(path);
			pathAndArgs.addAll(args);
		//Prozess starten
		proc = new ProcessBuilder(pathAndArgs).start();
		isRunning = true;
		
		Thread procEndWatcherThread = new Thread(new Runnable() {
			
			@Override
			public void run()
			{
				try
				{	//blockt bis der Prozess beendet wurde
					proc.waitFor();
					isRunning = false;
					//TODO event zur Benachrichtigung einbauen
				}
				catch (InterruptedException e)
				{
					//sollte nicht passieren
				}
			}
		});
		procEndWatcherThread.start();
		
		errRd = new BufferedReader(new InputStreamReader(
				proc.getErrorStream()));
		outRd = new BufferedReader(new InputStreamReader(
				proc.getInputStream()));
		BufferedWriter inWr = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(
					proc.getOutputStream())));
		pWr = new PrintWriter(inWr);
		
		//Blocke bis zum Eintreffen der Willkommensnachricht
		blockTillNextAnswer();
		
		while(outRd.ready())
		{	//Verwerfen
			outRd.readLine();
		}
		//Moegliche Fehler, sowie Meldung abfangen, dass Workspace geladen wurde
		while(errRd.ready())
		{	//TODO logging
			System.err.println(errRd.readLine());
		}
		
		//Initialisierung fertig, block loesen
	}
	
	public ConsoleConnection(List<String> args) throws IOException, RException
	{
		this(loadPathFromProp(), args);
	}
	
	public ConsoleConnection() throws IOException, RException
	{
		this(provideStandardArgs());
	}

	@Override
	public boolean isAlive()
	{
		return isRunning;
	}

	/**
	 * Sendet den �bergebenen Befehl sofort an die Konsole. F�gt einen Zeilenumbruch an. 
	 * @param cmd der auszuf�hrende Befehl
	 * @throws RException falls bei der �bermittlung zum Prozess ein Fehler auftritt
	 */
	@Override	
	public RObject sendCmd(String cmd) throws RException
	{	
		return new RParser().construct(sendCmdRaw(cmd));
	}

	@Override
	public RObject sendCmd(RCommand cmd) throws RException
	{
		return sendCmd(cmd.prepareForSending());
	}

	@Override
	public RReference sendCmd(String cmd, String name) throws RException
	{
		sendCmdVoid(name + "<-" +cmd);
		
		return new RReference(name);
	}

	@Override
	public RReference sendCmd(RCommand cmd, String name) throws RException {
		return sendCmd(cmd.prepareForSending(), name);
	}

	@Override
	public String sendCmdRaw(String cmd) throws RException 
	{
		try
		{
			String outputStr = "";
			pWr.println(cmd);
			pWr.flush();
			//Eingabe drin, auf Ausgabe horchen
			
			blockTillNextAnswer();
			
			while(errRd.ready()) //TODO fehler verarbeiten
				System.err.println(errRd.readLine());
			while(outRd.ready())
			{
				outputStr += (char) outRd.read();
				while(errRd.ready())
					System.err.println(errRd.readLine());
			}
			
			return outputStr;
		}
		catch (IOException e) 
		{
			throw new RException(e);
		}
	}

	@Override
	public String sendCmdRaw(RCommand cmd) throws RException {
		return sendCmdRaw(cmd.prepareForSending());
	}

	@Override
	public void sendCmdVoid(String cmd) throws RException {
		pWr.println(cmd);
		pWr.flush();
		//Eingabe drin
	}

	@Override
	public void sendCmdVoid(RCommand cmd) throws RException {
		sendCmdVoid(cmd.prepareForSending());
	}

	@Override
	public RObject loadSavedObject(String name) throws RException
	{
		return sendCmd(name);
	}

	@Override
	public void saveObject(RObject toSave, String name) throws RException
	{
		sendCmdVoid(name + "<-" + toSave.toRString());
	}

	@Override
	public List<String> getAllVars()
	{
		// TODO generischer Parser w�re gut, da ls() einen Vektor von Strings zur�ckliefert
		return null;
	}

	//	public void max(List<? extends Number> list) throws RException
	//	{
	//		sendCmd("max(" + resolveList(list) + ")");
	//	}
		
	
		/**
		 * Schlie�t sowohl alle offenen Ressourcen, als auch den ausgef�hrten R-Prozess.
		 * Kehrt zur�ck, wenn der Prozess beendet wurde.
		 * @throws IOException falls der Prozess bereits beendet sein sollte
		 */
	@Override
	public void close() { //TODO in anderen Thread verschieben= nonblocking machen?
		// Gebe Befehl zum Schlie�en der Anwendung
		try {
			sendCmdVoid("q()");

			// Warten bis Prozess terminiert hat
			proc.waitFor();
			System.out.println("Beendet. Exitvalue: " + proc.exitValue());

			errRd.close();
			outRd.close();
			pWr.close();
			// //kill, falls noch nicht erfolgt
			// proc.destroy();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// kann nicht sinnvoll behandelt werden, ignorieren
			// throw new RException(e);
		}
	}

	//Hilfsmethoden
	private void blockTillNextAnswer() throws RException
	{
		blockTillNextAnswer(outRd);
	}
	
	private void blockTillNextAnswer(BufferedReader reader) throws RException {
		try {
			while (!reader.ready())
				Thread.sleep(50); // Variable machen, wegen CPU last
		} catch (InterruptedException e) {
			throw new RException(
					"Thread wurde beim Warten auf die Antwort unterbrochen.", e);
		} catch (IOException e) {
			throw new RException("Fehler beim Warten auf die Antwort.", e);
		}
	}
	
	private void checkForSendErrors() throws RException
	{
		if(pWr.checkError())
			throw new RException("Fehler beim Senden der Anforderung.");
	}
	
	/**
	 * Hilfsmethode für parameterlosen Konstruktor.
	 * @return Liste mit Wert "--save"
	 */
	static private List<String> provideStandardArgs()
	{
		List<String> list = new ArrayList<>();
			list.add("--save");
		return list;
	}
	
	static private String loadPathFromProp() throws IOException
	{
		Properties prop = new Properties(); 
		
		prop.load(new FileInputStream("path.properties"));
		
		return prop.getProperty("path");
	}
}
