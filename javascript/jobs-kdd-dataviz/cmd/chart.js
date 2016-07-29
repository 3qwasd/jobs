"use strict";

Object.defineProperty(exports, "__esModule", {
   value: true
});
exports.JobsChart = undefined;

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _echarts = require("echarts");

var _echarts2 = _interopRequireDefault(_echarts);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var JobsChart = exports.JobsChart = function () {
   function JobsChart(id, params) {
      var autoRefresh = arguments.length <= 2 || arguments[2] === undefined ? false : arguments[2];

      _classCallCheck(this, JobsChart);

      this.id = id;
      this.params = params;
      this.autoRefresh = autoRefresh;
   }

   _createClass(JobsChart, [{
      key: "create",
      value: function create(dom) {
         this.chart = _echarts2.default.init(dom);
         this.chart.showLoading();
         return this;
      }
   }, {
      key: "registe",
      value: function registe() {}
   }, {
      key: "init",
      value: function init(option) {
         this.chart.hideLoading();
      }
   }]);

   return JobsChart;
}();
