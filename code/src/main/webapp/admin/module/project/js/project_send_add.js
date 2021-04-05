$(function () {
	$(window).resize(function () {
		$('#tableType').bootstrapTable('resetView');
		$('#tableUser').bootstrapTable('resetView');
		resizeWH();
	});

	var resizeWH = function () {
		$(".table-box").height($(window).height() - 100);
	};

	var id = Utils.getUrlParam("id");
	if (id) {
		ProjectSendAdd.Id = id;
		resizeWH();
		ProjectSendAdd.init();
	}
});

var ProjectSendAdd = {
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
		$("#btnAddType").click(function(){
			ProjectSendAdd.addType(ProjectSendAdd.Id);
		});
		if (ProjectSendAdd.Id) {
			$('#tableType').bootstrapTable({
				columns: [
					{ field: 'id', title: '发币编号', width: 150 },
					{ field: 'allMoney', title: '发币数量', width: 50 },
					{ field: 'sendMoney', title: '成功数量', width: 50 },
					{ field: 'createTime', title: '发币时间', width: 50, formatter: FormatUtils.formatterDateTime }
				]
			});
			$('#tableUser').bootstrapTable({
				columns: [
					// { field: 'id', title: '发币编号', width: 50 },
					{ field: 'userName', title: '用户账号', width: 50 },
					{ field: 'orderId', title: '订单号', width: 50 },
					{ field: 'projectMoney', title: '投资数量', width: 60, formatter: ProjectSendAdd.formatterUserMoney },
					{ field: 'sendMoney', title: '已发币', width: 60, formatter: ProjectSendAdd.formatterUserMoney },
					{ field: 'createTime', title: '投资时间', width: 140, formatter: FormatUtils.formatterDateTime }
					// { field: 'sendTime', title: '发币时间', width: 140, formatter: FormatUtils.formatterDateTime }
				]
			});
			ProjectSendAdd.search();
		}
	},
	"search": function () {
		UIUtils.loading('正在查询请稍候......');
		HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.PROJECT_INFO + ProjectSendAdd.Id, {}, function (data) {
			UIUtils.loaded();
			if (data && data.rows) {
				ProjectSendAdd.ProjectData = data.rows;
				Utils.setFormFieldValue("tbForm", ProjectSendAdd.ProjectData);
				$("#imageView").attr("src", ProjectSendAdd.ProjectData.image || '');
				$("#tbImage").attr("src", ProjectSendAdd.ProjectData.image || '');
				ProjectSendAdd.searchMoney();
				ProjectSendAdd.searchUser();
			}
		});
	},
	"searchMoney":function(){
		UIUtils.loading('正在查询请稍候......');
		HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.PROJECT_MONEY_SEND_SEARCH, {project_id:ProjectSendAdd.Id}, function (data) {
			UIUtils.loaded();
			if (data && data.rows) {
				$('#tableType').bootstrapTable('load', data.rows);
			}
		});			
	},
	"searchUser" : function(){
		this.searchUserData(0, function(data){
			$("#pagin").pagination(data.total, {
				callback: ProjectSendAdd.pageSelectCallback,
				items_per_page: ProjectSendAdd.PageSize //每页显示1项
			});
		});
	},
	"searchUserData": function (pageIndex, callback) {
		UIUtils.loading('正在查询请稍候......');
		var query = {projectId:ProjectSendAdd.Id};
		query.offset = pageIndex;
		query.limit = ProjectSendAdd.PageSize;
		HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.USER_MONEY_PROJECT_LIST, query, function (data) {
			UIUtils.loaded();
			$('#tableUser').bootstrapTable('load', data.rows);
			if(callback){
				callback(data);
			}
		});	
	},		
	"pageSelectCallback" : function(page) {
		ProjectSendAdd.searchUserData(page, function(data){ });
	},
	"formatterUserMoney": function (value, row, index) {
		return (value||'0') + " " + ProjectSendAdd.ProjectData.moneyTypeName;
	},
	"formatterOp": function (value, row, index) {
		return '<a href="javascript:ProjectSendAdd.editType(\'' + row.id + '\');">修改</a>&nbsp;&nbsp;&nbsp;' +
			'<a href="javascript:ProjectSendAdd.deleteType(\'' + value + '\', \'' + row.id + '\');">删除</a>';
	},
	"saveData": function () {
		if(!Utils.validateFormFieldValue("tbForm")){
			return;
		}
		var query = Utils.getFormFieldValue("tbForm");
		if (ProjectSendAdd.Id) {
			query.id = ProjectSendAdd.Id;
		}
		query.content = um.getContent();
		UIUtils.loading('正在保存请稍候......');
		HttpUtils.httpPost(Config.WEB_SERVER_API + Config.URI.PROJECT_SAVE, query, function (data) {
			UIUtils.loaded();
			if (parent && parent.UIUtils) {
				parent.ProjectManage.search();
				parent.UIUtils.closeWindow();
			}
		});
	},
	"addType":function(id){
		var t  = new Date().getTime();
		var url = "project_send_money_add.html?t=" + t + "&id=" + id;
		UIUtils.openWindow("发币", url, 370, 180);
	}
};