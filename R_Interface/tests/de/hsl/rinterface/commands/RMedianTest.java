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
import de.hsl.rinterface.objects.RValue;
import de.hsl.rinterface.objects.RVector;

public class RMedianTest {

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
		werte.add(500.00);

	}
	
	//Dies ist ein Beispiel
	@Test
	public void rmediantest() throws RException
	{
		RMedian median = new RMedian(werte);
		Assert.assertNotNull(median);
		RObject ro =con.sendCmd(median.prepareForSending());
		Assert.assertEquals(RObjectTypes.VALUE , ro.getType());
		RValue<String> rv = (RValue<String>) ro;
		Assert.assertEquals("80", rv.getValue());
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
