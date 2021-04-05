$(function () {
    if (!Utils.checkLogin()) return;
    ProjectManage.init();
    $(window).resize(function () {
        $('#table').bootstrapTable('resetView');
        resizeWH();
    });

    var resizeWH = function () {
        $(".table-box").height($(window).height() - 130);
    };
    resizeWH();
});

var ProjectManage = {
	"TypeList":[],
	"PageSize" : 50,
	"init" : function(){
		$('#table').bootstrapTable({
			columns: [
				{ field: 'title', title: '项目名称', width: 100 },
				{ field: 'startTime', title: '开始时间', width: 50, formatter:FormatUtils.parseTimestampToDate },
				{ field: 'endTime', title: '结束时间', width: 50, formatter:FormatUtils.parseTimestampToDate },
				{ field: 'description', title: '投资说明', width: 150 },
				{ field: 'progress', title: '当前进度', width: 50 },
				{ field: 'star', title: '项目评级', width: 50 },
				{ field: 'status', title: '项目状态', width: 50, formatter: ProjectManage.formatterStatus },
                // { field: 'isRecommend', title: '首页推荐', width: 50, formatter: ProjectManage.formatterIsRecommend },
				{ field: 'title', title: '操作', width: 50, formatter: ProjectManage.formatterOp }
			],
			onDblClickRow: function (row) {
			    ProjectManage.openDetail(row.id);
			}
		});
		$(".query-btn").bind("click", function () {
			ProjectManage.search();
		});
		$(".reset-btn").bind("click", function () {
			ProjectManage.reset();
		});
		$("#btnAdd").bind("click", function () {
			ProjectManage.openDetail();
		});
		this.initType(function(){
			ProjectManage.search();
		});
	},
	"initType": function (callback) {
		DataUtils.getMoneyTypeList("1", function(data){
			ProjectManage.TypeList = data;
			if(callback){
				callback();
			}
		});
	},
	"search" : function(){
		this.searchData(0, function(data){
			$("#pagin").pagination(data.total, {
				callback: ProjectManage.pageSelectCallback,
				items_per_page: ProjectManage.PageSize //每页显示1项
			});
		});
	},
	"searchData": function (pageIndex, callback) {
		var query = Utils.getSearchQuery("tbQueryBody");
		query.offset = pageIndex;
		query.limit = ProjectManage.PageSize;
		UIUtils.loading('正在查询请稍候......');
		HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.PROJECT_SEARCH, query, function(data){
			UIUtils.loaded();
			$('#table').bootstrapTable('load', data.rows);
			if(callback){
				callback(data);
			}
		});
	},
	"pageSelectCallback" : function(page) {
		ProjectManage.searchData(page, function(data){ });
	},
	"reset":function(){
		Utils.resetSearchQuery("tbQueryBody");
	},
	"formatterOp" : function(value, row, index){
		return '<a href="javascript:ProjectManage.openDetail(\''+row.id+'\');">修改</a>&nbsp;&nbsp;&nbsp;'+
			'<a href="javascript:ProjectManage.deleteData(\''+value+'\', \''+row.id+'\');">删除</a>';
	},
	"formatterIsRecommend" : function(value, row, index){
		if(value == 1){
			return '<span style="color:green">是</span>';
		}else{
			return '<span style="color:gray">否</span>';
		}
	},
	"formatterStatus" : function(value, row, index){
		if(value == 1){
			return '<span style="color:green">已发布</span>';
		}else if(value == 2){
			return '<span style="color:red">下架</span>';
		}else{
			return '<span style="color:gray">待发布</span>';
		}
	},
	"openDetail" : function(id){
		var t  = new Date().getTime();
		var url = "project_add.html?t=" + t;
		if(id){
			url += "&id=" + id;
		}
		UIUtils.openWindow("发布/修改项目", url, 800, 520);
	},
	"deleteData":function(title, id){
		UIUtils.confirm("确定要删除【"+title+"】项目吗？", function(){
			UIUtils.loading('正在删除请稍候......');
			HttpUtils.httpDelete(Config.WEB_SERVER_API + Config.URI.PROJECT_DELETE + id, {}, function (data, success) {
				UIUtils.loaded();
				if(success){
					ProjectManage.search();
				}
			});
		});
	}
};
