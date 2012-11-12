package de.hsl.rinterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		con.sendCmd("pnorm(1.70)");
		Thread.sleep(1000); 
		con.close();
		
	}
}
