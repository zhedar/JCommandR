package de.hsl.rinterface;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.hsl.rinterface.commands.RMean;
import de.hsl.rinterface.exception.RException;
import de.hsl.rinterface.objects.RMatrix;
import de.hsl.rinterface.objects.RObject;
import de.hsl.rinterface.objects.RObjectTypes;
import de.hsl.rinterface.objects.RValue;
import de.hsl.rinterface.objects.RVector;

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
	public void mean() throws RException
	{
//		RObject ro = con.sendCmd("matrix(1,5,5)");
//		if (ro.getType().equals(RObjectTypes.MATRIX))
//		{	//Ergebnis ist eine Matrix - prima
//			RMatrix name = (RMatrix) ro;
//			Assert.assertNotNull(name);
//			Assert.assertTrue(name.getRowLength()== 5 && name.getColLength() == 5);		
//		}
//		else	//Test fehlschlagen lassen, falls if nicht ausgelöst wurde
//			Assert.fail("Ergebnis ist keine Matrix, sollte aber eine sein.");
		
		RVector<Double> rv = new RVector<>();
		rv.add(1.0);
		rv.add(5.0);
		rv.add(90.0);
		rv.add(25.0);
		RMean mean =new RMean(rv);
		RObject ro = con.sendCmd(mean.prepareForSending());
		
		if (ro.getType().equals(RObjectTypes.VALUE)) {
			RValue<Double> value = (RValue<Double>) ro;
			
			Assert.assertNotNull(value);
			Assert.assertTrue((""+value.getValue()).equals("30.25"));
			System.out.println(value.getValue());
		}
		else	//Test fehlschlagen lassen, falls if nicht ausgelöst wurde
			Assert.fail("Ergebnis ist keine Value, sollte aber eine sein."+rv.getType()+ro);
		
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
