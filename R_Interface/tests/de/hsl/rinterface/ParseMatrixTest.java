package de.hsl.rinterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.hsl.rinterface.exception.RException;
import de.hsl.rinterface.objects.RMatrix;
import de.hsl.rinterface.objects.RObject;
import de.hsl.rinterface.objects.RObjectTypes;

public class ParseMatrixTest
{
	private Connection con;
	private List<String> argSave = new ArrayList<>();

	@Before
	public void init() throws IOException, RException
	{
		if (argSave.isEmpty())
			argSave.add("-save");
		con = new ConsoleConnection(argSave);
	}
	
	@Test
	public void parse5_5Matrix() throws RException
	{
		RObject ro=con.sendCmd("matrix(1,5,5)");
		if (ro.getType().equals(RObjectTypes.MATRIX))
		{
			RMatrix name = (RMatrix) ro;
			Assert.assertNotNull(name);
//			Assert.assertTrue(name.size() == 5);	TODO wo ist size hin?		
		}
	}
	
	@Test
	public void testSth()
	{

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
