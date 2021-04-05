$(function () {
	$(window).resize(function () {
		$('#table1').bootstrapTable('resetView');
		$('#table2').bootstrapTable('resetView');
		$('#table3').bootstrapTable('resetView');
		$('#table4').bootstrapTable('resetView');
		$('#table5').bootstrapTable('resetView');
		$('#table6').bootstrapTable('resetView');
		$('#table7').bootstrapTable('resetView');
		resizeWH();
	});

	var resizeWH = function () {
		$(".table-box").height($(window).height() - 100);
	};

	var id = Utils.getUrlParam("id");
	if (id) {
		UserInfo.Id = id;
		resizeWH();
	}
	UserInfo.init();
});

var UserInfo = {
	"PageSize": 15,
	"Id": null,
	"init": function () {
		$("#btnClose").click(function () {
			if (parent && parent.UIUtils) {
				parent.UIUtils.closeWindow();
			}
		});
		$("#btnSave").click(function () {
			UserInfo.saveData();
		});

		//项目投资
		$('#table1').bootstrapTable({
			columns: [
				{field:'projectName', title: '项目名称', width:300},
				{field:'typeName', title: '投资币种', width:100},
				{field:'money', title: '投资数量', width:100},
				{field:'createTime', title: '投资时间', formatter: FormatUtils.parseTimestampToDateTime },
      			{ field: 'id', title: '状态', width: 80 , formatter: UserInfo.formatterProjectStatus}
			]
		});

		//充提记录
		$('#table2').bootstrapTable({
			columns: [
				{field:'typeName', title: '币种'},
				{field:'money', title: '数量'},
				{field:'createTime', title: '提交时间', formatter: FormatUtils.parseTimestampToDateTime},
				{field:'checkStatusName', title: '审核状态'},
				{field:'checkReason', title: '审核意见'},
				{field:'checkTime', title: '审核时间', formatter: FormatUtils.parseTimestampToDateTime}
			]
		});

		//充提记录
		$('#table3').bootstrapTable({
			columns: [
				{field:'typeName', title: '币种'},
				{field:'money', title: '数量'},
				{field:'createTime', title: '提交时间', formatter: FormatUtils.parseTimestampToDateTime},
				{field:'checkStatusName', title: '审核状态'},
				{field:'checkReason', title: '审核意见'},
				{field:'checkTime', title: '审核时间', formatter: FormatUtils.parseTimestampToDateTime}
			]
		});
		//买入记录
		$('#table4').bootstrapTable({
			columns: [
				{field:'typeName', title: '币种'},
				{field:'money', title: '数量'},
				{field:'brokeMoney', title: '手续费'},
				{field:'createTime', title: '提交时间', formatter: FormatUtils.parseTimestampToDateTime}
			]
		});
		//卖出记录
		$('#table5').bootstrapTable({
			columns: [
				{field:'typeName', title: '币种', width:100},
				{field:'money', title: '数量', width:100},
				{field:'leaveMoney', title: '余额', width:100},
				{field:'buyTypeName', title: '接受币种', width:100},
				{field:'statusName', title: '状态', width:100},
				{field:'createTime', title: '提交时间', formatter: FormatUtils.parseTimestampToDateTime}
			]
		});
		//个人钱包
		$('#table6').bootstrapTable({
			columns: [
				{ field: 'typeName', title: '币种' },
          		{ field: 'money', title: '剩余数量' }
			]
		});
		//账户记录
		$('#table7').bootstrapTable({
			columns: [
				{ field: 'typeName', title: '币种', width: 100 },
          		{ field: 'logTypeName', title: '交易类型', width: 120 },
				{ field: 'beforeMoney', title: '交易前', width: 120 },
				{ field: 'money', title: '交易数量', width: 120 },
				{ field: 'afterMoney', title: '交易后', width: 120 },
				{ field: 'createTime', title: '交易时间', formatter: FormatUtils.parseTimestampToDateTime}
			]
		});

		if (UserInfo.Id) {
			UserInfo.search();
			UserInfo.load1();
			UserInfo.load2();
			UserInfo.load3();
			UserInfo.load4();
			UserInfo.load5();
			UserInfo.load6();
			UserInfo.load7();
		}
	},
	"search": function () {
		UIUtils.loading('正在查询请稍候......');
		HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.USER_INFO + UserInfo.Id, {}, function (data) {
			UIUtils.loaded();
			if (data && data.rows) {
				Utils.setFormFieldValue("tbForm", data.rows);
				$("#imageView1").attr("src", data.rows.cardImage1 || '');
				$("#imageView2").attr("src", data.rows.cardImage2 || '');
				$("#imageView3").attr("src", data.rows.cardImage3 || '');
				$("#imageView4").attr("src", data.rows.cardImage4 || '');
			}
		});
	},
	"load1" : function(){
		this.loadData1(0, function(data){
			$("#pagin1").pagination(data.total, {
				callback: function(page){
					UserInfo.loadData1(page, function(data){ });
				},
				items_per_page: UserInfo.PageSize //每页显示1项
			});
		});
	},
	"loadData1": function (pageIndex, callback) {
		var query = {};
		query.offset = pageIndex;
		query.limit = UserInfo.PageSize;
		query.userId = UserInfo.Id;
		UIUtils.loading('正在查询请稍候......');
		HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.USER_MONEY_PROJECT_LIST, query, function(data){
			UIUtils.loaded();
			$('#table1').bootstrapTable('load', data.rows);
			if(callback){
				callback(data);
			}
		});
	},
	"load2" : function(){
		this.loadData2(0, function(data){
			$("#pagin2").pagination(data.total, {
				callback: function(page){
					UserInfo.loadData2(page, function(data){ });
				},
				items_per_page: UserInfo.PageSize //每页显示1项
			});
		});
	},
	"loadData2": function (pageIndex, callback) {
		var query = {};
		query.userId = UserInfo.Id;
		query.offset = pageIndex;
		query.limit = UserInfo.PageSize;
		UIUtils.loading('正在查询请稍候......');
		HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.USER_MONEY_RECHARGE_SEARCH, query, function(data){
			UIUtils.loaded();
			$('#table2').bootstrapTable('load', data.rows);
			if(callback){
				callback(data);
			}
		});
	},
	"load3" : function(){
		this.loadData3(0, function(data){
			$("#pagin3").pagination(data.total, {
				callback: function(page){
					UserInfo.loadData3(page, function(data){ });
				},
				items_per_page: UserInfo.PageSize //每页显示1项
			});
		});
	},
	"loadData3": function (pageIndex, callback) {
		var query = {};
		query.userId = UserInfo.Id;
		query.offset = pageIndex;
		query.limit = UserInfo.PageSize;
		UIUtils.loading('正在查询请稍候......');
		HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.USER_MONEY_CASH_SEARCH, query, function(data){
			UIUtils.loaded();
			$('#table3').bootstrapTable('load', data.rows);
			if(callback){
				callback(data);
			}
		});
	},
	"load4" : function(){
		this.loadData4(0, function(data){
			$("#pagin4").pagination(data.total, {
				callback: function(page){
					UserInfo.loadData4(page, function(data){ });
				},
				items_per_page: UserInfo.PageSize //每页显示1项
			});
		});
	},
	"loadData4": function (pageIndex, callback) {
		var query = {};
		query.userId = UserInfo.Id;
		query.offset = pageIndex;
		query.limit = UserInfo.PageSize;
		UIUtils.loading('正在查询请稍候......');
		HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.USER_MONEY_BUY_LIST, query, function(data){
			UIUtils.loaded();
			$('#table4').bootstrapTable('load', data.rows);
			if(callback){
				callback(data);
			}
		});
	},
	"load5" : function(){
		this.loadData5(0, function(data){
			$("#pagin5").pagination(data.total, {
				callback: function(page){
					UserInfo.loadData5(page, function(data){ });
				},
				items_per_page: UserInfo.PageSize //每页显示1项
			});
		});
	},
	"loadData5": function (pageIndex, callback) {
		var query = {};
		query.userId = UserInfo.Id;
		query.offset = pageIndex;
		query.limit = UserInfo.PageSize;
		UIUtils.loading('正在查询请稍候......');
		HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.USER_MONEY_SELL_LIST, query, function(data){
			UIUtils.loaded();
			$('#table5').bootstrapTable('load', data.rows);
			if(callback){
				callback(data);
			}
		});
	},
	"load6" : function(){
		var query = {};
		query.userId = UserInfo.Id;
		UIUtils.loading('正在查询请稍候......');
		HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.USER_MONEY_LIST, query, function(data){
			UIUtils.loaded();
			$('#table6').bootstrapTable('load', data.rows);
		});
	},
	"load7" : function(){
		this.loadData7(0, function(data){
			$("#pagin7").pagination(data.total, {
				callback: function(page){
					UserInfo.loadData7(page, function(data){ });
				},
				items_per_page: UserInfo.PageSize //每页显示1项
			});
		});
	},
	"loadData7": function (pageIndex, callback) {
		var query = {};
		query.userId = UserInfo.Id;
		query.offset = pageIndex;
		query.limit = UserInfo.PageSize;
		UIUtils.loading('正在查询请稍候......');
		HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.USER_MONEY_LOG_LIST, query, function(data){
			UIUtils.loaded();
			$('#table7').bootstrapTable('load', data.rows);
			if(callback){
				callback(data);
			}
		});
	},
	"formatterProjectStatus" : function(value, row, index){
		if(row.status==0){
			return '<span style="color:gray;">进行中</span>';			
		}else{
			return '<span style="color:green;">已发币</span>';
		}
	},
};