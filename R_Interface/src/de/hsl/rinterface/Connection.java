package de.hsl.rinterface;
/***********************************************************************
 * Module:  Connection.java
 * Author:  tobo1987
 * Purpose: Defines the Interface Connection
 ***********************************************************************/

import java.io.IOException;
import java.util.*;

import de.hsl.rinterface.commands.RCommand;
import de.hsl.rinterface.exception.RException;
import de.hsl.rinterface.objects.RObject;

/** @pdOid e6210a27-8e1e-4a76-bcad-337c166cb6c3 */
public interface Connection {
   /** @throws IOException 
 * @throws InterruptedException 
 * @throws RException 
 * @pdOid a61f12ea-6797-4ed4-9806-8ae82df2ed87 */
   void close() throws IOException, InterruptedException, RException;
   /** @pdOid e52857a7-cc5b-4beb-8ca8-10a178dd8d7e */
   boolean isAlive();
   /** @param cmd
 * @throws RException 
    * @pdOid 22f909e0-bb81-4af9-8822-9463eee5e9fd */
   RObject sendCmd(String cmd) throws RException;
   /** @param cmd
    * @pdOid 29da57f2-9615-46b9-bd33-8931bbaa23c6 */
   RObject sendCmd(RCommand cmd);
   /** @pdOid fe0ba04f-ad7a-43b1-be41-6fbfc377bcf7 */
   List<String> getAllVars();

}