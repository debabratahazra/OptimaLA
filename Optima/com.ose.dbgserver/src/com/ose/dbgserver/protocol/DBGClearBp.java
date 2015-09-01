/*
     This module was generated automatically from /vobs/ose5/core_ext/dbgserver/private/dbgserverinterface.stl.
                DO NOT EDIT THIS FILE
*/



package com.ose.dbgserver.protocol;
import java.io.*;

public class DBGClearBp extends Message implements dbgserverinterfaceConstants{
   public int bpid;


   public DBGClearBp(int _bpid) {
      bpid = _bpid;
   }

   public DBGClearBp(DataInputStream _s) throws IOException { signalNo = 33033; read(_s);}
   public final void sendMessage(DataOutputStream _s) throws IOException { write(_s, this.bpid);}
   public final static void write(DataOutputStream _s, int _bpid   ) throws IOException {
         int _i;
         _s.writeInt(DBGCLEARBP);
         int _size=12;
         _s.writeInt(_size);
         _s.writeInt(_bpid);
   }
   public final void read(DataInputStream _s) throws IOException {
         int _i;
         int _size=_s.readInt();
         bpid=_s.readInt();
   }
}
