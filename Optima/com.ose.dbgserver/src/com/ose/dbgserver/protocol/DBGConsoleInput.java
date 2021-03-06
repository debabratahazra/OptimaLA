/*
     This module was generated automatically from /vobs/ose5/core_ext/dbgserver/private/dbgserverinterface.stl.
                DO NOT EDIT THIS FILE
*/



package com.ose.dbgserver.protocol;
import java.io.*;

public class DBGConsoleInput extends Message implements dbgserverinterfaceConstants{
   public byte buffer[];


   public DBGConsoleInput(byte _buffer[]) {
      buffer = _buffer;
   }

   public DBGConsoleInput(DataInputStream _s) throws IOException { signalNo = 33052; read(_s);}
   public final void sendMessage(DataOutputStream _s) throws IOException { write(_s, this.buffer);}
   public final static void write(DataOutputStream _s, byte _buffer[]   ) throws IOException {
         int _i;
         _s.writeInt(DBGCONSOLEINPUT);
         int _size=8
            +4+_buffer.length
            ;
         _s.writeInt(_size);
         int _dynSize=8;
         int _bytes2Skip;

         // write buffer
         _bytes2Skip=4-_dynSize&3;
         for(_i=0;_i<_bytes2Skip;_i++) _s.writeByte(0);
         _dynSize+=_bytes2Skip;
         _s.writeInt(_buffer.length);
         _dynSize+=_buffer.length;
         _s.write(_buffer);
   }
   public final void read(DataInputStream _s) throws IOException {
         int _i;
         int _size=_s.readInt();
         int _dynSize=8;
         int _bytes2Skip;

         // read buffer
         _bytes2Skip=4-_dynSize&3;
         if(_bytes2Skip!=0) {_s.skipBytes(_bytes2Skip);_dynSize+=_bytes2Skip;}
         _size=_s.readInt();
         _dynSize+=_size;
         buffer=new byte[_size];
         _s.readFully(buffer,0,_size);
   }
}
