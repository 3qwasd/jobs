require.config({
    baseUrl: "amd",
    paths: {
      "jquery": "../lib/jquery-2.2.2.min",
      "bytebuffer": "../lib/bytebuffer.min",
      "long": "../lib/long.min",
      "protobuf": "../lib/protobuf.min",
      "echarts": "../lib/echarts.min",
      "dvconst": "dvconst",
      "resources": "resources",
      "test": "test"
    }
});

require(["test"], function(test) {

});
