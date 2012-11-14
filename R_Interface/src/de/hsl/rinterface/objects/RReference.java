package de.hsl.rinterface.objects;

/***********************************************************************
 * Module:  RReference.java
 * Author:  tobo1987
 * Purpose: Defines the Class RReference
 ***********************************************************************/


/** @pdOid 2bb12943-02c9-4e96-a12a-8b89dfc4c7d4 */
public class RReference implements RObject
{
	/** @pdOid a3e51cee-b3ab-48ce-913f-ff1141a1f131 */
	public int name()
	{
		// TODO: implement
		return 0;
	}

	@Override
	public RTypes getType() {
		return RTypes.REFERENCE;
	}

	@Override
	public String toRString()
	{
		// TODO Auto-generated method stub
		return null;
	}

}