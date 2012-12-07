package de.hsl.rinterface.commands;
import java.io.File;
import java.util.Vector;
import de.hsl.rinterface.objects.RObject;
import de.hsl.rinterface.utils.RUtils;
/***********************************************************************
 * Module: RPlot.java
 * Author: Peggy Kübe
 * Purpose: Defines the Class RPlot
 ***********************************************************************/
/**
 * plot(x, y, ...)
 * x - the coordinates of points in the plot. Alternatively, a single plotting structure, function or any R object with a plot method can be provided.
 * y - the y coordinates of points in the plot, optional if x is an appropriate structure.
 * what type of plot should be drawn. Possible types are 
 * "p" for points, 
 * "l" for lines, 
 * "b" for both, 
 * "c" for the lines part alone of "b", 
 * "o" for both ‘overplotted’, 
 * "h" for ‘histogram’ like (or ‘high-density’) vertical lines,
 * "s" for stair steps, 
 * "S" for other steps, see ‘Details’ below, 
 * "n" for no plotting. 
 * main - an overall title for the plot: see title.
 * sub - a sub title for the plot: see title.
 * xlab - a title for the x axis: see title.
 * ylab - a title for the y axis: see title.
 * asp - the y/x aspect ratio, see plot.window.
 * 
 * Beispiel:
 * test<-c(2,77,900)
 * plot(test)
 * plot(test,type="l")
 */

public class RPlot implements RCommand
{
	private File file;
	private String savePath;
	private RPlotSaveTypes type;
	private RPlotTypes plottype;
	
	private RObject x;
	private RObject y;
	private String sub;
	private String main;
	private String xlab;
	private String ylab;
	private Vector<String> col;
	private double asp;
	
	@Override
	public String prepareForSending() {
		String path = RUtils.getRPath(file.getAbsolutePath());
		
		return  type + "(\"" + path + "\"); plot(" + x.toRString() + 
				(y != null ? ", y = " + y  : "") + 
				(main != null ? ", main = \"" + main + "\"" : "")+
				(sub != null ? ", sub = \"" + sub + "\"" : "")+
				(xlab != null ? ", xlab = \"" + xlab + "\"" : "")+
				(ylab != null ? ", ylab = \"" + ylab + "\"" : "")+
				(col != null ? ", col = " + col : "")+
				(asp != 0.0d ? ", asp = " + asp : "")+
				(plottype != null ? ", type = \"" + plottype + "\"" : "")
				+")";
	}

	public RPlot(RObject x, String savePath, RPlotSaveTypes type) {
		this.x = x;
		this.savePath = savePath;
		this.type = type;
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

	public RPlotTypes getPlottype() {
		return plottype;
	}

	public void setPlottype(RPlotTypes plottype) {
		this.plottype = plottype;
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

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getMain() {
		return main;
	}

	public void setMain(String main) {
		this.main = main;
	}

	public String getxlab() {
		return xlab;
	}

	public void setxLab(String xlab) {
		this.xlab = xlab;
	}

	public String getylab() {
		return ylab;
	}

	public void setylab(String ylab) {
		this.ylab = ylab;
	}

	public Vector<String> getCol() {
		return col;
	}

	public void setCol(Vector<String> col) {
		this.col = col;
	}

	public double getAsp() {
		return asp;
	}

	public void setAsp(double asp) {
		this.asp = asp;
	}
}