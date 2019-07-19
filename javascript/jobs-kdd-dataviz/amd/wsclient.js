define(["exports", "resources"], function (exports, _resources) {
   "use strict";

   Object.defineProperty(exports, "__esModule", {
      value: true
   });
   exports.default = WSClient;

   function _classCallCheck(instance, Constructor) {
      if (!(instance instanceof Constructor)) {
         throw new TypeError("Cannot call a class as a function");
      }
   }

   var _createClass = function () {
      function defineProperties(target, props) {
         for (var i = 0; i < props.length; i++) {
            var descriptor = props[i];
            descriptor.enumerable = descriptor.enumerable || false;
            descriptor.configurable = true;
            if ("value" in descriptor) descriptor.writable = true;
            Object.defineProperty(target, descriptor.key, descriptor);
         }
      }

      return function (Constructor, protoProps, staticProps) {
         if (protoProps) defineProperties(Constructor.prototype, protoProps);
         if (staticProps) defineProperties(Constructor, staticProps);
         return Constructor;
      };
   }();

   var WebSocketClient = function () {
      function WebSocketClient(url, handler) {
         _classCallCheck(this, WebSocketClient);

         this.url = url;
         this.queue = new Array();
         this.handler = handler || function (message) {
            return console.log(message);
         };
      }

      _createClass(WebSocketClient, [{
         key: "onOpen",
         value: function onOpen(event) {
            this.sendQueues();
         }
      }, {
         key: "sendQueues",
         value: function sendQueues() {
            var _this = this;

            var oldQueue = this.queue;
            this.queue = new Array();
            oldQueue.forEach(function (item) {
               return _this.websocket.send(item);
            });
         }
      }, {
         key: "onClose",
         value: function onClose(event) {
            this.websocket = undefined;
            if (this.queue.length > 0) {
               this.init();
            }
         }
      }, {
         key: "onMessage",
         value: function onMessage(event) {
            var messageEvent = new _resources.MessageEvent();
            messageEvent.unMarshal(event.data);
            this.handler(messageEvent);
         }
      }, {
         key: "onError",
         value: function onError(event) {
            console.log(JSON.stringify(event));
            this.websocket.close();
         }
      }, {
         key: "init",
         value: function init() {
            var _this2 = this;

            this.websocket = new WebSocket(this.url);
            this.websocket.binaryType = "arraybuffer";
            this.websocket.onopen = function (evt) {
               return _this2.onOpen(evt);
            };
            this.websocket.onclose = function (evt) {
               return _this2.onClose(evt);
            };
            this.websocket.onmessage = function (evt) {
               return _this2.onMessage(evt);
            };
            this.websocket.onerror = function (evt) {
               return _this2.onError(evt);
            };
         }
      }, {
         key: "send",
         value: function send(message) {
            if (this.websocket === undefined) {
               this.queue.push(message);
               this.init();
               return;
            }
            switch (this.websocket.readyState) {
               case WebSocket.OPEN:
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
            if (this.websocket.readyState === WebSocket.OPEN) {
               this.sendQueues();
            }
         }
      }]);

      return WebSocketClient;
   }();

   var wsClient = void 0;
   function WSClient(url, messageHandler) {
      if (wsClient === undefined) {
         wsClient = new WebSocketClient(url, messageHandler);
      }
      return wsClient;
   }
});
