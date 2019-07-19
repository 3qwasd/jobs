/**
 * @param {listener: NetworkWidget}
 */
DataLoader = function(listener) {
	this.listener = listener;
}
DataLoader.prototype.getData = function(){
	var loader = this;
	$.ajax({
		type: 'GET',
		timeout: TIMEOUT,
		async: true,
		url: this.listener.url,
		success: function(data) {
			loader.listener.loadDataSuccess(data);
		},
		error: function() {
			loader.listener.loadDataFailed();
		},
		statusCode: {
				401: function () {
					window.location.href = "login.html";
				}
			},
		contentType: 'application/json',
		dataType: 'json'
	});
}


DataLoader.prototype.postData = function(data) {
	var loader = this;
	$.ajax({
		type: 'POST',
		timeout: TIMEOUT,
		async: true,
		url: this.listener.url,
		data: JSON.stringify(data),
		success: function(data) {
			loader.listener.loadDataSuccess(data);
		},
		error: function() {
			loader.listener.loadDataFailed();
		},
		statusCode: {
				401: function () {
					window.location.href = "login.html";
				}
			},
		contentType: 'application/json; charset=utf-8',
		dataType: 'json'
	});
}


/**
 * 网络组件，需要从网络加载数据
 * @param {params:Object}
 */
NetworkWidget = function(url) {
	this.url = url || "";
	if(!this.loadingView)
		this.createLoadingView();
	this.dataLoader = new DataLoader(this);
}
NetworkWidget.prototype.createLoadingView = function(){
	if(this.view){
		this.loadingView = new NormalPanel({
			width: this.getLoadingArea().width() + "px",
			height: this.getLoadingArea().height() + "px"
		});
		this.loadingView.append(new LoadingDiv({
			width: this.loadingView.getView().width() + "px",
			height: this.loadingView.getView().height() + "px",
		}));
		var postion = this.getLoadingArea().find(".clear_div");
		if(postion.length > 0){ 
			postion.before(this.loadingView.getView());
		}else {
			this.getLoadingArea().append(this.loadingView.getView());
		}
	}
}
/**
 * 需要加载后显示的区域
 * @return 
 */
NetworkWidget.prototype.getLoadingArea = function() {}
/**
 * 加载数据
 * @return {}
 */
NetworkWidget.prototype.loadData = function() {
	this.startLoading();
	this.dataLoader.getData();
}
/**
 * 加载数据
 * @return {}
 */
NetworkWidget.prototype.postData = function(data) {
	this.startLoading();
	this.dataLoader.postData(data);
}
/**
 * 开始加载数据
 * @return {this}
 */
NetworkWidget.prototype.startLoading = function() {
	
	this.getLoadingArea().children().each(function(i, e) {
		$(e).hide();
	});
	this.loadingView.show();
}

/**
 * 加载数据结束
 * @return {this}
 */
NetworkWidget.prototype.endLoading = function() {
	this.getLoadingArea().children().each(function(i, e) {
		$(e).show();
	});
	if (this.errorView) {
		this.errorView.hide();
	}
	this.loadingView.hide();
}
	/**
	 * 初始化组件
	 * @param  {data}
	 * @return 
	 */
NetworkWidget.prototype.loadDataSuccess = function(data) {
	this.init(data);
	this.endLoading();
}
	/**
	 * 加载数据失败调用
	 * @return 
	 */
NetworkWidget.prototype.loadDataFailed = function() {
	this.loadingView.hide();
	this.createErrorView();
	this.errorView.show();
}
NetworkWidget.prototype.createErrorView = function() {
	if (!this.errorView) {
		this.errorView = new NormalPanel({
			width: this.loadingView.getView().width() + "px",
			height: this.loadingView.getView().height() + "px"
		});
		this.errorView.append(new ErrorDiv({
			width: this.errorView.getView().width() + "px",
			height: this.errorView.getView().height() + "px",
			fontSize: "16px"
		}));
		var postion = this.getLoadingArea().find(".clear_div");
		if (postion.length > 0) {
			postion.before(this.errorView.getView());
		} else {
			this.getLoadingArea().append(this.errorView.getView());
		}
	}
}