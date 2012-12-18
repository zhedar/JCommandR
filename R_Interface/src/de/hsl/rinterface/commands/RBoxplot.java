package de.hsl.rinterface.commands;

import java.io.File;
import java.util.Vector;

import de.hsl.rinterface.objects.RObject;
import de.hsl.rinterface.utils.RUtils;

/***********************************************************************
 * Module:  RBoxplot.java
 * Author:  Peggy Kübe
 * Purpose: Defines the Class RBoxplot
 ***********************************************************************/
/**
 * Box-Whisker-Plot: boxplot(x, range = 1.5, width = NULL, varwidth = FALSE,
        notch = FALSE, outline = TRUE, names, plot = TRUE,
        border = par("fg"), col = NULL, log = "",
        pars = list(boxwex = 0.8, staplewex = 0.5, outwex = 0.5),
        horizontal = FALSE, add = FALSE, at = NULL)
 * x - for specifying data from which the boxplots are to be produced. Either a numeric vector,
 * or a single list containing such vectors. Additional unnamed arguments specify further data as
 * separate vectors (each corresponding to a component boxplot). NAs are allowed in the data.
 * range - this determines how far the plot whiskers extend out from the box.
 * If range is positive, the whiskers extend to the most extreme data point which is no more than range
 * times the interquartile range from the box. A value of zero causes the whiskers to extend to the data extremes.
 * width - a vector giving the relative widths of the boxes making up the plot.
 * varwidth - if varwidth is TRUE, the boxes are drawn with widths proportional to the square-roots of the number of observations in the groups.
 * notch - if notch is TRUE, a notch is drawn in each side of the boxes.
 * If the notches of two plots do not overlap this is �strong evidence� that the two medians differ (Chambers et al., 1983, p. 62).
 * See boxplot.stats for the calculations used.
 * outline - if outline is not true, the outliers are not drawn (as points whereas S+ uses lines).
 * names - group labels which will be printed under each boxplot. Can be a character vector or an expression (see plotmath).
 * boxwex - a scale factor to be applied to all boxes. When there are only a few groups, the appearance of the plot can be improved by making the boxes narrower.
 * staplewex - staple line width expansion, proportional to box width.
 * outwex - outlier line width expansion, proportional to box width.
 * plot - if TRUE (the default) then a boxplot is produced. If not, the summaries which the boxplots are based on are returned.
 * border - an optional vector of colors for the outlines of the boxplots. The values in border are recycled if the length of border is less than the number of plots.
 * col - if col is non-null it is assumed to contain colors to be used to colour the bodies of the box plots. By default they are in the background colour.
 * log - character indicating if x or y or both coordinates should be plotted in log scale.
 * pars - a list of (potentially many) more graphical parameters, e.g., boxwex or outpch; these are passed to bxp (if plot is true); for details, see there.
 * horizontal - logical indicating if the boxplots should be horizontal; default FALSE means vertical boxes.
 * add - logical, if true add boxplot to current plot.
 * at - numeric vector giving the locations where the boxplots should be drawn, particularly when add = TRUE; defaults to 1:n where n is the number of boxes.
 */
public class RBoxplot implements RCommand {

	private File file;
	private RPlotSaveTypes type;
	
	private RObject x;
	private double range;
	private Vector<Double> width;
	private String varwidth;
	private String notch;
	private String outline;
	private Vector<String> names;
	private String plot;
	private Vector<String> border;
	private Vector<String> col;
	private String log;
	private String pars;
	private double boxwex;
	private double staplewex;
	private double outwex;
	private String horizontal;
	private String add;
	private Vector<Double> at;
	
	@Override
	public String prepareForSending() {
		
		return type + "(filename=\"" + RUtils.getRPath(file.getAbsolutePath()) + "\"); boxplot(" + x.toRString() + 
				(range != 0.0d ? ", range = " + range  : "") + 
				(width != null ? ", width = " + width : "")+
				(varwidth != null ? ", varwidth = " + varwidth : "")+
				(notch != null ? ", notch = " + notch : "") +
				(outline != null ? ", outline = " + outline : "") +
				(names != null ? ", names " : "") +
				(plot != null ? ", plot = " + plot : "") +
				(border != null ? ", border = " + border : "")+
				(col != null ? ", col = " + col : "")+
				(log != null ? ", log = \"" + log + "\"" : "")+
				(pars != null ? ", pars = " + pars : "")+
				(boxwex != 0.0d ? ", boxwex = " + boxwex : "")+
				(staplewex != 0.0d ? ", staplewex = " + staplewex : "")+
				(outwex != 0.0d ? ", outwex = " + outwex : "")+
				(horizontal != null ? ", horizontal = " + horizontal : "")+
				(add != null ? ", add = " + add : "")+
				(at != null ? ", at = " + at : "")
				+")";
	}

	public RBoxplot(RObject x, File file, RPlotSaveTypes type) {
		this.x = x;
		this.file = file;
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


	public RPlotSaveTypes getType() {
		return type;
	}

	public void setType(RPlotSaveTypes type) {
		this.type = type;
	}

	public double getRange() {
		return range;
	}

	public void setRange(double range) {
		this.range = range;
	}

	public Vector<Double> getWidth() {
		return width;
	}

	public void setWidth(Vector<Double> width) {
		this.width = width;
	}

	public String getVarwidth() {
		return varwidth;
	}

	public void setVarwidth(String varwidth) {
		this.varwidth = varwidth;
	}

	public String getNotch() {
		return notch;
	}

	public void setNotch(String notch) {
		this.notch = notch;
	}

	public String getOutline() {
		return outline;
	}

	public void setOutline(String outline) {
		this.outline = outline;
	}

	public Vector<String> getNames() {
		return names;
	}

	public void setNames(Vector<String> names) {
		this.names = names;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public Vector<String> getBorder() {
		return border;
	}

	public void setBorder(Vector<String> border) {
		this.border = border;
	}

	public Vector<String> getCol() {
		return col;
	}

	public void setCol(Vector<String> col) {
		this.col = col;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public String getPars() {
		return pars;
	}

	public void setPars(String pars) {
		this.pars = pars;
	}

	public double getBoxwex() {
		return boxwex;
	}

	public void setBoxwex(double boxwex) {
		this.boxwex = boxwex;
	}

	public double getStaplewex() {
		return staplewex;
	}

	public void setStaplewex(double staplewex) {
		this.staplewex = staplewex;
	}

	public double getOutwex() {
		return outwex;
	}

	public void setOutwex(double outwex) {
		this.outwex = outwex;
	}

	public String getHorizontal() {
		return horizontal;
	}

	public void setHorizontal(String horizontal) {
		this.horizontal = horizontal;
	}

	public String getAdd() {
		return add;
	}

	public void setAdd(String add) {
		this.add = add;
	}

	public Vector<Double> getAt() {
		return at;
	}

	public void setAt(Vector<Double> at) {
		this.at = at;
	}
}
