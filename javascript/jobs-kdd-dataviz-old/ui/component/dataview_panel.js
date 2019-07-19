DataViewPanel = function(params){
	var p = params || {};
	(!p.width) && (p.width = "100%");
	p.templete = DATAVIEW_PANEL_TEMPLETE;
	Panel.call(this, p);
	this.head = new ToolBarPanel();
	this.head.getView().css("padding-top", "0px");
	this.head.getView().css("padding-bottom", "0px");
	this.head.getView().css("padding-left", "30px");
	this.body = new NormalPanel({
		width: p.width
	});
	this.getView().append(this.head.getView());
	this.getView().append(this.body.getView());
	this.body.append(new ClearDiv());
	this.viewMap = {};
}
DataViewPanel.prototype = new Panel();
DataViewPanel.prototype.createHeadTools = function(){
	this.head.appendToolRight(new IconButton({
		icon: "lock",
		text: "锁定",
		fontSize: 12
	}));
	this.head.appendToolRight(new IconButton({
		icon: "cancel-circle",
		text: "清除",
		fontSize: 12
	}));
}
DataViewPanel.prototype.appendHeadLeft = function(widget){
	this.head.appendToolLeft(widget);
}
DataViewPanel.prototype.appendHeadRight = function(widget){
	this.head.appendToolRight(widget)
}
DataViewPanel.prototype.createDataView = function(queryParams, w, h){
	console.log(queryParams);
	var neww;
	var newh;
	if(queryParams.indices.indicPath == "/mediamatch/analysis/net_event/down_dbmv/down_dbmv_exception"
		||queryParams.indices.indicPath == "/mediamatch/analysis/net_event/up_mer_db/up_mer_db_exception"
		||queryParams.indices.indicPath == "/mediamatch/analysis/net_event/down_snr_db/down_snr_db_exception"
		||queryParams.indices.indicPath == "/mediamatch/analysis/net_event/up_dbmv/up_dbmv_exception"){
		neww = this.getMain().width() - 10 + "px";
		newh = "600px";
	}else{
		neww = this.getMain().width() / 2 - 10 + "px";
	}
	var viewId = "V_" + queryParams.id.toUpperCase();

	var params = {
		id: viewId,
		queryParams: queryParams,
		width: w || neww,
		height: h || newh || "320px"
	};
	var view = this.viewMap[viewId];
	if(!view){
		view = new DataView(params);
		this.viewMap[viewId] = view;
	}else{
		view.getView().remove();
		view.refresh();

	}
	return view;	
}
DataViewPanel.prototype.createDashBoardDataView = function(queryParams, w, h){
	var viewId = "V_" + queryParams.id.toUpperCase();
	console.log(viewId);
	var params = {
		id: viewId,
		queryParams: queryParams,
		width: w || this.getMain().width() / 2 - 10 + "px",
		height: h || "320px"
	};
	var view = this.viewMap[viewId];
	if(!view){
		view = new DashBoardDataView(params);
		this.viewMap[viewId] = view;
	}else{
		view.getView().remove();
		view.refresh();
	}
	return view;	
}
DataViewPanel.prototype.prependDataView = function(view){
	this.body.getView().prepend(view.getView());
	view.getView().hide();
	view.getView().show(500);
}
DataViewPanel.prototype.appendDataView = function(view){
	this.body.getView().append(view.getView());
}
DataViewPanel.prototype.getMain = function(){
	return this.body.getView();
}
