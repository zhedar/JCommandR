package de.hsl.rinterface;

import java.io.File;
import java.io.IOException;

import de.hsl.rinterface.exception.RException;
import de.hsl.rinterface.objects.RVector;

public class WorkspaceTest {
	public static void main(String[] args) throws IOException, RException{
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
