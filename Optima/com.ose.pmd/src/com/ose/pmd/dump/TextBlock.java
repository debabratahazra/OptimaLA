/* COPYRIGHT-ENEA-SRC-R1 *
 **************************************************************************
 * Copyright (C) 2006 by Enea Software AB.
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

package com.ose.pmd.dump;

public class TextBlock extends AbstractBlock
{
   private final String[] descs;
   private final long length;

   protected TextBlock(BLHDChunk blhdChunk, String[] descs) throws IffException
   {
      super(blhdChunk);
      this.descs = descs;
      int len = 0;
      for (int i = 0; i < descs.length; i++)
      {
         len += descs[i].length();
      }
      this.length = len;
   }

   public String[] getDescriptions()
   {
      return descs;
   }

   public long getLength()
   {
      return length;
   }
}
