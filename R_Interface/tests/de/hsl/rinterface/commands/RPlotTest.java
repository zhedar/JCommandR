package de.hsl.rinterface.commands;

/**
 * @author Peggy Kübe
 */
import java.io.IOException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.hsl.rinterface.Connection;
import de.hsl.rinterface.ConsoleConnection;
import de.hsl.rinterface.exception.RException;
import de.hsl.rinterface.objects.RObject;
import de.hsl.rinterface.objects.RObjectTypes;
import de.hsl.rinterface.objects.RTable;
import de.hsl.rinterface.objects.RValue;
import de.hsl.rinterface.objects.RVector;

public class RPlotTest {

	//Testverbindung
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
	
	//Dies ist ein Beispiel	
	@Test
	public void rplottest() throws RException
	{
		
		RPlot plot = new RPlot(werte, "D:/Eigene Dokumente/Peggy/Studium/test3.jpg",RPlotSaveTypes.JPEG);
		Assert.assertNotNull(plot);
		Assert.assertEquals("jpeg(\"D:/Eigene Dokumente/Peggy/Studium/test3.jpg\"); plot(c(10.0, 50.0, 90.0, 80.0, 500.0))", plot.prepareForSending().trim());
		RObject ro =con.sendCmd(plot.prepareForSending());
		Assert.assertNotNull(ro);
	}
	
		
	@After
	public void cleanUp() throws IOException, InterruptedException, RException
	{	//Connection schliessen, wenn nicht bereits geschehen
		if (con != null && con.isAlive())
		{
			con.close();
			con = null;
		}
	}
}
