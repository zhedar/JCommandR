package de.hsl.rinterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hsl.rinterface.commands.RRead;
import de.hsl.rinterface.exception.RException;


public class Main 
{
	public static void main(String[] args) throws IOException, RException, InterruptedException
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
		
		List<String> list = new ArrayList<>();
		list.add("--save");

		//"c:\\r\\R-2.15.0\\bin\\x64\\r.exe"
		Connection con = new ConsoleConnection(list);

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
//
//		try {
//			RRead read = new RRead("test.csv");
//			read.prepareForSending();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		System.out.println(con.sendCmdRaw(new RRead("C:\\test.csv").prepareForSending()));
		System.out.println(con.sendCmdRaw("mean(c(1,2,3,4))"));
		
		con.close();
		 
	}
}
