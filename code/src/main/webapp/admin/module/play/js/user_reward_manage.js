$(function () {
	if (!Utils.checkLogin()) return;
	UserRewardManage.init();
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

var UserRewardManage = {
	"PageSize": 50,
	"Id": null,
	"MoneyTypeList": null,
	"ProjectData":{},
	"init": function () {
		$("#btnAdd1").click(function(){
			UserRewardManage.add1(UserRewardManage.Id);
		});
		$("#btnAdd2").click(function(){
			UserRewardManage.add2(UserRewardManage.Id);
		});
		$('#table1').bootstrapTable({
			columns: [
				// { field: 'typeId', title: '币种编号', width: 50 },
				{ field: 'playTime', title: '期号', width: 50 },
				{ field: 'userName', title: '用户账号', width: 50 },
				{ field: 'modeName', title: '模式', width: 50 },
				{ field: 'rewardModeName', title: '选号', width: 100, formatter:UserRewardManage.formatterNumber1 },
				{ field: 'statusName', title: '开奖结果', width: 50 },
				{ field: 'sumMoney', title: '投注金额', width: 50, formatter:FormatUtils.formatDG },
				{ field: 'createTime', title: '投注时间', width: 100, formatter:FormatUtils.parseTimestampToDateTime },
				{ field: 'rewardCodeName', title: '中奖奖项', width: 50 },				
				{ field: 'rewardMoney', title: '中奖金额', width: 50, formatter:FormatUtils.formatDG },
				{ field: 'rewardName', title: '操作', width: 50, formatter: UserRewardManage.formatterOp }
			]
		});
		UserRewardManage.search1();
		$(".query-btn1").bind("click", function () {
			UserRewardManage.search1();
		});
		$(".reset-btn1").bind("click", function () {
			UserRewardManage.reset1();
		});
		$('#table2').bootstrapTable({
			columns: [
				// { field: 'typeId', title: '币种编号', width: 50 },
				{ field: 'playTime', title: '期号', width: 50 },
				{ field: 'userName', title: '用户账号', width: 50 },
				{ field: 'modeName', title: '模式', width: 50 },
				{ field: 'rewardModeName', title: '选号', width: 100, formatter:UserRewardManage.formatterNumber2 },
				{ field: 'statusName', title: '开奖结果', width: 50 },
				{ field: 'sumMoney', title: '投注金额', width: 50, formatter:FormatUtils.formatDG },
				{ field: 'createTime', title: '投注时间', width: 100, formatter:FormatUtils.parseTimestampToDateTime },
				{ field: 'rewardCodeName', title: '中奖奖项', width: 50 },				
				{ field: 'rewardMoney', title: '中奖金额', width: 50, formatter:FormatUtils.formatDG },
				{ field: 'rewardName', title: '操作', width: 50, formatter: UserRewardManage.formatterOp }
			]
		});
		UserRewardManage.search2();
		$(".query-btn2").bind("click", function () {
			UserRewardManage.search2();
		});
		$(".reset-btn2").bind("click", function () {
			UserRewardManage.reset2();
		});
	},
	"search1" : function(){
		this.searchData1(0, function(data){
			$("#pagin1").pagination(data.total, {
				callback: UserRewardManage.pageSelectCallback1,
				items_per_page: UserRewardManage.PageSize //每页显示1项
			});
		});
	},
	"searchData1": function (pageIndex, callback) {
		var query = Utils.getSearchQuery("tbQueryBody1");
		query.offset = pageIndex;
		query.limit = UserRewardManage.PageSize;
		UIUtils.loading('正在查询请稍候......');
		HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.USER_PLAY_SEVEN_LIST, query, function(data){
			UIUtils.loaded();
			$('#table1').bootstrapTable('load', data.rows);
			if(callback){
				callback(data);
			}
		});
	},
	"pageSelectCallback1" : function(page) {
		UserRewardManage.searchData1(page, function(data){ });
	},
	"reset1":function(){
		Utils.resetSearchQuery("tbQueryBody1");
	},
	"search2" : function(){
		this.searchData2(0, function(data){
			$("#pagin2").pagination(data.total, {
				callback: UserRewardManage.pageSelectCallback2,
				items_per_page: UserRewardManage.PageSize //每页显示1项
			});
		});
	},
	"searchData2": function (pageIndex, callback) {
		var query = Utils.getSearchQuery("tbQueryBody2");
		query.offset = pageIndex;
		query.limit = UserRewardManage.PageSize;
		UIUtils.loading('正在查询请稍候......');
		HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.USER_PLAY_THREE_LIST, query, function(data){
			UIUtils.loaded();
			$('#table2').bootstrapTable('load', data.rows);
			if(callback){
				callback(data);
			}
		});
	},
	"pageSelectCallback2" : function(page) {
		UserRewardManage.searchData2(page, function(data){ });
	},
	"reset2":function(){
		Utils.resetSearchQuery("tbQueryBody2");
	},
	"formatterNumber1": function (value, row, index) {
		return row.sixNumber + " | " + row.oneNumber;
	},
	"formatterNumber2": function (value, row, index) {
		return row.number;
	},
	"formatterOp": function (value, row, index) {
		// return '<a href="javascript:UserRewardManage.edit1(\'' + row.id + '\');">修改</a>&nbsp;&nbsp;&nbsp;' +
		// 	'<a href="javascript:UserRewardManage.delete1(\'' + value + '\', \'' + row.id + '\');">删除</a>';
		return "";
	},
	"add1":function(){
		var t  = new Date().getTime();
		var url = "play_seven_reward_add.html?t=" + t;
		UIUtils.openWindow("添加奖项设置", url, 420, 450);
	},
	"edit1":function(id){
		var t  = new Date().getTime();
		var url = "play_seven_reward_add.html?t=" + t + "&id=" + id;
		UIUtils.openWindow("修改奖项设置", url, 420, 450);
	},
	"delete1":function(title, id){
		UIUtils.confirm("确定要删除此奖项【"+title+"】吗？", function(){
			UIUtils.loading('正在删除请稍候......');
			HttpUtils.httpDelete(Config.WEB_SERVER_API + Config.URI.PLAY_SEVEN_REWRAD + "/" + id, {}, function (data, success) {
				UIUtils.loaded();
				if(success){
					UserRewardManage.search1();
				}
			});
		});
	},
};