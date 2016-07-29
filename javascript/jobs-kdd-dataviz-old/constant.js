var BASE_URL                 = "http://localhost:6080/";
var DATA_VLZ_URL             = BASE_URL + "app/dataviz";
var INDICES_BASE_URL         = BASE_URL + "app/ui/res/board/mediamatch/analysis";
var DASHBOARD_BASE_URL       = BASE_URL + "app/ui/res/board/mediamatch/dashboard";
var TIMEOUT                  = 120000; //ajax请求超时时间

var WIDGET_TYPE_RADIOBUTTON  = 0; //控件类型单选
var WIDGET_TYPE_DROWDOWNLIST = 1; //控件类型下拉列表框
var WIDGET_TYPE_DATEPICKER   = 2; //控件类型日期选择面板
var WIDGET_TYPE_TREEMENU     = 3; //控件类型树形菜单

	
// code 0000(这四位表示indice type 目前是两种，master 1，data 0，共支持16种) 0000(这4位保留) 0000(这4位保留) 0(保留位)0(是否受Master影响位)0(过滤位)0(时间位)
var FILTER_MASK_CODE                  = 0x0002;
var MASTER_MASK_CODE                  = 0x0004;
var DATE_MASK_CODE                    = 0x0001;
var ISMASTER_MASK_CODE                = 0x1000;

var DASHBOARD_INDICE_TYPE_MASTER_DATE = 0x1003;//时间改变需要刷新，主要指标0001 0000 0000 0011
var DASHBOARD_INDICE_TYPE_MASTER      = 0x1002;//时间改变无需刷新，主要指标0001 0000 0000 0010
var DASHBOARD_INDICE_TYPE_FLOWER_DATE = 0x0005;//时间改变需要刷新，次要指标，受MASTER支配，MASTER指标对应的view的变换事件的接受者 0000 0000 0000 0101
var DASHBOARD_INDICE_TYPE_FLOWER      = 0x0004;//时间改变无需刷新，次要指标，受MASTER支配，MASTER指标对应的view的变换事件的接受者0000 0000 0000 0100
var DASHBOARD_INDICE_TYPE_DATA_DATE   = 0x0001;//时间改变需要刷新，次要指标，不受MASTER事件支配0001 0000 0000 0001
var DASHBOARD_INDICE_TYPE_DATA        = 0x0000;//时间改变无需刷新，次要指标，不受MASTER事件支配0001 0000 0000 0000

var YEAR_SHORT    = "y"; //时间单位的简写年
var MONTH_SHORT   = "M"; //时间单位的简写月
var DAY_SHORT     = "d"; //时间单位的简写日
var WEEK_SHORT    = "w"; //时间单位的简写星期
var HOUR_SHORT    = "h"; //时间单位的简写小时
var MINUTER_SHORT = "m"; //时间单位的简写分钟
var SECOND_SHORT  = "s"; //时间单位的简写秒

var DATAVIEW_CHART_TIMEVARY = "/dataview/chart/timevary";
var DATAVIEW_CHART_TIMEXY   = "/dataview/chart/timexy";
var DATAVIEW_GRID_TOPN      = "/dataview/grid/topn";
var DATAVIEW_VIEW_SINGVALUE = "/dataview/view/singvlaue";
var DATAVIEW_GRID_TABLE     = "/dataview/grid/table";
var DATAVIEW_CHART_PIE      = "/dataview/chart/pie";
var DATAVIEW_GRID_DETAIL    = "/dataview/grid/detail";
//图表类型与协议ID的映射
var PTMap = {};
PTMap[DATAVIEW_CHART_TIMEVARY] = "CTIMEVARYCHART";
PTMap[DATAVIEW_CHART_TIMEXY]   = "CTIMEXYCHART";
PTMap[DATAVIEW_GRID_TOPN]      = "CTOPNGRID";
PTMap[DATAVIEW_VIEW_SINGVALUE] = "CSINGVALUEVIEW";
PTMap[DATAVIEW_GRID_TABLE]     = "CTABLE";
PTMap[DATAVIEW_CHART_PIE]      = "CPIECHART";
PTMap[DATAVIEW_GRID_DETAIL]    = "CDETAILVIEW";

function CREATEPROTOCOL(viewType, queryParams){
	return new (ProtocolMap[PTMap[viewType]])(queryParams);
}

var TIMEVAREY_HEIGHT = "200px";
var TIMEVAREY_WIDTH  = "100%";
var TIMEXY_HEIGHT    = "500px";
var TIMEXY_WIDTH     = "100%";

var DEFAULT_INDICE     = "view_event/audience/trend";
var DEFAULT_START_DATE = "2015-01-01 00:00:00";
var DEFAULT_END_DATE   = "2005-01-01 00:00:00";

var DEFAULT_CHART_HEIGHT  = "400px"; //默认的图表属性：高度
var DEFAULT_CHART_WIDTH   = "100%"; //默认的图表属性：宽度

var DATE_FORMAT_SHOW_WEEK = "yyyy-MM-dd 星期w HH:mm:ss"; //日期格式化字符串
var DATE_FORMAT_DEFAULT   = "yyyy-MM-dd HH:mm:ss"; //日期格式化字符串


var DATE                = "/date";
var DATE_QUICK          = DATE + "/quick";
var DATE_RELATE         = DATE + "/relate";
var DATE_ABSOULTE       = DATE + "/absoulte";
var DATE_QUICK_THIS     = DATE_QUICK + "/this";
var DATE_QUICK_LAST     = DATE_QUICK + "/last";
var DATE_QUICK_LASTTHIS = DATE_QUICK + "/lastthis";
var DATE_RELATE_AGO     = DATE_RELATE + "/ago";
var DATE_RELATE_LAST    = DATE_RELATE + "/last";


//日期单位
var UNIT_YEAR = {
	"id": YEAR_SHORT,
	"text": "年"
}; //日期单位年
var UNIT_MONTH = {
	"id": MONTH_SHORT,
	"text": "月"
}; //日期单位月
var UNIT_DAY = {
	"id": DAY_SHORT,
	"text": "日"
}; //日期单位日
var UNIT_HOUR = {
	"id": HOUR_SHORT,
	"text": "时"
}; //日期单位小时
var UNIT_MINUTER = {
	"id": MINUTER_SHORT,
	"text": "分"
}; //日期单位分钟

var RNAGE_UNIT_ARRAY = [UNIT_YEAR, UNIT_MONTH, UNIT_DAY, UNIT_HOUR];

//过滤字段分类
var PHONE_NO                 = { id: "phone_no", text:"用户编号" } //用户编号*
var CUST_ID                  = { id: "cust_id", text:"客户编号" } //客户编号*
var CONTRACT_ID              = { id: "Contract_id", text:"账户ID" } //账户ID*
var TERMINAL_NO              = { id: "terminal_no", text:"客户编号" } //账户ID*
var CUST_NAME                = { id: "cust_name", text:"客户姓名" } //客户姓名*
var OWNER_NAME               = { id: "owner_name", text:"客户等级名称" } //客户等级名称*
var SM_NAME                  = { id: "sm_name", text:"用户品牌名称" } //用户品牌名称
var RUN_NAME                 = { id: "run_name", text:"用户状态" } //用户状态
var GROUP_NAME               = { id: "group_name", text:"分公司名称" } //分公司名称*
var ADDRESS_NAME1            = { id: "address_name1", text:"用户一级地址" } //用户一级地址*
var ADDRESS_NAME2            = { id: "address_name2", text:"用户二级地址" } //用户二级地址*
var ADDRESS_NAME3            = { id: "address_name3", text:"用户三级地址" } //用户三级地址*
var ADDRESS_NAME4            = { id: "address_name4", text:"用户四级地址" } //用户四级地址*
var ADDRESSOJ                = { id: "addressoj", text:"用户地址全称" } //用户地址全称//END*
var OWNER_GRADE              = { id: "owner_grade", text:"客户等级" } //客户等级

var CHANNEL_GROUP_NAME       = { id: "channel_group_name", text:"渠道名称" } //客户等级
var PAY_NAME       			 = { id: "pay_name", text:"支付名称" } //支付名称


var DEVICE_ID                = { id: "device_id", text:"设备id" } //发送此次观看记录的设备id 
var ACCOUNT_ID               = { id: "account_id", text:"用户id" } //此次观看记录的用户id
var PROGRAM_ID               = { id: "program_id", text:"媒体节目id" } //媒体节目id
var RES_TYPE                 = { id: "res_type", text:"媒体节目类型" } //媒体节目类型 0是直播，1是点播
var RES_DURATION             = { id: "res_duration", text:"节目时长" } //节目时长（ms为单位）
var PROGRAM_TITLE            = { id: "program_title", text:"直播节目名称" } //直播节目名称
var STATION_ID               = { id: "station_id", text:"直播频道id" } //直播频道id
var STATION_NAME             = { id: "station_name", text:"直播频道名称" } //直播频道名称
var ORIGIN_TIME              = { id: "origin_time", text:"开始观看时间" } //开始观看时间 yyyy-mm-dd hh24:mi:ss
var DURATION                 = { id: "duration", text:"观看时长" } //观看时长（ms为单位）
var END_TIME                 = { id: "end_time", text:"观看行为结束时间" } //观看行为结束时间 yyyy-mm-dd hh24:mi:ss
var TIME_TAGS                = { id: "time_tags", text:"观看时间相关数据" } //观看时间相关数据（nested object）按在线观看每分钟统计
var TIME_TAGS_MONTH          = { id: "time_tags.month", text:"观看每分钟所在月份" } //观看每分钟所在月份 mm
var TIME_TAGS_DAY            = { id: "time_tags.day", text:"观看每分钟所在天数" } //观看每分钟所在天数（月份）dd
var TIME_TAGS_HOUR           = { id: "time_tags.hour", text:"观看每分钟所在小时数" } //观看每分钟所在小时数（天）hh24
var TIME_TAGS_MINUTE         = { id: "time_tags.minute", text:"在线观看每分钟" } //在线观看每分钟 yyyy-mm-dd hh24:mi//end
var VOD_TITLE                = { id: "vod_title", text:"vod节目名称" } //vod节目名称
var VOD_PROVIDER             = { id: "vod_provider", text:"vod提供商" } //vod提供商
var VOD_CAT_TAGS             = { id: "vod_cat_tags", text:"vod节目包相关信息" } //vod节目包相关信息（nested object）按不同的节目包目录组织
var VOD_CAT_TAGS_LEVEL1_NAME = { id: "vod_cat_tags.level1_name", text:"vod所属顶层包名" } //该vod所属顶层包名
var VOD_CAT_TAGS_LEVEL2_NAME = { id: "vod_cat_tags.level2_name", text:"vod所属第二层包名" } //该vod所属第二层包名
var VOD_CAT_TAGS_LEVEL3_NAME = { id: "vod_cat_tags.level3_name", text:"vod所属第三层包名" } //该vod所属第三层包名
var VOD_CAT_TAGS_LEVEL4_NAME = { id: "vod_cat_tags.level4_name", text:"vod所属第四层包名" } //该vod所属第四层包名
var VOD_CAT_TAGS_LEVEL5_NAME = { id: "vod_cat_tags.level5_name", text:"vod所属第四层包名" } //该vod所属第五层包名
var DIRECTORS                = { id: "directors", text:"节目导演" } //节目导演
var ACTORS                   = { id: "actors", text:"节目演员" } //节目演员
var CONTENT                  = { id: "content", text:"节目内容" } //节目内容
var CATEGORY_NAME            = { id: "category_name", text:"节目所属分类" } //节目所属分类
var SERIES_CATEGORY          = { id: "series_category", text:"节目系列剧分类" } //节目系列剧分类
var REGION                   = { id: "region", text:"节目地区信息" } //节目地区信息



var COMMON_FILTERS = [
	PHONE_NO,
	CUST_ID,
	CONTRACT_ID,
	TERMINAL_NO,
	CUST_NAME,
	OWNER_NAME,
	SM_NAME,
	RUN_NAME,
	GROUP_NAME,
	ADDRESS_NAME1,
	ADDRESS_NAME2,
	ADDRESS_NAME3,
	ADDRESS_NAME4,
	ADDRESSOJ
]


var VIEW_EVENT_QUERY_FILTERS = [
	PHONE_NO,
	CUST_ID,
	CUST_NAME,
	OWNER_GRADE,
	OWNER_NAME,
	GROUP_NAME,
	ADDRESS_NAME1,
	ADDRESS_NAME2,
	ADDRESS_NAME3,
	ADDRESS_NAME4,
	ADDRESSOJ,
	GROUP_NAME,
	PROGRAM_TITLE,
	STATION_NAME,
	VOD_TITLE,
	DIRECTORS,
	ACTORS,
	CONTENT,
	CATEGORY_NAME,
	SERIES_CATEGORY,
	REGION
];

var TOPN_GROUP_BY = [
	PHONE_NO,
	CUST_ID,
	CONTRACT_ID,
	TERMINAL_NO,
	CUST_NAME,
	OWNER_NAME,
	SM_NAME,
	RUN_NAME,
	GROUP_NAME,
	ADDRESS_NAME1,
	ADDRESS_NAME2,
	ADDRESS_NAME3,
	ADDRESS_NAME4,
	ADDRESSOJ,
	PROGRAM_TITLE,
	STATION_NAME,
	VOD_TITLE,
	DIRECTORS,
	ACTORS,
	CONTENT,
	CATEGORY_NAME,
	SERIES_CATEGORY,
	REGION,
	CHANNEL_GROUP_NAME,
	PAY_NAME
];

var CHART_CATEGORY_FIELD_NAME = "xLabels"; //图表分类轴所对应的字段名称
var CHART_X_FIELD_NAME        = "xLabels"; //图表分类轴所对应的字段名称
var CHART_Y_FIELD_NAME        = "yLabels"; //图表分类轴所对应的字段名称
var CHART_VALUE_FIELD_NAME    = "valueList"; //图表数值轴所对应的字段名称



var DATAVIEWMAP = {};


var RELATE_DATA_OPTION = [{
	id: "w",
	text: "个星期前"
}, {
	id: "M",
	text: "个月前"
}, {
	id: "d",
	text: "天前"
}, {
	id: "h",
	text: "个小时前"
}, {
	id: "m",
	text: "分钟前"
}, {
	id: "y",
	text: "年前"
}]
function AppInit(){
	var loader = new DataLoader();

	loader.getDataAsyn(BASE_RUL + "app/ui/res/dataview", function(data){
		DATAVIEWMAP = data;
	});
}


function getUnitShowText(unitName, len){
	switch (unitName) {
		case YEAR_SHORT:
			return "每" + getNumberCH(len) + "年";
		case MONTH_SHORT:
			return "每" + getNumberCH(len) + "月";
		case DAY_SHORT:
			return "每" + getNumberCH(len) + "天";
		case HOUR_SHORT:
			return "每" + getNumberCH(len) + "小时";
		case MINUTER_SHORT:
			return "每" + getNumberCH(len) + "分钟";
	}
}
function getNumberCH(num){
	switch(+num){
		case 1:
		return "";
		case 2:
		return "两";
		case 3:
		return "三";
		case 4:
		return "四";
		case 5:
		return "五";
		case 6:
		return "六";
		case 7:
		return "七";
		case 8:
		return "八";
		case 9:
		return "九";
		default:
		return num;
	}
}
function getTimeUnitMax(unitName) {
	switch (unitName) {
		case YEAR_SHORT:
			return 366;
		case MONTH_SHORT:
			return 12;
		case DAY_SHORT:
			return 31;
		case HOUR_SHORT:
			return 24;
		case MINUTER_SHORT:
			return 60;
	}
}

function getTimeUnitMin(unitName) {
	switch (unitName) {
		case YEAR_SHORT:
			return 0;
		case MONTH_SHORT:
			return 0;
		case DAY_SHORT:
			return 0;
		case HOUR_SHORT:
			return -1;
		case MINUTER_SHORT:
			return -1;
	}
}

function getTimeUnitSkip(unitName) {
	switch (unitName) {
		case YEAR_SHORT:
			return 0;
		case MONTH_SHORT:
			return 0;
		case DAY_SHORT:
			return 0;
		case HOUR_SHORT:
			return 1;
		case MINUTER_SHORT:
			return 1;
	}
}

function getKendoTimeUnit(unitName) {
	switch (unitName) {
		case YEAR_SHORT:
			return "years";
		case MONTH_SHORT:
			return "months";
		case DAY_SHORT:
			return "days";
		case HOUR_SHORT:
			return "hours";
		case WEEK_SHORT:
			return "weeks";
	}
}

function calcViewWidth(start, end, unit, len){

}
function calcViewHeight(sliecUnit, len) {
	if (sliecUnit == null || sliecUnit == undefined)
		return "200px";
	switch (sliecUnit) {
		case MONTH_SHORT:
			var i = 12 / len;
			if (i <= 6)
				return "200px";
			if (i <= 12)
				return "400px";
		case DAY_SHORT:
			var i = 32 / len;
			if (i < 6)
				return "200px";
			if (i < 12)
				return "400px";
			if (i < 24)
				return "700px";
			if (i < 32)
				return "900px";

		case HOUR_SHORT:
			var i = 23 / len;
			if (i < 6)
				return "200px";
			if (i < 12)
				return "400px";
			if (i < 24)
				return "700px";
		case MINUTER_SHORT:
			var i = 59 / len;
			if (i < 5)
				return "200px";
			if (i < 10)
				return "400px";
			if (i < 30)
				return "600px";
			if (i < 60)
				return "800px";

	}
	return "200px";
}
