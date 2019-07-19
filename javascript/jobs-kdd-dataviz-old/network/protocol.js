var ProtocolMap = {}
	//注册协议
function RegisterProtocol(id, constructor) {
	ProtocolMap[id] = constructor;
}
//协议
function Protocol(id, content) {
	this.id = id;
	if (content != null)
		this.content = content;
}
//转成json
Protocol.prototype.marshal = function() {
	return JSON.stringify(this);
}
	//将data转为协议对象
Protocol.prototype.unMarshal = function(data) {
	this.content = data.content;
	return this;
}
Protocol.prototype.process = function() {
	//需要子类实现
}

function SNoDataProtocol(content){
	Protocol.call(this, "SNODATAPROTOCOL", content);
}
SNoDataProtocol.prototype = new Protocol("SNODATAPROTOCOL");
RegisterProtocol("SNODATAPROTOCOL", SNoDataProtocol);

function STimeVaryChart(content) {
	Protocol.call(this, "STIMEVARYCHART", content);
}
STimeVaryChart.prototype = new Protocol("STIMEVARYCHART");
RegisterProtocol("STIMEVARYCHART", STimeVaryChart);

function CTimeVaryChart(content) {
	Protocol.call(this, "CTIMEVARYCHART", content);
}
CTimeVaryChart.prototype = new Protocol("CTIMEVARYCHART");
RegisterProtocol("CTIMEVARYCHART", CTimeVaryChart);

function CTimeXYChart(content){
	Protocol.call(this, "CTIMEXYCHART", content);
}
CTimeXYChart.prototype = new Protocol("CTIMEXYCHART");
RegisterProtocol("CTIMEXYCHART", CTimeXYChart);

function STimeXYChart(content){
	Protocol.call(this, "STIMEXYCHART", content);
}
STimeXYChart.prototype = new Protocol("STIMEXYCHART");
RegisterProtocol("STIMEXYCHART", STimeXYChart);

function CTopNGrid(content){
	Protocol.call(this, "CTOPNGRID", content);
}
CTopNGrid.prototype = new Protocol("CTOPNGRID");
RegisterProtocol("CTOPNGRID", CTopNGrid);

function STopNGrid(content){
	Protocol.call(this, "STOPNGRID", content);
}
STopNGrid.prototype = new Protocol("STOPNGRID");
RegisterProtocol("STOPNGRID", STopNGrid);

function CSingValueView(content){
	Protocol.call(this, "CSINGVALUEVIEW", content);
}
CSingValueView.prototype = new Protocol("CSINGVALUEVIEW");
RegisterProtocol("CSINGVALUEVIEW", CSingValueView);

function SSingValueView(content){
	Protocol.call(this, "SSINGVALUEVIEW", content);
}
SSingValueView.prototype = new Protocol("SSINGVALUEVIEW");
RegisterProtocol("SSINGVALUEVIEW", SSingValueView);

function CTable(content){
	Protocol.call(this, "CTABLE", content);
}
CTable.prototype = new Protocol("CTABLE");
RegisterProtocol("CTABLE", CTable);

function STable(content){
	Protocol.call(this, "STABLE", content);
}
STable.prototype = new Protocol("STABLE");
RegisterProtocol("STABLE", STable);

function CPieChart(content){
	Protocol.call(this, "CPIECHART", content);
}
CPieChart.prototype = new Protocol("CPIECHART");
RegisterProtocol("CPIECHART", CPieChart);

function SPieChart(content){
	Protocol.call(this, "SPIECHART", content);
}
SPieChart.prototype = new Protocol("SPIECHART");
RegisterProtocol("SPIECHART", SPieChart);

function CDetailView(content){
	Protocol.call(this, "CDETAILVIEW", content);
}
CDetailView.prototype = new Protocol("CDETAILVIEW");
RegisterProtocol("CDETAILVIEW", CDetailView);

function SDetailView(content){
	Protocol.call(this, "SDETAILVIEW", content);
}
SDetailView.prototype = new Protocol("SDETAILVIEW");
RegisterProtocol("SDETAILVIEW", SDetailView);