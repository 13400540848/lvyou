$(function () {
	$(window).resize(function () {
		$('#tableType').bootstrapTable('resetView');
		resizeWH();
	});

	var resizeWH = function () {
		$(".table-box").height($(window).height() - 50);
	};

	var id = Utils.getUrlParam("id");
	if (id) {
		PlayThreeResultAdd.Id = id;
		resizeWH();
		PlayThreeResultAdd.init();
	}
});

var PlayThreeResultAdd = {
	"PageSize": 50,
	"Id": null,
	"MoneyTypeList": null,
	"ProjectData":{},
	"init": function () {
		$("#btnClose").click(function () {
			if (parent && parent.UIUtils) {
				parent.UIUtils.closeWindow();
			}
		});
		if (PlayThreeResultAdd.Id) {
			$('#tableType').bootstrapTable({
				columns: [
					{ field: 'rewardCodeName', title: '奖项', width: 50 },
					{ field: 'perMoney', title: '单注金额', width: 50 },
					{ field: 'countNumber', title: '中奖注数', width: 50 },
					{ field: 'rewardMoney', title: '中奖金额', width: 50 },
					{ field: 'createTime', title: '抽奖时间', width: 100, formatter: FormatUtils.parseTimestampToDateTime }
				]
			});
			PlayThreeResultAdd.search();
		}
	},
	"search": function () {
		UIUtils.loading('正在查询请稍候......');
		HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.PLAY_THREE_RESULT_REWRAD, {playTime:PlayThreeResultAdd.Id}, function (data) {
			UIUtils.loaded();
			if (data && data.rows) {
				$('#tableType').bootstrapTable('load', data.rows);
			}
		});
	}
};