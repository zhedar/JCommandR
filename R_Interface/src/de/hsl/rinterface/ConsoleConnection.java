package de.hsl.rinterface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.hsl.rinterface.commands.RCommand;
import de.hsl.rinterface.exception.RException;
import de.hsl.rinterface.objects.RObject;
import de.hsl.rinterface.objects.RReference;
import de.hsl.rinterface.objects.RValue;
import de.hsl.rinterface.objects.RVector;
import de.hsl.rinterface.utils.RUtils;

/**
 * Konkrete Implementierung einer {@link Connection}, die Zugriff
 * auf einen R-Prozess bietet, basierend auf Konsolenein-/ausgaben.
 * @author pgruhn
 */
public class ConsoleConnection implements Connection
{
	private static Logger log =  Logger.getLogger("de.hsl.rinterface");
	
	//speichern für späteren Neuaufbau der Verbindung
	private List<String> pathAndArgs;
	private ProcHandlerThread procThread;
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
	
	/**
	 * Baut eine Verbindung zum angegebenen R-Prozess auf. Blockt, bis der Prozess gestartet
	 * und fertig initialisiert wurde. Sämtliche Streams sind danach leer und arbeitsbereit.
	 * @param path der Pfad zur R-Binary
	 * @param args Liste von Argumenten, die zum Start des Prozesses benutzt werden sollen
	 * @throws IOException
	 * @throws RException
	 */
	public ConsoleConnection(String path, List<String> args) throws IOException, RException
	{
		//Pfad und Argumente zusammenstecken
		List<String> pathAndArgs = new ArrayList<>();
			pathAndArgs.add(path);
			pathAndArgs.addAll(args);
		this.pathAndArgs = pathAndArgs;
		initCon();
	}
	
	/**
	 * Baut eine Verbindung zu einem R-Prozess auf, dessen Pfad in der path.properties
	 * unter dem Schlüssel "path" festgelegt ist. Blockt, bis der Prozess gestartet
	 * und fertig initialisiert wurde. Sämtliche Streams sind danach leer und arbeitsbereit.
	 * @param args Liste von Argumenten, die zum Start des Prozesses benutzt werden sollen
	 * @throws IOException
	 * @throws RException
	 */
	public ConsoleConnection(List<String> args) throws IOException, RException
	{
		this(RCONSTANTS.PATH, args);
	}
	
	/**
	 * Baut eine Verbindung zu einem R-Prozess auf, dessen Pfad in der path.properties
	 * unter dem Schlüssel "path" festgelegt ist. Der Prozess wird mit dem Kommandozeilen-
	 * argument "--no-save" gestartet. Blockt, bis der Prozess gestartet und fertig 
	 * initialisiert wurde. Sämtliche Streams sind danach leer und arbeitsbereit.
	 * @throws IOException
	 * @throws RException
	 */
	public ConsoleConnection() throws IOException, RException
	{
		this(provideStandardArgs());
	}

	@Override
	public boolean isAlive()
	{
		return procThread.isRunning();
	}

	@Override	
	public RObject sendCmd(String cmd) throws RException
	{	
		return RParser.construct(sendCmdRaw(RCONSTANTS.NAME_TMP_VAR + " <- " + cmd + ";" + RCONSTANTS.NAME_TMP_VAR), this);
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
			String tryCmd = RUtils.splitCommand(cmd);
			log.info("Sende Kommando: " + tryCmd);
			String outputStr = "";
			procThread.getpWr().println(tryCmd);
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
		procThread.getpWr().println(RUtils.splitCommand(cmd));
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

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllVars() throws RException
	{
		RObject ro = sendCmd("ls()");
		switch(ro.getType())
		{	
			case VALUE:
				List<String> vars = new ArrayList<>();
				
				RValue<String> var = (RValue<String>) ro; 
				if(!var.toString().equals("character(0)"))
					vars.add(var.toString());
				return vars;
			case VECTOR:
				return (RVector<String>) ro;
			default:
				throw new IllegalArgumentException("Liste von Variablen weder Value, noch Vector");
		}
	}

		/**
		 * Schlie�t sowohl alle offenen Ressourcen, als auch den ausgef�hrten R-Prozess.
		 * Kehrt zur�ck, wenn der Prozess beendet wurde.
		 * @throws IOException falls der Prozess bereits beendet sein sollte
		 */
	@Override
	public void close() {
		// Gebe Befehl zum Schlie�en der Anwendung
		try
		{	//mögliche Fehler auslesen und loggen
			processErrors();
		}
		catch(Exception ex)
		{
			log.warning("Fehler beim Schließen der Verbindung ausgelesen: " + ex.getMessage());
		}
		
		try {
			processErrors();
			sendCmdVoid("q()");
			
			procThread.getErrRd().close();
			procThread.getOutRd().close();
			procThread.getpWr().close();
		}
		catch (Exception e)
		{
			log.warning("Exception in close: " + e.getMessage() );
		}
		
		final Timer killTimer = new Timer();
		killTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				if(!procThread.isInterrupted())
					procThread.interrupt();
				killTimer.cancel();
			}
		}, RCONSTANTS.CLOSE_TIMEOUT);
	}

	//Hilfsmethoden
	private void blockTillNextAnswer() throws RException
	{
		blockTillNextAnswer(procThread.getOutRd());
	}
	
	private void blockTillNextAnswer(BufferedReader reader) throws RException {
		int tryCount = 0;
		try {
			while (!reader.ready())
			{
				if(tryCount++== RCONSTANTS.TRIES_TILL_FAIL)
				{	//mögliche Fehler auslesen, die dazu geführt haben könnten
					processErrors("Timeout beim Warten auf eine Antwort und Fehler aufgetreten: ");
					//keine Fehler ausgelesen, werfe Exception
					throw new RException("Timeout beim Warten auf eine Antwort.");
				}
					
				Thread.sleep(RCONSTANTS.TRY_IDLE_TIME);
			}
				
		} catch (InterruptedException e) {
			throw new RException(
					"Thread wurde beim Warten auf die Antwort unterbrochen.", e);
		} catch (IOException e) {
			throw new RException("Fehler beim Warten auf die Antwort.", e);
		}
	}
	
	
//	public static String getTempRefName() {
//		return tempRefName;
//	}

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
		
		sendCmdRaw("options(echo=FALSE)");
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
	
	/**
	 * Thread, der das Prozess-Objekt und sämtliche Streams darauf kapselt.<br>
	 * Wenn er unterbrochen wird und der Prozess noch läuft, wird er beendet.
	 * @author pgruhn
	 */
	private class ProcHandlerThread extends Thread
	{
		private Process proc;
		private BufferedReader errRd, outRd;
		private PrintWriter pWr;
		private boolean isRunning;
		
		/**
		 * Initialisiert den R-Prozess und legt die benötigten
		 * Stream-Ressourcen für ihn an.
		 * @throws IOException
		 */
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
		
		/**
		 * Liefert den Errorausgabestrom des Prozesses.
		 * @return {@link BufferedReader} auf stderr
		 */
		public BufferedReader getErrRd() {
			return errRd;
		}
		
		/**
		 * Liefert den Standardausgabestrom des Prozesses.
		 * @return {@link BufferedReader} auf stdout
		 */
		public BufferedReader getOutRd() {
			return outRd;
		}
		
		/**
		 * Liefert den Eingabestream des Prozesses.
		 * @return {@link PrintWriter} auf stdin
		 */
		public PrintWriter getpWr() {
			return pWr;
		}

		/**
		 * Ermittelt den aktuellen Status des Prozesses.
		 * @return true, wenn der Prozess noch läuft
		 */
		public boolean isRunning() {
			return isRunning;
		}
	}
}
