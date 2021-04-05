$(function () {
	var id = Utils.getUrlParam("id");
	if (id) {
		DictForm.Id = id;
	}
	DictForm.init();
});

var DictForm = {
	"PageSize": 15,
	"FormData":null,
	"Id": null,
	"init": function (callback) {
		$("#btnClose").click(function () {
			if (parent && parent.UIUtils) {
				parent.UIUtils.closeWindow();
			}
		});
		$("#btnSave").click(function () {
			DictForm.saveData();
		});

		if (DictForm.Id) {
			UIUtils.loading('正在查询请稍候......');
			HttpUtils.httpGet(Config.WEB_SERVER_API + Config.URI.DICT_INFO + DictForm.Id, {}, function (data) {
				UIUtils.loaded();
				if (data && data.rows) {
					FormUtils.setFormFieldValue("tbForm", data.rows);
					DictForm.FormData = data.rows;
				}
			});			
		}
	},
	"saveData": function () {
		if(!FormUtils.validateFormFieldValue("tbForm")){
			return;
		}
		var query = FormUtils.getFormFieldValue("tbForm", DictForm.FormData);
		UIUtils.loading('正在保存请稍候......');
		HttpUtils.httpPost(Config.WEB_SERVER_API + Config.URI.DICT_SAVE, query, function (data, success) {
			UIUtils.loaded();
			if(success){
				if (parent && parent.DictManage) {
					parent.DictManage.search();
					parent.UIUtils.closeWindow();
				}
			}
		});
	}
};