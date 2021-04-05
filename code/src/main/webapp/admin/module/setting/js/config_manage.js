$(function () {
    if (!Utils.checkLogin()) return;
    ConfigManage.init();
    $(window).resize(function () {
        $('#table').bootstrapTable('resetView');
        resizeWH();
    });

    var resizeWH = function () {
        $(".table-box").height($(window).height() - 130);
    };
    resizeWH();
});

var ConfigManage = {
	"init" : function(){
		$('#table').bootstrapTable({
			columns: [
				// { field: 'typeId', title: '标识', width: 50},
				{ field: 'title', title: '名称', width: 100 },
				{ field: 'code', title: '标识', width: 100},
				{ field: 'value', title: '值', width: 100},
				{ field: 'description', title: '说明', width: 200 },
				{ field: 'title', title: '操作', width: 50, formatter: ConfigManage.formatterOp }
			],
			onDblClickRow: function (row) {
			    ConfigManage.openDetail(row.id);
			}
		});
		$(".query-btn").bind("click", function () {
			ConfigManage.search();
		});
		$(".reset-btn").bind("click", function () {
			ConfigManage.reset();
		});
		$("#btnAdd").bind("click", function () {
			ConfigManage.openDetail();
		});

		ConfigManage.search();
	},
	"search" : function (callback) {
		UIUtils.loading('正在查询请稍候......');
		var url = Config.WEB_SERVER_API + Config.URI.CONFIG_SEARCH;
        HttpUtils.httpGet(url, {}, function (result) {
            UIUtils.loaded();
			$('#table').bootstrapTable('load', result.rows);
			if(callback){
				callback(data);
			}
		});
	},
	"reset":function(){
		Utils.resetSearchQuery("tbQueryBody");
	},
	"formatterOp" : function(value, row, index){
		return '<a href="javascript:ConfigManage.openDetail(\''+row.id+'\');">修改</a>&nbsp;&nbsp;&nbsp;'+
		'<a href="javascript:ConfigManage.deleteData(\''+value+'\', \''+row.id+'\');">删除</a>';
	},
	"openDetail" : function(id){
		var t  = new Date().getTime();
		var url = "config_add.html?t=" + t;
		if(id){
			url += "&id=" + id;
		}
		UIUtils.openWindow("添加/修改系统参数", url, 370, 400);
	},
	"deleteData":function(title, id){
		UIUtils.confirm("确定要删除【"+title+"】系统参数吗？", function(){
			UIUtils.loading('正在删除请稍候......');
			HttpUtils.httpDelete(Config.WEB_SERVER_API + Config.URI.CONFIG_DELETE + id, {}, function (data, success) {
				UIUtils.loaded();
				if(success){
					ConfigManage.search();
				}
			});
		});
	}
};
