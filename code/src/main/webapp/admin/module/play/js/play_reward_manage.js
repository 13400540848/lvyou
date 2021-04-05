$(function () {
	$(window).resize(function () {
		$('#table1').bootstrapTable('resetView');
		$('#table2').bootstrapTable('resetView');
	});
	PlayRewardManage.init();
});

var PlayRewardManage = {
	"PageSize": 15,
	"Id": null,
	"MoneyTypeList": null,
	"ProjectData":{},
	"init": function () {
		$("#btnAdd1").click(function(){
			PlayRewardManage.add1(PlayRewardManage.Id);
		});
		$("#btnAdd2").click(function(){
			PlayRewardManage.add2(PlayRewardManage.Id);
		});
		$('#table1').bootstrapTable({
			columns: [
				// { field: 'typeId', title: '币种编号', width: 50 },
				{ field: 'rewardName', title: '奖项名称', width: 50 },
				{ field: 'rewardCodeName', title: '奖项标识', width: 50 },
				{ field: 'rewardModeName', title: '奖励类型', width: 50 },
				{ field: 'rewardTimes', title: '奖励倍数', width: 50 },
				{ field: 'rewardPercent', title: '浮动奖金百分比', width: 50, formatter:FormatUtils.formatPercent },
				{ field: 'maxMoney', title: '封顶金额', width: 50, formatter:FormatUtils.formatDG },
				{ field: 'requireMoney', title: '要求奖池数量', width: 50, formatter:FormatUtils.formatDG },				
				{ field: 'rewardName', title: '操作', width: 50, formatter: PlayRewardManage.formatterOp1 }
			]
		});
		PlayRewardManage.search1();
		$('#table2').bootstrapTable({
			columns: [
				// { field: 'typeId', title: '币种编号', width: 50 },
				{ field: 'rewardName', title: '奖项名称', width: 50 },
				{ field: 'rewardCodeName', title: '奖项标识', width: 50 },
				{ field: 'rewardTimes', title: '奖励倍数', width: 50 },
				{ field: 'rewardName', title: '操作', width: 50, formatter: PlayRewardManage.formatterOp2 }
			]
		});
		PlayRewardManage.search2();
	},
	"search1":function(){
		UIUtils.loading('正在查询请稍候......');
		HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.PLAY_SEVEN_REWRAD, {}, function (data) {
			UIUtils.loaded();
			if (data && data.rows) {
				$('#table1').bootstrapTable('load', data.rows);
			}
		});			
	},
	"search2":function(){
		UIUtils.loading('正在查询请稍候......');
		HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.PLAY_THREE_REWRAD, {}, function (data) {
			UIUtils.loaded();
			if (data && data.rows) {
				$('#table2').bootstrapTable('load', data.rows);
			}
		});			
	},
	"formatterScale": function (value, row, index) {
		return "1" + row.typeName + "=" + value + PlayRewardManage.ProjectData.moneyTypeName;
	},
	"formatterOp1": function (value, row, index) {
		return '<a href="javascript:PlayRewardManage.edit1(\'' + row.id + '\');">修改</a>&nbsp;&nbsp;&nbsp;' +
			'<a href="javascript:PlayRewardManage.delete1(\'' + value + '\', \'' + row.id + '\');">删除</a>';
	},
	"formatterOp2": function (value, row, index) {
		return '<a href="javascript:PlayRewardManage.edit2(\'' + row.id + '\');">修改</a>&nbsp;&nbsp;&nbsp;' +
			'<a href="javascript:PlayRewardManage.delete2(\'' + value + '\', \'' + row.id + '\');">删除</a>';
	},
	"add1":function(){
		var t  = new Date().getTime();
		var url = "play_seven_reward_add.html?t=" + t;
		UIUtils.openWindow("添加奖项设置", url, 420, 260);
	},
	"edit1":function(id){
		var t  = new Date().getTime();
		var url = "play_seven_reward_add.html?t=" + t + "&id=" + id;
		UIUtils.openWindow("修改奖项设置", url, 420, 260);
	},
	"delete1":function(title, id){
		UIUtils.confirm("确定要删除此奖项【"+title+"】吗？", function(){
			UIUtils.loading('正在删除请稍候......');
			HttpUtils.httpDelete(Config.WEB_SERVER_API + Config.URI.PLAY_SEVEN_REWRAD + "/" + id, {}, function (data, success) {
				UIUtils.loaded();
				if(success){
					PlayRewardManage.search1();
				}
			});
		});
	},
	"add2":function(){
		var t  = new Date().getTime();
		var url = "play_three_reward_add.html?t=" + t;
		UIUtils.openWindow("添加奖项设置", url, 400, 230);
	},
	"edit2":function(id){
		var t  = new Date().getTime();
		var url = "play_three_reward_add.html?t=" + t + "&id=" + id;
		UIUtils.openWindow("修改奖项设置", url, 400, 230);
	},
	"delete2":function(title, id){
		UIUtils.confirm("确定要删除此奖项【"+title+"】吗？", function(){
			UIUtils.loading('正在删除请稍候......');
			HttpUtils.httpDelete(Config.WEB_SERVER_API + Config.URI.PLAY_THREE_REWRAD + "/" + id, {}, function (data, success) {
				UIUtils.loaded();
				if(success){
					PlayRewardManage.search2();
				}
			});
		});
	}
};