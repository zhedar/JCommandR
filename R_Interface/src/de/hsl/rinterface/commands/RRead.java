package de.hsl.rinterface.commands;

/***********************************************************************
 * Module: RRead.java 
 * Author: Tobias Steinmetzer
 * Purpose: Defines the Class RRead
 ***********************************************************************/

/**
 * Classe zum erstellen eines Read-Befehls f�r R.
 * Beschreibung:
 * Reads a file in table format and creates a data frame from it, 
 * with cases corresponding to lines and variables to fields in the file. 
 */

import java.io.File;
import java.io.FileNotFoundException;

import de.hsl.rinterface.RParser;

/** @pdOid 2fd80f5b-c8a9-4d29-aa6f-077440217b27 */
public class RRead implements RCommand {

	
	// TODO eventuell kann er noch nicht mit dem Pfad umgehen. Dies muss getestet werden. 
	private RReadTypes type;
	private File file;
	private boolean header;
	private String sep;
	private String quote;
	private String dec;
	private String rowName;
	private String colName;
	private String naStrings;
	private String commentChar;

	/**
	 * 
	 * @param path
	 * @throws FileNotFoundException
	 */
	public RRead(File file) throws FileNotFoundException {
		this.type = RReadTypes.TABLE;
		if (file.exists())
			this.file = file;
		else
			throw new FileNotFoundException("Die Datei wurde nicht gefunden");
	}

	public RRead(String path) throws FileNotFoundException {
		this.type = RReadTypes.TABLE;
		File file = new File(path);
		if (file.exists())
			this.file = file;
		else
			throw new FileNotFoundException("Die Datei wurde nicht gefunden");
	}

	public RReadTypes getType() {
		return type;
	}

	public void setType(RReadTypes type) {
		this.type = type;
	}

	public File getPath() {
		return file.getAbsoluteFile();
	}

	public void setPath(File path) throws FileNotFoundException {
		if (path.exists())
			this.file = path;
		else
			throw new FileNotFoundException("Die Datei wurde nicht gefunden");
	}

	public boolean isHeader() {
		return header;
	}

	public void setHeader(boolean header) {
		this.header = header;
	}

	public String getSep() {
		return sep;
	}

	public void setSep(String sep) {
		this.sep = sep;
	}

	public String getQuote() {
		return quote;
	}

	public void setQuote(String quote) {
		this.quote = quote;
	}

	public String getDec() {
		return dec;
	}

	public void setDec(String dec) {
		this.dec = dec;
	}

	public String getRowName() {
		return rowName;
	}

	public void setRowName(String rowName) {
		this.rowName = rowName;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getNaStrings() {
		return naStrings;
	}

	public void setNaStrings(String naStrings) {
		this.naStrings = naStrings;
	}

	public String getCommentChar() {
		return commentChar;
	}

	public void setCommentChar(String commentChar) {
		this.commentChar = commentChar;
	}

	@Override
	public String prepareForSending() {
		
		String path = new RParser().getRPath(file.getAbsolutePath());
		String cmd = "read." + type + "(" + path + ")";
		cmd += (header != false ? ", header=\"" + header + "\"" : "");
		cmd += (sep != null ? ", sep=\"" + sep + "\"" : "");
		cmd += (quote != null ? ", quote=\"" + quote + "\"" : "");
		cmd += (dec != null ? ", dec=\"" + dec + "\"" : "");
		cmd += (rowName != null ? "row.name=\"" + rowName + "\"" : "");
		cmd += (colName != null ? "col.name=\"" + colName + "\"" : "");
		cmd += (naStrings != null ? "na.strings=\"" + naStrings + "\"" : "");
		cmd += (commentChar != null ? "comment.char=\"" + commentChar + "\""
				: "");
		cmd += ")";
		return cmd;
	}
}
