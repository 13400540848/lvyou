$(function () {
    if (!Utils.checkLogin()) return;
    UserCashManage.init();
    $(window).resize(function () {
        $('#table').bootstrapTable('resetView');
        resizeWH();
    });

    var resizeWH = function () {
        $(".table-box").height($(window).height() - 130);
    };
    resizeWH();
});

var UserCashManage = {
	"PageSize" : 50,
	"init" : function(){
		$('#table').bootstrapTable({
			columns: [
				{ field: 'realName', title: '姓名', width: 50},
				{ field: 'userName', title: '账号', width: 50},
				{ field: 'orderId', title: '订单号', width: 80 },
				{ field: 'money', title: '数量', width: 50 },
				{ field: 'brokeMoney', title: '手续费', width: 50 },
				{ field: 'realMoney', title: '到账数量', width: 50 },
				{ field: 'typeName', title: '币种', width: 50},
				{ field: 'moneyAddress', title: '接币地址', width: 80},
				{ field: 'createTime', title: '申请时间', width: 70, formatter: FormatUtils.parseTimestampToDateTime },
				{ field: 'checkStatusName', title: '审核状态', width: 50},
				{ field: 'checkReason', title: '审核意见', width: 100},
				{ field: 'realNamexx', title: '操作', width: 50, formatter: UserCashManage.formatterOp }
			],
			onDblClickRow: function (row) {
			    // UserCashManage.openDetail(row.id);
			}
		});
		$(".query-btn").bind("click", function () {
			UserCashManage.search();
		});
		$(".reset-btn").bind("click", function () {
			UserCashManage.reset();
		});
		$("#btnAdd").bind("click", function () {
			UserCashManage.openDetail();
		});

		$('.detailDialog .dialog-btn').click(function (e) {
			$('.detailDialog').Dialog('close');
		});
		$('.moneyDialog .dialog-btn').click(function (e) {
			$('.moneyDialog').Dialog('close');
		});

		UserCashManage.search();
	},
	"search" : function(){
		this.searchData(0, function(data){
			$("#pagin").pagination(data.total, {
				callback: UserCashManage.pageSelectCallback,
				items_per_page: UserCashManage.PageSize //每页显示1项
			});
		});
	},
	"searchData": function (pageIndex, callback) {
		var query = Utils.getSearchQuery("tbQueryBody");
		query.offset = pageIndex;
		query.limit = UserCashManage.PageSize;
		UIUtils.loading('正在查询请稍候......');
		HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.USER_MONEY_CASH_SEARCH, query, function(data){
			UIUtils.loaded();
			$('#table').bootstrapTable('load', data.rows);
			if(callback){
				callback(data);
			}
		});
	},
	"reset":function(){
		Utils.resetSearchQuery("tbQueryBody");
	},
	"pageSelectCallback" : function(page) {
		UserCashManage.searchData(page, function(data){ });
	},
	"formatterOp" : function(value, row, index){
		if(row.checkStatus == 0){
			return '<a href="javascript:UserCashManage.openDetail(\''+row.id+'\');">审核</a>';
		}
		return '';
	},
	"openDetail" : function(id){
		var t  = new Date().getTime();
		var url = "user_cash_add.html?t=" + t;
		if(id){
			url += "&id=" + id;
		}
		UIUtils.openWindow("提现审核", url, 600, 290);
	}
};
