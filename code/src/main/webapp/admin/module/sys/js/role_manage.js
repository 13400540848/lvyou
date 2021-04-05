$(function () {
    if (!Utils.checkLogin()) return;
    DataUtils.getDictItems(RoleManage.DictKeys, function(items){
    	RoleManage.DictItemList = items;
    	DataTableUtils.setSearchQueryDict("tbQueryBody", items);
    	RoleManage.init();
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

var RoleManage = {
	"PageSize" : 50,
	"DictKeys":["NORMAL_STATUS"],
	"DictItemList":null,
	"init" : function(){
		$('#table').bootstrapTable({
			columns: [
	          	{ field: 'title', title: '操作', width: 80, align:'center', formatter: RoleManage.formatterOp },
				{ field: 'name', title: '角色名称', width: 100 },
				{ field: 'code', title: '角色编号', width: 100 },
				{ field: 'status', title: '状态', width: 40, dictKey:'NORMAL_STATUS', dictItems:RoleManage.DictItemList, formatter: FormatUtils.formatDictItem },
				{ field: 'description', title: '说明', width: 150 }				
//                { field: 'locationName', title: '广告位置', width: 50 },				
			],
			onDblClickRow: function (row) {
			    RoleManage.openDetail(row.id);
			}
		});
		$(".query-btn").bind("click", function () {
			RoleManage.search();
		});
		$(".reset-btn").bind("click", function () {
			RoleManage.reset();
		});
		$("#btnAdd").bind("click", function () {
			RoleManage.openDetail();
		});

		$('.detailDialog .dialog-btn').click(function (e) {
			$('.detailDialog').Dialog('close');
		});
		$('.moneyDialog .dialog-btn').click(function (e) {
			$('.moneyDialog').Dialog('close');
		});
		RoleManage.search();
	},
	"search" : function(){
		this.searchData(0, function(data){
			$("#pagin").pagination(data.total, {
				callback: RoleManage.pageSelectCallback,
				items_per_page: RoleManage.PageSize //每页显示1项
			});
		});
	},
	"searchData": function (pageIndex, callback) {
		var query = {};
		query.condition = DataTableUtils.getSearchQuery("tbQueryBody");
		DataTableUtils.getQueryPageList(Config.URI.ROLE_SEARCH, query, pageIndex, RoleManage.PageSize, function(data){
			$('#table').bootstrapTable('load', data);
			if(callback){
				callback(data);
			}
		});
	},
	"reset":function(){
		DataTableUtils.resetSearchQuery("tbQueryBody");
		RoleManage.search();
	},
	"pageSelectCallback" : function(page) {
		RoleManage.searchData(page, function(data){ });
	},
	"formatterOp" : function(value, row, index){
		return '<div class="table-buttons"><a href="javascript:RoleManage.openDetail(\''+row.id+'\');" class="edit">修改</a>&nbsp;&nbsp;&nbsp;'+
			'<a href="javascript:RoleManage.deleteData(\''+row.name+'\', \''+row.id+'\');" class="del">删除</a>&nbsp;&nbsp;&nbsp;'+
			'<a href="javascript:RoleManage.setMenu(\''+row.name+'\', \''+row.id+'\');" class="count">设置菜单</a></div>';
	},
	"openDetail" : function(id){
		var t  = new Date().getTime();
		var url = "role_form.html?t=" + t;
		if(id){
			url += "&id=" + id;
		}
		UIUtils.openWindow((id?"修改":"添加")+"角色", url, 800, 520);
	},
	"deleteData":function(name, id){
		UIUtils.confirm("确定要删除【"+name+"】角色吗？", function(){
			UIUtils.loading('正在删除请稍候......');
			HttpUtils.httpDelete(Config.WEB_SERVER_API + Config.URI.ROLE_DELETE + id, {}, function (data, success) {
				UIUtils.loaded();
				if(success){
					RoleManage.search();
				}
			});
		});
	},
	"closeDetailDialog":function(){
		$('.detailDialog').Dialog('close');
	},
	"setMenu":function(name, id){
		var t  = new Date().getTime();
		var url = "role_menu_manage.html?id="+id+"&t=" + t;
		UIUtils.openWindow("设置菜单【" + name + "】", url, 500, 520);
	},
};
