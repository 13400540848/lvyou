$(function () {
	$(window).resize(function () {
		resizeWH();
	});

	var resizeWH = function () {
		// $(".table-box").height($(window).height() - 100);
	};

	var id = Utils.getUrlParam("id");
	if (id) {
		UserRechargeAdd.Id = id;
		resizeWH();
	}
	UserRechargeAdd.init();
});

var UserRechargeAdd = {
	"PageSize": 15,
	"Id": null,
	"init": function () {
		$("#btnClose").click(function () {
			if (parent && parent.UIUtils) {
				parent.UIUtils.closeWindow();
			}
		});
		$("#btnSave").click(function () {
			UserRechargeAdd.saveData();
		});
	},
	"saveData": function () {
		if(!UserRechargeAdd.Id) return;
		if(!Utils.validateFormFieldValue("tbForm")){
			return;
		}
		var query = Utils.getFormFieldValue("tbForm");
		if (UserRechargeAdd.Id) {
			query.id = UserRechargeAdd.Id;
		}
		UIUtils.loading('正在保存请稍候......');
		HttpUtils.httpPost(Config.WEB_SERVER_API + Config.URI.USER_MONEY_RECHARGE_CHECK, query, function (data, success) {
			UIUtils.loaded();
			if(success){
				alert("审核成功！");
				if (parent && parent.UserRechargeManage) {
					parent.UserRechargeManage.search();
					parent.UIUtils.closeWindow();
				}
			}
		});
	}
};