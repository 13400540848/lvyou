$(function () {
	$(window).resize(function () {
		resizeWH();
	});

	var resizeWH = function () {
		// $(".table-box").height($(window).height() - 100);
	};

	var id = Utils.getUrlParam("id");
	if (id) {
		UserCashAdd.Id = id;
		resizeWH();
	}
	UserCashAdd.init();
});

var UserCashAdd = {
	"PageSize": 15,
	"Id": null,
	"init": function () {
		$("#btnClose").click(function () {
			if (parent && parent.UIUtils) {
				parent.UIUtils.closeWindow();
			}
		});
		$("#btnSave").click(function () {
			UserCashAdd.saveData();
		});
	},
	"saveData": function () {
		if(!UserCashAdd.Id) return;
		if(!Utils.validateFormFieldValue("tbForm")){
			return;
		}
		var query = Utils.getFormFieldValue("tbForm");
		if (UserCashAdd.Id) {
			query.id = UserCashAdd.Id;
		}
		UIUtils.loading('正在保存请稍候......');
		HttpUtils.httpPost(Config.WEB_SERVER_API + Config.URI.USER_MONEY_CASH_CHECK, query, function (data, success) {
			UIUtils.loaded();
			if(success){
				alert("审核成功！");
				if (parent && parent.UserCashManage) {
					parent.UserCashManage.search();
					parent.UIUtils.closeWindow();
				}
			}
		});
	}
};