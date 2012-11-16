package de.hsl.rinterface.commands;

import de.hsl.rinterface.objects.RObject;

/***********************************************************************
 * Module:  RCor.java
 * Author:  Peggy Kübe
 * Purpose: Defines the Class RCor
 ***********************************************************************/
/* Correlation: cor(x, y = NULL, use = "everything", method = c("pearson", "kendall", "spearman"))
 * x - a numeric vector, matrix or data frame.
 * y - NULL (default) or a vector, matrix or data frame with compatible dimensions to x. The default is equivalent to y = x (but more efficient).
 * use - an optional character string giving a method for computing covariances in the presence of missing values.
 * This must be (an abbreviation of) one of the strings "everything", "all.obs", "complete.obs", "na.or.complete", or "pairwise.complete.obs".
 * method - a character string indicating which correlation coefficient (or covariance) is to be computed.
 * One of "pearson" (default), "kendall", or "spearman", can be abbreviated.
 * 
 * cor(1:10,2:11)# == 1
 */
public class RCor implements RCommand{

	private RObject x;
	private RObject y;
	private String use;
	private String method;
	
	public RCor(RObject x, RObject y) {
		this.x = x;
		this.y = y;
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

	public String getUse() {
		return use;
	}


	public void setUse(String use) {
		this.use = use;
	}


	public String getMethod() {
		return method;
	}


	public void setMethod(String method) {
		this.method = method;
	}
	
	@Override
	public String prepareForSending() {
		return "cor(" + x.toRString() + " , " + y.toRString() +
				(use !=null ? ", use = \"" + use + "\"" : "") +
				(method !=null ? ", method = \"" + method + "\"" : "") +")";
	}

}
