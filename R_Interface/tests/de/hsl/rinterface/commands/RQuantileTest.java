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

public class RQuantileTest {

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
	public void rquantiltest() throws RException
	{
		
		RQuantile quantile = new RQuantile(werte);
		Assert.assertNotNull(quantile);
		Assert.assertEquals("quantile(c(10.0, 50.0, 90.0, 80.0, 500.0))", quantile.prepareForSending().trim());
		RObject ro =con.sendCmd(quantile.prepareForSending());
		Assert.assertNotNull(ro);
		Assert.assertEquals( RObjectTypes.VECTOR, ro.getType());
		RVector<String> rv = (RVector)ro;
		Assert.assertEquals("0%", rv.get(0));
		Assert.assertEquals("100%", rv.get(4));
		Assert.assertEquals("75%", rv.get(3));
		Assert.assertEquals("500", rv.get(9));
	}
	
	@Test
	public void rquantiltest2() throws RException
	{	
		RQuantile quantile2 = new RQuantile(werte);
		Assert.assertNotNull(quantile2);
		quantile2.setProbs("0.5");
		quantile2.setNa_rm("true");
		quantile2.setNames("false");
		quantile2.setType(2);
		RObject ro2 =con.sendCmd(quantile2.prepareForSending());
		Assert.assertEquals(RObjectTypes.VALUE , ro2.getType());
		RValue<String> rv2 = (RValue<String>) ro2;
		Assert.assertEquals("80", rv2.getValue());
		
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
