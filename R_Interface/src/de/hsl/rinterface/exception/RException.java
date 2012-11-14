package de.hsl.rinterface.exception;

/***********************************************************************
 * Module: RException.java Author: tobo1987 Purpose: Defines the Class RException
 ***********************************************************************/

/** @pdOid 5b752f97-7d32-42d0-90fb-2558fbeda0cf */
public class RException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2600998308273281714L;

	public RException()
	{
		super();
	}

	public RException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public RException(String message)
	{
		super(message);
	}

	public RException(Throwable cause)
	{
		super(cause);
	}

}