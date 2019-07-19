
ParamsToolBar = function(params){
	var p = params || {};
	ToolBarPanel.call(this, p);
	this.groupBys = new DropDownList({
		id: "groupBys",
		text: "TOPN分组",
		data: COMMON_FILTERS,
		width:150
	});
	this.rangeUnit = new DropDownList({
		id: "rangeUnit",
		text: "范围单位",
		data: RNAGE_UNIT_ARRAY,
		width:70
	});
	this.sliceUnit = new DropDownList({
		id: "sliceUnit",
		text: "切片单位",
		data: [UNIT_MONTH],
		width:70
	});
	this.appendTool(this.rangeUnit)
	.appendTool(new NumberInput({
		id: "rangeLen",
		text: "单位长度",
		width:70	
	}))
	.appendTool(new NumberInput({
		id: "topN",
		text: "TOPN",
		min:10,
		max:100,
		step:10
	}))
	.appendTool(this.sliceUnit)
	.appendTool(new NumberInput({
		id: "sliceLen",
		text: "切片长度",
		width:70
	})).appendTool(new NumberInput({
		id: "threshold",
		text: "剩余天数",
		width:70
	})).appendTool(new NumberInput({
		id: "min",
		text: "最低值",
		width:70,
		min:1,
		max: 100000
	})).appendTool(new NumberInput({
		id: "max",
		text: "最高值",
		width:70,
		value:100,
		min:1,
		max: 100000
	}))
	.appendTool(this.groupBys);
	this.init();
}
ParamsToolBar.prototype = new ToolBarPanel();

ParamsToolBar.prototype.refreshGroupBys = function(fields){
	this.groupBys.dataSource = fields;
	this.groupBys.init();
}

ParamsToolBar.prototype.showPart = function(params){
	for(var key in this.tools){
		this.tools[key].hide();
	}
	for(var i = 0; i < params.length; i++){
		this.tools[params[i]] && this.tools[params[i]].show();
	}
}

ParamsToolBar.prototype.getParams = function(){
	var params = {};
	for (var key in this.tools) {
		var widget = this.tools[key];
		if (!widget.isHide()) {
			if (key == "groupBys") {
				params[widget.id] = [widget.getCurrVal() + ":" + widget.getCurrText()];
			} else {
				params[widget.id] = widget.getCurrVal();
			}
		}
	}
	return params;
}
ParamsToolBar.prototype.getCurrVal = function(){
	return this.getParams();
}
ParamsToolBar.prototype.init = function(){
	var toolbar = this;
	this.rangeUnit.change = function(){
		DropDownList.prototype.change.call(this);
		var sliceUnitData;
		switch (this.getCurrVal()) {
		case YEAR_SHORT:
			sliceUnitData = [UNIT_MONTH];
			break;
		case MONTH_SHORT:
			sliceUnitData = [UNIT_DAY];
			break;
		case DAY_SHORT:
			sliceUnitData = [UNIT_HOUR];
			break;
		case HOUR_SHORT:
			sliceUnitData = [UNIT_MINUTER];
			break;
		}
		toolbar.sliceUnit.rebuild(sliceUnitData);
	}
	this.rangeUnit.kendo.select(2);
	this.rangeUnit.change();
};
