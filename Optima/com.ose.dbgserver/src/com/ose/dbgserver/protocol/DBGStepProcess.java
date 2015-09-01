/*
     This module was generated automatically from /vobs/ose5/core_ext/dbgserver/private/dbgserverinterface.stl.
                DO NOT EDIT THIS FILE
*/



package com.ose.dbgserver.protocol;
import java.io.*;

public class DBGStepProcess extends Message implements dbgserverinterfaceConstants{
   public int pid;
   public int type;
   public int start;
   public int end;


   public DBGStepProcess(int _pid, int _type, int _start, int _end) {
      pid = _pid;
      type = _type;
      start = _start;
      end = _end;
   }

   public DBGStepProcess(DataInputStream _s) throws IOException { signalNo = 33035; read(_s);}
   public final void sendMessage(DataOutputStream _s) throws IOException { write(_s, this.pid, this.type, this.start, this.end);}
   public final static void write(DataOutputStream _s, int _pid, int _type, int _start, int _end   ) throws IOException {
         int _i;
         _s.writeInt(DBGSTEPPROCESS);
         int _size=24;
         _s.writeInt(_size);
         _s.writeInt(_pid);
         _s.writeInt(_type);
         _s.writeInt(_start);
         _s.writeInt(_end);
   }
   public final void read(DataInputStream _s) throws IOException {
         int _i;
         int _size=_s.readInt();
         pid=_s.readInt();
         type=_s.readInt();
         start=_s.readInt();
         end=_s.readInt();
   }
}
