package de.hsl.rinterface.commands;

import java.io.File;
import java.util.Vector;

import de.hsl.rinterface.objects.RObject;
import de.hsl.rinterface.utils.RUtils;

/***********************************************************************
 * Module:  RPie.java
 * Author:  Peggy Kübe
 * Purpose: Defines the Class RPie
 ***********************************************************************/
/**
 * Kreisdiagramm: pie(x, labels = names(x), edges = 200, radius = 0.8,
    				clockwise = FALSE, init.angle = if(clockwise) 90 else 0,
    				density = NULL, angle = 45, col = NULL, border = NULL,
    				lty = NULL, main = NULL, ...)
 * x - a vector of non-negative numerical quantities. The values in x are displayed as the areas of pie slices.
 * labels - one or more expressions or character strings giving names for the slices.
 * Other objects are coerced by as.graphicsAnnot.
 * For empty or NA (after coercion to character) labels, no label nor pointing line is drawn.
 * edges - the circular outline of the pie is approximated by a polygon with this many edges.
 * radius - the pie is drawn centered in a square box whose sides range from -1 to 1.
 * If the character strings labeling the slices are long it may be necessary to use a smaller radius.
 * clockwise - logical indicating if slices are drawn clockwise or counter clockwise
 * (i.e., mathematically positive direction), the latter is default.
 * init.angle - number specifying the starting angle (in degrees) for the slices.
 * Defaults to 0 (i.e., ‘3 o'clock’) unless clockwise is true where init.angle defaults to 90 (degrees), (i.e., ‘12 o'clock’).
 * density - the density of shading lines, in lines per inch. The default value of NULL means that no shading lines are drawn.
 * Non-positive values of density also inhibit the drawing of shading lines.
 * angle - the slope of shading lines, given as an angle in degrees (counter-clockwise).
 * col - a vector of colors to be used in filling or shading the slices.
 * If missing a set of 6 pastel colours is used, unless density is specified when par("fg") is used.
 * border, lty - (possibly vectors) arguments passed to polygon which draws each slice.
 * main - an overall title for the plot.
 * ... - graphical parameters can be given as arguments to pie.
 * They will affect the main title and labels only.
 * 
 * Beispiel:
 * col = c("purple", "violetred1", "green3", "cornsilk", "cyan", "white")
 * col= "red"
 * col = gray(seq(0.4,1.0,length=6))
 */
public class RPie implements RCommand {

	private File file;
	private String savePath;
	private RPlotSaveTypes type; 
	
	private RObject x;
	private String labels;
	private int edges;
	private double radius;
	private String clockwise;
	private String init_angle;
	private String density;
	private String angle;
	private Vector<String> col;
	private String border;
	private String lty;
	private String main;
	
	@Override
	public String prepareForSending() {
		String path = RUtils.getRPath(file.getAbsolutePath());
		
		return  type + "(\"" + path + "\"); pie(" + x.toRString() + 
				(labels != null ? ", labels = \"" + labels + "\"" : "") + 
				(edges != 0 ? ", edges = " + edges : "")+
				(radius != 0.0d ? ", radius = " + labels : "")+
				(clockwise != null ? ", clockwise = " + clockwise : "") +
				(init_angle != null ? ", init.angle = " + init_angle : "") +
				(density != null ? ", density = " + density : "") +
				(angle != null ? ", angle = " + angle : "") +
				(col != null ? ", col = " + col : "")+
				(border != null ? ", border = " + border : "")+
				(lty != null ? ", lty = " + lty : "")+
				(main != null ? ", main = \"" + main + "\"" : "")
				+")";
	}
	
	public RPie(RObject x, String savePath, RPlotSaveTypes type) {
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

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}

	public int getEdges() {
		return edges;
	}

	public void setEdges(int edges) {
		this.edges = edges;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public String getClockwise() {
		return clockwise;
	}

	public void setClockwise(String clockwise) {
		this.clockwise = clockwise.toUpperCase();
	}

	public String getInit_angle() {
		return init_angle;
	}

	public void setInit_angle(String init_angle) {
		this.init_angle = init_angle;
	}

	public String getDensity() {
		return density;
	}

	public void setDensity(String density) {
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

	public String getLty() {
		return lty;
	}

	public void setLty(String lty) {
		this.lty = lty;
	}

	public String getMain() {
		return main;
	}

	public void setMain(String main) {
		this.main = main;
	}
}
