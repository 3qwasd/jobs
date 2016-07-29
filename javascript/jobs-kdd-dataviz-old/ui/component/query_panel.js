QueryPanel = function(params){
	var p = params || {};
	p.text = "标签";
	p.icon = "price-tags";
	p.iconOther = "price-tags";
	(!p.position) && (p.position = "relative");
	(!p.width) && (p.width = "100%");

	SlideAblePanel.call(this, p);
	this.filterType = new DropDownList({
		data:VIEW_EVENT_QUERY_FILTERS,
		width:150
	});
	this.fullTextInput = new ButtonInputBox({
		width:this.getView().width()*0.60 + "px"
	});
	this.searchButton = new IconButton({
		icon: "search",
		text: "查询",
		fontSize: 14
	});
	this.tagPanel = new TagBarPanel();
	this.appendToHead(this.filterType);
	this.appendToHead(this.fullTextInput);
	this.appendToHead(this.searchButton);
	this.getMain().css("z-index", "1000");
	this.append(this.tagPanel);
	this.init();
	var queryPanel = this;
	this.fullTextInput.getView().find("input").keyup(function(){
		queryPanel.searchTip();
	});
}
QueryPanel.prototype = new SlideAblePanel();
QueryPanel.prototype.searchTip = function(){
	var queryPanel = this;
	$.ajax({
		type: 'POST',
		timeout: TIMEOUT,
		async: true,
		url: BASE_URL + "app/ui/res/searchtip",
		data: JSON.stringify({
			fieldName: queryPanel.filterType.getCurrVal(),
			text: queryPanel.fullTextInput.getCurrVal()
		}),
		success: function(data) {
			//console.log(data);
			if(data.length > 0){
				queryPanel.fullTextInput.tipDiv().empty();
				for(var i = 0; i < data.length; i++){
					var text = data[i];
					var html = "<a href='javascript:void(0)'><div>"+ text + "</div></a>";
					var item = $(html);
					item.click(function(){
						queryPanel.fullTextInput.setTipText($(this).find("div").text());
						queryPanel.fullTextInput.tipDiv().hide();
					});
					queryPanel.fullTextInput.tipDiv().append(item);
				}
				queryPanel.fullTextInput.tipDiv().show();
			}else{
				queryPanel.fullTextInput.tipDiv().hide();
			}
		},
		error: function() {

		},
		contentType: 'application/json; charset=utf-8',
		dataType: 'json'
	});
}	
QueryPanel.prototype.init = function() {
	var queryPanel = this;
	this.tagPanel.hide = function() {
		queryPanel.slideUp();
	}
	this.tagPanel.show = function() {
		queryPanel.slideDown();
	}
	this.fullTextInput.click = function() {
		var p = {};
		var reg = new RegExp('"', "g");
		p.id = queryPanel.filterType.getCurrVal();
		p.value = queryPanel.fullTextInput.getCurrVal().replace(reg, "\"") || "*";
		p.text = queryPanel.filterType.getCurrText() + "：" + p.value;
		p.key = hex_md5(p.id + p.value);
		queryPanel.tagPanel.addTag(p);
	}
	this.searchButton.click = function(){
		queryPanel.search();
		
	}
}
QueryPanel.prototype.refreshFilters = function(fields){
	this.filterType.dataSource = fields;
	this.filterType.init();
}
QueryPanel.prototype.connIndicesPanel = function(panel){
	this.indicesPanel = panel;
}
QueryPanel.prototype.connDatePanel = function(panel){
	this.datePanel = panel;
}
QueryPanel.prototype.connParamsPanel = function(panel){
	this.paramsPanel = panel;
}
QueryPanel.prototype.connDataViewPanel = function(panel){
	this.dataViewPanel = panel;
}
QueryPanel.prototype.search = function(){
	var queryPanel = this;
	var tagValues = queryPanel.tagPanel.getTagValues();
	console.log("tagValues:" + tagValues);
	var indices = queryPanel.indicesPanel.getCurrVal();
	var date = queryPanel.datePanel.getCurrVal();
	var queryParams = queryPanel.paramsPanel.getCurrVal();
	var id = hex_md5(indices.indicPath + JSON.stringify(date.val) + JSON.stringify(queryParams) + JSON.stringify(tagValues));
	queryParams.filters = tagValues;
	queryParams.indices = indices;
	queryParams.date = date;
	queryParams.id = id;
	console.log(JSON.stringify(queryParams));
	var view = queryPanel.dataViewPanel.createDataView(queryParams);
	queryPanel.dataViewPanel.prependDataView(view);
}