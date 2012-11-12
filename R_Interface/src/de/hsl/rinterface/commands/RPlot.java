package de.hsl.rinterface.commands;
/***********************************************************************
 * Module:  RPlot.java
 * Author:  tobo1987
 * Purpose: Defines the Class RPlot
 ***********************************************************************/

import java.awt.Color;
import java.util.*;

import de.hsl.rinterface.objects.RVector;

/** @pdOid 39a0896e-bf92-4772-9991-e0c33a78c6ff */
public class RPlot implements RCommand {
   /** @pdOid 79fd6d76-223e-40ae-a028-9a4cb5db1edb */
   private String savePath;
   /** @pdOid af74cd0a-2eb4-405b-9387-2a454d038853 */
   private RVector xCoords;
   /** @pdOid 50b90bbb-0a8d-45c6-b5df-5e6026a85ebf */
   private RVector yCoords;
   /** @pdOid 96421329-6fff-422f-89f8-d2338cf819d0 */
   private String subtitle;
   /** @pdOid 9daeeec5-9a1f-4287-b6b9-9522e9953859 */
   private String title;
   /** @pdOid b8d6ca41-6fe7-4a32-9491-ac4bb14f026f */
   private String xLab;
   /** @pdOid 89f14368-ae70-499a-b221-797363624ee3 */
   private String yLab;
   /** @pdOid fdb9769c-dc26-4a49-b7a4-ec1fda36c1a1 */
   private char  type;
   /** @pdOid e9409dcd-f1c3-4831-a10b-3b29931073e1 */
   private Color col;
   /** @pdOid dd97a996-7d6d-4fb9-9760-18bdc17ef2a5 */
   private int lwd;
   
   /** @pdOid bccb5916-dfcc-4f14-9756-d9b7ce72dcfc */
   public int setterNachDoku() {
      // TODO: implement
      return 0;
   }


	@Override
	public void prepareStatement() {
		// TODO Auto-generated method stub
		
	}
}