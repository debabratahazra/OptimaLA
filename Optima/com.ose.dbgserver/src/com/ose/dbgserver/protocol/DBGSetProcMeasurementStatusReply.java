/*
     This module was generated automatically from /vobs/ose5/core_ext/dbgserver/private/dbgserverinterface.stl.
                DO NOT EDIT THIS FILE
*/



package com.ose.dbgserver.protocol;
import java.io.*;

public class DBGSetProcMeasurementStatusReply extends Message implements dbgserverinterfaceConstants{
   public int measurementStatus;
   public int uInterval;
   public int currentTimeTick;
   public int currentTimeUTick;


   public DBGSetProcMeasurementStatusReply(int _measurementStatus, int _uInterval, int _currentTimeTick, int _currentTimeUTick) {
      measurementStatus = _measurementStatus;
      uInterval = _uInterval;
      currentTimeTick = _currentTimeTick;
      currentTimeUTick = _currentTimeUTick;
   }

   public DBGSetProcMeasurementStatusReply(DataInputStream _s) throws IOException { signalNo = 33005; read(_s);}
   public final void sendMessage(DataOutputStream _s) throws IOException { write(_s, this.measurementStatus, this.uInterval, this.currentTimeTick, this.currentTimeUTick);}
   public final static void write(DataOutputStream _s, int _measurementStatus, int _uInterval, int _currentTimeTick, int _currentTimeUTick   ) throws IOException {
         int _i;
         _s.writeInt(DBGSETPROCMEASUREMENTSTATUSREPLY);
         int _size=24;
         _s.writeInt(_size);
         _s.writeInt(_measurementStatus);
         _s.writeInt(_uInterval);
         _s.writeInt(_currentTimeTick);
         _s.writeInt(_currentTimeUTick);
   }
   public final void read(DataInputStream _s) throws IOException {
         int _i;
         int _size=_s.readInt();
         measurementStatus=_s.readInt();
         uInterval=_s.readInt();
         currentTimeTick=_s.readInt();
         currentTimeUTick=_s.readInt();
   }
}
