package de.hsl.rinterface.commands;

import java.io.File;
import java.util.Vector;

import de.hsl.rinterface.objects.RObject;
import de.hsl.rinterface.utils.RUtils;

/***********************************************************************
 * Module:  RBarplot.java
 * Author:  Peggy Kübe
 * Purpose: Defines the Class RBarplot
 ***********************************************************************/
/**
 * Balkendiagramm: barplot(height, width = 1, space = NULL,
        names.arg = NULL, legend.text = NULL, beside = FALSE,
        horiz = FALSE, density = NULL, angle = 45,
        col = NULL, border = par("fg"),
        main = NULL, sub = NULL, xlab = NULL, ylab = NULL,
        xlim = NULL, ylim = NULL, xpd = TRUE, log = "",
        axes = TRUE, axisnames = TRUE,
        cex.axis = par("cex.axis"), cex.names = par("cex.axis"),
        inside = TRUE, plot = TRUE, axis.lty = 0, offset = 0,
        add = FALSE, args.legend = NULL, ...)
 * height - either a vector or matrix of values describing the bars which make up the plot.
 * If height is a vector, the plot consists of a sequence of rectangular bars with heights given by the values in the vector.
 * If height is a matrix and beside is FALSE then each bar of the plot corresponds to a column of height,
 * with the values in the column giving the heights of stacked sub-bars making up the bar.
 * If height is a matrix and beside is TRUE, then the values in each column are juxtaposed rather than stacked.
 * width - optional vector of bar widths. Re-cycled to length the number of bars drawn.
 * Specifying a single value will have no visible effect unless xlim is specified.
 * space - the amount of space (as a fraction of the average bar width) left before each bar.
 * May be given as a single number or one number per bar. If height is a matrix and beside is TRUE,
 * space may be specified by two numbers, where the first is the space between bars in the same group,
 * and the second the space between the groups. If not given explicitly, it defaults to c(0,1)
 * if height is a matrix and beside is TRUE, and to 0.2 otherwise.
 * names.arg - a vector of names to be plotted below each bar or group of bars.
 * If this argument is omitted, then the names are taken from the names attribute of height if this is a vector,
 * or the column names if it is a matrix.
 * legend.text - a vector of text used to construct a legend for the plot, or a logical indicating whether
 * a legend should be included. This is only useful when height is a matrix.
 * In that case given legend labels should correspond to the rows of height; if legend.text is true,
 * the row names of height will be used as labels if they are non-null.
 * beside - a logical value. If FALSE, the columns of height are portrayed as stacked bars, and if TRUE the columns are portrayed as juxtaposed bars.
 * horiz - a logical value. If FALSE, the bars are drawn vertically with the first bar to the left.
 * If TRUE, the bars are drawn horizontally with the first at the bottom.
 * density - a vector giving the density of shading lines, in lines per inch, for the bars or bar components.
 * The default value of NULL means that no shading lines are drawn. Non-positive values of density also inhibit the drawing of shading lines.
 * angle - the slope of shading lines, given as an angle in degrees (counter-clockwise), for the bars or bar components.
 * col - a vector of colors for the bars or bar components. By default, grey is used if height is a vector,
 * and a gamma-corrected grey palette if height is a matrix.
 * border - the color to be used for the border of the bars. Use border = NA to omit borders.
 * If there are shading lines, border = TRUE means use the same colour for the border as for the shading lines.
 * main,sub	- overall and sub title for the plot.
 * xlab - a label for the x axis.
 * ylab - a label for the y axis.
 * xlim	- limits for the x axis.
 * ylim - limits for the y axis.
 * xpd - logical. Should bars be allowed to go outside region?
 * log - string specifying if axis scales should be logarithmic; see plot.default.
 * axes - logical. If TRUE, a vertical (or horizontal, if horiz is true) axis is drawn.
 * axisnames - logical. If TRUE, and if there are names.arg (see above), the other axis is drawn (with lty=0) and labeled.
 * cex.axis	- expansion factor for numeric axis labels.
 * cex.names - expansion factor for axis names (bar labels).
 * inside - logical. If TRUE, the lines which divide adjacent (non-stacked!) bars will be drawn.
 * Only applies when space = 0 (which it partly is when beside = TRUE).
 * plot - logical. If FALSE, nothing is plotted.
 * axis.lty	- the graphics parameter lty applied to the axis and tick marks of the categorical (default horizontal) axis.
 * Note that by default the axis is suppressed.
 * offset - a vector indicating how much the bars should be shifted relative to the x axis.
 * add - logical specifying if bars should be added to an already existing plot; defaults to FALSE.
 * args.legend - list of additional arguments to pass to legend();
 * names of the list are used as argument names. Only used if legend.text is supplied.
 * ... - arguments to be passed to/from other methods.
 * For the default method these can include further arguments (such as axes, asp and main) and
 * graphical parameters (see par) which are passed to plot.window(), title() and axis.
 */
public class RBarplot implements RCommand {

	
	private File file;
	private String savePath;
	private RPlotSaveTypes type; 
	
	private RObject height;
	private String width;
	private String space;
	private Vector<String> names_arg;
	private Vector<String> legend_text;
	private String beside;
	private String horiz;
	private Vector<Double> density;
	private String angle;
	private Vector<String> col;
	private String border;
	private String main;
	private String sub;
	private String xlab;
	private String ylab;
	private String xlim;
	private String ylim;
	private String xpd;
	private String log;
	private String axes;
	private String axisnames;
	private String cex_axis;
	private String cex_names;
	private String inside;
	private String plot;
	private int axis_lty;
	private Vector<Double> offset;
	private String add;
	private String args_legend;
	
	@Override
	public String prepareForSending() {
		String path = RUtils.getRPath(file.getAbsolutePath());
		
		return  type + "(\"" + path + "\"); barplot(" + height.toRString() + 
				(width != null ? ", width = " + width  : "") + 
				(space != null ? ", space = " + space : "")+
				(names_arg != null ? ", names.arg = " + names_arg : "")+
				(legend_text != null ? ", legend.text = " + legend_text : "") +
				(beside != null ? ", beside = " + beside : "") +
				(horiz != null ? ", horiz = " + horiz : "") +
				(density != null ? ", density = " + density : "") +
				(angle != null ? ", angle = " + angle : "") +
				(col != null ? ", col = " + col : "")+
				(border != null ? ", border = " + border : "")+
				(main != null ? ", main = \"" + main + "\"" : "")+
				(sub != null ? ", sub = \"" + sub + "\"" : "")+
				(xlab != null ? ", xlab = \"" + xlab + "\"" : "")+
				(ylab != null ? ", ylab = \"" + ylab + "\"" : "")+
				(xlim != null ? ", xlim = " + xlim : "")+
				(ylim != null ? ", ylim = " + ylim : "")+
				(xpd != null ? ", xpd = " + xpd : "")+
				(log != null ? ", log = \"" + log + "\"" : "")+
				(axes != null ? ", axes = " + axes : "")+
				(axisnames != null ? ", axisnames = " + axisnames : "")+
				(cex_axis != null ? ", cex.axis = " + cex_axis : "")+
				(cex_names != null ? ", cex.names = " + cex_names : "")+
				(inside != null ? ", inside = " + inside : "")+
				(plot != null ? ", plot = " + plot : "")+
				(axis_lty != 0 ? ", axis.lty = " + axis_lty : "")+
				(offset != null ? ", offset = " + offset : "")+
				(add != null ? ", add = " + add : "")+
				(args_legend != null ? ", args.legend = " + args_legend : "")
				+")";
	}

	public RBarplot(RObject height, String savePath, RPlotSaveTypes type) {
		this.height = height;
		this.savePath = savePath;
		this.type = type;
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

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public RObject getHeight() {
		return height;
	}

	public void setHeight(RObject height) {
		this.height = height;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}

	public Vector<String> getNames_arg() {
		return names_arg;
	}

	public void setNames_arg(Vector<String> names_arg) {
		this.names_arg = names_arg;
	}

	public Vector<String> getLegend_text() {
		return legend_text;
	}

	public void setLegend_text(Vector<String> legend_text) {
		this.legend_text = legend_text;
	}

	public String getBeside() {
		return beside;
	}

	public void setBeside(String beside) {
		this.beside = beside;
	}

	public String getHoriz() {
		return horiz;
	}

	public void setHoriz(String horiz) {
		this.horiz = horiz;
	}

	public Vector<Double> getDensity() {
		return density;
	}

	public void setDensity(Vector<Double> density) {
		this.density = density;
	}

	public String getAngle() {
		return angle;
	}

	public void setAngle(String angle) {
		this.angle = angle;
	}

	public Vector<String> getCol() {
		return col;
	}

	public void setCol(Vector<String> col) {
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

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
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

	public String getXpd() {
		return xpd;
	}

	public void setXpd(String xpd) {
		this.xpd = xpd;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public String getAxes() {
		return axes;
	}

	public void setAxes(String axes) {
		this.axes = axes;
	}

	public String getAxisnames() {
		return axisnames;
	}

	public void setAxisnames(String axisnames) {
		this.axisnames = axisnames;
	}

	public String getCex_axis() {
		return cex_axis;
	}

	public void setCex_axis(String cex_axis) {
		this.cex_axis = cex_axis;
	}

	public String getCex_names() {
		return cex_names;
	}

	public void setCex_names(String cex_names) {
		this.cex_names = cex_names;
	}

	public String getInside() {
		return inside;
	}

	public void setInside(String inside) {
		this.inside = inside;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public int getAxis_lty() {
		return axis_lty;
	}

	public void setAxis_lty(int axis_lty) {
		this.axis_lty = axis_lty;
	}

	public Vector<Double> getOffset() {
		return offset;
	}

	public void setOffset(Vector<Double> offset) {
		this.offset = offset;
	}

	public String getAdd() {
		return add;
	}

	public void setAdd(String add) {
		this.add = add;
	}

	public String getArgs_legend() {
		return args_legend;
	}

	public void setArgs_legend(String args_legend) {
		this.args_legend = args_legend;
	}

}
