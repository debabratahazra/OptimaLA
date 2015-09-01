/*
     This module was generated automatically from /vobs/ose5/core_ext/dbgserver/private/dbgserverinterface.stl.
                DO NOT EDIT THIS FILE
*/



package com.ose.dbgserver.protocol;
import java.io.*;

public class DBGWriteRegisterReply extends Message implements dbgserverinterfaceConstants{
   public int status;
   public int regVal[];


   public DBGWriteRegisterReply(int _status, int _regVal[]) {
      status = _status;
      regVal = _regVal;
   }

   public DBGWriteRegisterReply(DataInputStream _s) throws IOException { signalNo = 33027; read(_s);}
   public final void sendMessage(DataOutputStream _s) throws IOException { write(_s, this.status, this.regVal);}
   public final static void write(DataOutputStream _s, int _status, int _regVal[]   ) throws IOException {
         int _i;
         _s.writeInt(DBGWRITEREGISTERREPLY);
         int _size=12
            +4+(_regVal.length*4)
            ;
         _s.writeInt(_size);
         _s.writeInt(_status);
         int _dynSize=12;
         int _bytes2Skip;

         // write regVal
         _bytes2Skip=4-_dynSize&3;
         for(_i=0;_i<_bytes2Skip;_i++) _s.writeByte(0);
         _dynSize+=_bytes2Skip;
         _s.writeInt((_regVal.length*4));
         _dynSize+=(_regVal.length*4);
         for(_i = 0 ; _i < _regVal.length ; _i++) 
            _s.writeInt(_regVal[_i]);
   }
   public final void read(DataInputStream _s) throws IOException {
         int _i;
         int _size=_s.readInt();
         status=_s.readInt();
         int _dynSize=12;
         int _bytes2Skip;

         // read regVal
         _bytes2Skip=4-_dynSize&3;
         if(_bytes2Skip!=0) {_s.skipBytes(_bytes2Skip);_dynSize+=_bytes2Skip;}
         _size=_s.readInt();
         _dynSize+=_size;
         _size/=4;
         regVal=new int[_size];
         for(_i = 0 ; _i < _size ; _i++) 
            regVal[_i]=_s.readInt();
   }
}
