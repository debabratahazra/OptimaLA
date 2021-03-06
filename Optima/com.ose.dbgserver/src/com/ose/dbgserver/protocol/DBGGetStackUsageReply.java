/*
     This module was generated automatically from /vobs/ose5/core_ext/dbgserver/private/dbgserverinterface.stl.
                DO NOT EDIT THIS FILE
*/



package com.ose.dbgserver.protocol;
import java.io.*;

public class DBGGetStackUsageReply extends Message implements dbgserverinterfaceConstants{
   public int pid;
   public int status;
   public int stackStart;
   public int stackEnd;
   public int max;


   public DBGGetStackUsageReply(int _pid, int _status, int _stackStart, int _stackEnd, int _max) {
      pid = _pid;
      status = _status;
      stackStart = _stackStart;
      stackEnd = _stackEnd;
      max = _max;
   }

   public DBGGetStackUsageReply(DataInputStream _s) throws IOException { signalNo = 32968; read(_s);}
   public final void sendMessage(DataOutputStream _s) throws IOException { write(_s, this.pid, this.status, this.stackStart, this.stackEnd, this.max);}
   public final static void write(DataOutputStream _s, int _pid, int _status, int _stackStart, int _stackEnd, int _max   ) throws IOException {
         int _i;
         _s.writeInt(DBGGETSTACKUSAGEREPLY);
         int _size=28;
         _s.writeInt(_size);
         _s.writeInt(_pid);
         _s.writeInt(_status);
         _s.writeInt(_stackStart);
         _s.writeInt(_stackEnd);
         _s.writeInt(_max);
   }
   public final void read(DataInputStream _s) throws IOException {
         int _i;
         int _size=_s.readInt();
         pid=_s.readInt();
         status=_s.readInt();
         stackStart=_s.readInt();
         stackEnd=_s.readInt();
         max=_s.readInt();
   }
}
