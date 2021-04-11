$(function () {
    if (!Utils.checkLogin()) return;
    DataUtils.getDictItems(CollegeManage.DictKeys, function(items){
    	CollegeManage.DictItemList = items;
    	DataTableUtils.setSearchQueryDict("tbQueryBody", items);
    	CollegeManage.init();
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

var CollegeManage = {
	"PageSize" : 50,
	"DictKeys":["NORMAL_STATUS"],
	"DictItemList":null,
	"init" : function(){
		$('#table').bootstrapTable({
			columns: [
	          	{ field: 'title', title: '操作', width: 80, align:'center', formatter: CollegeManage.formatterOp },
				{ field: 'name', title: '学院名称', width: 100 },
				{ field: 'code', title: '学院编号', width: 100 },
				{ field: 'school.name', title: '所属学校', width: 100 },
				{ field: 'status', title: '状态', width: 40, dictKey:'NORMAL_STATUS', dictItems:CollegeManage.DictItemList, formatter: FormatUtils.formatDictItem }
			],
			onDblClickRow: function (row) {
			    CollegeManage.openDetail(row.id);
			}
		});
		$(".query-btn").bind("click", function () {
			CollegeManage.search();
		});
		$(".reset-btn").bind("click", function () {
			CollegeManage.reset();
		});
		$("#btnAdd").bind("click", function () {
			CollegeManage.openDetail();
		});
		$("#btnExport").bind("click", function () {
			CollegeManage.exportData();
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
				exts: 'xls|xlsx',
				multiple: true,
				url: Config.WEB_SERVER_API + Config.URI.COLLEGE_IMPORT, //导入接口
				done: function (res) {
					console.log(res);
					if(res.resultCode == '0'){
						UIUtils.tip("导入成功！", function(){
							CollegeManage.search();
						});
					}else{
						UIUtils.error(res.resultMsg);
					}
				},
				error: function (xhr) {
					console.log(xhr);
					alert('上传失败!');
					//请求异常回调
				}
			});
		});
		CollegeManage.initSchools(function(){
			CollegeManage.search();
		});
	},
	"initSchools":function(callback){
		DataUtils.getSchoolList(function(data){
			$("#tbSchool").append("<option value='' selected='selected'>——全部——</option>");
			for(var i=0;i<data.length;i++){
				$("#tbSchool").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
			}
			if(callback){
				callback(data);
			}
		});
	},
	"search" : function(){
		this.searchData(0, function(data){
			$("#pagin").pagination(data.total, {
				callback: CollegeManage.pageSelectCallback,
				items_per_page: CollegeManage.PageSize //每页显示1项
			});
		});
	},
	"searchData": function (pageIndex, callback) {
		var query = {};
		query.condition = DataTableUtils.getSearchQuery("tbQueryBody");
		DataTableUtils.getQueryPageList(Config.URI.COLLEGE_SEARCH, query, pageIndex, CollegeManage.PageSize, function(data){
			$('#table').bootstrapTable('load', data);
			if(callback){
				callback(data);
			}
		});
	},
	"reset":function(){
		DataTableUtils.resetSearchQuery("tbQueryBody");
		CollegeManage.search();
	},
	"pageSelectCallback" : function(page) {
		CollegeManage.searchData(page, function(data){ });
	},
	"formatterOp" : function(value, row, index){
		return '<div class="table-buttons"><a href="javascript:CollegeManage.openDetail(\''+row.id+'\');" class="edit">修改</a>&nbsp;&nbsp;&nbsp;'+
			'<a href="javascript:CollegeManage.deleteData(\''+row.name+'\', \''+row.id+'\');" class="del">删除</a></div>';
	},
	"openDetail" : function(id){
		var t  = new Date().getTime();
		var url = "college_form.html?t=" + t;
		if(id){
			url += "&id=" + id;
		}
		UIUtils.openWindow((id?"修改":"添加")+"学院", url, 800, 520);
	},
	"deleteData":function(name, id){
		UIUtils.confirm("确定要删除【"+name+"】学院吗？", function(){
			UIUtils.loading('正在删除请稍候......');
			HttpUtils.httpDelete(Config.WEB_SERVER_API + Config.URI.COLLEGE_DELETE + id, {}, function (data, success) {
				UIUtils.loaded();
				if(success){
					CollegeManage.search();
				}
			});
		});
	},
	// "importData":function(){
	// 	UIUtils.confirm("确定要导出学院吗？", function(){
	// 		var query = {};
	// 		query.condition = DataTableUtils.getSearchQuery("tbQueryBody");
	// 		query.pageIndex = 1;
	// 		query.pageSize = 10000;
	// 		UIUtils.loading('正在导出请稍候......');
	// 		HttpUtils.httpPost(Config.WEB_SERVER_API + Config.URI.COLLEGE_EXPORT, query, function (data, success) {
	// 			UIUtils.loaded();
	// 			UIUtils.tip("导出成功！");
	// 			if(success && data && data.rows){
	// 				ControlUtils.DownloadFile.download(data.rows);
	// 			}
	// 		});
	// 	});
	// },
	"exportData":function(){
		UIUtils.confirm("确定要导出学院吗？", function(){
			var query = {};
			query.condition = DataTableUtils.getSearchQuery("tbQueryBody");
			query.pageIndex = 1;
			query.pageSize = 10000;
			UIUtils.loading('正在导出请稍候......');
			HttpUtils.httpPost(Config.WEB_SERVER_API + Config.URI.COLLEGE_EXPORT, query, function (data, success) {
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
