$(function () {
    if (!Utils.checkLogin()) return;
    ProjectSendManage.init();
    $(window).resize(function () {
        $('#table').bootstrapTable('resetView');
        resizeWH();
    });

    var resizeWH = function () {
        $(".table-box").height($(window).height() - 130);
    };
    resizeWH();
});

var ProjectSendManage = {
	"TypeList":[],
	"PageSize" : 50,
	"init" : function(){
		$('#table').bootstrapTable({
			columns: [
				{ field: 'title', title: '项目名称', width: 60 },
				{ field: 'startTime', title: '开始时间', width: 50, formatter:FormatUtils.parseTimestampToDate },
				{ field: 'endTime', title: '结束时间', width: 50, formatter:FormatUtils.parseTimestampToDate },
				{ field: 'moneyTypeName', title: '项目币种', width: 30 },
				{ field: 'star', title: '项目币种数量', width: 50, formatter: ProjectSendManage.formatProjectMoneyAll },
				{ field: 'moneyTypes', title: '接受币种', width: 50},	
				{ field: 'star', title: '目标数量', width: 50, formatter: ProjectSendManage.formatterAllMoney },			
				{ field: 'progress', title: '投资进度', width: 50, formatter:ProjectSendManage.formatterProgress },
				{ field: 'progress', title: '已投数量', width: 50, formatter:ProjectSendManage.formatProjectMoneyUser },
				{ field: 'status', title: '项目状态', width: 50, formatter: ProjectSendManage.formatterStatus },
                // { field: 'isRecommend', title: '首页推荐', width: 50, formatter: ProjectSendManage.formatterIsRecommend },
				{ field: 'title', title: '操作', width: 50, formatter: ProjectSendManage.formatterOp }
			],
			onDblClickRow: function (row) {
			    ProjectSendManage.openDetail(row.id);
			}
		});
		$(".query-btn").bind("click", function () {
			ProjectSendManage.search();
		});
		$(".reset-btn").bind("click", function () {
			ProjectSendManage.reset();
		});
		$("#btnAdd").bind("click", function () {
			ProjectSendManage.openDetail();
		});
		this.initType(function(){
			ProjectSendManage.search();
		});
	},
	"initType": function (callback) {
		DataUtils.getMoneyTypeList("1", function(data){
			ProjectSendManage.TypeList = data;
			if(callback){
				callback();
			}
		});
	},
	"search" : function(){
		this.searchData(0, function(data){
			$("#pagin").pagination(data.total, {
				callback: ProjectSendManage.pageSelectCallback,
				items_per_page: ProjectSendManage.PageSize //每页显示1项
			});
		});
	},
	"searchData": function (pageIndex, callback) {
		var query = Utils.getSearchQuery("tbQueryBody");
		query.offset = pageIndex;
		query.limit = ProjectSendManage.PageSize;
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
		ProjectSendManage.searchData(page, function(data){ });
	},
	"reset":function(){
		Utils.resetSearchQuery("tbQueryBody");
	},
	"formatterOp" : function(value, row, index){
		return '<a href="javascript:ProjectSendManage.openDetail(\''+row.id+'\');">发币</a>';
	},
	"formatterIsRecommend" : function(value, row, index){
		if(value == 1){
			return '<span style="color:green">是</span>';
		}else{
			return '<span style="color:gray">否</span>';
		}
	},
	"formatterProgress" : function(value, row, index){
		return value + ' %';
	},
	"formatterAllMoney" : function(value, row, index){
		return FormatUtils.formatMoneyAll(row.projectMoneys);
	},
	"formatProjectMoneyAll" : function(value, row, index){
		return FormatUtils.formatProjectMoneyAll(row);
	},
	"formatProjectMoneyUser" : function(value, row, index){
		return FormatUtils.formatProjectMoneyUser(row);
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
		var url = "project_send_add.html?t=" + t;
		if(id){
			url += "&id=" + id;
		}
		UIUtils.openWindow("项目发币", url, 800, 560);
	},
	"deleteData":function(title, id){
		UIUtils.confirm("确定要删除【"+title+"】项目吗？", function(){
			UIUtils.loading('正在删除请稍候......');
			HttpUtils.httpDelete(Config.WEB_SERVER_API + Config.URI.PROJECT_DELETE + id, {}, function (data, success) {
				UIUtils.loaded();
				if(success){
					ProjectSendManage.search();
				}
			});
		});
	}
};
