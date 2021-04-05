$(function () {
    if (!Utils.checkLogin()) return;
    DictManage.init();
    $(window).resize(function () {
        $('#table').bootstrapTable('resetView');
        resizeWH();
    });

    var resizeWH = function () {
        $(".table-box").height($(window).height() - 160);
    };
    resizeWH();
});

var DictManage = {
	"PageSize" : 50,
	"init" : function(){
		$('#table').bootstrapTable({
			columns: [
	          	{ field: 'title', title: '操作', width: 80, align:'center', formatter: DictManage.formatterOp },
				{ field: 'name', title: '字典名称', width: 100 },
				{ field: 'code', title: '字典编号', width: 100 },
				{ field: 'remark', title: '备注', width: 150 }				
//                { field: 'locationName', title: '广告位置', width: 50 },				
			],
			onDblClickRow: function (row) {
			    DictManage.openDetail(row.id);
			}
		});
		$(".query-btn").bind("click", function () {
			DictManage.search();
		});
		$(".reset-btn").bind("click", function () {
			DictManage.reset();
		});
		$("#btnAdd").bind("click", function () {
			DictManage.openDetail();
		});
		$("#btnSetItem").bind("click", function () {
			DictManage.openDetail();
		});
		

		$('.detailDialog .dialog-btn').click(function (e) {
			$('.detailDialog').Dialog('close');
		});
		$('.moneyDialog .dialog-btn').click(function (e) {
			$('.moneyDialog').Dialog('close');
		});
		DictManage.search();
	},
	"search" : function(){
		this.searchData(0, function(data){
			$("#pagin").pagination(data.total, {
				callback: DictManage.pageSelectCallback,
				items_per_page: DictManage.PageSize //每页显示1项
			});
		});
	},
	"searchData": function (pageIndex, callback) {
		var query = {};
		query.condition = DataTableUtils.getSearchQuery("tbQueryBody");
		DataTableUtils.getQueryPageList(Config.URI.DICT_SEARCH, query, pageIndex, DictManage.PageSize, function(data){
			$('#table').bootstrapTable('load', data);
			if(callback){
				callback(data);
			}
		});
	},
	"reset":function(){
		DataTableUtils.resetSearchQuery("tbQueryBody");
		DictManage.search();
	},
	"pageSelectCallback" : function(page) {
		DictManage.searchData(page, function(data){ });
	},
	"formatterOp" : function(value, row, index){
		return '<div class="table-buttons"><a href="javascript:DictManage.openDetail(\''+row.id+'\');" class="edit">修改</a>&nbsp;&nbsp;&nbsp;'+
			'<a href="javascript:DictManage.deleteData(\''+row.name+'\', \''+row.id+'\');" class="del">删除</a>&nbsp;&nbsp;&nbsp;'+
			'<a href="javascript:DictManage.setItem(\''+row.name+'\', \''+row.id+'\');" class="count">设置字典项</a></div>';
	},
	"openDetail" : function(id){
		var t  = new Date().getTime();
		var url = "dict_form.html?t=" + t;
		if(id){
			url += "&id=" + id;
		}
		UIUtils.openWindow((id?"修改":"添加")+"字典", url, 800, 520);
	},
	"deleteData":function(name, id){
		UIUtils.confirm("确定要删除【"+name+"】字典吗？", function(){
			UIUtils.loading('正在删除请稍候......');
			HttpUtils.httpDelete(Config.WEB_SERVER_API + Config.URI.DICT_DELETE + id, {}, function (data, success) {
				UIUtils.loaded();
				if(success){
					DictManage.search();
				}
			});
		});
	},
	"closeDetailDialog":function(){
		$('.detailDialog').Dialog('close');
	},
	"setItem" : function(name, id){
		var t  = new Date().getTime();
		var url = "dict_item_manage.html?id="+id+"&t=" + t;
		UIUtils.openWindow("设置字典项【" + name + "】", url, 900, 520);
	}
};
