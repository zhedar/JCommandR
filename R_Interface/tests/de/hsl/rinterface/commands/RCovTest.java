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

public class RCovTest {

	//Testverbindung
	private Connection con;
	private RVector<Double> x;
	private RVector<Double> y;

	@Before
	public void init() throws IOException, RException
	{
		con = new ConsoleConnection();
		x = new RVector<>();
		x.add(10.0);
		x.add(50.0);
		x.add(90.0);
		x.add(80.0);
		x.add(500.0);
		
		y = new RVector<>();
		y.add(1.0);
		y.add(2.0);
		y.add(3.0);
		y.add(4.0);
		y.add(5.0);
		
	}
	
	//Dies ist ein Beispiel
	@Test
	public void rcovtest() throws RException
	{
		RCov cov = new RCov(x,y);
		Assert.assertNotNull(cov);
		RObject ro =con.sendCmd(cov.prepareForSending());
		Assert.assertEquals(RObjectTypes.VALUE , ro.getType());
		RValue<String> rv = (RValue<String>) ro;
		Assert.assertEquals("252.5", rv.getValue());
			
		RCov cov2 = new RCov(x,y);
		cov2.setUse("everything");
		cov2.setMethod("kendall");
		Assert.assertNotNull(cov2);
		RObject ro2 =con.sendCmd(cov2.prepareForSending());
		Assert.assertEquals(RObjectTypes.VALUE , ro2.getType());
		RValue<String> rv2 = (RValue<String>) ro2;
		Assert.assertEquals("16", rv2.getValue());
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
