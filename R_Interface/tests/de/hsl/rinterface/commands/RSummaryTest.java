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
import de.hsl.rinterface.objects.RMatrix;
import de.hsl.rinterface.objects.RObject;
import de.hsl.rinterface.objects.RObjectTypes;
import de.hsl.rinterface.objects.RTable;
import de.hsl.rinterface.objects.RValue;
import de.hsl.rinterface.objects.RVector;

public class RSummaryTest {

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
		public void rsummarytest() throws RException
		{
			RSummary rs = new RSummary(werte);
			Assert.assertNotNull(rs);
			Assert.assertEquals("summary(c(1.0, 2.0, 3.0, 4.0, 5.0))", rs.prepareForSending().trim());
			RObject ro = con.sendCmd(rs.prepareForSending());
			Assert.assertNotNull(ro);
			Assert.assertEquals( RObjectTypes.TABLE, ro.getType());
			RTable rt = (RTable)ro;
			Assert.assertEquals(6, rt.getColLength());
			Assert.assertEquals(1, rt.getRowLength());
			Assert.assertEquals("Median", rt.getColTitleAt(2));
			Assert.assertEquals("3", rt.getMatrixAt(0, 2));
			Assert.assertEquals("1", rt.getMatrixAt(0, 0));
			Assert.assertEquals("5", rt.getMatrixAt(0, 5));	
			System.out.println(rt);
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
