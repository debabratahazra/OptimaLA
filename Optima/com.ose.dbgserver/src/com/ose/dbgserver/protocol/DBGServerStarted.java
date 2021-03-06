/*
     This module was generated automatically from /vobs/ose5/core_ext/dbgserver/private/dbgserverinterface.stl.
                DO NOT EDIT THIS FILE
*/



package com.ose.dbgserver.protocol;
import java.io.*;

public class DBGServerStarted extends Message implements dbgserverinterfaceConstants{
   public int serverPort;
   public int notUsed1;
   public int notUsed2;
   public String name;
   public String info;


   public DBGServerStarted(int _serverPort, int _notUsed1, int _notUsed2, String _name, String _info) {
      serverPort = _serverPort;
      notUsed1 = _notUsed1;
      notUsed2 = _notUsed2;
      name = _name;
      info = _info;
   }

   public DBGServerStarted(DataInputStream _s) throws IOException { signalNo = 33049; read(_s);}
   public final void sendMessage(DataOutputStream _s) throws IOException { write(_s, this.serverPort, this.notUsed1, this.notUsed2, this.name, this.info);}
   public final static void write(DataOutputStream _s, int _serverPort, int _notUsed1, int _notUsed2, String _name, String _info   ) throws IOException {
         int _i;
         int _nameSize=_name.length()+1;
         int _infoSize=_info.length()+1;
         _s.writeInt(DBGSERVERSTARTED);
         int _size=20
            +4+((_nameSize & 3) == 0 ? _nameSize : (_nameSize + 4 - (_nameSize&3)))
            +4+_infoSize
            ;
         _s.writeInt(_size);
         _s.writeInt(_serverPort);
         _s.writeInt(_notUsed1);
         _s.writeInt(_notUsed2);
         int _dynSize=20;
         int _bytes2Skip;

         // write name
         _bytes2Skip=4-_dynSize&3;
         for(_i=0;_i<_bytes2Skip;_i++) _s.writeByte(0);
         _dynSize+=_bytes2Skip;
         _s.writeInt(_nameSize);
         _dynSize+=_nameSize;
         _s.writeBytes(_name);
         _s.writeByte(0);

         // write info
         _bytes2Skip=4-_dynSize&3;
         for(_i=0;_i<_bytes2Skip;_i++) _s.writeByte(0);
         _dynSize+=_bytes2Skip;
         _s.writeInt(_infoSize);
         _dynSize+=_infoSize;
         _s.writeBytes(_info);
         _s.writeByte(0);
   }
   public final void read(DataInputStream _s) throws IOException {
         int _i;
         int _size=_s.readInt();
         serverPort=_s.readInt();
         notUsed1=_s.readInt();
         notUsed2=_s.readInt();
         int _dynSize=20;
         int _bytes2Skip;

         // read name
         _bytes2Skip=4-_dynSize&3;
         if(_bytes2Skip!=0) {_s.skipBytes(_bytes2Skip);_dynSize+=_bytes2Skip;}
         _size=_s.readInt();
         _dynSize+=_size;
         byte _name[] = new byte[_size];
         _s.readFully(_name, 0, _size);
         name=new String(_name, 0,0, _name.length-1);

         // read info
         _bytes2Skip=4-_dynSize&3;
         if(_bytes2Skip!=0) {_s.skipBytes(_bytes2Skip);_dynSize+=_bytes2Skip;}
         _size=_s.readInt();
         _dynSize+=_size;
         byte _info[] = new byte[_size];
         _s.readFully(_info, 0, _size);
         info=new String(_info, 0,0, _info.length-1);
   }
}
