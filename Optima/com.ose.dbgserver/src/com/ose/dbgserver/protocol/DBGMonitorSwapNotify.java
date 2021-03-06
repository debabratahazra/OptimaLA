/*
     This module was generated automatically from /vobs/ose5/core_ext/dbgserver/private/dbgserverinterface.stl.
                DO NOT EDIT THIS FILE
*/



package com.ose.dbgserver.protocol;
import java.io.*;

public class DBGMonitorSwapNotify extends Message implements dbgserverinterfaceConstants{
   public short state;
   public short notUsed;
   public int tick;
   public int utick;
   public int abstime;
   public int abstick;
   public int fromPid;
   public int toPid;


   public DBGMonitorSwapNotify(short _state, short _notUsed, int _tick, int _utick, int _abstime, int _abstick, int _fromPid, int _toPid) {
      state = _state;
      notUsed = _notUsed;
      tick = _tick;
      utick = _utick;
      abstime = _abstime;
      abstick = _abstick;
      fromPid = _fromPid;
      toPid = _toPid;
   }

   public DBGMonitorSwapNotify(DataInputStream _s) throws IOException { signalNo = 32931; read(_s);}
   public final void sendMessage(DataOutputStream _s) throws IOException { write(_s, this.state, this.notUsed, this.tick, this.utick, this.abstime, this.abstick, this.fromPid, this.toPid);}
   public final static void write(DataOutputStream _s, short _state, short _notUsed, int _tick, int _utick, int _abstime, int _abstick, int _fromPid, int _toPid   ) throws IOException {
         int _i;
         _s.writeInt(DBGMONITORSWAPNOTIFY);
         int _size=36;
         _s.writeInt(_size);
         _s.writeShort(_state);
         _s.writeShort(_notUsed);
         _s.writeInt(_tick);
         _s.writeInt(_utick);
         _s.writeInt(_abstime);
         _s.writeInt(_abstick);
         _s.writeInt(_fromPid);
         _s.writeInt(_toPid);
   }
   public final void read(DataInputStream _s) throws IOException {
         int _i;
         int _size=_s.readInt();
         state=_s.readShort();
         notUsed=_s.readShort();
         tick=_s.readInt();
         utick=_s.readInt();
         abstime=_s.readInt();
         abstick=_s.readInt();
         fromPid=_s.readInt();
         toPid=_s.readInt();
   }
}
