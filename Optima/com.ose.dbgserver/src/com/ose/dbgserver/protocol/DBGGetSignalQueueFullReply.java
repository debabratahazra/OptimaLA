/*
     This module was generated automatically from /vobs/ose5/core_ext/dbgserver/private/dbgserverinterface.stl.
                DO NOT EDIT THIS FILE
*/



package com.ose.dbgserver.protocol;
import java.io.*;

public class DBGGetSignalQueueFullReply extends Message implements dbgserverinterfaceConstants{
   public int pid;
   public int from;
   public int addressee;
   public int signalSize;
   public int signalNumber;
   public int entry;
   public byte data[];


   public DBGGetSignalQueueFullReply(int _pid, int _from, int _addressee, int _signalSize, int _signalNumber, int _entry, byte _data[]) {
      pid = _pid;
      from = _from;
      addressee = _addressee;
      signalSize = _signalSize;
      signalNumber = _signalNumber;
      entry = _entry;
      data = _data;
   }

   public DBGGetSignalQueueFullReply(DataInputStream _s) throws IOException { signalNo = 33016; read(_s);}
   public final void sendMessage(DataOutputStream _s) throws IOException { write(_s, this.pid, this.from, this.addressee, this.signalSize, this.signalNumber, this.entry, this.data);}
   public final static void write(DataOutputStream _s, int _pid, int _from, int _addressee, int _signalSize, int _signalNumber, int _entry, byte _data[]   ) throws IOException {
         int _i;
         _s.writeInt(DBGGETSIGNALQUEUEFULLREPLY);
         int _size=32
            +4+_data.length
            ;
         _s.writeInt(_size);
         _s.writeInt(_pid);
         _s.writeInt(_from);
         _s.writeInt(_addressee);
         _s.writeInt(_signalSize);
         _s.writeInt(_signalNumber);
         _s.writeInt(_entry);
         int _dynSize=32;
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
         pid=_s.readInt();
         from=_s.readInt();
         addressee=_s.readInt();
         signalSize=_s.readInt();
         signalNumber=_s.readInt();
         entry=_s.readInt();
         int _dynSize=32;
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
