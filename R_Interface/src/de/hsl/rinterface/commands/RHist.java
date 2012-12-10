package de.hsl.rinterface.commands;


import java.io.File;
import java.util.Vector;

import de.hsl.rinterface.objects.RObject;
import de.hsl.rinterface.utils.RUtils;

/***********************************************************************
 * Module:  RHist.java
 * Author:  Peggy Kübe
 * Purpose: Defines the Class RHist
 ***********************************************************************/
/**
 * Histogramm (Häufigkeitsverteilung): hist(x, breaks = "Sturges",
								     freq = NULL, probability = !freq,
								     include.lowest = TRUE, right = TRUE,
								     density = NULL, angle = 45, col = NULL, border = NULL,
								     main = paste("Histogram of" , xname),
								     xlim = range(breaks), ylim = NULL,
								     xlab = xname, ylab,
								     axes = TRUE, plot = TRUE, labels = FALSE,
								     nclass = NULL, warn.unused = TRUE, ...)
 * x - a vector of values for which the histogram is desired.
 * breaks - one of: 
 * 		a vector giving the breakpoints between histogram cells, 
 * 		a single number giving the number of cells for the histogram, 
 * 		a character string naming an algorithm to compute the number of cells (see ‘Details’), 
 * 		a function to compute the number of cells. 
 * In the last three cases the number is a suggestion only. 
 * freq - logical; if TRUE, the histogram graphic is a representation of frequencies, the counts component of the result;
 * if FALSE, probability densities, component density, are plotted (so that the histogram has a total area of one).
 * Defaults to TRUE if and only if breaks are equidistant (and probability is not specified).
 * probability - an alias for !freq, for S compatibility.
 * include.lowest - logical; if TRUE, an x[i] equal to the breaks value will be included in the first (or last, for right = FALSE) bar.
 * This will be ignored (with a warning) unless breaks is a vector.
 * right - logical; if TRUE, the histogram cells are right-closed (left open) intervals.
 * density - the density of shading lines, in lines per inch.
 * The default value of NULL means that no shading lines are drawn.
 * Non-positive values of density also inhibit the drawing of shading lines.
 * angle - the slope of shading lines, given as an angle in degrees (counter-clockwise).
 * col - a colour to be used to fill the bars. The default of NULL yields unfilled bars.
 * border - the color of the border around the bars. The default is to use the standard foreground color.
 * main, xlab, ylab - these arguments to title have useful defaults here.
 * xlim, ylim - the range of x and y values with sensible defaults.
 * Note that xlim is not used to define the histogram (breaks), but only for plotting (when plot = TRUE).
 * axes - logical. If TRUE (default), axes are draw if the plot is drawn.
 * plot - logical. If TRUE (default), a histogram is plotted, otherwise a list of breaks and counts is returned.
 * In the latter case, a warning is used if (typically graphical) arguments are specified that only apply to the plot = TRUE case.
 * labels - logical or character. Additionally draw labels on top of bars, if not FALSE; see plot.histogram.
 * nclass - numeric (integer). For S(-PLUS) compatibility only, nclass is equivalent to breaks for a scalar or character argument.
 * warn.unused - logical. If plot=FALSE and warn.unused=TRUE, a warning will be issued when graphical parameters are passed to hist.default().
 * ...	further arguments and graphical parameters passed to plot.histogram and thence to title and axis (if plot=TRUE).
 */
public class RHist implements RCommand {

	private File file;
	private String savePath;
	private RPlotSaveTypes type; 
	
	private RObject x;
	private String breaks;
	private String freq;
	private String probability;
	private String include_lowest;
	private String right;
	private Vector<Double>  density;
	private String angle;
	private String col;
	private String border;
	private String main;
	private String xlab;
	private String ylab;
	private String xlim;
	private String ylim;
	private String axes;
	private String plot;
	private String labels;
	private int nclass;
	private String warn_unused;
	
	@Override
	public String prepareForSending() {
		String path = RUtils.getRPath(file.getAbsolutePath());
		
		return  type + "(\"" + path + "\"); hist(" + x.toRString() + 
				(breaks != null ? ", breaks = " + breaks  : "") + 
				(freq != null ? ", freq = " + freq : "")+
				(probability != null ? ", probability = " + probability : "")+
				(include_lowest != null ? ", include.lowest = " + include_lowest : "") +
				(right != null ? ", right = " + right : "") +
				(density != null ? ", density = " + density : "") +
				(angle != null ? ", angle = " + angle : "") +
				(col != null ? ", col = " + col : "")+
				(border != null ? ", border = " + border : "")+
				(main != null ? ", main = \"" + main + "\"" : "")+
				(xlab != null ? ", xlab = \"" + xlab + "\"" : "")+
				(ylab != null ? ", ylab = \"" + ylab + "\"" : "")+
				(xlim != null ? ", xlim = " + xlim : "")+
				(ylim != null ? ", ylim = " + ylim : "")+
				(axes != null ? ", axes = " + axes : "")+
				(plot != null ? ", plot = " + plot : "")+
				(labels != null ? ", labels = " + labels : "")+
				(nclass != 0 ? ", nclass = " + nclass : "")+
				(warn_unused != null ? ", warn.unused = " + warn_unused : "")
				+")";
	}

	public RHist(RObject x, String savePath, RPlotSaveTypes type) {
		this.x = x;
		this.savePath = savePath;
		this.type = type;
	}

	public RObject getX() {
		return x;
	}

	public void setX(RObject x) {
		this.x = x;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public RPlotSaveTypes getType() {
		return type;
	}

	public void setType(RPlotSaveTypes type) {
		this.type = type;
	}

	public String getBreaks() {
		return breaks;
	}

	public void setBreaks(String breaks) {
		this.breaks = breaks;
	}

	public String getFreq() {
		return freq;
	}

	public void setFreq(String freq) {
		this.freq = freq.toUpperCase();
	}

	public String getProbability() {
		return probability;
	}

	public void setProbability(String probability) {
		this.probability = probability;
	}

	public String getInclude_lowest() {
		return include_lowest;
	}

	public void setInclude_lowest(String include_lowest) {
		this.include_lowest = include_lowest;
	}

	public String getRight() {
		return right;
	}

	public void setRight(String right) {
		this.right = right.toUpperCase();
	}

	public Vector<Double>  getDensity() {
		return density;
	}

	public void setDensity(Vector<Double>  density) {
		this.density = density;
	}

	public String getAngle() {
		return angle;
	}

	public void setAngle(String angle) {
		this.angle = angle;
	}

	public String getCol() {
		return col;
	}

	public void setCol(String col) {
		this.col = col;
	}

	public String getBorder() {
		return border;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	public String getMain() {
		return main;
	}

	public void setMain(String main) {
		this.main = main;
	}

	public String getXlab() {
		return xlab;
	}

	public void setXlab(String xlab) {
		this.xlab = xlab;
	}

	public String getYlab() {
		return ylab;
	}

	public void setYlab(String ylab) {
		this.ylab = ylab;
	}

	public String getXlim() {
		return xlim;
	}

	public void setXlim(String xlim) {
		this.xlim = xlim;
	}

	public String getYlim() {
		return ylim;
	}

	public void setYlim(String ylim) {
		this.ylim = ylim;
	}

	public String getAxes() {
		return axes;
	}

	public void setAxes(String axes) {
		this.axes = axes.toUpperCase();
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot.toUpperCase();
	}

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels.toUpperCase();
	}

	public int getNclass() {
		return nclass;
	}

	public void setNclass(int nclass) {
		this.nclass = nclass;
	}

	public String getWarn_unused() {
		return warn_unused;
	}

	public void setWarn_unused(String warn_unused) {
		this.warn_unused = warn_unused.toUpperCase();
	}
}
