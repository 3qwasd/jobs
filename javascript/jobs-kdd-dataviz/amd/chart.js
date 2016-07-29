define(["exports", "echarts", "dvconst", "dataviz", "jquery"], function (exports, _echarts, _dvconst, _dataviz, _jquery) {
   "use strict";

   Object.defineProperty(exports, "__esModule", {
      value: true
   });
   exports.JobsChart = undefined;

   var _echarts2 = _interopRequireDefault(_echarts);

   var DV = _interopRequireWildcard(_dvconst);

   var _dataviz2 = _interopRequireDefault(_dataviz);

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

   var JobsChart = exports.JobsChart = function () {
      function JobsChart(params) {
         var autoRefresh = arguments.length <= 1 || arguments[1] === undefined ? false : arguments[1];

         _classCallCheck(this, JobsChart);

         this.id = this.generateId();
         this.params = params;
         this.autoRefresh = autoRefresh;
      }

      _createClass(JobsChart, [{
         key: "init",
         value: function init(dom) {
            this.chart = _echarts2.default.init(dom);
            this.chart.showLoading();
            this.registe();
            return this;
         }
      }, {
         key: "registe",
         value: function registe() {
            _dataviz2.default.registe(this.id, this).newChart(this.id, this.params);
         }
      }, {
         key: "generateId",
         value: function generateId() {
            return _jquery2.default.now();
         }
      }, {
         key: "setOption",
         value: function setOption(option) {
            this.chart.hideLoading();
         }
      }]);

      return JobsChart;
   }();
});
