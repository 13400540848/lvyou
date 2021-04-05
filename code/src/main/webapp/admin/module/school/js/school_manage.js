$(function () {
    if (!Utils.checkLogin()) return;
    DataUtils.getDictItems(SchoolManage.DictKeys, function(items){
    	SchoolManage.DictItemList = items;
    	DataTableUtils.setSearchQueryDict("tbQueryBody", items);
    	SchoolManage.init();
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

var SchoolManage = {
	"PageSize" : 50,
	"DictKeys":["NORMAL_STATUS"],
	"DictItemList":null,
	"init" : function(){
		$('#table').bootstrapTable({
			columns: [
	          	{ field: 'title', title: '操作', width: 80, align:'center', formatter: SchoolManage.formatterOp },
				{ field: 'name', title: '学校名称', width: 100 },
				{ field: 'code', title: '学校编号', width: 100 },
				{ field: 'status', title: '状态', width: 40, dictKey:'NORMAL_STATUS', dictItems:SchoolManage.DictItemList, formatter: FormatUtils.formatDictItem }
			],
			onDblClickRow: function (row) {
			    SchoolManage.openDetail(row.id);
			}
		});
		$(".query-btn").bind("click", function () {
			SchoolManage.search();
		});
		$(".reset-btn").bind("click", function () {
			SchoolManage.reset();
		});
		$("#btnAdd").bind("click", function () {
			SchoolManage.openDetail();
		});
		$("#btnExport").bind("click", function () {
			SchoolManage.exportData();
		});
		$('.detailDialog .dialog-btn').click(function (e) {
			$('.detailDialog').Dialog('close');
		});
		$('.moneyDialog .dialog-btn').click(function (e) {
			$('.moneyDialog').Dialog('close');
		});
		layui.use('upload', function () {
			var upload = layui.upload;
			var uploadInst = upload.render({
				elem: '#btnImport', //绑定元素
				accept: 'file',
				exts: 'xls|txt',
				url: Config.WEB_SERVER_API + Config.URI.SCHOOL_IMPORT, //导入接口
				done: function (res) {
					console.log(res);
					if(res.state == 'SUCCESS'){
						UIUtils.tip("导入成功！");
					}else{
						UIUtils.error(res.state);
					}
				},
				error: function (xhr) {
					console.log(xhr);
					alert('上传失败!');
					//请求异常回调
				}
			});
		});
		SchoolManage.search();
	},
	"search" : function(){
		this.searchData(0, function(data){
			$("#pagin").pagination(data.total, {
				callback: SchoolManage.pageSelectCallback,
				items_per_page: SchoolManage.PageSize //每页显示1项
			});
		});
	},
	"searchData": function (pageIndex, callback) {
		var query = {};
		query.condition = DataTableUtils.getSearchQuery("tbQueryBody");
		DataTableUtils.getQueryPageList(Config.URI.SCHOOL_SEARCH, query, pageIndex, SchoolManage.PageSize, function(data){
			$('#table').bootstrapTable('load', data);
			if(callback){
				callback(data);
			}
		});
	},
	"reset":function(){
		DataTableUtils.resetSearchQuery("tbQueryBody");
		SchoolManage.search();
	},
	"pageSelectCallback" : function(page) {
		SchoolManage.searchData(page, function(data){ });
	},
	"formatterOp" : function(value, row, index){
		return '<div class="table-buttons"><a href="javascript:SchoolManage.openDetail(\''+row.id+'\');" class="edit">修改</a>&nbsp;&nbsp;&nbsp;'+
			'<a href="javascript:SchoolManage.deleteData(\''+row.name+'\', \''+row.id+'\');" class="del">删除</a></div>';
	},
	"openDetail" : function(id){
		var t  = new Date().getTime();
		var url = "school_form.html?t=" + t;
		if(id){
			url += "&id=" + id;
		}
		UIUtils.openWindow((id?"修改":"添加")+"学校", url, 800, 520);
	},
	"deleteData":function(name, id){
		UIUtils.confirm("确定要删除【"+name+"】学校吗？", function(){
			UIUtils.loading('正在删除请稍候......');
			HttpUtils.httpDelete(Config.WEB_SERVER_API + Config.URI.SCHOOL_DELETE + id, {}, function (data, success) {
				UIUtils.loaded();
				if(success){
					SchoolManage.search();
				}
			});
		});
	},
	// "importData":function(){
	// 	UIUtils.confirm("确定要导出学校吗？", function(){
	// 		var query = {};
	// 		query.condition = DataTableUtils.getSearchQuery("tbQueryBody");
	// 		query.pageIndex = 1;
	// 		query.pageSize = 10000;
	// 		UIUtils.loading('正在导出请稍候......');
	// 		HttpUtils.httpPost(Config.WEB_SERVER_API + Config.URI.SCHOOL_EXPORT, query, function (data, success) {
	// 			UIUtils.loaded();
	// 			UIUtils.tip("导出成功！");
	// 			if(success && data && data.rows){
	// 				ControlUtils.DownloadFile.download(data.rows);
	// 			}
	// 		});
	// 	});
	// },
	"exportData":function(){
		UIUtils.confirm("确定要导出学校吗？", function(){
			var query = {};
			query.condition = DataTableUtils.getSearchQuery("tbQueryBody");
			query.pageIndex = 1;
			query.pageSize = 10000;
			UIUtils.loading('正在导出请稍候......');
			HttpUtils.httpPost(Config.WEB_SERVER_API + Config.URI.SCHOOL_EXPORT, query, function (data, success) {
				UIUtils.loaded();
				UIUtils.tip("导出成功！");
				if(success && data && data.rows){
					ControlUtils.DownloadFile.download(data.rows);
				}
			});
		});
	},
	"closeDetailDialog":function(){
		$('.detailDialog').Dialog('close');
	}
};
