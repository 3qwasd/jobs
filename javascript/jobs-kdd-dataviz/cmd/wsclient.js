"use strict";

var _dvconst = require("dvconst");

var DV = _interopRequireWildcard(_dvconst);

function _interopRequireWildcard(obj) { if (obj && obj.__esModule) { return obj; } else { var newObj = {}; if (obj != null) { for (var key in obj) { if (Object.prototype.hasOwnProperty.call(obj, key)) newObj[key] = obj[key]; } } newObj.default = obj; return newObj; } }

var socket = new WebSocket(DV.SERVICE_WEBSOCKET_PATH);
