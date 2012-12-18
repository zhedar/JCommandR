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

public class RHistTest {

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
	public void rhisttest() throws RException
	{
		File file = new File("C:\\Users\\tobo1987\\Documents\\Uni\\hist.jpg");
		RHist hist = new RHist(werte,file,RPlotSaveTypes.JPEG);
		Assert.assertNotNull(hist);
		hist.setMain("Ueberschrift");
		hist.setCol("c(\"Red\",\"Green\",\"Yellow\")");
		Assert.assertEquals("jpeg(filename=\"C:/Users/tobo1987/Documents/Uni/hist.jpg\"); hist(c(10.0, 50.0, 90.0, 80.0, 500.0), col = c(\"Red\",\"Green\",\"Yellow\"), main = \"Ueberschrift\")", hist.prepareForSending().trim());
		System.out.println(hist.prepareForSending());
		RObject ro =con.sendCmd(hist.prepareForSending());
		Assert.assertNotNull(ro);
	}
	
	@Test
	public void rhisttest2() throws RException
	{
		File file = new File("C:\\Users\\tobo1987\\Documents\\Uni\\hist2.jpg");
		RHist hist = new RHist(werte,file,RPlotSaveTypes.JPEG);
		Assert.assertNotNull(hist);
		hist.setMain("Ueberschrift");
		hist.setCol("\"Red\"");
		Assert.assertEquals("jpeg(filename=\"C:/Users/tobo1987/Documents/Uni/hist2.jpg\");  hist(c(10.0, 50.0, 90.0, 80.0, 500.0), col = \"Red\", main = \"Ueberschrift\")", hist.prepareForSending().trim());
		System.out.println(hist.prepareForSending());
		RObject ro =con.sendCmd(hist.prepareForSending());
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
