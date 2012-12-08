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
import de.hsl.rinterface.objects.RTable;
import de.hsl.rinterface.objects.RVector;

public class RTTestTest {

	//Testverbindung
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
	
	//Dies ist ein Beispiel
		@Test
		public void rmatrixtest() throws RException
		{
			RTTest ttest = new RTTest(werte);
			Assert.assertNotNull(ttest);
			RObject ro = con.sendCmd(ttest.prepareForSending());
			Assert.assertEquals(ro.getType(), RObjectTypes.STRING);
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
