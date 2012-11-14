package de.hsl.rinterface.objects;
/***********************************************************************
 * Module:  RObject.java
 * Author:  tobo1987
 * Purpose: Defines the Interface RObject
 ***********************************************************************/


/** @pdOid cefcaac4-11e4-4207-8026-4f884e70f39b */
public interface RObject {
   /** @param name
    * @pdOid 134d06ca-ded9-4935-ade9-fecbec14344a */
   void save(String name);
   /** @param name
    * @pdOid 2e936f6a-b4e2-4aa3-afa9-8ea335c24f39 */
   RObject load(String name);
   String toRString();
   RTypes getType();

}