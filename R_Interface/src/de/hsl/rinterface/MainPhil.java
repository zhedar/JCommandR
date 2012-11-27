package de.hsl.rinterface;

import java.io.File;
import java.io.IOException;

import de.hsl.rinterface.exception.RException;
import de.hsl.rinterface.objects.RVector;

public class MainPhil {
	public static void main(String[] args) throws IOException, RException{
		// int count = 0;
		// List<Integer> list = new ArrayList<>();
		// list.add(2);
		// list.add(354);
		// list.add(1);
		// list.add(5);
		// list.add(-5);
		// String listCmd = "c(";
		//
		// for(Number n : list)
		// {
		// if(++count != list.size())
		// listCmd += n.toString() + ", ";
		// else
		// listCmd += n.toString() + ")";
		// }
		//
		// System.out.println(listCmd);

		// "c:\\r\\R-2.15.0\\bin\\x64\\r.exe"

		// anfänglicher Sleep nicht mehr benötigt, da der Konstruktoraufruf
		// blockt, bis alles initialisiert ist
		// RObject ro=con.sendCmd("matrix(1,5,20)");
		// if (ro.getType().equals(RTypes.MATRIX)){
		// RMatrix name = (RMatrix) ro;
		// for (ArrayList<Number> arrayList : name) {
		// for (Number number : arrayList) {
		// System.out.print(" " + number);
		// }
		// System.out.print(System.getProperty("line.separator"));
		// }
		// }

		// RPlot plot = new RPlot(new RVector());

		// RVector<Double> rv = new RVector<>();
		//
		// con.saveObject(rv, "a");
		// RObject loaded = con.loadSavedObject("a");
		// if(loaded.getType()== RObjectTypes.VECTOR)
		// {
		// RVector loadedVec = (RVector) loaded;
		// System.out.println(loadedVec.size());
		// }

		// List<String> list = new ArrayList<>();
		// list.add("--save");
//		Connection con = null;
//		try {
//			con = new ConsoleConnection();
//			System.out.println(con.sendCmdRaw(new RRead("test.csv")));
//			System.out.println(con.sendCmdRaw("mean(c(1,2,3,4))"));
//			System.out.println(con.sendCmdRaw("qwertz"));
//		} catch (RException ex) {
//			ex.printStackTrace();
//			System.out.println(con.isAlive());
//			System.out.println(con.sendCmdRaw("mean(c(3,2,6,4))"));
//			System.out.println(con.isAlive());
//			System.out.println(con.sendCmdRaw("max(c(1,2,3))"));
//			System.out.println(con.isAlive());
//			System.out.println(con.sendCmdRaw("ls()"));
//			con.close();
//		}

		//Workspace-Beispiel
		 try(Connection con = new ConsoleConnection())
		 {
//		 System.out.println(con.sendCmdRaw(new RRead("test.csv")));
//		 System.out.println(con.sendCmd(new RRead("test.csv")));
//			 System.out.println(con.sendCmdRaw("rm(neuerwd)"));
//			 con.sendCmdVoid("options(echo=FALSE)");
//			 con.sendCmdVoid("setwd(\"/Users/pgruhn/rtest/"+ "\")");
//			 System.out.println(con.sendCmdRaw("rm(neuerwd)"));
			 
			 System.out.println(con.sendCmdRaw("ls()"));
			 System.out.println(con.sendCmdRaw("getwd()"));
			 File w1 = new File("w1");
			 w1.mkdir();
			 con.changeWorkspace(w1);
			 System.out.println(con.sendCmdRaw("getwd()"));
			 System.out.println(con.sendCmdRaw("ls()"));
//			 System.out.println(con.loadSavedObject("testrnd"));
//			 con.sendCmdVoid("testrnd <- runif(1, 5.0, 7.5)");
			 con.saveWorkspace();
			 File w2 = new File("w2");
			 w2.mkdir();
			 con.changeWorkspace(w2);
			 System.out.println(con.sendCmdRaw("getwd()"));
			 System.out.println(con.sendCmdRaw("ls()"));
			 RVector<Double> rvec = new RVector<>();
			 	rvec.add(2.0d);
			 	rvec.add(1.0d);
			 con.saveObject(rvec, "neuerwd2");
			System.out.println(con.sendCmdRaw("ls()"));
			con.sendCmdVoid("rm(neuerwd2)");
			System.out.println(con.sendCmdRaw("ls()"));
			con.saveWorkspace();
		 }
	}
}
