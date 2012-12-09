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
import de.hsl.rinterface.objects.RString;
import de.hsl.rinterface.objects.RValue;
import de.hsl.rinterface.objects.RVector;

public class RBinomTestTest {

	//Testverbindung
	private Connection con;
	private RVector<Double> werte;

	@Before
	public void init() throws IOException, RException
	{
		con = new ConsoleConnection();
		werte = new RVector<>();
		for (int i = 1; i <= 2; i++) {
			werte.add((double) i);
		}
	}
	
	//Dies ist ein Beispiel
		@Test
		public void rbinomtest() throws RException
		{
			RBinomTest<Double> binomtest = new RBinomTest<>(werte, 0);
			Assert.assertNotNull(binomtest);
			System.out.println(binomtest.prepareForSending());
			System.out.println(con.sendCmdRaw(binomtest.prepareForSending()));
			RObject ro = con.sendCmd(binomtest.prepareForSending());
			Assert.assertNotNull(ro);
			Assert.assertEquals(RObjectTypes.STRING, ro.getType());
			RString rs = (RString) ro;
			Assert.assertNotNull(rs);
		}
	
		
	@After
	public void cleanUp() throws IOException, InterruptedException, RException
	{	//Connection schließen, wenn nicht bereits geschehen
		if (con != null && con.isAlive())
		{
			con.close();
			con = null;
		}
	}
}
