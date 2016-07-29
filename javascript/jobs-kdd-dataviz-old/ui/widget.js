/**
 * clear div 清除左右浮动的组件
 */
ClearDiv = function(){
	Widget.call(this,{
		templete: CLEAR_DIV_TEMPLETE
	});
}
ClearDiv.prototype = new Widget();


/**
 * 载入中div
 */
LoadingDiv = function(params){
	var p = params || {};
	p.templete = LOADING_DIV_TEMPLETE;
	(!p.width) && (p.width = "100%");
	(!p.height) && (p.height = "30px");
	(!p.imgHeight) && (p.imgHeight = "21px");
	(!p.imgWidth) && (p.imgWidth = "21px");
	(!p.fontSize) && (p.fontSize = "12px");
	Widget.call(this, p);
}
LoadingDiv.prototype = new Widget();
LoadingDiv.prototype.show = function(){
	this.getView().show();
}
LoadingDiv.prototype.hide = function(){
	this.getView().hide();
}
/**
 * 展示错误的div
 */
ErrorDiv = function(params){
	var p = params || {};
	(!p.width) && (p.width = "100%");
	(!p.height) && (p.height = "35px");
	(!p.fontSize) && (p.fontSize = "12px");
	(!p.icon) && (p.icon = "sad");
	(!p.text) && (p.text = "服务器没有响应！");
	p.templete = ERROR_DIV_TEMPLETE;
	Widget.call(this, p);
}
ErrorDiv.prototype = new Widget();
/**
 * 图标标题
 * @param {params:Object}
 */
IconTitle = function(params){
	params.templete = ICON_TITLE_TEMPLETE;
	(!params.fontSize) && (params.fontSize = "12px");
	
	Widget.call(this, params);
}
IconTitle.prototype = new Widget();
/**
 * 标签组件
 * @param {params:Object}
 */
Tag = function(params){
	if(params == null || params == undefined || !(params instanceof Object)) 
		return this;
	var l = params.text.getBytesLength();
	var unit = 9;
	params.width = l * unit + "px";
	params.templete = TAG_TEMPLETE;
	
	InputWidget.call(this, params);
	
	var tag = this;
	this.getMain().val(params.value);
	this.view.find("a").click(function(){
		tag.close();
	});
}
Tag.prototype = new InputWidget();
Tag.prototype.getMain = function(){
	return this.view.find("input[type='hidden']");
}
/**
 * 图标按钮组件
 * @param {params:Object}
 */
IconButton = function(params){
	if(params == null || params == undefined || !(params instanceof Object)) 
		return this;
	params.templete = ICON_BUTTON_TEMPLETE;
	Widget.call(this, params);
	var iconButton = this;
	if(params.click){
		this.click = params.click;
	}
	this.icon = params.icon;
	this.iconOther = params.iconOther || "";
	this.currIcon = this.icon;
	this.getMain().click(function(){
		iconButton.click();
	});
	if(params.text){
		this.getMain().append(params.text);
	}
}
IconButton.prototype = new Widget();
IconButton.prototype.getMain = function(){
	return this.view.find("button");
}
IconButton.prototype.getIcon = function(){
	return this.view.find("span");
}
IconButton.prototype.enable = function(enable){
	this.getMain().attr("disabled", !enable);
}
IconButton.prototype.changeTitle = function(title){
	this.getMain().html(ICON_BUTTON_TEMPLETE.replace("{icon}", this.icon));
	this.getMain().append(title);
}
/**
 * 切换icon
 * @param  {iconname:string}
 * @return {[type]}
 */
IconButton.prototype.changeIcon = function(iconname){
	iconname && (this.iconOther = iconname);
	if(this.currIcon == this.icon){
		this.getIcon().removeClass("icon-"+this.icon);
		this.getIcon().addClass("icon-"+this.iconOther);
		this.currIcon = this.iconOther;
	}else{
		this.getIcon().removeClass("icon-"+this.iconOther);
		this.getIcon().addClass("icon-"+this.icon);
		this.currIcon = this.icon;
	}
}
IconButton.prototype.changeText = function(text) {
	var html = ICON_TEMPLETE.replace("{icon}", this.icon) + text;
	this.getMain().html(html);
}
IconButton.prototype.click = function(){}
/**
 * iconbutton的列表
 * @param {params:Object}
 */
ButtonList = function(params){
	if(params == null || params == undefined || !(params instanceof Object)) 
		return this;
	params.templete = ICON_BUTTON_LIST_TEMPLETE;
	Widget.call(this,params);
	var bl = this;
	this.items = params.items || new Array();
	for(var i = 0; i < this.items.length; i++){
		this.append(this.items[i]);
	}
}
ButtonList.prototype = new Widget();
ButtonList.prototype.getMain = function(){
	return this.view.find(".button_area");
}
ButtonList.prototype.addIconButton = function(iconButton){
	this.items.push(iconButton);
	this.append(iconButton)
}
/**
 * 浮动的div容器
 */
FloatContainer = function(widget){
	Widget.call(this,{
		templete: FLOAT_CONTAINER_TEMPLETE
	});
	if(widget){
		this.append(widget);
	}
}
FloatContainer.prototype = new Widget();
/**
 * 浮动的div容器
 */
FloatContainerR = function(){
	Widget.call(this,{
		templete: FLOAT_CONTAINER_TEMPLETE
	});
	this.getView().css("float", "right");
}
FloatContainerR.prototype = new Widget();
/**
 * 日期选择框
 * @param {[type]} params [description]
 */
DatePicker = function(params){
	var p = params || {};
	p.templete = INPUT_TEMPLETE;
	p.width = 90;
	InputWidget.call(this, p);
}
DatePicker.prototype = new InputWidget();
DatePicker.prototype.init = function(){
	this.getMain().kendoDatePicker({
		culture: "zh-CN",
		format: "yyyy-MM-dd",
		value: new Date().format("yyyy-MM-dd")
	});
};
/**
 * 时间选择框
 * @param {[type]} params [description]
 */
TimePicker = function(params){
	var p = params || {};
	p.width = 90;
	InputWidget.call(this, p);
	this.getMain().width("90px");
}
TimePicker.prototype = new InputWidget();
TimePicker.prototype.init = function(){
	this.getMain().kendoTimePicker({
		culture: "zh-CN",
		format: "HH:mm:ss",
		value: "00:00:00",
		interval:30,
		max: new Date(2015,0,1,23,59,59)
	});
};
/**
 * 时间日期选择器
 * @param {[type]} params [description]
 */
DateTimePicker = function(params){
	var p = params || {};
	p.templete = NOMARL_DIV;
	InputWidget.call(this, p);
	this.datePicker = new DatePicker();
	this.timePicker = new TimePicker();
	this.append(new FloatContainer().append(this.datePicker));
	this.append(new FloatContainer().append(this.timePicker));
	this.append(new ClearDiv());
	this.init();
}
DateTimePicker.prototype = new InputWidget();

DateTimePicker.prototype.getMain = function(){
	return this.getView();
}
DateTimePicker.prototype.init = function(){
	this.datePicker.init();
	this.timePicker.init();
	return this;
}
DateTimePicker.prototype.getCurrVal = function(){
	var date = this.datePicker.getCurrVal();
	var time = this.timePicker.getCurrVal();
	return date + " " + time;
}

/**
 * 输入框
 * @param {[type]} params [description]
 */
MaskInput = function(params){
	var p = params || {};
	(!p.width) && (p.width = 100);
	this.mask = p.mask || "";
	this.defaultValue = p.defaultValue || "";
	InputWidget.call(this, p);
}
MaskInput.prototype = new InputWidget();
MaskInput.prototype.init = function(){
	var input = this;
	this.kendo = this.getMain().kendoMaskedTextBox({
		mask: this.mask,
		value: this.defaultValue,
		change: function(){
			input.change();
		},
	}).data("kendoMaskedTextBox");
}
MaskInput.prototype.enable = function(){
	this.kendo.enable(false);
	return this;
}
MaskInput.prototype.change = function(){

}
/**
 * 数字选择器
 * @param {[type]}
 */
NumberInput = function(params){
	var p = params || {};
	console.log(params);
	(!p.width) && (p.width = 100);
	this.min = p.min || 1;
	this.max = p.max || 31;
	this.step = p.step || 1;
	this.value = p.value || this.min;
	this.format = p.format || "0";
	InputWidget.call(this, p);
}
NumberInput.prototype = new InputWidget();
NumberInput.prototype.init = function(){
	var widget = this;
	this.kendo = this.getMain().kendoNumericTextBox({
		min : this.min,
		max : this.max,
		step : this.step,
		value : this.value,
		format: this.format,
		change: function(){
			widget.change();
		},
		spin: function(){
			widget.change();
		}
	}).data("kendoNumericTextBox");
}
NumberInput.prototype.change = function(){
}
/**
 * 带按钮的输入框
 * @param {[type]} params [description]
 */
ButtonInputBox = function(params){
	var p = params || {}
	p.templete = BUTTON_INPUT_BOX_TEMPLETE;
	(!p.postion) && (p.postion = "right");
	(!p.icon) && (p.icon = "tick");
	(!p.width) && (p.width = "200px");
	InputWidget.call(this, p);
	var input = this;
	this.getView().find("a").click(function(){
		input.click();
	});
}
ButtonInputBox.prototype = new InputWidget();
ButtonInputBox.prototype.setTipText = function(text){
	console.log(text);
	this.getView().find("input").val(text);
}
ButtonInputBox.prototype.tipDiv = function(){
	return this.getView().find("#tipList");
}
ButtonInputBox.prototype.click = function(){
};
ButtonInputBox.prototype.clearValue = function(){
}

/**
 * 下拉列表框
 * @param {[type]} params [description]
 */
DropDownList = function(params) {

	var p = params ||{};
	(!p.width) && (p.width = 100);
	this.dataSource = p.data || new Array();
	this.dataTextField = "text";
	this.dataValueField = "id";
	if(this.dataSource[0])
		this.value = this.dataSource[0].id
	InputWidget.call(this, p);
	this.change();
}
DropDownList.prototype = new InputWidget();
DropDownList.prototype.init = function() {
	var widget = this;
	this.kendo = this.getMain().kendoDropDownList({
		dataSource: this.dataSource,
		dataTextField: this.dataTextField,
		dataValueField: this.dataValueField,
		change:function(){
			widget.change();
		}
	}).data("kendoDropDownList");
	this.kendo.select(0);
	this.change();
}
DropDownList.prototype.getCurrVal = function() {
	return this.currVal;
}
DropDownList.prototype.getCurrText = function() {
	return this.currText;
}
DropDownList.prototype.change = function() {
	var index = this.kendo.selectedIndex;
	this.currVal = this.dataSource[index].id;
	this.currText = this.dataSource[index].text;
}
DropDownList.prototype.rebuild = function(data){
	this.dataSource = data;
	this.value = data[0].id
	this.init();
	this.change(data[0]);
}
/**
 * 日历选择框
 * @param {[type]} params [description]
 */
CalendarPicker = function(params){
	var p = params || {};
	p.templete = NOMARL_DIV;

	var picker = this;
	this.dateInput = new MaskInput({
		width: 100,
		mask: "0000-00-00"
	});
	this.dateInput.change = function(){
		picker.kendo.value(this.getCurrVal());
	}
	this.timePicker = new TimePicker();
	this.calendar = new Widget({
		templete: NOMARL_DIV_CUSTOM_WIDTH,
		width:"200px"
	});
	var div = new ClearDiv();
	div.getView().css("padding-left", "5px").css("padding-right", "5px");

	InputWidget.call(this, p);
	p.text && this.append(new ClearDiv().append(new IconTitle(params)));

	this.append(new FloatContainer().append(this.dateInput))
	.append(new FloatContainer().append(this.timePicker))
	.append(div.append(this.calendar));

}
CalendarPicker.prototype = new InputWidget();
CalendarPicker.prototype.getMain = function(){
	return this.getView();
}
CalendarPicker.prototype.init = function(){
	this.dateInput.init();
	this.timePicker.init();
	var now = new Date();
	var cale = this;
	this.kendo = this.calendar.getView().kendoCalendar({
		culture: "zh-CN",
		format: "yyyy-MM-dd",
		change: function(){
			cale.change();
		}
	}).data("kendoCalendar");
	this.kendo.value(now);
	this.dateInput.setValue(now.format("yyyy-MM-dd"));
	this.timePicker.setValue(now.format("HH:mm:ss"));
	return this;
}
CalendarPicker.prototype.getCurrVal = function(){
	return this.dateInput.getCurrVal() + " " + this.timePicker.getCurrVal();
}
CalendarPicker.prototype.getCurrText = function(){
	return this.kendo.value().format("yyyy-MM-dd 星期w") + " " + this.timePicker.getCurrVal();
}
CalendarPicker.prototype.change = function(){
	this.dateInput.setValue(kendo.toString(this.kendo.value(), 'yyyy-MM-dd'));
}

/**
 * 范围时间选择面板
 * @param {[type]} params [description]
 */
RangeDatePicker = function(params){
	var p = params || {};
	p.templete = NOMARL_DIV;
	var startParams = {
		id: "startDate",
		icon: "clock",
		text: "开始时间："
	}
	var endParams = {
		id: "endDate",
		icon: "clock",
		text: "结束时间："
	}
	this.startDate = new CalendarPicker(startParams);
	this.endDate = new CalendarPicker(endParams);
	InputWidget.call(this, p);
	
	this.append(new FloatContainer().append(this.startDate))
	.append(new FloatContainer().append(this.endDate))
	.append(new ClearDiv())

}
RangeDatePicker.prototype = new InputWidget();
RangeDatePicker.prototype.getMain = function(){
	return this.getView();
}
RangeDatePicker.prototype.init = function(){
	var cal = this;
	cal.endDate.kendo.min(cal.startDate.kendo.value());
	cal.startDate.kendo.max(cal.endDate.kendo.value());
	this.startDate.change = function(){
		CalendarPicker.prototype.change.call(this);
		cal.endDate.kendo.min(this.kendo.value());
	}
	this.endDate.change = function(){
		CalendarPicker.prototype.change.call(this);
		cal.startDate.kendo.max(this.kendo.value());
	}
}
RangeDatePicker.prototype.getCurrVal = function(){
	return {
		startDate: this.startDate.getCurrVal(),
		endDate: this.endDate.getCurrVal()
	}
}
RangeDatePicker.prototype.getCurrText = function(){
	return this.startDate.getCurrText() + RIGHT_ARROW + this.endDate.getCurrText()
}
/**
 * 相对时间选取面板
 * @param {[type]} params [description]
 */
RelateDatePicker = function(params){
	var p = params || {};
	p.templete = NOMARL_DIV;
	this.numberInput = new NumberInput({
		max: 100,
		min: 1,
		width:80
	});
	this.dropdown = new DropDownList({
		width:100,
		data: RELATE_DATA_OPTION
	});
	var start = new FloatContainer();
	var end = new FloatContainer();
	this.now = new Date();
	this.startTitle = new IconTitle({
		id: "startDate",
		icon: "clock",
		text: "开始：" + "<b id='start_date_title'>" + 
			this.calcStartDate().format("yyyy-MM-dd 周w HH:mm:ss")
		 + "</b>"
	});
	this.endTitle = new IconTitle({
		id: "endDate",
		icon: "clock",
		text: "结束：" + "<b>" + this.now.format("yyyy-MM-dd 周w HH:mm:ss") + "</b>"
	});
	
	start.append(new ClearDiv().append(this.startTitle));
	start.append(new FloatContainer().append(this.numberInput))
	.append(new FloatContainer().append(this.dropdown));
	end.append(new ClearDiv().append(this.endTitle))
	end.append(new FloatContainer().append(new MaskInput({
		defaultValue: "现在",
		width: 210
	}).enable(false)));
	InputWidget.call(this, p);
	this.append(start);
	this.append(new FloatContainer().append(new IconTitle({icon: "arrow-right2"})))
	this.append(end);
	this.append(new ClearDiv());
	
}
RelateDatePicker.prototype = new InputWidget();
RelateDatePicker.prototype.getMain = function(){
	return this.getView();
}
RelateDatePicker.prototype.init = function(){
	var widget = this;
	this.numberInput.change = function(){
		widget.getView().find("#start_date_title").text(widget.calcStartDate().format("yyyy-MM-dd 周w HH:mm:ss"));
	}
	this.dropdown.change = function(){
		DropDownList.prototype.change.call(this);
		widget.getView().find("#start_date_title").text(widget.calcStartDate().format("yyyy-MM-dd 周w HH:mm:ss"));
	}
}
RelateDatePicker.prototype.getCurrVal = function(){
	return this.currVal;
}
RelateDatePicker.prototype.getCurrText = function(){
	return this.currText;
}
RelateDatePicker.prototype.calcStartDate = function(){
	var num = this.numberInput.getCurrVal();
	var type = this.dropdown.getCurrVal();
	var end = this.now;
	var year = end.getFullYear();
	var month = end.getMonth();
	var date = end.getDate();
	var hour = end.getHours();
	var minuter = end.getMinutes();
	var second = end.getSeconds();
	switch (type) {
		case YEAR_SHORT:
			year = year - num;
			break;
		case MONTH_SHORT:
			month = month - num;
			break;
		case DAY_SHORT:
			date = date - num;
			break;
		case WEEK_SHORT:
			date = date - 7 * num;
			break;
		case HOUR_SHORT:
			hour = hour - num;
			break;
		case MINUTER_SHORT:
			minuter = minuter - num;
			break;
		case SECOND_SHORT:
			break;
	}
	this.currVal = DATE_RELATE_AGO + "/" + type + "/" + num;
	this.currText = num+this.dropdown.getCurrText()+RIGHT_ARROW+"现在";
	var start = new Date(year, month, date, hour, minuter, second);
	return start;
}

/**
 * 图标按钮组件
 * @param {params:Object}
 */
TextButton = function(params){
	if(params == null || params == undefined || !(params instanceof Object)) 
		return this;
	if(params.icon)
		params.templete = BUTTON_TEMPLETE;
	else
		params.templete = BUTTON_TEMPLETE_NO_ICON;
	Widget.call(this, params);
	var textButton = this;
	this.icon = params.icon;
	this.getMain().click(function(){
		textButton.click();
	});
	if(params.text){
		this.getMain().append(params.text);
	}
}
TextButton.prototype = new Widget();
TextButton.prototype.getMain = function(){
	return this.view;
}
TextButton.prototype.getIcon = function(){
	return this.view.find("span");
}
TextButton.prototype.enable = function(enable){
	this.getMain().attr("disabled", !enable);
}
TextButton.prototype.changeText = function(text) {
	var html = "";
	if(this.icon)
		html = ICON_TEMPLETE.replace("{icon}", this.icon) + text;
	else
		html = text;
	this.getMain().html(html);
}
TextButton.prototype.click = function(){}

ToggleButton = function(params){
	var p = params || {};
	p.templete = TOGGLE_BUTTON_TEMPLETE;
	Widget.call(this, params);
	this.toggle = false;
	var toggleButton = this;
	this.getMain().click(function(){
		toggleButton.click();
	});
}
ToggleButton.prototype = new Widget();
ToggleButton.prototype.getMain = function(){
	return this.view;
}
ToggleButton.prototype.click = function(){
	this.toggle = !this.toggle;
	if(this.toggle){
		this.getMain().removeClass("toggle_button_up");
		this.getMain().addClass("toggle_button_down");
	}else{
		this.getMain().removeClass("toggle_button_down");
		this.getMain().addClass("toggle_button_up");
	}
}