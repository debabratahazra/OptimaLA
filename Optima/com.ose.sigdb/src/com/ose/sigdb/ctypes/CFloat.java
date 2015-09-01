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

package com.ose.sigdb.ctypes;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;

import com.ose.sigdb.util.Format;

/**
 * This class represents the C float type as a 32 bit floating point value
 * according to IEEE 754
 */
public class CFloat extends CPrimType
{

   /**
    * @param _defaultEndian
    */
   public CFloat(int _defaultEndian)
   {
      super("float", 4, _defaultEndian, "%f");
   }

   /**
    * @param value
    * @param data
    * @param offset
    * @param endian
    */
   public void assign(Object value, byte[] data, int offset, int endian)
   {
      try
      {
         Number num = (Number) value;
         ByteArrayOutputStream bs = new ByteArrayOutputStream(size);
         DataOutputStream s = new DataOutputStream(bs);

         s.writeFloat(num.floatValue());
         byte[] bytes = bs.toByteArray();

         if (endian == CType.BIG_ENDIAN)
         {
            for (int i = 0; i < size; i++)
            {
               data[offset + i] = bytes[i];
            }
         }
         else if (endian == CType.LITTLE_ENDIAN)
         {
            for (int i = 0; i < size; i++)
            {
               data[offset + i] = bytes[size - (i + 1)];
            }
         }
         else
         {
            throw new Error("Unknown endian.");
         }
      }
      catch (IOException e)
      {
         throw new Error("??? " + e.toString());
      }
   }

   /**
    * @param value
    * @param offset
    * @param endian
    * @param format
    * @return
    */
   public String toString(byte[] value, int offset, int endian, String format)
   {
      /*
       * DataInputStream allways treat data as big endian, so if little endian
       * we must swap the bytes.
       */
      byte[] correctEndian;
      int index;

      if (endian == CType.LITTLE_ENDIAN)
      {
         correctEndian = swapEndian(value, offset);
         index = 0; // reset the offset for the new byte array.
      }
      else
      {
         correctEndian = value;
         index = offset;
      }

      DataInputStream s = new DataInputStream(new ByteArrayInputStream(
            correctEndian, index, size));
      double dvalue = 0;

      try
      {
         dvalue = s.readFloat();
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }

      String form;

      if (format == null)
      {
         form = defaultFormat;
      }
      else
      {
         form = format;
      }

      String result;

      try
      {
         result = (new Format(form)).form(dvalue);
      }
      catch (java.lang.IllegalArgumentException e)
      {
         // An incorrect format string so try with the default one.
         result = (new Format(defaultFormat)).form(dvalue);
      }

      return result;
   }
}
