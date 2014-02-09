package de.hsl.rinterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.hsl.rinterface.exception.RException;
import de.hsl.rinterface.objects.RObject;
import de.hsl.rinterface.objects.RObjectTypes;
import de.hsl.rinterface.objects.RValue;

public class ConsoleConnectionTest
{
//	private Connection con;
	private List<String> argSave = new ArrayList<>();

	@Before
	public void init()
	{
		if (argSave.isEmpty())
			argSave.add("--save");
	}

	@Test
	public void nonParamConstructor() throws IOException, RException
	{
		Connection con = new ConsoleConnection();
		Assert.assertTrue("Verbindung besteht nicht.", con.isAlive());
		con.close();
	}
	
	@Test
	public void oneParamConstructor() throws IOException, RException
	{
		Connection con = new ConsoleConnection(argSave);
		Assert.assertTrue("Verbindung besteht nicht", con.isAlive());
		con.close();
	}

//	@Test(expected = RException.class)
//	public void closeAndAlive() throws IOException, InterruptedException,
//			RException
//	{
//		Connection con = new ConsoleConnection(argSave);
//		Assert.assertTrue("Verbindung besteht nicht mehr.", con.isAlive());
//		try
//		{
//			con.close();
//			Thread.sleep(50);
//			Assert.assertFalse(
//					"Connection ist evtl. noch nicht zu. Prüfung zu schnell, oder" +
//					"Trennung hat nicht funktioniert.",
//					con.isAlive());
//		}
//		catch (Exception e)
//		{ //sollte funktionieren
//			Assert.fail();
//		}
//		
//		//sollte Exception werfen, da bereits geschlossen
//		con.close();
//	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void sendCmd1() throws IOException, RException
	{
		Connection con = new ConsoleConnection(argSave);
		RObject ret = con.sendCmd("pnorm(1.70)");
		if(ret.getType() == RObjectTypes.VALUE)
		{
			String val = ((RValue<String>) ret).getValue();
			Assert.assertEquals("0.9554345", val);
			Assert.assertEquals(val, ret.toString());
		}
		else
			Assert.fail("Ergebnis ist kein Einzelwert.");
		con.close();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void showAllVars() throws IOException, RException
	{
		String name = "testvar3d";
		Connection con = new ConsoleConnection(argSave);
		//vorhandene Variablen rausräumen
		con.sendCmdVoid("rm(list=ls(all=TRUE))");
		//neue Variable hinzufügen
		con.saveObject(new RValue<Double>(3.00d), name);
		List<String> vars = con.getAllVars();
		Assert.assertNotNull(vars);
		RValue<String> loadedName = (RValue<String>) con.loadSavedObject(name);
		double loadedVal = Double.parseDouble(loadedName.getValue());
		Assert.assertTrue(3.00d == loadedVal);
		con.close();
	}

	@After
	public void cleanUp() throws IOException, InterruptedException, RException
	{
//		if (con != null && con.isAlive())
//		{
//			con.close();
//			con = null;
//		}
	}
}
