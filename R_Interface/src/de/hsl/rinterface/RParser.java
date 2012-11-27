package de.hsl.rinterface;

/***********************************************************************
 * Module:  RParser.java
 * Author:  tobo1987
 * Purpose: Defines the Class RParser
 ***********************************************************************/

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hsl.rinterface.exception.RException;
import de.hsl.rinterface.objects.RMatrix;
import de.hsl.rinterface.objects.RObject;
import de.hsl.rinterface.objects.RValue;
import de.hsl.rinterface.objects.RVector;

/** @pdOid 0331b484-3a04-4082-9cca-3c92db3656d8 */
public class RParser {

	/** @throws RException 
	 * @pdOid b6537a14-6fd3-4cd8-9682-9ea62319ef7a */
	static public RObject construct(String string, Connection con) throws RException {
		System.out.println("is.vector("+con.getTempVarName()+"); is.matrix("+con.getTempVarName()+"); is.table("+con.getTempVarName()+"); is.data.frame("+con.getTempVarName()+")");
//		String type = con.sendCmdRaw("" +
//				"is.vector("+con.getTempVarName()+"); " +
//				"is.matrix("+con.getTempVarName()+")");
				//"is.table("+con.getTempVarName()+");" +
				//"is.data.frame("+con.getTempVarName()+")");
		//System.out.println(type);
		//System.out.println(con.getTempVarName());
		return null;	
	}

}
