chartProcess = function(params){
	if (this.content) {
		var p = params || {};
		var chart = new Chart(p);
		return chart;
	}
};

STimeVaryChart.prototype.process = chartProcess;

STimeXYChart.prototype.process = chartProcess;

SPieChart.prototype.process = function(params) {
	if (this.content) {
		var p = params || {};
		var chart = new Chart(p);
		return chart;
	}
};

STopNGrid.prototype.process = function(params){
	if (this.content) {
		var p = params || {};
		var grid = new TopNGrid(p);
		return grid;
	}
}

SSingValueView.prototype.process = function(params){
	if (this.content) {
		var p = params || {};
		var view = new ValuesView(p);
		return view;
	}
}
SNoDataProtocol.prototype.process = function(params){
	return new NoDataView(params);
}
STable.prototype.process = function(params){
	if(this.content){
		var p = params || {};
		var view = new Table(p);
		return view;
	}
}
SDetailView.prototype.process = function(params){
	if(params.id == 'V_110198173011111111111'){
		if(this.content && !this.content['当前用户喜好']&&!this.content['周边用户喜好']){
			return new NoDataView1(params);
		}else{
			var p = params || {};
			return new DetailView2(p);
		}
	}else{
		if(this.content){
			var p = params || {};
			var view = new DetailView(p);
			return view;
		}
	}
}
