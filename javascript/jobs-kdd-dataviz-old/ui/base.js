/**
 * 基础组件类
 * @param {params:Object}
 */
Widget = function(params) {
	//判断参数
	if(params == null || params == undefined || !(params instanceof Object)) 
		return this;
	this.id = params.id || "";
	//设置templete中的参数
	var patt = new RegExp("\\{[a-zA-Z0-9]+\\}", "g");
	if (params.templete) {
		var result;
		while ((result = patt.exec(params.templete)) != null) {
			var placeholder = result[0];
			var name = placeholder.substring(1, placeholder.length - 1);
			var value;
			if(params[name] === undefined || params[name] === null){
				value = "";
			}else{
				value = params[name];
			}
			params.templete = params.templete.replace(placeholder, value);
		}
	}
	//使用jquery创建视图
	this.view = $((params.templete || "<div></div>"));
	//如果设置了父元素，则追加该组件到父元素中
	params.parentid && ($("#" + params.parentid).append(this.view));
	this.isHidden = false;
}
Widget.prototype = new Object();
/**
 * 获取该组件的主要部分，需要子类实现覆盖
 * @return {this.view}
 */
Widget.prototype.getMain = function() {
	return this.view;
}
/**
 * 获取该组件的视图，
 * @return {this.view}
 */
Widget.prototype.getView = function() {
	return this.view;
}
/**
 * 向该组件中添加子组件
 * @param  {Widget}
 * @return {this}
 */
Widget.prototype.append = function(widget){
	if(widget instanceof Widget){
		this.getMain().append(widget.getView());
	}
	return this;
}
/**
 * 关闭该组件
 * @return
 */
Widget.prototype.close = function(){
	this.view.remove();
}
/**
 * 初始化方法
 * @return
 */
Widget.prototype.init = function(){

}
Widget.prototype.hide = function(){
	this.view.hide();
	this.isHidden = true;
}
Widget.prototype.show = function(){
	this.view.show();
	this.isHidden = false;
}
Widget.prototype.isHide = function(){
	return this.isHidden;
}
Widget.prototype.setDataItemListener = function(widget){
	this.dataItemListener = widget;
}
Widget.prototype.itemSelect = function(){
	if(this.dataItemListener)
		this.dataItemListener.dataItemSelect(this.currDataItem);
}
/**
 * 基础面板组件
 * @param {params:Object}
 */
Panel = function(params) {	
	Widget.call(this, params);
}
Panel.prototype = new Widget();

/**
 * 输入类型的组件
 * @param {params:Object}
 */
InputWidget = function(params) {
	var p = params || {};
	if(!p.templete){
		if(!p.text) {
			p.templete = INPUT_TEMPLETE;
		}else{
			p.templete = INPUT_TEMPLETE_WITH_LABEL;	
		}
	}
	Widget.call(this, p);
	this.id = p.id || "";
	this.init();
}
InputWidget.prototype = new Widget(); 
InputWidget.prototype.getMain = function() {
	return this.view.find("input");
}
InputWidget.prototype.init = function(){};
/**
 * 获取当前值
 * @return {value}
 */
InputWidget.prototype.getCurrVal = function(){
	return this.getMain().val();
}
/**
 * @return {Object}是个键值对，表示该控件的id和value
 */
InputWidget.prototype.getKV = function(){
	return {
		key: this.id,
		value: this.getCurrVal()
	}
}
InputWidget.prototype.setValue = function(value){
	this.getMain().val(value);
}