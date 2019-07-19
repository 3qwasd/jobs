"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.Protocol = exports.ChartOption = undefined;

var _bytebuffer = require("bytebuffer");

var _bytebuffer2 = _interopRequireDefault(_bytebuffer);

var _long = require("long");

var _long2 = _interopRequireDefault(_long);

var _protobuf = require("protobuf");

var _protobuf2 = _interopRequireDefault(_protobuf);

var _dvconst = require("dvconst");

var DV = _interopRequireWildcard(_dvconst);

var _jquery = require("jquery");

var _jquery2 = _interopRequireDefault(_jquery);

function _interopRequireWildcard(obj) { if (obj && obj.__esModule) { return obj; } else { var newObj = {}; if (obj != null) { for (var key in obj) { if (Object.prototype.hasOwnProperty.call(obj, key)) newObj[key] = obj[key]; } } newObj.default = obj; return newObj; } }

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var ChartOption = exports.ChartOption = _protobuf2.default.loadProtoFile(DV.SERVICE_RES_PATH + "/proto/echart_option.proto").build();
var Protocol = exports.Protocol = _protobuf2.default.loadProtoFile(DV.SERVICE_RES_PATH + "/proto/kdd_message.proto").build();

var response = _jquery2.default.getJSON(DV.SERVICE_RES_PATH + "/messages");
console.log(response.responseJSON);
