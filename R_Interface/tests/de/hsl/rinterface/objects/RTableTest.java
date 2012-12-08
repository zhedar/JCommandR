package de.hsl.rinterface.objects;

public class RTableTest {

	public static void main(String[] args) {
		RTable t1 = new RTable(2, 2);
		t1.setColTitle(new String[] { "a", "b" });
		t1.setRowTitle(new String[] { "c", "d" });
		t1.setMatrix(new String[][] { { "1", "2" }, { "3", "4" } });
		System.out.println(t1.toString());
	}
}
