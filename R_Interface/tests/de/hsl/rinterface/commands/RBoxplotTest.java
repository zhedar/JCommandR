package de.hsl.rinterface.commands;

/**
 * @author Peggy Kübe
 */
import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.hsl.rinterface.Connection;
import de.hsl.rinterface.ConsoleConnection;
import de.hsl.rinterface.exception.RException;
import de.hsl.rinterface.objects.RObject;
import de.hsl.rinterface.objects.RVector;

public class RBoxplotTest {

	private Connection con;
	private RVector<Double> werte;

	@Before
	public void init() throws IOException, RException
	{
		con = new ConsoleConnection();
		werte = new RVector<>();
		werte.add(10.0);
		werte.add(50.0);
		werte.add(90.0);
		werte.add(80.0);
		werte.add(500.0);

	}
	
	@Test
	public void rboxplottest() throws RException
	{
		File file = new File("C:\\Users\\tobo1987\\Documents\\Uni\\boxplot.jpg");
		RBoxplot boxplot = new RBoxplot(werte,file,RPlotSaveTypes.JPEG);
		Assert.assertNotNull(boxplot);
		boxplot.setRange(3.5);
		boxplot.setOutline("TRUE");
		Assert.assertEquals("jpeg(filename=\"C:/Users/tobo1987/Documents/Uni/plot.jpg\");boxplot(c(10.0, 50.0, 90.0, 80.0, 500.0), range = 3.5, outline = TRUE)", boxplot.prepareForSending().trim());
		System.out.println(boxplot.prepareForSending());
		RObject ro =con.sendCmd(boxplot.prepareForSending());
		Assert.assertNotNull(ro);
	}
	
		
	@After
	public void cleanUp() throws IOException, InterruptedException, RException
	{	
		if (con != null && con.isAlive())
		{
			con.close();
			con = null;
		}
	}
}
