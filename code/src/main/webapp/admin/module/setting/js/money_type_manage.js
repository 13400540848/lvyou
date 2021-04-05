$(function () {
    if (!Utils.checkLogin()) return;
    MoneyTypeManage.init();
    $(window).resize(function () {
        $('#table').bootstrapTable('resetView');
        resizeWH();
    });

    var resizeWH = function () {
        $(".table-box").height($(window).height() - 130);
    };
    resizeWH();
});

var MoneyTypeManage = {
	"init" : function(){
		$('#table').bootstrapTable({
			columns: [
				// { field: 'typeId', title: '标识', width: 50},
				{ field: 'typeName', title: '名称', width: 50 },
				{ field: 'typeCode', title: '标识', width: 50},
				{ field: 'rechargeAddress', title: '充值地址', width: 100},				
				{ field: 'typeMode', title: '类型', width: 50, formatter: MoneyTypeManage.formatterMode},
				{ field: 'rechargeBrokePercent', title: '充值手续费', width: 100, formatter:FormatUtils.formatPercent},				
				{ field: 'cashBrokePercent', title: '提现手续费', width: 100, formatter:FormatUtils.formatPercent},				
				{ field: 'dgBrokePercent', title: 'DG兑换手续费', width: 100, formatter:FormatUtils.formatPercent},				
				{ field: 'typeName', title: '操作', width: 50, formatter: MoneyTypeManage.formatterOp }
			],
			onDblClickRow: function (row) {
			    MoneyTypeManage.openDetail(row.id);
			}
		});
		$(".query-btn").bind("click", function () {
			MoneyTypeManage.search();
		});
		$(".reset-btn").bind("click", function () {
			MoneyTypeManage.reset();
		});
		$("#btnAdd").bind("click", function () {
			MoneyTypeManage.openDetail();
		});

		MoneyTypeManage.search();
	},
	"search" : function (callback) {
		UIUtils.loading('正在查询请稍候......');
		DataUtils.getMoneyTypeList($("#tbMode").val(), function(data){
			UIUtils.loaded();
			$('#table').bootstrapTable('load', data);
			if(callback){
				callback(data);
			}
		});
	},
	"reset":function(){
		Utils.resetSearchQuery("tbQueryBody");
	},
	"formatterMode" : function(value, row, index){
		switch(row.typeMode){
			case 1:
			return '<span style="color:blue">投资币种</span>';
			case 2:
			return '<span style="color:red">系统</span>';
			default:
			return '<span style="color:green">代币</span>';
		}
	},
	"formatterOp" : function(value, row, index){
		return '<a href="javascript:MoneyTypeManage.openDetail(\''+row.typeId+'\');">修改</a>&nbsp;&nbsp;&nbsp;'+
		'<a href="javascript:MoneyTypeManage.deleteData(\''+value+'\', \''+row.typeId+'\');">删除</a>';
	},
	"openDetail" : function(id){
		var t  = new Date().getTime();
		var url = "money_type_add.html?t=" + t;
		if(id){
			url += "&id=" + id;
		}
		UIUtils.openWindow("添加/修改币种", url, 420, 450);
	},
	"deleteData":function(title, id){
		UIUtils.confirm("确定要删除【"+title+"】币种吗？", function(){
			UIUtils.loading('正在删除请稍候......');
			HttpUtils.httpDelete(Config.WEB_SERVER_API + Config.URI.MONEY_TYPE_DELETE + id, {}, function (data, success) {
				UIUtils.loaded();
				if(success){
					MoneyTypeManage.search();
				}
			});
		});
	}
};
