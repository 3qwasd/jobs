define(["protobuf", "resources", "dvconst", "wsclient"], function (_protobuf, _resources, _dvconst, _wsclient) {
   "use strict";

   var _protobuf2 = _interopRequireDefault(_protobuf);

   var DV = _interopRequireWildcard(_dvconst);

   var _wsclient2 = _interopRequireDefault(_wsclient);

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

   console.log(_resources.ChartOption);
   console.log(_resources.Protocol);
   console.log(_resources.MessageMap);

   var ws = (0, _wsclient2.default)(DV.SERVICE_WSURI, function (message) {
      console.log(message);
      console.log(JSON.stringify(message));
   });

   var messageEvent = new _resources.MessageEvent();
   var newChart = new (_resources.MessageMap.get(1))();
   console.log(newChart);
   var paramValue = new _resources.Value();
   paramValue.set("stringValue", "asdfsadfasdf");
   console.log(paramValue);
   newChart.setChartId("1111111111111");
   var dim = new _resources.Dim({
      name: "xxx",
      value: {
         stringValue: "xxxxxxxx"
      }
   });
   console.log(dim);
   newChart.params.push(dim);

   messageEvent.setCode(1).setMessage(newChart);
   ws.send(messageEvent.marshal());
});
