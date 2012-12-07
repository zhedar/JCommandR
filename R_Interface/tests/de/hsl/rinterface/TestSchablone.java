package de.hsl.rinterface;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.hsl.rinterface.exception.RException;
import de.hsl.rinterface.objects.RMatrix;
import de.hsl.rinterface.objects.RObject;
import de.hsl.rinterface.objects.RObjectTypes;

public class TestSchablone
{
	//Testverbindung
	private Connection con;

	@Before
	public void init() throws IOException, RException
	{
		con = new ConsoleConnection();
	}
	
	//Dies ist ein Beispiel
	@Test
	public void parse5_5Matrix() throws RException
	{
		RObject ro = con.sendCmd("matrix(1,5,5)");
		if (ro.getType().equals(RObjectTypes.MATRIX))
		{	//Ergebnis ist eine Matrix - prima
			RMatrix name = (RMatrix) ro;
			Assert.assertNotNull(name);
			Assert.assertTrue(name.getRowLength()== 5 && name.getColLength() == 5);		
		}
		else	//Test fehlschlagen lassen, falls if nicht ausgelöst wurde
			Assert.fail("Ergebnis ist keine Matrix, sollte aber eine sein.");
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
