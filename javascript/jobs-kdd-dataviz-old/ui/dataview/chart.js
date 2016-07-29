
Chart = function(params){
	var p = params || {};
	p.templete = CHART_DIV_TEMPLETE;
	(!p.width)&&(p.width = "auto");
	(!p.height)&&(p.height = "auto");
	(!p.padding)&&(p.padding = "0px");
	Widget.call(this, p);
}
Chart.prototype = new Widget();
Chart.prototype.init = function(chartParams){
	if(this.getView().height() < 150){
		if(chartParams.legend)
			chartParams.legend.visible = false;
	}
	this.chart = this.getView().kendoChart(chartParams).data("kendoChart");
}

NoDataView = function(params){
	Chart.call(this, params);
}
NoDataView.prototype = new Chart();
NoDataView.prototype.init = function(chartParams) {
	this.getView().append(new ErrorDiv({
		fontSize: "16px",
		height: this.getView().height() + "px",
		width: this.getView().width() + "px",
		icon: chartParams.icon,
		text: chartParams.message
	}).getView());	
}
NoDataView1 = function(params){
	Chart.call(this, params);
}
NoDataView1.prototype = new Chart();
NoDataView1.prototype.init = function(chartParams) {
	this.getView().append(new ErrorDiv({
		fontSize: "16px",
		height: this.getView().height() + "px",
		width: this.getView().width() + "px",
		icon: chartParams.icon,
		text: "暂无数据"
	}).getView());	
}
Grid = function(params){
	var p = params || {};
	p.templete = CHART_DIV_TEMPLETE;
	(!p.height)&&(p.height = "auto");
	(!p.padding)&&(p.padding = "0px");
	Widget.call(this, p);
}
Grid.prototype = new Widget();
Grid.prototype.init = function(chartParams){
	this.grid = this.view.kendoGrid(chartParams).data("kendoGrid");
	var grid = this;
	this.grid.setOptions({
		change: function(){
			grid.itemSelect();
		}
	});
}

Table = function(params){
	var p = params || {};
	p.templete = TABLE_DIV_TEMPLETE;
	(!p.height)&&(p.height = "auto");
	(!p.padding)&&(p.padding = "0px");
	Widget.call(this, p);
	this.pageBar = new ToolBarPanel();
	var table = this;
	this.tableDiv = new NormalPanel({
		width: this.getView().width() - 2 + "px",
		height: (this.getView().height() - 50) + "px"
	});
	this.getView().find("#tableArea").height((this.getView().height() - 50) + "px");
	this.getView().find("#tableArea").append(this.tableDiv.getView());
	this.tableDiv.getView().css("board", "none");
	PageButton = function(params){
		TextButton.call(this,params);
		this.num = 1;
		this.getView().css("margin", "0px");
		this.getView().css("padding", "0px");
	}
	PageButton.prototype = new TextButton();
	PageButton.prototype.click = function(){
		table.turnPage(this.num);
	}
	this.first = new PageButton({icon:"first"});
	this.last = new PageButton({icon:"last"});
	this.prev = new PageButton({icon:"backward2"});
	this.next = new PageButton({icon:"forward3"});
	this.pageBar.appendTool(this.first);
	this.pageBar.appendTool(this.prev);
	this.numButtons = new Array(); 
	
	for(var i = 1; i < 6; i++){
		var numButton = new PageButton({text: i});
		numButton.num = i;
		this.numButtons.push(numButton);
		this.pageBar.appendTool(numButton);
	}
	this.pageBar.appendTool(this.next);
	this.pageBar.appendTool(this.last);

	this.getView().append(this.pageBar.getView());
}
Table.prototype = new Grid();
Table.prototype.getMain = function(){
	return this.tableDiv.getView();
}
Table.prototype.turnPage = function(pageNum){
	this.first.enable(false);
	this.last.enable(false);
	this.prev.enable(false);
	this.next.enable(false);
	for(var i = 0; i < this.numButtons.length; i++){
		this.numButtons[i].enable(false);
	}
	this.dataItemListener.refresh({
		from: (pageNum - 1) * 20
	});
}
Table.prototype.refreshPageBar = function(pageBarParams){
	for(var i = pageBarParams.startPage; i <= pageBarParams.endPage; i++){
		var index = i - pageBarParams.startPage;
		var button = this.numButtons[index];
		button.changeText(i);
		button.num = i;
		if(i == pageBarParams.currPage){
			button.enable(false);
		}else{
			button.enable(true);
		}
	}
	var gap = pageBarParams.endPage - pageBarParams.startPage + 1;
	for(var i = gap; i < this.numButtons.length; i++){
		this.numButtons[i].hide();
	}
	this.first.num = pageBarParams.firstPage;
	this.last.num = pageBarParams.lastPage;
	this.prev.num = pageBarParams.prePage;
	this.next.num = pageBarParams.nextPage;
	if(this.first.num == pageBarParams.currPage){
		this.first.enable(false);
	}else{
		this.first.enable(true);
	}
	if(this.last.num == pageBarParams.currPage){
		this.last.enable(false);
	}else{
		this.last.enable(true);
	}
	if(this.prev.num < 1){
		this.prev.enable(false);
	}else{
		this.prev.enable(true);
	}
	if(this.next.num > pageBarParams.totalPage){
		this.next.enable(false);
	}else{
		this.next.enable(true);
	}
	if(this.last.num == 0)
		this.last.enable(false);
}
Table.prototype.init = function(chartParams){
	this.grid = this.getMain().kendoGrid(chartParams).data("kendoGrid");
	var grid = this;
	this.grid.setOptions({
		change: function(){
			grid.itemSelect();
		}
	});
	this.refreshPageBar(chartParams.pageBar);
}
Table.prototype.itemSelect = function(){
	var rowIndex = this.grid.select()[0].rowIndex;
	this.currDataItem = this.grid.dataSource.data()[rowIndex];
	Widget.prototype.itemSelect.call(this);
}
TopNGrid = function(params){
	Grid.call(this, params);
}
TopNGrid.prototype = new Grid();
TopNGrid.prototype.init = function(chartParams){
	for(var i = 0; i < chartParams.columns.length; i++){
		var column = chartParams.columns[i];
		if(column.field == "orderNum"){
			column.width = "10%";
			column.template = TOPN_GRID_NUM_TEMPLETE;
			column.title = "排名";
		}
		if(column.field == "name"){
			column.width = "50%";
			(!column.title) && (column.title = "名称");
		}
		if(column.field == "size"){
			column.width = "20%";
			column.template = TOPN_GRID_BAR_TEMPLETE;
			column.title = "得分";
		}
		if(column.field == "value"){
			column.width = "20%";
			column.template = TOPN_GRID_VALUE_TEMPLETE.replace("{valueUnit}", column.unit || "");
		}
	}
	Grid.prototype.init.call(this, chartParams);
	this.grid.thead.css("border", "none");
	this.grid.tbody.find("tr").css("border","none");
	this.grid.tbody.find("td").css("border","none");
}
SingValueView = function(params){
	var p = params || {};
	p.templete = VALUE_ITEM_TEMPLETE;
	Widget.call(this, p);
}
SingValueView.prototype = new Widget();

ValuesView = function(params){
	var p = params || {};
	p.templete = CHART_DIV_TEMPLETE;
	(!p.height)&&(p.height = "auto");
	(!p.padding)&&(p.padding = "0px");
	Widget.call(this, p);
}
ValuesView.prototype = new Widget();
ValuesView.prototype.init = function(data){
	for(var i = 0; i < data.items.length; i++){
		var p = data.items[i];
		p.fontSize = "20px";
		p.width = this.getView().width() + "px";
		p.height = this.getView().height() + "px";
		this.append(new SingValueView(p));
	}
}

ItemValueView = function(params){
	var p = params || {};
	p.templete = ITEM_VIEW_TEMPLETE;
	Widget.call(this, p);
}
ItemValueView.prototype = new Widget();

DetailView = function(params){
	var p = params || {};
	ValuesView.call(this, p);
}
DetailView.prototype = new ValuesView();
DetailView.prototype.init = function(data) {
	
	this.tabPanel = new TabPanel();
	this.tagFilter = {};
	for(var key in data){
		if(key === "dataTable") continue;
		if(key === "当前用户喜好" || key === "周边用户喜好"){
			continue;
		}else{
			this.createNormalTab(data[key], key);
		}
    }
    this.append(this.tabPanel.refresh());
    this.tabPanel.select(0);
}
DetailView.prototype.createTagTab = function(tagDatas, text){
	var tab = new Tab({
		icon: "",
		text: text,
		height: this.view.height() - 40 + "px",
		padding: "0"
	});
	var normalPanel = new NormalPanel();
	var dv = this;
	normalPanel.getMain().css("text-align", "left")
	.css("padding", "10px");
	tab.append(normalPanel);
	console.log(tagDatas);
	tagDatas.forEach(function(tagData, index, array){
		normalPanel.append(new IconTitle({
			text: tagData.text + "："
		}));
		var tags = tagData.value.split(',');
		tags.forEach(function(val, index, array){
			var button = new ToggleButton({
				text: val
			});
			button.field = tagData.field;
			normalPanel.append(button);
			button.click = function(){
				ToggleButton.prototype.click.call(this);
				if(this.toggle){
					dv.addLikeTag(this.field, button.getMain().text());
				}else{
					dv.removeLikeTag(this.field, button.getMain().text());
				}
				dv.ds.refreshFavorite(dv.tagFilter);
			}
		});
		normalPanel.getMain().append($("<br>"));
	});

	this.tabPanel.appendTab(tab);
}
DetailView.prototype.addLikeTag = function(field, tagContent){
	if(!this.tagFilter[field]){
		this.tagFilter[field] = new Array();
	}
	this.tagFilter[field].push(tagContent);
}
DetailView.prototype.removeLikeTag = function(field, tagContent){
	if(!this.tagFilter[field]){
		return;
	}
	for(var i = 0; i < this.tagFilter[field].length; i++){
		if(this.tagFilter[field][i] == tagContent) break;
	}
	this.tagFilter[field].splice(i, 1);
}
DetailView.prototype.createNormalTab = function(items, text) {
	var tab = new Tab({
		icon: "",
		text: text,
		height: this.view.height() - 40 + "px",
		padding: "0"
	});
	tab.getMain().kendoListView({
		dataSource: {
			data: items
		},
		template: ITEM_VIEW_TEMPLETE
	});
	tab.append(new ClearDiv());
	this.tabPanel.appendTab(tab);
}
DetailView2 = function(params){
	var p = params || {};
	DetailView.call(this, p);
}
DetailView2.prototype = new DetailView();
DetailView2.prototype.init = function(data) {
	
	this.tabPanel = new TabPanel();
	this.tagFilter = {};
	console.log(data);
	for(var key in data){
		if(key === "dataTable") continue;
		if(key === "当前用户喜好" || key === "周边用户喜好"){
			this.createNormalTab(data[key], key);
			continue;
		}
    }
    this.append(this.tabPanel.refresh());
    this.tabPanel.select(0);
}