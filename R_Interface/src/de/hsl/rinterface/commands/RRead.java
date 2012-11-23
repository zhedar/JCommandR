package de.hsl.rinterface.commands;

/***********************************************************************
 * Module: RRead.java 
 * Author: Tobias Steinmetzer
 * Purpose: Defines the Class RRead
 ***********************************************************************/

/**
 * Classe zum erstellen eines Read-Befehls für R.
 * Beschreibung:
 * Reads a file in table format and creates a data frame from it, 
 * with cases corresponding to lines and variables to fields in the file. 
 * 
 * Benutzung:
 * read.table(file, header = FALSE, sep = "", quote = "\"'",
 *          dec = ".", row.names, col.names,
 *          as.is = !stringsAsFactors,
 *          na.strings = "NA", colClasses = NA, nrows = -1,
 *          skip = 0, check.names = TRUE, fill = !blank.lines.skip,
 *          strip.white = FALSE, blank.lines.skip = TRUE,
 *          comment.char = "#",
 *          allowEscapes = FALSE, flush = FALSE,
 *          stringsAsFactors = default.stringsAsFactors(),
 *          fileEncoding = "", encoding = "unknown", text)
 *
 * read.csv(file, header = TRUE, sep = ",", quote="\"", dec=".",
 *         fill = TRUE, comment.char="", ...)
 *
 * read.csv2(file, header = TRUE, sep = ";", quote="\"", dec=",",
 *         fill = TRUE, comment.char="", ...)
 *
 * read.delim(file, header = TRUE, sep = "\t", quote="\"", dec=".",
 *          fill = TRUE, comment.char="", ...)
 *
 * read.delim2(file, header = TRUE, sep = "\t", quote="\"", dec=",",
 *          fill = TRUE, comment.char="", ...)
 *          
 * Parameter:
 * header - 		a logical value indicating whether the file contains the names of the variables as its first line. If missing, the value is determined from the file format: header is set to TRUE if and only if the first row contains one fewer field than the number of columns.
 * sep - 			the field separator character. Values on each line of the file are separated by this character. If sep = "" (the default for read.table) the separator is ‘white space’, that is one or more spaces, tabs, newlines or carriage returns.
 * quote - 			the set of quoting characters. To disable quoting altogether, use quote = "". See scan for the behaviour on quotes embedded in quotes. Quoting is only considered for columns read as character, which is all of them unless colClasses is specified.
 * dec - 			the character used in the file for decimal points.
 * row.names - 		a vector of row names. This can be a vector giving the actual row names, or a single number giving the column of the table which contains the row names, or character string giving the name of the table column containing the row names.
 * 					If there is a header and the first row contains one fewer field than the number of columns, the first column in the input is used for the row names. Otherwise if row.names is missing, the rows are numbered.
 * 					Using row.names = NULL forces row numbering. Missing or NULL row.names generate row names that are considered to be ‘automatic’ (and not preserved by as.matrix).
 * col.names - 		a vector of optional names for the variables. The default is to use "V" followed by the column number.
 * na.strings -		a character vector of strings which are to be interpreted as NA values. Blank fields are also considered to be missing values in logical, integer, numeric and complex fields.
 * comment.char -	character: a character vector of length one containing a single character or an empty string. Use "" to turn off the interpretation of comments altogether.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import de.hsl.rinterface.objects.RObject;
import de.hsl.rinterface.*;

/** @pdOid 2fd80f5b-c8a9-4d29-aa6f-077440217b27 */
public class RRead implements RCommand {

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

	/**
	 * Bereitet den Read-Befehl als R-Kommando vor.
	 */
	@Override
	public String prepareForSending() {
		
		String path = new RParser().getRPath(file.getAbsolutePath());	
		String cmd = "read." + type + "(\"" + path + "\"";
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
		//System.out.println(cmd);
		return cmd;
	}
}