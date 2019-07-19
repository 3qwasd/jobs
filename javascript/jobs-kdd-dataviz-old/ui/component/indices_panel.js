/**
 * 指标选择面板
 * @param {params:Object}
 */
IndicesPanel = function(params) {
    var p = params || {};
    p.text = "指标";
    p.icon = "arrow-right";
    p.iconOther = "arrow-down";
    (!p.width) && (p.width = "50%");
    SlideAblePanel.call(this, p);
    var url = INDICES_BASE_URL + "/children";
    NetworkWidget.call(this, url);
    this.loadData();
}

for (var i in SlideAblePanel.prototype) {
	IndicesPanel.prototype[i] = SlideAblePanel.prototype[i]
}
for (var i in NetworkWidget.prototype) {
	IndicesPanel.prototype[i] = NetworkWidget.prototype[i]
}
IndicesPanel.prototype.getLoadingArea = function(){
    return this.head;
}
IndicesPanel.prototype.init = function(data){
    this.currVal = {};
	var tabPanel = new TabPanel();
    var indicePanel = this;
    var initButton = null;
    var other;
    for(var key in data){
        other = null;
        var item = data[key];
        var tab = new Tab({
            icon: item.icon,
            text: item.text
        });
        for(var key_1 in item.children){
            var item_1 = item.children[key_1];
            if(!item_1.children){
                if(!other){
                    other = new ButtonList({
                        icon: "",
                        text: ""
                    });
                }
                var iconButton = new IconButton({
                    id: item_1.path,
                    icon: item_1.icon,
                    text: item_1.text,
                    fontSize:"12"
                });
                iconButton.viewType = item_1.charttype;
                iconButton.needParams = new Array();
                iconButton.text = "指标：<b>/" + item.text + "/" + item_1.text + "</b>";
                iconButton.unit = item_1.unit;
                iconButton.description = item_1.description;
                iconButton.format = item_1.format || {};
                iconButton.valueTitle = item_1.valuetitle;
                iconButton.fields = item_1.params.filters.fields;
                iconButton.fetchFields = item_1.fetchFields;
                //item_1.params.fetchFields && iconButton.fetchFields = item_1.params.fetchFields;
                for(var pname in item_1.params){
                    iconButton.needParams.push(pname);
                }
                iconButton.click = function(){
                    indicePanel.changeIndice(this);
                }
                other.addIconButton(iconButton);
                continue;
            }
            var bl = new ButtonList({
                icon: item_1.icon,
                text: item_1.text
            });
            for(var key_2 in item_1.children){
                var item_2 = item_1.children[key_2];
                var iconButton = new IconButton({
                    id: item_2.path,
                    icon: item_2.icon,
                    text: item_2.text,
                    fontSize:"12"
                });
                iconButton.viewType = item_2.charttype;
                iconButton.needParams = new Array();
                iconButton.text = "指标：<b>/" + item.text + "/" + item_1.text + "/" + item_2.text + "</b>";
                iconButton.unit = item_2.unit;
                iconButton.description = item_2.description;
                iconButton.format = item_2.format || {};
                iconButton.valueTitle = item_2.valuetitle;
                iconButton.fields = item_2.params.filters.fields;
                (item_2.fetchFields) && (iconButton.fetchFields = item_2.fetchFields);
                for(var pname in item_2.params){
                    iconButton.needParams.push(pname);
                }
                iconButton.click = function(){
                    indicePanel.changeIndice(this);
                }
                if(initButton == null) initButton = iconButton;
                bl.addIconButton(iconButton);
            }
            tab.append(bl);
        }
        tab.append(other);
        other = null;
        tab.append(new ClearDiv());
        tabPanel.appendTab(tab);
    }
    this.append(tabPanel.refresh());
    initButton.click();
    tabPanel.select(0);
};
IndicesPanel.prototype.changeIndice = function(indice){
    this.currVal.indicPath = indice.id;
    this.currVal.viewType = indice.viewType;
    this.currVal.unit = indice.unit;
    this.currVal.valutitle = indice.valueTitle;
    this.currVal.format = indice.format;
    this.currVal.icon = indice.icon;
    this.currVal.description = indice.description;
    this.currVal.fetchFields = indice.fetchFields;
    this.changeTitle(indice.text);
    this.slideUp();
    this.paramsPanel.showPart(indice.needParams);
    this.paramsPanel.refreshGroupBys(indice.fields);
    this.queryPanel.refreshFilters(indice.fields);
}
IndicesPanel.prototype.connection = function(paramsPanel){
    this.paramsPanel = paramsPanel;
}
IndicesPanel.prototype.connectionQueryPanel = function(queryPanel){
    this.queryPanel = queryPanel;
}
IndicesPanel.prototype.getCurrVal = function(){
    return this.currVal;
}
