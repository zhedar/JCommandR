package de.hsl.rinterface.commands;

import de.hsl.rinterface.objects.RObject;
import de.hsl.rinterface.objects.RVector;

/***********************************************************************
 * Module:  RDensity.java
 * Author:  Peggy Kübe
 * Purpose: Defines the Class RDensity
 ***********************************************************************/
/**
 * Density:density(x, bw = "nrd0", adjust = 1,
        	kernel = c("gaussian", "epanechnikov", "rectangular",
                   "triangular", "biweight",
                   "cosine", "optcosine"),
        	weights = NULL, window = kernel, width, give.Rkern = FALSE,
        	n = 512, from, to, cut = 3, na.rm = FALSE, ...)
 * x - the data from which the estimate is to be computed.
 * bw - the smoothing bandwidth to be used.
 * The kernels are scaled such that this is the standard deviation of the smoothing kernel.
 * (Note this differs from the reference books cited below, and from S-PLUS.) 
 * bw can also be a character string giving a rule to choose the bandwidth. See bw.nrd.
 * The default, "nrd0", has remained the default for historical and compatibility reasons,
 * rather than as a general recommendation, where e.g., "SJ" would rather fit, see also V&R (2002). 
 * The specified (or computed) value of bw is multiplied by adjust. 
 * adjust - the bandwidth used is actually adjust*bw.
 * This makes it easy to specify values like �half the default� bandwidth.
 * kernel, window - a character string giving the smoothing kernel to be used.
 * This must be one of "gaussian", "rectangular", "triangular", "epanechnikov", "biweight", "cosine"
 * or "optcosine", with default "gaussian", and may be abbreviated to a unique prefix (single letter). 
 * "cosine" is smoother than "optcosine", which is the usual �cosine� kernel in the literature and almost MSE-efficient.
 * However, "cosine" is the version used by S. 
 * weights - numeric vector of non-negative observation weights, hence of same length as x.
 * The default NULL is equivalent to weights = rep(1/nx, nx) where nx is the length of (the finite entries of) x[].
 * width - this exists for compatibility with S; if given, and bw is not, will set bw to width if this is a character string,
 * or to a kernel-dependent multiple of width if this is numeric.
 * give.Rkern - logical; if true, no density is estimated, and the �canonical bandwidth� of the chosen kernel is returned instead.
 * n - the number of equally spaced points at which the density is to be estimated.
 * When n > 512, it is rounded up to a power of 2 during the calculations (as fft is used) and the final result is interpolated by approx.
 * So it almost always makes sense to specify n as a power of two. 
 * from,to - the left and right-most points of the grid at which the density is to be estimated; the defaults are cut * bw outside of range(x).
 * cut - by default, the values of from and to are cut bandwidths beyond the extremes of the data.
 * This allows the estimated density to drop to approximately zero at the extremes.
 * na.rm - logical; if TRUE, missing values are removed from x. If FALSE any missing values cause an error.
 */

public class RDensity implements RCommand {

	private RObject x;
	private String bw;
	private int adjust;
	private String kernel;
	private RVector<Double> weights;
	private String window;
	private String width;
	private String give_Rkern;
	private int n;
	private double from;
	private double to;
	private double cut;
	private String na_rm;
	
	@Override
	public String prepareForSending() {
		return "density(" + x.toRString() + 
				(bw != null ? ", bw = " + bw : "")+
				(adjust != 0 ? ", adjust = " + adjust : "")+
				(kernel != null ? ", kernel = \"" + kernel + "\"" : "")+
				(weights != null ? ", weights = " + weights : "")+
				(window != null ? ", window = \"" + window + "\"" : "")+
				(width != null ? ", width = " + width : "")+
				(give_Rkern != null ? ", give.Rkern = \"" + give_Rkern + "\"" : "")+
				(n != 0 ? ", n = " + n : "")+
				(from != 0.0d ? ", from = " + from : "")+
				(to != 0.0d ? ", to = " + to : "")+
				(cut != 0.0d ? ", cut = " + cut : "")+
				(na_rm != null ? ", na.rm = \"" + na_rm + "\"" : "")
				 +")";
	}
	
	public RDensity(RObject x) {
		this.x = x;
	}

	public RObject getX() {
		return x;
	}

	public void setX(RObject x) {
		this.x = x;
	}

	public String getBw() {
		return bw;
	}

	public void setBw(String bw) {
		this.bw = bw;
	}

	public int getAdjust() {
		return adjust;
	}

	public void setAdjust(int adjust) {
		this.adjust = adjust;
	}

	public String getKernel() {
		return kernel;
	}

	public void setKernel(String kernel) {
		this.kernel = kernel;
	}

	public RVector<Double> getWeights() {
		return weights;
	}

	public void setWeight(RVector<Double> weights) {
		this.weights = weights;
	}

	public String getWindow() {
		return window;
	}

	public void setWindow(String window) {
		this.window = window;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getGive_Rkern() {
		return give_Rkern;
	}

	public void setGive_Rkern(String give_Rkern) {
		this.give_Rkern = give_Rkern.toUpperCase();
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public double getFrom() {
		return from;
	}

	public void setFrom(double from) {
		this.from = from;
	}

	public double getTo() {
		return to;
	}

	public void setTo(double to) {
		this.to = to;
	}

	public double getCut() {
		return cut;
	}

	public void setCut(double cut) {
		this.cut = cut;
	}

	public String getNa_rm() {
		return na_rm;
	}

	public void setNa_rm(String na_rm) {
		this.na_rm = na_rm.toUpperCase();
	}
}
