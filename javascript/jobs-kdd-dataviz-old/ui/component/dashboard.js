DashBoard = function(params){
	var p = params || {};
	(!p.width) && (p.width = "100%");
	(!p.height) && (p.height = "auto");
	p.id = params.id || "dashboard_panel";
	p.templete = DASH_BOARD;
	Panel.call(this, p);
	this.path = params.path;
	var url = DASHBOARD_BASE_URL + params.path;
	NetworkWidget.call(this, url);
	this.loadData();
}

for (var i in Panel.prototype) {
	DashBoard.prototype[i] = Panel.prototype[i]
}
for (var i in NetworkWidget.prototype) {
	DashBoard.prototype[i] = NetworkWidget.prototype[i]
}
DashBoard.prototype.createParamsPanel = function() {
	
	this.datePanel = new DatePanel({
		width: "50%",
		parentid: this.id
	});
	this.append(new ClearDiv());
	this.queryPanel = new QueryPanel({
		width: this.getView().width() + "px"
	});
	this.append(this.queryPanel);
	this.append(new ClearDiv());
	var height = this.getView().height() -
		this.datePanel.getView().height() -
		this.queryPanel.getView().height();
	this.dvph = height;
	this.dvpw = this.getView().width();
	this.dataViewPanel = new DataViewPanel({
		parentid: this.id,
		height: this.dvph + "px",
		width: this.dvpw + "px"
	});
	var db = this;
	this.queryPanel.connDatePanel(this.datePanel);
	this.queryPanel.connDataViewPanel(this.dataViewPanel);
	this.queryPanel.search = function(){
		db.search();
	}
	this.datePanel.dateChange = function(){
		db.refreshByDate();
	}
}
DashBoard.prototype.getLoadingArea = function(){
    return this.getView();
}
DashBoard.prototype.init = function(data){
	this.logoPanel = new SlideAblePanel({
		text: data.text,
		icon: data.icon,
		fontSize: "16px",
		width: "50%",
		parentid: this.id
	});
	var db = this;
	this.createParamsPanel();
	this.masterViews = new Array();
	this.dateViews = new Array();//受时间影响的view
	this.filterViews = new Array();//受过滤器影响的view
	this.flowerViews = new Array();//受master影响的view
	this.allViews = {};
	for(var i = 0; i < data.indices.length; i++){
		var indic = data.indices[i];
		var h = indic.height;
		var w = indic.width;
		var view = this.dataViewPanel.createDashBoardDataView(indic, (this.dvpw - 15) * w  + "px", (this.dvph - 80) * 	h  + "px");
		this.dataViewPanel.appendDataView(view);
		var categoryCode = indic.indiceType;
		if(categoryCode & ISMASTER_MASK_CODE){
			this.masterViews.push(view);
			view.dataItemSelect = function(dataItem){
				var filterFields = this.cprotocol.content.indices.filterFields;
				var filters = {};
				for(var i = 0; i < filterFields.length; i++){
					filters[filterFields[i]] = new Array();
					filters[filterFields[i]].push(dataItem[filterFields[i]]);
				}
				db.refreshByMaster(filters);
			}
		}
		view.ds = db;
		if(categoryCode & DATE_MASK_CODE){
			this.dateViews.push(view);
		}
		if(categoryCode & MASTER_MASK_CODE){
			this.flowerViews.push(view);
		}
		if(categoryCode & FILTER_MASK_CODE){
			this.filterViews.push(view);
		}
		this.allViews["D" + indic.id] = view;
	}
}
DashBoard.prototype.search = function(){
	for(var i = 0; i < this.filterViews.length; i++){
		var view = this.filterViews[i];
		var tagValues = this.queryPanel.tagPanel.getTagValues();
		view.setFilters(tagValues);
		view.refresh();
	}
}
DashBoard.prototype.refreshFavorite = function(filters){
	this.allViews["Dzhibopindao"].setFilters(filters).refresh();
	this.allViews["Dzhibojiemu"].setFilters(filters).refresh();
	this.allViews["Djiemudaoyan"].setFilters(filters).refresh();
	this.allViews["Djiemuyanyuan"].setFilters(filters).refresh();
}
DashBoard.prototype.refreshByDate = function(){
	for(var i = 0; i < this.dateViews.length; i++){
		var view = this.dateViews[i];
		var date = this.datePanel.getCurrVal();
		view.setRangeDate(date);
		view.refresh();
	}
}
DashBoard.prototype.refreshByMaster = function(filters){
	
	for(var i = 0; i < this.flowerViews.length; i++){
		var view = this.flowerViews[i];
		view.setFilters(filters);
		view.refresh();
	}
}