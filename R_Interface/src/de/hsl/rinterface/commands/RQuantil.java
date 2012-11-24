package de.hsl.rinterface.commands;

import de.hsl.rinterface.objects.RObject;

/***********************************************************************
 * Module:  RQuantil.java
 * Author:  Peggy Kübe
 * Purpose: Defines the Class RQuantil
 ***********************************************************************/
/**
 * Quantil: quantile(x, probs = seq(0, 1, 0.25),
 					na.rm = FALSE, names = TRUE, type = 7, ...)
 * x - numeric vector whose sample quantiles are wanted, or
 * an object of a class for which a method has been defined (see also ‘details’).
 * NA and NaN values are not allowed in numeric vectors unless na.rm is TRUE.
 * probs - numeric vector of probabilities with values in [0,1].
 * (Values up to 2e-14 outside that range are accepted and moved to the nearby endpoint.)
 * na.rm - logical; if true, any NA and NaN's are removed from x before the quantiles are computed.
 * names - logical; if true, the result has a names attribute. Set to FALSE for speedup with many probs.
 * type - an integer between 1 and 9 selecting one of the nine quantile algorithms detailed below to be used.
 *
 * Beispiel:
 * quantile(3)
   0%  25%  50%  75% 100% 
   3    3    3    3    3 
 * quantile(3:10)
   0%   25%   50%   75%  100% 
   3.00  4.75  6.50  8.25 10.00 
 * test <-c(1,5,50,800)
 * quantile(test)
   0%   25%   50%   75%  100% 
   1.0   4.0  27.5 237.5 800.0 
 * quantile(test, 0.5)
   50% 
   27.5 
 */

public class RQuantil implements RCommand {

	private RObject x;
	private RObject probs;
	private String na_rm;
	private String names;
	private int type;
	
	public RQuantil(RObject x) {
		this.x = x;
	}
	
	public RObject getX() {
		return x;
	}

	public void setX(RObject x) {
		this.x = x;
	}

	public RObject getProbs() {
		return probs;
	}

	public void setProbs(RObject probs) {
		this.probs = probs;
	}

	public String getNa_rm() {
		return na_rm;
	}

	public void setNa_rm(String na_rm) {
		this.na_rm = na_rm;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String prepareForSending() {
		return "quantil(" + x.toRString() + 
				(probs != null ? ", probs = " + probs : "")+
				(na_rm != null ? ", na.rm = " + na_rm : "")+
				(names != null ? ", names = " + names : "")+
				(type != 0 ? ", type = " + type : "")
				 +")";
	}
}
