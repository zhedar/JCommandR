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

public class ConsoleConnectionTest
{
	private Connection con;
	private List<String> argSave = new ArrayList<>();

	@Before
	public void init()
	{
		if (argSave.isEmpty())
			argSave.add("-save");
	}

	@Test
	public void oneParamConstructor() throws IOException, RException
	{
		con = new ConsoleConnection(argSave);
		Assert.assertTrue("Verbindung besteht nicht mehr.", con.isAlive());
	}

	@Test
	public void twoParamConstructor()
	{
		//TODO wie path festlegen zum testen auf verschiedenen maschinen?
		//		con = new ConsoleConnection(path, args)l
	}

	@Test(expected = RException.class)
	public void closeAndAlive() throws IOException, InterruptedException,
			RException
	{
		con = new ConsoleConnection(argSave);
		Assert.assertTrue("Verbindung besteht nicht mehr.", con.isAlive());
		try
		{
			con.close();
			Assert.assertFalse(
					"Connection ist evtl. noch nicht zu. Prüfung zu schnell, oder" +
					"Trennung hat nicht funktioniert.",
					con.isAlive());
		}
		catch (Exception e)
		{ //sollte funktionieren
			Assert.fail();
		}
		
		//sollte Exception werfen, da bereits geschlossen
		con.close();
	}
	
	@Test
	public void sendCmd1() throws IOException, RException
	{
		con = new ConsoleConnection(argSave);
		RObject ret = con.sendCmd("pnorm(1.70)");
//		ret.toString();
	}
	
	@Test
	public void showAllVars() throws IOException, RException
	{
		con = new ConsoleConnection(argSave);
		List<String> vars = con.getAllVars();
		Assert.assertNotNull(vars);
		//TODO f�llen und pr�fen
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
