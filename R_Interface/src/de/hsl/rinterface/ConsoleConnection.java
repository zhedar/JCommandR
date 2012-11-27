package de.hsl.rinterface;

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
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import de.hsl.rinterface.commands.RCommand;
import de.hsl.rinterface.exception.RException;
import de.hsl.rinterface.objects.RObject;
import de.hsl.rinterface.objects.RReference;

public class ConsoleConnection implements Connection
{
	//TODO evtl in thread verlagern
	private Process proc;
	private BufferedReader errRd, outRd;
	private PrintWriter pWr;
	private boolean isRunning;
	
	private static Logger log =  Logger.getLogger("de.hsl.rinterface");
	private long closeTimeOut = 200l;
	
	//speichern für späteren Neuaufbau der Verbindung
	private List<String> pathAndArgs;
	private ProcEndWatcherThread procEndWatcherThread;
	//temporäre variable in der letzte antwort gespeichert wird
	private String tempVarName;
	
	static
	{
		try {
			log.addHandler(new FileHandler("rinterface%u.log", 50000, 1, true));
			log.setLevel(Level.FINEST);
		}
		catch (Exception e) 
		{
			log.warning("Loggingfehler. Filehandler konnte nicht erstellt werden. " + e.getMessage());
		}
	}
	
	public ConsoleConnection(String path, List<String> args) throws IOException, RException
	{
		//Pfad und Argumente zusammenstecken
		List<String> pathAndArgs = new ArrayList<>();
			pathAndArgs.add(path);
			pathAndArgs.addAll(args);
		this.pathAndArgs = pathAndArgs;
		initCon();
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
		return new RParser().construct(sendCmdRaw(tempVarName + " <- " + cmd + ";" + tempVarName));
	}

	@Override
	public RObject sendCmd(RCommand cmd) throws RException
	{
		return sendCmd(cmd.prepareForSending());
	}

	@Override
	public RReference sendCmd(String cmd, String name) throws RException
	{
		sendCmdVoid(name + " <- " +cmd);
		
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
			log.info("Sende Kommando: " + cmd);
			String outputStr = "";
			pWr.println("try(" + cmd + ")");
			pWr.flush();
			//Eingabe drin, auf Ausgabe horchen
			
			blockTillNextAnswer();
			
			processErrors("Eingabe fehlerhaft: " + cmd);
			while(outRd.ready())
			{
				outputStr += (char) outRd.read();
				processErrors();
			}
			processErrors();
			
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
		log.info("Sende Kommando ohne Rückgabe(void): " + cmd);
		pWr.println(cmd);
		pWr.flush();
		try {
			processErrors();
		} catch (Exception e) {
			e.printStackTrace();
//			log.throwing(sourceClass, sourceMethod, thrown);
		}
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
		throw new NotImplementedException();
	}

		/**
		 * Schlie�t sowohl alle offenen Ressourcen, als auch den ausgef�hrten R-Prozess.
		 * Kehrt zur�ck, wenn der Prozess beendet wurde.
		 * @throws IOException falls der Prozess bereits beendet sein sollte
		 */
	@Override
	public void close() {
		// Gebe Befehl zum Schlie�en der Anwendung
		try {
			sendCmdVoid("q()");
			
			errRd.close();
			outRd.close();
			pWr.close();
		}
		catch (Exception e)
		{
			log.warning("Exception in close: " + e.getMessage() );
		}
		
		final Timer killTimer = new Timer();
		killTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				if(!procEndWatcherThread.isInterrupted())
					procEndWatcherThread.interrupt();
				killTimer.cancel();
			}
		}, closeTimeOut);
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
	
	private void processErrors(String msg) throws RException
	{
		if(pWr.checkError())
			throw new RException("Fehler beim Senden der Anforderung.");
		try
		{
			String errStr = msg + " ";
			while(errRd.ready())
			{
				char c = (char) errRd.read();
				errStr += c;
//				System.out.println("c:" + c);
			}
			if(	!errStr.isEmpty() && 
				!errStr.contains("[Vorher gesicherter Workspace wiederhergestellt]")
				&& (errStr.length() != msg.length() +1))
			{
				log.warning("Fehler ausgelesen: " + errStr);
				throw new RException(errStr);
			}
		}
		catch (IOException e)
		{
			log.throwing("ConsoleConnection", "processErrors", e);
			throw new RException("Fehler beim Fehlerauslesen. Ausführung ist möglichweise nicht mehr sicher.", e);
		}
	}
	
	private void processErrors() throws IOException, RException
	{
		processErrors("");
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
		try {
			Properties prop = new Properties(); 
			
			prop.load(new FileInputStream("path.properties"));
			
			return prop.getProperty("path");
		} catch (Exception e) {
			log.throwing("ConsoleConnection", "loadPathFromProp()", e);
			throw e;
		}
	}

//	@Override
//	public void rebuildConnection() throws IOException, RException
//	{
//		close();
//		
//		if(pathAndArgs == null)
//			pathAndArgs =provideStandardArgs();
//		
//		initCon();
//	}
	
	//TODO ins interface rein
	public long getCloseTimeOut() {
		return closeTimeOut;
	}

	public void setCloseTimeOut(long closeTimeOut) {
		this.closeTimeOut = closeTimeOut;
	}
	
	private void initCon() throws RException, IOException
	{
		//Prozess starten
				proc = new ProcessBuilder(pathAndArgs).start();
				
				errRd = new BufferedReader(new InputStreamReader(
						proc.getErrorStream()));
				outRd = new BufferedReader(new InputStreamReader(
						proc.getInputStream()));
				BufferedWriter inWr = new BufferedWriter(new OutputStreamWriter(
							proc.getOutputStream()));
				pWr = new PrintWriter(inWr);
				
				procEndWatcherThread = new ProcEndWatcherThread();
				procEndWatcherThread.start();
				
				//Blocke bis zum Eintreffen der Willkommensnachricht
				blockTillNextAnswer();
				
				while(outRd.ready())
				{	//Verwerfen
					outRd.readLine();
				}
				//Moegliche Fehler, sowie Meldung abfangen, dass Workspace geladen wurde
				String errStr = "";
				while(errRd.ready())
				{	
					char c = (char) errRd.read();
					errStr += c;
				}
				if(!errStr.isEmpty())
					log.warning(errStr);
					
				log.info("Connection aufgebaut.");
	}
	
	private class ProcEndWatcherThread extends Thread
	{
		private Process termProc = proc; //TODO defensive-copy
		
		@Override
		public void run()
		{
			boolean closed = false;
			try
			{	//blockt bis der Prozess beendet wurde
				isRunning = true;
				termProc.waitFor();
				closed = true;
				log.info("Prozess erfolgreich innerhalb der vorgegebenen Zeit geschlossen.");
			}
			catch (InterruptedException e)
			{
				Thread.currentThread().interrupt();
			}
			finally
			{
				if(!closed)
				{
					log.info("Timeout überschritten. Beende Prozess.");
					termProc.destroy();
				}
					
				//TODO könnte vorherigen start wieder überschreiben
				isRunning = false;
				log.info("Verbindung beendet. Exitvalue: " + termProc.exitValue());
			}
		}
	}
}
