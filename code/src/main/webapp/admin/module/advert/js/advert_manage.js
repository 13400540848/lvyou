$(function () {
    if (!Utils.checkLogin()) return;
    AdvertManage.init();
    $(window).resize(function () {
        $('#table').bootstrapTable('resetView');
        resizeWH();
    });

    var resizeWH = function () {
        $(".table-box").height($(window).height() - 130);
    };
    resizeWH();
});

var AdvertManage = {
	"PageSize" : 50,
	"init" : function(){
		$('#table').bootstrapTable({
			columns: [
				{ field: 'title', title: '广告名称', width: 100 },
				{field:'showOrder', title:'显示顺序', width:50},
				{ field: 'startTime', title: '开始时间', width: 50, formatter:FormatUtils.parseTimestampToDate },
				{ field: 'endTime', title: '结束时间', width: 50, formatter:FormatUtils.parseTimestampToDate },
				{ field: 'description', title: '说明', width: 150 },
				{ field: 'status', title: '状态', width: 50, formatter: AdvertManage.formatterStatus },
                { field: 'locationName', title: '广告位置', width: 50 },
				{ field: 'title', title: '操作', width: 50, formatter: AdvertManage.formatterOp }
			],
			onDblClickRow: function (row) {
			    AdvertManage.openDetail(row.id);
			}
		});
		$(".query-btn").bind("click", function () {
			AdvertManage.search();
		});
		$(".reset-btn").bind("click", function () {
			AdvertManage.reset();
		});
		$("#btnAdd").bind("click", function () {
			AdvertManage.openDetail();
		});

		$('.detailDialog .dialog-btn').click(function (e) {
			$('.detailDialog').Dialog('close');
		});
		$('.moneyDialog .dialog-btn').click(function (e) {
			$('.moneyDialog').Dialog('close');
		});
		AdvertManage.initLocations(function(){
			AdvertManage.initStatus(function(){
				AdvertManage.search();
			});
		});
	},
	"initLocations":function(callback){
		DataUtils.getAdvertLocations(function(data){
			$("#tbLocation").append("<option value='' selected='selected'>——全部——</option>");
			for(var i=0;i<data.length;i++){
				$("#tbLocation").append("<option value='"+data[i].value+"'>"+data[i].text+"</option>");
			}			
			if(callback){
				callback(data);
			}
		});
	},
	"initStatus":function(callback){
		DataUtils.getAdvertStatus(function(data){
			$("#tbStatus").append("<option value='' selected='selected'>——全部——</option>");
			for(var i=0;i<data.length;i++){
				$("#tbStatus").append("<option value='"+data[i].value+"'>"+data[i].text+"</option>");
			}			
			if(callback){
				callback(data);
			}
		});
	},	
	"search" : function(){
		this.searchData(0, function(data){
			$("#pagin").pagination(data.total, {
				callback: AdvertManage.pageSelectCallback,
				items_per_page: AdvertManage.PageSize //每页显示1项
			});
		});
	},
	"searchData": function (pageIndex, callback) {
		var query = Utils.getSearchQuery("tbQueryBody");
		query.offset = pageIndex;
		query.limit = AdvertManage.PageSize;
		UIUtils.loading('正在查询请稍候......');
		HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.ADVERT_SEARCH, query, function(data){
			UIUtils.loaded();
			$('#table').bootstrapTable('load', data.rows);
			if(callback){
				callback(data);
			}
		});
	},
	"reset":function(){
		Utils.resetSearchQuery("tbQueryBody");
	},
	"pageSelectCallback" : function(page) {
		AdvertManage.searchData(page, function(data){ });
	},
	"formatterOp" : function(value, row, index){
		return '<a href="javascript:AdvertManage.openDetail(\''+row.id+'\');">修改</a>&nbsp;&nbsp;&nbsp;'+
			'<a href="javascript:AdvertManage.deleteData(\''+value+'\', \''+row.id+'\');">删除</a>';
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
		var url = "advert_add.html?t=" + t;
		if(id){
			url += "&id=" + id;
		}
		UIUtils.openWindow("添加/修改广告", url, 800, 520);
	},
	"deleteData":function(title, id){
		UIUtils.confirm("确定要删除【"+title+"】广告吗？", function(){
			UIUtils.loading('正在删除请稍候......');
			HttpUtils.httpDelete(Config.WEB_SERVER_API + Config.URI.ADVERT_DELETE + id, {}, function (data, success) {
				UIUtils.loaded();
				if(success){
					AdvertManage.search();
				}
			});
		});
	},
	"closeDetailDialog":function(){
		$('.detailDialog').Dialog('close');
	}
};
