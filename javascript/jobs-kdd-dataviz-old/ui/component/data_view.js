DataView = function(params){
	var p = params || {};
	(!p.width) && (p.width = "33%");
	(!p.height) && (p.height = "auto");
	this.dataViewid = p.id;
	if(!p.templete)
		p.templete = DATAVIEW_TEMPLETE;
	this.queryParams = p.queryParams;
	var bodyHeight = p.height;
	p.height = "auto";
	Panel.call(this, p);
	if(!this.queryParams) return;
	this.genTitle(this.queryParams);
	// this.head = new SlideAblePanel({
	// 	width: this.getView().width() + "px",
	// 	height: "35px",
	// 	icon: p.queryParams.indices.icon,
	// 	iconOther: p.queryParams.indices.icon,
	// 	text: this.title,
	// 	flag: true
	// });
	this.head = new ToolBarPanel();
	this.head.getView().css("padding-top", "0px");
	this.head.getView().css("padding-bottom", "0px");
	this.head.getView().css("padding-left", "10px");
	this.titleButton = new IconButton({
		icon: p.queryParams.indices.icon,
		text: this.title
	});
	this.head.appendToolLeft(this.titleButton);
	var dv = this;
	
	this.body = new NormalPanel({
		width: this.getView().width()  + "px",
		height: bodyHeight
	});
	var bh = this.body.getView().height();
	this.body.getView().height((bh - 35) + "px");
	this.foot = new ToolBarPanelR({
		height:"25px",
		width: this.getView().width()  + "px"
	});

	this.append(this.head);
	this.append(new ClearDiv());
	this.append(this.body);
	//this.append(this.foot);
	this.append(new ClearDiv());
	this.viewType = this.queryParams.indices.viewType;
	var url = DATA_VLZ_URL;
	NetworkWidget.call(this, url);
	this.cprotocol = CREATEPROTOCOL(this.viewType, this.queryParams);
	this.postData(this.cprotocol);
	this.srcFilters = this.cprotocol.content.filters || {};
}
for (var i in Panel.prototype) {
	DataView.prototype[i] = Panel.prototype[i]
}
for (var i in NetworkWidget.prototype) {
	DataView.prototype[i] = NetworkWidget.prototype[i]
}
DataView.prototype.getLoadingArea = function(){
    return this.body.getView();
}
DataView.prototype.refresh = function(params){
	if (params) {
		for (var key in params) {
			this.cprotocol.content[key] = params[key];
		}
	}
	this.postData(this.cprotocol);
	this.changeTitle();
}
DataView.prototype.changeTitle = function(){
	this.genTitle(this.queryParams);
	this.titleButton.changeTitle(this.title);
	//this.head.changeTitle(this.title);
}
DataView.prototype.setFilters = function(filters){
	this.cprotocol.content.filters = {};
	this.cprotocol.content.from = 0;
	for(var key in this.srcFilters){
		this.cprotocol.content.filters[key] = this.srcFilters[key];
	}
	for(var key in filters){
		this.cprotocol.content.filters[key] = filters[key];
	}
	return this;
}
DataView.prototype.setRangeDate = function(date){
	this.cprotocol.content.date = date;
}
DataView.prototype.init = function(data){
	var protocol = new (ProtocolMap[data.id])(data.content);
	var p = {
		id: this.dataViewid,
		width:this.body.getView().width() + "px",
		height: this.body.getView().height() + "px"
	};
	var chart = protocol.process(p);
	this.appendChart(chart);
	this.chart = chart;
	chart.init(protocol.content);
	this.chart.dataTable = protocol.content.dataTable;
	var dv = this;
	if(dv.ds){
		this.chart.ds = dv.ds;
	}
	if(this.chart.getView().width() > 150)
		if(this.chart.dataTable){
			if(!this.csvButton){
				this.csvButton = new IconButton({
					text: "CSV",
					icon: "table",
					fontSize: "12px",
					click: function(){
						if(dv.chart.dataTable)
							exportToCsv(dv.tableName + ".csv", dv.chart.dataTable);
					}
				});
				this.head.appendToolRight(this.csvButton);
			}
		}
}
DataView.prototype.appendChart = function(chart){
	this.body.getView().find("#dataview_chart_area").remove();
	this.body.getView().append(chart.getView().attr("id", "dataview_chart_area"));
	chart.setDataItemListener(this);
}
DataView.prototype.dataItemSelect = function(dataItem){}

DataView.prototype.genTitle = function(p){
	this.tableName = p.date.text + "_" + p.indices.description;
	this.title = DATE_VIEW_TITLE_TEMPLETE.replace("{date}", p.date ? p.date.text : "......").replace("{desciption}", p.indices.description);
}

DashBoardDataView = function(params){
	var p = params || {};
	p.templete = DASHBOARD_DATAVIEW_TEMPLETE;
	DataView.call(this, p);
}
DashBoardDataView.prototype = new DataView();
DashBoardDataView.prototype.genTitle = function(p){
	this.title = p.indices.description;
}