$(function () {
    if (!Utils.checkLogin()) return;
    DataUtils.getDictItems(UserManage.DictKeys, function(items){
    	UserManage.DictItemList = items;
    	DataTableUtils.setSearchQueryDict("tbQueryBody", items);
    	UserManage.init();
    });
    $(window).resize(function () {
        $('#table').bootstrapTable('resetView');
        resizeWH();
    });

    var resizeWH = function () {
        $(".table-box").height($(window).height() - 160);
    };
    resizeWH();
});

var UserManage = {
	"PageSize" : 50,
	"DictKeys":["USER_TYPE", "NORMAL_STATUS","SEX"],
	"DictItemList":null,
	"init" : function(){
		$('#table').bootstrapTable({
			columns: [
	          	{ field: 'title', title: '操作', width: 180, align:'center', formatter: UserManage.formatterOp },
				{ field: 'account', title: '账号', width: 80 },
				{ field: 'realName', title: '姓名', width: 80 },
				{ field: 'mobilePhone', title: '手机号', width: 80 },
				{ field: 'sex', title: '性别', width: 40, dictKey:'SEX', dictItems:UserManage.DictItemList, formatter: FormatUtils.formatDictItem },
				{ field: 'type', title: '类型', width: 40, dictKey:'USER_TYPE', dictItems:UserManage.DictItemList, formatter: FormatUtils.formatDictItem },
				{ field: 'status', title: '状态', width: 40, dictKey:'NORMAL_STATUS', dictItems:UserManage.DictItemList, formatter: FormatUtils.formatDictItem },				
				{ field: 'mail', title: '邮箱', width: 80 }
//                { field: 'locationName', title: '广告位置', width: 50 },				
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
		var query = {};
		query.condition = DataTableUtils.getSearchQuery("tbQueryBody");
		DataTableUtils.getQueryPageList(Config.URI.USER_SEARCH, query, pageIndex, UserManage.PageSize, function(data){
			$('#table').bootstrapTable('load', data);
			if(callback){
				callback(data);
			}
		});
	},
	"reset":function(){
		DataTableUtils.resetSearchQuery("tbQueryBody");
		UserManage.search();
	},
	"pageSelectCallback" : function(page) {
		UserManage.searchData(page, function(data){ });
	},
	"formatterOp" : function(value, row, index){
		return '<div class="table-buttons"><a href="javascript:UserManage.openDetail(\''+row.id+'\');" class="edit">修改</a>&nbsp;&nbsp;&nbsp;'+
		'<a href="javascript:UserManage.deleteData(\''+row.realName+'\', \''+row.id+'\');" class="del">删除</a>&nbsp;&nbsp;&nbsp;'+
			'<a href="javascript:UserManage.resetPassword(\''+row.realName+'\', \''+row.id+'\');" class="check">重置密码</a>&nbsp;&nbsp;&nbsp;'+
		'<a href="javascript:UserManage.setRole(\''+row.realName+'\', \''+row.id+'\');" class="count">设置角色</a>&nbsp;&nbsp;&nbsp;</div>';
	},
	"formatterIcon" : function(value, row, index){
		return '<div class="form-icon"><div class="item-icon '+value+'"></div></div>';
	},
	"openDetail" : function(id){
		var t  = new Date().getTime();
		var url = "user_form.html?t=" + t;
		if(id){
			url += "&id=" + id;
		}
		UIUtils.openWindow((id?"修改":"添加")+"用户", url, 800, 520);
	},
	"deleteData":function(name, id){
		UIUtils.confirm("确定要删除【"+name+"】用户吗？", function(){
			UIUtils.loading('正在删除请稍候......');
			HttpUtils.httpDelete(Config.WEB_SERVER_API + Config.URI.USER_DELETE + id, {}, function (data, success) {
				UIUtils.loaded();
				if(success){
					UserManage.search();
				}
			});
		});
	},
	"resetPassword":function(name, id){
		UIUtils.confirm("确定要重置【"+name+"】的密码吗？<br />（重置后密码为123456）", function(){
			UIUtils.loading('正在重置请稍候......');
			HttpUtils.httpPost(Config.WEB_SERVER_API + Config.URI.USER_PASSWORD_RESET + id, {}, function (data, success) {
				UIUtils.loaded();
				if(success){
					UIUtils.tip("重置成功", function(){
						UserManage.search();
					});
				}
			});
		});
	},
	"setRole" : function(name, id){
		var t  = new Date().getTime();
		var url = "user_role_manage.html?id="+id+"&t=" + t;
		UIUtils.openWindow("设置角色【"+name+"】", url, 500, 520);
	},
	"closeDetailDialog":function(){
		$('.detailDialog').Dialog('close');
	}
};
