package de.hsl.rinterface.commands;

import de.hsl.rinterface.objects.RObject;

/***********************************************************************
 * Module:  RVar.java
 * Author:  Peggy Kübe
 * Purpose: Defines the Class RVar
 ***********************************************************************/
/** Varianz: var(x, y = NULL, na.rm = FALSE, use)
 * x - a numeric Vector, matrix or data frame.
 * y - NULL (default) or a vector, matrix or data frame with compatible dimensions to x.
 * The default is equivalent to y = x (but more efficient).
 * na.rm - logical. Should missing values be removed?
 * use - an optional character string giving a method for computing covariances in the presence of missing values.
 * This must be (an abbreviation of) one of the strings "everything", "all.obs", "complete.obs", "na.or.complete", or "pairwise.complete.obs".
 * 
 * Beispiel:
 * var(1:10)# 9.166667
 * var(1:5,1:5)# 2.5
 */
public class RVar implements RCommand{

	private RObject x;
	private RObject y;
	private String na_rm;
	private String use;
	
	public RVar(RObject x) {
		this.x = x;
	}

	public RObject getX() {
		return x;
	}

	public void setX(RObject x) {
		this.x = x;
	}

	public RObject getY() {
		return y;
	}

	public void setY(RObject y) {
		this.y = y;
	}

	public String getNa_rm() {
		return na_rm;
	}

	public void setNa_rm(String na_rm) {
		this.na_rm = na_rm;
	}

	public String getUse() {
		return use;
	}

	public void setUse(String use) {
		this.use = use;
	}

	@Override
	public String prepareForSending() {
		return "var(" + x.toRString() + 
				(y !=null ? ", y = " + y.toRString() : "") +
				(na_rm !=null ? ", na.rm = " + na_rm : "") +
				(use !=null ? ", use = \"" + use + "\"" : "")  +")";
	}
}
