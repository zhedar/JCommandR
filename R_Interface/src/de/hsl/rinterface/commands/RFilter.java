package de.hsl.rinterface.commands;

import de.hsl.rinterface.objects.RObject;

/***********************************************************************
 * Module:  RFilter.java
 * Author:  Peggy Kübe
 * Purpose: Defines the Class RFilter
 ***********************************************************************/
/**
 * filter(x, filter, method = c("convolution", "recursive"),
       sides = 2, circular = FALSE, init)
 * x - a univariate or multivariate time series.
 * filter - a vector of filter coefficients in reverse time order (as for AR or MA coefficients).
 * method - Either "convolution" or "recursive" (and can be abbreviated).
 * If "convolution" a moving average is used: if "recursive" an autoregression is used.
 * sides - for convolution filters only. If sides=1 the filter coefficients are for past values only;
 * if sides=2 they are centred around lag 0. In this case the length of the filter should be odd, but if it is even, more of the filter is forward in time than backward.
 * circular - for convolution filters only. If TRUE, wrap the filter around the ends of the series, otherwise assume external values are missing (NA).
 * init - for recursive filters only. Specifies the initial values of the time series just prior to the start value, in reverse time order. The default is a set of zeros.  
 *
 *Beispiel:
 * x <- 1:100
 * filter(x, rep(1, 3))
 * filter(x, rep(1, 3), sides = 1)
 * filter(x, rep(1, 3), sides = 1, circular = TRUE)
 * filter(presidents, rep(1,3))
 * y<-c(1,2,3)
 * filter(y,2)
 */
public class RFilter implements RCommand{

	private RObject x;
	private RObject filter;
	private String method;
	private int sides;
	private String circular;
	private String init = "init";
	
	public RFilter(RObject x, RObject filter) {
		this.x = x;
		this.filter = filter;
	}

	public RObject getX() {
		return x;
	}

	public void setX(RObject x) {
		this.x = x;
	}

	public RObject getFilter() {
		return filter;
	}

	public void setFilter(RObject filter) {
		this.filter = filter;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public int getSides() {
		return sides;
	}

	public void setSides(int sides) {
		this.sides = sides;
	}

	public String getCircular() {
		return circular;
	}

	public void setCircular(String circular) {
		this.circular = circular.toUpperCase();
	}

	public String getInit() {
		return init;
	}

	public void setInit(String init) {
		this.init = init;
	}

	@Override
	public String prepareForSending() {
		return "filter(" + x.toRString() + " , " + filter.toRString()+
				(method!=null ? ", method = \"" + method+ "\"" : "")+
				(sides != 0 ? ", sides = " + sides : "")+
				(circular!=null ? ", circular = " + circular : "")+
				(init!=null ?  ", "+init : "")
				 +")";
	}
}