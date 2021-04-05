$(function () {
    if (!Utils.checkLogin()) return;
    UserManage.init();
    $(window).resize(function () {
        $('#table').bootstrapTable('resetView');
        resizeWH();
    });

    var resizeWH = function () {
        $(".table-box").height($(window).height() - 130);
    };
    resizeWH();
});

var UserManage = {
	"PageSize" : 50,
	"init" : function(){
		$('#table').bootstrapTable({
			columns: [
				{ field: 'realName', title: '姓名', width: 50},
				{ field: 'nickName', title: '昵称', width: 50 },
				{ field: 'userName', title: '账号', width: 50},
				{ field: 'checkStatus', title: '审核状态', width: 50, formatter: UserManage.formatterCheckStatus },
				{ field: 'status', title: '用户状态', width: 50, formatter: UserManage.formatterStatus },
				{ field: 'realName', title: '操作', width: 50, formatter: UserManage.formatterOp }
			],
			onDblClickRow: function (row) {
			    UserManage.openDetail(row.id);
			}
		});
		$(".query-btn").bind("click", function () {
			UserManage.search();
		});
		$(".reset-btn").bind("click", function () {
			UserManage.reset();
		});
		$("#btnAdd").bind("click", function () {
			UserManage.openDetail();
		});

		$('.detailDialog .dialog-btn').click(function (e) {
			$('.detailDialog').Dialog('close');
		});
		$('.moneyDialog .dialog-btn').click(function (e) {
			$('.moneyDialog').Dialog('close');
		});

		UserManage.search();
	},
	"search" : function(){
		this.searchData(0, function(data){
			$("#pagin").pagination(data.total, {
				callback: UserManage.pageSelectCallback,
				items_per_page: UserManage.PageSize //每页显示1项
			});
		});
	},
	"searchData": function (pageIndex, callback) {
		var query = Utils.getSearchQuery("tbQueryBody");
		query.offset = pageIndex;
		query.limit = UserManage.PageSize;
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
		UserManage.searchData(page, function(data){ });
	},
	"formatterOp" : function(value, row, index){
		return '<a href="javascript:UserManage.openDetail(\''+row.id+'\');">查看详情</a>';
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
		var url = "user_info.html?t=" + t;
		if(id){
			url += "&id=" + id;
		}
		UIUtils.openWindow("用户详情", url, 880, 560);
	}
};
