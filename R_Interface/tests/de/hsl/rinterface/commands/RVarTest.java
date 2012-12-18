package de.hsl.rinterface.commands;

/**
 * @author Tobias Steinmetzer
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

public class RVarTest {

	private Connection con;
	private RVector<Double> werte;

	@Before
	public void init() throws IOException, RException
	{
		con = new ConsoleConnection();
		werte = new RVector<>();
		for (int i = 1; i <= 5; i++) {
			werte.add((double) i);
		}
	}
	
		@Test
		public void rvartest() throws RException
		{
			RVar var = new RVar(werte);
			Assert.assertNotNull(var);
			RObject ro =con.sendCmd(var.prepareForSending());
			Assert.assertEquals(RObjectTypes.VALUE , ro.getType());
			RValue<String> rv = (RValue<String>) ro;
			Assert.assertEquals("2.5", rv.getValue());
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
