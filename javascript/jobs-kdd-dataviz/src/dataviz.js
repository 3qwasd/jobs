import * as DV from "dvconst";
import {JobsChart} from "chart";
import {ChartOption, Protocol, MessageMap, CodeMap, MessageEvent} from "resources";
import WSClient from "wsclient";

const CNewChart = Protocol.build("CNewChart");

class DataVizManager{
   constructor(){
      this.chartMap = new Map();
      this.wsClient = WSClient(DV.SERVICE_WSURI, this.processEvent.bind(this));
   }
   registe(id, chart){
      this.chartMap.set(id, chart);
      return this;
   }
   newChart(id, params){
      let cnewchart = new CNewChart();
      cnewchart.setChartId(id);
      let msgEvt = new MessageEvent(CodeMap.get("CNewChart"), cnewchart);
      this.wsClient.send(msgEvt.marshal());
      return this;
   }
   processEvent(messageEvent){
      console.log(messageEvent);
   }
}
const dataVizManger = new DataVizManager();
export const dataViz = dataVizManger;
