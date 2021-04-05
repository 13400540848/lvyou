$(function () {
    if (!Utils.checkLogin()) return;
    var id = Utils.getUrlParam("id");
	if (id) {
		DictItemManage.Id = id;
		DictItemManage.init();
	}    
    $(window).resize(function () {
        $('#table').bootstrapTable('resetView');
        resizeWH();
    });

    var resizeWH = function () {
        $(".table-box").height($(window).height() - 160);
    };
    resizeWH();
});

var DictItemManage = {
	"PageSize" : 50,
	"Id":null,
	"init" : function(){
		$('#table').bootstrapTable({
			columns: [
	          	{ field: 'title', title: '操作', width: 100, align:'center', formatter: DictItemManage.formatterOp },
				{ field: 'name', title: '标签', width: 80 },
				{ field: 'code', title: '编号', width: 80 },
				{ field: 'value', title: '值', width: 80 },
				{ field: 'sort', title: '显示顺序', width: 40 },
				{ field: 'remark', title: '备注', width: 120 }	
//                { field: 'locationName', title: '广告位置', width: 50 },				
			],
			onDblClickRow: function (row) {
			    DictItemManage.openDetail(row.id);
			}
		});
		$(".query-btn").bind("click", function () {
			DictItemManage.search();
		});
		$(".reset-btn").bind("click", function () {
			DictItemManage.reset();
		});
		$("#btnAdd").bind("click", function () {
			DictItemManage.openDetail();
		});
		if(DictItemManage.Id){
			$("#tbDictId").val(DictItemManage.Id);
		}
		DictItemManage.search();
	},
	"search" : function(){
		this.searchData(0, function(data){
			$("#pagin").pagination(data.total, {
				callback: DictItemManage.pageSelectCallback,
				items_per_page: DictItemManage.PageSize //每页显示1项
			});
		});
	},
	"searchData": function (pageIndex, callback) {
		var query = {};
		query.condition = DataTableUtils.getSearchQuery("tbQueryBody");
		DataTableUtils.getQueryPageList(Config.URI.DICT_ITEM_SEARCH, query, pageIndex, DictItemManage.PageSize, function(data){
			$('#table').bootstrapTable('load', data);
			if(callback){
				callback(data);
			}
		});
	},
	"pageSelectCallback" : function(page) {
		DictItemManage.searchData(page, function(data){ });
	},
	"formatterOp" : function(value, row, index){
		return '<div class="table-buttons"><a href="javascript:DictItemManage.openDetail(\''+row.id+'\');" class="edit">修改</a>&nbsp;&nbsp;&nbsp;'+
			'<a href="javascript:DictItemManage.deleteData(\''+row.name+'\', \''+row.id+'\');" class="del">删除</a></div>';
	},
	"openDetail" : function(id){
		var t  = new Date().getTime();
		var url = "dict_item_form.html?pid="+DictItemManage.Id+"&t=" + t;
		if(id){
			url += "&id=" + id;
		}
		UIUtils.openWindow((id?"修改":"添加")+"字典项", url, 800, 320);
	},
	"deleteData":function(name, id){
		UIUtils.confirm("确定要删除【"+name+"】字典项吗？", function(){
			UIUtils.loading('正在删除请稍候......');
			HttpUtils.httpDelete(Config.WEB_SERVER_API + Config.URI.DICT_ITEM_DELETE + id, {}, function (data, success) {
				UIUtils.loaded();
				if(success){
					DictItemManage.search();
				}
			});
		});
	},
	"closeDetailDialog":function(){
		$('.detailDialog').Dialog('close');
	}
};
