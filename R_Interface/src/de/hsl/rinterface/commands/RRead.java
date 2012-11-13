package de.hsl.rinterface.commands;

/***********************************************************************
 * Module: RRead.java Author: tobo1987 Purpose: Defines the Class RRead
 ***********************************************************************/

import java.util.*;

import de.hsl.rinterface.objects.RObject;

/** @pdOid 2fd80f5b-c8a9-4d29-aa6f-077440217b27 */
public class RRead implements RCommand
{
	/**
	 * @pdRoleInfo migr=no name=RObject assc=association3 coll=java.util.Collection
	 *             impl=java.util.HashSet mult=0..*
	 */
	public java.util.Collection<RObject> rObject;

	/**
	 * @param path
	 * @pdOid 4b1e713d-992c-4dc3-a773-70678e68f904
	 */
	public int readcsv(String path)
	{
		// TODO: implement
		return 0;
	}

	/** @pdOid 9e132a4c-7959-4797-8ea2-5b7d10908e19 */
	public int readTable()
	{
		// TODO: implement
		return 0;
	}

	/** @pdGenerated default getter */
	public java.util.Collection<RObject> getRObject()
	{
		if (rObject == null)
			rObject = new java.util.HashSet<RObject>();
		return rObject;
	}

	/** @pdGenerated default iterator getter */
	public java.util.Iterator getIteratorRObject()
	{
		if (rObject == null)
			rObject = new java.util.HashSet<RObject>();
		return rObject.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newRObject
	 */
	public void setRObject(java.util.Collection<RObject> newRObject)
	{
		removeAllRObject();
		for (java.util.Iterator iter = newRObject.iterator(); iter.hasNext();)
			addRObject((RObject) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newRObject
	 */
	public void addRObject(RObject newRObject)
	{
		if (newRObject == null)
			return;
		if (this.rObject == null)
			this.rObject = new java.util.HashSet<RObject>();
		if (!this.rObject.contains(newRObject))
			this.rObject.add(newRObject);
	}

	/**
	 * @pdGenerated default remove
	 * @param oldRObject
	 */
	public void removeRObject(RObject oldRObject)
	{
		if (oldRObject == null)
			return;
		if (this.rObject != null)
			if (this.rObject.contains(oldRObject))
				this.rObject.remove(oldRObject);
	}

	/** @pdGenerated default removeAll */
	public void removeAllRObject()
	{
		if (rObject != null)
			rObject.clear();
	}

	@Override
	public String prepareForSending()
	{
		// TODO Auto-generated method stub
		return null;
	}

}