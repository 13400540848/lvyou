$(function () {
	if (!Utils.checkLogin()) return;
	PlayResultManage.init();
	$(window).resize(function () {
		$('#table1').bootstrapTable('resetView');
		$('#table2').bootstrapTable('resetView');
		resizeWH();
	});	
    var resizeWH = function () {
        $(".table-box").height($(window).height() - 180);
    };
    resizeWH();
});

var PlayResultManage = {
	"PageSize": 50,
	"Id": null,
	"MoneyTypeList": null,
	"ProjectData":{},
	"init": function () {
		$("#btnAdd1").click(function(){
			PlayResultManage.add1(PlayResultManage.Id);
		});
		$("#btnAdd2").click(function(){
			PlayResultManage.add2(PlayResultManage.Id);
		});
		$('#table1').bootstrapTable({
			columns: [
				// { field: 'typeId', title: '币种编号', width: 50 },
				{ field: 'playTime', title: '期号', width: 50 },
				{ field: 'rewardModeName', title: '开奖选号', width: 100, formatter:PlayResultManage.formatterNumber1 },
				{ field: 'perMoney', title: '单注金额', width: 50, formatter:FormatUtils.formatDG },
				{ field: 'countNumber', title: '投注数量', width: 50 },
				{ field: 'sumMoney', title: '总金额', width: 50, formatter:FormatUtils.formatDG },
				{ field: 'rewardMoney', title: '中奖金额', width: 50, formatter:FormatUtils.formatDG },
				{ field: 'leaveMoney', title: '利润', width: 50, formatter:FormatUtils.formatDG },
				{ field: 'statusName', title: '状态', width: 50 },
				{ field: 'createTime', title: '开始时间', width: 100, formatter:FormatUtils.parseTimestampToDateTime },
				{ field: 'endTime', title: '截止时间', width: 100, formatter:FormatUtils.parseTimestampToDateTime },
				{ field: 'publishTime', title: '开奖时间', width: 100, formatter:FormatUtils.parseTimestampToDateTime },
				{ field: 'rewardName', title: '操作', width: 50, formatter: PlayResultManage.formatterOp1 }
			]
		});
		PlayResultManage.search1();
		$(".query-btn1").bind("click", function () {
			PlayResultManage.search1();
		});
		$(".reset-btn1").bind("click", function () {
			PlayResultManage.reset1();
		});
		$('#table2').bootstrapTable({
			columns: [
				// { field: 'typeId', title: '币种编号', width: 50 },
				{ field: 'playTime', title: '期号', width: 50 },
				{ field: 'rewardModeName', title: '开奖选号', width: 50, formatter:PlayResultManage.formatterNumber2 },
				{ field: 'modeName', title: '选号形态', width: 50},
				{ field: 'perMoney', title: '单注金额', width: 50, formatter:FormatUtils.formatDG },
				{ field: 'countNumber', title: '投注数量', width: 50 },
				{ field: 'sumMoney', title: '总金额', width: 50, formatter:FormatUtils.formatDG },
				{ field: 'rewardMoney', title: '中奖金额', width: 50, formatter:FormatUtils.formatDG },
				{ field: 'leaveMoney', title: '利润', width: 50, formatter:FormatUtils.formatDG },
				{ field: 'statusName', title: '状态', width: 50 },
				{ field: 'createTime', title: '开始时间', width: 100, formatter:FormatUtils.parseTimestampToDateTime },
				{ field: 'endTime', title: '截止时间', width: 100, formatter:FormatUtils.parseTimestampToDateTime },
				{ field: 'publishTime', title: '开奖时间', width: 100, formatter:FormatUtils.parseTimestampToDateTime },
				{ field: 'rewardName', title: '操作', width: 50, formatter: PlayResultManage.formatterOp2 }
			]
		});
		PlayResultManage.search2();
		$(".query-btn2").bind("click", function () {
			PlayResultManage.search2();
		});
		$(".reset-btn2").bind("click", function () {
			PlayResultManage.reset2();
		});
	},
	"search1" : function(){
		this.searchData1(0, function(data){
			$("#pagin1").pagination(data.total, {
				callback: PlayResultManage.pageSelectCallback1,
				items_per_page: PlayResultManage.PageSize //每页显示1项
			});
		});
	},
	"searchData1": function (pageIndex, callback) {
		var query = Utils.getSearchQuery("tbQueryBody1");
		query.offset = pageIndex + 1;
		query.limit = PlayResultManage.PageSize;
		UIUtils.loading('正在查询请稍候......');
		HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.PLAY_SEVEN_RESULT, query, function(data){
			UIUtils.loaded();
			$('#table1').bootstrapTable('load', data.rows);
			if(callback){
				callback(data);
			}
		});
	},
	"pageSelectCallback1" : function(page) {
		PlayResultManage.searchData1(page, function(data){ });
	},
	"reset1":function(){
		Utils.resetSearchQuery("tbQueryBody1");
	},
	"search2" : function(){
		this.searchData2(0, function(data){
			$("#pagin2").pagination(data.total, {
				callback: PlayResultManage.pageSelectCallback2,
				items_per_page: PlayResultManage.PageSize //每页显示1项
			});
		});
	},
	"searchData2": function (pageIndex, callback) {
		var query = Utils.getSearchQuery("tbQueryBody2");
		query.offset = pageIndex + 1;
		query.limit = PlayResultManage.PageSize;
		UIUtils.loading('正在查询请稍候......');
		HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.PLAY_THREE_RESULT, query, function(data){
			UIUtils.loaded();
			$('#table2').bootstrapTable('load', data.rows);
			if(callback){
				callback(data);
			}
		});
	},
	"pageSelectCallback2" : function(page) {
		PlayResultManage.searchData2(page, function(data){ });
	},
	"reset2":function(){
		Utils.resetSearchQuery("tbQueryBody2");
	},
	"formatterNumber1": function (value, row, index) {
		if(row.sixNumber && row.oneNumber)
			return row.sixNumber + " | " + row.oneNumber;
		return "";
	},
	"formatterNumber2": function (value, row, index) {
		if(row.number1 && row.number2 && row.number3)
			return row.number1 + " " + row.number2 + " " + row.number3;
		return "";
	},
	"formatterOp1": function (value, row, index) {
		return '<a href="javascript:PlayResultManage.edit1(\'' + row.playTime + '\');">详情</a>';
	},
	"formatterOp2": function (value, row, index) {
		return '<a href="javascript:PlayResultManage.edit2(\'' + row.playTime + '\');">详情</a>';
	},
	"edit1":function(id){
		var t  = new Date().getTime();
		var url = "play_seven_result_add.html?t=" + t + "&id=" + id;
		UIUtils.openWindow("查看详情", url, 620, 320);
	},
	"edit2":function(id){
		var t  = new Date().getTime();
		var url = "play_three_result_add.html?t=" + t + "&id=" + id;
		UIUtils.openWindow("查看详情", url, 620, 320);
	}
};