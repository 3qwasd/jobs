"use strict";

var _dvconst = require("dvconst");

var DV = _interopRequireWildcard(_dvconst);

var _chart = require("chart");

var _resources = require("resources");

function _interopRequireWildcard(obj) { if (obj && obj.__esModule) { return obj; } else { var newObj = {}; if (obj != null) { for (var key in obj) { if (Object.prototype.hasOwnProperty.call(obj, key)) newObj[key] = obj[key]; } } newObj.default = obj; return newObj; } }

var chartMap = new Map();
var websocket = new WebSocket(DV.SERVICE_WSURI);

websocket.onopen = function (evt) {
  onOpen(evt);
};
websocket.onclose = function (evt) {
  onClose(evt);
};
websocket.onmessage = function (evt) {
  onMessage(evt);
};
websocket.onerror = function (evt) {
  onError(evt);
};
