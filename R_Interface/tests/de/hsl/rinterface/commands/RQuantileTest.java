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

public class RQuantileTest {

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
	public void rquantiltest() throws RException
	{
		RQuantile quantile= new RQuantile(werte);
		Assert.assertNotNull(quantile);
		
	//	Assert.assertEquals(RObjectTypes.VALUE , ro.getType());
		//RValue<String> rv = (RValue<String>) ro;
		//Assert.assertEquals("80", rv.getValue());
		
		
		Assert.assertEquals("quantile(c(10.0, 50.0, 90.0, 80.0, 500.0))", quantile.prepareForSending().trim());

		RObject ro =con.sendCmd(quantile.prepareForSending());
		
		Assert.assertNotNull(ro);
		Assert.assertEquals( RObjectTypes.TABLE, ro.getType());
		RTable rt = (RTable)ro;
		Assert.assertEquals(1, rt.getColLength());
		Assert.assertEquals(5, rt.getRowLength());
		Assert.assertEquals("75%", rt.getColTitleAt(3));
		Assert.assertEquals("80", rt.getMatrixAt(0, 2));
		Assert.assertEquals("10", rt.getMatrixAt(0, 0));
		Assert.assertEquals("500", rt.getMatrixAt(0, 4));	
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
