/**
 * [SLIDEABLE_PANEL_TEMPLETE]
 * @type {String}
 */
var SLIDEABLE_PANEL_TEMPLETE = '<div class="slideable_bar_panel" id="{id}" style="width:{width};postion:relative;z-index:2000;">' +
	'<div id="panel_head" class="option-area normat_div_area" style="padding-left:10px; height:{height}; font-size:{fontSize}"></div>' +
	'<div id="panel_body" class="slideable_bar_content" style="position:{position};"></div>' +
	'</div>';

/**
 * [CLEAR_DIV_TEMPLETE clear div]
 * @type {String}
 */
var CLEAR_DIV_TEMPLETE = '<div class="clear_div"></div>';
/**
 * [ICON_BUTTON 图标按钮]
 * @type {String}
 */
var ICON_BUTTON_TEMPLETE = '<div style="margin:0px;padding:0px">' +
	'<button style="font-size:{fontSize}px;height:20px;line-height:20px; background:none;border:none;overflow:hidden;padding:0px" id="{id}">' +
	'<span class="icon icon-{icon}">' +
	'</span>' +
	'</button>' +
	'</div>';
var BUTTON_TEMPLETE = '<button style="font-size:{fontSize}px; background:none;border:none;overflow:hidden;" id={id}>' +
	'<span class="icon icon-{icon}">' +
	'</span>' +
	'</button>';
var BUTTON_TEMPLETE_NO_ICON = '<button style="font-size:{fontSize}px; background:none;border:none" id={id}>' +
	'</button>';
/**
 * [ICON_BUTTON_LIST_TEMPLETE 图表按钮列表]
 * @type {String}
 */
var ICON_BUTTON_LIST_TEMPLETE = '<div class="button_list">' +
	'<div class="k-block" style="font-size:12px; line-height:20px">' +
	'<div class="k-header" style="font-size:14px;text-align:left;padding-left:25px;width:auto">' +
	'<span class="icon icon-{icon}"></span>{text}' +
	'</div>' +
	'<div class="button_area" style="min-height:100px"></div>' +
	'</div></div>';
var ICON_TEMPLETE = '<span class="icon icon-{icon}"></span>';
var TAB_PANEL_TEMPLETE = '<div style="border:none;background-color:#FFF"><ul></ul></div>';
var ICON_TITLE_TEMPLETE = '<span class="icon icon-{icon}" style="font-size:{fontSize};height:20px;line-height:20px;overflow:hidden;"> {text} </span>';
var TAB_CONTENT_TEMPLETE = '<div style="padding:0px">' +
	'<div style="padding:{padding};height:{height};width:{width};text-align:center; border:none;" id="tab_content_body"></div>' +
	'<div id="tab_cntent_foot"></div>' +
	'</div>';
var INPUT_TEMPLETE_WITH_LABEL = '<div>{text}：' +
	'<input id="{id}" name="{name}" style="width:{width}px"></div>';
var INPUT_TEMPLETE = '<div><input id="{id}" name="{name}" style="width:{width}px"></div>';

/**
 * 
 * @type {String}
 */
var BUTTON_INPUT_BOX_TEMPLETE = '<div style="width:{width};">' +
	'<span class="k-textbox k-space-{postion}" style="width:100%;line-height:30px">' +
	'<input type="text" id="{id}" name="{name}"> ' +
	'<a href="#" class="k-icon k-i-{icon}"></a> ' +
	'</span><div class="tipDiv" id="tipList"></div>' +
	'</div>';

var RIGHT_ARROW = '<span class="icon icon-arrow-right2"></span>';

/**
 * [TAG_TEMPLETE 标签组件模板]
 * @type {String}
 */
var TAG_TEMPLETE = '<div class="tag_div"><span class="icon icon-price-tag"></span>' +
	'{text}<a href="javascript:void(0)" class="k-icon k-i-close" style="margin-left:5px"></a>' +
	'<input type="hidden" editable="false" id={id} ></div>'

var DATAVIEW_TEMPLETE = '<div id="{id}" style="width:{width};height:auto;min-height:{height}" class="data_view_div"></div>'

var DASHBOARD_DATAVIEW_TEMPLETE = '<div id="{id}" style="width:{width};height:auto;min-height:{height}" class="dashboard_data_view_div"></div>'

var NORAML_PANEL_TEMPLETE = '<div style="width:{width};height:{height};"></div>';

var CHART_PANEL_TEMPLETE = "<div class='chart_div' id='{id}'>" +
	"<div class='chart_div_head'></div>" +
	"<div class='chart_div_body'></div>" +
	"<div class='chart_div_foot'></div>" +
	"</div>";

var PROGRESSBAR_TEMPLETE = '<div style="width:80%"></div>';

var OPTION_DIV_ROW = '<div class="option-area normat_div_area" id="{id}"></div>';
var NOMARL_DIV = '<div id="{id}" style="width:{width}"></div>';
var NOMARL_DIV_CUSTOM_WIDTH = '<div id="{id}" style="width:{width}"></div>';
var NONE_DIV = '<div style="width:100%;height:100%;line-height:100%;text-align:center;font-size:32px">' +
	'<span class="icon icon-{icon}">{text}</span>' +
	'</div>';
var DATA_VIEW_CONTAINER = '<div id="regional-sales" class="main-section-content row"></div>';

var SING_VALUE_TEMPLETE = '<div style="width:{width};height:{height};font-size:64px;line-height:{line-height};text-align:center">' +
	'<span class="icon icon-star-full"></span>{value}' +
	'</div>';

var LOADING_DIV_TEMPLETE = '<div id="loading_div" style="width:{width};height:{height};line-height:{height};font-size:{fontSize};text-align:center;background-color:#FFF">' +
	'<img src="image/loading_28_28.gif"/>' +
	'</div>';

var FLOAT_CONTAINER_TEMPLETE = '<div class="float_container"></div>';

var ERROR_DIV_TEMPLETE = '<div id="error_div" style="width:{width};height:{height};line-height:{height};font-size:{fontSize};text-align:center;background-color:#fff">'+
'<strong><span class="icon icon-{icon}">&nbsp;{text}</span></strong></div>';

var TOOL_BAR_BOTTOM_TEMPLETE = '<div class="tool_bar_panel_bottom" style="height:{height};line-height:{height};padding-left:20px"></div>';

var DATAVIEW_PANEL_TEMPLETE = '<div style="width:{width};height:{height};" class="data_view_panel"></div>';

var CHART_DIV_TEMPLETE = '<div style="width:{width};height:{height};padding:{padding};max-height:500px"></div>';

var TABLE_DIV_TEMPLETE = '<div style="width:{width};height:{height};padding:{padding};">' +
	'<div id="tableArea"></div>'+
	'</div>';

var TOPN_GRID_NUM_TEMPLETE = "<div style='height:15px;text-align:center;line-height:15px;font-size:14px'>" +
	"<strong><font style='color:#= (function(){if(orderNum < 4) return 'red';})() #'>#: orderNum #</font>" +
	"</strong></div>";
var TOPN_GRID_NAME_TEMPLETE = "<div style='height:15px;text-align:left;line-height:15px;font-size:12px;'>" + "#: name #</div>";
var TOPN_GRID_BAR_TEMPLETE = "<div style='width:#: size #%;background-color:\\#ddd;height:10px'></div>";
var TOPN_GRID_VALUE_TEMPLETE = "<div style='height:15px;text-align:left;line-height:15px;font-size:12px;'>" + "<strong>#: value #</strong>&nbsp;{valueUnit}</div>";

var VALUE_ITEM_TEMPLETE = '<div style="width:{width};height:{height};line-height:{height};'+
'font-size:{fontSize};text-align:center;font-weight:bolder;    background-color: #fff;">' +
	'<span class="icon icon-{icon}"></span>{value}&nbsp;{unit}' +
	'</div>';

var DATE_VIEW_TITLE_TEMPLETE = '&nbsp;<font style="font-size:12px;font-weight:bolder">[&nbsp;{date}&nbsp;]</font>&nbsp;&nbsp;<font style="font-size:14px;">{desciption}</font>'

var DASH_BOARD = '<div id="{id}" style="width:{width};height:{height}"></div>';

var ITEM_VIEW_TEMPLETE = '<div class="item_view_templete">#:text#：<b>#:value#</b></div>';

var TOGGLE_BUTTON_TEMPLETE = '<button class="toggle_button_up">{text}</button>'