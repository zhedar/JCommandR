package de.hsl.rinterface.objects;
/**
 * @author Tobias Steinmetzer
 */
import java.io.IOException;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import de.hsl.rinterface.exception.RException;

public class RMatrixTest {

	@Before
	public void init() throws IOException, RException
	{
	}
	
	@Test
	public void rtable() {
		RMatrix m1 = new RMatrix(2, 3);
		Assert.assertNotNull(m1);
		Assert.assertEquals(3, m1.getColLength());
		Assert.assertEquals(2, m1.getRowLength());
		m1.setMatrix(new String[][] { { "1", "2","3" }, { "4", "5","6" } });
		Assert.assertEquals(m1.getMatrixAt(1, 1), "5");
	}
	
	@After
	public void cleanUp() throws IOException, InterruptedException, RException
	{	
	}
}
