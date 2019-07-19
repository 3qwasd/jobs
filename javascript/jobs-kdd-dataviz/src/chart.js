import EC from "echarts";
import * as DV from "dvconst";
import dataViz from "dataviz";
import jQuery from "jquery";

export class JobsChart{
   constructor(params, autoRefresh = false){
      this.id = this.generateId();
      this.params = params;
      this.autoRefresh = autoRefresh;
   }
   init(dom){
      this.chart = EC.init(dom);
      this.chart.showLoading();
      this.registe();
      return this;
   }
   registe(){
      dataViz.registe(this.id, this).newChart(this.id, this.params);
   }
   generateId(){
      return jQuery.now();
   }
   setOption(option){
      this.chart.hideLoading();
   }
}
