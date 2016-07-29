/**
 * 时间选择面板
 * @param {params:Object}
 */
DatePanel = function(params) {
    var p = params || {};
    p.text = "时间";
    p.icon = "clock";
    p.iconOther = "clock2";
    (!p.width) && (p.width = "50%");
    SlideAblePanel.call(this, p);
    var url = BASE_URL + "app/ui/res/widget/date";
    NetworkWidget.call(this, url);
    this.create();
    this.loadData();
}

for (var i in SlideAblePanel.prototype) {
	DatePanel.prototype[i] = SlideAblePanel.prototype[i]
}
for (var i in NetworkWidget.prototype) {
	DatePanel.prototype[i] = NetworkWidget.prototype[i]
}
DatePanel.prototype.getLoadingArea = function(){
	return this.head;
}
DatePanel.prototype.create = function(){
    this.tabPanel = new TabPanel();
    this.quick = new Tab({
        icon: "clock",
        text: "快速选取"
    });
    this.relate = new Tab({
        icon: "clock",
        text: "相对范围"
    });
    this.absoulte = new Tab({
        icon: "clock",
        text: "绝对范围"
    });
    var datePanel = this;
    this.tabPanel.appendTab(this.quick);
    this.tabPanel.appendTab(this.relate);
    this.tabPanel.appendTab(this.absoulte);
    this.rangeDatePicker = new RangeDatePicker();
    this.absoulte.append(this.rangeDatePicker);
    var absourlteToolBar = new ToolBarPanelR();
    var decideButton = new IconButton({
        icon: "checkmark",
        text: "确定"
    });
    decideButton.click = function(){
        datePanel.changeDate("absoulte", datePanel.rangeDatePicker.getCurrVal(),
            datePanel.rangeDatePicker.getCurrText());
    }
    var cancelButton = new IconButton({
        icon: "cross",
        text: "取消"
    });
    cancelButton.click = function(){
        datePanel.slideUp();
    }
    this.absoulte.appendToFoot(
        absourlteToolBar
        .appendTool(decideButton)
        .appendTool(cancelButton)
        );
    this.append(this.tabPanel.refresh());
    this.tabPanel.select(0);
}
DatePanel.prototype.init = function(data){
    var datePanel = this;
    var initButton = null;
    var i = 1;

	for(var key in data){
        var sortNum = data[key].sortNum;
        var seg = key.split("/");
        var button = new IconButton({
            text: data[key].text,
            fontSize:"10",
            id: key
        });
        button.type = seg[2];
        button.text = data[key].text;
        button.click = function(){
            datePanel.changeDate(this.type, this.id, this.text);
        }
        if(initButton == null) initButton = button;
        if(sortNum / 10 > i){
            i++;
            this[seg[2]].append(new ClearDiv());
            this[seg[2]].append(new FloatContainer().append(new IconTitle({
                icon: "stop2"
            })));
        }
        this[seg[2]].append(new FloatContainer().append(button));
    }
    this.tabPanel.refresh()
    this.quick.append(new ClearDiv());
    this.relate.append(new ClearDiv());
    this.relate.getMain().append($("<hr>"));
    this.relateDatePicker = new RelateDatePicker();
    this.relate.append(this.relateDatePicker);
    var relateToolBar = new ToolBarPanelR();
    var decideButton = new IconButton({
        icon: "checkmark",
        text: "确定"
    });
    decideButton.click = function(){

        datePanel.changeDate("relate", 
            datePanel.relateDatePicker.getCurrVal(),
            datePanel.relateDatePicker.getCurrText());
    }
    var cancelButton = new IconButton({
        icon: "cross",
        text: "取消"
    });
    cancelButton.click = function(){
        datePanel.slideUp();
    }
    this.relate.appendToFoot(
        relateToolBar
        .appendTool(decideButton)
        .appendTool(cancelButton)
        );

    this.currType = "quick";
    this.currVal = "/date/quick/last/d/30";
    this.currText = "过去30天";
    this.changeTitle("时间：<b>" + this.currText + "</b>");
    this.slideUp();
};
DatePanel.prototype.endLoading = function(){
    NetworkWidget.prototype.endLoading.call(this);
    this.tabPanel.getView().show();
}
DatePanel.prototype.changeDate = function(type, value, text){
    this.currType = type;
    this.currVal = value;
    this.changeTitle("时间：<b>"+text+"</b>");
    this.currText = text;
    this.slideUp();
    this.dateChange();
}
DatePanel.prototype.getCurrVal = function(){
    return {
        type: this.currType,
        val: this.currVal,
        text: this.currText
    }
}
DatePanel.prototype.dateChange = function(){

}
