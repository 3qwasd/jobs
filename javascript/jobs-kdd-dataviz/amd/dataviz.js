define(["exports", "dvconst", "chart", "resources", "wsclient"], function (exports, _dvconst, _chart, _resources, _wsclient) {
   "use strict";

   Object.defineProperty(exports, "__esModule", {
      value: true
   });
   exports.dataViz = undefined;

   var DV = _interopRequireWildcard(_dvconst);

   var _wsclient2 = _interopRequireDefault(_wsclient);

   function _interopRequireDefault(obj) {
      return obj && obj.__esModule ? obj : {
         default: obj
      };
   }

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

   var CNewChart = _resources.Protocol.build("CNewChart");

   var DataVizManager = function () {
      function DataVizManager() {
         _classCallCheck(this, DataVizManager);

         this.chartMap = new Map();
         this.wsClient = (0, _wsclient2.default)(DV.SERVICE_WSURI, this.processEvent.bind(this));
      }

      _createClass(DataVizManager, [{
         key: "registe",
         value: function registe(id, chart) {
            this.chartMap.set(id, chart);
            return this;
         }
      }, {
         key: "newChart",
         value: function newChart(id, params) {
            var cnewchart = new CNewChart();
            cnewchart.setChartId(id);
            var msgEvt = new _resources.MessageEvent(_resources.CodeMap.get("CNewChart"), cnewchart);
            this.wsClient.send(msgEvt.marshal());
            return this;
         }
      }, {
         key: "processEvent",
         value: function processEvent(messageEvent) {
            console.log(messageEvent);
         }
      }]);

      return DataVizManager;
   }();

   var dataVizManger = new DataVizManager();
   var dataViz = exports.dataViz = dataVizManger;
});
