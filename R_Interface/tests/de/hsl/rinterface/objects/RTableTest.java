package de.hsl.rinterface.objects;

import java.io.IOException;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import de.hsl.rinterface.exception.RException;

public class RTableTest {
	
	@Before
	public void init() throws IOException, RException
	{
	}
	
	@Test
	public void rtable() {
		RTable t1 = new RTable(2, 3);
		Assert.assertNotNull(t1);
		Assert.assertEquals(3, t1.getColLength());
		Assert.assertEquals(2, t1.getRowLength());
		t1.setColTitle(new String[] { "a", "b","c" });
		Assert.assertEquals(t1.getColTitleAt(0), "a");
		t1.setRowTitle(new String[] { "c", "d" });
		Assert.assertEquals(t1.getRowTitleAt(1), "d");
		t1.setMatrix(new String[][] { { "1", "2","3" }, { "4", "5","6" } });
		Assert.assertEquals(t1.getMatrixAt(1, 1), "5");
	}
	
	@After
	public void cleanUp() throws IOException, InterruptedException, RException
	{	
	}
}