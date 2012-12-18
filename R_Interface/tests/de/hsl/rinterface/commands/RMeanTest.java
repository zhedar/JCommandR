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

public class RMeanTest {

	private Connection con;
	private RVector<Double> werte;

	@Before
	public void init() throws IOException, RException
	{
		con = new ConsoleConnection();
		werte = new RVector<>();
		werte.add(1.0);
		werte.add(5.0);
		werte.add(90.0);
		werte.add(25.0);

	}
	
	@Test
	public void rmeantest() throws RException
	{
		RMean mean = new RMean(werte);
		Assert.assertNotNull(mean);
		RObject ro =con.sendCmd(mean);
		Assert.assertEquals(RObjectTypes.VALUE , ro.getType());
		RValue<String> rv = (RValue<String>) ro;
		Assert.assertEquals("30.25", rv.getValue());
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
