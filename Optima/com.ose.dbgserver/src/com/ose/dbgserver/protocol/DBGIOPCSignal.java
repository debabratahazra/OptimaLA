/*
     This module was generated automatically from /vobs/ose5/core_ext/dbgserver/private/dbgserverinterface.stl.
                DO NOT EDIT THIS FILE
*/



package com.ose.dbgserver.protocol;
import java.io.*;

public class DBGIOPCSignal extends Message implements dbgserverinterfaceConstants{
   public int sessionHandle;
   public int pid;
   public byte data[];


   public DBGIOPCSignal(int _sessionHandle, int _pid, byte _data[]) {
      sessionHandle = _sessionHandle;
      pid = _pid;
      data = _data;
   }

   public DBGIOPCSignal(DataInputStream _s) throws IOException { signalNo = 33021; read(_s);}
   public final void sendMessage(DataOutputStream _s) throws IOException { write(_s, this.sessionHandle, this.pid, this.data);}
   public final static void write(DataOutputStream _s, int _sessionHandle, int _pid, byte _data[]   ) throws IOException {
         int _i;
         _s.writeInt(DBGIOPCSIGNAL);
         int _size=16
            +4+_data.length
            ;
         _s.writeInt(_size);
         _s.writeInt(_sessionHandle);
         _s.writeInt(_pid);
         int _dynSize=16;
         int _bytes2Skip;

         // write data
         _bytes2Skip=4-_dynSize&3;
         for(_i=0;_i<_bytes2Skip;_i++) _s.writeByte(0);
         _dynSize+=_bytes2Skip;
         _s.writeInt(_data.length);
         _dynSize+=_data.length;
         _s.write(_data);
   }
   public final void read(DataInputStream _s) throws IOException {
         int _i;
         int _size=_s.readInt();
         sessionHandle=_s.readInt();
         pid=_s.readInt();
         int _dynSize=16;
         int _bytes2Skip;

         // read data
         _bytes2Skip=4-_dynSize&3;
         if(_bytes2Skip!=0) {_s.skipBytes(_bytes2Skip);_dynSize+=_bytes2Skip;}
         _size=_s.readInt();
         _dynSize+=_size;
         data=new byte[_size];
         _s.readFully(data,0,_size);
   }
}