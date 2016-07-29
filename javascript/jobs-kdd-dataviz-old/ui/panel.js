
/**
 * 普通面板
 */
NormalPanel = function(params){
	var p = params || {};
	(!p.width) && (p.width = "100%");
	(!p.height) && (p.height = "auto");
	
	p.templete = NORAML_PANEL_TEMPLETE;
	Panel.call(this, p);
}
NormalPanel.prototype = new Panel();

/**
 * 选项卡面板
 * @param {params:Object}
 */
TabPanel = function(params){
	params = params || {};
	params.templete = TAB_PANEL_TEMPLETE;
	Panel.call(this, params);
	this.tabs = params.tabs || new Array();

	for(var i = 0; i < this.tabs.length; i++){
		this.appendTab(this.tabs[i]);
	}
}
TabPanel.prototype = new Panel();
TabPanel.prototype.appendTab = function(tab){
	this.view.find("ul").append($("<li></li>").append(tab.tab.getView()));
	this.append(tab)
	return this;
}
TabPanel.prototype.refresh = function() {
	this.view.kendoTabStrip({
		animation: {
			close: {
				duration: 500,
				effects: "slideUp"
			},
			open: {
				duration: 500,
				effects: "slideDown"
			}
		}
	});
	return this;
}
TabPanel.prototype.select = function(i){
	this.view.data("kendoTabStrip").select(i);
}
/**
 * 选项卡
 * @param {params:Object}
 */
Tab = function(params){
	if(params == null || params == undefined || !(params instanceof Object)) 
		return this;
	this.tab = new IconTitle(params);
	(!params.width) && (params.width = "100%");
	(!params.height) && (params.height = "auto");
	(!params.padding) && (params.padding = "5px");
	params.templete = TAB_CONTENT_TEMPLETE;
	Panel.call(this, params);
}
Tab.prototype = new Panel();
Tab.prototype.getMain = function(){
	return this.view.find("#tab_content_body");
}
Tab.prototype.appendToFoot = function(widget){
	this.view.find("#tab_cntent_foot").append(widget.getView());
	return this;
}
/**
 * 具有可隐藏面板的组件
 * @param {params:Object}
 */
SlideAblePanel = function(params){
	if(params == null || params == undefined || !( params instanceof Object )) 
		return this;
	if(!params.width){
		params.width = "50%";
	}
	if(!params.height){
		params.height = "auto";
	}
	if(!params.position){
		params.position = "absolute";
	}
	if(!params.fontSize){
		params.fontSize = "12px"
	}
	params.templete = SLIDEABLE_PANEL_TEMPLETE;
	Panel.call(this, params);
	var panel = this;
	this.button = new IconButton(params);
	this.button.click = function(){
		panel.clickButton();
	}
	this.head = this.view.find("#panel_head");
	if(!params.flag){
		this.head.append(new ClearDiv().getView());
		this.appendToHead(this.button);
	}else{
		this.head.css("padding-left", "10px")
		this.head.append(this.button.getView());
	}

	this.body = this.getMain();
	this.body.hide();
}
SlideAblePanel.prototype = new Panel();
SlideAblePanel.prototype.clickButton = function(){
	if (this.body.is(":hidden")) {
		this.slideDown();
	} else {
		this.slideUp();
	}
}
SlideAblePanel.prototype.getMain = function(){
	return this.view.find("#panel_body");
}
SlideAblePanel.prototype.changeTitle = function(title){
	this.button.changeText(title);
}
SlideAblePanel.prototype.slideDown = function(){
	this.body.slideDown(500);
	this.button.changeIcon();
}
SlideAblePanel.prototype.slideUp = function(){
	this.body.slideUp(500);
	this.button.changeIcon();
}
SlideAblePanel.prototype.appendToHead = function(widget){
	this.head.find(".clear_div").before(new FloatContainer(widget).getView());
}
/**
 * 工具栏面板
 * @param {[type]} params [description]
 */
ToolBarPanel = function(params){
	var p = params || {};
	p.templete = TOOL_BAR_BOTTOM_TEMPLETE;
	(!p.height) && (p.height = "auto");
	Panel.call(this, p);
	this.tools = new Array();
	this.append(new ClearDiv());
}
ToolBarPanel.prototype = new Panel();
ToolBarPanel.prototype.appendTool = function(widget){
	this.getMain().find(".clear_div").before(new FloatContainer().append(widget).getView());
	this.tools[widget.id] = widget;
	return this;
}
ToolBarPanel.prototype.appendToolLeft = function(widget){
	this.getMain().find(".clear_div").before(new FloatContainer().append(widget).getView());
	this.tools[widget.id] = widget;
	return this;
}
ToolBarPanel.prototype.appendToolRight = function(widget){
	this.getMain().find(".clear_div").before(new FloatContainerR().append(widget).getView());
	this.tools[widget.id] = widget;
	return this;
}


ToolBarPanelR = function(params){
	ToolBarPanel.call(this, params);
}
ToolBarPanelR.prototype = new ToolBarPanel();
ToolBarPanelR.prototype.appendTool = function(widget){
	this.getMain().prepend(new FloatContainerR().append(widget).getView());
	this.tools.push(widget);
	return this;
}
/**
 * 标签栏
 * @param {[type]} params [description]
 */
TagBarPanel = function(params){
	ToolBarPanel.call(this, params);
	this.tags = {};
	this.tagNum = 0;
}
TagBarPanel.prototype = new ToolBarPanel();
TagBarPanel.prototype.addTag = function(tagParams){
	if(this.tags["P" + tagParams.key]) return this;
	var tag = new Tag(tagParams);
	var tagPanel = this;
	tag.close = function() {
		Widget.prototype.close.call(this);
		tagPanel.tagNum --;
		delete tagPanel.tags["P" + tagParams.key];
		if (tagPanel.tagNum == 0)
			tagPanel.hide();
	};
	this.append(tag);
	if (this.tagNum == 0)
		this.show();
	this.tagNum++;
	this.tags["P" + tagParams.key] = tag;
}
TagBarPanel.prototype.getTagValues = function(){
	if(this.tagNum == 0) return null;
	var tagValues = new Object();
	for(var key in this.tags){
		var tag = this.tags[key];
		if(!tagValues[tag.id]){
			tagValues[tag.id] = new Array();
		}
		tagValues[tag.id].push(tag.getCurrVal());
	}
	return tagValues;
}