$(function () {
    if (!Utils.checkLogin()) return;
    UserCheckManage.init();
    $(window).resize(function () {
        $('#table').bootstrapTable('resetView');
        resizeWH();
    });

    var resizeWH = function () {
        $(".table-box").height($(window).height() - 130);
    };
    resizeWH();
});

var UserCheckManage = {
	"PageSize" : 50,
	"init" : function(){
		$('#table').bootstrapTable({
			columns: [
				{ field: 'realName', title: '姓名', width: 50},
				{ field: 'nickName', title: '昵称', width: 50 },
				{ field: 'userName', title: '账号', width: 50},
				{ field: 'checkStatus', title: '审核状态', width: 50, formatter: UserCheckManage.formatterCheckStatus },
				{ field: 'status', title: '用户状态', width: 50, formatter: UserCheckManage.formatterStatus },
				{ field: 'realName', title: '操作', width: 50, formatter: UserCheckManage.formatterOp }
			],
			onDblClickRow: function (row) {
			    UserCheckManage.openDetail(row.id);
			}
		});
		$(".query-btn").bind("click", function () {
			UserCheckManage.search();
		});
		$(".reset-btn").bind("click", function () {
			UserCheckManage.reset();
		});
		$("#btnAdd").bind("click", function () {
			UserCheckManage.openDetail();
		});

		$('.detailDialog .dialog-btn').click(function (e) {
			$('.detailDialog').Dialog('close');
		});
		$('.moneyDialog .dialog-btn').click(function (e) {
			$('.moneyDialog').Dialog('close');
		});

		UserCheckManage.search();
	},
	"search" : function(){
		this.searchData(0, function(data){
			$("#pagin").pagination(data.total, {
				callback: UserCheckManage.pageSelectCallback,
				items_per_page: UserCheckManage.PageSize //每页显示1项
			});
		});
	},
	"searchData": function (pageIndex, callback) {
		var query = Utils.getSearchQuery("tbQueryBody");
		query.offset = pageIndex;
		query.limit = UserCheckManage.PageSize;
		UIUtils.loading('正在查询请稍候......');
		HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.USER_SEARCH, query, function(data){
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
		UserCheckManage.searchData(page, function(data){ });
	},
	"formatterOp" : function(value, row, index){
		if(row.checkStatus == 0){
			return '<a href="javascript:UserCheckManage.openDetail(\''+row.id+'\');">审核</a>';
		}
		return '';
	},
	"formatterCheckStatus" : function(value, row, index){
		if(value == 1){
			return '<span style="color:green">通过</span>';
		}else if(value == -1){
			return '<span style="color:red">待提交认证</span>';
		}else if(value == 2){
			return '<span style="color:red">不通过</span>';
		}else{
			return '<span style="color:gray">待审核</span>';
		}
	},
	"formatterStatus" : function(value, row, index){
		if(value == 1){
			return '<span style="color:red">已删除</span>';
		}else if(value == 2){
			return '<span style="color:gray">已禁用</span>';
		}else{
			return '<span style="color:green">正常</span>';
		}
	},
	"openDetail" : function(id){
		var t  = new Date().getTime();
		var url = "user_check_add.html?t=" + t;
		if(id){
			url += "&id=" + id;
		}
		UIUtils.openWindow("实名认证审核", url, 800, 560);
	}
};
