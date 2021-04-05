$(function () {
    if (!Utils.checkLogin()) return;
    DataUtils.getDictItems(MenuManage.DictKeys, function(items){
    	MenuManage.DictItemList = items;
    	DataTableUtils.setSearchQueryDict("tbQueryBody", items);
    	MenuManage.init();
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

var MenuManage = {
	"PageSize" : 50,
	"DictKeys":["MENU_TYPE", "YES_NO"],
	"DictItemList":null,
	"init" : function(){
		$('#table').bootstrapTable({
			columns: [
	          	{ field: 'title', title: '操作', width: 100, align:'center', formatter: MenuManage.formatterOp },
				{ field: 'name', title: '菜单名称', width: 80 },
				{ field: 'code', title: '菜单编号', width: 80 },
				{ field: 'type', title: '类型', width: 30, dictKey:'MENU_TYPE', dictItems:MenuManage.DictItemList, formatter: FormatUtils.formatDictItem },
				{ field: 'icon', title:'图标', width:30, formatter: MenuManage.formatterIcon},
				{ field: 'sort', title:'显示顺序', width:30},
				{ field: 'url', title: '访问地址', width: 100 },
				{ field: 'isHide', title: '是否隐藏', width: 40, dictKey:'YES_NO', dictItems:MenuManage.DictItemList, formatter: FormatUtils.formatDictItem },
				{ field: 'description', title: '说明', width: 80}
//                { field: 'locationName', title: '广告位置', width: 50 },				
			],
			onDblClickRow: function (row) {
			    MenuManage.openDetail(row.id);
			}
		});
		$(".query-btn").bind("click", function () {
			MenuManage.search();
		});
		$(".reset-btn").bind("click", function () {
			MenuManage.reset();
		});
		$("#btnAdd").bind("click", function () {
			MenuManage.openDetail();
		});

		MenuManage.initNavs(function(){
			MenuManage.search();
		});
		
//		MenuManage.initLocations(function(){
//			MenuManage.initStatus(function(){
//				MenuManage.search();
//			});
//		});
	},
	"initNavs":function(callback){
		DataUtils.getNavList(function(data){
			$("#tbPid").append("<option value='' selected='selected'>——全部——</option>");
			for(var i=0;i<data.length;i++){
				$("#tbPid").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
			}
			if(callback){
				callback(data);
			}
		});
	},
	"search" : function(){
		this.searchData(0, function(data){
			$("#pagin").pagination(data.total, {
				callback: MenuManage.pageSelectCallback,
				items_per_page: MenuManage.PageSize //每页显示1项
			});
		});
	},
	"searchData": function (pageIndex, callback) {
		var query = {};
		query.condition = DataTableUtils.getSearchQuery("tbQueryBody");
		DataTableUtils.getQueryPageList(Config.URI.MENU_SEARCH, query, pageIndex, MenuManage.PageSize, function(data){
			$('#table').bootstrapTable('load', data);
			if(callback){
				callback(data);
			}
		});
	},
	"reset":function(){
		DataTableUtils.resetSearchQuery("tbQueryBody");
		MenuManage.search();
	},
	"pageSelectCallback" : function(page) {
		MenuManage.searchData(page, function(data){ });
	},
	"formatterOp" : function(value, row, index){
		var html = '<div class="table-buttons"><a href="javascript:MenuManage.openDetail(\''+row.id+'\');" class="edit">修改</a>&nbsp;&nbsp;&nbsp;'+
			'<a href="javascript:MenuManage.deleteData(\''+row.name+'\', \''+row.id+'\');" class="del">删除</a>';
		if(row.type==1){
			html += '&nbsp;&nbsp;&nbsp;<a href="javascript:MenuManage.setRole(\''+row.name+'\', \''+row.id+'\');" class="count">设置角色</a>';
		}
		html += '</div>';
		return html;
	},
	"formatterIcon" : function(value, row, index){
		return '<div class="form-icon"><div class="item-icon '+value+'"></div></div>';
	},
	"openDetail" : function(id){
		var t  = new Date().getTime();
		var url = "menu_form.html?t=" + t;
		if(id){
			url += "&id=" + id;
		}
		UIUtils.openWindow((id?"修改":"添加")+"菜单", url, 800, 520);
	},
	"deleteData":function(name, id){
		UIUtils.confirm("确定要删除【"+name+"】菜单吗？", function(){
			UIUtils.loading('正在删除请稍候......');
			HttpUtils.httpDelete(Config.WEB_SERVER_API + Config.URI.MENU_DELETE + id, {}, function (data, success) {
				UIUtils.loaded();
				if(success){
					MenuManage.search();
				}
			});
		});
	},
	"closeDetailDialog":function(){
		$('.detailDialog').Dialog('close');
	},
	"setRole":function(name, id){
		var t  = new Date().getTime();
		var url = "menu_role_manage.html?id="+id+"&t=" + t;
		UIUtils.openWindow("设置角色【" + name + "】", url, 500, 520);
	}
};
