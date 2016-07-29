define(["exports", "bytebuffer", "long", "protobuf", "dvconst", "jquery"], function (exports, _bytebuffer, _long, _protobuf, _dvconst, _jquery) {
   "use strict";

   Object.defineProperty(exports, "__esModule", {
      value: true
   });
   exports.MessageEvent = exports.CodeMap = exports.MessageMap = exports.Dim = exports.NumRange = exports.MapValue = exports.ListValue = exports.Value = exports.Struct = exports.Protocol = exports.ChartOption = undefined;

   var _bytebuffer2 = _interopRequireDefault(_bytebuffer);

   var _long2 = _interopRequireDefault(_long);

   var _protobuf2 = _interopRequireDefault(_protobuf);

   var DV = _interopRequireWildcard(_dvconst);

   var _jquery2 = _interopRequireDefault(_jquery);

   function _interopRequireWildcard(obj) {
      if (obj && obj.__esModule) {
         return obj;
      } else {
         var newObj = {};

         if (obj != null) {
            for (var key in obj) {
               if (Object.prototype.hasOwnProperty.call(obj, key)) newObj[key] = obj[key];
            }
         }

         newObj.default = obj;
         return newObj;
      }
   }

   function _interopRequireDefault(obj) {
      return obj && obj.__esModule ? obj : {
         default: obj
      };
   }

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

   var ChartOption = exports.ChartOption = _protobuf2.default.loadProtoFile(DV.SERVICE_RES_PATH + "/proto/echart_option.proto");
   var Protocol = exports.Protocol = _protobuf2.default.loadProtoFile(DV.SERVICE_RES_PATH + "/proto/kdd_message.proto");
   var Struct = exports.Struct = _protobuf2.default.loadProtoFile(DV.SERVICE_RES_PATH + "/proto/kdd_struct.proto");
   var Value = exports.Value = Struct.build("Value");
   var ListValue = exports.ListValue = Struct.build("ListValue");
   var MapValue = exports.MapValue = Struct.build("MapValue");
   var NumRange = exports.NumRange = Struct.build("NumRange");
   var Dim = exports.Dim = Struct.build("Dim");

   var messageMap = new Map();
   var codeMap = new Map();
   _jquery2.default.ajax({
      url: DV.SERVICE_RES_PATH + "/messages",
      dataType: "json",
      async: false,
      success: function success(data) {
         data.forEach(function (item) {
            messageMap.set(item.code, Protocol.build(item.name));
            codeMap.set(item.name, item.code);
         });
      }
   });

   var MessageMap = exports.MessageMap = messageMap;

   var CodeMap = exports.CodeMap = codeMap;

   var MessageEvent = exports.MessageEvent = function () {
      function MessageEvent(code, message) {
         _classCallCheck(this, MessageEvent);

         this.code = code;
         this.message = message;
      }

      _createClass(MessageEvent, [{
         key: "setCode",
         value: function setCode(code) {
            this.code = code;
            return this;
         }
      }, {
         key: "setMessage",
         value: function setMessage(message) {
            this.message = message;
            return this;
         }
      }, {
         key: "marshal",
         value: function marshal() {
            var messageBytes = this.message.encode();
            var len = 4 + 2 + messageBytes.limit;
            var byteBuffer = new _bytebuffer2.default(6);
            byteBuffer.writeInt32(len).writeInt16(this.code).flip();
            byteBuffer = _bytebuffer2.default.concat([byteBuffer, messageBytes]);
            return byteBuffer.buffer;
         }
      }, {
         key: "unMarshal",
         value: function unMarshal(bytes) {
            var byteBuffer = void 0;
            if (_bytebuffer2.default.isByteBuffer(bytes)) {
               byteBuffer = bytes;
            } else {
               byteBuffer = _bytebuffer2.default.wrap(bytes);
            }
            var len = byteBuffer.readInt32();
            var code = byteBuffer.readInt16();
            this.setCode(code);
            var receive = messageMap.get(code).decode(byteBuffer.toArrayBuffer());
            this.setMessage(receive);
         }
      }]);

      return MessageEvent;
   }();
});
