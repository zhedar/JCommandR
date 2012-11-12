package de.hsl.rinterface.commands.statistics;
/***********************************************************************
 * Module:  StatCommands.java
 * Author:  tobo1987
 * Purpose: Defines the Class StatCommands
 ***********************************************************************/

import java.util.List;

import de.hsl.rinterface.commands.RCommand;
import de.hsl.rinterface.objects.RObject;

//TODO in verschiedene Klassen verpacken, jeder Befehl eine eigene Klasse
/** @pdOid 5b80ea91-2314-43e3-b4a0-b01f5b8388ff */
public class StatCommands implements RCommand {
   /** @pdRolelnfo migr=no name=RObject assc=association2 coll=java.util.Collection impl=java.util.HashSet mult=0..* */
   public java.util.Collection<RObject> rObject;
   
   /** @param values
    * @pdOid a7083740-57a3-4b94-8cb7-9144a31ee33e */
   public Number median(List<Number> values) {
      // TODO: implement
      return null;
   }
   
   /** @param values
    * @pdOid 484cbb7b-4639-40d0-bab7-c84c8a80868c */
   public Number pnorm(List<Number> values) {
      // TODO: implement
      return null;
   }
   
   /** @param values
    * @pdOid 4af02638-6410-4a26-962a-05b7b133fec1 */
   public Number mittelwert(List<Number> values) {
      // TODO: implement
      return null;
   }
   
   /** @param values
    * @pdOid c8526948-f31c-40d3-b661-d4df64f4c908 */
   public Number varianz(List<Number> values) {
      // TODO: implement
      return null;
   }
   
   /** @param values
    * @pdOid 4c3aa7c6-ba33-4c5b-ad1d-ab1bf7c30d53 */
   public Number cov(List<Number> values) {
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

@Override
public String prepareForSending()
{
	// TODO Auto-generated method stub
	return null;
}

}