package de.hsl.rinterface;

/***********************************************************************
 * Module:  Connection.java
 * Author:  tobo1987
 * Purpose: Defines the Interface Connection
 ***********************************************************************/

import java.io.Closeable;
import java.io.File;
import java.util.List;

import de.hsl.rinterface.commands.RCommand;
import de.hsl.rinterface.exception.RException;
import de.hsl.rinterface.objects.RObject;
import de.hsl.rinterface.objects.RReference;

/**
 * @pdOid e6210a27-8e1e-4a76-bcad-337c166cb6c3 
 * 		Schnittstelle, die Verbindungen
 *      erfüllen müssen, um eine Verbindung zu einer R-Laufzeitumgebung
 *      herzustellen.
 */
public interface Connection extends Closeable, AutoCloseable
{
	/**
	 * Sendet einen Befehl an den R-Prozess und blockt
	 * bis eine Antwort eintrifft.<br> Befehle sollten einzelne
	 * Anweisungen sein, da es sonst Probleme bei der Auswertung
	 * geben kann.
	 * @param cmd der zu sendende Befehl
	 * @throws RException
	 * @pdOid 22f909e0-bb81-4af9-8822-9463eee5e9fd
	 */
	RObject sendCmd(String cmd) throws RException;

	/**
	 * * Sendet einen vorgefertigten Befehl({@link RCommand}) an den R-Prozess und blockt
	 * bis eine Antwort eintrifft.
	 * @param cmd
	 * @throws RException
	 * @pdOid 29da57f2-9615-46b9-bd33-8931bbaa23c6
	 */
	RObject sendCmd(RCommand cmd) throws RException;
	
	/**
	 * Sendet einen Befehl an den R-Prozess und blockt
	 * bis eine Antwort eintrifft. Verarbeitet das Ergebnis
	 * nicht, sondern gibt es nur zurück.
	 * @param cmd der zu sendende Befehl
	 * @return Ergebnisausgabe des R-Prozesses, z.B. "[1] 0.0034"
	 * @throws RException
	 */
	String sendCmdRaw(String cmd) throws RException;
	
	/**
	 * Sendet vorgefertigten Befehl({@link RCommand}) an den 
	 * R-Prozess und blockt bis eine Antwort eintrifft.
	 * Verarbeitet das Ergebnis nicht, sondern gibt es nur zurück.
	 * @param cmd der zu sendende Befehl
	 * @return Ergebnisausgabe des R-Prozesses, z.B. "[1] 0.0034"
	 * @throws RException
	 */
	String sendCmdRaw(RCommand cmd) throws RException;
	/**
	 * Sendet einen Befehl an den R-Prozess, ohne zu blocken oder
	 * eine Antwort zurückzugeben.<br> Sollte nur verwendet werden, wenn
	 * keine Antwort erwartet wird, da diese sonst fälschlicherweise
	 * der nächste Anfrage zugeteilt werden kann.
	 * @param cmd der zu sendende Befehl
	 * @throws RException
	 */
	void sendCmdVoid(String cmd) throws RException;
	/**
	 * Sendet einen vorgefertigten Befehl({@link RCommand}) an den R-Prozess, 
	 * ohne zu blocken oder eine Antwort zurückzugeben.<br>
	 * Sollte nur verwendet werden, wenn
	 * keine Antwort erwartet wird, da diese sonst fälschlicherweise
	 * der nächste Anfrage zugeteilt werden kann.
	 * @param cmd der zu sendende Befehl
	 * @throws RException
	 */
	void sendCmdVoid(RCommand cmd) throws RException;
	
	/**
	 * Sendet einen Befehl an den R-Prozess,
	 * speichert ihn im aktuellen Workspace und liefert eine {@link RReference}
	 * auf ihn zurück. Blockt nicht, bis die Variable gespeichert wurde.
	 * @param cmd der zu sendende Befehl
	 * @param name der Name der Varibalen, in der das Ergebnis gespeichert werden soll
	 * @return {@link RReference} auf name
	 * @throws RException
	 */
	RReference sendCmd(String cmd, String name) throws RException;
	/**
	 * Sendet einen vorgefertigten Befehl({@link RCommand}) an den R-Prozess,
	 * speichert ihn im aktuellen Workspace und liefert eine {@link RReference}
	 * auf ihn zurück. Blockt nicht, bis die Variable gespeichert wurde.
	 * @param cmd der zu sendende Befehl
	 * @param name der Name der Varibalen, in der das Ergebnis gespeichert werden soll
	 * @return {@link RReference} auf name
	 * @throws RException
	 */
	RReference sendCmd(RCommand cmd, String name) throws RException;

	
	/**
	 * Lädt ein gespeichertes Objekt aus dem aktuellen Workspace.
	 * @param name der Name, unter dem es gepsichert wurde
	 * @return geladenes {@link RObject}
	 * @throws RException
	 */
	RObject loadSavedObject(String name) throws RException;
	/**
	 * Speichert ein {@link RObject} im aktuellen Workspace ab.
	 * @param toSave das zu speichernde Objekt
	 * @param name der Name unter dem es gespeichert werden soll
	 * @throws RException falls ein Problem mit der Verbindung auftrittdf
	 */
	void saveObject(RObject toSave, String name) throws RException;

	
	/** @pdOid fe0ba04f-ad7a-43b1-be41-6fbfc377bcf7
	 * Liefert eine Liste von allen Variablen zurück, die im momentanen Workspace gespeichert sind.
	 * @return {@link List} aller Namen
	 * @throws RException */
	List<String> getAllVars() throws RException;

	/** @pdOid e52857a7-cc5b-4beb-8ca8-10a178dd8d7e 
	 * Testet, ob der R-Prozess hinter dieser Verbindung noch läuft.
	 * @return true, wenn er noch nicht beendet wurde*/
	boolean isAlive();
//	void rebuildConnection() throws IOException, RException;

//	/**
//	 * Schließt die Verbindung zum R-Prozess und aller Streams zu ihm. Versucht
//	 * den R-Prozess zu beenden und blockt bis zur Terminierung.
//	 * 
//	 * @throws InterruptedException
//	 *             falls der aktuelle Thread von einem anderen Thread
//	 *             unterbrochen wird, während er auf das Terminieren des
//	 *             R-Prozesses wartet. Das Warten wurde dann abgebrochen.
//	 * @throws RException falls Probleme beim Schließen auftraten
//	 * @pdOid a61f12ea-6797-4ed4-9806-8ae82df2ed87
//	 */
//	void close() throws InterruptedException, RException;
	
	
	public long getCloseTimeOut();
	/**
	 * Setzt ein Timeout in Millisekunden, während dem der 
	 * R-Prozess terminieren soll.<br> Braucht er länger als erwartet,
	 * wird er zu beenden versucht.<br>Sollte bei langsameren Maschinen höher
	 * gesetzt werden, damit der Prozess ordentlich terminieren kann.
	 * @param closeTimeOut timeout in ms, default: 200 ms
	 */
	public void setCloseTimeOut(long closeTimeOut);
	
	/**
	 * Liefert den Namen der Variable zurück, in der das Ergebnis
	 * der letzten Anfrage gespeichert ist.
	 * @return name der Variable, default: <i>RInterfaceTempVar</i>
	 */
	//public static String getTempVarName();
	
	/**
	 * Setzt den namen der Variable, in die das Ergebnis
	 * der letzten Anfrage gespeichert werden soll.<br>
	 * Nur wenn unbedingt nötig ändern.
	 * @param tempVarName name der Variable, default: <i>RInterfaceTempVar</i>
	 */
	//public void setTempVarName(String tempVarName);
	
	/**
	 * Liefert den Ordner des aktuellen Workspaces zurück.
	 * @return working directory
	 */
	public File getWorkspace();
	
	/**
	 * Setzt den zu verwendenden Workspace.<br>
	 * Der vorher aktive Workspace wird <b>nicht</b> gespeichert und 
	 * mögliche ungespeicherte Variablen gehen durch den Wechsel verloren.<br>
	 * Sollte sich im angegebenen Ordner bereits eine ".RData"-Datei befinden,
	 * wird der vorhandene Workspace aus ihr geladen. Wenn nicht, wird ein 
	 * leerer Workspace erstellt.
	 * @param workspace Pfad zum Workspaceordner
	 * @throws RException
	 */
	public void changeWorkspace(File workspace) throws RException;
	/**
	 * Speichert den aktiven Workspace an einer bestimmten Stelle.
	 * @throws RException
	 * @see changeWorkspace(File)
	 */
	public void saveWorkspace(File workspace) throws RException;
	
	/**
	 * Speichert den aktiven Workspace im angegebenen Order in einer ".RData"-Datei.
	 * @throws RException
	 * @see changeWorkspace(File)
	 */
	public void saveWorkspace() throws RException;
	
	public void setTriesTillFail(int tryCount);
	
	public int getTriesTillFail();

	long getTryIdleTime();

	void setTryIdleTime(long idleTime);
}
