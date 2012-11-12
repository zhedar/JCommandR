package de.hsl.rinterface;
/***********************************************************************
 * Module:  RParser.java
 * Author:  tobo1987
 * Purpose: Defines the Class RParser
 ***********************************************************************/

import de.hsl.rinterface.objects.RObject;

/** @pdOid 0331b484-3a04-4082-9cca-3c92db3656d8 */
public class RParser {
   /** @pdRoleInfo migr=no name=RObject assc=association4 coll=java.util.Collection impl=java.util.HashSet mult=0..* type=Aggregation */
   public java.util.Collection<RObject> rObject;
   
   /** @pdOid b6537a14-6fd3-4cd8-9682-9ea62319ef7a */
   public RObject construct() {
      // TODO: implement
      return null;
   }
   
   
   /** @pdGenerated default getter */
   public java.util.Collection<RObject> getRObject() {
      if (rObject == null)
         rObject = new java.util.HashSet<RObject>();
      return rObject;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorRObject() {
      if (rObject == null)
         rObject = new java.util.HashSet<RObject>();
      return rObject.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newRObject */
   public void setRObject(java.util.Collection<RObject> newRObject) {
      removeAllRObject();
      for (java.util.Iterator iter = newRObject.iterator(); iter.hasNext();)
         addRObject((RObject)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newRObject */
   public void addRObject(RObject newRObject) {
      if (newRObject == null)
         return;
      if (this.rObject == null)
         this.rObject = new java.util.HashSet<RObject>();
      if (!this.rObject.contains(newRObject))
         this.rObject.add(newRObject);
   }
   
   /** @pdGenerated default remove
     * @param oldRObject */
   public void removeRObject(RObject oldRObject) {
      if (oldRObject == null)
         return;
      if (this.rObject != null)
         if (this.rObject.contains(oldRObject))
            this.rObject.remove(oldRObject);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllRObject() {
      if (rObject != null)
         rObject.clear();
   }

}