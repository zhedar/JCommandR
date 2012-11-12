package de.hs.rinterface.utils;

import java.util.List;

public abstract class RUtils 
{
	/**
	 * Löst eine {@link List} in einen R-Vector auf. c(1,2,3,...) 
	 * @param list die aufzulösende {@link List}
	 * @return Textrepräsentation des Strings
	 */
	static public String resolveList(List<? extends Number> list)
	{
		int count = 0;
		String listCmd = "c(";
		for(Number n : list)
		{
			if(++count != list.size())
				listCmd += n.toString() + ", ";
			else
				listCmd += n.toString() + ")";
		}
	
		return listCmd;
	}
}
