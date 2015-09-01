/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2007 by Enea Software AB.
 * All rights reserved.
 *
 * This Software is furnished under a software license agreement and
 * may be used only in accordance with the terms of such agreement.
 * Any other use or reproduction is prohibited. No title to and
 * ownership of the Software is hereby transferred.
 *
 * PROPRIETARY NOTICE
 * This Software consists of confidential information.
 * Trade secret law and copyright law protect this Software.
 * The above notice of copyright on this Software does not indicate
 * any actual or intended publication of such Software.
 **************************************************************************
 * COPYRIGHT-END */

package com.ose.xmleditor.validation.conditions;

public class StringCondition extends Condition
{
   private final static String ERROR = "Not a valid string";
   
   protected boolean doValidation(String value)
   {
      if (value != null && value.length() > 0)
      {
         if (!value.startsWith(" "))
         {
            return true;
         }
      }

      return false;
   }

   protected String getErrorMessage()
   {
      return ERROR;
   }
}
