import ByteBuffer from "bytebuffer";
import Long from "long";
import ProtoBuf from "protobuf";

import * as DV from "dvconst";
import jQuery from "jquery";

export const ChartOption = ProtoBuf.loadProtoFile(DV.SERVICE_RES_PATH +　"/proto/echart_option.proto");
export const Protocol = ProtoBuf.loadProtoFile(DV.SERVICE_RES_PATH + "/proto/kdd_message.proto");
export const Struct = ProtoBuf.loadProtoFile(DV.SERVICE_RES_PATH + "/proto/kdd_struct.proto");
export const Value = Struct.build("Value");
export const ListValue = Struct.build("ListValue");
export const MapValue = Struct.build("MapValue");
export const NumRange = Struct.build("NumRange");
export const Dim = Struct.build("Dim");

let messageMap = new Map();
let codeMap = new Map();
jQuery.ajax({
   url: DV.SERVICE_RES_PATH + "/messages",
   dataType: "json",
   async: false,
   success: (data) => {
     data.forEach( item => {
        messageMap.set(item.code, Protocol.build(item.name));
        codeMap.set(item.name, item.code)
     });
   }
});

export const MessageMap = messageMap;

export const CodeMap = codeMap;

export class MessageEvent{

   constructor(code, message){
      this.code = code;
      this.message = message;
   }
   setCode(code){
      this.code = code;
      return this;
   }
   setMessage(message){
      this.message = message;
      return this;
   }
   marshal(){
      let messageBytes = this.message.encode();
      let len = 4 + 2 + messageBytes.limit;
      let byteBuffer = new ByteBuffer(6);
      byteBuffer.writeInt32(len).writeInt16(this.code).flip();
      byteBuffer = ByteBuffer.concat([byteBuffer, messageBytes]);
      return byteBuffer.buffer;
   }
   unMarshal(bytes){
      let byteBuffer;
      if(ByteBuffer.isByteBuffer(bytes)){
         byteBuffer = bytes;
      }else{
         byteBuffer = ByteBuffer.wrap(bytes);
      }
      let len = byteBuffer.readInt32();
      let code = byteBuffer.readInt16();
      this.setCode(code);
      let receive = (messageMap.get(code)).decode(byteBuffer.toArrayBuffer());
      this.setMessage(receive);
   }
}
