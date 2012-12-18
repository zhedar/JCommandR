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

public class RFilterTest {

	private Connection con;
	private RVector<Double> werte;
	private String s_filter;

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
		
		s_filter = "2";

	}
	
	@Test
	public void rfiltertest() throws RException
	{
		RFilter filter = new RFilter(werte, s_filter);
		Assert.assertNotNull(filter);
		filter.setSides(1);
		filter.setMethod("convolution");
		System.out.println(filter.prepareForSending());
		Assert.assertEquals("filter(c(10.0, 50.0, 90.0, 80.0, 500.0) , 2, method = \"convolution\", sides = 1)", filter.prepareForSending().trim());
		RObject ro =con.sendCmd(filter.prepareForSending());
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
