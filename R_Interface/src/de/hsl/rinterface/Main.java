package de.hsl.rinterface;

import java.io.IOException;

import de.hsl.rinterface.commands.RRead;
import de.hsl.rinterface.exception.RException;
import de.hsl.rinterface.objects.RMatrix;


public class Main 
{
	public static <T> void main(String[] args) throws IOException, 
			RException, InterruptedException
	{
//		int count = 0;
//		List<Integer> list = new ArrayList<>();
//			list.add(2);
//			list.add(354);
//			list.add(1);
//			list.add(5);
//			list.add(-5);
//		String listCmd = "c(";
//		
//		for(Number n : list)
//		{
//			if(++count != list.size())
//				listCmd += n.toString() + ", ";
//			else
//				listCmd += n.toString() + ")";
//		}
//	
//		System.out.println(listCmd);
		
		//"c:\\r\\R-2.15.0\\bin\\x64\\r.exe"

		//anfänglicher Sleep nicht mehr benötigt, da der Konstruktoraufruf blockt, bis alles initialisiert ist
//		RObject ro=con.sendCmd("matrix(1,5,20)");
//		if (ro.getType().equals(RTypes.MATRIX)){
//			RMatrix name = (RMatrix) ro;
//			for (ArrayList<Number> arrayList : name) {
//				for (Number number : arrayList) {
//					System.out.print(" " + number);
//				}
//				System.out.print(System.getProperty("line.separator"));
//			}
//		}
		
//		RPlot plot = new RPlot(new RVector());
		
//		RVector<Double> rv = new RVector<>();
//		
//			con.saveObject(rv, "a");
//		RObject loaded = con.loadSavedObject("a");
//		if(loaded.getType()== RObjectTypes.VECTOR)
//		{
//			RVector loadedVec = (RVector) loaded;
//			System.out.println(loadedVec.size());
//		}

//		List<String> list = new ArrayList<>();
//			list.add("--save");
//		Connection con = null;
//		try
//		{
//			con = new ConsoleConnection();
//			System.out.println(con.sendCmdRaw(new RRead("test.csv")));
//			System.out.println(con.sendCmdRaw("mean(c(1,2,3,4))"));
//			System.out.println(con.sendCmdRaw("qwertz"));
//		}
//		catch(RException ex)
//		{
//			ex.printStackTrace();
//			System.out.println(con.isAlive());
//			System.out.println(con.sendCmdRaw("mean(c(3,2,6,4))"));
//			System.out.println(con.isAlive());
//			System.out.println(con.sendCmdRaw("max(c(1,2,3))"));
//			System.out.println(con.isAlive());
//			System.out.println(con.sendCmdRaw("ls()"));
//			con.close();
//			
//		}
		try(Connection con = new ConsoleConnection())
		{
			/**
			 * geht nicht
			 */
			System.out.println(con.sendCmd("summary(c(1,2,3,4,5))"));
//			System.out.println(con.sendCmd(new RRead("c:\\test.csv")));
//			System.out.println(con.sendCmdRaw("qwertz"));
			
			/**
			 * geht
			 */
//			RMatrix m = (RMatrix) con.sendCmd("matrix(2,10,5)");
//			System.out.println(m);
//			System.out.println(con.sendCmd("c(3,2,3,1)").toString());
//			System.out.println(con.sendCmd("c(\"awd\",\"test\",\"agh\",\"oiuzt\")").toString());
//			System.out.println(con.sendCmd("mean(c(1,2,3,4))"));
//			System.out.println(con.sendCmdRaw("mean(c(1,2,3,4))"));
//			System.out.println(con.sendCmdRaw("mean(c(1,2,3,4))"));
//			System.out.println(con.sendCmdRaw(new RRead("c:\\test.csv")));

			
		}
	}
}
