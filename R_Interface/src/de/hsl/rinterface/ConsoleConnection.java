package de.hsl.rinterface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
import de.hsl.rinterface.utils.RUtils;

public class ConsoleConnection implements Connection
{
	private static Logger log =  Logger.getLogger("de.hsl.rinterface");
	private long closeTimeOut = 200l;
	
	//speichern für späteren Neuaufbau der Verbindung
	private List<String> pathAndArgs;
	private ProcHandlerThread procThread;
	/**temporäre variable in der letzte antwort gespeichert wird**/
	private String tempVarName;
	private File workspace;
	
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
		return procThread.isRunning();
	}

	/**
	 * Sendet den �bergebenen Befehl sofort an die Konsole. F�gt einen Zeilenumbruch an. 
	 * @param cmd der auszuf�hrende Befehl
	 * @throws RException falls bei der �bermittlung zum Prozess ein Fehler auftritt
	 */
	@Override	
	public RObject sendCmd(String cmd) throws RException
	{	
		return RParser.construct(sendCmdRaw(tempVarName + " <- " + cmd + ");(" + tempVarName), this);
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
			procThread.getpWr().println("try(" + cmd + ")");
			procThread.getpWr().flush();
			//Eingabe drin, auf Ausgabe horchen
			
			blockTillNextAnswer();
			
			processErrors("Eingabe fehlerhaft: " + cmd);
			while(procThread.getOutRd().ready())
			{
				outputStr += (char) procThread.getOutRd().read();
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

	//TODO wie fehler, die hier entstehen, auffangen?
	@Override
	public void sendCmdVoid(String cmd) throws RException {
		log.info("Sende Kommando ohne Rückgabe(void): " + cmd);
		procThread.getpWr().println("try(" + cmd + ")");
		procThread.getpWr().flush();
		try {
			processErrors();
		} catch (Exception e) {
			log.throwing("ConsoleConnection", "sendCmdVoid", e);
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
		sendCmdVoid(name + " <- " + toSave.toRString());
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
			
			procThread.getErrRd().close();
			procThread.getOutRd().close();
			procThread.getpWr().close();
		}
		catch (Exception e)
		{
			log.warning("Exception in close: " + e.getMessage() );
		}
		
		//TODO 
		final Timer killTimer = new Timer();
		killTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				if(!procThread.isInterrupted())
					procThread.interrupt();
				killTimer.cancel();
			}
		}, closeTimeOut);
	}

	//Hilfsmethoden
	private void blockTillNextAnswer() throws RException
	{
		blockTillNextAnswer(procThread.getOutRd());
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
		if(procThread.getpWr().checkError())
			throw new RException("Fehler beim Senden der Anforderung.");
		try
		{
			String errStr = msg + " ";
			while(procThread.getErrRd().ready())
			{
				char c = (char) procThread.getErrRd().read();
				errStr += c;
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
	 * @return Liste mit Wert "--no-save"
	 */
	static private List<String> provideStandardArgs()
	{
		List<String> list = new ArrayList<>();
			list.add("--no-save");
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
	
	@Override
	public long getCloseTimeOut() {
		return closeTimeOut;
	}
	
	@Override
	public void setCloseTimeOut(long closeTimeOut) {
		this.closeTimeOut = closeTimeOut;
	}
	
	@Override
	public String getTempVarName() {
		return tempVarName;
	}
	
	@Override
	public void setTempVarName(String tempVarName) {
		this.tempVarName = tempVarName;
	}

	private void initCon() throws RException, IOException 
	{
		procThread = new ProcHandlerThread();
		procThread.start();

		// Blocke bis zum Eintreffen der Willkommensnachricht
		blockTillNextAnswer();

		while (procThread.getOutRd().ready()) { // Verwerfen
			procThread.getOutRd().readLine();
		}
		// Moegliche Fehler, sowie Meldung abfangen, dass Workspace geladen
		// wurde
		String errStr = "";
		while (procThread.getErrRd().ready()) {
			char c = (char) procThread.getErrRd().read();
			errStr += c;
		}
		if (!errStr.isEmpty())
			log.warning(errStr);

		log.info("Connection aufgebaut.");
		
		sendCmdVoid("options(echo=FALSE)");
	}
	
	@Override
	public File getWorkspace() {
		return workspace;
	}
	
	@Override
	public void changeWorkspace(File workspace) throws RException {
		this.workspace = workspace;
		sendCmdVoid("setwd(\"" + RUtils.getRPath(workspace.getAbsolutePath()) + "\")");
		sendCmdVoid("rm(list=ls())");
		if(new File(workspace.getAbsolutePath() + File.separator + ".RData").exists())
			sendCmdVoid("load(\".RData\")");
	}
	
	//TODO test und evtl. abändern
	@Override
	public void saveWorkspace(File workspace) throws RException
	{
		sendCmdVoid("save.image(file=\"" + RUtils.getRPath(workspace.getAbsolutePath()) + "/.Rdata" + "\")");
	}
	
	@Override
	public void saveWorkspace() throws RException {
		sendCmdVoid("save.image()");
	}

	private class ProcHandlerThread extends Thread
	{
		private Process proc;
		private BufferedReader errRd, outRd;
		private PrintWriter pWr;
		private boolean isRunning;
		
		public ProcHandlerThread() throws IOException
		{
			proc = new ProcessBuilder(pathAndArgs).start();
			
			errRd = new BufferedReader(new InputStreamReader(
					proc.getErrorStream()));
			outRd = new BufferedReader(new InputStreamReader(
					proc.getInputStream()));
			BufferedWriter inWr = new BufferedWriter(new OutputStreamWriter(
						proc.getOutputStream()));
			pWr = new PrintWriter(inWr);
		}
			
		@Override
		public void run()
		{
			boolean closed = false;
			try
			{	//blockt bis der Prozess beendet wurde
				isRunning = true;
				proc.waitFor();
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
					proc.destroy();
				}
					
				isRunning = false;
				log.info("Verbindung beendet. Exitvalue: " + proc.exitValue());
			}
		}

		public BufferedReader getErrRd() {
			return errRd;
		}

		public BufferedReader getOutRd() {
			return outRd;
		}
		
		public PrintWriter getpWr() {
			return pWr;
		}

		public boolean isRunning() {
			return isRunning;
		}
	}
}
