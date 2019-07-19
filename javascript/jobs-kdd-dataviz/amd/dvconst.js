define(["exports"], function (exports) {
  "use strict";

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  var SERVICE_HOST = exports.SERVICE_HOST = "localhost:8080";
  var SERVICE_PATH = exports.SERVICE_PATH = "http://" + SERVICE_HOST + "/";
  var SERVICE_RES_PATH = exports.SERVICE_RES_PATH = SERVICE_PATH + "res";
  var SERVICE_WSURI = exports.SERVICE_WSURI = "ws://" + SERVICE_HOST + "/websocket";
});
