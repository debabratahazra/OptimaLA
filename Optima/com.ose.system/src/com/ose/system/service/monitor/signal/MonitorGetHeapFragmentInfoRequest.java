/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2009 by Enea Software AB.
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

package com.ose.system.service.monitor.signal;

public class MonitorGetHeapFragmentInfoRequest extends MonitorGetInfoRequest
{
   public static final int SIG_NO = 39143;

   public MonitorGetHeapFragmentInfoRequest()
   {
      super(SIG_NO);
   }

   public MonitorGetHeapFragmentInfoRequest(int scopeType, int scopeId)
   {
      super(SIG_NO, 0, scopeType, scopeId, null);
   }
}