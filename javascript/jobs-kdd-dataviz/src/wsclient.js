import {MessageEvent} from "resources";

/**
*WebSocketClient
**/
class WebSocketClient{

   constructor(url, handler){
      this.url = url;
      this.queue = new Array();
      this.handler = handler || ((message) => console.log(message));
   }
   onOpen(event){
      this.sendQueues();
   }
   sendQueues(){
      let oldQueue = this.queue;
      this.queue = new Array();
      oldQueue.forEach((item) => this.websocket.send(item));
   }
   onClose(event){
      this.websocket = undefined;
      if(this.queue.length > 0){
         this.init();
      }
   }
   onMessage(event){
      let messageEvent = new MessageEvent();
      messageEvent.unMarshal(event.data);
      (this.handler)(messageEvent);
   }
   onError(event){
      console.log(JSON.stringify(event))
      this.websocket.close();
   }
   init(){
      this.websocket = new WebSocket(this.url);
      this.websocket.binaryType = "arraybuffer" ;
      this.websocket.onopen = (evt) => this.onOpen(evt);
      this.websocket.onclose = (evt) => this.onClose(evt);
      this.websocket.onmessage = (evt) => this.onMessage(evt);
      this.websocket.onerror = (evt) => this.onError(evt);
   }
   send(message){
      if(this.websocket === undefined){
         this.queue.push(message);
         this.init();
         return;
      }
      switch (this.websocket.readyState) {
         case WebSocket.OPEN :
            this.websocket.send(message);
            return;
         case WebSocket.CONNECTING:
         case WebSocket.CLOSING:
            this.queue.push(message);
            break;
         case WebSocket.CLOSED:
            this.queue.push(message);
            this.init();
            break;
      }
      if(this.websocket.readyState === WebSocket.OPEN){
         this.sendQueues();
      }
   }

}
let wsClient;
export default function WSClient(url, messageHandler){
   if(wsClient === undefined){
      wsClient = new WebSocketClient(url, messageHandler);
   }
   return wsClient;
}
