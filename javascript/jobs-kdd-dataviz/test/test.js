import ProtoBuf from "protobuf";
import {
   ChartOption, Protocol,
   Value, ListValue, MapValue, NumRange, Dim,
   MessageMap, MessageEvent
} from "resources";
import * as DV from "dvconst";
import WSClient from "wsclient";

console.log(ChartOption);
console.log(Protocol);
console.log(MessageMap);

let ws = WSClient(DV.SERVICE_WSURI, (message)=>{
   console.log(message);
   console.log(JSON.stringify(message));
});

let messageEvent = new MessageEvent();
let newChart = new (MessageMap.get(1))();
console.log(newChart);
let paramValue = new Value();
paramValue.set("name", "x");
paramValue.set("stringValue", "asdfsadfasdf");
console.log(paramValue);
newChart.setChartId("1111111111111");
let value = new Value();
value.set("stringValue", "xxxxxxxx");
let dim = new Dim({name: "xxx"});
dim.set("value", value);
console.log(dim);
newChart.params.push(dim);


messageEvent.setCode(1).setMessage(newChart);
ws.send(messageEvent.marshal());
