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

public class RPlotTest {

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
	public void rplottest() throws RException
	{
		File file = new File("C:\\Users\\tobo1987\\Documents\\Uni\\test.jpg");
		RPlot plot = new RPlot(werte,file,RPlotSaveTypes.JPEG);
		Assert.assertNotNull(plot);
		Assert.assertEquals("jpeg(filename=\"C:/Users/tobo1987/Documents/Uni/test.jpg\"); plot(c(10.0, 50.0, 90.0, 80.0, 500.0))", plot.prepareForSending().trim());
		System.out.println(plot.prepareForSending());
		RObject ro =con.sendCmd(plot.prepareForSending());
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
